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
			<c:if test="${type eq 1}">
			</c:if>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var type = ${type};
	var dtype = ${dtype};
	var str = '部门';
	if(type == 2){
		str = '食阁管理公司';
	}else if(type == 3){
		str = '食阁名称';
	}else if(type == 4){
		str = '摊位名称';
	}else if(type == 5){
		str = '食阁拥有主体';
	}
	var url = "${ctx}/sysUser/ddx/"+type+"/"+dtype+"/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "IM账号", width: "15%"},
			{label: "姓<span class='wzkg'></span>名", width: "10%"},
			{label: "所属" + str, width: "15%"},
			{label: "职<span class='wzkg'></span>务", width: "10%"},
			{label: "性<span class='wzkg'></span>别", width: "5%"},
			{label: "电<span class='wzkg'></span>话", width: "10%"},
			{label: "初始密码", width: "10%"},
			{label: "状<span class='wzkg'></span>态", width: "15%", classs: ['th-bgcolor']},
			{label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "imAccount"},
			{field: "name"},
			{field: "deptName"},
			{field: "job"},
			{field: "sex"},
			{field: "telephone"},
			{field: "showPassword"},
			{field: "imStatus", format: format},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toDetail}
		]
	});
	
	
	function toDetail(page) {
		parent.$.prompt.layerUrl("${ctx}/sysUser/load.htm?eid=" + page.item.eid+"&isDDX=1", 1010);
	}
	
	
});

function format(item) {
	var html = "";
	if(item.imStatus == 1) {
		html += '<span style="margin-right: 20px;" onclick="changeStatus('+item.eid+',1,'+item.imStatus+');" ><input type="radio" id="eid_'+item.eid+'_1" value="1" name="use_'+item.eid+'" checked="checked">&nbsp;&nbsp;启用</span>';
		html += '<span onclick="changeStatus('+item.eid+',0,'+item.imStatus+');"><input type="radio" id="eid_'+item.eid+'_0" value="0" name="use_'+item.eid+'">&nbsp;&nbsp;停用</span>';
	} else {
		html += '<span style="margin-right: 20px;" onclick="changeStatus('+item.eid+',1,'+item.imStatus+');"><input type="radio" id="eid_'+item.eid+'_1" value="1" name="use_'+item.eid+'">&nbsp;&nbsp;启用</span>';
		html += '<span onclick="changeStatus('+item.eid+',0,'+item.imStatus+');"><input type="radio" id="eid_'+item.eid+'_0" value="0" name="use_'+item.eid+'" checked="checked">&nbsp;&nbsp;停用</span>';
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