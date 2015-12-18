<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>超时</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">
	        	<h3 class="panel-title">请重新登录</h3>
	      	</div>
	      	<div class="panel-body">
		        session过期,请重新登录!<a href="<c:url value="/login.jsp"/>">登录</a>
			</div>
			
		</div>
	</div>
</body>
</html>