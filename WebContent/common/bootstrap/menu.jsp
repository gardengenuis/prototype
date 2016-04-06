<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<div id="menu">
	<!-- Static navbar -->
	<nav class="navbar navbar-default navbar-static-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img src="<c:url value="/images/logo_admin.png"/>"/></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<c:forEach var="menu" items="${menus }">
						<c:choose>
							<c:when test="${fn:length(menu.subMenus) > 0}">
								<li class="dropdown">
									<a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" href="#">${menu.name }<b class="caret"></b></a>
									<ul class="dropdown-menu">
										<c:forEach var="subMenu" items="${menu.subMenus}">
											<c:set var="subMenu" value="${subMenu}" scope="request"/>
											<jsp:include page="/common/bootstrap/menuItems.jsp"/>
										</c:forEach>
									</ul>
							</c:when>
							<c:otherwise>
								<li>
									<a href="#" onclick="openURL('<c:url value="${menu.url }"/>', this)">${menu.name }</a>
							</c:otherwise>
						</c:choose>
								</li>
					</c:forEach>
				</ul>	
				
				<ul class="nav navbar-nav navbar-right ">
					<li><a href="#" onclick="openURL('<c:url value="/admin/desktop.do"/>', this)"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>&nbsp;桌面</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" data-hover="dropdown"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;<c:out value="${username}"/> <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" onclick="openURL('<c:url value="/admin/changePass.do"/>', this)"><span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>&nbsp;修改密码</a></li>
							<li><a href="<c:url value="/logout_authentication"/>"><span class="glyphicon glyphicon-off" aria-hidden="true"></span>&nbsp;退出</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

</div>