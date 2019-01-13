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
</head>
<style>
.company {
    width: 300px;
    color: #FFF;
    height: 60px;
    font-size: 16px;
    line-height: 60px;
    margin: 0 auto;
    text-align: center;
}
.time {
    color: #FFF;
    font-size: 16px;
    line-height: 60px;
    float: right;
    position: relative;
    top: -58px;
    margin-right:50px;
}
</style>
<body>
	<div class="top" style="background-color: #3DA8F6;">
		<div class="toplogo">
					<%--<a href="javascript:;" class="toplogo_oa"><img src="${ctx}/images/smartSG_logo.jpg" ></a>--%>
		</div>
		<div class="company">
			<span style="border: 1px solid #fff;border-radius:4px;padding: 9px 15px;" id="key_comName">
				smartSG
			</span>
		</div>
		<%--<div class="time">
			到期时间：<span style="margin-right: 15px;" id="key_time">${company.expireTime}</span>
			<span style="border-radius: 39px;color:#3DA8F6;  border: 1px solid #fff;padding: 4px 22px;background-color: #fff;cursor: pointer;" id="keepVip">
				续费
			</span>
		</div>--%>
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
		</form>
		<%--<div style="font-size: 16px; margin-top: 41px;margin-left: 10px;"><strong>特别提示&nbsp;&nbsp;:&nbsp;&nbsp;</strong>超级用户请在PC端&nbsp;USB接口插入超级用户锁&nbsp;!</div>--%>
	</div>
	<!-- 主体内容end -->
	<div class="footer">
		<p>Copyright © 2018 广州超享网络技术有限公司 | 粤ICP备120215号-12</p>
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
$(function() {
	setWraperHeight();
	$('#icon-check').click(function(){
		if($(this).hasClass('icon-checked')){
			$(this).removeClass('icon-checked');
			$("input[name='rememberMe']").prop("checked", false);
		}else{
			$(this).addClass('icon-checked');
			$("input[name='rememberMe']").prop("checked", true);
		}
		
	});
	$('#icon-check').addClass('icon-checked');
	$("input[name='rememberMe']").prop("checked", true);
	$('#username').focus();
	$("#forgetPass").click(function() {
		window.location.href = "${ctx}/forgetPass.htm";
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
							window.location.href = "${ctx}/index.htm";
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
</script>
</html>
</body>
</html>