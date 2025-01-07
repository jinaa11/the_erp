<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title> </title>
    <link rel="stylesheet" href="/erp/vendors/typicons.font/font/typicons.css">
    <link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
    <link rel="shortcut icon" href="/erp/images/favicon.png" />
<style>
    body {
        font-family: Arial, sans-serif;
        text-align: center;
    }
    table {
        width: 70%;
        border-collapse: collapse;
        margin: 20px auto;
    }
    table, th, td {
        border: 1px solid #ddd;
    }
    th, td {
        padding: 10px;
        text-align: left;
    }
    th {
        background-color: #f4f4f4;
        text-align: center;
    }
    .numeric {
        text-align: right;
    }
    .section-title {
        font-weight: bold;
        text-align: center;
    }
    .account-info {
        font-size: 17px;
        margin-bottom: 20px;
    }
    .centered-header h2 {
        font-size: 26px;
        font-weight: bold;
        text-align: center;
    }
    .sub-header {
        font-size: 18px!important;
         text-align: center;
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
    
#printbtn{    
	position: absolute; /* 화면에 고정 */
	border-radius: 3px;
	top: 50px;
    right: 130px; /* 화면 오른쪽에서 50px 떨어짐 */
    z-index: 1000; /* 다른 요소 위에 표시 */
    background-color: #f0f7ff;
    border: none;
    width: 4%;
    height: 30px;
}

#printbtn:hover{
	background-color: #ccdbed;
}


</style>
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
				<div class="main-content" style="width: calc(100% - 250px); margin-left: 250px;">
   <div class="parent-div" style="width:100%;">
        <div class="main-panel" style="width: 100%;">
            <div class="content-wrapper">
                <div class="row">
                    <div class="col-lg-12 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                    <button id = "printbtn" onclick="printDiv('printableArea')">Print</button>
									<div id="printableArea">
                                <div class="centered-header">
                                
                                    <h2>손익계산서</h2>
                                </div>
                                <div class="sub-header">
                                    <p>제 1기 2024/01/01 부터 2024/12/31 까지</p>
                                    <p>회사명: KOSTA<c:out value="${companyName}" /></p>
                                </div>

                                <table style="width: 90%;">
                                    <thead>
                                        <tr style="text-align: center;">
                                            <th>재무제표 표시명</th>
                                            <th>계정명</th>
                                            <th>차변</th>
                                            <th>대변</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="previousParentType" value="" />
                                        <c:forEach var="vo" items="${KEY_INCOME_FLIST}">
                                            <tr>
                                                <td class="section-title">
                                                    <c:choose>
                                                        <c:when test="${vo.parent_type != previousParentType}">
                                                            ${vo.parent_type}
                                                            <c:set var="previousParentType" value="${vo.parent_type}" />
                                                        </c:when>
                                                        <c:otherwise> </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${vo.account_name}</td>
                                                <td class="numeric">
                                                    <c:if test="${vo.debit != 0}">
                                                        <fmt:formatNumber value="${vo.debit}" type="number" groupingUsed="true" />
                                                    </c:if>
                                                </td>
                                                <td class="numeric">
                                                    <c:if test="${vo.credit != 0}">
                                                        <fmt:formatNumber value="${vo.credit}" type="number" groupingUsed="true" />
                                                    </c:if>
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
			<%@ include file="/erp/layout/footer_layout.jsp"%>
        </div>
        </div>
    </div>
        </div>
    </div>
</body>
</html>
    
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="/erp/vendors/js/vendor.bundle.base.js"></script>
    <script src="/erp/js/off-canvas.js"></script>
    <script src="/erp/js/hoverable-collapse.js"></script>
    <script src="/erp/js/template.js"></script>
    <script src="/erp/js/settings.js"></script>
    <script src="/erp/js/todolist.js"></script>
    <script>
    function printDiv(divId) {
        var printContents = document.getElementById(divId).innerHTML;
        var originalContents = document.body.innerHTML;

        // 전체 화면 내용을 변경하여 해당 부분만 출력
        document.body.innerHTML = printContents;

        // 브라우저 프린트 실행
        window.print();

        // 원래 내용 복원
        document.body.innerHTML = originalContents;

        // 페이지 새로고침 방지
        window.location.reload();
    }
</script>
</body>
</html>