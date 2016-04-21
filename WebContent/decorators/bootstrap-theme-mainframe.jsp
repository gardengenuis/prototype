<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width,  initial-scale=1.0, user-scalable=0, minimum-scale=1.0,  maximum-scale=1.0,minimal-ui" />
	<jsp:include page="/common/jquery-css.jsp"/>
	<jsp:include page="/common/bootstrap/bootstrap-css.jsp"/>
	<jsp:include page="/common/jquery-js.jsp"/>
	<jsp:include page="/common/bootstrap/bootstrap-js.jsp"/>
	<jsp:include page="/common/bootstrap/dropdown.jsp"/>
	<jsp:include page="/common/dialog.jsp"/>
    <title><decorator:title /></title>
    <decorator:head />
    <script type="text/javascript">
	function reinitIframe() {
	    try {
	    	var realHeigth=$(window.frames["frameWin"].document).find("#main_content").height();
	    	if(realHeigth<500){
	    		realHeigth=500;
	    	}
	    	$("#frameWin").height(realHeigth);
	    } catch (ex) { }
	}
	
	function init(obj){
		reinitIframe();
	}
	</script>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <jsp:include page="/common/bootstrap/header.jsp"/>
	
	<iframe id="frameWin" name="frameWin" width="100%" height="500px" frameborder="0" src="<c:url value="desktop.do"/>" border=0 marginWidth=0 
       			frameSpacing=0 marginHeight=0 width=100%  vspace="0" scrolling="no" onload="init(this);"/>


</body>
</html>