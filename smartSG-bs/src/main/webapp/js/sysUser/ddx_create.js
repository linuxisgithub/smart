$(function() {
	var path = $("#contextPath").val();
	var userType = $("#userType").val();
	var dtype;
	findDeptsByType($("#s_deptId"),-1);
	$("#return_btn").click(function() {
		closeWin();
	});
	$("#reset_btn").click(function() {
		$("#mainForm")[0].reset();
	});
	
	$("#level").select2({
		minimumResultsForSearch: search_detault,
	});

	if(userType == 2 || userType == 3 || userType == 4 || userType == 5) {
		dtype = 2;
	}else if(userType == 1){
		dtype = 1;
		userType = -1;
	}
	var defaultVal = $("#deptId").val();
	var $s_deptId = $("#s_deptId");
	var p_depts = initDeptsByType($s_deptId, {
		type: userType,
		dtype: dtype,
		flag: true,
		defaultVal: defaultVal
	});
	$s_deptId.change(function() {
		var deptId = $(this).val();
		var p_dept = p_depts[deptId];
		$("#deptName").val(p_dept.name);
		$("#deptId").val(deptId);
	});
	
	
	
	//提交表单前设置为不可用
	$("select[name=s_deptId]").attr("disabled",true);
	$("input[name=job]").attr("disabled",true);
	$("select[name=level]").attr("disabled",true);
	$("input[name=name]").attr("disabled",true);
	$("input[name=telephone]").attr("disabled",true);
	$("input[name=imAccount]").attr("disabled",true);
	
	
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		
		//提交时设置为可用
		$("select[name=s_deptId]").attr("disabled",false);
		$("input[name=job]").attr("disabled",false);
		$("select[name=level]").attr("disabled",false);
		$("input[name=name]").attr("disabled",false);
		$("input[name=telephone]").attr("disabled",false);
		$("input[name=imAccount]").attr("disabled",false);
		
		jsonAjax.post(path + "/sysUser/updateImStatus.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				closeWin();
			} else {
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				//$(this).prop('disabled', true);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});