
<%
String currentPage = request.getRequestURI();
/* System.out.println("currentPage = " + currentPage);*/

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


<nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
	<div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">


		<a href="/home" target="_blank">
			<img
				src="data:image/png;base64,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"
				width=100px height=50px>
		</a>
	</div>
	<div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
		<ul class="navbar-nav mr-lg-2">
		
			<li class="nav-item  d-none d-lg-flex"><a class="nav-link active" href="#"> Statistic </a></li>
			
		</ul>
		<ul class="navbar-nav navbar-nav-right">
	
	
			



			<li class="nav-item nav-profile dropdown"><a class="nav-link dropdown-toggle pl-0 pr-0" href="#" data-toggle="dropdown" id="profileDropdown">
					<i class="typcn typcn-user-outline mr-0"></i>
					<span class="nav-profile-name">Menu</span>
				</a>
				<div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
					<a class="dropdown-item">
						<i class="typcn typcn-cog text-primary"></i>
						Settings
					</a>
					<a class="dropdown-item" href="<%=request.getContextPath()%>/erp/layout/logout.jsp">
						<i class="typcn typcn-power text-primary"></i>
						Logout
					</a>
				</div></li>



		</ul>
		<button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
			<span class="typcn typcn-th-menu"></span>
		</button>
	</div>
</nav>
