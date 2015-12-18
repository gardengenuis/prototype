<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>部门管理</title>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/department/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/department/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<display:table export="true"  name="departments" requestURI="" pagesize="10" uid="departments" id="departments" partialList="false">
			<display:column property="departId" title="#" sortable="true"   />
			<display:column property="departCode" title="部门编号"   />
			<display:column property="departName" title="部门名称"   />
			<display:column title="上级部门"   >
				<translate:tableColumn tableName="SYS_DEPARTMENT" inputColumnName="DEPART_ID" outputColumnName="DEPART_NAME" inputValue="${departments.parentId }"/>
			</display:column>
			<display:column title="状态"   >
				<translate:dict fieldCode="STATUS" fieldValue="${departments.status }"/>
			</display:column>
			
			<display:setProperty name="paging.banner.item_name" value="部门" />
			<display:setProperty name="paging.banner.items_name" value="部门" />
			<display:setProperty name="export.excel.filename" value="department.xls" />
			<display:setProperty name="export.rtf.filename" value="department.rtf" />
		</display:table>
		
	</div>
</body>
</html>