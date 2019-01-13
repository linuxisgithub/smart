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
			<button class="pagination_btn add_btn" id="add_btn">新增公司层面</button>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script><%--?type=settings--%>
<script type="text/javascript">
$(function() {
	var url = "${ctx}/sysDepartment/data/1/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "部门名称", width: "30%"},
			{label: "正职称谓", width: "30%"},
			{label: "副职称谓", width: "30%"},
			{label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "name"},
			{field: "mainJob"},
			{field: "lessJob"},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toDetail}
		]
	});
	function toDetail(page) {
		parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=1&dtype=1&eid=" + page.item.eid);
	}
	$("#add_btn").click(function() {
		parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=1&dtype=1");
	});
});
</script>
</body>
</html>