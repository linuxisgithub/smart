$(function() {
	var path = $("#contextPath").val();
	var btype = $("#btype").val();
	
	var $s_deptId = $("#s_deptId");
	var default_deptid = $("#deptId").val();
	var p_depts = initDeptsByType($s_deptId, {
		type: -1, 
		flag: true, 
		defaultVal: default_deptid
	});
	$s_deptId.change(function() {
		var eid = $(this).val();
		var item = p_depts[eid];
		$("#deptName").val(item.name);
		$("#deptId").val(eid);
		$s_userId.html("");
		$("#userId").val("-1");
		$("#userName").val("请选择");
		p_users = initUserByDeptId($s_userId, {
			deptId: eid, 
			flag: true
		});
	});
	
	var $s_userId = $("#s_userId");
	var p_users = initUserByDeptId($s_userId, {
		deptId: default_deptid, 
		flag: true, 
		defaultVal: $("#userId").val()
	});
	$s_userId.change(function() {
		var eid = $(this).val();
		var item = p_users[eid];
		$("#userName").val(item.name);
		$("#userId").val(eid);
	});
	
	$("#contentNum").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#approvalLevel").select2({
		minimumResultsForSearch: search_detault,
	});
	
	$("#return_btn").click(function() {
		closeWin();
	});
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var title = $.trim($("#title").val());
		if(title == "") {
			$.prompt.message("报告名称不能为空！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var deptId = $.trim($("#deptId").val());
		if(deptId == "" || deptId == "-1") {
			$.prompt.message("请选择一个发起部门！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var userId = $.trim($("#userId").val());
		if(userId == "" || userId == "-1") {
			$.prompt.message("请选择一个发起人！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		
		var approvalId = $.trim($("#approvalId").val());
		if(approvalId == "") {
			$.prompt.message("请设置批审流程！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		jsonAjax.post(path + "/settings/report/templet/save.htm", $("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("保存成功！", $.prompt.msg);
				parent.refreshContent();
				closeWin();
			} else {
				$(this).prop('disabled', false);
				$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
			}
		});
	});
	$("#add_appr").click(function() {
		var url = path + "/approvalset/load/detail.htm?btype=" + btype;
		var approvalId = $("#approvalId").val();
		if(approvalId != "") {
			url += "&eid=" + approvalId;
		}
		$.prompt.layerUrl2({url: url, width: 800,height: 450});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});