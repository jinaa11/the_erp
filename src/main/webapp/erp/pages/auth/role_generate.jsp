<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>권한 관리</title>
<!-- base:css -->
<link rel="stylesheet"
	href="/erp/vendors/typicons.font/font/typicons.css">
<link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
<!-- endinject -->
<!-- plugin css for this page -->
<!-- End plugin css for this page -->
<!-- inject:css -->
<link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
<!-- endinject -->
<link rel="shortcut icon" href="/erp/images/favicon.png" />

<style>
.modal {
	display: none; /* 기본적으로 숨김 */
	position: fixed; /* 고정 위치 */
	z-index: 1; /* 가장 위에 표시 */
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto; /* 스크롤이 필요한 경우 */
	background-color: rgba(0, 0, 0, 0.4); /* 배경에 투명도 */
}

.modal-content {
	background-color: #fefefe;
	margin: 15% auto; /* 화면 중앙에 배치 */
	padding: 20px;
	border: 1px solid #888;
	width: 15%; /* 모달 너비 */
}

.close {
	color: #aaa;
	font-size: 28px;
	font-weight: bold;
	float: right;
	cursor: pointer;
}

.close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}
</style>

</head>
<body>
	<%@ include file="/erp/layout/top_layout.jsp"%>

	<!-- partial -->
	<div class="container-fluid page-body-wrapper">

		<!-- partial -->
		<!-- partial:partials/_sidebar.html -->

		<%@ include file="/erp/layout/side_layout.jsp"%>
		<div class="container-scroller">
			<div class="col-lg-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">기능 별 권한 설정</h4>
						<div
							style="display: flex; justify-content: space-between; gap: 10px;">
							<input type="button" value="권한 추가"
								style="flex: 1; padding: 8px; box-sizing: border-box;"
								id="add-role-modal"> <input type="button"
								value="기능별 권한 추가"
								style="flex: 1; padding: 8px; box-sizing: border-box;"
								id="add-featurerole-modal">
						</div>
						<div class="table-responsive">
							<table class="table table-hover" id="dynamicTable">
								<thead>
									<tr>
										<th>주소</th>
										<th>http 메서드</th>
										<th>추가일</th>
										<th>추가자</th>
										<th>규칙</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- page-body-wrapper ends -->
	</div>
	<%@ include file="/erp/layout/footer_layout.jsp"%>
	<!-- 권한 추가 모달 -->
	<div id="roleModal" class="modal" style="display: none;">
		<div class="modal-content">
			<span class="close" id="roleModalClose">&times;</span>
			<h4>권한 추가</h4>
			<label for="departmentId">관련부서: </label>
			<select id="departmentId" name="departmentId">
			  <option value="1">인사</option>
			  <option value="21">회계</option>
			  <option value="22">총무</option>
			</select>
			<br />
			<!-- <input type="text" id="roleHttpMethod" placeholder="HTTP 메서드"><br/><br/>
	        <input type="text" id="roleUserName" placeholder="추가자"><br/><br/> -->
			<button id="saveRole">저장</button>
		</div>
	</div>

	<!-- 기능별 권한 추가 모달 -->
	<div id="featureRoleModal" class="modal" style="display: none;">
		<div class="modal-content">
			<span class="close" id="featureRoleModalClose">&times;</span>
			<h4>기능별 권한 추가</h4>
			<input type="text" id="featureRoleUrl" placeholder="URL 주소"><br />
			<br /> 
			<label for="featureRoleHttpMethod">관련 권한: </label>
			<select id="featureRoleHttpMethod" name="featureRoleHttpMethod">
			  <option value="GET">GET</option>
			  <option value="PUT">PUT</option>
			  <option value="POST">POST</option>
			  <option value="DELETE">DELETE</option>
			</select><br />
			<br /> 
			<!-- select 태그로 변경 -->
			<label for="featureRoleContraintRoleSeq">관련권한: </label>
			<select id="featureRoleContraintRoleSeq">
				<!-- 서버에서 받아온 데이터로 동적으로 옵션을 추가할 예정 -->
			</select><br />
			<br />
			<button id="saveFeatureRole">저장</button>
		</div>
	</div>


	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<!-- container-scroller -->
	<!-- base:js -->
	<script src="/erp/vendors/js/vendor.bundle.base.js"></script>
	<!-- endinject -->
	<!-- inject:js -->
	<script src="/erp/js/off-canvas.js"></script>
	<script src="/erp/js/hoverable-collapse.js"></script>
	<script src="/erp/js/template.js"></script>
	<script src="/erp/js/settings.js"></script>
	<script src="/erp/js/todolist.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							$(document)
									.ready(
											function() {
												$
														.ajax({
															url : "/api/v1/auth/features",
															method : 'GET',
															contentType : "application/json",
															dataType : "json",
															success : function(
																	obj) {
																let tableBody = $("#dynamicTable tbody");
																tableBody
																		.empty();

																$
																		.each(
																				obj.data.datas,
																				function(
																						index,
																						item) {
																					let date = new Date(
																							item.createAt);
																					let formattedDate = date
																							.toISOString()
																							.split(
																									'T')[0];

																					let row = $("<tr></tr>");
																					row
																							.attr(
																									"data-editable",
																									"false"); // 기본적으로 수정 불가능

																					let urlInput = $("<input type='text' value='" + item.url + "' disabled />");
																					row
																							.append(
																									"<td></td>")
																							.find(
																									"td")
																							.last()
																							.append(
																									urlInput);

																					let httpMethodInput = $("<input type='text' value='" + item.httpMethod + "' disabled />");
																					row
																							.append(
																									"<td></td>")
																							.find(
																									"td")
																							.last()
																							.append(
																									httpMethodInput);

																					let dateInput = $("<input type='text' value='" + formattedDate + "' disabled />");
																					row
																							.append(
																									"<td></td>")
																							.find(
																									"td")
																							.last()
																							.append(
																									dateInput);

																					let userNameInput = $("<input type='text' value='" + item.userName + "' disabled />");
																					row
																							.append(
																									"<td></td>")
																							.find(
																									"td")
																							.last()
																							.append(
																									userNameInput);

																					let roleSeqInput = $("<input type='text' value='" + item.roleSeq + "' disabled />");
																					row
																							.append(
																									"<td></td>")
																							.find(
																									"td")
																							.last()
																							.append(
																									roleSeqInput);

																					let patchButtonTd = $("<td></td>");
																					let patchButton = $(
																							"<button></button>")
																							.text(
																									"수정");
																					patchButtonTd
																							.append(patchButton);
																					row
																							.append(patchButtonTd);

																					let delButtonTd = $("<td></td>");
																					let delButton = $(
																							"<button></button>")
																							.text(
																									"삭제");
																					delButtonTd
																							.append(delButton);
																					row
																							.append(delButtonTd);

																					tableBody
																							.append(row);

																					patchButton
																							.click(function() {
																								let isEditable = row
																										.attr("data-editable") === "true";

																								row
																										.attr(
																												"data-editable",
																												!isEditable);

																								row
																										.find(
																												"input")
																										.each(
																												function() {
																													$(
																															this)
																															.prop(
																																	"disabled",
																																	isEditable);
																												});

																								patchButton
																										.text(isEditable ? "수정"
																												: "저장");

																								if (!isEditable) {
																									patchButton
																											.off('click');
																									patchButton
																											.click(function() {
																												// 수정된 데이터 가져오기
																												let updatedData = {
																													url : urlInput
																															.val(),
																													httpMethod : httpMethodInput
																															.val(),
																													roleSeq : roleSeqInput
																															.val(),
																													featureSeq : item.featureSeq
																												};

																												$
																														.ajax({
																															url : "/api/v1/auth/feature",
																															method : "PUT",
																															contentType : "application/json",
																															data : JSON
																																	.stringify(updatedData),
																															dataType : 'json',
																															success : function(
																																	response) {
																																alert("수정이 완료되었습니다.");
																																console
																																		.log(response);
																																patchButton
																																		.text("수정");
																																row
																																		.attr(
																																				"data-editable",
																																				"false");
																																row
																																		.find(
																																				"input")
																																		.prop(
																																				"disabled",
																																				true);
																															},
																															error : function(
																																	err) {
																																alert("수정 중 오류가 발생했습니다.");
																																console
																																		.error(err);
																															}
																														});
																											});
																								}
																							});

																					// 삭제 버튼 클릭 이벤트
																					delButton
																							.click(function() {
																								$
																										.ajax({
																											url : "/api/v1/auth/feature?featureSeq="
																													+ item.featureSeq,
																											method : "DELETE",
																											dataType : 'json',
																											success : function(
																													response) {
																												alert("삭제가 완료되었습니다.");
																												console
																														.log(response);
																												row
																														.remove();
																											},
																											error : function(
																													err) {
																												alert("삭제 중 오류가 발생했습니다.");
																												console
																														.error(err);
																											}
																										});
																							});
																				});
															},
															error : function(
																	err) {
																alert(err.responseJSON.message);
															}
														});
											});

							function deleteFeature(featureSeq) {
								$.ajax({
									url : '/your-api-endpoint', // 실제 API URL로 변경
									method : 'POST',
									data : JSON.stringify({
										featureSeq : featureSeq
									}), // 전송할 데이터 (JSON 형식)
									contentType : 'application/json',
									dataType : 'json',
									success : function(response) {
										console.log('성공:', response);
										// 서버 응답에 대한 추가적인 처리 (예: 메시지 표시 등)
									},
									error : function(error) {
										console.error('오류:', error);
										// 오류 처리
									}
								});
							}
							
						});
	</script>
	<script>
		$(document)
				.ready(
						function() {
							// 기능별 권한 추가 모달을 띄울 때 해당 부서 목록을 가져오기
							$.ajax({
								url: '/api/v1/auth/roles', // 역할 목록을 가져오는 API 엔드포인트
								method: 'GET',
								contentType: 'application/json',
								dataType: 'json',
								success: function(response) {
									// 성공적으로 데이터를 받았을 경우
									let roles = response.data.roleSummaries; // roleSummaries
									let selectBox = $('#featureRoleContraintRoleSeq');

									// 기존 옵션을 초기화
									selectBox.empty();

									// 서버에서 받은 데이터로 동적으로 옵션 추가
									$.each(roles, function(index, role) {
										console.log(role.roleSeq);
										console.log(role.departmentName);
										selectBox.append(
												$("<option value='" + role.roleSeq + "'>" + role.departmentName + ": " + role.roleSeq + "</option>")
										);
									});
								},
								error: function(error) {
									console.error('권한 목록 로드 오류:', error);
									alert("권한 목록을 불러오는 데 실패했습니다.");
								}
							});

							// '기능별 권한 추가' 버튼 클릭 시 모달 띄우기
							$("#add-featurerole-modal").click(function() {
								$("#featureRoleModal").show(); // 기능별 권한 추가 모달 표시
							});

							// 기능별 권한 추가 모달 닫기 (X 버튼 클릭)
							$("#featureRoleModalClose").click(function() {
								$("#featureRoleModal").hide(); // 기능별 권한 추가 모달 숨기기
							});

							// 모달 바깥 클릭 시 모달 닫기
							$(window).click(function(event) {
								if ($(event.target).is(".modal")) {
									$(".modal").hide(); // 모달 외부를 클릭하면 모든 모달 숨기기
								}
							});

							// 기능별 권한 추가 저장 버튼 클릭
							$("#saveFeatureRole").click(
								function() {
									let featureRoleData = {
										url : $("#featureRoleUrl").val(),
										httpMethod : $("#featureRoleHttpMethod")
												.val(),
										featureRoleContraintRoleSeq : $( 
											"#featureRoleContraintRoleSeq")
											.val()
									};

									$.ajax({
										url : "/api/v1/auth/feature",
										method : "POST",
										contentType : "application/json",
										data : JSON.stringify(featureRoleData),
										success : function(response) {
											alert("기능별 권한이 추가되었습니다.");
											$("#featureRoleModal").hide();
											location.reload();
										},
										error : function(error) {
											alert("기능별 권한 추가 중 오류가 발생했습니다.");
											console.error(error);
										}
									});
								});
						});
	</script>
	<script>
		$(document).ready(
				function() {
					// '권한 추가' 버튼 클릭 시 모달 띄우기
					$("#add-role-modal").click(function() {
						$("#roleModal").show(); // 권한 추가 모달 표시
					});

					// '기능별 권한 추가' 버튼 클릭 시 모달 띄우기
					$("#add-featurerole-modal").click(function() {
						$("#featureRoleModal").show(); // 기능별 권한 추가 모달 표시
					});

					// 권한 추가 모달 닫기 (X 버튼 클릭)
					$("#roleModalClose").click(function() {
						$("#roleModal").hide(); // 권한 추가 모달 숨기기
					});

					// 기능별 권한 추가 모달 닫기 (X 버튼 클릭)
					$("#featureRoleModalClose").click(function() {
						$("#featureRoleModal").hide(); // 기능별 권한 추가 모달 숨기기
					});

					// 모달 바깥 클릭 시 모달 닫기
					$(window).click(function(event) {
						if ($(event.target).is(".modal")) {
							$(".modal").hide(); // 모달 외부를 클릭하면 모든 모달 숨기기
						}
					});

					// 권한 추가 저장 버튼 클릭
					$("#saveRole").click(function() {
						let roleData = {
							departmentId : $("#departmentId").val(),
						};

						$.ajax({
							url : "/api/v1/auth/role",
							method : "POST",
							contentType : "application/json",
							data : JSON.stringify(roleData),
							success : function(response) {
								alert("권한이 추가되었습니다.");
								$("#roleModal").hide();
							},
							error : function(error) {
								alert("권한 추가 중 오류가 발생했습니다.");
								console.error(error);
							}
						});
					});

					// 기능별 권한 추가 저장 버튼 클릭
					$("#saveFeatureRole").click(
							function() {
								let featureRoleData = {
									url : $("#featureRoleUrl").val(),
									httpMethod : $("#featureRoleHttpMethod")
											.val(),
									featureRoleContraintRoleSeq : $(
											"#featureRoleContraintRoleSeq")
											.val()
								};

								$.ajax({
									url : "/api/v1/auth/feature",
									method : "POST",
									contentType : "application/json",
									data : JSON.stringify(featureRoleData),
									success : function(response) {
										alert("기능별 권한이 추가되었습니다.");
										$("#featureRoleModal").hide();
										location.reload();
									},
									error : function(error) {
										alert("기능별 권한 추가 중 오류가 발생했습니다.");
										console.error(error);
									}
								});
							});

				});
	</script>
</body>
</html>