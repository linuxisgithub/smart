<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
</head>
<body style="overflow-x: hidden;">
<div class="mainRight-tab-conW">				
	<div class="tab-con table">
		<div style="display: none;" alt="隐藏表单域">
			<input type="hidden" name="btype" id="btype" value="${btype}">
		</div>
		<div class="yj-add-top">
			<div class="yj-add-title">
			考勤设置
			</div>
		</div>
		<div class="settings-table">
			<table>
				<thead>
					<tr>
						<th style="width: 20%;">工作日设置</th>
						<th style="width: 20%;">签到时间</th>
						<th style="width: 20%;">午休开始时间</th>
						<th style="width: 20%;">午休结束时间</th>
						<th style="width: 20%;">签退时间</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%;">
							星期一
							<input type="hidden" name="date_1" id="date_1" value="1">
							<input type="hidden" name="remark_1" id="remark_1" value="monday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_1" id="startTime_1"
							value="${model.startTime_1}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_1\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_1" id="mid_startTime_1"
							value="${model.mid_startTime_1}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_1\')}',maxDate:'#F{$dp.$D(\'mid_endTime_1\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_1" id="mid_endTime_1"
							value="${model.mid_endTime_1}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_1\')}',maxDate:'#F{$dp.$D(\'endTime_1\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_1" id="endTime_1"
							value="${model.endTime_1}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_1\')}'})">
						</td>
					</tr>
					<tr>
						<td style="width: 20%;">
							星期二
							<input type="hidden" name="date_2" id="date_2" value="2">
							<input type="hidden" name="remark_2" id="remark_2" value="tuesday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_2" id="startTime_2"
							value="${model.startTime_2}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_2\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_2" id="mid_startTime_2"
							value="${model.mid_startTime_2}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_2\')}',maxDate:'#F{$dp.$D(\'mid_endTime_2\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_2" id="mid_endTime_2"
							value="${model.mid_endTime_2}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_2\')}',maxDate:'#F{$dp.$D(\'endTime_2\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_2" id="endTime_2"
							value="${model.endTime_2}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_2\')}'})">
						</td>
					</tr>
					<tr>
						<td style="width: 20%;">
							星期三
							<input type="hidden" name="date_3" id="date_3" value="3">
							<input type="hidden" name="remark_3" id="remark_3" value="wednesday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_3" id="startTime_3"
							value="${model.startTime_3}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_3\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_3" id="mid_startTime_3"
							value="${model.mid_startTime_3}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_3\')}',maxDate:'#F{$dp.$D(\'mid_endTime_3\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_3" id="mid_endTime_3"
							value="${model.mid_endTime_3}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_3\')}',maxDate:'#F{$dp.$D(\'endTime_3\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_3" id="endTime_3"
							value="${model.endTime_3}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_3\')}'})">
						</td>
					</tr>
					<tr>
						<td style="width: 20%;">
							星期四
							<input type="hidden" name="date_4" id="date_4" value="4">
							<input type="hidden" name="remark_4" id="remark_4" value="thursday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_4" id="startTime_4"
							value="${model.startTime_4}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_4\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_4" id="mid_startTime_4"
							value="${model.mid_startTime_4}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_4\')}',maxDate:'#F{$dp.$D(\'mid_endTime_4\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_4" id="mid_endTime_4"
							value="${model.mid_endTime_4}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_4\')}',maxDate:'#F{$dp.$D(\'endTime_4\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_4" id="endTime_4"
							value="${model.endTime_4}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_4\')}'})">
						</td>
					</tr>
					
					<tr>
						<td style="width: 20%;">
							星期五
							<input type="hidden" name="date_5" id="date_5" value="5">
							<input type="hidden" name="remark_5" id="remark_5" value="friday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_5" id="startTime_5"
							value="${model.startTime_5}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_5\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_5" id="mid_startTime_5"
							value="${model.mid_startTime_5}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_5\')}',maxDate:'#F{$dp.$D(\'mid_endTime_5\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_5" id="mid_endTime_5"
							value="${model.mid_endTime_5}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_5\')}',maxDate:'#F{$dp.$D(\'endTime_5\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_5" id="endTime_5"
							value="${model.endTime_5}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_5\')}'})">
						</td>
					</tr>
					
					<tr>
						<td style="width: 20%;">
							星期六
							<input type="hidden" name="date_6" id="date_6" value="6">
							<input type="hidden" name="remark_6" id="remark_6" value="saturday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_6" id="startTime_6"
							value="${model.startTime_6}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_6\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_6" id="mid_startTime_6"
							value="${model.mid_startTime_6}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_6\')}',maxDate:'#F{$dp.$D(\'mid_endTime_6\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_6" id="mid_endTime_6"
							value="${model.mid_endTime_6}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_6\')}',maxDate:'#F{$dp.$D(\'endTime_6\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_6" id="endTime_6"
							value="${model.endTime_6}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_6\')}'})">
						</td>
					</tr>
					
					<tr>
						<td style="width: 20%;">
							星期日
							<input type="hidden" name="date_7" id="date_7" value="7">
							<input type="hidden" name="remark_7" id="remark_7" value="sunday">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="startTime_7" id="startTime_7"
							value="${model.startTime_7}" 
							onfocus="WdatePicker({startDate:'09:00:00',dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'mid_startTime_7\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_startTime_7" id="mid_startTime_7"
							value="${model.mid_startTime_7}" 
							onfocus="WdatePicker({startDate:'12:00:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime_7\')}',maxDate:'#F{$dp.$D(\'mid_endTime_7\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="mid_endTime_7" id="mid_endTime_7"
							value="${model.mid_endTime_7}" 
							onfocus="WdatePicker({startDate:'13:30:00',dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_startTime_7\')}',maxDate:'#F{$dp.$D(\'endTime_7\')}'})">
						</td>
						<td style="width: 20%;">
							<input type="text" class="table_input-center" name="endTime_7" id="endTime_7"
							value="${model.endTime_7}" 
							onfocus="WdatePicker({startDate:'17:30:00', dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'mid_endTime_7\')}'})">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="position: fixed;bottom: 60px;height: 45px;line-height: 45px;">
		<span style="margin-right: 10px;">超过：</span>
		<input type="number" name="signLate" id="signLate" min="0" style="height: 26px;width: 80px;" value="${model.signLate}">分钟签到算迟到
		<span style="margin: 0 25px;">&nbsp;</span>
		<span style="margin-right: 10px;">提前：</span>
		<input type="number" name="leaveEarly" id="leaveEarly" min="0" style="height: 26px;width: 80px;" value="${model.leaveEarly}">分钟签退算早退
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" name="eid" id="eid" value="${model.eid}">
	<input type="hidden" name="type" id="type" value="${type}">
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="layer-btn-w100 submit-btn" id="save_btn">确定</button>
		<button class="layer-btn-w100 return_btn" id="return_btn">返回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/tools/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript">
