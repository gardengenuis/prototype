<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>用户管理</title>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/user/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/user/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<display:table export="true"  name="users" requestURI="" pagesize="10" uid="users" id="users" partialList="false">
			<display:column property="userId" title="#" sortable="true"   />
			<display:column property="userCode" title="用户编号" sortable="true"   />
			<display:column property="userName" title="用户名称" sortable="true"   />
			<display:column escapeXml="false" title="所属部门" >
				<c:forEach items="${users.departments }" var="dept" varStatus="status">
           			${dept.departName}<c:if test="${!status.last }">,</c:if>
           		</c:forEach>
			</display:column>
			<display:column escapeXml="false" title="状态" >
				<translate:dict fieldCode="STATUS" fieldValue="${users.status }"/>
			</display:column>
			
			<display:setProperty name="paging.banner.item_name" value="用户" />
			<display:setProperty name="paging.banner.items_name" value="用户" />
			<display:setProperty name="export.excel.filename" value="user.xls" />
			<display:setProperty name="export.rtf.filename" value="user.rtf" />
		</display:table>
	  </div>

</body>
</html>