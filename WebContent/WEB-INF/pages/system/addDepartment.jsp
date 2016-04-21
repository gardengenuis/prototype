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
			<li role="presentation"><a href="<c:url value="/admin/system/department/list.do"/>"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>列表</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/department/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<form class="form-horizontal" role="form" action="<c:url value="/admin/system/department/add.do"/>">
			<input type="hidden" name="status" id="status" value="1">
	        <div class="form-group">
	            <label for="departCode">部门代码</label>
	            <input type="text" class="form-control" name="departCode" id="departCode" placeholder="输入部门代码(建议填写数字)" required>
	        </div>
	       	<div class="form-group">
	            <label for="departName">部门名称</label>
	            <input type="text" class="form-control" name="departName" id="departName" placeholder="输入部门名称" required>
	        </div>
			<div class="form-group">
	            <label for="parentId">上级部门</label>
		            <select class="form-control" id="parentId" name="parentId" >
		            	<option value="" selected>选择上级部门</option>
		            	<c:forEach items="${departments}" var="depart">
							<option value="${depart.departId }">${depart.departName }</option>
						</c:forEach>
					</select>
			</div>
			<button type="submit" class="btn btn-success center-block"><span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;保存</button>
	    </form>
	  	
	</div>
</body>
</html>