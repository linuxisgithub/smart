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
			<button class="pagination_btn pagination_btn_right add_btn" id="add_btn">新增合作单位</button>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var url = "${ctx}/sysDepartment/data/5/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "部门名称", width: "15%"},
			{label: "地址", width: "15%"},
			{label: "管理对象", width: "15%"},
			{label: "所属部门", width: "15%", order: "asc", orderName: "pname"},
			{label: "正职称谓", width: "15%"},
			{label: "副职称谓", width: "15%"},
			{label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "name"},
			{field: "",format:format},
			{field: "",forma:forma},
			{field: "pname"},
			{field: "mainJob"},
			{field: "lessJob"},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toDetail}
		]
	});
	function toDetail(page) {
		parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=5&eid=" + page.item.eid + "&dtype=" + page.item.dtype);
	}
	$("#add_btn").click(function() {
		parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=5&dtype=2");
	});
});
function format(item) {
	var str = '';
	$.ajax({
		'url' : '${ctx}/sysDepartment/sgyyzAddress/' + item.eid + '.htm',
		'type' : "post",
		'async': false,
		'dataType' : 'JSON',
		'success' : function(resp) {
			str = resp.data;
		},
		'error' : function() {}
	});
	return str;
}
function forma(item) {
	return "食阁";
}
</script>
</body>
</html>