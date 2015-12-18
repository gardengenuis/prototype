function TreeSelect() {
	var curEditObjId;
	var treeObject;
	var rootNode = [];
	var settingUrl = "<c:url value="/ajax/department/tree.do"/>";
	var boxId;
	
	var setting = {
			async: {
				enable: true,
				url: settingUrl,
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
	
	var zTreeOnDrop = function(event, treeId, treeNodes, targetNode, moveType) {
		if ( $(event.target).attr("id") ==$("#" + boxId).attr("id")) {
			for ( var i=0; i<treeNodes.length; i++) {
				var node = treeNodes[i];
	   
				if ( !containValue( $("#" + boxId + " option"), node.id) ) {
					$(event.target).append("<option value='" + node.id + "'>" + node.name + "</option>");
				}
			}
		}
	};
	
	var zTreeOnDblClick = function(event, treeId, treeNode) {
		if ( !containValue( $("#" + boxId + " option"), treeNode.id) ) {
			$("#" + boxId).append("<option value='" + treeNode.id + "'>" + treeNode.name + "</option>");
		}
	};
	
	var containValue = function(target, id) {
		var rlt = false;
		target.each(function(){ //遍历全部option
			var value = $(this).val(); //获取option的内容

			if ( id == value) {
				rlt = true; 
			}
		});
		return rlt;
	};
		
	var filter = function(treeId, parentNode, childNodes) {
		return childNodes;
	};
}