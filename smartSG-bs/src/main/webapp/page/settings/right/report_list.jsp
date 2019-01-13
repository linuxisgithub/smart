<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表格样式</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/settings.css"/>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js?type=settings"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<style type="text/css">
.select2-selection--single {
	height: 26px !important;
}
.select2-selection__rendered {
	height: 26px !important;
	line-height: 26px !important;
}
.submit-btn {
	background-color: #5f5f5f !important;
}
</style>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW">
		<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
		<div class="tab-pages layer-sm">
			<table class="table" id="pagination_table">
				<thead>
					<tr>
						<th>报告名称</th>
						<th>报告部门</th>
						<th>报告人</th>
						<th>报告字数</th>
						<th style="width: 90px;">资金附件</th>
						<th>批阅级数</th>
						<th>批阅字数</th>
						<th class="th-bgcolor2" style="width: 80px;">批审流程</th>
						<th class="th-bgcolor2" style="width: 80px;">使用状态</th>
						<th class="th-bgcolor2" style="width: 80px;">预<span class='wzkg'></span>览</th>
						<c:if test="${type eq 'edit'}">
						<th class="th-bgcolor2" style="width: 80px;">编<span class='wzkg'></span>辑</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<div style="display: none;" alt="隐藏表单域">
					<input type="hidden" name="eid" id="approvalId" value="">
					<input type="hidden" name="btype" id="btype" value="9">
					<input type="hidden" name="approvalPerson" id="approvalPerson" value="">
				</div>
			</table>
		</div>
		</form>
		<div class="grayline"></div>
		<div class="pages">
			<c:if test="${type eq 'create'}">
			<!-- <button class="pagination_btn pagination_btn_right submit-btn" id="save_btn">确定</button> -->
			</c:if>
			<c:if test="${type eq 'edit'}">
			<button class="pagination_btn submit-btn pagination_btn_right" id="confirm_btn">完<span class="wzkg"></span>成</button>
			</c:if>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
	<input type="hidden" id="list_type" value="${param.type}">
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/settings/right/report_list.js"></script>
<script type="text/javascript">
$(function() {
	var path = $("#contextPath").val();
	$("#confirm_btn").click(function() {
		$("#confirm_btn").prop('disabled', true);
		var confirmId = $("#confirmId").val();
		var hasConfirmId = $("#hasConfirmId").val();
		var confirmPid = $("#confirmPid").val();
		if(hasConfirmId == 0) {
			var param = {confirmId:confirmId, confirmPid:confirmPid};
			jsonAjax.post(path + "/sysSettings/menu/confirm.htm", param, function(response) {
				if(response.status == 200) {
					parent.$.prompt.message("确定成功！", $.prompt.ok);
					parent.confirmCallback(confirmId, confirmPid, response.data);
				} else {
					parent.$.prompt.message("确定失败：" + response.msg, $.prompt.error);
					$("#confirm_btn").prop('disabled', false);
				}
			});
		} else {
			$.prompt.message("您已经确定过了！", $.prompt.msg);
		}
	});
});
</script>
</body>
</html>