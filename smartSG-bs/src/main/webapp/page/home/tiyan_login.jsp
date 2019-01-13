<%@page import="com.system.model.LoginUser"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	Object object = session.getAttribute("USER_IN_SESSION");
	if(object != null && object instanceof LoginUser) {
		//已经登录了，直接跳转主页
		response.sendRedirect(request.getContextPath() + "/index.htm");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>
smartSG
</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/login/registerCSS.css">
<style type="text/css">
#newBridge {
	display: none !important;
}
</style>
</head>
<body>
	<div class="top">
		<div class="toplogo">
			<a href="javascript:;">
				<%-- <img src="${ctx}/images/login/logo-login.png" alt="云境工作平台"> --%>
					<a href="javascript:;" class="toplogo_oa">
						<img src="${ctx}/images/smartSG_logo.jpg" alt="smartSG">
					</a>
			</a>
		</div>
		<div class="btn-login" id="btn-login" style="margin-top: 6px;">
			<span href="javascript:;" style="color: #777;font-size: 18px;">
			体验版，功能限制
			<span>
		</div>
	</div>
	<!-- 主体内容start -->
	<div class="wraper" style="display: none;">
		<div class="title">用户登录</div>
		<form action="" id="mainForm" method="post">
			<div class="inputwrap">
				<label class="lable-login"><i class="icon-user"></i></label>
				<input type="text" name="account" id="username" placeholder="手机号/登录账号">
			</div>
			<div class="inputwrap">
				<label class="lable-login"><i class="icon-password"></i></label>
				<input type="password" name="password" id="password" maxlength="20" placeholder="密码（6-20位，区分大小写）">
			</div>
			<div class="textDiv">
				<input class="login-checbox" type="checkbox" name="rememberMe" id="rememberMe">
				<label for="saveAccount"><span class="icon-check" id="icon-check"></span>下次自动登录</label>
				<span class="text-right" id="forgetPass"><a href="javascript:;">忘记密码？</a></span>
			</div>
			<div class="submitwrap">
				<a href="javascript:;">登 录</a>
			</div>
			<div class="tiplink" id="toRegister">
				<c:if test="${localProject ne 'true'}">
				<a href="javascript:;">没有帐号，免费注册<i class="icon-arrow"></i></a>
				</c:if>
			</div>		
			<!-- <div id="toRegister" style="margin-top: 20px;text-align: center;">
				<a href="javascript:;" style="color: #3da8f5;text-decoration: none;">没有账号，免费注册 →</a>
			</div> -->
		</form>
				
	</div>
	<!-- 主体内容end -->
	<div class="footer">
		<p>Copyright © 2017 广州超享网络技术有限公司 | 粤ICP备120215号-12</p>
	</div>
</body>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<script type="text/javascript">
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
$(document).ready(function(){
	setWraperHeight();
	bdSDK();
	
	var level = "${level}" == "" ? "${param.level}" : "${level}";
	if(level == "") {
		level = "yx";
	}
	$('#icon-check').click(function(){
		if($(this).hasClass('icon-checked')){
			$(this).removeClass('icon-checked');
			$("input[name='']").prop("checked", false);
		}else{
			$(this).addClass('icon-checked');
			$("input[name='rememberMe']").prop("checked", true);
		}
		
	});
	$('#icon-check').addClass('icon-checked');
	$("input[name='rememberMe']").prop("checked", true);
	$('#username').focus();
	$("#toRegister").click(function() {
		window.location.href = "${ctx}/register.htm?level=" + level;
	});
	$("#forgetPass").click(function() {
		window.location.href = "${ctx}/forgetPass.htm?level=" + level;
	});
	//$('#username').blur(checkName);
	//$('#password').blur(checkPassword);
	$("#username").keydown(function(event){
	    if(event.keyCode == 13){
	        event.preventDefault();
	        //触发登录按钮
	        $(".submitwrap").trigger("click");
	    }
	});
	$("#password").keydown(function(event){
	    if(event.keyCode==13){
	        event.preventDefault();	
	        //触发登录按钮
	        $(".submitwrap").trigger("click");
	    }
	});
	function checkName(){
		var name = $('#username').val();
		if(name == null || name == ""){
			//提示错误
			$.prompt.message("手机号或登录账号不能为空！", $.prompt.msg);
			return false;
		}
		return true;
	}
	function checkPassword(){
		var password = $('#password').val();
		if(password == null || password == ""){
			//提示错误
			$.prompt.message("密码不能为空！", $.prompt.msg);
			return false;
		}
		return true;
	}
	$(".submitwrap").click(function() {
		var name = $('#username').val();
		if(name == null || name == ""){
			//提示错误
			$.prompt.message("手机号或登录账号不能为空！", $.prompt.msg);
			return false;
		}
		var password = $('#password').val();
		if(password == null || password == ""){
			//提示错误
			$.prompt.message("密码不能为空！", $.prompt.msg);
			return false;
		}
		var rememberMe = $("input[name='rememberMe']").is(":checked");
		
		$.ajax({
			'url' : "${ctx}/login.htm",
			'type' : "post",
			'async' : true,
			'data' : {rememberMe: rememberMe, account: $("#username").val(),password: MD5Util(MD5Util($("#password").val()))},
			'dataType' : 'json',
			'beforeSend': function(xmlHttp) {
				xmlHttp.loadIndex = layer.load();
			},
			'success' : function(response, statusText) {
				if(response.status == 200) {
					if(response.data.result == 0) {
						if(response.data.only_im == 1) {
							//window.location.href = "${ctx}/im/index.htm";
							window.location.href = "${ctx}/index.htm";
						} else {
							window.location.href = "${ctx}/index.htm";
						}
					} else {
						$.prompt.message("登录失败：" + response.data.msg, $.prompt.msg);
					}
				} else {
					$.prompt.message("登录失败：网络繁忙，请稍候", $.prompt.msg);
				}
			},
			'error' : function(xmlHttp, e1, e2) {
				$.prompt.message("登录失败：网络繁忙，请稍候", $.prompt.msg);
			},
			'complete': function(xmlHttp, textStatus) {
				layer.close(xmlHttp.loadIndex);
			}
		});
	});
});
/**
 * 百度SDK
 */
function bdSDK() {
	var url = window.location.href;
	if(url.indexOf("from=register") != -1) {
		var _hmt = _hmt || [];
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?8a66704b45d0be82a79a23917e398782";
		var s = document.getElementsByTagName("script")[0]; 
		s.parentNode.insertBefore(hm, s);
	}
}
</script>
</html>