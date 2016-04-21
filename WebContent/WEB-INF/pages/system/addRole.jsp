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
			<li role="presentation"><a href="<c:url value="/admin/system/role/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<form class="form-horizontal" role="form" action="<c:url value="/admin/system/role/add.do"/>">
			<input type="hidden" name="status" id="status" value="1">
	        <div class="form-group">
	            <label for="roleCode">角色编码</label>
	            <input type="text" class="form-control" name="roleCode" id="roleCode" placeholder="输入角色编码(英文缩写)" required>
	        </div>
	       	<div class="form-group">
	            <label for="roleName">角色名称</label>
	            <input type="text" class="form-control" name="roleName" id="roleName" placeholder="输入角色名称" required>
	        </div>
	        <button type="submit" class="btn btn-success center-block"><span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;保存</button>
	    </form>
	  	
	</div>
</body>
</html>