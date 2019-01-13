<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表格样式</title>
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
			<button class="pagination_btn add_btn" id="add_btn">发<span class="wzkg"></span>件</button>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
<form id="queryForm">
	<input name="s_business" value="send">
</form>
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var url = "${ctx}/suggestions/data/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "日<span class='wzkg'></span>期", width: "20%"},
			{label: "收件人", width: "20%"},
			{label: "建议类型", width: "15%"},
			{label: "内容摘要", width: "15%"},
			{label: "详<span class='wzkg'></span>情", width: "10%"},
			{label: "处理结果", width: "10%"},
			{label: "删<span class='wzkg'></span>除", width: "10%"},
		],
		columns: [
			{field: "eid", visible:false},
			{field: "sendDate"},
			{field: "receives"},
			{field: "kind"},
			{field: "content"},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toEdit},
			{field: "status"},
			{fhtml: "<img src='${ctx}/images/icons/clear_icon.png'/>", func: toDelete},
		]
	});
	
	$("#add_btn").click(function() {
		parent.$.prompt.layerUrl("${ctx}/suggestions/send/load.htm");
	});
});
function toEdit(page){
	if(page.item.msgType == "reply"){
		parent.$.prompt.layerUrl("${ctx}/suggestions/reply/load.htm?eid=" + page.item.eid);
	}else{
		parent.$.prompt.layerUrl("${ctx}/suggestions/send/load.htm?eid=" + page.item.eid);
	}
}
function toDelete(page){
	layer.confirm("确认删除该记录吗?", { title: "删除确认",btn: ['确认','取消'] }, function (index) {
		var param = {l_eid: page.item.eid, s_msgType: page.item.msgType};
		var path = $("#contextPath").val();
		jsonAjax.post(path + "/suggestions/remove.htm", param, function(response) {
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