<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表格样式</title>
<style type="text/css">
</style>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW">
		<div class="tab-pages">
			<table id="pagination_table" style="margin-top:10px;"></table>
		</div>
		<div class="grayline"></div>
		<div class="pages">
			<ul class="pagination" id="pagination"></ul>
			<button class="pagination_btn btn" style="padding: 0px 0px;height: 36px;width: 120px;color: white;" id="copy_btn">复制并应用于</button>
			<%--<button class="pagination_btn submit-btn" id="save_btn">保<span class='wzkg'></span>存</button>--%>
			<button class="pagination_btn add_btn" id="add_btn">新增菜品</button>
			<button class="pagination_btn btn" style="padding: 0px 0px;height: 36px;width: 100px;color: white;" id="setting_btn">设<span class='wzkg'></span>置</button>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
	<input type="hidden" id="bid" value="${twId}">
	<input type="hidden" id="type" value="${type}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
	var bid = $("#bid").val();
$(function() {
	var url = "${ctx}/dishInfo/data/list/"+bid+".htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			/*{label: "菜品编号", width: "8%"},*/
			{label: "菜品其它语言名称", width: "12%"},
			{label: "英文名称", width: "12%"},
			{label: "单<span class='wzkg'></span>位", width: "8%"},
			{label: "单<span class='wzkg'></span>价", width: "8%"},
			{label: "准备时间", width: "8%"},
			{label: "二维码", width: "10%"},
			{label: "分<span class='wzkg'></span>组", width: "10%"},
			{label: "图<span class='wzkg'></span>片", width: "8%", classs: ['th-bgcolor']},
			{label: "基本信息", width: "8%", classs: ['th-bgcolor']},
			{label: "菜品特性", width: "8%", classs: ['th-bgcolor']},
			{label: "删<span class='wzkg'></span>除", width: "8%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			/*{field: "serNo"},*/
			{field: "name"},
			{field: "enName"},
			{field: "unit"},
			{field: "sellingPrice"},
			{field: "preparationTime"},
			{field: "",format:format},
			{field: "groupName"},
			{fhtml: "<img src='${ctx}/images/icons/default-img.png'/>",func: toImg},
			{fhtml: "<img src='${ctx}/images/icons/details.png'/>", func: toInfo},
			{fhtml: "<img src='${ctx}/images/icons/details.png'/>", func: toDishFeatures},
			{fhtml: "<img src='${ctx}/images/icons/clear_icon.png'/>", func: toDelete}
		]
	});

	$("#add_btn").click(function() {
		parent.$.prompt.layerUrl("${ctx}/dishInfo/load.htm?bid="+bid);
	});
	$("#setting_btn").click(function () {
		window.location.href = "${ctx}/dish/setting.htm?type=1&bid="+bid;
	})
	$("#copy_btn").click(function () {
		var type = $("#type").val();
		var url = "${ctx}/sg/to/apply.htm?bid="+bid+"&type="+type;
		parent.$.prompt.layerUrl2({url:url, width: 336, height: 498});
	})
});
function toInfo(page){
	parent.$.prompt.layerUrl("${ctx}/dishInfo/load.htm?eid=" + page.item.eid);
}
function toDishFeatures(page){
	window.location.href = "${ctx}/dishFeatures/data/list.htm?bid="+page.item.eid;
}
function toImg(page){
	parent.$.prompt.layerUrl("${ctx}/dishInfo/img.htm?eid=" + page.item.eid);
}


function format(item) {
	var html = "<img src='' style='width:35px;margin:2px 0px;' alt='无'>";
	return html;
}

function toDelete(page){
	layer.confirm("确认删除该菜品吗?", { title: "删除确认",btn: ['确认','取消'] }, function (index) {
		var param = {eid: page.item.eid};
		var path = $("#contextPath").val();
		jsonAjax.post(path + "/dishInfo/remove.htm", param, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("删除成功！", $.prompt.ok);
			} else {
				parent.$.prompt.message("删除失败：" + response.msg, $.prompt.error);
			}
			window.location.href = window.location.href;
		});
	}, function(index) {
		layer.close(index);
	});
}
</script>
</body>
</html>