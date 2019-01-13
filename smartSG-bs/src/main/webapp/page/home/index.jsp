<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>smartSG-平台管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/index/settings.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
	<style type="text/css">
		.company {
			width: 300px;
			width: -webkit-calc(100% - 890px);
			width: calc(100% - 890px);
			width: -moz-calc(100% - 890px);
			color: #FFF;
			height:60px;
			line-height:60px;
			margin: 0 auto;
			float: left;
			text-align: center;
		}
		.container {
			min-width: 960px;
			width: 100%;
			margin: 0px auto;
		}
		.header {
			width: 100%;
			height: 60px;
			background-color: #3da8f5;
		}

		.logo {
			width: 198px;
			height: 60px;
			background-color: #0a93f5;
			float: left;
			padding-left: 0px;
		}
		.logo img{
			width:198px;
			height:60px;
			padding-top:0px;
			padding-left: 0px;
		}
		.Hleft {
			width: 250px;
			height: 45px;
			float: left;
			margin-top: 12px;
			margin-left: 20px;
			cursor: pointer;
		}
		.header-man-img .icon {
			width: 36px;
			height: 36px;
			float: left;
			border-radius: 50%;
			margin-left:0px;
			margin-top:0px;
		}

		.header-man-name {
			float: left;
			margin-top: 8px;
			margin-left: 15px;
			color: #fff;
			font-size: 14px;
		}

		.sanjiao-icon {
			float: left;
			margin: 15px 0px 0px 8px;
			border: solid transparent;
			border-width: 6px;
			border-top-color: #fff;
		}
		.Hright {
			width: 185px;
			height: 60px;
			float: right;
			margin-right: 15px;
		}
		.header-line {
			width: 3px;
			height: 30px;
			float: left;
			margin: 20px 0px 0px 22px;
			background: url(/smartSG-bs/images/index/Header_right.png) no-repeat;
			background-position: -125px 0px;
		}
		.header-administrator {
			width: 22px;
			height: 22px;
			float: left;
			margin: 18px 0px 0px 22px;
			background: url(/smartSG-bs/images/index/Header_right.png) no-repeat;
			background-position: -146px 0px;
			cursor: pointer;
		}

		.header-exit {
			width: 22px;
			height: 22px;
			float: left;
			margin: 18px 0px 0px 22px;
			background: url(/smartSG-bs/images/index/Header_right.png) no-repeat;
			background-position: -205px 0px;
			cursor: pointer;
		}

	</style>
