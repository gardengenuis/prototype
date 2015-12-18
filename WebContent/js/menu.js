function openURL(url, target) {
	// 刷新内容页
	$("#frameWin").attr("src",url);
	
	// 刷新导航条
	var navNames = refreshNavBar($(target));
}

function refreshNavBar(target) {
	$("#nav-bar").empty();
	var navName = target.text();
	
	var activeTag = $("<li class='active'></li>").text(navName);

	$("#nav-bar").prepend(activeTag);
	
	var parentTarget = target.closest(".dropdown-menu").parent().children("a");
	
	if ( parentTarget.length > 0)
		fillParentNavBar(parentTarget);	
}

function fillParentNavBar(target) {
	var navName = target.text();
	
	var activeTag = $("<li></li>").text(navName);

	$("#nav-bar").prepend(activeTag);
	
	var parentTarget = target.closest(".dropdown-menu").parent().children("a");
	
	if ( parentTarget.length > 0)
		fillParentNavBar(parentTarget);
}