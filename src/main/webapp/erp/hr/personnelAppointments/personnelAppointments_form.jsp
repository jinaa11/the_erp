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
  <title>인사발령 등록</title>
  <!-- base:css -->
  <link rel="stylesheet" href="/erp/vendors/typicons.font/font/typicons.css">
  <link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="/erp/images/favicon.png" />
  
  
<!-- 모달 -->
<style>

h2{
    text-align: center;
}

.modal_btn {
    display: block;
    margin: 40px auto;
    padding: 10px 20px;
    background-color: royalblue;
    border: none;
    border-radius: 5px;
    color: #fff;
    cursor: pointer;
    transition: box-shadow 0.2s;
}

.modal_btn:hover {
    box-shadow: 3px 4px 11px 0px #00000040;
}

/*모달 팝업 배경 영역 스타일링*/
.modal {
	display: none; /*평소에는 보이지 않도록*/
    position: absolute;
    top:0;
    left: 0;
    width: 100%;
    height: 100vh;
    background: rgba(0,0,0,0.5);
    overflow: hidden;
    z-index: 1000; /* 모달의 z-index 값 */
}


/* 팝업 내부 스타일 */
.modal .modal_popup {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 20px;
    background: #ffffff;
    border-radius: 20px;
    width: 400px; /* 모달 창의 너비 */
    height: auto; /* 내용에 따라 높이 자동 조절 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    overflow-y: auto; /* 내용이 많을 경우 스크롤 */
    z-index: 1010;
}

.modal .modal_popup .close_btn {
    display: block;
    padding: 10px 20px;
    background-color: rgb(116, 0, 0);
    border: none;
    border-radius: 5px;
    color: #fff;
    cursor: pointer;
    transition: box-shadow 0.2s;
}

.select_btn {
    display: block;
    padding: 10px 20px;
    background-color: rgb(116, 0, 0);
    border: none;
    border-radius: 5px;
    color: #fff;
    cursor: pointer;
    transition: box-shadow 0.2s;
}

.modal.on {
    display: block;
}

/* 테이블 스타일 */
#resultTable {
    width: 100%;
    border-collapse: collapse; /* 테두리 겹침 제거 */
    text-align: left;
    margin-top: 10px;
}

#resultTable th, #resultTable td {
    border: 1px solid #ddd; /* 테두리 색상 */
    padding: 8px; /* 셀 간격 */
}

#resultTable th {
    background-color: #f4f4f4; /* 헤더 배경 색 */
    font-weight: bold;
}
</style>
    
</head>

