var components = [];
var deleteUrl = "";
var successUrl = "";
var editUrl = "";
var functionName = "";
var idName = "";


$(document).on("click", "#editBtn", function(e) {
		var targetId = $(e.target).val();
		var oper = $(e.target).text();
		
		if (oper == '修改') { // 点击修改
			
			for ( var i=0; i<components.length; i++) {
				$("#" + components[i] +targetId).removeAttr("disabled");
				if ( i == 0) {
					$("#" + components[i] +targetId).focus();
				}
			}
			
			$(e.target).removeClass("btn-warning");
			$(e.target).addClass("btn-info");
			$(e.target).text("保存");
		} else {  // 点击保存
			
			for ( var i=0; i<components.length; i++) {
				if ( isEmpty($("#" + components[i] +targetId).val())
						&& isNotEmpty($("#" + components[i] +targetId).attr("required"))) {
					popError("保存失败:[必填项不能为空]");
					return;
				}
			}
			
			var jsonData = {};
			
			for ( var i=0; i<components.length; i++) {
				eval("jsonData." + components[i] + "='" + $("#" + components[i] +targetId).val() + "'");
			}
			eval("jsonData." + idName + "='" + targetId + "'");
			
			$.ajax({
				url: editUrl,
				type: "POST",
				beforeSend: function(xhr) {
		            xhr.setRequestHeader("Accept", "application/json");
		            xhr.setRequestHeader("Content-Type", "application/json");
				},
				datatype:"json",
				data: JSON.stringify(jsonData),
				success: function (data) {
					// JOPO json object
					popSuccess("保存" + functionName + "ID:[" +eval("data.object." + idName) + "]成功", function() {
						location.reload();
					});
				},
				error: function( jqXHR, textStatus, errorThrown) {
					popError("保存失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
				}
			});
			
			for ( var i=0; i<components.length; i++) {
				$("#" + components[i] +targetId).attr("disabled",true);
			}
			
			$(e.target).removeClass("btn-info");
			$(e.target).addClass("btn-warning");
			$(e.target).text("修改");
		}
	});
	
	$(document).on("click", "#deleteBtn", function(e) {
		popConfirm(
				"确认删除?",
				function() {
    				var jsonData = {};
    				eval("jsonData." + idName + "='" + $(e.target).val() + "'");
    		        //Example.show("uh oh, look out!");
    				$.ajax({
    					url: deleteUrl,
    					type: "DELETE",
    					beforeSend: function(xhr) {
				            xhr.setRequestHeader("Accept", "application/json");
				            xhr.setRequestHeader("Content-Type", "application/json");
						},
						datatype:"json",
    					data: JSON.stringify(jsonData),
    					success: function (data) {
    						// JOPO json object
    						if (data.code == "0") {// 成功
    							popSuccess("删除" + functionName + "ID:[" +eval("data.object." + idName) + "]成功", function() {
        							location.reload();
        						});
    						} else {
    							popError("删除" + functionName + "失败:[" + data.msg + "]");
    						}
    					},
    					error: function( jqXHR, textStatus, errorThrown) {
    						popError("删除失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]");
    					}
    				});
				});
    });