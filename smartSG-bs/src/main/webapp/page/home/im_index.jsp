<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
<c:choose>
	<c:when test="${USER_IN_SESSION.company.isSpec eq 1 }">城南IMOA</c:when>
	<c:otherwise>云境</c:otherwise>
</c:choose>
</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/indexCSS.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/notice.css"/>
</head>
<body style="overflow-x: hidden;">
<div style="display: none;">
		<object type="application/x-rockey3plugin" id="ROCKEY3"></object>
		<input id="ishaveKey" type="hidden">
		<input id="isHaveDrive" type="hidden">
</div>
<div class="container">
	<!-- 头部开始 -->
	<div class="header">
		<div class="logo">
			<c:choose>
				<c:when test="${USER_IN_SESSION.company.isSpec eq 1}">
					<a href="javascript:;"><img src="${ctx}/images/index/cn_logo.png" 
					style="height: 40px;width: 90px;position: relative;top: 10px;left: 25px;"></a>
				</c:when>
				<c:when test="${USER_IN_SESSION.company.level eq 1}">
					<a href="javascript:;"><img src="${ctx}/images/index/oa_logo.png" ></a>
				</c:when>
				<c:when test="${USER_IN_SESSION.company.level eq 2}">
					<a href="javascript:;"><img src="${ctx}/images/index/buss_logo.png" ></a>
				</c:when>
				<c:when test="${USER_IN_SESSION.company.level eq 3}">
					<a href="javascript:;"><img src="${ctx}/images/index/crm_logo.png" ></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:;"><img src="${ctx}/images/index/crm_logo.png" ></a>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="Hleft">
		    <div class="header-man-img" id="index_userIcon"><img class="icon" id="loginicon" src=""></div>
		    <div class="header-man-name" id="index_userInfo">${USER_IN_SESSION.user.name}</div>
			<span class="sanjiao-icon" id="index_userInfo2"></span>
		</div>
		<div class="company">
			<span style="border: 1px solid #fff;border-radius:4px;padding: 5px 8px;">
				${USER_IN_SESSION.company.name}
			</span>
		</div>
		<div class="Hright">
			<ul>
				<!-- <li id="toCheck" data-mid="110"><a href="javascript:void(0);" class="header-time" title="考勤"></a></li>
				<li><a href="javascript:void(0);" class="header-tool" title="工具"></a></li>
				<li id="toSearch"><a href="javascript:void(0);" class="header-search" title="搜索"></a></li>
				<li><div class="header-line"></div></li>
				<li id="toSuper"><a href="javascript:void(0);" class="header-administrator" title="超级用户"></a></li>
				<li><div class="header-line"></div></li> -->
				<li id="logout"><a href="javascript:void(0);" class="header-exit" title="退出系统" style="float: right;margin:18px 18px 0px 22px;"></a></li>
			</ul>
		</div>
           <div class="clear"></div>    
	    </div>		
	<!-- 头部结束 -->

	<!-- 主体内容开始 -->
	<div class="mainContainer" id="mainContainer">
		<div class="mainContainerWrapper" style="border-left:0px;width: 100%">
			<!--叮当享界面开始-->
            <div class="mainRight" id="mainDDX" style="overflow: auto;margin: 18px 0px 0px 0px;">
                <!-- 包含叮当享jsp页面 -->
                <c:if test="${USER_IN_SESSION.user.imStatus eq 1}">
                <%@ include file="/page/chat/lyl.jsp" %>
                </c:if>
            </div>
            <!--叮当享界面开始-->
		</div>
	</div>
	<!-- 主体内容结束 -->
</div>
<div style="display: none;">
	<input type="hidden" id="caseStatus" value="${USER_IN_SESSION.company.caseStatus}">
	<input type="hidden" id="only_im" value="1">
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<script type="text/javascript">
var userType = "${USER_IN_SESSION.user.userType}";
var caseStatus = "${USER_IN_SESSION.company.caseStatus}";
var needLead = "${USER_IN_SESSION.company.needLead}";
var imStatus = "${USER_IN_SESSION.user.imStatus}";
var icon = "${USER_IN_SESSION.user.icon}";
var path = "${ctx}";

