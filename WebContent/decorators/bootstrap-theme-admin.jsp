<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width,  initial-scale=1.0, user-scalable=0, minimum-scale=1.0,  maximum-scale=1.0,minimal-ui" />
	<jsp:include page="/common/css.jsp"/>
	<jsp:include page="/common/bootstrap/css.jsp"/>
	<jsp:include page="/common/bootstrap/jquery-ui-css.jsp"/>
	<jsp:include page="/common/js.jsp"/>
	<jsp:include page="/common/bootstrap/js.jsp"/>   
    <title><decorator:title /></title>
    <decorator:head />
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <decorator:body />
   
    <jsp:include page="/common/bootstrap/footer.jsp"/>
</body>
</html>