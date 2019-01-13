/**
 * oa忘记密码JS
 */

/** *******1,验证逻辑开始******** */
var phone_flg = true;
var send_msg = true;

var path = $("#contextPath").val();
$(function() {

	// 获取图形验证码
	$("#getCode").attr("src",
			path + "/register/getCodeImg.htm?timestemp=" + (new Date().getTime()));

	// 点击重新获取图形码
	$("#getCode").click(
			function() {
				$(this).attr(
						"src",
						path + "/register/getCodeImg.htm?timestemp="
								+ (new Date().getTime()));
			});

	// 完成
	$("#saveDiv").on("click", function() {

		var phone = $("#phone").val();
		var code =  $("#code").val();
		var messCode = $("#messCode").val();
		var pw = $("#pW").val().trim();
		var pWyes = $("#pWyes").val();
		
		if(phone == ""){
			$.prompt.message("请输入手机号码", $.prompt.msg);
			return false;
		}
		if(code == ""){
			$.prompt.message("请输入图形码", $.prompt.msg);
			return false;
		}
		if (messCode == "") {
			$.prompt.message("请输入短信验证码", $.prompt.msg);
			return false;
		} else {
			checkMessage();
			if(mess_flg=="1"){
				return false;
			}
		}
		if (pw == "") {
			$.prompt.message("请输入密码", $.prompt.msg);
			return false;
		} else if (pw.length < 6) {
			$.prompt.message("密码至少6位，请重新输入", $.prompt.msg);
			return false;
		} else if (pWyes == "") {
			$.prompt.message("请输入确认密码", $.prompt.msg);
			return false;
		} else if (pw != pWyes) {
			$.prompt.message("两次密码不一致，请重新输入", $.prompt.msg);
			return false;
		}else{

			// 密码MD5加密2次
			var oneMd5 = MD5Util(pw);
			var twoMd5 = MD5Util(oneMd5);
			 $.post("forget.htm",{"account":phone,"password" : twoMd5},function(rst){
						if (rst.data) {
							$.prompt.message("恭喜您，重置密码成功！", $.prompt.msg);
							setTimeout(function() {
								window.location.href = path + "/login.htm";
							}, 2000);
						}else{
							$.prompt.message("手机号尚未注册云境！", $.prompt.msg);
						}
				 
			 },'json');
		}
		
	});

	// 点击获取短信验证码
	$("#getMessCode").on("click", function() {
		var phone = $("#phone").val();
		var code = $("#code").val();

		if (phone == "") {
			$.prompt.message("请输入手机号码", $.prompt.msg);
		} else if (!(/^1\d{10}$/.test(phone))) {
			$.prompt.message("请输入正确的手机号码", $.prompt.msg);
		}else{
			if(code == ""){
				$.prompt.message("请输入图形码", $.prompt.msg);
				return false;
			}else{
				checkPhone();
				if(phone_flg){
					checkCode();
				}
			}
		}
	});


});

// 验证手机是否注册
var checkPhone = function() {

	var phone = $("#phone").val();

	$.get(path + "/register/isHavaPhone.htm", {
		"phone" : phone,
		"userType" : 0
	}, function(rst) {
		if (rst.status == "501") {
			$.prompt.message("手机号尚未注册", $.prompt.msg);
			phone_flg = false;
			return false;
		} else {
			phone_flg = true;
		}

	}, 'json');
};

// 验证图形验证码
var checkCode = function() {

	var code = $("#code").val();

	jsonAjax.asyncPost(path + "/register/checkCodeImg.htm", {
		"code" : code
	}, function(rst) {

		if (rst.status != 200) {
			$.prompt.message("图形码错误，请重新输入", $.prompt.msg);
			return false;
		} else {
			if (send_msg) {
				sendMessage();
			}
		}

	}, 'json');
};

// 验证短信码
var mess_flg = "0";
var checkMessage = function() {

	var phone = $("#phone").val();
	var messCode = $("#messCode").val();

	jsonAjax.asyncPost(path + "/register/checkMessage.htm", {
		"phone" : phone,
		"messageCode" : messCode
	}, function(rst) {

		if (rst.status != 200) {
			$.prompt.message("短信验证码错误，请重新输入", $.prompt.msg);
			mess_flg = "1";
		}else{
			mess_flg = "0";
		}

	}, 'json');
};

// 发送短信验证码
var sendMessage = function() {

	var phone = $("#phone").val();

	$.post(path + "/register/sendMessage.htm", {
		"phone" : phone
	}, function(rst) {

		if (rst.data.status != 500) {
			time("getMessCode");
		}

	}, 'json');
};
/** *******1,注册验证逻辑结束******** */


// 计时器
var wait = 60;
var time = function(_id) {

	var obj = $("#" + _id);
	if (wait == 0) {
		send_msg = true;
		obj.html("获取验证码");
		wait = 60;
		$("#getCode").attr("src",
				path + "/register/getCodeImg.htm?timestemp=" + (new Date().getTime()));
	} else {
		send_msg = false;
		obj.html("重新发送(" + wait + ")");
		wait--;
		setTimeout(function() {
			time(_id);
		}, 1000);
	}
};
