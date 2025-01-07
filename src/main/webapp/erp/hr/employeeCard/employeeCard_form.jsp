<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">


<!-- Mirrored from demo.bootstrapdash.com/celestial-free/template/pages/samples/register.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 30 Dec 2024 08:36:56 GMT -->
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>인사카드 등록</title>
  <!-- base:css -->
  <link rel="stylesheet" href="../../vendors/typicons.font/font/typicons.css">
  <link rel="stylesheet" href="../../vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- plugin css for this page -->
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="../../css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="../../images/favicon.png" />
</head>

<body>

<%
String currentPage = request.getRequestURI();
/* System.out.println("currentPage = " + currentPage); */

String loginPage = "/erp/pages/auth/Login.jsp";

if (!currentPage.equals(loginPage)) {
	Cookie[] cookies = request.getCookies();
	boolean hasAuthCookie = false;

	if (cookies != null) {
		for (Cookie cookie : cookies) {
	if (cookie.getName().equals("auth")) {
		hasAuthCookie = true;
		break;
	}
		}
	}

	if (!hasAuthCookie && !response.isCommitted()) {
		response.sendRedirect(loginPage);
		return;
	}
}
%>


  <div class="container-scroller">
  <div class="container-fluid page-body-wrapper full-page-wrapper">
    <div class="content-wrapper d-flex align-items-center auth px-0">
      <div class="row w-100 mx-0">
        <div class="col-lg-8 mx-auto">
          <div class="auth-form-light text-left py-5 px-4 px-sm-5">
            <div class="brand-logo">
              <img src="../../images/logo.svg" alt="logo">
            </div>
            
            <h4 style="font-size: 36px;">회원가입</h4><br>
            <h6 class="font-weight-light" style="font-size: 20px;">
              Welcome, new member! <br><br>
              Please provide your details to complete registration.
            </h6>

            <form action="/employeeCardServlet" method="POST">
              <!-- 개인정보 -->
              <br><br>
              <h5 class="mb-4" style="font-size: 20px;">개인정보</h5>
              
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" name="user_name" id="user_name" placeholder="이름">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <select class="form-control form-control-lg" name="gender" id="gender">
                      <option value="0">남성</option>
                      <option value="1">여성</option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" name="user_id" id="user_id" placeholder="아이디">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" name="user_password" id="user_password" placeholder="비밀번호">
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="date" class="form-control form-control-lg" name="birth" id="birth" placeholder="생년월일">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="number" class="form-control form-control-lg" name="phone_number" id="phone_number" placeholder="휴대전화번호">
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" name="address" id="address" placeholder="주소">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="email" class="form-control form-control-lg" name="email" id="email" placeholder="이메일">
                  </div>
                </div>
              </div>

              <!-- 회사정보 -->
              <br>
              <h5 class="mb-4" style="font-size: 20px;">회사정보</h5>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    입사일 <input type="date" class="form-control form-control-lg" name="hire_date" id="hire_date">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    부서 선택 <input type="number" class="form-control form-control-lg" name="department_id" id="department_id" placeholder="부서 번호(ID)">
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    직급 선택
                    <select class="form-control form-control-lg" name="position" id="position">
                      <option>사원</option>
                      <option>대리</option>
                      <option>과장</option>
                      <option>차장</option>
                      <option>부장</option>
                      <option>팀장</option>
                      <option>대표이사</option>
                    </select>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    매니저 선택 <input type="number" class="form-control form-control-lg" name="manager_id" id="manager_id" placeholder="매니저 번호(ID)">
                  </div>
                </div>
              </div>

              <!-- Hidden Fields -->
              <input type="hidden" name="register_date" id="register_date">
              <input type="hidden" name="modify_date" id="modify_date">
              <input type="hidden" name="user_status" id="user_status" value="0">
              <input type="hidden" name="extension_number" id="extension_number">
              <input type="hidden" name="termination_date" id="termination_date">
              <input type="hidden" name="pageGubun" id="pageGubun" value="I001">

              <div class="mt-3">
                <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">회원가입</button>
              </div>
            </form>

            <div class="text-center mt-4 font-weight-light">
              이미 회원가입을 하셨나요? <a href="login.html" class="text-primary">로그인하러가기</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
  

  <!-- base:js -->
  <script src="../../vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- inject:js -->
  <script src="../../js/off-canvas.js"></script>
  <script src="../../js/hoverable-collapse.js"></script>
  <script src="../../js/template.js"></script>
  <script src="../../js/settings.js"></script>
  <script src="../../js/todolist.js"></script>
  <!-- endinject -->


  

<script src="https://code.jquery.com/jquery-3.7.1.js"></script>

<script type="text/javascript">
    window.onload = function() {
        // 현재 날짜와 시간을 가져오기
		var currentDate = new Date();
		
        // 숫자가 10미만일 때는 앞에 0덧붙이기 (예를 들어 5월 => 05월)
		function padWithZero(num) {
		    return num < 10 ? '0' + num : num;
		}
		
        // 날짜형식 지정
		var formattedDateTime = 
		    currentDate.getFullYear() + '-' + 
		    padWithZero(currentDate.getMonth() + 1) + '-' + 
		    padWithZero(currentDate.getDate()) + ' ' + 
		    padWithZero(currentDate.getHours()) + ':' + 
		    padWithZero(currentDate.getMinutes()) + ':' + 
		    padWithZero(currentDate.getSeconds());

        // 정한 형식대로 날짜를 셋팅
        document.getElementById("register_date").value = formattedDateTime;
        document.getElementById("modify_date").value = formattedDateTime;

        // user_status값 숫자 0(활성)으로 설정
        document.getElementById("user_status").value = 0;
    };
    
    
    // AJAX 요청으로 데이터를 전송
    function submit() {
    	console.log("submit이 호출됨");
    	
        // 날짜를 받아오고 " 00:00:00"을 추가하여 처리
        var hireDate  = document.getElementById("hire_date").value;  // 입사일 입력 필드에서 날짜 값 가져오기
        var birthDate = document.getElementById("birth").value;  	 // 생일 입력 필드에서 날짜 값 가져오기

        // 날짜 값이 비어 있지 않으면 " 00:00:00" 추가
        var formattedHireDate  = hireDate ?  hireDate  + " 00:00:00" : "";
        var formattedBirthDate = birthDate ? birthDate + " 00:00:00" : "";
	    
		
        // AJAX 요청을 사용하여 JSON 데이터 전송
       
</script>


  
  
  
</body>



<!-- Mirrored from demo.bootstrapdash.com/celestial-free/template/pages/samples/register.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 30 Dec 2024 08:36:56 GMT -->
</html>