<body>
  <div class="container-scroller">
    
    <!-- partial:../../partials/_navbar.html -->
    
    
    <%@ include file="/erp/layout/top_layout.jsp" %>
    
    
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:../../partials/_settings-panel.html -->

          <%@ include file="/erp/layout/side_layout.jsp" %>
      <!-- partial -->
      <!-- partial:../../partials/_sidebar.html -->
      
      
      
      
      <!-- 테이블 내용 수정하시면 됩니다. -->
      
      <!-- partial -->
   <div class="main-panel">
   
        <div class="content-wrapper">
        
          <div class="row">
          
            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
              
                <div class="card-body">
                
                  <h4 class="card-title" style="font-size: 30px;">인사발령 등록</h4>
                  
                      		<!-- 모달창 열기 버튼 -->
						    <section>
						        <button type="button" class="modal_btn">사원검색</button>
						    </section>
                      		<!-- 모달창 열기 버튼 -->
                  
                  <div class="table-responsive">
                  
                  <!-- 폼 -->
                  <form id="myForm" name="myForm" action="/PAServlet" method="POST">
                  	<input type="hidden" id="pageGubun" name="pageGubun" value="I001">
                  
                  	
                    <table class="table">
                      <thead>
                        <tr>
                          <th scope="col">발령일자 선택</th>
                          <th scope="col">사원 번호</th>
                          <th scope="col">이름</th>
                          <th scope="col">기존 부서</th>
                          <th scope="col">기존 직급</th>
                          <th scope="col">발령 부서</th>
                          <th scope="col">발령 직급</th>
                          <th scope="col">발령구분</th>
                          <th scope="col">적요</th>
                        </tr>
                      </thead>
                      
                      
                      <tbody id="table-tbody">
                      	<tr>
                      		<td>
                      			<input type="date" id="pa_date" name="pa_date" class="form-control">
                      		</td>
                      		
                      		<td>
    							<input type="text" class="form-control" id="user_seq" name="user_seq" readonly>
                      		</td>
                      		
                      		<td>
    							<input type="text" class="form-control" id="user_name" name="user_name" readonly>
                      		</td>
                      		
                      		<td>
                      			<input type="text" class="form-control" name="before_dept" id="before_dept" readonly>
                      		</td>
                      		
                      		<td>
                      			<input type="text" class="form-control" name="before_position" id="before_position" readonly>
                      		</td>
                      		
                      		<td>
                      			<select class="form-control form-control-lg" name="assigned_dept" id="assigned_dept">
                      				<option value="1">인사부</option>
				                    <option value="2">개발부</option>
									<option value="21">회계</option>
									<option value="22">총무</option>
									<option value="31">공공영업1팀</option>
									<option value="32">공공영업2팀</option>
									<option value="33">기업영업1팀</option>
									<option value="34">기업영업2팀</option>
			                    </select>
                      		</td>
                      	
                      		
                      		<td>
                   			  	<select class="form-control form-control-lg" name="assigned_position" id="assigned_position">
			                      <option>사원</option>
			                      <option>대리</option>
			                      <option>과장</option>
			                      <option>차장</option>
			                      <option>부장</option>
			                      <option>팀장</option>
			                      <option>대표이사</option>
			                    </select>
                      		</td>
                      		
                      		<td>
                      			<select type="text" id="assignment_type" name="assignment_type" class="form-control">
                      			  <option value="1">부서이동</option>
			                      <option value="2">승진</option>
                      			</select>
                      		</td>
                      		
                      		 <td>
                      			<input type="text" id="notes" name="notes" class="form-control" placeholder="설명을 입력해주세요">
                      		 </td>
						<tr>	
                      </tbody>
                    </table>
                    </form>
                    
                    
                  </div>
                </div>
	            	<button type="submit" class="btn btn-primary" id="submitBtn"> 발령 등록 </button>
              </div>
            </div>
          </div>
        </div>

	      <!-- 모달 창은 일반적으로 tbody 밖에 위치 -->
		  <!--모달 팝업-->
			<div class="modal">
			    <div class="modal_popup">
			        <h4>사원 검색</h4>
			        <p>사원을 검색하여 선택해주세요</p>
			        <br>
			        <form id="searchForm"> 
			        <table>
			        	<tr>
				        	<td>
						        검색구분 : <select name="searchGubun" id="searchGubun">
											<option value="user_name">이름</option>
											<option value="user_seq">사원번호</option>
										</select>
					        </td>
					        <td>
					        	검색 : <input type="text" name="searchStr" id="searchStr">
					        </td>
					        
			        	</tr>
			        </table>
			        </form>
					

					<br>

				        <!-- 검색 결과 테이블 -->
						<table id="resultTable">
						    <thead>
						        <tr>
						            <th>사번</th>
						            <th>이름</th>
						            <th>부서</th>
						            <th>직급</th>
						            <th>선택</th>
						        </tr>
						    </thead>
						    <tbody>
						        <!-- 행들이 들어갈 곳 -->
						    </tbody>
						</table>
										
				        <br><br>

			        <button type="button" class="close_btn">닫기</button>
			        <br>
			    </div> <!-- 모달팝업 div -->
			</div> <!-- 모달 div -->
			<!-- 모달 팝업-->


                  

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
	const modal = document.querySelector('.modal');
	const modalOpen = document.querySelector('.modal_btn');
	const modalClose = document.querySelector('.close_btn');
	
	//열기 버튼을 눌렀을 때 모달팝업이 열림
	modalOpen.addEventListener('click',function(){
	  	//'on' class 추가
	    modal.classList.add('on');
	});
	
	//닫기 버튼을 눌렀을 때 모달팝업이 닫힘
	modalClose.addEventListener('click',function(){
	    //'on' class 제거
	    modal.classList.remove('on');
	});
