<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<!-- base:css -->
<link rel="stylesheet"
	href="/erp/vendors/typicons.font/font/typicons.css">
<link rel="stylesheet" href="/erp/vendors/css/vendor.bundle.base.css">
<!-- endinject -->
<!-- inject:css -->
<link rel="stylesheet" href="/erp/css/vertical-layout-light/style.css">

<div id="left_nav">
	<nav class="sidebar sidebar-offcanvas" id="sidebar">

		<ul class="nav">
			<li class="nav-item">
				<div class="d-flex sidebar-profile">
					<div class="sidebar-profile-image">
						<img src="/erp/images/faces/face29.png" alt="image"> <span
							class="sidebar-status-indicator"></span>
					</div>
					<div class="sidebar-profile-name">
						<p class="sidebar-name">Kenneth Osborne</p>
						<p class="sidebar-designation">Welcome</p>
					</div>
				</div>
				<p class="sidebar-menu-title">Menu</p>
			</li>
			<li class="nav-item"><a class="nav-link" href="/home"> <i
					class="typcn typcn-device-desktop menu-icon"></i> <span
					class="menu-title">Home <span
						class="badge badge-primary ml-3">New</span></span>
			</a></li>
			<li class="nav-item"><a class="nav-link" data-toggle="collapse"
				href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
					<i class="typcn typcn-briefcase menu-icon"></i> <span
					class="menu-title">회계</span> <!-- <i class="typcn typcn-chevron-right menu-arrow"></i> -->
			</a>
				<div class="collapse" id="ui-basic">
					<ul class="nav flex-column sub-menu">
						<li class="nav-item"><a class="nav-link"
							href="/finance/accounts">계정관리</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/finance/general_ledger">전표관리</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/finance/sum_FinanceList">계정별원장</a></li>
						<li class="nav-item"><a class="nav-link" href="/finance/sofp">재무상태표</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/finance/income_Statement">손익계산서</a></li>
					</ul>
				</div></li>
			<li class="nav-item"><a class="nav-link" data-toggle="collapse"
				href="#form-elements" aria-expanded="false"
				aria-controls="form-elements"> <i
					class="typcn typcn-film menu-icon"></i> <span class="menu-title">인사</span>
					<!-- <i class="menu-arrow"></i> -->
			</a>
				<div class="collapse" id="form-elements">
					<ul class="nav flex-column sub-menu">
						<li class="nav-item"><a class="nav-link"
							href="/v1/attendance/selectAll">근태관리</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/employeeCardServlet">인사카드</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/PAServlet">인사발령</a></li>
					</ul>
				</div></li>
			<li class="nav-item"><a class="nav-link" data-toggle="collapse"
				href="#charts" aria-expanded="false" aria-controls="charts"> <i
					class="typcn typcn-chart-pie-outline menu-icon"></i> <span
					class="menu-title">총무</span> <!-- <i class="menu-arrow"></i> -->
			</a>
				<div class="collapse" id="charts">
					<ul class="nav flex-column sub-menu">
						<li class="nav-item"><a class="nav-link"
							href="/facility/list">시설관리</a></li>
					</ul>
				</div></li>


			<li class="nav-item"><a class="nav-link" data-toggle="collapse"
				href="#charts2" aria-expanded="false" aria-controls="charts"> <i
					class="typcn typcn-chart-pie-outline menu-icon"></i> <span
					class="menu-title">권한 관리</span> <!-- <i class="menu-arrow"></i> -->
			</a>
				<div class="collapse" id="charts2">
					<ul class="nav flex-column sub-menu">
						<li class="nav-item"><a class="nav-link"
							href="/erp/pages/auth/role_generate.jsp">기능별 권한 관리</a></li>
					</ul>
				</div></li>
		</ul>
	</nav>
</div>

