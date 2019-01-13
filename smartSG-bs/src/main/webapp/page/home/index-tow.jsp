<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>smartSG 二级菜单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/indexCSS.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/notice.css"/>
<style type="text/css">
.logo_plus_crm {
     left: -30px;
     top: 0px;
}

</style>
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
		<div class="logo" id="logo" style="width: 148px;padding-left: 50px;">
		    <img src="${ctx}/images/smartSG_logo.jpg" style="padding-top: 7px;" >
		</div>
		<div class="Hleft">
		    <div class="header-man-img" id="index_userIcon"><img class="icon" id="loginicon" src=""></div>
		    <div class="header-man-name" id="index_userInfo">${USER_IN_SESSION.user.name}</div>
			<span class="sanjiao-icon" id="index_userInfo2"></span>
		</div>
		<div class="company">
			<span style="border-radius:4px;padding: 5px 8px;font-size: 24px;">
				${USER_IN_SESSION.company.name}
			</span>
		</div>
		<div class="Hright">
			<ul style="margin-left: 55px;">
				<%--<li><div class="header-line"></div></li>--%>
				<li id="toSuper"><a href="javascript:void(0);" class="header-administrator" title="超级用户"></a></li>
				<li><div class="header-line"></div></li>
				<li id="logout"><a href="javascript:void(0);" class="header-exit" title="退出系统" ></a></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 头部结束 -->

	<!-- 主体内容开始 -->
	<div class="mainContainer" id="mainContainer">
		<div class="mainContainerWrapper">
			<!-- 左边导航栏开始 -->
			<div class="mainLeft" id="mainLeft">
				<%--div class="mainLeft-menu" id="mainLeft-menu_1">
					<div class="menu-icon"><img src="${ctx}/images/index/inform_icon.png"></div>
					<div class="menu-txt">测试<span style="font-weight: bold;">· </span>one</div>
				</div>--%>
			</div>
			<!-- 左边导航栏结束 -->
			<!-- 右边内容开始 -->
			<div class="mainRight" id="mainRight">
				<div class="mainRight-tab-nav">
					<ul id="sec_menu">
						<%--<li id="100" class="active">工作台</li>
						<li >部门通知</li>
						<li>文件中心</li>
						<li style="margin-left: 27px;">批审</li>--%>
					</ul>
					 <div class="mainRight-tab-btn">
						<div class="mainRight-tab-btn1" style="text-align: center;" id="mainDDX_btn">
							<a href="javascript:void(0);">即时通讯</a>
							<div class="im-redDot" id="header_DDX_RedDot"></div>
						</div>
							<div class="mainRight-tab-btn2" style="text-align: center;" id="mainHelp_btn">
								<a href="javascript:void(0);">帮助
									<div class="im-redDot" id="header_Help__RedDot"></div>
								</a>
							</div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="mainRight-tab-con">
					<iframe id="content_iframe" name="content_iframe" src="" style="width:100%;height:550px; border: 0px;"></iframe>
				</div>
			</div>
			<!-- 右边内容开始 -->

			<!--叮当享界面开始-->
            <div class="mainRight" id="mainDDX" style="
            display: none;overflow: auto;">
                <!-- 包含叮当享jsp页面 -->
                <%@ include file="/page/chat/lyl.jsp" %>
                 <%--<c:if test="${USER_IN_SESSION.user.imStatus eq 1}">
                </c:if>--%>
            </div>
            <!--叮当享界面结束-->

			<!--帮助页面开始-->
			<div class="mainRight" id="mainHelp" style="
            display: none;overflow: auto;">
				<!-- 包含帮助jsp页面 -->
				<%@ include file="/page/platformManagement/help.jsp" %>
			</div>
			<!--帮助页面结束-->
		</div>
	</div>
	<!-- 主体内容结束 -->
</div>
<div style="display: none;">

