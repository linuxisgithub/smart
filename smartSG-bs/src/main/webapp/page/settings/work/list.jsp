<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表格样式</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
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
						<th style="width: 30%;">考勤规则</th>
						<th style="width: 30%;">应用部门</th>
						<th style="width: 30%;">状<span class='wzkg'></span>态</th>
						<th class="th-bgcolor2 tr_splc" style="width: 10%;min-width: 80px;">考勤设置</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		</form>
		<div class="grayline"></div>
		<div class="pages">
			<!-- <button class="pagination_btn pagination_btn_right submit-btn" id="confirm_btn">完成</button> -->
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript">
$(function() {
	var path = $("#contextPath").val();
	var isSpec = "${USER_IN_SESSION.company.isSpec}";
	var datas = {};
	jsonAjax.post(path + "/work/settings/list.htm",{}, function(response) {
		if(response.status == 200) {
			var bodyNode = $("tbody");
			$.each(response.data, function(index, each) {
				var $tr = $("<tr/>");
				var trData = each;
				datas[trData['eid']] = trData;
				$("<td/>").text(trData['name']).attr("id", trData['eid'] + "_" + "name").appendTo($tr);
				
				var useTd = $("<td/>");
				useTd.text(trData['useName']).attr("id", trData['eid'] + "_" + "useName").attr("data-eid", trData['eid']);
				useTd.css("cursor", "pointer");
				useTd.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					toSetUse(item);
				});
				useTd.appendTo($tr);
				
				var statusTd = $("<td/>");
				var span_radio_1 =  $("<span/>").attr("id", trData['eid'] + "_status_1").attr("data-eid", trData['eid']);
				span_radio_1.css("padding", "0 10").css("margin-right", "5px").css("cursor", "pointer");
				var input_radio_1 =  $("<input/>").attr("type", "radio").attr("value", "1").css("cursor", "pointer")
					.attr("name", "eid_" + trData['eid']);
				span_radio_1.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					changeStatus(item, 1);
				});
				
				var span_radio_0 =  $("<span/>").attr("id", trData['eid'] + "_status_0").attr("data-eid", trData['eid']);
				span_radio_0.css("margin-left", "5px").css("cursor", "pointer");
				var input_radio_0 =  $("<input/>").attr("type", "radio").attr("value", "0").css("cursor", "pointer")
					.attr("name", "eid_" + trData['eid']);
				span_radio_0.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					changeStatus(item, 0);
				});
				if(trData['status'] == 1) {
					input_radio_1.attr("checked", "checked");
				} else {
					input_radio_0.attr("checked", "checked")
				}
				input_radio_1.appendTo(span_radio_1);
				span_radio_1.append("&nbsp;&nbsp;启用");
				span_radio_1.appendTo(statusTd);
				
				input_radio_0.appendTo(span_radio_0);
				span_radio_0.appendTo(statusTd);
				span_radio_0.append("&nbsp;&nbsp;停用");
				
				span_radio_1.appendTo(statusTd);
				span_radio_0.appendTo(statusTd);
				statusTd.appendTo($tr);
				
				var imgNode = $("<img/>").attr("src", path + "/images/icons/gray_edit.png").attr("data-eid", trData['eid']);
				imgNode.css("cursor", "pointer");
				imgNode.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					toDetail(item);
				});
				var editTd = $("<td/>").attr("id", trData['eid'] + "_edit_td");
				imgNode.appendTo(editTd);
				editTd.appendTo($tr);
				
				$tr.appendTo(bodyNode);
			});
			var $tr = $("<tr/>").attr("id", "add_tr");
			var addTd = $("<td/>").attr("colspan", 4);
			var addHtml = '<div class="setting_btn" id="add_btn" style="width:75px;">';
			addHtml += '<img src="'+ path +'/images/icons/settingAdd_icon.png" style="position: relative;top:3px;">';
			addHtml += '<span style="margin-left:10px;">添加</span>';
			addHtml += '</div>';
			addHtml += '<div class="setting_btn" id="save_btn" style="display: none;width:75px;">';
			addHtml += '<img src="'+ path +'/images/icons/save.png" style="position: relative;top:3px;">';
			addHtml += '<span style="margin-left:10px;">保存</span>';
			addHtml += '</div>';
			addTd.append(addHtml);
			addTd.appendTo($tr);
			$tr.appendTo(bodyNode);
			$("#add_btn").click(function() {
				toAdd();
			});
		} else {
			$.prompt.message("获取数据失败：" + response.msg, $.prompt.msg);
		}
	});
	function toAdd() {
		$("#add_btn").hide();
		$("#save_btn").show();
		$("#save_btn").click(function() {
			doSave($(this));
		});
		var html = "";
		
		html += '<tr id="new_tr">';
		
		html += '<td><input type="text" name="name" id="name"></td>';
		
		html += '<td style="cursor: pointer;" id="select_btn">';
		html += '<div class="select_btn" id="use_btn"><label style="margin-right: 10px;cursor: pointer;">请选择</label></div>';
		html += '<span id="use_btn_text" style="display: none;"></span>';
		html += '<input type="hidden" name="useId" id="useId">';
		html += '<input type="hidden" name="useName" id="useName">';
		html += '<input type="hidden" name="useKind" id="useKind">';
		html += '</td>';
		
		html += '<td>';
		html += '<span id="new_status_1" style="padding: 0px 10px;margin-right: 5px;cursor: pointer;"><input type="radio" value="1" name="status" id="status_1" checked="checked">&nbsp;&nbsp;启用</span>';
		html += '<span id="new_status_0" style="margin-left: 5px; cursor: pointer;"><input type="radio" value="0" name="status" id="status_0">&nbsp;&nbsp;停用</span>';
		html += '</td>';
		
		html += '<td style="cursor: pointer;" id="toSetTimes">';
		html += '<img alt="" src="'+path+'/images/icons/gray_edit.png">';
		html += '<input type="hidden" name="workTime" id="workTime">';
		html += '<input type="hidden" name="signLate" id="signLate">';
		html += '<input type="hidden" name="leaveEarly" id="leaveEarly">';
		html += '</td>';
		
		html += '</tr>';
		$("#add_tr").before(html);
		
		$("#new_status_1").click(function() {
			$("#status_1").prop("checked", "checked");
		});
		$("#new_status_0").click(function() {
			$("#status_0").prop("checked", "checked");
		});
		
		$("#select_btn").click(function() {
			toSetUse();
		});
		$("#toSetTimes").click(function() {
			toSetTimes();
		});
	}
	function doSave($this) {
		$this.prop('disabled', true);
		var name = $.trim($("#name").val());
		if(name == "") {
			$.prompt.message("考勤规则不能为空！", $.prompt.msg);
			$this.prop('disabled', false);
			return;
		}
		var useId = $.trim($("#useId").val());
		if(useId == "") {
			$.prompt.message("请设置应用部门！", $.prompt.msg);
			$this.prop('disabled', false);
			return;
		}
		var workTime = $.trim($("#workTime").val());
		if(workTime == "") {
			$.prompt.message("请设置考勤时间!", $.prompt.msg);
			$this.prop('disabled', false);
			return;
		}
		jsonAjax.post(path + "/work/settings/save.htm", $("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				parent.top.$.prompt.message("保存成功！", $.prompt.msg);
				window.location.href = window.location.href;
			} else {
				$this.prop('disabled', false);
				$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	function toSetTimes(item) {
		var url = path + "/work/settings/set/time.htm";
		if(item && item.eid != undefined) {
			url += "?eid=" + item.eid;
		}
		parent.$.prompt.layerUrl2({url: url, width: 850, height: 550});
	}
	function toSetUse(item) {
		var url = path + "/work/settings/set/use.htm";
		if(item && item.eid != undefined) {
			url += "?type=1&eid=" + item.eid;
		} else {
			url += "?type=2";
		}
		parent.$.prompt.layerUrl2({url: url, width: 360, height: 442});
	}
	function toDetail(item) {
		toSetTimes(item);
	}
	var falg = false;
	function changeStatus(item, status) {
		if(item.status == status) {
			return false;
		}
		if(falg == false) {
			falg = true;
			if(item.useKind == 1 && status == 0) {
				if(isSpec == 1){
					parent.$.prompt.message("不能停用，全机构至少要有一个规则为启用状态！", $.prompt.msg, 5000);
				}else{
					parent.$.prompt.message("不能停用，全公司至少要有一个规则为启用状态！", $.prompt.msg, 5000);
				}
				window.location.href = window.location.href;
			} else {
				var param = {eid: item.eid, status: status, useId: item.useId, useKind: item.useKind};
				jsonAjax.post(path + "/work/settings/update/status.htm", param, function(response) {
					if(response.status == 200) {
						parent.$.prompt.message("保存成功！", $.prompt.msg);
					} else {
						parent.$.prompt.message("保存失败：" + response.msg, $.prompt.error, 5000);
					}
					window.location.href = window.location.href;
				});
			}
		}
	}
});
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