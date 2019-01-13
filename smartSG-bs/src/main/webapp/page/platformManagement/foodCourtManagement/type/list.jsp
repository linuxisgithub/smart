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
			<button class="pagination_btn add_btn" id="add_btn">新增食阁类型</button>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var url = "${ctx}/sg/type/data/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "类型名称", width: "25%"},
			{label: "描<span class='wzkg'></span>述", width: "25%"},
			{label: "状<span class='wzkg'></span>态", width: "25%", classs: ['th-bgcolor']},
			{label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']},
			{label: "删<span class='wzkg'></span>除", width: "10%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "name"},
			{field: "description"},
			{field: "status",format:format},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toEdit},
			{fhtml: "<img src='${ctx}/images/icons/clear_icon.png'/>", func: toDelete},
		]
	});
	
	$("#add_btn").click(function() {
		parent.$.prompt.layerUrl("${ctx}/sg/type/load.htm");
	});
});
function format(item){
	var html = "";
	if(item.status == 1) {
		html += '<span style="margin-right: 20px;" onclick="changeStatus('+item.eid+',1,'+item.status+');"><input type="radio" id="eid_'+item.eid+'_1" value="1" name="use_'+item.eid+'" checked="checked">&nbsp;&nbsp;启用</span>';
		html += '<span onclick="changeStatus('+item.eid+',0,'+item.status+');"><input type="radio" id="eid_'+item.eid+'_0" value="0" name="use_'+item.eid+'">&nbsp;&nbsp;停用</span>';
	} else {
		html += '<span style="margin-right: 20px;" onclick="changeStatus('+item.eid+',1,'+item.status+');"><input type="radio" id="eid_'+item.eid+'_1" value="1" name="use_'+item.eid+'">&nbsp;&nbsp;启用</span>';
		html += '<span onclick="changeStatus('+item.eid+',0,'+item.status+');"><input type="radio" id="eid_'+item.eid+'_0" value="0" name="use_'+item.eid+'" checked="checked">&nbsp;&nbsp;停用</span>';
	}
	return html;
}
function toEdit(page){
	parent.$.prompt.layerUrl("${ctx}/sg/type/load.htm?eid=" + page.item.eid);
}
function toDelete(page){
	layer.confirm("确认删除该食阁类型吗?", { title: "删除确认",btn: ['确认','取消'] }, function (index) {
		var param = {eid: page.item.eid};
		var path = $("#contextPath").val();
		jsonAjax.post(path + "/sg/type/remove.htm", param, function(response) {
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
function changeStatus(eid, status, currStatus){
	flag = false;
	if(currStatus == status) {
		return false;
	}
	var param = {eid: eid, status: status};
	var path = $("#contextPath").val();
	jsonAjax.post(path + "/sg/type/updateStatus.htm", param, function(response) {
		if(response.status == 200) {
			parent.$.prompt.message("保存成功！", $.prompt.msg);
		} else {
			parent.$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
		}
		window.location.href = window.location.href;
	});
}
</script>
</body>
</html>