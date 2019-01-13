/**
 * Created by an.han on 14-2-20.
 */

var share_title;
var share_summary;
var share_Url;

window.onload = function() {
	
	for(var i=1;i<=6;i++){  
		$("#tg_"+i).on("click",function(){
			
			var input = $(this).prev();
			
			$(input).val("");
			
		});
	}
	
	

	$("#shareBtnW").click(
			function() {

				var result = checkIsPayUser();
				if (result == false) {
					openPayTip(2);
					return false;
				}

				share_title = encodeURI(encodeURI(share_title));
				share_summary = encodeURI(encodeURI(share_summary));
				layerOpen_share_exten(
						path + '/page/share_cus/tg_share.jsp?shareUrl='
								+ share_Url + '&title=' + share_title
								+ '&summary=' + share_summary, '分享到', 3,
						'300px');
			});

	// 点击列表自己头像显示自己提交的工作
	$("#loginIcon").on('click', function() {

		$("#page_GZ_GZQ").hide();
		$("#page_GZ_GZQMY").show();
		$("#page_GZ_GZQXQ").hide();

		// 查询自己创建的工作
		getMyWord();

		var list = $("#my_list");

		$.each(list, function(index, e) {

			// 详情
			var detail = $(e).find(".comment-list");

			// 详情
			$(detail).click(function() {

				var id = $(this).attr("id");

				$("#page_GZ_GZQ").hide();
				$("#page_GZ_GZQXQ").show();
				$("#page_GZ_GZQMY").hide();

				$("#detail_return1").hide();
				$("#detail_return2").show();

				showDetail(id);

			});

		});

	});

	// 工作圈
	function showgsq() {

		// 查询发送给自己和自己创建的工作
		getMyWordAndReceive();

		var list = $("#list");

		$.each(list, function(index, e) {

			// 评论输入框
			var textArea = $(e).find(".comment");
			// 回复按钮
			var reply = $(e).find(".btn");
			// 详情
			var detail = $(e).find(".comment-list");

			// 详情
			$(detail).click(function() {

				$("#detail_return1").show();
				$("#detail_return2").hide();

				var id = $(this).attr("id");

				$("#page_GZ_GZQ").hide();
				$("#page_GZ_GZQXQ").show();

				showDetail(id);

			});

			// 为每条数据父级添加鼠标获取焦点事件
			$(textArea).focus(function() {

				$(this).parent().addClass("text-box-on");

				$(this).val($(this).val() == '评论…' ? '' : $(this).val());

			});

			// 为每条数据父级添加鼠标失去焦点事件
			$(textArea).blur(function() {

				var value = $(this).val();

				if (value == '') {
					$(this).val("评论…");
					$(this).parent().removeClass("text-box-on");
				}

			});

			// 列表绑定回复处理事件
			$(reply).click(function() {

				var cssl = $(this).css("btn-off");

				// 按钮灰色不能提交(字数超过140)
				if (cssl == '') {

					// 获取评论内容
					var reply_content = $(this).prev().val();

					var $text_div = $(this);

					// 当前记录id
					// prev() 返回前一个同级dom 元素
					var eid = $(this).prev().attr("id");
					var type = $(this).prev().attr("btype");

					$.post("extension/record.htm", {
						"l_bid" : eid,
						"l_type" : type,
						"s_remark" : reply_content
					}, function(rst) {

						if (rst.status == 200) {
							layerSuccMsg("评论成功", 180);

							$text_div.prev().val("评论…");
							$text_div.parent().removeClass("text-box-on");

						}
						;

					}, 'json');
				}

			});

			// 评论按键事件
			$(textArea).keyup(function() {
				var val = this.value;
				var len = val.length;
				var els = this.parentNode.children;
				var btn = els[1];
				var word = els[2];
				if (len <= 0 || len > 140) {
					btn.className = 'btn btn-off';
				} else {
					btn.className = 'btn';
				}
				word.innerHTML = len + '/140';
			});
		});

	}

	// 详情内点击评论输入框展开事件
	$("#show_textarea").on('click', function() {

		$(this).parent().addClass("text-box-on");

		$(this).val($(this).val() == '评论…' ? '' : $(this).val());

	});

	// 详情内点击评论输入框收缩事件
	$("#show_textarea").blur(function() {

		var value = $(this).val();

		if (value == '') {
			$(this).val("评论…");
			$(this).parent().removeClass("text-box-on");
		}

	});

	// 详情内评论
	$("#detail_reply").click(
			function() {

				var ctxPath = $("#path").val();

				var cssl = $(this).css("btn-off");

				// 按钮灰色不能提交(字数超过140)
				if (cssl == '') {

					// 评论内容
					var reply_content = $("#show_textarea").val();
					var eid = $("#detail_eid").val();
					var type = $("#detail_type").val();

					$.post("extension/record.htm", {
						"l_bid" : eid,
						"l_type" : type,
						"s_remark" : reply_content
					}, function(rst) {

						if (rst.status == 200) {
							layerSuccMsg("评论成功", 180);

							var loginUser = rst.data.loginUser;
							var reply_time = rst.data.createTimeForMat;

							var recordHtml = $("#recordDemo").html();

							var userName = '我';
							var userIcon = '';

							// 默认头像
							if (loginUser['icon'] == ''
									|| loginUser['icon'] == null
									|| loginUser['icon'] == "null") {
								userIcon = ctxPath
										+ '/chat/images/defaultSingleIcon.png';
							} else {
								userIcon = loginUser['icon'];
							}

							var recordList = recordHtml.replace(
									'##record_remark##', reply_content)
									.replace('##record_time##', reply_time)
									.replace('##record_icon##', userIcon)
									.replace('##record_userName##', userName)
									.replace('none', 'block');

							$("#detail_recordList").append(recordList);

							$("#show_textarea").val('');
						}

					}, 'json');
				}
			});

	// 详情内评论字数输入框监控
	$("#show_textarea").keyup(function() {
		var val = this.value;
		var len = val.length;
		var els = this.parentNode.children;
		var btn = els[1];
		var word = els[2];
		if (len <= 0 || len > 140) {
			btn.className = 'btn btn-off';
		} else {
			btn.className = 'btn';
		}
		word.innerHTML = len + '/140';
	});

	$("#ddx_middle").niceScroll({
		cursorcolor : "#bbbcbe",
		cursoropacitymax : 1,
		touchbehavior : false,
		cursorwidth : "6px",
		cursorborder : "0",
		oneaxismousemode:false,
		cursorborderradius : "5px"
	});
	$(".ddx_middle_GZ_GZQ_detail").niceScroll({
		cursorcolor : "#bbbcbe",
		cursoropacitymax : 1,
		touchbehavior : false,
		cursorwidth : "6px",
		cursorborder : "0",
		oneaxismousemode:false,
		cursorborderradius : "5px"
	});
	$(".ddx_middle_GZ_GZQ_self").niceScroll({
		cursorcolor : "#bbbcbe",
		cursoropacitymax : 1,
		touchbehavior : false,
		cursorwidth : "6px",
		cursorborder : "0",
		oneaxismousemode:false,
		cursorborderradius : "5px"
	});

	if ($.browser.msie && $.browser.version.substr(0, 1) < 7) {
		$('li').has('ul').mouseover(function() {
			$(this).children('ul').css('visibility', 'visible');
		}).mouseout(function() {
			$(this).children('ul').css('visibility', 'hidden');
		});
	}

	/* Mobile */
	$('#menu-wrap').prepend('<div id="menu-trigger">Menu</div>');
	$("#menu-trigger").on('click', function() {
		$("#menu").slideToggle();
	});

	// iPad
	var isiPad = navigator.userAgent.match(/iPad/i) != null;
	if (isiPad)
		$('#menu ul').addClass('no-transition');

	// 格式化日期
	function formateDate(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var h = date.getHours();
		var mi = date.getMinutes();
		m = m > 9 ? m : '0' + m;
		return y + '-' + m + '-' + d + ' ' + h + ':' + mi;
	}

};

