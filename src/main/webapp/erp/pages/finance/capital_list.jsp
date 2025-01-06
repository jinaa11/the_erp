<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>CelestialUI Admin</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- base:css -->
<link rel="stylesheet" href="/erp/vendors/typicons.font/font/typicons.css">
<link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
<!-- endinject -->
<!-- inject:css -->
<link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
<!-- endinject -->
<link rel="shortcut icon" href="/images/favicon.png" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

</head>

<body>
	<div class="container-scroller">
		<!-- partial:../../partials/_navbar.html -->


		<%@ include file="/erp/layout/top_layout.jsp"%>

		<div id="top_nav">
			<!-- partial -->
			<div class="container-fluid page-body-wrapper">

				<!-- partial -->
				<!-- partial:../../partials/_sidebar.html -->

				<%@ include file="/erp/layout/side_layout.jsp"%>


				<!-- partial -->
				<div class="main-panel">
					<div class="content-wrapper">
						<div class="row">
							<div class="col-lg-12 grid-margin stretch-card">
								<div class="card">



									<div class="card-body">


										<h2 class="card-title">비용</h2>

										<div class="checkbox-group" style="margin-bottom: 15px; display: flex; gap: 30px;">
											<!-- 첫 번째 체크박스 그룹 -->
											<div class="card" style="padding: 15px; min-width: 200px;">
												<h5 style="font-weight: 700; margin-bottom: 15px; color: #333;">항목선택</h5>
												<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px;">
													<div>
														<label><input type="checkbox" name="department" value="logistics"> 물류</label>
													</div>
													<div>
														<label><input type="checkbox" name="department" value="facility"> 시설</label>
													</div>
													<div>
														<label><input type="checkbox" name="department" value="hr"> 인사</label>
													</div>
												</div>
											</div>

											<!-- 두 번째 체크박스 그룹 -->
											<div class="card" style="padding: 15px; min-width: 200px;">
												<h5 style="font-weight: 700; margin-bottom: 15px; color: #333;">처리상태</h5>
												<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px;">
													<div>
														<label><input type="checkbox" name="status" value="pending"> 처리 안됨</label>
													</div>
													<div>
														<label><input type="checkbox" name="status" value="processing"> 처리 중</label>
													</div>
													<div>
														<label><input type="checkbox" name="status" value="completed"> 처리 완료</label>
													</div>
													<div>
														<label><input type="checkbox" name="status" value="rejected"> 거부</label>
													</div>
												</div>
											</div>
										</div>

										<div class="table-responsive">


											<table class="table">
												<thead>
													<tr>
														<th scope="col">번호</th>
														<th scope="col">날짜</th>
														<th scope="col">설명</th>
														<th scope="col">항목</th>
														<th scope="col">금액</th>
														<th scope="col">결제수단</th>
														<th scope="col">처리상태</th>
													</tr>
												</thead>
												<tbody id="table-tbody">
													<c:forEach var="item" items="${ALIST}">
														<tr>
															<td>${item.referenceSeq}</td>
															<td>${item.manageAt}</td>
															<td>${item.summary}</td>
															<td>${item.capitalType}</td>
															<td><fmt:formatNumber value="${item.cost}" pattern="#,###" />원</td>
															<td>${item.paymentType}</td>
															<td><c:choose>
																	<c:when test="${item.status eq '처리 안됨'}">
																		<label class="badge badge-warning">${item.status}</label>
																	</c:when>
																	<c:when test="${item.status eq '처리 중'}">
																		<label class="badge badge-info">${item.status}</label>
																	</c:when>
																	<c:when test="${item.status eq '처리 완료'}">
																		<label class="badge badge-success">${item.status}</label>
																	</c:when>
																	<c:when test="${item.status eq '거부'}">
																		<label class="badge badge-danger">${item.status}</label>
																	</c:when>
																	<c:otherwise>
																		<label class="badge badge-secondary">${item.status}</label>
																	</c:otherwise>
																</c:choose></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>



										</div>
										<br>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- content-wrapper ends -->
					<!-- partial:../../partials/_footer.html -->

					<!--  footer -->
					<%@ include file="/erp/layout/footer_layout.jsp"%>


					<!-- partial -->
				</div>
				<!-- main-panel ends -->
			</div>
			<!-- page-body-wrapper ends -->
		</div>
	</div>
	<!-- container-scroller -->
	<!-- base:js -->
	<script src="/erp/vendors/js/vendor.bundle.base.js"></script>

	<script src="/erp/js/off-canvas.js"></script>
	<script src="/erp/js/hoverable-collapse.js"></script>
	<script src="/erp/js/template.js"></script>
	<script src="/erp/js/settings.js"></script>
	<script src="/erp/js/todolist.js"></script>


	<script>
		
	</script>
</body>

</html>
