<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>测试</title>
	<script>
	
	
	$(document).ready(function() {
		var dialog, form;
		
		$("#deptBox").on('dblclick', 'option', function (e) {
			alert($(e.target).text());
			$(e.target).remove();
		});
		
		dialog = $( "#dialog-form" ).dialog({
		      autoOpen: false,
		      height: 400,
		      width: 400,
		      modal: true,
		      buttons: {
		        "Create an account": null,
		        Cancel: function() {
		          dialog.dialog( "close" );
		        }
		      },
		      close: function() {
		        form[0].reset();
		        allFields.removeClass( "ui-state-error" );
		      }
		    });
		 
		    form = dialog.find( "form" ).on( "submit", function( event ) {
		      event.preventDefault();
		    });
		 
		    $( "#create-user" ).button().on( "click", function() {
		      dialog.dialog( "open" );
		    });
	});

</script>
<SCRIPT>
   var zTreeObj;
   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
   var setting = {
		   async: {
				enable: true,
				url: "<c:url value="/admin/system/department/tree.do"/>",
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
	
	
   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
   var rootNote = [
                   { id:"1", name:"总店", open:true, isParent:true}
   ];
   
   $(document).ready(function(){
      zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, rootNote);
      
      
   });
   
   
  
  </SCRIPT>
</head>
<body>

<div id="dialog-form" title="修改所属部门" style="display: none;">
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
								<ul id="treeDemo" class="ztree"></ul>
							</td>
							<td align="left" valign="top" height="100%" width="50%">
								<div id="hello">aaa</div>
								<select name="select" size="10" id="deptBox" style="width:150px" multiple="multiple">
								</select>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
</div>

<button id="create-user">Create new user</button>	

</body>
</html>