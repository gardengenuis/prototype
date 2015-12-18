<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>修改密码</title>
</head>
<body>

	<div class="container">
		<form class="form-horizontal" role="form" >
			<input type="hidden" name="status" id="status" value="1">
	        <div class="form-group">
	            <label for="oldPassword">旧密码</label>
	            <input type="password" class="form-control" name="oldPassword" id="oldPassword" placeholder="输入用户旧密码" required>
	        </div>
	       	<div class="form-group">
	            <label for="password">新密码</label>
	            <input type="password" class="form-control" name="password" id="password" placeholder="输入用户新密码" required>
	        </div>
			<div class="form-group">
	            <label for="password_confirm">确认新密码</label>
	            <input type="password" class="form-control" name="password_confirm" id="password_confirm" placeholder="确认用户新密码" required>
	        </div>
	        <button type="button" id="saveBtn" class="btn btn-primary">保存</button>
	    </form>
    </div>
<script src="<c:url value="/js/validate.js"/>"></script>
<script>
$(document).on("click", "#saveBtn", function(e) {
	if ( isEmpty($("#oldPassword").val())) {
		bootbox.alert("请输入旧密码");
		return;
	}
	if ( isEmpty($("#password").val())) {
		bootbox.alert("请输入新密码");
		return;
	}
	if ( isEmpty($("#password_confirm").val())) {
		bootbox.alert("请输入确认新密码");
		return;
	}
	if ($("#password_confirm").val() != $("#password").val()) {
		bootbox.alert("确认密码与新密码不相同,请再输入一次");
		return;
	}
	
	var jsonData = {
			oldPassword: $("#oldPassword").val(),
			password: $("#password").val()
	};
	
	$.ajax({
		url: "<c:url value="/admin/changePass.do"/>",
		type: "POST",
		beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
		},
		datatype:"json",
		data: JSON.stringify(jsonData),
		success: function (data) {
			if ( data.code == "0") {
				// JOPO json object
				bootbox.alert("修改密码成功");
			} else {
				bootbox.alert("修改密码失败:[" +data.msg+ "]")
			}
			
		},
		error: function( jqXHR, textStatus, errorThrown) {
			bootbox.alert("修改密码失败", function() {
				// do something
			});
		}
	});
});
</script>
</body>
</html>