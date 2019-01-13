<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>云境</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/login/registerCSS.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
</head>
<body>
<div class="top">
	<div class="toplogo">
		<c:choose>
			<c:when test="${param.level eq 'yx'}">
				<a href="javascript:;" class="toplogo_oa">
					<img src="${ctx}/images/login/new_login/yx.png" >
				</a>
			</c:when>
			<c:when test="${param.level eq 'xs'}">
				<a href="javascript:;" class="toplogo_oa">
					<img src="${ctx}/images/login/new_login/xs.png" >
				</a>
			</c:when>
			<c:when test="${param.level eq 'yj'}">
				<a href="javascript:;" class="toplogo_oa">
					<img src="${ctx}/images/login/new_login/yj.png" >
				</a>
			</c:when>
			<c:otherwise>
				<a href="javascript:;" class="toplogo_oa">
					<img src="${ctx}/images/login/new_login/yx.png" >
				</a>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="btn-login" id="btn-login">
		<a href="javascript:;" style="background-color: #3DA8F6;color: #fff">
		<img src="${ctx}/images/login/login_icon.png" style="position: relative;top: 3px;left: -3px;">体验登录</a>
	</div>
</div>
<!-- 主体内容start -->
<div class="wraper" style="display: none;">
	<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
		<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
	<div class="title">注册账号</div>
		<div class="company-input">
			<input type="text" placeholder="请输入企业名称"  id="comName" >
		</div>
		<div class="cellphone-input">
			<span class="cellphone-area">+86</span>		
			<input type="text" placeholder="请输入手机号码" id="phone" maxlength="11" >
		</div>
		<div class="code-div">
			<div class="code-input-wrap">
				<input type="text" placeholder="请输入验证码" id="code" maxlength="4">
				<div class="img-code"><img id="getCode" alt=""></div>
			</div>
			<div class="authcode-btn">
				<button id="getMessCode">获取短信验证码</button>
			</div>
		</div>
		<div class="authcode">
			<input type="text" placeholder="请输入短信验证码" maxlength="6" id="messCode">
		</div>
		<div class="password">
			<input type="password" placeholder="请输入密码" maxlength="12" id="pass" >
		</div>
		<div class="submitwrap">
			<a href="javascript:;" id="submitF">注 册</a>
		</div>
	<input type="hidden" value="${ctx }" id="path">
	<form  id="mainForm">
		<input name="password" id="form_pass" type="hidden">
		<input name="name" id="form_comName" type="hidden">
		<input name="telephone" id="form_phone" type="hidden">
		<c:choose>
			<c:when test="${param.level eq 'yj'}">
				<input name="level" id="level" type="hidden" value="3">
			</c:when>
			<c:when test="${param.level eq 'xs'}">
				<input name="level" id="level" type="hidden" value="2">
			</c:when>
			<c:when test="${param.level eq 'yx'}">
				<input name="level" id="level" type="hidden" value="1">
			</c:when>
			<c:otherwise>
				<input name="level" id="level" type="hidden" value="1">
			</c:otherwise>
		</c:choose>
	</form>
</div>
<!-- 主体内容end -->
<div class="footer">
	<p>Copyright © 2017 广州超享网络技术有限公司 | 粤ICP备120215号-12</p>
</div>
<script type="text/javascript">
var send_msg = true;
var sendPhone;
var wait = 60;
var msgSt;
var timeSt;
var path = $("#contextPath").val();
function setWraperHeight() {
	var win_height = $(window).height();
	if(win_height < 500) {
		win_height = 500;
	}
	var wraper_height = $(".wraper").height();
	var top = (win_height - 0 - wraper_height) / 2;
	$(".wraper").css("top", top + "px");
	$(".wraper").show();
}
var level = "${param.level}";
if(level == "") {
	level = "yx";
}
$(function() {
	setWraperHeight();
	// 获取图形验证码
	$("#getCode").attr("src",path + "/register/getCodeImg.htm?timestemp="+ (new Date().getTime()));

	// 点击重新获取图形码
	$("#getCode").click(function() {
		$(this).attr("src",path + "/register/getCodeImg.htm?timestemp="+ (new Date().getTime()));
	});
	
	// 点击获取短信验证码
	$("#getMessCode").on("click", function() {

		var phone = $("#phone").val();
		var code = $("#code").val();
		var comName = $("#comName").val();

		if(comName == ""){
			$.prompt.message("企业名称不能为空！", $.prompt.msg);		
		}else if (phone == "") {
			$.prompt.message("手机号码不能为空！", $.prompt.msg);
		} else if (isNaN(Number(phone))){  
			$.prompt.message("手机号码只能输入数字", $.prompt.msg);
		} else if(phone.length < 11){
			$.prompt.message("手机号码必须位11位数字", $.prompt.msg);
		}else {
			if (code == "") {
				$.prompt.message("验证码不能为空！", $.prompt.msg);
				return false;
			} else {
				checkCode();
			}
		}
	});
	
	//注册按钮点击
	$("#submitF").click(function(){
		doFinish();
	});
	$("#btn-login").click(function() {
		window.location.href = "${ctx}/login.htm?level=" + level;
	});
});

