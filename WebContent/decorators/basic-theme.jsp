<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>

<html class="no-js">
<head>
        <meta charset="utf-8">
        <meta name="description" content="">
        <meta name="HandheldFriendly" content="True">
        <meta name="MobileOptimized" content="320">
        <meta name="viewport" content="width=device-width, initial-scale=1, minimal-ui">
        <meta http-equiv="cleartype" content="on">
        
	<meta name="viewport" content="width=device-width,  initial-scale=1.0, user-scalable=0, minimum-scale=1.0,  maximum-scale=1.0,minimal-ui" />
	<jsp:include page="/common/jquery-css.jsp"/>
	<jsp:include page="/common/bootstrap/bootstrap-css.jsp"/>
	<jsp:include page="/common/jquery-js.jsp"/>
	<jsp:include page="/common/bootstrap/bootstrap-js.jsp"/>
	<jsp:include page="/common/dialog.jsp"/>
    <title><decorator:title /></title>
    <decorator:head />
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
	<div id="main_content">
    <decorator:body />
    <jsp:include page="/common/bootstrap/footer.jsp"/>
    </div>
</body>
</html>