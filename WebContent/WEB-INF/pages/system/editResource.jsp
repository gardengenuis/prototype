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
					<button id="editBtn" value="${resources.resourceId }" type="button" class="btn btn-warning">修改</button>
				</div>
				<div class="btn-group">
					<button id="editRoleBtn" value="${resources.resourceId }" type="button" class="btn btn-info">修改所属角色</button>
				</div>
				<div class="btn-group">
					<button id="deleteBtn" value="${resources.resourceId }" type="button" class="btn btn-danger">删除</button>
				</div>
			</display:column>
			<display:setProperty name="paging.banner.item_name" value="资源" />
			<display:setProperty name="paging.banner.items_name" value="资源" />
		</display:table>
		
		
	  	
	</div>
	
	<!-- 弹出框（角色） -->
	<div id="dialog-form-role" title="修改所属角色" style="display: none;">
		<p class="validateTips">双击增加角色</p>
			<form>
				<table >
					<thead>
						<tr>
			            	<th>选择角色</th>
			            	<th>当前角色（双击可删除）</th>
			          	</tr>
			        </thead>
					<tbody>
						<tr>
							<td align="left" valign="top" height="100%" width="50%">
								<select name="select" size="10" id="roleBox2Choose" style="width:150px" multiple="multiple">
								</select>
							</td>
							<td align="left" valign="top" height="100%" width="50%">
								<select name="select" size="10" id="roleBox" style="width:150px" multiple="multiple">
								</select>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
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
	var curEditResourceId;
	
	$(document).ready(function() {
		var dialog, form;

		$("#roleBox").on('dblclick', 'option', function (e) {
			$(e.target).remove();
		});
		
		$("#roleBox2Choose").on('dblclick', 'option', function (e) {
			if ( !containValue( $("#roleBox option"), $(e.target).val()) ) {
				$("#roleBox").append("<option value='" + $(e.target).val() + "'>" +$(e.target).text() + "</option>");
			}
		});
		
		dialog = $( "#dialog-form-role" ).dialog({
			autoOpen: false,
			height: 400,
			width: 400,
			modal: true,
			buttons: {
				"保存": function() {
					var roleIds = new Array();
					$("#roleBox option").each(function(){ //遍历全部option
				        var val = $(this).val(); //获取option的内容
				        roleIds.push(val); //添加到数组中
				    });
					var jsonData = {
							resourceId: curEditResourceId,
							roleId: roleIds
					};
					
					$.ajax({
						url: "<c:url value="/ajax/roleresource/add.do"/>",
						type: "POST",
						async:true,
						beforeSend: function(xhr) {
				            xhr.setRequestHeader("Accept", "application/json");
				            xhr.setRequestHeader("Content-Type", "application/json");
						},
						datatype:"json",
						data: JSON.stringify(jsonData),
						success: function (data) {
							// JOPO json object
							if (data.code == "0") {// 成功
								bootbox.alert("修改资源角色成功",function() {
									//location.href="<c:url value="/admin/system/resource/edit.do"/>";
									location.reload();
								});
								
							} else {
								bootbox.alert("获取资源角色失败:[" + data.msg + "]");
							}
						},
						error: function( jqXHR, textStatus, errorThrown) {
							bootbox.alert("修改资源角色失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
						}
					});
		        	$( this ).dialog( "close" );
		        },
		        "取消": function() {
		        	$(this).dialog("close");
		        }
			},
			close: function() {
				form[0].reset();
			}
		});
		 /*
		form = dialog.find( "form" ).on( "submit", function( event ) {
			event.preventDefault();
		});
		*/
		$("button[id^='editRoleBtn']").on( "click", function(e) {
			curEditResourceId = $(this).val();
			
			var jsonData = {
					resourceId: curEditResourceId
			};
			
			$("#roleBox").empty();
			$("#roleBox2Choose").empty();
			
			$.ajax({
				url: "<c:url value="/ajax/roleresource/tree.do"/>",
				type: "POST",
				async:false,
				beforeSend: function(xhr) {
		            xhr.setRequestHeader("Accept", "application/json");
		            xhr.setRequestHeader("Content-Type", "application/json");
				},
				datatype:"json",
				//data: JSON.stringify(jsonData),
				success: function (data) {
					// JOPO json object
					if (data.code == "0") {// 成功
						var roles = data.object;
						for ( var i=0; i<roles.length; i++) {
							var role = roles[i];
							var option = "<option value='" + role.roleId + "'>" + role.roleName + "</option>";
							$("#roleBox2Choose").append(option);
						}
					} else {
						bootbox.alert("获取角色列表失败:[" + data.msg + "]");
					}
				},
				error: function( jqXHR, textStatus, errorThrown) {
					bootbox.alert("获取角色列表失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
				}
			});
			
			$.ajax({
				url: "<c:url value="/ajax/roleresource/list.do"/>",
				type: "POST",
				async:false,	//同步执行
				beforeSend: function(xhr) {
		            xhr.setRequestHeader("Accept", "application/json");
		            xhr.setRequestHeader("Content-Type", "application/json");
				},
				datatype:"json",
				data: JSON.stringify(jsonData),
				success: function (data) {
					// JOPO json object
					if (data.code == "0") {// 成功
						var roles = data.object;
						for ( var i=0; i<roles.length; i++) {
							var role = roles[i];
							var option = "<option value='" + role.roleId + "'>" + role.roleName + "</option>";
							$("#roleBox").append(option);
						}
					} else {
						bootbox.alert("获取资源角色失败:[" + data.msg + "]");
					}
				},
				error: function( jqXHR, textStatus, errorThrown) {
					bootbox.alert("获取资源角色失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
				}
			});
			
			dialog.dialog( "open" );
		});
	});
	
	 function containValue(target, id) {
		   var rlt = false;
		   target.each(function(){ //遍历全部option
		        var value = $(this).val(); //获取option的内容

				if ( id == value) {
					rlt = true; 
				}
		    });
		   
			return rlt;
		};
	</script>
</body>
</html>