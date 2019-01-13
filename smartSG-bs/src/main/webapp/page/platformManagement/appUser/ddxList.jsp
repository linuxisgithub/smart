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
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var url = "${ctx}/sysUser/appDdx/data/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "IM账号", width: "20%"},
			{label: "用户昵称", width: "20%"},
			{label: "电<span class='wzkg'></span>话", width: "20%"},
			{label: "注册账号", width: "20%"},
			{label: "状<span class='wzkg'></span>态", width: "20%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "imAccount"},
			{field: "name"},
			{field: "telephone"},
			{field: "account"},
			{field: "imStatus", format: format}
		]
	});
});

function format(item) {
	var html = "";
	if(item.imStatus == 1) {
		html += '<span style="margin-right: 40px;"><input type="radio" id="eid_'+item.eid+'_1" onclick="changeStatus('+item.eid+',1,'+item.imStatus+');" value="1" name="use_'+item.eid+'" checked="checked" style="">&nbsp;&nbsp;启用</span>';
		html += '<span><input type="radio" id="eid_'+item.eid+'_0" onclick="changeStatus('+item.eid+',0,'+item.imStatus+');" value="0" name="use_'+item.eid+'">&nbsp;&nbsp;停用</span>';
	} else {
		html += '<span style="margin-right: 40px;"><input type="radio" id="eid_'+item.eid+'_1" onclick="changeStatus('+item.eid+',1,'+item.imStatus+');" value="1" name="use_'+item.eid+'">&nbsp;&nbsp;启用</span>';
		html += '<span><input type="radio" id="eid_'+item.eid+'_0" onclick="changeStatus('+item.eid+',0,'+item.imStatus+');" value="0" name="use_'+item.eid+'" checked="checked">&nbsp;&nbsp;停用</span>';
	}
	return html;
}

var falg = false;
function changeStatus(eid, imStatus, currStatus){
	if(currStatus == imStatus) {
		return false;
	}
	if(falg == false) {
		falg = true;
		var param = {eid: eid, imStatus: imStatus};
		var path = $("#contextPath").val();
		jsonAjax.post(path + "/sysUser/updateImStatus.htm", param, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("保存成功！", $.prompt.ok);
			} else {
				parent.$.prompt.message("保存失败：" + response.msg, $.prompt.error);
			}
			window.location.href = window.location.href;
		});
	}
}
</script>
</body>
</html>