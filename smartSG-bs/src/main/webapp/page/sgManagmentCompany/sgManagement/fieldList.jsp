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
			<button class="pagination_btn return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
			<button class="pagination_btn add_btn" id="add_btn">添加字段</button>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
	<input type="hidden" id="bid" value="${bid}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
	var bid = $("#bid").val();
$(function() {
	var url = "${ctx}/sgField/data/list.htm?bid="+bid;
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "字段名称", width: "20%"},
			{label: "默认值", width: "20%"},
			{label: "字段类型", width: "20%"},
			{label: "显示状态", width: "20%", classs: ['th-bgcolor']},
			{label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']},
			{label: "删<span class='wzkg'></span>除", width: "10%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "name"},
			{field: "defaultValue"},
			{field: "fieldType",format:format},
			{field: "isShow",forma:forma},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toEdit},
			{fhtml: "<img src='${ctx}/images/icons/clear_icon.png'/>", func: toDelete},
		]
	});

	$("#add_btn").click(function() {
		parent.$.prompt.layerUrl("${ctx}/sgField/load.htm?bid="+bid);
	});
	$("#return_btn").click(function() {
		window.history.back();
	});
});
function toEdit(page){
	parent.$.prompt.layerUrl("${ctx}/sgField/load.htm?eid=" + page.item.eid);
}
function toDelete(page){
	layer.confirm("确认删除该字段吗?", { title: "删除确认",btn: ['确认','取消'] }, function (index) {
		var param = {eid: page.item.eid};
		var path = $("#contextPath").val();
		jsonAjax.post(path + "/sgField/remove.htm", param, function(response) {
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

function format(item){
    var str = "";
    if (item.fieldType == 1){
        str = "文本";
    }else if(item.fieldType){
        str = "数字";
    }else if(item.fieldType){
        str = "货币";
    }else if(item.fieldType){
        str = "日期";
    }
    return str;
}

function forma(item){
    var str = "";
    if (item.isShow == 1){
        str = "是";
    }else{
        str = "否";
    }
    return str;
}

</script>
</body>
</html>