</script>



<script>
$(document).ready(function() {

	
    // jQuery 동적 이벤트 바인딩
    $(document).on('keyup', "#searchStr", function() {
        var str = $("#searchStr").val();
        if (str != "") {
            var formData = $("#searchForm").serialize(); // k=v&k=v
            console.log(formData);

            $.ajax({
                url : "/restSearch",
                method : 'POST',
                data : formData,
                //contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
                //dataType: "json", 	
                success : function(obj) {
                	console.log(obj);  // vo 구조 확인
                	
                	
                    // 결과 테이블의 tbody 부분 초기화
                    $("#resultTable tbody").empty();

                    // 검색 결과 추가
					var htmlStr = "";
					
					$(obj).map(function(i, vo) {
						htmlStr += "<tr>";
	                    htmlStr += "<td>" + vo.user_seq + "</td>";
	                    htmlStr += "<td>" + vo.user_name + "</td>";
	                    htmlStr += "<td>" + vo.department_name + "</td>";
	                    htmlStr += "<td>" + vo.position + "</td>";
	                    htmlStr += "<td><button class='select_btn' data-user_seq='" + vo.user_seq + 
	                               "' data-user_name='" + vo.user_name + 
	                               "' data-department_name='" + vo.department_name + 
	                               "' data-position='" + vo.position + "'>선택</button></td>";
	                    htmlStr += "</tr>";
					});

					$("#resultTable tbody").html(htmlStr);

                },
                error : function(err) {
                    console.log("에러:" + err);
                }
            });
        }
    });
    
    
 	// 모달창 내 사원검색 후 사원 "선택" 버튼 클릭 시 동작
    $(document).on('click', '.select_btn', function() {
        const user_seq 			= $(this).data('user_seq');
        const user_name 		= $(this).data('user_name');
        const department_name 	= $(this).data('department_name');
        const position 			= $(this).data('position');

        // 이전 페이지의 입력 필드에 값 채우기
        $('#user_seq').val(user_seq);
        $('#user_name').val(user_name);
        $('#before_dept').val(department_name);
        $('#before_position').val(position);

        // 모달 창 닫기
        $('.modal').removeClass('on');
    });


 	// 발령 등록 (I001)
    $("#submitBtn").click(function() {

        if ($("#pa_date").val() == "") {
            alert("발령일자를 선택해주세요");

        } else if ($("#user_seq").val() == "") {
            alert("사원을 선택해주세요");

        } else if ($("#notes").val() == "") {
            alert("설명을 입력해주세요");
            $("#notes").focus();

        }
        
        var jsonObj = {
        		"pa_date" 			: $("#pa_date").val(),
        		"user_seq" 			: $("#user_seq").val(),
        		"before_dept" 		: $("#before_dept").val(),
        		"before_position" 	: $("#before_position").val(),
        		"assigned_dept" 	: $("#assigned_dept").val(),
        		"assigned_position" : $("#assigned_position").val(),
        		"assignment_type" 	: $("#assignment_type").val(),
        		"notes" 			: $("#notes").val()
        		};

        
        var jsonStr = JSON.stringify(jsonObj);
        
        
        $.ajax({
            url : "/PAServlet?pageGubun=I001",
            method : 'POST',
            contentType : "application/json; charset=UTF-8" ,	//내가보내는거 json임을 명시
            
            data : jsonStr ,  
            
            success: function(response) {
                console.log("서버응답값 : " + response.status + "건" + response.message);
                
                if (response.status === 1) {
                    alert("발령 등록이 완료되었습니다.");
                    location.replace("/PAServlet");	//GET방식
                
					} else if (response.status === 0) {
                    alert("정상적으로 등록되지 않았습니다.");
                    
                } else {
                    alert("알 수 없는 오류가 발생했습니다.");
                }
            },
            error: function(err) {
                console.log("에러:" + err);
            }
        });
    });

	
});
</script>







</body>

</html>
