<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>登录</title>
	<script>
		$(function ()
		{ 
			$(".progress").hide();
			
			if ( window.top != window.self)
				window.top.location = "<c:url value="/login.jsp"/>";
		});
		
		function waiting() {
			$(".progress").show();
			$("input").attr("readonly", true);
			$("#thg2hide").hide();
		}
	</script>
</head>
<body>
	
	<div class="container">
		<form class="form-signin" role="form"
			action="<c:url value="/login_authentication"/>" method="post" onsubmit="waiting();">
			<h2 class="form-signin-heading">请登录</h2>
			
			<input type="text" name="username" class="form-control"
				placeholder="登录名" required autofocus /> 
			<br /> 
			<input
				type="password" name="password" class="form-control"
				placeholder="密码" required />
			<div id="thg2hide">
				<input type="checkbox" name="remember-me" />&nbsp;记住我
			<c:if test="${message != null }">
				<div class="alert alert-danger" role="alert"><strong>登录失败!原因:</strong>[${message }]</div>
			</c:if>
			
			
	
			<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
			</div>
			
			<div class="progress" style="display:none">
				<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
					登录中,请稍后...
				</div>
			</div>
		</form>
	</div>
</body>
</html>