</head>
<body style="overflow-x: hidden;">
<div class="container">
		<!-- 头部开始 -->
	<div class="header">
		<div class="logo" id="logo">
			<img src="${ctx}/images/smartSG_logo.jpg">
		</div>
		<div class="Hleft">
			<div class="header-man-img" id="index_userIcon"><img class="icon" id="loginicon" src=""></div>
			<div class="header-man-name" id="index_userInfo">${USER_IN_SESSION.user.name}</div>
			<span class="sanjiao-icon" id="index_userInfo2"></span>
		</div>
		<div class="company">
			<span style="border-radius:4px;padding: 5px 8px;font-size: 24px;">
				${USER_IN_SESSION.user.userType  eq 1 ? USER_IN_SESSION.company.name:USER_IN_SESSION.user.deptName}
			</span>
		</div>
		<div class="Hright">
			<ul style="margin-left: 55px;">
				<%--<li><div class="header-line"></div></li>--%>
                <c:if test="${USER_IN_SESSION.user.userType eq 1}">
                    <li id="toSuper"><a href="javascript:void(0);" class="header-administrator" title="超级用户"></a></li>
					<li><div class="header-line"></div></li>
                </c:if>
				<li id="logout"><a href="javascript:void(0);" class="header-exit" ${USER_IN_SESSION.user.userType eq 1 ? '':'style="margin-left:85px;"'} title="退出系统" ></a></li>
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
				</div>
				<!-- 左边导航栏结束 -->
				<!-- 右边内容开始 -->
				<div class="mainRight" id="mainRight">
					<div class="mainRight-tab-nav">
						<ul id="third_menu">

						</ul>
						<div class="mainRight-tab-btn" style="margin-right:-90px;">
							<c:if test="${USER_IN_SESSION.user.userType eq 1}">
								<div class="mainRight-tab-btn1" style="text-align: center;" id="mainDDX_btn">
									<a href="javascript:void(0);" style="color: white;">即时通讯</a>
									<div class="im-redDot" id="header_DDX_RedDot"></div>
								</div>
							</c:if>
							<div class="mainRight-tab-btn2" style="text-align: center;background-color:#3da8f5;${USER_IN_SESSION.user.userType eq 1 ? '':'margin-left:90px;'}" id="mainHelp_btn">
								<a href="javascript:void(0);" style="color: white;">帮助
									<div class="im-redDot" id="header_Help__RedDot"></div>
								</a>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="mainRight-tab-con">
						<iframe id="content_iframe" src="" style="width:100%;height:550px; border: 0px;"></iframe>
					</div>
				</div>
				<!-- 右边内容结束 -->
				<!--叮当享界面开始-->
            <div class="mainRight" id="mainDDX" style="display: none;height:550px;">
                <!-- 包含叮当享jsp页面 -->
                <c:if test="${USER_IN_SESSION.user.imStatus eq 1}">
					<%@ include file="/page/chat/lyl.jsp" %>
                </c:if>
            </div>
            <!--叮当享界面结束-->
				<!--帮助页面开始-->
				<div class="mainRight" id="mainHelp" style="display: none;height:550px;background-color: white;padding: 10px 10px;">
					<!-- 包含帮助jsp页面 -->
					<c:if test="${USER_IN_SESSION.user.userType eq 1}">
						<%@ include file="/page/platformManagement/help.jsp" %>
					</c:if>
					<c:if test="${USER_IN_SESSION.user.userType eq 2}">
						<%@ include file="/page/sgManagmentCompany/help.jsp" %>
					</c:if>
					<c:if test="${USER_IN_SESSION.user.userType eq 4}">
						<%@ include file="/page/tw/help.jsp" %>
					</c:if>
				</div>
				<!--帮助页面结束-->
			</div>
		</div>
		<!-- 主体内容结束 -->
	</div>
