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
			<li role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</a></li>
			<li role="presentation"><a href="<c:url value="/admin/system/department/add.do"/>"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</a></li>
		</ul>

		<display:table export="true"  name="departments" requestURI="" pagesize="10" uid="departments" id="departments" partialList="false">
			<display:column property="departId" title="#" sortable="true"   />
			<display:column title="部门编号"   >
				<input type="text" class="form-control" id="departCode${ departments.departId}" value="${departments.departCode }" disabled required>
			</display:column>
			<display:column title="部门名称">
				<input type="text" class="form-control" id="departName${departments.departId}" value="${departments.departName }" disabled required>
			</display:column>
			<display:column title="上级部门"   >
				<select class="form-control" id="parentId${departments.departId}"  disabled>
					<option value="${departments.parentId }" selected><translate:tableColumn tableName="SYS_DEPARTMENT" inputColumnName="DEPART_ID" outputColumnName="DEPART_NAME" inputValue="${departments.parentId }" /></option>
					<c:if test="${departments.parentId != null}">
						<option value="" ></option>
					</c:if>
            		<c:forEach items="${departs}" var="depart">
            			<c:if test="${depart.departId != departments.departId && departments.parentId != depart.departId}">
            				<option value="${depart.departId }">${depart.departName }</option>
            			</c:if>
					</c:forEach>
				</select>
			</display:column>
			<display:column title="状态"   >
				<translate:dictSelect fieldCode="STATUS" fieldValue="${departments.status}" cssClass="form-control" disabled="true" idName="status${departments.departId}" required="true"></translate:dictSelect>
			</display:column>
			
			<display:column escapeXml="false" title="操作" >
				<div class="btn-group">
					<button id="editBtn" value="${departments.departId }" type="button" class="btn btn-warning">修改</button>
				</div>
				<div class="btn-group">
					<button id="deleteBtn" value="${departments.departId }" type="button" class="btn btn-danger">删除</button>
				</div>
			</display:column>
			
			<display:setProperty name="paging.banner.item_name" value="部门" />
			<display:setProperty name="paging.banner.items_name" value="部门" />
		</display:table>
			  
	</div>
	
	<script src="<c:url value="/js/validate.js"/>"></script>
	<script src="<c:url value="/js/basic_edit.js"/>"></script>
	<script>
	var components = ["departCode","departName","parentId","status"];
	var deleteUrl = "<c:url value="/admin/system/department/delete.do"/>";
	var editUrl = "<c:url value="/admin/system/department/edit.do"/>";
	var successUrl = "<c:url value="/admin/system/department/edit.do"/>";
	var functionName = "部门";
	var idName = "departId";
	</script>
</body>
</html>