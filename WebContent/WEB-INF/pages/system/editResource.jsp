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
			<li role="presentation"><a href="<c:url value="/admin/system/resource/list.do"/>"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/resource/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>

		<display:table export="false"  name="resources" requestURI="" pagesize="8" uid="resources" id="resources" partialList="false">
			<display:column property="resourceId" title="#" sortable="true"   />
			<display:column title="资源名称" sortable="true" >
				<input type="text" id="resourceName${resources.resourceId}" class="form-control" value="${resources.resourceName }" disabled required style="width:100px">
			</display:column>
			<display:column title="资源URL" sortable="true" >
				<input type="text" class="form-control" id="resourceUrl${resources.resourceId}" value="${resources.resourceUrl }" disabled>
			</display:column>
			<display:column escapeXml="false" title="资源类型" >
				<translate:dictSelect fieldCode="RESOURCE_TYPE" fieldValue="${resources.resourceType}" cssClass="form-control" disabled="true" idName="resourceType${resources.resourceId}"/>
			</display:column>
			<display:column escapeXml="false" title="父资源" >
				<select class="form-control" id="parentId${resources.resourceId}"  disabled>
					<option value="${resources.parentId }" selected><translate:tableColumn tableName="SYS_RESOURCE" inputColumnName="RESOURCE_ID" outputColumnName="RESOURCE_NAME" inputValue="${resources.parentId }" /></option>
					<c:if test="${resources.parentId != null}">
						<option value="" ></option>
					</c:if>
            		<c:forEach items="${menuRes}" var="res">
            			<c:if test="${res.resourceId != resources.resourceId && resources.parentId != res.resourceId}">
            				<option value="${res.resourceId }">${res.resourceName }</option>
            			</c:if>
					</c:forEach>
				</select>
			</display:column>
			<display:column escapeXml="false" title="状态" >
				<translate:dictSelect fieldCode="STATUS" fieldValue="${resources.status}" cssClass="form-control" disabled="true" idName="status${resources.resourceId}" required="true"></translate:dictSelect>
			</display:column>
			<display:column title="顺序" sortable="true" >
				<input type="number" class="form-control" id="orderNum${resources.resourceId}" value="${resources.orderNum }" disabled required placeholder="输入有效数字" style="width:100px">
			</display:column>
			<display:column escapeXml="false" title="操作" >
				<div class="btn-group">
					<button id="editBtn" value="${resources.resourceId }" type="button" class="btn btn-warning glyphicon glyphicon-edit" title="修改"></button>
				</div>
				<div class="btn-group">
					<button id="editRoleBtn" value="${resources.resourceId }" type="button" class="btn btn-info glyphicon glyphicon-tower" title="所属角色"></button>
				</div>
				<div class="btn-group">
					<button id="deleteBtn" value="${resources.resourceId }" type="button" class="btn btn-danger glyphicon glyphicon-trash" title="删除"></button>
				</div>
			</display:column>
			<display:setProperty name="paging.banner.item_name" value="资源" />
			<display:setProperty name="paging.banner.items_name" value="资源" />
		</display:table>
	</div>
	
	<script src="<c:url value="/js/validate.js"/>"></script>
	<script src="<c:url value="/js/basic_edit.js"/>"></script>
	<script>
	var components = ["resourceName","resourceUrl","status","resourceType","parentId","orderNum"];
	var deleteUrl = "<c:url value="/admin/system/resource/delete.do"/>";
	var editUrl = "<c:url value="/admin/system/resource/edit.do"/>";
	var successUrl = "<c:url value="/admin/system/resource/edit.do"/>";
	var functionName = "资源";
	var idName = "resourceId";
	</script>
	
	<!-- 修改角色 -->
	<script>	
	$(document).ready(function() {
		// 点击修改资源角色
       	$("button[id^='editRoleBtn']").on( "click", function(e) {
       		var resourceId = $(this).val();
       		art.dialog.open("<c:url value="/admin/system/role/popup/pickRole.do?resourceId="/>" + resourceId, {
       			title: "修改资源角色",
       			width: "400px",
       			height: "400px",
       			fixed:true,
       			lock:true
       		});
       	});
	});
	</script>
</body>
</html>