<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>角色管理</title>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/role/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/role/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<display:table export="true"  name="roles" requestURI="" pagesize="10" uid="roles" id="roles" partialList="false">
			<display:column property="roleId" title="#" sortable="true"   />
			<display:column property="roleCode" title="角色编号"   />
			<display:column property="roleName" title="角色名称"   />
			<display:column title="状态"   >
				<translate:dict fieldCode="STATUS" fieldValue="${roles.status }"/>
			</display:column>
		
			<display:setProperty name="paging.banner.item_name" value="角色" />
			<display:setProperty name="paging.banner.items_name" value="角色" />
			<display:setProperty name="export.excel.filename" value="role.xls" />
			<display:setProperty name="export.rtf.filename" value="role.rtf" />
		</display:table>
	</div>
</body>
</html>