// 返回列表
function changeToListPage(show, hide, hide2) {

	$("#" + hide).hide();
	$("#" + hide2).hide();
	$("#" + show).show();

}

// 详情
function showDetail(eid) {

	var ctxPath = $("#path").val();

	$.ajax({
		'url' : "extension/findDetail/" + eid + ".htm",
		'type' : "get",
		'async' : false,
		'dataType' : 'json',
		'success' : function(rst) {

			var obj = rst.data;

			// 头像和姓名
			var uname = obj['uname'];
			var type = obj['type'];
			var icon = obj['icon'];

			// 设置eid
			$("#detail_eid").val(obj['eid']);
			// 设置类型
			$("#detail_type").val(type);

			var userIcon = '';

			// 默认头像
			if (icon == '' || icon == null || icon == "null") {
				userIcon = ctxPath + '/chat/images/defaultSingleIcon.png';
			} else {
				userIcon = icon;
			}

			var type_img = '';

			var type_name = "";

			if (type == 42) {

				type_name = "商品推广";
				type_img = ctxPath + "/chat/images/ddx/work/sptg.png";

			} else if (type == 43) {

				type_name = "活动推广";
				type_img = ctxPath + "/chat/images/ddx/work/hdtg.png";

			} else if (type == 44) {

				type_name = "合作推广";
				type_img = ctxPath + "/chat/images/ddx/work/hztg.png";

			}

			share_title = type_name;
			share_summary = type_name;
			share_Url = obj.shareUrl;

			$("#detail_btype").attr("src", type_img);
			$("#detail_userName").text(uname + ":" + type_name);
			$("#detail_remark").text(obj['remark']);
			$("#detail_icon").attr("src", userIcon);
			$("#detail_title").text(obj['name']);
			$("#detail_time").text(obj['createTime']);

			// 附件列表
			var annexList = obj['annexList'];
			// 评论列表
			var recordList = obj['recordList'];

			var aLiAddress = obj['aLiAddress'];
			var jdAddress = obj['jdAddress'];
			var owAddress = obj['owAddress'];
			var taobaoAddress = obj['taobaoAddress'];
			var tianmaoAddress = obj['tianmaoAddress'];
			var wdAddress = obj['wdAddress'];

			var addStr = "";

			if (aLiAddress != "") {
				addStr += '<a href="#" data-ars="' + aLiAddress
						+ '"><img class="addressCs" src="' + ctxPath
						+ '/chat/images/ddx/work/ali.png"/></a>';
			}
			if (jdAddress != "") {
				addStr += '<a href="#" data-ars="' + jdAddress
						+ '"><img class="addressCs" src="' + ctxPath
						+ '/chat/images/ddx/work/jd.png"/></a>';
			}
			if (owAddress != "") {
				addStr += '<a href="#" data-ars="' + owAddress
						+ '"><img class="addressCs" src="' + ctxPath
						+ '/chat/images/ddx/work/ow.png"/></a>';
			}
			if (taobaoAddress != "") {
				addStr += '<a href="#" data-ars="' + taobaoAddress
						+ '"><img class="addressCs" src="' + ctxPath
						+ '/chat/images/ddx/work/tbao.png"/></a>';
			}
			if (tianmaoAddress != "") {
				addStr += '<a href="#" data-ars="' + tianmaoAddress
						+ '"><img class="addressCs" src="' + ctxPath
						+ '/chat/images/ddx/work/tmao.png"/></a>';
			}
			if (wdAddress != "") {
				addStr += '<a href="#" data-ars="' + wdAddress
						+ '" ><img class="addressCs" src="' + ctxPath
						+ '/chat/images/ddx/work/weid.png"/></a>';
			}

			$("#detail_address").html(addStr);

			var $detail_address = $("#detail_address").children();

			$.each($detail_address, function(i, e) {
				
				$(e).click(function(){
					
					var address = $(this).data("ars");
					
					window.open(address);
					
				});
				
			});
			
			
			// 附件
			var fileStr = "";
			var imgStr = "";
			$.each(annexList, function(i, e) {

				var showType = e['showType'];
				var srcName = e['srcName'];
				var path = e['path'];
				if (showType == 2) {
					imgStr += '<a href="' + path + '"><img class="head" src="'
							+ path + '" alt="' + srcName + '"/></a>';
				}
				$("#detail_img").html(imgStr);
				$("#detail_file").html(fileStr);
			});

			// 评论
			var recordListHtml = "";
			$.each(recordList, function(i, e) {

				// 评论例子HTML
				var recordHtml = $("#recordDemo").html();
				var userId = e['userId'];

				// 登录用户
				var loginUser = obj['loginUser'];

				var userName = '';
				var userIcon = '';

				if (loginUser['eid'] == userId) {
					userName = '我';
					var loginIcon = loginUser['icon'];
					if (loginIcon == '' || loginIcon == null
							|| loginIcon == "null") {
						userIcon = ctxPath
								+ '/chat/images/defaultSingleIcon.png';
					} else {
						userIcon = loginIcon;
					}
				} else {

					userName = e['userName'];

					var notImIcon = e['icon'];

					if (notImIcon == '' || notImIcon == null
							|| notImIcon == "null") {
						userIcon = ctxPath
								+ '/chat/images/defaultSingleIcon.png';
					} else {
						userIcon = notImIcon;
					}

				}

				var recordList = recordHtml.replace('##record_remark##',
						e['remark'])
						.replace('##record_time##', e['createTime']).replace(
								'##record_icon##', userIcon).replace(
								'##record_userName##', userName).replace(
								'none', 'block');

				recordListHtml += recordList;

			});

			$("#detail_recordList").html('');
			$("#detail_recordList").html(recordListHtml);

		}
	});
}