</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<script type="text/javascript" src="${ctx}/js/flash_title.js"></script>
<script type="text/javascript" src="${ctx}/chat/js/ddx_gd/jquery.nicescroll.js"></script>
<script type="text/javascript">
/**滚动条**/
var index_niceScroll = function(selector){
	selector.niceScroll({
		cursorcolor:"#bbbcbe",
		cursoropacitymax:1,
		touchbehavior:false,
		cursorwidth:"6px",
		cursorborder:"0",
		cursorborderradius:"5px"
	});
}
var userType = "${USER_IN_SESSION.user.userType}";
var imStatus = "${USER_IN_SESSION.user.imStatus}";
var icon = "${USER_IN_SESSION.user.icon}";
var companyId = "${USER_IN_SESSION.company.eid}";
var path = "${ctx}";
var currIsHome = 1;
var createMsgNum = 0;
$(function() {
	if(icon == null || icon == ""){
		icon = "${ctx}/images/index/default_man_small.png";
	}
	$("#loginicon").attr("src",icon);
	
	$("#mainDDX_btn").click(function() {
		if(imStatus == 1) {
			setCurrIsHome(0);
			$(".mainLeft-menu").removeClass("active");
			$("#mainRight").hide();
			$("#mainDDX").show();
		} else {
			$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
		}
	});
	$("#mainHelp_btn").click(function() {
		$(".mainLeft-menu").removeClass("active");
		$("#mainRight").hide();
		$("#mainHelp").show();
	});
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
	index_config.initMenu();
	index_config.initHeight();
	index_config.initTopMenu();
    index_niceScroll($("#mainLeft"));//优化滚动条
	$("#logout").click(function() {
		index_config.logout();
	});
    $("#logo").click(function() {
		toHome();
	});
	$("#100").click(function() {
		setCurrIsHome(1);
		$("#content_iframe").attr('src', "${ctx}/home.htm");
	});
	function toHome() {
		setCurrIsHome(1);
		$("#mainRight").show();
		$("#mainDDX").hide();
	}

	$("#toSearch").click(function() {
		setCurrIsHome(0);
		$("#109").click();
	});
	$("#toSuper").click(function() {// TODO
		/*var menu = index_config.menus["10904"];
		if(menu == undefined) {
			$.ajax({
				'url' : "\${ctx}/menu/109.htm",
				'type' : "post",
				'async' : true,
				'dataType' : 'json',
				'success' : function(response, statusText) {
					if(response.status == 200) {
						var _menus = response.data;
						$.each(_menus, function(index, _menu) {
							index_config.menus[_menu.id] = _menu;
						});
					}
					menu = index_config.menus["10904"];
					if(menu != undefined) {
						if(menu.hasRight == 1 || userType == 2) {
							index_config.toSuper(menu);
						} else {
							$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
							return false;
						}
					}
				}
			});
		} else {*/
			if(userType == 2) {//menu.hasRight == 1 ||
				index_config.toSuper();//index_config.toSuper(menu);
			} else {
				toLessRight();
				return false;
			}
		//}
	});
});
function toLessRight() {
	//$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
	$("#content_iframe").attr('src', "${ctx}/right/less.htm");
}
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

/**
 * 余home页面交互
 */
