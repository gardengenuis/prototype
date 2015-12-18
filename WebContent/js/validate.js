var isNotEmpty = function( src) {
	if ( src != null
			&& src != "") {
		return true;
	}
	return false;
};

var isEmpty = function( src) {
	return !isNotEmpty(src);
};

var isValidPhoneNum = function(src) {
	var regBox = {
			regMobile : /^0?1[3|4|5|8|7][0-9]\d{8}$/,//手机
	        regTel : /^[\d]{8}$/
	}
	var mflag = regBox.regMobile.test(src);
    var tflag = regBox.regTel.test(src);
    
    if (!(mflag||tflag)) {
        return false;
    }else{
        return true;
    };
}

var isValidMobileNum = function(src) {
	var regBox = {
			regMobile : /^0?1[3|4|5|8|7][0-9]\d{8}$/
	}
	var mflag = regBox.regMobile.test(src);
    
    if (!(mflag)) {
        return false;
    }else{
        return true;
    };
}

var isValidBusCode = function(src) {
	var reg = /^[\d]{6}(\w+)$/
	var mflag = reg.test(src);
    
    if (!mflag) {
        return false;
    }else{
        return true;
    };
}