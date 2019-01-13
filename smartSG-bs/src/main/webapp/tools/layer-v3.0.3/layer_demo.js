var _path = $("#contextPath").val();
document.write("<script type='text/javascript' src='"+_path+"/tools/layer-v3.0.3/layer.js'></script>");
var settings = {
	height: {
		setContent: function(options) {
			if(options.other == undefined || options.other == "") {
				options.other = 0;
			}
			if(options.w_class == undefined || options.w_class == "") {
				options.w_class = "content";
			}
			var height = $(window).height();
			if($(document).height() > height) {
				height = $(document).height();
			}
			height = height - 80 - 62 - options.other;layerUrl
			$("." + options.w_class).css("height", height + "px");
			$("." + options.w_class).css("overflow", "auto");
		},
		setTopAndContent: function(options) {
			if(options.other == undefined || options.other == "") {
				options.other = 0;
			}
			if(options.w_class == undefined || options.w_class == "") {
				options.w_class = "top-content";
			}
			var height = $(window).height();
			if($(document).height() > height) {
				height = $(document).height();
			}
			height = height - 62 - options.other;
			$("." + options.w_class).css("height", height + "px");
			$("." + options.w_class).css("overflow", "auto");
		},
		setHeight: function(options) {
			if(options.other == undefined || options.other == "") {
				options.other = 0;
			}
			if(options.w_class == undefined || options.w_class == "") {
				options.w_class = "top-content";
			}
			var height = $(window).height();
			if($(document).height() > height) {
				height = $(document).height();
			}
			height = height - options.other;
			$("." + options.w_class).css("height", height + "px");
			$("." + options.w_class).css("overflow", "auto");
		}
	}
}
jQuery.approval = {
	/**
	 * 查看批审流程
	 * 使用方式：$.approval.detail(approvalId);
	 * @param approvalId
	 */	
	detail: function(approvalId) {
		var url = _path + "/approvalset/show/detail.htm";
		if(approvalId != undefined && approvalId != null) {
			url += "?eid=" + approvalId;
		}
		parent.$.prompt.layerUrl(url, 850, 535);
	},
	/**
	 * 查看批审记录
	 * 使用方式：$.approval.detail(bid, btype);
	 * @param bid
	 * @param btype
	 */	
	record: function(bid, btype, noRemark) {
		if(noRemark == undefined || noRemark == "") {
			noRemark = 0;
		}
		var url = _path + "/approvalset/toApprovalRecord.htm?l_bid=" + bid + "&i_type=" + btype;
		url += "&i_noRemark=" + noRemark;
		$.prompt.layerUrl(url, 850, 560);
	}
}
jQuery.prompt = {
	percent_h: 0.80,
	percent_w: 0.75,
	ok: "ok",
	msg: "msg",
	warn: "warn",
	error: "error",
	layerCapital: function(options) {
		if(options.title == undefined || options.title == null) {
			options.title = false;
		}
		if(options.width == undefined || options.width == null) {
			options.width = 900;
		}
		if(options.height == undefined || options.height == null) {
			options.height = 500;
		}
		if(options.frameIndex == undefined) {
			options.frameIndex = 1;
		}
		var url = _path+"/capitalDetail/load.htm?bid="+options.bid+"&btype="+options.btype+"&frameIndex="+options.frameIndex+"&busnessTitle="+options.busnessTitle+"&sumMoney="+options.sumMoney;
		var defaule = {
			title: options.title,
			type: 2,
		  	area: [options.width + "px", options.height + "px"],
		  	closeBtn: 0,
		  	scrollbar: false,
		  	shadeClose:true,
		  	content: url
		};
		defaule = $.extend({}, defaule, options);
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	layerSearch: function(options) {
		var win_width = $(window).width() * 0.85;
		var win_height = $(window).height() * 0.85;
		options.width = win_width;
		options.height = win_height;
		if(options.width < 1100) {
			options.width = 1100;
		}
		options.width = 1100;
		if(options.height < 650) {
			options.height = 650;
		}
		$.prompt.layerUrl2(options);
	},
	/**
	 * 打开抄送
	 * 使用方式：$.prompt.layerCc();
	 * 使用方式：parent.$.prompt.layerCc({frameIndex:2});
	 * @param options
	 * @returns
	 */
	layerCc: function(options) {
		if(options.title == undefined || options.title == null) {
			options.title = "选择抄送人";
		}
		options.title = encodeURI(encodeURI(options.title));
		if(options.width == undefined || options.width == null) {
			options.width = 600;
		}
		if(options.height == undefined || options.height == null) {
			options.height = 500;
		}
		if(options.frameIndex == undefined) {
			options.frameIndex = 1;
		}
		if(options.cc == undefined) {
			options.cc = "cc";
		}
		if(options.ccUid == undefined) {
			options.ccUid = "ccUid";
		}
		if(options.ccName == undefined) {
			options.ccName = "ccName";
		}
		var url = _path + "/main/toCc.htm?frameIndex=" + options.frameIndex;
		url += "&cc=" + options.cc;
		url += "&ccUid=" + options.ccUid;
		url += "&ccName=" + options.ccName;
		url += "&title=" + options.title;
		options.title = false;
		var defaule = {
			title: false,
			type: 2,
		  	area: [options.width + "px", options.height + "px"],
		  	closeBtn: 0,
		  	scrollbar: false,
		  	shadeClose:true,
		  	content: url
		};
		defaule = $.extend({}, defaule, options);
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	/**
	 * 使用方式：$.prompt.layerHtml({$object:$("#demo"),width:600,height:400});
	 * 使用方式：$.prompt.layerHtml({content:"这里放html代码",width:600,height:400});
	 * 说明：打开包裹在id=demo的标签内的html
	 * @param $object
	 * @returns
	 */
	layerHtml: function(options) {
		if(options.width == undefined || options.width == null) {
			options.width = $(window).width() * jQuery.prompt.percent_w;
		}
		if(options.height == undefined || options.height == null) {
			options.height = $(window).height() * jQuery.prompt.percent_h;
		}
		var content = "";
		var $object = options.$object;
		if($object != undefined) {
			content = $object.html();
		} else {
			content = options.content;
		}
		var defaule = {
			title: false,
			type: 1,
		  	area: [options.width + "px", options.height + "px"],
		  	closeBtn: 0,
		  	scrollbar: false,
		  	shadeClose:true,
		  	content: content
		};
		defaule = $.extend({}, defaule, options);
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	/**
	 * 显示附件
	 * 使用方式：$.prompt.layerShowUpload(bid, btype);
	 * @param options
	 * @returns
	 */
	layerShowUpload: function(bid, btype) {
		var url = _path + "/annex/list.htm?bid=" + bid + "&btype=" + btype;
		var defaule = {
			title: "查看附件",
			type: 2,
			area: ["870px", "470px"],
		    closeBtn: 1,
		  	scrollbar: false,
		  	shadeClose:true,
		  	content: url
		};
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	
	/**
	 * 显示附件 (名称居中)
	 * 使用方式：$.prompt.layerShowUpload(bid, btype);
	 * @param options
	 * @returns
	 */
	layerShowUploadOther: function(bid, btype) {
		var url = _path + "/annex/list.htm?bid=" + bid + "&btype=" + btype;
		var defaule = {
			title: "",	
			type: 2,
			area: ["900px", "500px"],
		    closeBtn: 0,
		  	scrollbar: false,
		  	shadeClose:true,
		  	content: url
		};
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	/**
	 * 使用默认配置：$.prompt.layerUpload();
	 * 使用参数方式：$.prompt.layerUpload({title:"标题",fileNames: "fileNames",fileInfos: "fileInfos"});
	 * @param options
	 * @returns
	 */
	layerUpload: function(options) {
		if(options.title == undefined || options.title == null) {
			options.title = "上传附件";
		}
		var defaule = {
			title: options.title,
			fileNames: "fileNames", // 显示附件的标题的控件ID
			fileInfos: "fileInfos", // 保存附件的详情的控件ID
			type: 2,
			area: ["870px", "470px"],
			closeBtn: 1,
			scrollbar: false,
			shadeClose:true,
			content: ""
		};
		
		if(options.isFrame == undefined) {
			options.isFrame = 1;
		}
		defaule = $.extend({}, defaule, options);
		defaule.content = _path + "/page/home/upload.jsp?isFrame="+options.isFrame+"&fileNames=" + defaule.fileNames + "&fileInfos=" + defaule.fileInfos;
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	/**
	 * 使用方式：$.prompt.layerUrl("http://www.baidu.com", 600, 400, "标题");
	 * @param url
	 * @param title
	 * @param width
	 * @param height
	 * @returns
	 */
	layerUrl: function(url, width, height, title) {
		if(title == undefined || title == null) {
			title = false;
		}
		if(width == undefined || width == null) {
			width = $(window).width() * jQuery.prompt.percent_w;
		}
		if(height == undefined || height == null) {
			height = $(window).height() * jQuery.prompt.percent_h;
		}
		var defaule = {
			title: title,
			type: 2,
		  	area: [width + "px", height + "px"],
		  	closeBtn: 0,
		  	scrollbar: false,//是否允许浏览器出现滚动条
		  	shadeClose:true,//是否点击遮罩关闭
		  	resize:false,
		  	content: url
		};
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	/**
	 * 使用方式：$.prompt.layerUrl2({url:"http:www.baidu.com", title:"标题"});
	 * $.prompt.layerUrl2({url:"http:www.baidu.com", width:500, height:500});
	 * @param url
	 * @param title
	 * @param width
	 * @param height
	 * @returns
	 */
	layerUrl2: function(options) {
		if(options.title == undefined || options.title == null) {
			options.title = false;
		}
		if(options.title == "分享到") {
			options.title = ['分享到', 'text-align: center;padding-left:75px;'];
		}
		if(options.width == undefined || options.width == null) {
			options.width = $(window).width() * jQuery.prompt.percent_w;
		}
		if(options.height == undefined || options.height == null) {
			options.height = $(window).height() * jQuery.prompt.percent_h;
		}
		var defaule = {
			title: options.title,
			type: 2,
		  	area: [options.width + "px", options.height + "px"],
		  	closeBtn: 0,
		  	scrollbar: false,
		  	shadeClose:true,
		  	content: options.url
		};
		defaule = $.extend({}, defaule, options);
		var _layerIndex = layer.open(defaule);
		return _layerIndex;
	},
	/**
	 * 提示窗口：含确定关闭按钮
	 * 使用方式：$.prompt.tip("标题", $.prompt.msg);
	 * @param title
	 * @param type 不传默认$.prompt.msg
	 */
	tip: function(title, type) {
		var icon = 6;
		if(type == jQuery.prompt.ok) {
			icon = 1;
		} else if(type == jQuery.prompt.msg) {
			icon = 6;
		} else if(type == jQuery.prompt.warn) {
			icon = 0;
		} else if(type == jQuery.prompt.error) {
			icon = 2;
		}
		layer.alert(title, {
			closeBtn: 0,
			icon: icon,
			move: false
		});
		$(".layui-layer-btn a").text("确定").css("background-color", "#8CBD62");
		$(".layui-layer-btn a").text("确定").css("border-color", "#8CBD62");
	},
	/**
	 * 父页面提示窗口：含确定关闭按钮
	 * 使用方式：$.prompt.parentTip("标题", $.prompt.msg);
	 * @param title
	 * @param type 不传默认$.prompt.msg
	 */
	parentTip: function(title, type) {
		var icon = 6;
		if(type == jQuery.prompt.ok) {
			icon = 1;
		} else if(type == jQuery.prompt.msg) {
			icon = 6;
		} else if(type == jQuery.prompt.warn) {
			icon = 0;
		} else if(type == jQuery.prompt.error) {
			icon = 2;
		}
		parent.layer.alert(title, {
			closeBtn: 0,
			icon: icon,
			move: false
		});
		parent.$(".layui-layer-btn a").text("确定").css("background-color", "#8CBD62");
		parent.$(".layui-layer-btn a").text("确定").css("border-color", "#8CBD62");
	},
	/**
	 * 提示窗口
	 * 使用方式：$.prompt.message("注册成功！", $.prompt.msg);
	 * @param title
	 * @param type 不传默认$.prompt.msg
	 * @param time 多少毫秒后关闭
	 */
	message: function(title, type, options) {
		if(options == undefined) {
			options = {};
		}
		if(options.time == undefined) {
			options.time = 3000;
		}
		if(type == jQuery.prompt.ok) {
			layer.msg(title, {
				time : options.time,
				icon : 1
			});
		} else if(type == jQuery.prompt.error) {
			layer.msg(title, {
				time : options.time,
				icon : 2
			});
		} else if(type == jQuery.prompt.warn) {
			layer.msg(title, {
				time : options.time,
				icon : 0
			});
		} else if(type == jQuery.prompt.msg) {
			layer.msg(title, {time : options.time});
		} else {
			layer.msg(title, {time : options.time});
		}
	}
}