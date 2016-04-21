<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width,  initial-scale=1.0, user-scalable=0, minimum-scale=1.0,  maximum-scale=1.0,minimal-ui" />
	<jsp:include page="/common/jquery-css.jsp"/>
	<jsp:include page="/common/bootstrap/bootstrap-css.jsp"/>
	<jsp:include page="/common/jquery-js.jsp"/>
	<jsp:include page="/common/bootstrap/bootstrap-js.jsp"/>
    <title><decorator:title /></title>
    <decorator:head />
</head>
<%
	String code = request.getParameter("code");
%>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
	<div id="main_content">
	<%
		if ( "500".equals(code)) {
	%>
    <div class="container">
		<div class="panel panel-danger">
			<div class="panel-heading">
	        	<h3 class="panel-title">500服务器错误</h3>
	      	</div>
	      	<div class="panel-body">
	      		请联系管理员...
				<% if (exception != null) { %>
				    <pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %></pre>
				 <% } else if ((Exception)request.getAttribute("javax.servlet.error.exception") != null) { %>
				    <pre><% ((Exception)request.getAttribute("javax.servlet.error.exception"))
				                           .printStackTrace(new java.io.PrintWriter(out)); %></pre>
				 <% } %>
			</div>
		</div>
	</div>
	<%
		} else if ( "403".equals(code)) {
	%>
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">
	        	<h3 class="panel-title">403访问权限不足</h3>
	      	</div>
	      	<div class="panel-body">
				对不起，你没有权限访问此页面...
				<p style="text-align: center; margin-top: 20px">
				    <a href="javascript:history.back(1)"
				        title="出现(403)错误">
				    <img style="border: 0" 
				        src="<c:url value="/images/403.jpg"/>" 
				        alt="出现(403)错误,点击图像后退!" /></a>
				</p>
			</div>
		</div>
	</div>
	<%
		} else if ( "404".equals(code)) {
	%>
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">
	        	<h3 class="panel-title">404页面不存在</h3>
	      	</div>
	      	<div class="panel-body">
				对不起，您访问的页面不存在...
				<p style="text-align: center; margin-top: 20px">
				    <a href="javascript:history.back(1)"
				        title="出现(404)错误">
				    <img style="border: 0" 
				        src="<c:url value="/images/404.jpg"/>" 
				        alt="出现(404)错误,点击图像后退!" /></a>
				</p>
			</div>
		</div>
	</div>
	<%
		}
	%>
	<hr />
	<jsp:include page="/common/bootstrap/footer.jsp"/>
	</div>
</body>
</html>