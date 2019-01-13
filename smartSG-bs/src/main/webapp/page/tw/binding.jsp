<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/settings.css"/>
<title>表格样式</title>
	<style>
		/*登录页面样式start*/
		.inputwrap{
			background: #fff;
			margin:15px 17px;
			width:90%;
			height: 49px;
			border: 1px solid #d0d0d0;
			position: relative;
		}
		.lable-login {
			position: absolute;
			top: 14px;
			left: 16px;
		}
		.inputwrap input{
			border: 0px;
			padding: 10px 15px 10px 45px;
			width: 100%;
			height: 100%;
			line-height: 28px;
			font-size: 16px;
			border: 0 none;
			box-sizing: border-box;
		}
		.icon-user{
			background-image: url(/smartSG-bs/images/login/icons.png);
			background-position: 0 center;
			display:inline-block;
			width: 18px;
			height: 20px;

		}
		.icon-password{
			background-image: url(/smartSG-bs/images/login/icons.png);
			background-position: -17px center;
			display:inline-block;
			width: 18px;
			height: 20px;

		}
	</style>
</head>
<body>
<div style="display: none;" alt="隐藏表单域">
</div>
<div style="height: 190px;">
	<div style="height: 40px;" align="center">
		<h2 style="padding-top:10px;">${type eq 'tw'?'绑定摊位账号':'绑定食阁账号'}</h2>
	</div>
	<div style="height: 150px;">
		<div class="inputwrap">
			<label class="lable-login"><i class="icon-user"></i></label>
			<input type="text" name="account" id="username" autocomplete="off" placeholder="手机号/登录账号（领导层账号）">
		</div>
		<div class="inputwrap">
			<label class="lable-login"><i class="icon-password"></i></label>
			<input onfocus="this.type='password'" name="password" id="password" autocomplete="off" maxlength="20" placeholder="密码（6-20位，区分大小写）">
		</div>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right"style="margin-right:90px;">
		<button class="submit-btn" id="binding_btn">绑<span class="wzkg"></span>定</button>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<div style="display: none;" alt="隐藏表单域">
	<input type="hidden" id="type" value="${type}">
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<script type="text/javascript" src="${ctx}/js/tw/binding.js"></script>
</body>
</html>