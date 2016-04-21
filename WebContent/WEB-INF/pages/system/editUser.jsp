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
			<li role="presentation"><a href="<c:url value="/admin/system/user/list.do"/>"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/user/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>

		<display:table export="false"  name="users" requestURI="" pagesize="8" uid="users" id="users" partialList="false">
			<display:column property="userId" title="#" sortable="true"   />
			<display:column escapeXml="false" title="用户编号" >
				<input type="text" class="form-control" id="userCode${ users.userId}" value="${users.userCode }" disabled required style="width:50px">
			</display:column>
			<display:column escapeXml="false" title="用户名称" >
				<input type="text" class="form-control" id="userName${ users.userId}" value="${users.userName }" disabled required style="width:60px">
			</display:column>
			<display:column escapeXml="false" title="密码" >
				<input type="password" class="form-control" id="password${users.userId}" value="${users.password }" disabled required style="width:100px">
			</display:column>
			<display:column escapeXml="false" title="所属部门" >
				<c:forEach items="${users.departments }" var="dept" varStatus="status">
           			${dept.departName}<c:if test="${!status.last }">,</c:if>
           		</c:forEach>
			</display:column>
			<display:column escapeXml="false" title="拥有角色" >
				<c:forEach items="${users.roles }" var="role" varStatus="status">
           			${role.roleName}<c:if test="${!status.last }">,</c:if>
           		</c:forEach>
			</display:column>
			<display:column escapeXml="false" title="状态" >
				<translate:dictSelect fieldCode="STATUS" fieldValue="${users.status}" cssClass="form-control" disabled="true" idName="status${users.userId}" required="true"/>
			</display:column>
			<display:column escapeXml="false" title="操作" >
				<div class="btn-group">
					<button id="editBtn" value="${users.userId }" type="button" class="btn btn-warning">修改</button>
				</div>
				<div class="btn-group">
					<button id="editDeptBtn" value="${users.userId }" type="button" class="btn btn-info">所属部门</button>
				</div>
				<div class="btn-group">
					<button id="editRoleBtn" value="${users.userId }" type="button" class="btn btn-info">拥有角色</button>
				</div>
				<div class="btn-group">
					<button id="deleteBtn" value="${users.userId }" type="button" class="btn btn-danger">删除</button>
				</div>
			</display:column>
			
			<display:setProperty name="paging.banner.item_name" value="用户" />
			<display:setProperty name="paging.banner.items_name" value="用户" />
		</display:table>
	</div>
		
	<script src="<c:url value="/js/validate.js"/>"></script>
	<script src="<c:url value="/js/basic_edit.js"/>"></script>
	<script>
		var components = ["userName","password","status"];
		var deleteUrl = "<c:url value="/admin/system/user/delete.do"/>";
		var editUrl = "<c:url value="/admin/system/user/edit.do"/>";
		var successUrl = "<c:url value="/admin/system/user/edit.do"/>";
		var functionName = "用户";
		var idName = "userId";
	</script>
	
	<script>	
	$(document).ready(function() {
		// 点击修改用户角色
       	$("button[id^='editRoleBtn']").on( "click", function(e) {
       		var userId = $(this).val();
       		art.dialog.open("<c:url value="/admin/system/role/popup/pickRole.do?userId="/>" + userId, {
       			title: "修改用户角色",
       			width: "400px",
       			height: "400px",
       			fixed:true,
       			lock:true
       		});
       	});
		
     	// 点击修改用户部门
       	$("button[id^='editDeptBtn']").on( "click", function(e) {
       		var userId = $(this).val();
       		art.dialog.open("<c:url value="/admin/system/department/popup/pickDepartment.do?userId="/>" + userId, {
       			title: "修改用户部门",
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