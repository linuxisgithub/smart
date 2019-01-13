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
<script type="text/javascript" src="${ctx }/js/forgetPass/forget.js"></script>
</head>
<body>
<div class="top">
	<div class="toplogo">
		<a href="javascript:;">
			<%--<c:choose>
			<c:when test="${param.level eq 'oa'}">
				<a href="javascript:;" class="toplogo_oa"><img src="${ctx}/images/login/oa_logo_b.png" ></a>
			</c:when>
			<c:when test="${param.level eq 'oa_plus'}">
				<a href="javascript:;"><img class="logo_img_plus" src="${ctx}/images/login/business_login_tiyan.png" ></a>
			</c:when>
			<c:when test="${param.level eq 'crm'}">
				<a href="javascript:;"><img src="${ctx}/images/login/crm_login_tiyan.png" ></a>
			</c:when>
			<c:otherwise>
				<a href="javascript:;"><img src="${ctx}/images/login/crm_login_tiyan.png" ></a>
			</c:otherwise>
		</c:choose>--%>
		</a>
	</div>
	<%--<div class="btn-login" id="btn-login">
		<a href="javascript:;" style="background-color: #3DA8F6;color: #fff">
		<img src="${ctx}/images/login/login_icon.png" style="position: relative;top: 3px;left: -3px;">体验登录</a>
	</div>--%>
	<div class="btn-login" id="btn-login">
		<a href="javascript:;">登录</a>
	</div>
</div>
<!-- 主体内容start -->
<div class="wraper" style="display: none;">
	<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
		<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
	<div class="title">忘记密码</div>
		<div class="forgetpass-input">
			<input type="text" id="phone" placeholder="请输入手机号码">
		</div>			
		<div class="code-div">
			<div class="code-input-wrap">
				<input type="text" id="code" placeholder="请输入图形码">
				<div class="img-code"><img id="getCode" src="" alt=""></div>
			</div>
			<div class="authcode-btn">
				<button id="getMessCode">获取短信验证码</button>
			</div>
		</div>
		<div class="authcode">
			<input type="text" id="messCode" placeholder="请输入短信验证码">
		</div>
		<div class="password">
			<input type="password" id="pW" placeholder="请输入新密码">
		</div>
		<div class="password">
			<input type="password" id="pWyes" placeholder="请输入确认密码">
		</div>
		<div class="submitwrap" id="saveDiv">
			<a href="javascript:;">完 成</a>
		</div>
	
</div>
<!-- 主体内容end -->
<div class="footer">
	<p>Copyright © 2018 广州超享网络技术有限公司 | 粤ICP备120215号-12</p>
</div>
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
	$("#btn-login").click(function() {
		window.location.href = "${ctx}/login.htm";
	});
	setWraperHeight();
});
</script>
</body>
</html>