//验证图形验证码
var checkCode = function() {

	var code = $("#code").val();
	$.post(path + "/register/checkCodeImg.htm", {
		"code" : code
	}, function(rst) {
		if (rst.status != 200) {
			$.prompt.message("图形码错误！", $.prompt.msg);
			return false;
		} else {
			if (send_msg) {
				sendMessage();
			}
		}

	}, 'json');
};

//发送短信验证码
var sendMessage = function() {

	var phone = $("#phone").val();

	$.post(path + "/register/sendMessage.htm", {
		"phone" : phone
	}, function(rst) {
		if (rst.data.status != 500) {
			$.prompt.message("短信验证码已发送", $.prompt.msg);
			if(rst.data.showMsg && rst.data.showMsg == 1) {
				$.prompt.message(rst.data.messageCode, $.prompt.msg);
				$("#messCode").val(rst.data.messageCode);
			}
			timeSt = 'true';
			time("getMessCode");
		}

	}, 'json');
};


//计时器
var time = function(_id) {

	if (timeSt != 'false') {
		var obj = $("#" + _id);
		if (wait == 0) {
			send_msg = true;
			obj.html("获取验证码");
			obj.removeAttr("disabled");
			wait = 60;
		} else {
			send_msg = false;
			obj.html("重新发送(" + wait + ")");
			obj.attr("disabled","disabled");
			wait--;
			setTimeout(function() {
				time(_id);
			}, 1000);
		}
	}

};

//验证短信码和手机号
var doFinish= function() {

	var phone = $("#phone").val();
	var messCode = $("#messCode").val();
	var pass = $("#pass").val();
	var comName = $("#comName").val();

	if (messCode == "") {
		$.prompt.message("短信验证码不能为空！", $.prompt.msg);
		return false;
	} else if (phone == "") {
		$.prompt.message("手机号码不能为空！", $.prompt.msg);
		return false;
	}else if(pass == ""){
		$.prompt.message("密码不能为空！", $.prompt.msg);
		return false;
	}

	$.post(path + "/register/checkMsgAndPhone.htm", {
		"phone" : phone,
		"messageCode" : messCode
	}, function(rst) {
		//短信验证码错误
		if (rst.status == 508) {
			$.prompt.message("短信验证码错误！", $.prompt.msg);

		} else if (rst.status == 502) {
			//手机号码已注册
			$.prompt.message("手机号码已注册，请直接登录！", $.prompt.msg);

			$("#code").val("");
			$("#phone").val("");
			$("#pass").val("");
			$("#messCode").val("");
			$("#getCode").attr(
					"src",
					path + "/register/getCodeImg.htm?timestemp="
							+ (new Date().getTime()));
			send_msg = true;
			$("#getMessCode").html("获取短信验证码");
			$("#getMessCode").removeAttr("disabled");
			timeSt = 'false';
			wait = 60;
		} else {
			
			// 设置表单密码
			var oneMd5 = MD5Util(pass);
			var twoMd5 = MD5Util(oneMd5);

			$("#form_pass").val(twoMd5);
			$("#form_comName").val(comName);
			$("#form_phone").val(phone);

			$.post(path + "/register/registerCompany.htm", $("#mainForm")
					.serialize(), function(data) {
				if (data.status == 200) {
					
					$.prompt.message("恭喜您，注册成功！  正在跳转登录页面...", $.prompt.msg,3000);
					
					setTimeout(function() {
						window.location.href = "${ctx}/login.htm?level=" + level + "&from=register";
					}, 3000);
				}else{
					$.prompt.message("注册失败！");
				}
			}, 'json');
			
		}

	}, 'json');

};
</script>
</body>
</html>