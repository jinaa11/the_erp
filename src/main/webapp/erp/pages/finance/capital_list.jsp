<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>CelestialUI Admin</title>
<style>
	#searchBtn{
	  color: #fff;
	  background-color: #f2125e !important;
   	  border-color: #f2125e !important;
	}
	
	#searchBtn:hover{
  	  background-color: #c60b4a !important;
  	  border-color: #ba0a46 !important;
	}
</style>

</head>

<body>
	<div class="container-scroller">

		<%@ include file="/erp/layout/top_layout.jsp"%>

		<div id="top_nav">
			<!-- partial -->
			<div class="container-fluid page-body-wrapper">

				<%@ include file="/erp/layout/side_layout.jsp"%>


				<!-- partial -->
				<div class="main-panel">
					<div class="content-wrapper">
						<div class="row">
							<div class="col-lg-12 grid-margin stretch-card">
								<div class="card">
									<div class="card-body">
										<h2 class="card-title">비용</h2>
										<div class="checkbox-group" style="margin-bottom: 15px; display: flex; gap: 30px; align-items: flex-start;">
											<!-- 첫 번째 체크박스 그룹 -->
											<div class="card" style="padding: 15px; min-width: 200px;">
												<h5 style="font-weight: 700; margin-bottom: 15px; color: #333;">항목선택</h5>
												<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px;">
													<div>
														<label><input type="checkbox" name="department" value="물류"> 물류</label>
													</div>
													<div>
														<label><input type="checkbox" name="department" value="시설"> 시설</label>
													</div>
													<div>
														<label><input type="checkbox" name="department" value="인"> 인사</label>
													</div>
												</div>
											</div>

											<!-- 두 번째 체크박스 그룹 -->
											<div class="card" style="padding: 15px; min-width: 200px;">
												<h5 style="font-weight: 700; margin-bottom: 15px; color: #333;">처리상태</h5>
												<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px;">
													<div>
														<label><input type="checkbox" name="status" value="처리 안됨"> 처리 안됨</label>
													</div>
													<div>
														<label><input type="checkbox" name="status" value="처리 중"> 처리 중</label>
													</div>
													<div>
														<label><input type="checkbox" name="status" value="처리 완료"> 처리 완료</label>
													</div>
													<div>
														<label><input type="checkbox" name="status" value="거부"> 거부</label>
													</div>
												</div>
											</div>

											<!-- 검색 버튼 -->
											<button type="button" id="searchBtn" class="btn"  tabindex="-1" style="padding: 12px 40px; font-size: 24px;">검색</button>
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
														<tr data-id="${item.capitalManagementSeq}" name="seqId">
															<td>${item.referenceSeq}</td>
															<td>${item.manageAt}</td>
															<td>${item.summary}</td>
															<td>${item.capitalType}</td>
															<td><fmt:formatNumber value="${item.cost}" pattern="#,###" />원</td>
															<td>${item.paymentType}</td>
															<td class="status-cell"><c:choose>
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
															<td class="action-cell">
																<button class="btn btn-sm btn-outline-primary edit-btn">수정</button>
																<div class="btn-group" style="display: none;">
																	<button class="btn btn-sm btn-success save-btn">완료</button>
																	<button class="btn btn-sm btn-danger cancel-btn">취소</button>
																</div>
															</td>
														</tr>
													</c:forEach>
												</tbody>




											</table>
										</div>
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
		
	
	// HTML에 체크박스 이벤트와 검색 버튼 클릭 이벤트를 추가
			$(document).ready(function() {
    // 검색 버튼 클릭 이벤트
		    $('#searchBtn').click(function() {
		        sendAjaxRequest();
		    });
		
		    // Ajax 공통 요청 함수
		    function sendAjaxRequest(additionalData = {}) {
		        let capitalTypes = [];
		        let statuses = [];
		        
		        $('input[name="department"]:checked').each(function() {
		            capitalTypes.push($(this).val());
		        });
		        
		        $('input[name="status"]:checked').each(function() {
		            statuses.push($(this).val());
		        });
		
		        const baseData = {
		            'capitalTypes': capitalTypes,
		            'statuses': statuses
		        };
		
		        const requestData = { ...baseData, ...additionalData };
		
		        $.ajax({
		            url: '/capital' + (additionalData.action ? '?action=' + additionalData.action : ''),
		            type: 'POST',
		            data: requestData,
		            traditional: true,
		            success: function(response) {		            	
		            	if (additionalData.action === 'edit') {
		                    alert('변경 완료');
		                }
		                updateTable(response);
		            },
		            error: function(xhr, status, error) {
		                console.error('Error:', error);
		                if (additionalData.action === 'edit') {
		                    alert('상태 업데이트 실패');
		                }
		            }
		        });
		    }
		
		    // 테이블 업데이트 함수
		    function updateTable(data) {
		        const tbody = $('#table-tbody');
		        tbody.empty();
		
		        if (!data || data.length === 0 || data[0].capitalManagementSeq === null) {
		            tbody.append('<tr><td colspan="8" class="text-center">자금 관리 내역이 없습니다.</td></tr>');
		            return;
		        }
		
		        data.forEach(function(item) {
		            let statusHtml = '';
		            switch(item.status) {
		                case '처리 안됨':
		                    statusHtml = '<label class="badge badge-warning">처리 안됨</label>';
		                    break;
		                case '처리 중':
		                    statusHtml = '<label class="badge badge-info">처리 중</label>';
		                    break;
		                case '처리 완료':
		                    statusHtml = '<label class="badge badge-success">처리 완료</label>';
		                    break;
		                case '거부':
		                    statusHtml = '<label class="badge badge-danger">거부</label>';
		                    break;
		                default:
		                    statusHtml = '<label class="badge badge-secondary">-</label>';
		            }
		
		            const formattedCost = new Intl.NumberFormat('ko-KR').format(item.cost);
		            const row = $("<tr>")
		                .attr('data-id', item.capitalManagementSeq)
		                .append($("<td>").text(item.referenceSeq || '-'))
		                .append($("<td>").text(item.manageAt ? formatDate(item.manageAt) : '-'))
		                .append($("<td>").text(item.summary || '-'))
		                .append($("<td>").text(item.capitalType || '-'))
		                .append($("<td>").text(item.cost ? formattedCost + '원' : '-'))
		                .append($("<td>").text(item.paymentType || '-'))
		                .append($("<td>").addClass('status-cell').html(statusHtml))
		                .append($("<td>").addClass('action-cell').html(`
		                    <button class="btn btn-sm btn-outline-primary edit-btn">수정</button>
		                    <div class="btn-group" style="display: none;">
		                        <button class="btn btn-sm btn-success save-btn">완료</button>
		                        <button class="btn btn-sm btn-danger cancel-btn">취소</button>
		                    </div>
		                `));
		            tbody.append(row);
		        });
		    }
		
		    // 이벤트 위임을 사용한 버튼 이벤트 처리
		    $(document).on('click', '.edit-btn', function() {
		        const row = $(this).closest('tr');
		        const statusCell = row.find('.status-cell');
		        const currentStatus = statusCell.find('.badge').text();
		        
		        const selectBox = $('<select>', { class: 'form-control status-select' });
		        const options = ['처리 안됨', '처리 중', '처리 완료', '거부'];
		        
		        options.forEach(status => {
		            const option = $('<option>', {
		                value: status,
		                text: status
		            });
		            if (status === currentStatus) {
		                option.attr('selected', 'selected');
		            }
		            selectBox.append(option);
		        });
		        
		        statusCell.data('original-content', statusCell.html());
		        statusCell.html(selectBox);
		        $(this).hide();
		        $(this).siblings('.btn-group').show();
		    });
		
		    $(document).on('click', '.cancel-btn', function() {
		        const row = $(this).closest('tr');
		        const statusCell = row.find('.status-cell');
		        statusCell.html(statusCell.data('original-content'));
		        $(this).closest('.btn-group').hide();
		        row.find('.edit-btn').show();
		    });
		
		    $(document).on('click', '.save-btn', function() {
		        const row = $(this).closest('tr');
		        const selectedStatus = row.find('select').val();
		        const capitalManagementSeq = row.data('id');
		        
		        sendAjaxRequest({
		            action: 'edit',
		            id: capitalManagementSeq,
		            status: selectedStatus
		        });
		    });
		});
		
		function formatDate(dateStr) {
		    const date = new Date(dateStr);
		    return date.toLocaleDateString('ko-KR');
		}

	   

	
	</script>
</body>

</html>
