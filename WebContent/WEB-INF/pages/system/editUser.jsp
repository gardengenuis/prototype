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
					<button id="editDeptBtn" value="${users.userId }" type="button" class="btn btn-info">修改所属部门</button>
				</div>
				<div class="btn-group">
					<button id="editRoleBtn" value="${users.userId }" type="button" class="btn btn-info">修改角色</button>
				</div>
				<div class="btn-group">
					<button id="deleteBtn" value="${users.userId }" type="button" class="btn btn-danger">删除</button>
				</div>
			</display:column>
			
			<display:setProperty name="paging.banner.item_name" value="用户" />
			<display:setProperty name="paging.banner.items_name" value="用户" />
		</display:table>
		
	  	
	  	
	</div>
	
	<!-- 弹出框（部门） -->
	<div id="dialog-form-dept" title="修改所属部门" style="display: none;">
		<p class="validateTips">拖拉或者双击增加部门</p>
			<form>
				<table >
					<thead>
						<tr>
			            	<th>选择部门</th>
			            	<th>当前部门（双击可删除）</th>
			          	</tr>
			        </thead>
					<tbody>
						<tr>
							<td align="left" valign="top" height="100%" width="50%">
								<ul id="deptTree" class="ztree"></ul>
							</td>
							<td align="left" valign="top" height="100%" width="50%">
								<select name="select" size="10" id="deptBox" style="width:150px" multiple="multiple">
								</select>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
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
		var components = ["userName","password","status"];
		var deleteUrl = "<c:url value="/admin/system/user/delete.do"/>";
		var editUrl = "<c:url value="/admin/system/user/edit.do"/>";
		var successUrl = "<c:url value="/admin/system/user/edit.do"/>";
		var functionName = "用户";
		var idName = "userId";
	</script>
	
	<!-- 修改部门 -->
	<script>
		var curEditDeptId;
		// 部门树
		var deptTreeObj;
		var rootNote = [
			{ id:"1", name:"根部门", open:true, isParent:true}
		];
		// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		   var setting = {
				   async: {
						enable: true,
						url: "<c:url value="/ajax/department/tree.do"/>",
						type:"POST",
						autoParam:["id"],
						dataFilter: filter
					},
					callback: {
						onDrop: zTreeOnDrop,
						onDblClick: zTreeOnDblClick
					},
					edit: {
						enable: true,
						showRemoveBtn: false,
						showRenameBtn: false,
						drag: {
							isCopy: false,
							isMove: true,
							prev: false,
							next: false,
							inner: false
						}
					}
		   };
		   function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
			   if ( $(event.target).attr("id") ==$("#deptBox").attr("id")) {
				   for ( var i=0; i<treeNodes.length; i++) {
					   var node = treeNodes[i];
					   
					   if ( !containValue( $("#deptBox option"), node.id) ) {
						   $(event.target).append("<option value='" + node.id + "'>" + node.name + "</option>");
					   }
				   }
			   }
		   };
		   function zTreeOnDblClick(event, treeId, treeNode) {
				   if ( !containValue( $("#deptBox option"), treeNode.id) ) {
					   $("#deptBox").append("<option value='" + treeNode.id + "'>" + treeNode.name + "</option>");
				   }
		   };
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
		   
		   function filter(treeId, parentNode, childNodes) {
				return childNodes;
			};
		
		// 弹出框
		$(document).ready(function() {
			deptTreeObj = $.fn.zTree.init($("#deptTree"), setting, rootNote);

			var dialog, form;

			$("#deptBox").on('dblclick', 'option', function (e) {
				$(e.target).remove();
			});
			
			dialog = $( "#dialog-form-dept" ).dialog({
				autoOpen: false,
				height: 400,
				width: 400,
				modal: true,
				buttons: {
					"保存": function() {
						var departIds = new Array();
						$("#deptBox option").each(function(){ //遍历全部option
					        var val = $(this).val(); //获取option的内容
					        departIds.push(val); //添加到数组中
					    });
						var jsonData = {
								userId: curEditDeptId,
								departId: departIds
						};
						
						$.ajax({
							url: "<c:url value="/ajax/userdepartment/add.do"/>",
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
									bootbox.alert("修改用户部门成功",function() {
										//location.href="<c:url value="/admin/system/user/edit.do"/>";
										location.reload();
									});
									
								} else {
									bootbox.alert("获取用户部门失败:[" + data.msg + "]");
								}
							},
							error: function( jqXHR, textStatus, errorThrown) {
								bootbox.alert("修改用户部门失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
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
			 
			form = dialog.find( "form" ).on( "submit", function( event ) {
				event.preventDefault();
			});
			
			$("button[id^='editDeptBtn']").on( "click", function(e) {
				curEditDeptId = $(this).val();
				
				var jsonData = {
						userId: curEditDeptId
				};
				
				$("#deptBox").empty();
				
				$.ajax({
					url: "<c:url value="/ajax/userdepartment/list.do"/>",
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
    						var departs = data.object;
							for ( var i=0; i<departs.length; i++) {
								var depart = departs[i];
								var option = "<option value='" + depart.departId + "'>" + depart.departName + "</option>";
								$("#deptBox").append(option);
							}
						} else {
							bootbox.alert("获取用户部门失败:[" + data.msg + "]");
						}
					},
					error: function( jqXHR, textStatus, errorThrown) {
						bootbox.alert("获取用户部门失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
					}
				});
				
				dialog.dialog( "open" );
			});
		});
	</script>
	
	<!-- 修改角色 -->
	<script>
	var curEditRoleId;
	
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
							userId: curEditRoleId,
							roleId: roleIds
					};
					
					$.ajax({
						url: "<c:url value="/ajax/userrole/add.do"/>",
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
								bootbox.alert("修改用户角色成功",function() {
									//location.href="<c:url value="/admin/system/user/edit.do"/>";
									location.reload();
								});
								
							} else {
								bootbox.alert("获取用户角色失败:[" + data.msg + "]");
							}
						},
						error: function( jqXHR, textStatus, errorThrown) {
							bootbox.alert("修改用户角色失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
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
			curEditRoleId = $(this).val();
			
			var jsonData = {
					userId: curEditRoleId
			};
			
			$("#roleBox").empty();
			$("#roleBox2Choose").empty();
			
			$.ajax({
				url: "<c:url value="/ajax/userrole/tree.do"/>",
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
				url: "<c:url value="/ajax/userrole/list.do"/>",
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
						bootbox.alert("获取用户部门失败:[" + data.msg + "]");
					}
				},
				error: function( jqXHR, textStatus, errorThrown) {
					bootbox.alert("获取用户部门失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
				}
			});
			
			dialog.dialog( "open" );
		});
	});
	</script>
</body>
</html>