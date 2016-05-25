<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="<c:url value="/css/jquery/jquery-picklist.css"/>" rel="stylesheet" type="text/css" />
	<script src="<c:url value="/js/jquery-2.1.1/jquery-1.8.1.js"/>"></script>
	<script src="<c:url value="/js/jquery-2.1.1/jquery-ui-1.11.4.js"/>"></script>
	<jsp:include page="/common/bootstrap/bootstrap-js.jsp"/>
	<jsp:include page="/common/dialog.jsp"/>
	<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1/jquery-picklist.js"/>"></script>
	<title>选择资源</title>
</head>
<body>
	<div class="container">
		<form class="form-horizontal" role="form" method="POST" action="<c:url value="/admin/system/resource/popup/updatePickRes.do"/>">
			<input type="hidden" name="roleId" value="${roleId }">
			<div class="form-group">
				<select id="resourceId" name="resourceId" multiple class="form-control">
					<c:forEach items="${resources }" var="res">
					<option value="${res.resourceId }"<c:if test="${selectetRes[res.resourceId] != null}">selected</c:if>>${res.resourceName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<div class="span7 text-center">
					<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-save"></span>保存</button>
					<button id="cancelBtn" type="button" class="btn btn-danger" onClick="javascript:art.dialog.close()"><span class="glyphicon glyphicon-remove"></span>关闭</button>
				</div>
			</div>
		</form>
	</div>
	<script>
	$(document).ready(function(){
		$("#resourceId").pickList({
				sourceListLabel: "可选资源",
				targetListLabel: "已选资源"
		});
	});
	
	function process() {
		form.action = "<c:url value="/admin/system/resource/popup/updatePickRes.do"/>";
	}
	</script>
	
	<%@include file="/common/formResponse.jsp"%>
</body>
</html>