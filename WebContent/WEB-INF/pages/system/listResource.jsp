<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>资源管理</title>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/resource/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/resource/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<display:table export="true"  name="resources" requestURI="" pagesize="10" uid="resources" id="resources" partialList="true" size="resultSize">
			<display:column property="resourceId" title="#" sortable="true"   />
			<display:column property="resourceName" title="资源名称" sortable="true"   />
			<display:column property="resourceUrl" title="资源URL" sortable="true"   />
			<display:column escapeXml="false" title="资源类型" >
				<translate:dict fieldCode="RESOURCE_TYPE" fieldValue="${resources.resourceType }"/>
			</display:column>
			<display:column escapeXml="false" title="父资源" >
				<translate:tableColumn tableName="SYS_RESOURCE" inputColumnName="RESOURCE_ID" outputColumnName="RESOURCE_NAME" inputValue="${resources.parentId }"/>
			</display:column>
			<display:column escapeXml="false" title="状态" >
				<translate:dict fieldCode="STATUS" fieldValue="${resources.status }"/>
			</display:column>
			<display:column property="orderNum" title="顺序" sortable="true"   />
			
			<display:setProperty name="paging.banner.item_name" value="资源" />
			<display:setProperty name="paging.banner.items_name" value="资源" />
			<display:setProperty name="export.excel.filename" value="resource.xls" />
			<display:setProperty name="export.rtf.filename" value="resource.rtf" />
		</display:table>
		
	  	
	</div>
</body>
</html>