// 查询自己发送的工作
function getMyWord() {

	var path = $("#path").val();

	$.ajax({
		'url' : "extension/findPageMyExtension/1.htm",
		'type' : "post",
		'async' : false,
		'dataType' : 'json',
		'success' : function(rst, statusText) {
			var list = rst.data;

			var ouer = rst.otherData.loginUser;

			var icon1 = '';

			// 默认头像
			if (ouer['icon'] == '' || ouer['icon'] == null
					|| ouer['icon'] == "null") {
				icon1 = path + '/chat/images/defaultSingleIcon.png';
			} else {
				icon1 = ouer['icon'];
			}

			$("#my_loginIcon").attr("src", icon1);

			if (list.length == 0) {
				layerMsg("暂无数据", 120);
			}

			var all_list = "";

			for (var i = 0; i < list.length; i++) {
				var obj = list[i];

				// 单条工作的例子HTML
				var list_content = $("#my_demo").html();

				var type = obj['type'];
				var createTime = obj['createTime'];

				var month = createTime.substring(0, 2);

				var day = createTime.substring(3, 5);

				var eid = obj['eid'];
				// var isReder = obj['isReder'];
				var title = obj['name'];

				var type_name = "";
				var type_img = "";

				if (type == 42) {
					type_name = "商品推广";
					type_img = path + "/chat/images/ddx/work/sptg.png";
				} else if (type == 43) {
					type_name = "活动推广";
					type_img = path + "/chat/images/ddx/work/hdtg.png";
				} else if (type == 44) {
					type_name = "合作推广";
					type_img = path + "/chat/images/ddx/work/hztg.png";
				}

				var list_html = list_content.replace('none', "block").replace(
						'##my_eid##', eid).replace('##my_type_name##',
						type_name).replace('##my_title##', title).replace(
						'##my_type_img##', type_img).replace('##my_day##', day)
						.replace('##my_month##', month).replace('##my_type##',
								type);

				var list_aa = list_html.replace('##my_eid##', eid);

				all_list += list_aa;

			}

			$("#my_list").html(all_list);
		},
		'error' : function() {
			layerMsg("数据加载失败");
		},
	});

}

