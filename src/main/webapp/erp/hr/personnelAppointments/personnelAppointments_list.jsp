<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" 	uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="sql" 	uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>인사발령 조회</title>
  <!-- base:css -->
  <link rel="stylesheet" href="/erp/vendors/typicons.font/font/typicons.css">
  <link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="/erp/images/favicon.png" />
</head>

<body>
  <div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <%@ include file="/erp/layout/top_layout.jsp" %>
    
    
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:../../partials/_settings-panel.html -->
      <!-- partial -->
      <!-- partial:../../partials/_sidebar.html -->
      <%@ include file="/erp/layout/side_layout.jsp" %>
      
   
      <!-- 테이블 내용 수정하시면 됩니다. -->
      
      <!-- partial -->
   <div class="main-panel">
        <div class="content-wrapper">
          <div class="row">
          
            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title" style="font-size: 30px;">인사발령 조회</h4>
                  <div class="table-responsive">
                    <table class="table">
                      <thead>
                        <tr>
                          <th scope="col"><input type="checkbox" name="allCheck" id="allCheck"/></th>
                          <th scope="col">내역 번호</th>
                          <th scope="col">발령일자</th>
                          <th scope="col">사원번호</th>
                          <th scope="col">이름</th>
                          <th scope="col">생년월일</th>
                          <th scope="col">이전 부서</th>
                          <th scope="col">이전 직급</th>
                          <th scope="col">발령 부서</th>
                          <th scope="col">발령 직급</th>
                          <th scope="col">발령구분</th>
                          <th scope="col">적요</th>
                        </tr>
                      </thead>
                      <tbody id="table-tbody">
                      
                      	<c:forEach var="pavo" items="${MY_KEY_PALIST}">
                        <tr>
                          <td><input type="checkbox" name="RowCheck" value="${pavo.pah_seq}"/></td>
                          <td>${pavo.pah_seq}</td>
                          <td>${pavo.pa_date}</td>
                          <td>${pavo.user_seq}</td>
                          <td>${pavo.user_name}</td>
                          <td>${pavo.birth}</td>
                          <td>${pavo.before_dept}</td>
                          <td>${pavo.before_position}</td>
                          
                          <td>
				            <c:choose>
				                <c:when test="${pavo.assigned_dept == 1}">인사부</c:when>
				                <c:when test="${pavo.assigned_dept == 2}">개발부</c:when>
				                <c:when test="${pavo.assigned_dept == 21}">회계</c:when>
				                <c:when test="${pavo.assigned_dept == 22}">총무</c:when>
				                <c:when test="${pavo.assigned_dept == 31}">공공영업1팀</c:when>
				                <c:when test="${pavo.assigned_dept == 32}">공공영업2팀</c:when>
				                <c:when test="${pavo.assigned_dept == 33}">기업영업1팀</c:when>
				                <c:when test="${pavo.assigned_dept == 34}">기업영업2팀</c:when>
				                <c:otherwise>알 수 없음</c:otherwise>
				            </c:choose>
				          </td>
                          
                          <td>${pavo.assigned_position}</td>
                          <td>${pavo.assignment_type}</td>
                          <td>${pavo.notes}</td>
                          
                        </tr>
                       
                        </c:forEach>
                      </tbody>
                    </table>

					<br>
					<span style="font-size: 20px;">${MY_KEY_PAGING_HTML}</span>
					<br><br><br><br>
                    
                  </div>
                  <button type="button" class="btn btn-primary" id="insertFormBtn"> 신규 등록 </button>
                  <input  type="button" class="btn btn-primary" value="선택 삭제" onclick="deleteValue()">

                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- content-wrapper ends -->
        <!-- partial:../../partials/_footer.html -->
        
        <%@ include file="/erp/layout/footer_layout.jsp" %>
        
        
        <!-- partial -->
      </div>
      <!-- 테이블 내용 수정 end -->
      
      
      
      
      
      <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->
  <!-- base:js -->
  <script src="/erp/vendors/js/vendor.bundle.base.js"></script>
  
  <script src="/erp/js/off-canvas.js"></script>
  <script src="/erp/js/hoverable-collapse.js"></script>
  <script src="/erp/js/template.js"></script>
  <script src="/erp/js/settings.js"></script>
  <script src="/erp/js/todolist.js"></script>



<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
    $( document ).ready(function() {
        $("#insertFormBtn").click( function() {
        	location.href = "/erp/hr/personnelAppointments/personnelAppointments_form.jsp"
    	});
});        
</script>


<script type="text/javascript">
	$(function() {
        var chkObj = $("input[name='RowCheck']");
        var rowCnt = chkObj.length;
        
        // 전체 선택 버튼
        $("input[name='allCheck']").on("click", function() {
            var isChecked = $(this).prop("checked");
            chkObj.prop("checked", isChecked);  // 전체 체크박스를 상태에 맞게 변경
        });

        // 개별 선택 버튼
        $("input[name='RowCheck']").on("click", function() {
            var allChecked = chkObj.length === $("input[name='RowCheck']:checked").length;
            $("input[name='allCheck']").prop("checked", allChecked);  // 전체 선택 체크박스 상태 변경
        });
	});
</script>


<script type="text/javascript">
    // 삭제 메서드
    function deleteValue() {
        var url = "/PAServlet"; // 서버 URL
        var valueArr = [];
        $("input[name='RowCheck']:checked").each(function() {
            valueArr.push($(this).val());
        });

        if (valueArr.length == 0) {
            alert("선택된 항목이 없습니다.");
            return;
        }

        var chk = confirm("선택한 항목을 삭제하시겠습니까?");
        if (chk) {
            $.ajax({
                url : url ,
                type : "POST" ,
                traditional : true , // 배열 전송 방식 설정
                data : { 
                    valueArr: valueArr ,
                    pageGubun: "D001" // 페이지 구분자
                },
                //dataType : "json", // 서버에서 JSON을 반환할 것으로 예상
                success: function(response) {
                	// 서버 응답 데이터 확인
                    console.log("서버 응답 데이터: ", response);
                	
                    // 응답의 status 값 확인
 					if (response.status === 1) {
                        alert("항목이 삭제되었습니다.");
                        location.replace("/PAServlet");	//GET방식
                    
 					} else if (response.status === 0) {
                        alert("정상적으로 삭제되지 않았습니다.");
                        
                    } else {
                        alert("알 수 없는 오류가 발생했습니다.");
                    }
                },
                error: function(err) {
                    console.error("에러:", err);
                }
            });
        }
    }
</script>






</body>

</html>
