<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>전표 관리</title>

<style>
body {
	font-family: Arial, sans-serif;
}

.centered-header {
	text-align: center;
	margin-bottom: 10px;
}

.centered-header h2 {
	margin: 0;
	font-size: 26px;
}

.centered-header p {
	margin: 0;
	font-size: 18px;
}

.sub-header {
	width: 50%;
	margin: 18px auto;
	display: flex;
	justify-content: space-between;
	font-size: 18px;
}
.scroll-button {
	
    top: 150px; /* 하단에서 20px 위 */
    right: 60px; /* 오른쪽에서 20px 왼쪽 */
    position: fixed;
    padding: 10px 15px;
    font-size: 16px;
    color: white;
    background-color: #007bff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    z-index: 1000; /* 다른 요소 위에 표시 */
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.scroll-button:hover {
    background-color: #0056b3;
}


table {
	width: 60%;
	margin: 20px auto;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
}

th {
	background-color: #f2f2f2;
	text-align: !important;
	vertical-align: middle;
}

td {
	text-align: left;
}

.numeric {
	text-align: right;
}

button.deleteVoucherBtn {
	background-color: #f2f2f2 !important;
	color: #000 !important;
	border: 1px solid #ddd !important;
	padding: 8px !important;
	cursor: pointer !important;
	text-align: center !important;
	width: 100% !important;
}

button.deleteVoucherBtn:hover {
	background-color: #ff4d4d !important;
	color: white !important;
	border-color: #ff4d4d !important;
}

button.submit-button {
	padding: 10px 15px;
	font-size: 16px;
	color: white;
	background-color: #007bff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	text-align: center;
	width: 70px;
}

button.submit-button:hover {
	background-color: #0056b3;
}

.table-container, .form-group{
	display: flex; /* Flexbox 레이아웃 활성화 */
    align-items: center; /* 세로 정렬을 가운데로 맞춤 */
    gap: 20px; /* red-section과 blue-section 사이 간격 */
	
	
	.red-section {
	    flex: 1; /* red-section이 가능한 공간을 차지 */
	}
	
	.blue-section {
	    flex: 0; /* blue-section은 고정 크기를 유지 */
	}
	
	input[type="number"]::-webkit-inner-spin-button, input[type="number"]::-webkit-outer-spin-button
		{
		-webkit-appearance: none;
		margin: 0;
	}
	
	input[type="number"] {
		-moz-appearance: textfield;
	}
	
	input, textarea {
		border: 1px solid #ccc;
		padding: 10px;
		border-radius: 4px;
		width: 100%;
		box-sizing: border-box;
	}
	
	input:focus, textarea:focus {
		border: 2px solid #007bff;
		box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
	}
}
    #sidebar {
        position: fixed; /* 화면에 고정 */
        top: 0;          /* 화면 상단 기준 위치 */
        left: 0;         /* 화면 왼쪽 기준 위치 */
        width: 250px;    /* 사이드바 너비 */
        height: 100vh;   /* 전체 화면 높이 */
        overflow-y: auto; /* 내용이 넘치면 스크롤 가능 */
        color: #fff;    /* 글자 색상 */
    }

    .main-content {
        margin-left: 250px; /* 사이드바 너비만큼 메인 콘텐츠 밀기 */
    }
    
.sidebar-profile {
    margin-top: 60px; /* 위쪽에 여백 추가 */
    padding: 15px;
    display: flex;
    align-items: center;
    color: #fff; /* 텍스트 색상 */
    border-radius: 8px;
}

.sidebar-profile-image img {
    border-radius: 50%; /* 프로필 이미지를 원형으로 */
    width: 50px; /* 이미지 크기 */
    height: 50px;
}

.sidebar-profile-name {
    margin-left: 10px; /* 이미지와 텍스트 간 간격 */
}

.sidebar-name {
    font-size: 16px;
    font-weight: bold;
    margin: 0;
}

.sidebar-designation {
    font-size: 14px;
    margin: 0;
    color: #ccc; /* 약간 밝은 회색 */
}
body {
        margin: 0;
        padding: 0;
    }
    .footer {
        position: relative;
        bottom: 0;
        width: calc(100% - 0px); /* Adjust the footer width */
        text-align: center;
        background-color: #f8f9fa; /* Optional: Footer background color */
        padding: 10px 0;
    }
</style>
<!-- base:css -->
<link rel="stylesheet"
	href="/erp/vendors/typicons.font/font/typicons.css">
