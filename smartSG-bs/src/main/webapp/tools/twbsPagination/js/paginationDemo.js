var _path = $("#contextPath").val();
var scripts = document.getElementsByTagName("script");
var nowScript = scripts[scripts.length - 1];
var src = nowScript.src;
var type = "";
if(src.lastIndexOf("?") != -1) {
	var _type = src.substring(src.lastIndexOf("?") + 1);
	var map = _type.split("=");
	if(map[1] == "settings") {
		type = "_settings";
	}
}
document.write("<link rel='stylesheet' href='"+_path+"/tools/twbsPagination/css/pagination"+type+".css' />");
document.write("<script type='text/javascript' src='"+_path+"/tools/layer-v3.0.3/layer_demo.js'></script>");
document.write("<script type='text/javascript' src='"+_path+"/tools/twbsPagination/js/jquery.twbsPagination.min.js'></script>");
document.write("<script type='text/javascript' src='"+_path+"/chat/js/ddx_gd/jquery.nicescroll.js'></script>");

function initPagination($pagination, options) {
	var data = $("#queryForm").serialize();//查询条件
	defaultOpts = $.extend({}, defaultOpts, options, {$pagination: $pagination}, {searchData: data});
	var titles = defaultOpts.titles;
	$("#pagination_table").html("<thead></thead><tbody></tbody>");
	var headNode = $("<tr/>");
	$.each(titles, function(index, title) {
		$th = $("<th/>").html(title.label);
		if(typeof(title.visible) != "undefined" && title.visible == false) {
			$th.css("display", "none");
		}
		if(typeof(title.width) != "undefined") {
			$th.css("width", title.width);
		}
		if(typeof(title.classs) != "undefined") {
			$.each(title.classs, function(index, each) {
				$th.addClass(each);
			});
		}
		var column = title;
		var order = column.order;
		if(typeof(order) != "undefined") {
			$th.css("cursor","pointer");
			var $img_ud1  = $("<img/>").addClass("orderImg_up1").attr("id", column.orderName + "_ud1").attr("width", "12px").attr("height", "14px").css("display", "none")
				.css("margin-left","5px").css("position","relative").css("top","1px").attr("src", _path + "/images/ud1.png");
			var $img_desc = $("<img/>").addClass("orderImg").attr("id", column.orderName + "_desc").attr("width", "12px").attr("height", "14px").css("display", "none")
				.css("margin-left","5px").css("position","relative").css("top","1px").attr("src", _path + "/images/desc.png");
			var $img_asc  = $("<img/>").addClass("orderImg").attr("id", column.orderName + "_asc").attr("width", "12px").attr("height", "14px").css("display", "none")
				.css("margin-left","5px").css("position","relative").css("top","1px").attr("src", _path + "/images/asc.png");
			var currOrder = column.currOrder;
			if(typeof(currOrder) != "undefined" && currOrder == true) {
				defaultOpts.orderName = column.orderName;
				defaultOpts.order = column.order;
				if(column.order == "desc") {
					$img_desc.css("display", "inline-block");
				} else {
					$img_asc.css("display", "inline-block");
				}
			} else {
				$img_ud1.css("display", "inline-block");
			}
			$img_ud1.appendTo($th);
			$img_desc.appendTo($th);
			$img_asc.appendTo($th);
			$th.click(function() {
				if(defaultOpts.orderName == column.orderName) {
					if(defaultOpts.order == "desc") {
						defaultOpts.order = "asc";
					} else {
						defaultOpts.order = "desc";
					}
				} else {
					defaultOpts.order = column.order;
				}
				defaultOpts.orderName = column.orderName;
				$(".orderImg").hide();
				$(".orderImg_up1").show();
				$(this).find("img.orderImg_up1").hide();
				$("#" + defaultOpts.orderName + "_" + defaultOpts.order).show();
				gotoPage(1);
			});
		}

		/*var date = column.date;
		if(typeof(date) != "undefined") {
			$th.css("cursor","pointer");
			var $img_ud1  = $("<img/>").addClass("orderImg_up1").attr("width", "12px").attr("height", "14px")
				.css("margin-left","5px").css("position","relative").css("top","1px").attr("src", _path + "/images/ud1.png");
			$img_ud1.appendTo($th);
			$th.click(function() {
				//跳后台弹出页面
				var url = _path+"/procurementorder/toYearMonth.htm";
				parent.parent.$.prompt.layerUrl2({url: url, width: 500,height:300});
			});
		}*/

		$th.appendTo(headNode);
	});
	headNode.appendTo($("#pagination_table>thead"));
	$pagination.twbsPagination(defaultOpts);
	gotoPage(defaultOpts.startPage);
}
function setTotalPages(totalPages, currentPage) {
	if(!currentPage) {
		currentPage = 1;
		defaultOpts.currentPage = currentPage;
	}
	defaultOpts.$pagination.twbsPagination('destroy');
	defaultOpts.$pagination.twbsPagination($.extend({},defaultOpts,{startPage: currentPage,totalPages: totalPages}));
}
function gotoPage(page) {
	defaultOpts.currentPage = page;
	var _url = appendParam(defaultOpts.url, "pageNumber", page);
	_url = appendOrder(_url);
	$.ajax({
		'url': _url,
		'type': "post",
		'data': defaultOpts.searchData,
		'dataType':'json',
		'beforeSend': function(xmlHttp) {
			if(layer) {
				xmlHttp.loadIndex = layer.load();
			}
		},
		'success':function(response,statusText){
			if(response.status == 200) {
				$("#pagination_table>tbody").html("");
				if(response.pageCount != defaultOpts.totalPages) {
					defaultOpts.totalPages = response.pageCount;
					setTotalPages(response.pageCount);
				}
				defaultOpts.items = {};
				$.each(response.data, function(index, item) {
					if(typeof(item.eid) == "undefined") {
						item['eid'] = index;
					}
					var trNode = $("<tr/>");
					if(typeof(defaultOpts.rowClick) != "undefined") {
						trNode.css("cursor", "pointer");
						trNode.click(function() {
							var page = {"currentPage": defaultOpts.currentPage, "index": index + 1, "item": item};
							callFun(defaultOpts.rowClick, page);
						});
					}

					var columns = defaultOpts.columns;
					$.each(columns, function(_index, column) {
						var $td = $("<td/>");
						if(typeof(column.visible) != "undefined" && column.visible == false) {
							$td.css("display", "none");
						}
						if(typeof(column.width) != "undefined") {
							$td.css("width", column.width);
						}
						if(typeof(column.colspan) != "undefined") {
							$td.attr("colspan", column["colspan"]);
						}
						var func = column.func;
						var fhtml = column.fhtml;
						if(typeof(func) != "undefined") {
							var $link = $("<a/>").prop("href", "javascript:void(0);");
							if(typeof(fhtml) != "undefined") {
								$link.append(fhtml);
							} else {
								if(typeof(column.format) != "undefined") {
									$link.html(format(item));
								} else if(typeof(column.forma) != "undefined") {
									$link.html(forma(item));
								} else if(typeof(column.form) != "undefined") {
									$link.html(form(item));
								} else {
									$link.text(item[column.field]);

								}


							}

							$td.click(function() {
								if(typeof(column.args) != "undefined") {
									callFun(func, item[column.args]);
								} else {
									var page = {"currentPage": defaultOpts.currentPage, "index": index + 1, "item": item};
									callFun(func, page);
								}
							});

							/*$link.click(function() {
								if(typeof(column.args) != "undefined") {
									callFun(func, item[column.args]);
								} else {
									var page = {"currentPage": defaultOpts.currentPage, "index": index + 1, "item": item};
									callFun(func, page);
								}
							});*/
							$link.appendTo($td);
							$td.appendTo(trNode);
						} else {
							var columnData = item[column.field];
							if(columnData != "undefined" && (columnData == null || columnData == "null")) {
								item[column.field] = "";
							}
							if(typeof(column.format) != "undefined") {
								$td.html(format(item)).appendTo(trNode);
							} else if(typeof(column.forma) != "undefined") {
								$td.html(forma(item)).appendTo(trNode);
							} else if(typeof(column.form) != "undefined") {
								$td.html(form(item)).appendTo(trNode);
							}  else {
								$td.text(item[column.field]).appendTo(trNode);
							}
						}
					});
					defaultOpts.items[item.eid] = item;
					trNode.appendTo($("#pagination_table>tbody"));
				});

				if(typeof(response.otherData) != "undefined") {
					var totalCol = defaultOpts.totalCol;
					defaultOpts.otherData = response.otherData;
					if(totalCol.length > 0) {
						var trNode = $("<tr/>");
						var j = 1;
						$.each(defaultOpts.totalCol, function(_index, column) {
							var columnData = response.otherData[column["field"]];
							if(columnData != "undefined" && (columnData == null || columnData == "null")) {
								columnData = "";
								response.otherData[column["field"]] = "";
							}
							if(typeof(column.visible) != "undefined" && column.visible == false) {
								$("<td/>").css("display", "none").attr("colspan", column["colspan"]).text(columnData).appendTo(trNode);;
							}else{
								$("<td/>").attr("colspan", column["colspan"]).text(columnData).appendTo(trNode);

							}
							j = j + parseInt(column["colspan"]);
						});
						trNode.appendTo($("#pagination_table>tbody"));
					}
				}

			}
		},
		'error':function(xmlHttp, e1, e2){
			if(layer) {
				$.prompt.message("网络繁忙，请稍候！", $.prompt.msg);
			}
		},
		'complete': function(xmlHttp, textStatus) {
			try{
				callBack();
			}catch(exception){}
			if(layer) {
				layer.close(xmlHttp.loadIndex);
			}
		}
	});
}
function callFunWithDownArgs(func) {
	func.call(window);
}
function callFun(func, args) {
	func.call(window, args);
}
/**
 * 给URL追加参数
 */
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
function appendOrder(url) {
	var _url = url;
	if(defaultOpts.orderName != null) {
		_url = appendParam(_url, "s_orderBy", defaultOpts.orderName + "_" + defaultOpts.order);
	}
	return _url;
}
var defaultOpts = {
	url: null,
	titles:[],
	columns:[],
	totalCol:[],
	items:{},//保存的数据
	$pagination: null,
	orderName:null,
	order:null,
	searchData: null,
	otherData:null,
	totalPages: 1, //总页数：默认1
	currentPage:1,
	startPage: 1, //开始页：默认1
	visiblePages: 0, //最大可见页标签：默认5
	initiateStartPageClick: false, //当插件初始化时是否触发onPageClick：默认true
	hideOnlyOnePage: false, //只有一页时是否隐藏分页控件：默认false
	first: "首页",
	prev: "<img src='"+_path+"/images/icons/pgUp_btn.png'/>",
	next: "<img src='"+_path+"/images/icons/pgDn_btn.png'/>",
	last: "末页",
	loop: false, //是否循环，即到最后页时再点下一页是否调到第一页：默认false
	paginationClass: "pagination", //给当前元素加上class：默认pagination
	nextClass: "page-item next page-btn", //next按钮的css class：默认page-item next
	prevClass: "page-item prev page-btn", //prev按钮的css class：默认page-item prev
	lastClass: "page-item last page-item-hide", //prev按钮的css class：默认page-item last
	firstClass: "page-item first page-item-hide", //prev按钮的css class：默认page-item first
	pageClass: "page-item", // 每个页码按钮的css class：默认page-item
	activeClass: "active", //当前页的css class：默认active
	disabledClass: "disabled", //无法点击按钮的css class：默认disabled
	onPageClick: function (event, page) {
		gotoPage(page);
	}
};
function refreshList() {
	gotoPage(defaultOpts.currentPage);
}
function initShareBtn() {
	$("#shareBtn").click(function(){
		var btype = $("#btype").val();
		var s_searchData = defaultOpts.searchData;
		var param = {};

		var tems = s_searchData.split("&");
		for (var i = 0; i < tems.length; i++) {
			var tem = tems[i];
			var map = tem.split("=");
			param[map[0]] = map[1];
		}
		s_searchData = JSON.stringify(param);
		s_searchData = encodeURI(encodeURI(s_searchData));
		var s_pageNumber = defaultOpts.currentPage;
		var url = _path + '/share/toShare.htm?type=' + btype + '&pageNumber='+s_pageNumber+'&searchData='+s_searchData;
		url = appendOrder(url);
		parent.$.prompt.layerUrl2({url: url, width: 450,height:280,title:"分享到"});

		/*var btype = $("#btype").val();
		var s_searchData = encodeURI(encodeURI(defaultOpts.searchData));
		
		var s_pageNumber = defaultOpts.currentPage;
		var url = _path + '/share/toShare.htm?type=' + btype + '&pageNumber='+s_pageNumber+'&searchData='+s_searchData;
		parent.$.prompt.layerUrl2({url: url, width: 450,height:280,title:"分享到"});*/
	});

}
$(function(){
	try{

		$("body,.tab-pages").niceScroll({
			cursorcolor: "#bbbcbe",//#CC0071 光标颜色
			cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
			touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
			cursorwidth: "5px", //像素光标的宽度
			cursorborder: "0", // 游标边框css定义
			cursorborderradius: "5px",//以像素为光标边界半径
			autohidemode: true //是否隐藏滚动条
		});

	}catch(error){

	}
});