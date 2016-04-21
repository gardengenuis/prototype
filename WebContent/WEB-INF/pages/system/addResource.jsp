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
			<li role="presentation"><a href="<c:url value="/admin/system/resource/edit.do"/>"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>
		
		<form class="form-horizontal" role="form" action="<c:url value="/admin/system/resource/add.do"/>">
			<input type="hidden" name="status" id="status" value="1">
			<div class="form-group">
	            <label for="resourceName">资源名称</label>
	            <input type="text" class="form-control" name="resourceName" id="resourceName" placeholder="输入资源名称" required>
	        </div>
	        <div class="form-group">
	            <label for="resourceUrl">资源URL</label>
	            <input type="text" class="form-control" name="resourceUrl" id="resourceUrl" placeholder="输入资源URL">
	        </div>
	        <div class="form-group">
	            <label for="resourceType">资源类型</label>
	            <translate:dictSelect fieldCode="RESOURCE_TYPE" cssClass="form-control" tagName="resourceType" idName="resourceType" required="true"></translate:dictSelect>
	        </div>
	        <div class="form-group">
	            <label for="parentId">父资源</label>
		            <select class="form-control" id="parentId" name="parentId" >
		            	<option value="" selected>选择父资源</option>
		            	<c:forEach items="${resources}" var="res">
							<option value="${res.resourceId }">${res.resourceName }</option>
						</c:forEach>
					</select>
			</div>
			<div class="form-group">
	            <label for="orderNum">顺序</label>
	            <input type="text" class="form-control" name="orderNum" id="orderNum" placeholder="输入有效数字" required>
	        </div>
	        <button type="submit" class="btn btn-success center-block"><span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;保存</button>
	    </form>
	  	
	</div>
</body>
</html>