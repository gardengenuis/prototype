function popError(content, onclose) {
	popDialog("error", content, onclose);
}

function popSuccess(content, onclose) {
	popDialog("succeed", content, onclose);
}

function popAlert(content) {
	art.dialog.alert(content);
}

function popConfirm(content, yesFn, noFn) {
	art.dialog.confirm(content, yesFn, noFn);
}

function popDialog(type, content, onclose) {
	art.dialog.through({
	    icon: type,
	    title: "提示",
	    lock: true,
	    fixed:true,
	    content: content,
	    closeFn: onclose,
	    button: [{
		    	name: '确定',
		    	focus: true
	    	}
	    ]
	});
}