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
			<li role="presentation"><a href="<c:url value="/admin/system/role/list.do"/>"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/role/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>

		<display:table export="true"  name="roles" requestURI="" pagesize="10" uid="roles" id="roles" partialList="false">
			<display:column property="roleId" title="#" sortable="true"   />
			<display:column title="角色编号"   >
				<input type="text" class="form-control" id="roleCode${roles.roleId}" value="${roles.roleCode }" disabled required>
			</display:column>
			<display:column title="角色名称"   >
				<input type="text" class="form-control" id="roleName${roles.roleId}" value="${roles.roleName }" disabled required>
			</display:column>
			<display:column title="状态"   >
				<translate:dictSelect fieldCode="STATUS" fieldValue="${roles.status}" cssClass="form-control" disabled="true" idName="status${roles.roleId}" required="true"></translate:dictSelect>
			</display:column>
		
			<display:column escapeXml="false" title="操作" >
				<div class="btn-group">
					<button id="editBtn" value="${roles.roleId }" type="button" class="btn btn-warning">修改</button>
				</div>
				<div class="btn-group">
					<button id="editResBtn" value="${roles.roleId }" type="button" class="btn btn-info">修改拥有资源</button>
				</div>
				<div class="btn-group">
					<button id="deleteBtn" value="${roles.roleId }" type="button" class="btn btn-danger">删除</button>
				</div>
			</display:column>
			
			<display:setProperty name="paging.banner.item_name" value="角色" />
			<display:setProperty name="paging.banner.items_name" value="角色" />
		</display:table>
	  	
	</div>
	
	<script src="<c:url value="/js/validate.js"/>"></script>
	<script src="<c:url value="/js/basic_edit.js"/>"></script>
	<script>
	var components = ["roleCode","roleName","status"];
	var deleteUrl = "<c:url value="/admin/system/role/delete.do"/>";
	var editUrl = "<c:url value="/admin/system/role/edit.do"/>";
	var successUrl = "<c:url value="/admin/system/role/edit.do"/>";
	var functionName = "角色";
	var idName = "roleId";
	</script>
	
	<!-- 修改拥有资源 -->
	<script>	
	$(document).ready(function() {
		// 点击修改角色资源
       	$("button[id^='editResBtn']").on( "click", function(e) {
       		var roleId = $(this).val();
       		art.dialog.open("<c:url value="/admin/system/resource/popup/pickRes.do?roleId="/>" + roleId, {
       			title: "修改角色资源",
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