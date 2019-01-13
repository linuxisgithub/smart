<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>SMART SG 后台管理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/settings.css">
</head>
<body>
	<div class="container">
		<!-- 头部开始 -->
		<div class="header">
			<div class="logo">
					<img src="${ctx}/images/setting/yjSetting_logo.png">
			</div>
			<div class="Hleft" id="index_comInfo">
			    <div class="header-man-img"><img class="icon" src="${ctx}/images/setting/default_man_small.png"></div>
			    <div class="header-man-name">${USER_IN_SESSION.user.name}</div>
				<span class="sanjiao-icon"></span>
			</div>
			<div class="Hright2">
				<ul>
					
					<li><div class="header-line"></div></li>
					<li><a href="javascript:void(0);" class="header-exit" id="logout" title="退出系统" ></a></li>
				</ul>
			</div>
            <div class="clear"></div>    
 	    </div>		
		<!-- 头部结束 -->

		<!-- 主体内容开始 -->
		<div class="mainContainer" id="mainContainer">
			<div class="mainContainerWrapper">
				<!-- 左边导航栏开始 -->
				<div class="mainLeft" id="mainLeft" >				
					<%-- 
					<div class="mainLeft-setting-menu">				
						<div class="menu-tit">
							<span class="menu-number">A</span>基础设置
						</div>
						<ul class="menu-tit2">
							<li><img src="${ctx}/images/setting/1.png" alt="" class="menu-tit2-number">公司架构<span class="setting-ok"><img src="${ctx}/images/setting/ok.png" alt=""></span></li>
							<li><img src="${ctx}/images/setting/2.png" alt="" class="menu-tit2-number">系统用户</li>
							<li><img src="${ctx}/images/setting/3.png" alt="" class="menu-tit2-number">《叮当享》</li>
						</ul>
					</div>
					<div class="mainLeft-setting-menu">
						<div class="menu-tit"><span class="menu-number">B</span>办公系统 <span style="font-weight: bold;">· </span>OA</div>
						<ul class="menu-tit2">
								<li><img src="${ctx}/images/setting/1.png" alt="" class="menu-tit2-number">公司架构<img src="" alt=""></li>
								<li><img src="${ctx}/images/setting/2.png" alt="" class="menu-tit2-number">我的办公</li>
								<li><img src="${ctx}/images/setting/3.png" alt="" class="menu-tit2-number">工作报告</li>
								<li><img src="${ctx}/images/setting/4.png" alt="" class="menu-tit2-number">人事行政</li>
						</ul>
					</div>
					<div class="mainLeft-setting-menu">
						<div class="menu-tit"><span class="menu-number">C</span>项目任务</div>
					</div>
					<div class="mainLeft-setting-menu">
						<div class="menu-tit"><span class="menu-number">D</span>销售及市场</div>
						<ul class="menu-tit2">
							<li><img src="${ctx}/images/setting/1.png" alt="" class="menu-tit2-number">销售运营</li>
							<li><img src="${ctx}/images/setting/2.png" alt="" class="menu-tit2-number">市场工作</li>								
						</ul>
					</div>
					<div class="mainLeft-setting-menu">
						<div class="menu-tit"><span class="menu-number">E</span>客户关系 <span style="font-weight: bold;">· </span> CRM</div>
					</div>
					<div class="mainLeft-setting-menu">
						<div class="menu-tit"><span class="menu-number">F</span>超级用户</div>						
					</div>			 --%>		
				</div>									
				
				<!-- 左边导航栏结束 -->
				<!-- 右边内容开始 -->
				<div class="mainRight" id="mainRight">
					<div class="mainRight-tab-nav">
						<ul id="third_menu">
							<!-- <li class="active">公司管理层</li>
							<li >一级部门</li>
							<li>二级部门</li>
							<li style="margin-left: 27px;">驻外部门</li> -->
							
						</ul>
						<div class="myhelp">							
							<img src="${ctx}/images/setting/help.png" alt="设置帮助">设置帮助	
							<div class="hidden-box">
								<div class="qq">
									<img src="${ctx}/images/setting/qq_icon.png" alt="企业QQ">
									<div class="ewm"><img src="${ctx}/images/setting/qq_ewm.png" alt="企业QQ二维码"></div>
								</div>
								<div class="weixin">
									<img src="${ctx}/images/setting/weixin_icon.png" alt="企业微信">
									<div class="ewm"><img src="${ctx}/images/setting/weixin_ewm.png" alt="企业微信二维码"></div>
								</div>
							</div>
							
						</div>
						<div class="clear"></div>
					</div>
					<div class="mainRight-tab-con">
						<iframe id="content_iframe" src="" style="width:100%;height:550px; border: 0px;"></iframe>
					</div>
				</div>
				<!-- 右边内容开始 -->
			</div>
		</div>
		<!-- 主体内容结束 -->
	</div>
