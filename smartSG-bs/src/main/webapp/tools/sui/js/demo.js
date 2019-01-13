$.validate.setRule("number", number, function ($input) {
	var rules = $($input[0]).data("rules").split("|");
	var numRule = "";
	$.each(rules, function(index, rule) {
		var _index = rule.indexOf("number");
		if(_index != -1) {
			numRule = rule;
			return false;
		}
	});
	var param = numRule.split("=")[1];
	var paramArray = param.slice(1,param.length-1).split(",");
	var errorMsg = "输入最多" + paramArray[0] + "位整数，" + paramArray[1] + "位小数";
  	return errorMsg;
});
var number = function(value, element, param) {
	var paramArray = param.slice(1,param.length-1).split(",");
	var reguStr = "^[0]([.]{1}[0-9]{1,2})?$|^[1-9]{1,"+paramArray[0]+"}([.]{1}[0-9]{1,"+paramArray[1]+"})?$";
	var regExp = new RegExp(reguStr);
	if(regExp.test(value)) {
		return true;
	}
	return false;
};
/**
 * <input type="text" name="number" placeholder="小数" data-rules="required|number=[7,3]" maxlength="20" />
 */

$.validate.setRule("match", match, '两次密码不一致！');
var match = function(value, element, param) {
	value = trim(value);
	return value == $(element).parents('form').find("[name='"+param+"']").val();
};

/**
 * <input type="password" name="password"  placeholder="请填写新密码" data-rules="required"/>
 * <input type="password" name="confirmPassword"  placeholder="请填写确认密码" data-rules="required|match=password"/>
 */