<link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
<!-- inject:css -->
<link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
<link rel="shortcut icon" href="/erp/images/favicon.png" />
</head>
<body>
	<div class="container-scroller">

		<!-- partial:partials/_navbar.html -->


		<%@ include file="/erp/layout/top_layout.jsp"%>

		<!-- partial -->
		<div class="container-fluid page-body-wrapper">

			<!-- partial -->
			<!-- partial:partials/_sidebar.html -->

			<%@ include file="/erp/layout/side_layout.jsp"%>
			<div class="main-content">
			<div class="parent-div" style="width: 100%;">
				<div class="main-panel" style="width: 100%;">
					<div class="content-wrapper">
						<div class="row">
							<div class="col-lg-12 grid-margin stretch-card">
								<div class="card">
									<div class="card-body">
										<h4 class="card-title">전표 관리</h4>
										<p class="card-description" style="width: 100%; display: flex; justify-content: space-between; align-items: center;">
										    <span>2024년</span>
										    <button id="scrollToBottom" class="scroll-button">전표 추가</button>
										</p>
										<div class="table-responsive">
											<div id="voucherListContainer">
												<table class="table">
													<thead>
														<tr>
															<th style="text-align: center;">날짜</th>
															<th style="text-align: center;">적요</th>
															<th style="text-align: center;" width="80">계정ID</th>
															<th style="text-align: center;">계정명</th>
															<th style="text-align: center;">차변</th>
															<th style="text-align: center;">대변</th>
															<th></th>
														</tr>
													</thead>
													<tbody>
														<c:set var="debitSum" value="0" />
														<c:set var="creditSum" value="0" />
														<c:forEach var="vo" items="${KEY_SLIP_FLIST}">
															<c:set var="debitSum" value="${debitSum + vo.debit}" />
															<c:set var="creditSum" value="${creditSum + vo.credit}" />
															<tr>
																<td>${vo.voucher_date}</td>
																<td>${vo.descript}</td>
																<td>${vo.account_id}</td>
																<td>${vo.account_name}</td>
																<td class="numeric"><c:if test="${vo.debit != 0}">
																		<fmt:formatNumber value="${vo.debit}" type="number"
																			groupingUsed="true" />
																	</c:if></td>
																<td class="numeric"><c:if test="${vo.credit != 0}">
																		<fmt:formatNumber value="${vo.credit}" type="number"
																			groupingUsed="true" />
																	</c:if></td>
																<td><c:if test="${vo.descript != '합계'}">
																		<form id="deleteVoucherForm" onsubmit="return false;">
																			<input type="hidden" name="voucher_date"
																				value="${vo.voucher_date}"> <input
																				type="hidden" name="descript" value="${vo.descript}">
																			<input type="hidden" name="account_id"
																				value="${vo.account_id}">

																			<button class="deleteVoucherBtn" type="submit"
																				data-voucher-date="${vo.voucher_date}"
																				data-descript="${vo.descript}"
																				data-account-id="${vo.account_id}">삭제</button>
																		</form>
																	</c:if></td>
															</tr>
														</c:forEach>
														<tr>
															<td colspan="4">합계:</td>
															<td class="numeric"><c:if test="${debitSum != 0}">
																	<fmt:formatNumber value="${debitSum}" type="number"
																		groupingUsed="true" />
																</c:if></td>
															<td class="numeric"><c:if test="${creditSum != 0}">
																	<fmt:formatNumber value="${creditSum}" type="number"
																		groupingUsed="true" />
																</c:if></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</div>
											<hr>
											<div
												style="margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; width: 100%; box-sizing: border-box;">
												<h3 style="text-align: center;">전표 추가</h3>
												<form id="addVoucherForm" onsubmit="return false;">
													<div class="form-group">
														<label for="voucher_date">날짜:</label> <input type="date"
															id="voucher_date" name="voucher_date" required
															style="width: 20%; text-align: left; display: inline-block;">
														<label for="descript">적요:</label> <input type="text"
															id="descript" name="descript" required
															style="width: 20%; text-align: left; display: inline-block;">
													</div>

													<div class="table-container">
													 <div class="red-section">
														<table
															style="margin-top: 20px; border: 1px solid #ccc; width: 100%; text-align: center;">
															<thead>
																<tr>
																	<th>차변 계정 ID</th>
																	<th>차변 계정명</th>
																	<th>차변 금액</th>
																	<th>대변 계정 ID</th>
																	<th>대변 계정명</th>
																	<th>대변 금액</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td><input type="text" id="debit_account_id"
																		name="debit_account_id" required></td>
																	<td><input type="text" id="debit_account_name"
																		name="debit_account_name" required></td>
																	<td><input type="number" id="debit" name="debit"
																		required></td>
																	<td><input type="text" id="credit_account_id"
																		name="credit_account_id" required></td>
																	<td><input type="text" id="credit_account_name"
																		name="credit_account_name" required></td>
																	<td><input type="number" id="credit" name="credit"
																		required></td>
																	
																		
																</tr>
															</tbody>
														</table>
															</div>
																<div class="blue-section">
																<button id="addVoucherBtn" type="submit" class="submit-button" style="text-align: center;">입력</button>
																</div>
														</div>		

													</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
			<%@ include file="/erp/layout/footer_layout.jsp"%>
					
				</div>
			</div>
		</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<!-- container-scroller -->
	<!-- base:js -->
	<script src="/erp/vendors/js/vendor.bundle.base.js"></script>
	<!-- endinject -->
	<!-- Plugin js for this page-->
	<!-- End plugin js for this page-->
	<!-- inject:js -->
	<script src="/erp/js/off-canvas.js"></script>
	<script src="/erp/js/hoverable-collapse.js"></script>
	<script src="/erp/js/template.js"></script>
	<script src="/erp/js/settings.js"></script>
	<script src="/erp/js/todolist.js"></script>
	<!-- endinject -->
	<!-- plugin js for this page -->
	<!-- End plugin js for this page -->
	<!-- Custom js for this page-->
	<!-- End custom js for this page-->
	<script>
		$(document)
				.ready(
						function() {
							// 전표 추가 요청
							$(document)
									.on(
											"click",
											"#addVoucherBtn",
											function(event) {
												event.preventDefault(); // 기본 동작 방지

												const voucherDate = $(
														"#voucher_date").val();
												const descript = $("#descript")
														.val();
												const debitAccountId = $(
														"#debit_account_id")
														.val();
												const debitAccountName = $(
												"#debit_account_name")
												.val();
												const debitAmount = $("#debit")
														.val();
												const creditAccountId = $(
														"#credit_account_id")
														.val();

												const creditAccountName = $(
														"#credit_account_name")
														.val();
												const creditAmount = $(
														"#credit").val();

												$
														.ajax({
															url : "/finance/add_voucher",
															type : "POST",
															data : {
																voucher_date : voucherDate,
																descript : descript,
																debit_account_id : debitAccountId,
																debit_account_name : debitAccountName,
																debit : debitAmount,
																credit_account_id : creditAccountId,
																credit_account_name : creditAccountName,
																credit : creditAmount
															},
															success : function(
																	response) {
																if (response
																		.trim() === "success") {
																	alert("전표가 성공적으로 추가되었습니다.");
																	
																	$(
																	"#voucher_date")
																	.val(
																			'');
															$(
																	"#descript")
																	.val(
																			'');
															$(
																	"#debit_account_id")
																	.val(
																			'');
															$(
															"#debit_account_name")
															.val(
																	'');
															$(
																	"#debit")
																	.val(
																			'');
															$(
															"#credit_account_id")
															.val(
																	'');
															$(
															"#credit_account_name")
															.val(
																	'');
															$("#credit").val(
																	'');
															
																	updateVoucherList();
																} else {
																	alert("전표 추가에 실패했습니다.");
																}
															},
															error : function(
																	xhr,
																	status,
																	error) {
																alert("서버와의 통신 중 문제가 발생했습니다.");
															}
														});
											});

							// 전표 삭제 요청
							$(document).on("click", ".deleteVoucherBtn", function (event) {
    event.preventDefault(); // 기본 동작 방지

    const voucherDate = $(this).data("voucher-date");
    const descript = $(this).data("descript");
    const accountId = $(this).data("account-id");

    if (!confirm("정말로 이 전표를 삭제하시겠습니까?")) {
        return; // 사용자가 삭제를 취소한 경우
    }

    // AJAX 요청
    $.ajax({
        url: "/finance/delete_voucher",
        type: "POST",
        data: {
            voucher_date: voucherDate,
            descript: descript,
            account_id: accountId
        },
        success: function (response) {
            if (response.trim() === "success") {
                alert("전표가 성공적으로 삭제되었습니다.");
                updateVoucherList(); // 전표 목록 갱신
            } else {
                alert("전표 삭제에 실패했습니다. 다시 시도해주세요.");
            }
        },
        error: function (xhr, status, error) {
            //console.error("AJAX 요청 실패:", error);
            alert("서버와의 통신 중 문제가 발생했습니다.");
        }
    });
});
							// 전표 목록 갱신 함수
							function updateVoucherList() {
								$
										.ajax({
											url : "/finance/general_ledger",
											type : "GET",
											success : function(html) {
												const updatedContent = $(html)
														.find(
																"#voucherListContainer")
														.html();
												$("#voucherListContainer")
														.html(updatedContent); // 화면에 목록 갱신
											},
											error : function(xhr, status, error) {
												//console.error("목록 갱신 실패:", error);
												alert("목록을 갱신하는 데 문제가 발생했습니다.");
											}
										});
							}
						});
	</script>
	<script>
		document.getElementById("scrollToBottom").addEventListener("click", function() {
		    window.scrollTo({
		        top: document.body.scrollHeight, // 문서 전체 높이로 이동
		        behavior: "smooth" // 부드럽게 스크롤
		    });
		});
	</script>
	
</body>
</html>