$(function() {
	var path = $("#contextPath").val();
	var eid = $("#eid").val();
	var height = $(window).height();
	height = height - 125 - 60;
	$(".settings-table").css("height", height + "px");
	if(eid == "") {
		var workTime = parent.$("#content_iframe").contents().find("#workTime").val();
		var signLate = parent.$("#content_iframe").contents().find("#signLate").val();
		var leaveEarly = parent.$("#content_iframe").contents().find("#leaveEarly").val();
		if(workTime != undefined && workTime != "") {
			var workTimes = $.parseJSON(workTime);
			for (var index = 0; index < workTimes.length; index++) {
				var _workTime = workTimes[index];
				var index = _workTime.date;
				$("#startTime_" + index).val(_workTime.startTime);
				$("#endTime_" + index).val(_workTime.endTime);
			}
		}
		$("#leaveEarly").val(leaveEarly);
		$("#signLate").val(signLate);
	}
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var signLate = $.trim($("#signLate").val());
		if(!validateInteger(signLate)) {
			$.prompt.message("请设置超过分钟签到算【迟到】！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		
		var leaveEarly = $.trim($("#leaveEarly").val());
		if(!validateInteger(leaveEarly)) {
			$.prompt.message("请设置提前分钟签退算【早退】！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var workTimes = [];
		for (var index = 1; index <= 7; index++) {
			var startTime = $("#startTime_" + index).val();
			var mid_startTime = $("#mid_startTime_" + index).val();
			var mid_endTime = $("#mid_endTime_" + index).val();
			var endTime = $("#endTime_" + index).val();
			if(startTime != "" && endTime != "" && mid_startTime != "" && mid_endTime != "") {
				var workTime = {
					startTime: startTime,
					midStartTime: mid_startTime,
					midEndTime: mid_endTime,
					endTime: endTime,
					remark: $("#remark_" + index).val(),
					date: index
				};
				workTimes.push(workTime);
			} else if(!(startTime == "" && mid_startTime == "" && mid_endTime == "" && endTime == "")) {
				$.prompt.message("每个工作日的4个时间要么同时设置要么都不设置！", $.prompt.msg);
				$(this).prop('disabled', false);
				return false;
			}
		}
		if(workTimes.length == 0) {
			$.prompt.message("请最少设置一个工作日！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var _workTime = JSON.stringify(workTimes);
		if(eid != "") {
			var param = {
				workTime: _workTime,
				signLate: signLate,
				leaveEarly: leaveEarly,
				eid: eid
			};
			jsonAjax.post(path + "/work/settings/update/time.htm", param, function(response) {
				if(response.status == 200) {
					parent.top.$.prompt.message("保存成功！", $.prompt.msg);
					parent.refreshContent();
					closeWin();
				} else {
					$("#save_btn").prop('disabled', false);
					$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
				}
			});
		} else {
			parent.$("#content_iframe").contents().find("#workTime").val(_workTime);
			parent.$("#content_iframe").contents().find("#signLate").val(signLate);
			parent.$("#content_iframe").contents().find("#leaveEarly").val(leaveEarly);
			closeWin();
		}
	});
	$("#return_btn").click(function() {
		closeWin();
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});
</script>
</body>
</html>