function talkToHome(kind, options) {
	if(currIsHome != 1) {
		return;
	}
}
function setCurrIsHome(curr) {
	currIsHome = curr;
}
function getCreateMsgNum() {
	return createMsgNum;
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
		
		$("#mainLeft").niceScroll({  
		    cursorcolor:"#bbbcbe",  
		    cursoropacitymax:1,  
		    touchbehavior:false,  
		    cursorwidth:"6px",  
		    cursorborder:"0",  
		    cursorborderradius:"5px",
		    autohidemode: "scroll",
		    hidecursordelay: "40"
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
	menus : {},
	firstMenuIds : {},
	firstIndex : 0,
	initTopMenu: function() {
		$.ajax({
			'url' : "${ctx}/menu/work.htm",
			'type' : "post",
			'async' : true,
			'dataType' : 'json',
			'success' : function(response, statusText) {
				if(response.status == 200) {
					var _menus = response.data;
					$.each(_menus, function(index, _menu){
						index_config.firstMenuIds[index] = _menu.id;
						index_config.menus[_menu.id] = _menu;
					});
				} else {
					$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
				}
			},
			'error' : function(xmlHttp, e1, e2) {
				$.prompt.message("获取菜单失败：网络繁忙，请稍候", $.prompt.msg);
			}
		});
		
		$.ajax({
			'url' : "${ctx}/menu/109.htm",
			'type' : "post",
			'async' : true,
			'dataType' : 'json',
			'success' : function(response, statusText) {
				if(response.status == 200) {
					var _menus = response.data;
					$.each(_menus, function(index, _menu) {
						index_config.menus[_menu.id] = _menu;
					});
				} else {
					$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
				}
			}
		});
	},
	initMenu : function() {
		$.ajax({
			'url' : "${ctx}/menu/first.htm",
			'type' : "post",
			'async' : true,
			'dataType' : 'json',
			'beforeSend': function(xmlHttp) {
				xmlHttp.loadIndex = layer.load();
			},
			'success' : function(response, statusText) {
				if(response.status == 200) {
					var _menus = response.data;
					$.each(_menus, function(index, _menu){
						index_config.firstMenuIds[index] = _menu.id;
						index_config.menus[_menu.id] = _menu;
						var menuName = _menu.name;
						
						var _html = "";
						_html += '<div class="mainLeft-menu" id="' + _menu.id + '">';
						_html += '<div class="menu-icon"><img src="${ctx}/images/index/menu/' + _menu.icon + '"></div>';
						_html += '<div class="menu-txt">' + menuName + '</div>';
						_html += '</div>';
						$("#mainLeft").append(_html);
					});
					
					index_config.fristMenuAction();
					$("#" + index_config.firstMenuIds[index_config.firstIndex]).trigger("click");
				} else {
					$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
				}
			},
			'error' : function(xmlHttp, e1, e2) {
				$.prompt.message("获取菜单失败：网络繁忙，请稍候", $.prompt.msg);
			},
			'complete': function(xmlHttp, textStatus) {
				layer.close(xmlHttp.loadIndex);
			}
		});
	},
	fristMenuAction : function() {
		$(".mainLeft-menu").click(function() {
			index_config.doFristMenuAction($(this));
		});
	},
	doFristMenuAction : function($this) {
		setCurrIsHome(0);
		var menu = index_config.menus[$this.attr("id")];
		if(menu.url == undefined || menu.url == 'null' || menu.url == null) {
			$.prompt.message("该功能还没实现！", $.prompt.msg);
		} else {
			$(".mainLeft-menu").removeClass('active');
			$this.addClass('active');
			if(menu.hasRight == 0) {
				toLessRight();
				return false;
			} else {
				var pid = menu.id;
				$.ajax({
					'url' : "${ctx}/menu/" + pid +".htm",
					'type' : "post",
					'async' : true,
					'dataType' : 'json',
					'success' : function(response, statusText) {
						if(response.status == 200) {
							var _menus = response.data;
							$("#sec_menu").html("");
							var firstMenuId = "";
							$.each(_menus, function(index, _menu) {
								index_config.menus[_menu.id] = _menu;
								if(_menu.level == "2") {
									if(_menu.hasRight == 1 || (_menu.hasRight == 0 && _menu.menuShow == "1") || userType == 2) {
										
										if(firstMenuId == "") {
											firstMenuId = _menu.id;
										}
										var _html = "";
										if(index == 0) {
												_html += '<li id="' + _menu.id + '" class="active" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';
										} else {
												_html += '<li id="' + _menu.id + '" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';
										}
										$("#sec_menu").append(_html);
									}
								}
							});
							var flag = true;
							$.each(_menus, function(index, _menu){
								
								if(_menu.level == "2.5") {
									if(_menu.hasRight == 1 || (_menu.hasRight == 0 && _menu.menuShow == "1") || userType == 2) {

										var companyId = "${USER_IN_SESSION.company.eid}";
									
										if(firstMenuId == "") {
											firstMenuId = _menu.id;
										}

										var _html = "";
										if(flag) {
											flag = false;
												_html += '<li id="' + _menu.id + '" style="margin-left: 27px;" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';

										} else {
												_html += '<li id="' + _menu.id + '" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';

										}
										$("#sec_menu").append(_html);
									}
								}
							});
							var flag = true;
							$.each(_menus, function(index, _menu){
								
								if(_menu.level == "2.8") {
									if(_menu.hasRight == 1 || (_menu.hasRight == 0 && _menu.menuShow == "1") || userType == 2) {
										
										if(firstMenuId == "") {
											firstMenuId = _menu.id;
										}

										var _html = "";
										if(flag) {
											flag = false;
												_html += '<li id="' + _menu.id + '" style="margin-left: 27px;" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';
										} else {
												_html += '<li id="' + _menu.id + '" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';
										}
										$("#sec_menu").append(_html);
									}
								}
							});
							//添加一个间隔
							var flag = true;
							$.each(_menus, function(index, _menu){
								
								if(_menu.level == "3") {
									if(_menu.hasRight == 1 || (_menu.hasRight == 0 && _menu.menuShow == "1") || userType == 2) {
										
										if(firstMenuId == "") {
											firstMenuId = _menu.id;
										}

										var _html = "";
										if(flag) {
											flag = false;
												_html += '<li id="' + _menu.id + '" style="margin-left: 27px;" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';
										} else {
												_html += '<li id="' + _menu.id + '" data-pid="'+pid+'">' + _menu.name + '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;"></span></li>';
										}
										$("#sec_menu").append(_html);
									}
								}
							});
							index_config.secMenuAction();
							if(firstMenuId == "") {
								$.prompt.message("该菜单下您没有可使用的功能！", $.prompt.msg);
								toLessRight();
							} else {
								$("#" + firstMenuId).trigger("click");
							}
						} else {
							$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
						}
					},
					'error' : function(xmlHttp, e1, e2) {
						$.prompt.message("获取菜单失败：网络繁忙，请稍候", $.prompt.msg);
					}
				});
			}
		}
	},
	secMenuAction : function() {
		$(".mainRight-tab-nav ul li").click(function() {
			setCurrIsHome(0);
			$("#mainRight").show();
			$("#mainDDX").hide();
			var menu = index_config.menus[$(this).attr("id")];
			if(menu.url == undefined || menu.url == 'null' || menu.url == null || menu.url == '') {
				$.prompt.message("该功能还没实现！", $.prompt.msg);
			} else {
					$(".mainRight-tab-nav ul li").removeClass('active');
					$(this).addClass('active');
					if(menu.hasRight == 0 && userType != 2) {
						toLessRight();
						return false;
					} else {
						$("#content_iframe").attr('src', "${ctx}" + menu.url);
					}
			}
		});
	},
	logout: function() {
		var url = "${ctx}/logout.htm";
		window.location.href = url;
	},
	toSuper: function() {// TODO
		var needLock = "${sessionScope.USER_IN_SESSION.company.needLock}";
		var promptDefault = "666666";
		if(needLock != 0) {
			promptDefault = "";
		}
		layer.prompt(
			{title: '请输入超级密码！', formType: 1, value: promptDefault},
			function(password, index) {
					
				password = MD5Util(MD5Util(password));
				if(needLock == 0) {
					
					// needLock=0不需要加密锁
					window.open("${ctx}/admin/system/settings.htm");
					layer.close(index);
				
				} else {

					if(true){//checDrive()
						var lockStatus = checkLock();
						if(true) { //if(lockStatus == 0) {
							
							jsonAjax.post("${ctx}/admin/settings/befor/check.htm", {password: password}, function(response) {
								if(response.status == 200) {
									if(response.data == 1) {
										window.open("${ctx}/admin/system/settings.htm");
										layer.close(index);
									} else {
										$.prompt.message("超级密码验证失败！", $.prompt.error);
									}
								} else {
									$.prompt.message("超级密码验证失败！", $.prompt.error);
								}
							});
						
						}else if(lockStatus == 1){
							
							$.prompt.message("请插入超级用户锁 ！", $.prompt.error);
						
						}else{
						
							$.prompt.message("此浏览器不兼容超级用户锁，请使用IE浏览器登录", $.prompt.msg);
						
						}
					}else{
						
						$.prompt.message("超级用户锁驱动程序尚未运行！", $.prompt.error);
					
					}
				}
			}
			
		);
	
	}
	

}

// 验证锁逻辑，验证通过设置result = true
function checkLock() {
	var result = 1;
	try {
		R3FindInfo();
		var ishaveKey = $("#ishaveKey").val();
		if(ishaveKey == "0"){
			result = 0;
		}
	} catch (e) {
		result = 2;
	}
	return result;
}

//验证是否存在驱动 ,存在设置result = true  
function checDrive() {
	var result = false;
	try {
		R3Check();
		var ishaveKey = $("#isHaveDrive").val();
		if(ishaveKey == "1"){
			result = true;
		}
	} catch (e) {
		$.prompt.message("此浏览器不兼容超级用户锁，请使用IE浏览器登录", $.prompt.msg);
	}
	return result;
}



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
    	$("#ishaveKey").val("0");
    }else
    {
    	$("#ishaveKey").val("1");
    }
    return;
}

/**
 * 验证用户是否安装驱动程序，只对IE有效
 */
function R3Check()
{
	var R3 = document.getElementById("ROCKEY3");
    var ret;
    //如果返回值不为零，则存在，否则不存在。
    ret = R3.SD_ComCheck();
    if ( ret == 0)
    {
       $("#isHaveDrive").val("0");
    }
    else  
    {
    	$("#isHaveDrive").val("1");
    }
    
    return;
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