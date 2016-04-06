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
	<title>选择角色</title>
</head>
<body>
	<div class="container">
		<form class="form-horizontal" role="form" method="POST" action="<c:url value="/admin/system/role/popup/updatePickRole.do"/>">
			<input type="hidden" name="userId" value="${userId }">
			<input type="hidden" name="resourceId" value="${resourceId }">
			<div class="form-group">
				<select id="roleId" name="roleId" multiple class="form-control">
					<c:forEach items="${roles }" var="role">
					<option value="${role.roleId }"<c:if test="${selectetRole[role.roleId] != null}">selected</c:if>>${role.roleName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<div class="span7 text-center">
					<input type="submit" class="btn btn-success" value="保存" >
					<button id="cancelBtn" type="button" class="btn btn-info " onClick="javascript:art.dialog.close()">取消</button>
				</div>
			</div>
		</form>
	</div>
	<script>
	$(document).ready(function(){
		$("#roleId").pickList({
				sourceListLabel: "可选角色",
				targetListLabel: "已选角色"
		});
	});
	
	function process() {
		form.action = "<c:url value="/admin/system/role/popup/updatePickRole.do"/>";
	}
	</script>
	
	<%@include file="/common/formResponse.jsp"%>
</body>
</html>