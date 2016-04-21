var ajaxTipDl=null;

document.ondblclick = function(e) {
	return false; 
} 

$(function() {
	$setFormCheckSubmited();
});

function $setFormCheckSubmited() {
	var frms = document.forms;
	for ( var i = 0; i < document.forms.length; i++) {
		var form = document.forms[i];
		form.oldOnSubmit = form.onsubmit;
		form.method="POST";
		form.submited = false;//添加一个submited属性，并且设置其为false
		form.onsubmit = function() {
			if (form.oldOnSubmit != undefined) {
				var result = form.oldOnSubmit();
				if (result != undefined && !result) {
					return false;
				}
			}
			if(!$submitForm(form)){
				return false;
			}
			if(!form.exp){
				showRequestTip();
			}
		}
		 if(form.oldOnSubmit==undefined){
			 form.baseSubmit = form.submit;//保存表单原来的submit 方法
			 form.submit =function(){
				form.onsubmit(); 
				form.baseSubmit();
			 };
		} 
		//form.baseSubmit = form.submit;//保存表单原来的submit 方法
		//form.submit = new Function("$submitForm(this)");
		//$addElementEventHandler(form, "onsubmit","$submitForm(document.forms[" + i + "])");
	}
}

//提交一个表单，如果当前表单已经提交，按么就不会继续提交该表单
function $submitForm(frm) {
	if (frm.submited)
		return false;
	frm.submited = true;
	return true;
}

function showRequestTip(){
	ajaxTipDl=art.dialog.through({
		id: 'Tips',
		padding:0,
		width:"200px",
		noFn: false,
	    title: false,
	    lock: true,
	    content: "请求处理中,请稍等..."
	});
}