$(function() {
	if(icon == null || icon == ""){
		icon = "${ctx}/images/index/default_man_small.png";
	}
	$("#loginicon").attr("src",icon);
	
	
	$("#index_userIcon").click(function() {
		toUserIcon();
	});
	$("#index_userInfo").click(function() {
		toUserInfo();
	});
	$("#index_userInfo2").click(function() {
		toUserInfo();
	});
	function toUserInfo() {
		var url = "${ctx}/sysUser/self/info.htm?eid=${USER_IN_SESSION.user.eid}";
		parent.$.prompt.layerUrl2({url: url, width: 550,height:340});
	}
	function toUserIcon() {
		var url = "${ctx}/sysUser/self/userICon.htm?eid=${USER_IN_SESSION.user.eid}";
		parent.$.prompt.layerUrl2({url: url, width: 600,height:450});
	}
	index_config.initHeight();
	$("#logout").click(function() {
		index_config.logout();
	});
	
});
function updateIcon(icon){
	$("#loginicon").attr("src",icon);
}

function refreshContent() {
	$("#content_iframe").attr("src", $("#content_iframe").attr("src"));
}
function refreshIframeContent() {
	var url = parent.$("#content_iframe").contents().find("#super_iframe").attr("src");
	parent.$("#content_iframe").contents().find("#super_iframe").attr("src", url);
}
function setContentUrl(url) {
	$("#content_iframe").attr("src", url);
}
function refreshIframePagination() {
	content_iframe.window.refreshList();
}
var ddx_middle_chat_H;
var index_config = {
	min_height : 650,
	top_height : 60,
	initHeight: function() {
		index_config.resetSize();
		$(window).resize(function() {
			index_config.resetSize();
		});
	},
	resetSize : function() {
		setTimeout(function() {
			var height = $(window).height();
			if(height < index_config.min_height) {
				height = index_config.min_height;
	    	}
			var main_height = height - index_config.top_height;
			$("#mainLeft").css("height", main_height + "px");
			
			var mainRight_margin = 18;
			$("#mainRight").css("height", (main_height - mainRight_margin) + "px");
			$("#content_iframe").css("height", (main_height - mainRight_margin - 40) + "px");
			
			$("#mainDDX").css("height", (main_height - mainRight_margin - 10) + "px");
	    	height = height - index_config.top_height - 20;
	    	var ddx_content_H = height - 20 - 10;
	    	$(".ddx_content").css("height", ddx_content_H + "px"); 
	    	height = height - 56 - 70;
	    	var ddx_middle_H = height - 30;
	    	$(".ddx_middle").css("height", ddx_middle_H + "px");
	    	
	    	var ddx_middle_txl_H = ddx_middle_H + 70;
	    	$(".ddx_middle_txl").css("height", ddx_middle_txl_H + "px");
	    	ddx_middle_chat_H = ddx_middle_H - 21;
	    	$(".ddx_middle_chat").css("height", ddx_middle_chat_H + "px");
		}, 100);
	},
	
	logout: function() {
		var isVip = "${sessionScope.USER_IN_SESSION.company.isVip}";
		var url = "${ctx}/logout.htm";
		if(isVip == 0) {
			var level = "${USER_IN_SESSION.company.level}";
			if(level == 1) {
				url += "?level=oa";
			} else if(level == 2) {
				url += "?level=oa_plus";
			} else {
				url += "?level=crm";
			}
			
		}
		window.location.href = url;
	}
}

function appendParam(url, param_name, param_value) {
	var _url = url;
	var index = _url.indexOf("?");
	if(index != -1) {
		_url = _url + "&" + param_name + "=" + param_value;
	} else {
		_url = _url + "?" + param_name + "=" + param_value;
	}
	return _url;
}
</script>
</body>
</html>