</body>
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
var imStatus = "${USER_IN_SESSION.user.imStatus}";
var account = "${USER_IN_SESSION.user.account}";
var companyId = "${USER_IN_SESSION.company.eid}";
var userType = "${USER_IN_SESSION.user.userType}";
var level = "${USER_IN_SESSION.user.level}";
var icon = "${USER_IN_SESSION.user.icon}";
var currIsHome = 1;
var createMsgNum = 0;
var confirmId = "";
$(document).ready(function(){
    index_config.initMenu();
	
	index_config.initHeight();
	
	index_niceScroll($("#mainLeft"));//优化滚动条
	index_niceScroll($("#content_iframe"));//优化滚动条

	if(icon == ''){
		icon = "${ctx}/images/setting/default_man_small.png"
	}
	$("#loginicon").attr("src",icon);
	
	$("#logout").click(function() {//退出系统
		index_config.logout();
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
	
	$("#logo").click(function() {
        toHome();
	});
    function toHome() {
        setCurrIsHome(1);
        $("#mainRight").show();
        $("#mainDDX").hide();
    }

	$("#toSuper").click(function() {//超级用户
		/*var menu = index_config.menus["10804"];
		if(menu == undefined) {
			$.ajax({
				'url' : "\${ctx}/menu/108.htm",
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
					menu = index_config.menus["10804"];
					if(menu != undefined) {
						if(menu.hasRight == 1 || level == 3) {
							index_config.toSuper(menu);
						} else {
							$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
							//return false;
						}
					}
				}
			});
		} else {*/
			if(level == 3) {
				index_config.toSuper(menu);
			} else {
				//toLessRight();
				$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
				return false;
			}
		/*}*/
	});
	
	//模拟推送
	/* setInterval(function(){
			var message = {
				"menuId":1050101,
				"pid":10501,
				"ppid":105,
			}
			setApproveDot(message);
	}, 6000); */
});

//保存批审推送消息的ID
var approveResponseMessageIds = {};
var dotNums = {}; // 保存菜单推送的数量
var menuDot = {};
// 处理菜单推送数量
var setApproveDot = function(message, messageId) {
	var pid = message.pid;
	var ppid = message.ppid;
	var menuId = message.menuId;
	if(dotNums[pid] != undefined) {
		dotNums[pid] = dotNums[pid] + 1;
	} else {
		dotNums[pid] = 1;
	}
	
	if(dotNums[ppid] != undefined) {
		dotNums[ppid] = dotNums[ppid] + 1;
	} else {
		dotNums[ppid] = 1;
	}
	
	if(dotNums[menuId] != undefined) {
		dotNums[menuId] = dotNums[menuId] + 1;
	} else {
		dotNums[menuId] = 1;
	}
	if(menuDot[pid] != undefined) {
		menuDot[pid][menuId] = 1;
	} else {
		var childs = {};
		childs[menuId] = 1;
		menuDot[pid] = childs;
	}
	if(approveResponseMessageIds[message.menuId] != undefined) {
		approveResponseMessageIds[message.menuId].push(messageId);
	} else {
		var ids = [];
		ids.push(messageId);
		approveResponseMessageIds[message.menuId] = ids;
	}
	if($("#"+menuId).length > 0 && $("#"+menuId).hasClass("active")){
		refreshContent();
	}
	setDotNumsShow(menuId, pid, ppid);
	 //createMsg(message); //批审抄送不需要右下角弹框
}
var setDotNumsShow = function(menuId, pid, ppid) {
	if(dotNums[pid] == undefined || dotNums[pid] < 1) {
		$("#" + pid + "_dot").hide()
		$("#" + pid + "_dot").html(0);
	} else if(dotNums[pid] > 99) {
		$("#" + pid + "_dot").show();
		$("#" + pid + "_dot").html("99+");
	} else {
		$("#" + pid + "_dot").show();
		$("#" + pid + "_dot").html(dotNums[pid]);
	}
	if(dotNums[menuId] == undefined || dotNums[menuId] < 1) {
		$("#" + menuId + "_dot").hide()
		$("#" + menuId + "_dot").html("0");
	} else if(dotNums[menuId] > 99) {
		$("#" + menuId + "_dot").show();
		$("#" + menuId + "_dot").html("99+");
	} else {
		$("#" + menuId + "_dot").show();
		$("#" + menuId + "_dot").html(dotNums[menuId]);
	}
	
	if(dotNums[ppid] == undefined || dotNums[ppid] < 1) {
		$("#" + ppid + "_dot").hide()
		$("#" + ppid + "_dot").html("0");
	} else if(dotNums[menuId] > 99) {
		$("#" + ppid + "_dot").show();
		$("#" + ppid + "_dot").html("99+");
	} else {
		$("#" + ppid + "_dot").show();
		$("#" + ppid + "_dot").html(dotNums[ppid]);
	}
}
var setApproveRead = function(menuId, pid, ppid) {
	var menuIdNum = dotNums[menuId];
	if(menuIdNum == undefined) {
		menuIdNum = 0;
	}
	dotNums[menuId] = 0;
	if(dotNums[pid]) {
		dotNums[pid] = dotNums[pid] - menuIdNum;
	} else {
		dotNums[pid] = 0;
	}
	
	if(dotNums[ppid]) {
		dotNums[ppid] = dotNums[ppid] - menuIdNum;
	} else {
		dotNums[ppid] = 0;
	}
	
	setDotNumsShow(menuId, pid, ppid);
	
	var ids = approveResponseMessageIds[menuId];
	if(ids != undefined && ids.length > 0) {
		//发送应答消息（清空离线消息）
		im.sendResponseMessage(ids);
	}
}



function toLessRight() {
	//$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
	$("#content_iframe").attr('src', "${ctx}/home/notRole.htm");
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
var index_config = {
	menus:{},//系统设置菜单
	min_height : 650,
	top_height : 60,
	initHeight: function() {
		index_config.resetSize();
		$(window).resize(function() {
			index_config.resetSize();
		});
	},
	resetSize : function() {
		var height = $(window).height();
		if(height < index_config.min_height) {
			height = index_config.min_height;
    	}
		var main_height = height - index_config.top_height;
		$("#mainLeft").css("height", main_height + "px");
		var mainRight_margin = 18;
		$("#mainRight").css("height", (main_height - mainRight_margin) + "px");
		$("#content_iframe").css("height", (main_height - mainRight_margin - 40) + "px");
	},
	allMenus: {},
	firstMenus:{},	//一级菜单
	secMenus:{},	//二级菜单
	thirdMenus:{},	//三级菜单
	toSuper: function(menu) {
		var needLock = "${sessionScope.USER_IN_SESSION.company.needLock}";
		var promptDefault = "666666";
		if(needLock != 0) {
			promptDefault = "";
		}
		layer.prompt({title: '请输入超级密码！', formType: 1, value: promptDefault}, function(password, index) {
			password = MD5Util(MD5Util(password));

			if(needLock == 0) {
				//needLock=0不需要加密锁
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
		});
	},
	initMenu : function() {
		$.ajax({
			'url' : "${ctx}/menu/firstThree.htm",
			'type' : "get",
			'async' : true,
			'dataType' : 'json',
			'beforeSend': function(xmlHttp) {
				xmlHttp.loadIndex = layer.load();
			 },
			'success' : function(response, statusText) {
				if(response.status == 200) {
					var _firstMenus = response.data.first;
					var index_num=0;
					var firstId = "";
					$.each(_firstMenus, function(index, _menu) {
						if(index == 0) {
							firstId = _menu.id;
						}
						index_config.allMenus[_menu.id] = _menu;
						var html = "";
						var childHtml = "";
						var _secMenus = response.data[_menu.id];
						if(_secMenus != undefined) {
							index_config.secMenus[_menu.id] = _secMenus;
							childHtml += '<ul class="menu-tit2">';
							$.each(_secMenus, function(_index, _secMenu) {
								index_config.allMenus[_secMenu.id] = _secMenu;
								index_config.firstMenus[_secMenu.id] = _secMenu;
								childHtml += '<li id="'+_secMenu.id+'">';
								childHtml += _secMenu.name;
								childHtml+= '<span class="im-redNum_2" id="'+_secMenu.id+'_dot" style="display: none;"></span>';
								childHtml += '</li>';
							});
							if(userType == 4&&level==1){// 摊位平台的添加按钮  领导层显示
								childHtml += '<li id="addTW" style="padding-left:85px;width:148px;"><img src="${ctx}/images/icons/settingAdd_icon2.png">&nbsp;&nbsp;新增摊位</li>';
							}
							if(userType == 5&&level==1){// 食阁拥有者平台的添加按钮  领导层显示
								childHtml += '<li id="addSG" style="padding-left:85px;width:148px;"><img src="${ctx}/images/icons/settingAdd_icon2.png">&nbsp;&nbsp;新增食阁</li>';
							}
							childHtml += '</ul>';
						}
						html += '<div class="mainLeft-setting-menu">';
						if(userType == 5){
							html += '	<div class="menu-tit" id="'+_menu.id+'" style="display:none;">';
						}else{
							html += '	<div class="menu-tit" id="'+_menu.id+'">';
						}
						html += _menu.name;
						html += '<span class="setting-ok"><img  align="right"  style="margin-top: 20px" src="${ctx}/images/index/up.png"></span>';
						html += '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;">0</span>';
						html += '	</div>';
						if(childHtml != "") {
							html += childHtml;
						}
						html += '</div>';
						index_config.firstMenus[_menu.id] = _menu;
						$("#mainLeft").append(html);
						index_num++;
					});
					index_config.fristMenuAction();
					index_config.secMenuAction();
					$("#" + firstId).trigger("click");
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
	currFirstMenuId:"",//当前一级菜单得id
	fristMenuAction : function() {
		$('.mainLeft-setting-menu .menu-tit').click(function() {
			// 点击一级菜单
			var currFirstId = "";
			var menuId = $(this).attr("id");
			if(menuId != index_config.currFirstMenuId) {
				index_config.currFirstMenuId = menuId;
				$('.mainLeft-setting-menu .menu-tit2').hide();
				$('.setting-ok img').attr("src","${ctx}/images/index/up.png");
				$(this).siblings('.menu-tit2').show();
				$(this).find(".setting-ok img").attr("src","${ctx}/images/index/down.png");
			} else {
				var _display = $(this).siblings('.menu-tit2').css("display");
				if(_display=="block"){
					$(this).siblings('.menu-tit2').hide();
					$(this).find(".setting-ok img").attr("src","${ctx}/images/index/up.png");
				}else{
					$(this).siblings('.menu-tit2').show();
					$(this).find(".setting-ok img").attr("src","${ctx}/images/index/down.png");
				}
				return;
			}
			var menu = index_config.firstMenus[menuId];
			$('.mainLeft-setting-menu>.menu-tit').removeClass('active');
			$('.mainLeft-setting-menu .menu-tit2 li').removeClass('active');
			var actionId = "";
			if(menu.childNum != 0) {
				var childs = index_config.secMenus[menuId];
				var child = childs[0];
				$('#' + child.id).addClass('active');
				actionId = child.id;
			} else {
				$(this).addClass('active');
				actionId = menuId;
			}
			index_config.doAction(actionId);
		});
	},
	secMenuAction : function() {
		$('.mainLeft-setting-menu .menu-tit2 li').click(function() {
			// 点击二级菜单
			var menuId = $(this).attr("id");
			if (menuId == 'addTW'){// 绑定摊位
				var url = "${ctx}/tw/toBinding/tw.htm";
				$.prompt.layerUrl2({url:url, width: 400, height: 250});
				return;
			}
			if (menuId == 'addSG'){// 绑定食阁
				var url = "${ctx}/tw/toBinding/sg.htm";
				$.prompt.layerUrl2({url:url, width: 400, height: 250});
				return;
			}
			$('.mainLeft-setting-menu>.menu-tit').removeClass('active');
			$('.mainLeft-setting-menu .menu-tit2 li').removeClass('active');
			$(this).addClass('active');
            $("#mainHelp").hide();
            $("#mainDDX").hide();
            $("#mainRight").show();
			index_config.doAction(menuId);
		});
	},
	thirdMenuAction: function() {
		$(".mainRight-tab-nav ul li").click(function() {
			// 三级菜单
			$(".mainRight-tab-nav ul li").removeClass('active');
			$(this).addClass('active');
			var menu = index_config.firstMenus[$(this).attr("id")];
			if(menu.hasRight == 0 && level !=3){
				//$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
				$("#content_iframe").attr('src', "${ctx}/home/notRole.htm" );
			}
			else if(typeof(menu.url) == 'undefined' 
							|| menu.url == null 
							|| menu.url == "") {
				$.prompt.message("该功能还没实现！", $.prompt.msg);
			} 
			else {
				var confirmMenu = index_config.firstMenus[confirmId];
				var url = appendParam(menu.url, "confirmId", confirmId);
				url = appendParam(url, "hasConfirmId", confirmMenu.confirm);
				url = appendParam(url, "confirmPid", confirmMenu.pid);
				$("#content_iframe").attr('src', "${ctx}" + url);
				var ppid = index_config.getPpidByThird(menu.id);
				setTimeout(function(){
					setApproveRead(menu.id,menu.pid,ppid);
				},1500);
			}
		});
	},
	doAction: function(actionId) {
		confirmId = actionId;
		var menus = index_config.thirdMenus[actionId];
		if(menus != undefined) {
			index_config.changeThirdMenus(menus, false);
		} else {
			// 加载菜单
			var pid = actionId;
			$.ajax({
				'url' : "${ctx}/menu/" + pid +".htm",
				'type' : "post",
				'async' : true,
				'dataType' : 'json',
				'success' : function(response, statusText) {
					if(response.status == 200) {
						var _menus = response.data;
						index_config.thirdMenus[actionId] = _menus;
						index_config.changeThirdMenus(_menus, true);
					} else {
						$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
					}
				},
				'error' : function(xmlHttp, e1, e2) {
					$.prompt.message("获取菜单失败：网络繁忙，请稍候", $.prompt.msg);
				}
			});
		}
	},
	getPpidByThird: function(menuId) {
		var thirdMenu = index_config.allMenus[menuId];
		var pid = thirdMenu.pid;
		var ppid = index_config.allMenus[pid].pid;
		return ppid;
	},
	changeThirdMenus: function(_menus, isInit) {
		$("#third_menu").html("");
		var firstId = "";
		$.each(_menus, function(index, _menu){
			index_config.allMenus[_menu.id] = _menu;
		});
		var ppid = index_config.getPpidByThird(_menus[0].id);
		$.each(_menus, function(index, _menu){
			if(index == 0) {
				firstId = _menu.id;
			} 
			if(isInit == true) {
				index_config.firstMenus[_menu.id] = _menu;
			}
			if(_menu.level == "3") {
				var _html = "";
				var name = _menu.name;
				if(index == 0) {
					_html += '<li id="' + _menu.id + '" class="active">' + name;
					_html += '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;position: absolute;left:110px"></span>';
					_html += '</li>';
				} else {
					_html += '<li id="' + _menu.id + '">' + name;
					_html += '<span class="im-redNum_2" id="'+_menu.id+'_dot" style="display: none;position: absolute;left:110px"></span>';
					_html += '</li>';
				}
				$("#third_menu").append(_html);
				/* setDotNumsShow(_menu.id, _menu.pid, ppid); */
			}
		});
		var flag = true;
		$.each(_menus, function(index, _menu){
			if(_menu.level == "3.5") {
				var _html = "";
				var name = _menu.name;
				if(flag) {
					flag = false;
					_html += '<li id="' + _menu.id + '" style="margin-left: 27px;">' + name + '</li>';
				} else {
					_html += '<li id="' + _menu.id + '">' + name + '</li>';
				}
				$("#third_menu").append(_html);
			}
		});
		var flag = true;
		$.each(_menus, function(index, _menu){
			if(_menu.level == "3.6") {
				var _html = "";
				var name = _menu.name;
				if(flag) {
					flag = false;
					_html += '<li id="' + _menu.id + '" style="margin-left: 27px;">' + name + '</li>';
				} else {
					_html += '<li id="' + _menu.id + '">' + name + '</li>';
				}
				$("#third_menu").append(_html);
			}
		}); 
		index_config.thirdMenuAction();
		$("#" + firstId).trigger("click");
	},
	logout: function() {
		window.location.href = "${ctx}/logout.htm";
	}
}
function getCreateMsgNum() {
	return createMsgNum;
}
function refreshContent() {
	$("#content_iframe").attr("src", $("#content_iframe").attr("src"));
}

function confirmCallback(_confirmId, _confirmPid, _flag) {
	index_config.firstMenus[_confirmId].confirm = 1;
	$("#" + _confirmId).append('<span class="setting-ok"><img src="${ctx}/images/setting/ok.png"></span>');
	if(_flag == 1) {
		index_config.firstMenus[_confirmPid].confirm = 1;
		$("#" + _confirmPid).append('<span class="setting-ok"><img src="${ctx}/images/setting/ok.png"></span>');
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

function dj(){
	setCurrIsHome(1);
	$("#content_iframe").attr('src', "${ctx}/toApproval.htm");
}

function updateIcon(icon){
	$("#loginicon").attr("src",icon);
}
function toUserIcon() {
	var url = "${ctx}/sysUser/self/userICon.htm?eid=${USER_IN_SESSION.user.eid}";
	parent.$.prompt.layerUrl2({url: url, width: 600,height:450});
}
</script>
</html>