</body>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/chat/js/ddx_gd/jquery.nicescroll.js"></script>
<script type="text/javascript">
var companyLevel = '${USER_IN_SESSION.company.level}';
var isVip = '${USER_IN_SESSION.company.isVip}';
var companyId = "${USER_IN_SESSION.company.eid}";
var confirmId = "";
$(document).ready(function(){
	config.initMenu();
	config.initHeight();
	$("#logout").click(function() {
		config.logout();
	});
	$("#index_comInfo").click(function() {
		var url = "${ctx}/settings/company/info.htm";
		parent.$.prompt.layerUrl2({url: url, width: 450,height:340});
	});
});
var config = {
	min_height : 650,
	top_height : 60,
	initHeight: function() {
		config.resetSize();
		$(window).resize(function() {
			config.resetSize();
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
		var height = $(window).height();
		if(height < config.min_height) {
			height = config.min_height;
    	}
		var main_height = height - config.top_height;
		$("#mainLeft").css("height", main_height + "px");
		
		var mainRight_margin = 18;
		$("#mainRight").css("height", (main_height - mainRight_margin) + "px");
		$("#content_iframe").css("height", (main_height - mainRight_margin - 40) + "px");
	},
	firstMenus : {},
	secMenus:{},
	thirdMenus:{},
	initMenu : function() {
		$.ajax({
			'url' : "${ctx}/admin/menu/first.htm",
			'type' : "get",
			'async' : true,
			'dataType' : 'json',
			'beforeSend': function(xmlHttp) {
				xmlHttp.loadIndex = layer.load();
			},
			'success' : function(response, statusText) {
				if(response.status == 200) {
					debugger
					var _firstMenus = response.data.first;
					var letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I','J','k','L','M','N','O','P','Q','R','S','T'];
					var index_num=0;
					var firstId = "";
					debugger
					$.each(_firstMenus, function(index, _menu) {

						if(index == 0) {
							firstId = _menu.id;
						}
						
						var html = "";
						var childHtml = "";
						var _secMenus = response.data[_menu.id];
						if(_secMenus != undefined) {
							config.secMenus[_menu.id] = _secMenus;
							childHtml += '<ul class="menu-tit2">';
							$.each(_secMenus, function(_index, _secMenu) {

								config.firstMenus[_secMenu.id] = _secMenu;
								childHtml += '<li id="'+_secMenu.id+'">'
								if(_secMenu.icon != undefined && _secMenu.icon != 'null' && _secMenu.icon != null) {
									childHtml += '<img src="${ctx}/images/setting/'+_secMenu.icon+'" class="menu-tit2-number">';
								}
								childHtml += _secMenu.name;
								if(_secMenu.confirm == "1" || _secMenu.confirm == 1) {
									childHtml += '<span class="setting-ok"><img src="${ctx}/images/setting/ok.png"></span>';
								}
								childHtml += '</li>';
							});
							childHtml += '</ul>';
						}
						html += '<div class="mainLeft-setting-menu">';
						html += '	<div class="menu-tit" id="'+_menu.id+'">';
						html += '		<span class="menu-number">'+letters[index_num]+'</span>' + _menu.name;
						if(_menu.confirm == "1" || _menu.confirm == 1) {
							html += '<span class="setting-ok"><img src="${ctx}/images/setting/ok.png"></span>';
						}
						html += '	</div>';
						if(childHtml != "") {
							html += childHtml;
						}
						html += '</div>';
						config.firstMenus[_menu.id] = _menu;
						$("#mainLeft").append(html);
						index_num++;
					});
					config.fristMenuAction();
					config.secMenuAction();
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
	fristMenuAction : function() {
		$('.mainLeft-setting-menu .menu-tit').click(function() {
			// 点击一级菜单
			var currFirstId = "";
			var menuId = $(this).attr("id");
			if(menuId != currFirstId) {
				currFirstId = menuId;
				$('.mainLeft-setting-menu .menu-tit2').hide();
				$(this).siblings('.menu-tit2').show();
			} else {
				return;
			}
			var menu = config.firstMenus[menuId];
			$('.mainLeft-setting-menu>.menu-tit').removeClass('active');
			$('.mainLeft-setting-menu .menu-tit2 li').removeClass('active');
			var actionId = "";
			if(menu.childNum != 0) {
				var childs = config.secMenus[menuId];
				var child = childs[0];
				$('#' + child.id).addClass('active');
				actionId = child.id;
			} else {
				$(this).addClass('active');
				actionId = menuId;
			}
			config.doAction(actionId);
		});
	},
	secMenuAction : function() {
		$('.mainLeft-setting-menu .menu-tit2 li').click(function() {
			// 点击二级菜单
			var menuId = $(this).attr("id");
			$('.mainLeft-setting-menu>.menu-tit').removeClass('active');
			$('.mainLeft-setting-menu .menu-tit2 li').removeClass('active');
			$(this).addClass('active');
			config.doAction(menuId);
		});
	},
	thirdMenuAction: function() {
		$(".mainRight-tab-nav ul li").click(function() {
			// 三级菜单
			$(".mainRight-tab-nav ul li").removeClass('active');
			$(this).addClass('active');
			var menu = config.firstMenus[$(this).attr("id")];
			if(menu.url == undefined || menu.url == 'null' || menu.url == null) {
				$.prompt.message("该功能还没实现！", $.prompt.msg);
			} else {
				var confirmMenu = config.firstMenus[confirmId];
				var url = appendParam(menu.url, "confirmId", confirmId);
				url = appendParam(url, "hasConfirmId", confirmMenu.confirm);
				url = appendParam(url, "confirmPid", confirmMenu.pid);
				$("#content_iframe").attr('src', "${ctx}" + url);
			}
		});
	},
	doAction: function(actionId) {
		confirmId = actionId;
		var menus = config.thirdMenus[actionId];
		if(menus != undefined) {
			config.changeThirdMenus(menus, false);
		} else {
			// 加载菜单
			var pid = actionId;
			$.ajax({
				'url' : "${ctx}/admin/menu/" + pid +".htm",
				'type' : "post",
				'async' : true,
				'dataType' : 'json',
				'success' : function(response, statusText) {
					if(response.status == 200) {
						var _menus = response.data;
						config.thirdMenus[actionId] = _menus;
						config.changeThirdMenus(_menus, true);
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
	changeThirdMenus: function(_menus, isInit) {
		$("#third_menu").html("");
		var firstId = "";
		$.each(_menus, function(index, _menu){
			if(index == 0) {
				firstId = _menu.id;
			} 
			if(isInit == true) {
				config.firstMenus[_menu.id] = _menu;
			}

			if(_menu.level == "3") {
				var _html = "";
				var name = _menu.name;
				if(index == 0) {
					_html += '<li id="' + _menu.id + '" class="active">' + name + '</li>';
				} else {
					_html += '<li id="' + _menu.id + '">' + name + '</li>';
				}
				$("#third_menu").append(_html);
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
		config.thirdMenuAction();
		$("#" + firstId).trigger("click");
	},
	logout: function() {
		//window.location.href = "${ctx}/logout.htm";
		window.close();
	}
}
function refreshContent() {
	$("#content_iframe").attr("src", $("#content_iframe").attr("src"));
}
function confirmCallback(_confirmId, _confirmPid, _flag) {
	config.firstMenus[_confirmId].confirm = 1;
	$("#" + _confirmId).append('<span class="setting-ok"><img src="${ctx}/images/setting/ok.png"></span>');
	if(_flag == 1) {
		config.firstMenus[_confirmPid].confirm = 1;
		$("#" + _confirmPid).append('<span class="setting-ok"><img src="${ctx}/images/setting/ok.png"></span>');
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
</html>