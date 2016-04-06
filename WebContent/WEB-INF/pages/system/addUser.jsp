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
			<li role="presentation"><a href="<c:url value="/admin/system/user/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<form class="form-horizontal" role="form" action="<c:url value="/admin/system/user/add.do"/>" onsubmit="return validate()">
			<input type="hidden" name="status" id="status" value="1">
	        <div class="form-group">
	            <label for="userCode">用户编号</label>
	            <input type="text" class="form-control" name="userCode" id="userCode" placeholder="输入用户编号(英文缩写)" required>
	        </div>
	       	<div class="form-group">
	            <label for="userName">用户名称</label>
	            <input type="text" class="form-control" name="userName" id="userName" placeholder="输入用户名称" required>
	        </div>
			<div class="form-group">
	            <label for="password">密码</label>
	            <input type="password" class="form-control" name="password" id="password" placeholder="输入用户密码" required>
	            <label for="password_confirm">确认密码</label>
	            <input type="password" class="form-control" name="password_confirm" id="password_confirm" placeholder="确认用户密码" required>
	        </div>
	        <button type="submit" class="btn btn-primary">保存</button>
	    </form>
<script src="<c:url value="/js/validate.js"/>"></script>
<script>
	function validate() {
		if ( $("#password_confirm").val() != $("#password").val()) {
			bootbox4frame.alert("确认密码与新密码不相同,请再输入一次");
			return false;
		} else {
			return true;
		}
	}
	
	$(document).ready(function() {
		var message = "${message}";
		
		if ( message != "") {
			bootbox4frame.alert(message);
		}
	});
</script>	  	
	</div>
</body>
</html>