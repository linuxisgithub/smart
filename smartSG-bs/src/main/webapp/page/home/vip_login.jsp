<%@page import="com.system.model.LoginUser"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>系统设置登录</title>
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

.xufei{
 border-radius: 39px;
color:#fff; 
 border: 1px solid #fff;
 padding: 4px 22px;
 background-color: #fff;
 cursor: pointer;
}
</style>
<body>
	<div class="top" >
		<div class="toplogo">
			<a href="javascript:;"><img id="logoImg" src="${ctx}/images/login/crm.png" ></a>
		</div>
		<div class="company">
			<span style="border: 1px solid #fff;border-radius:4px;padding: 9px 15px;" id="key_comName">
				广州超享网络技术有限公司
			</span>
		</div>
		<div class="time">
			到期时间 &nbsp;: &nbsp;<span style="margin-right: 15px;" id="key_time">2017-09-18</span>
			<span class="xufei">
				续费
			</span>
			
		</div>
	</div>
	<!-- 主体内容start -->
	<div class="wraper">
		<div class="title" style="margin-top: 40px;">系统设置登录</div>
		<form action="" id="mainForm" method="post">
			<div class="inputwrap">
				<label class="lable-login"><i class="icon-user"></i></label>
				<input type="text" name="account" id="username" value="系统管理员"  disabled="disabled">
			</div>
			<div class="inputwrap">
				<label class="lable-login"><i class="icon-password"></i></label>
				<input type="password" name="password" id="password" maxlength="20" placeholder="超级密码（6-20位，区分大小写）">
			</div>
			<div class="submitwrap">
				<a href="javascript:;">登 录</a>
			</div>
		</form>
		<div style="font-size: 16px; margin-top: 41px;margin-left: 5px;"><strong>特别提示&nbsp;&nbsp;:&nbsp;&nbsp;</strong>超级用户请在PC端&nbsp;USB接口插入《超级用户锁》&nbsp;!</div>		
	</div>
	<!-- 主体内容end -->
	<div class="footer" style="margin-top: 120px;">
		<p>Copyright © 2017 广州超享网络技术有限公司 | 粤ICP备120215号-12</p>
	</div>
	<div style="display: none;">
		<object type="application/x-rockey3plugin" id="ROCKEY3"></object>
  </div>
  <input id="keyHardId" type="hidden">
  <input id="keycompanyId" type="hidden">
  <input id="rul" type="hidden" value="${param.toLoad}">
</body>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<script type="text/javascript">
var background_color;
var logoImgSrc;
 $(document).ready(function(){
	try{
		 var hardNo = R3FindInfo();
		 
		 if(hardNo !='' && hardNo !=null){
				$("#keyHardId").val(hardNo);
				
				var url = "${ctx}/getRoKeyData.htm";
				$.ajax({
					'url' : url,
					'type' : "GET",	
					'async' : true,
					'dataType' : 'json',
					'data': {hardNo:hardNo},
					'success' : function(rst) {
						var key_comName =  rst.data.key_comName;
						var key_time = rst.data.key_time;
						var key_companyId = rst.data.key_companyId;
						var dnsAddress = rst.data.dnsAddress;
						//1 蓝：#3da8f6；2 绿：#439015；3 橙：#f97f09；4 灰：#7b7b7b；5 深蓝：#0771ae；
						var theme = rst.data.theme;
						var level = rst.data.level;
						
						var is = $("#rul").val();
						if(is != "1"){
							window.location.href= "http://"+dnsAddress+"/vipLogin.htm?toLoad=1";
						}
						
						if(level == 1){
							logoImgSrc ="${ctx}/images/login/oa_logo.png";
						}else if(level ==2){
							logoImgSrc ="${ctx}/images/login/siness_oa.png";
						}else if(level ==3){
							logoImgSrc = "${ctx}/images/login/crm.png";
						}else{
							logoImgSrc = "${ctx}/images/login/crm.png";
						}
						
						$("#logoImg").attr("src",logoImgSrc);
						
						if(theme == 1){
							background_color = "#3da8f6";
						}else if(theme == 2){
							background_color ="#439015";
						}else if(theme == 3){
							background_color = "#f97f09";
						}else if(theme == 4){
							background_color = "#7b7b7b";
						}else if(theme == 5){
							background_color = "#0771ae";
						}
						
						$(".submitwrap a").css("background-color",background_color);
						$(".top").css("background-color",background_color);
						$(".xufei").css({"color":background_color});
						
						$("#key_comName").text(key_comName);
						
						document.title = key_comName;
						
						$("#key_time").text(key_time);
						$("#keycompanyId").val(key_companyId);
						
					},
					'error' : function() {
						alert("服务器异常！");
					}
				});
				
			}
	}catch (e) {
		$.prompt.message("此浏览器不兼容超级用户锁，请使用IE浏览器登录", $.prompt.msg);
		return;
	}
	 
	 $(".submitwrap").click(function(){
		try{
			 var hard =	R3FindInfo();
			 if(hard == undefined){
				 $.prompt.message("请插入超级用户锁！", $.prompt.msg);
					return;
			 }
		}catch(e){
			$.prompt.message("此浏览器不兼容超级用户锁，请使用IE浏览器登录", $.prompt.msg);
			return;
		}
		 
		 var password = $('#password').val();
			if(password == null || password == ""){
				//提示错误
				$.prompt.message("密码不能为空！", $.prompt.msg);
				return false;
		}
			
			var keycompanyId = $("#keycompanyId").val();
		
			$.ajax({
				'url' : "${ctx}/superLogin.htm",
				'type' : "post",
				'async' : true,
				'data' : {eid: keycompanyId,adminPassword: MD5Util(MD5Util($("#password").val()))},
				'dataType' : 'json',
				'beforeSend': function(xmlHttp) {
					xmlHttp.loadIndex = layer.load();
				},
				'success' : function(response, statusText) {
					if(response.status == 200) {
						if(response.data.result == 0) {
							window.location.href = "${ctx}/admin/system/settings.htm";
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
 * 获取加密锁硬件序列号
 */
function R3FindInfo()
{
	//获取加密锁对象
	var R3 = document.getElementById("ROCKEY3");
    var ret;

    ret = R3.SD_FindInfo();
    //有锁
    if( ret == 0 )
    {
    	return R3.HardID;
    }else
    {
    }
    return;
}


</script>
</html>
</body>
</html>