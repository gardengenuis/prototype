var bootbox4frame = {
		alert : function(content, callback) {
			top.bootbox.alert(content,callback);
		},
		
		dialog : function(data) {
			top.bootbox.dialog(data);
		}
};