// 查询发送给自己和自己创建的工作
function getMyWordAndReceive() {

	var path = $("#path").val();

	$.ajax({
		'url' : "extension/findPageExtension/1.htm",
		'type' : "post",
		'async' : false,
		'dataType' : 'json',
		'success' : function(rst) {
			var list = rst.data;
			var ouer = rst.otherData.loginUser;

			var icon1 = '';

			// 默认头像
			if (ouer['icon'] == '' || ouer['icon'] == null
					|| ouer['icon'] == "null") {
				icon1 = path + '/chat/images/defaultSingleIcon.png';
			} else {
				icon1 = ouer['icon'];
			}

			$("#loginIcon").attr("src", icon1);

			if (list.length == 0) {
				layerMsg("暂无数据", 120);
			}

			var all_list = "";

			for (var i = 0; i < list.length; i++) {
				var obj = list[i];

				// 单条工作的例子HTML
				var list_content = $("#demo").html();

				var type = obj['type'];
				var createTime = obj['createTime'];
				var eid = obj['eid'];
				var icon = obj['icon'];
				var userId = obj['userId'];
				var title = obj['name'];
				var uname = obj['uname'];

				var type_name = "";
				var type_img = "";

				// GOODS_EXTENSION(42), // 商品推广
				// ACTIVITY_EXTENSION(43), // 活动推广
				// COOPERATION_EXTENSION(44);// 合作推广

				if (type == 42) {
					type_name = "商品推广";
					type_img = path + "/chat/images/ddx/work/sptg.png";
				} else if (type == 43) {
					type_name = "活动推广";
					type_img = path + "/chat/images/ddx/work/hdtg.png";
				} else if (type == 44) {
					type_name = "合作推广";
					type_img = path + "/chat/images/ddx/work/hztg.png";
				}

				// 头像和姓名
				var name = '';
				if (ouer['eid'] == userId) {
					name = "我";
				} else {
					name = uname;
				}

				var userIcon = '';

				// 默认头像
				if (icon == '' || icon == null || icon == "null") {
					userIcon = path + '/chat/images/defaultSingleIcon.png';
				} else {
					userIcon = icon;
				}

				var list_html = list_content.replace("##list_icon##", userIcon)
						.replace("##btype##", type).replace("##list_replyId##",
								eid).replace('##list_name##', name).replace(
								'##list_type_name##', type_name).replace(
								"##list_title##", title).replace(
								"##list_time##", createTime).replace(
								'##list_workType##', type_img).replace('none',
								"block").replace('##list_btype##', type)
						.replace('##list_eid##', eid);

				all_list += list_html;

			}

			$("#list").html(all_list);
		},
		'error' : function() {
			layerMsg("数据加载失败");
		},
	});

};

