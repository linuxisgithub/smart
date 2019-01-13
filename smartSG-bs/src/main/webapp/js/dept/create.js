$(function() {
	var path = $("#contextPath").val();
	var type = $("#type").val();
	var dtype = $("#dtype").val();
	$("#return_btn").click(function() {
		closeWin();
	});
	$("#reset_btn").click(function() {
		$("#mainForm")[0].reset();
	});
	$("#mainJob").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#lessJob").select2({
		minimumResultsForSearch: search_detault,
	});

	if(type == 2 || type == 3 || type == 4 || type == 5) {
		var init = type;
		var initD = dtype;
		if(init == 5 || init == 2) {
			init = 2;
            initD = -1;
		}
		var defaultVal = $("#pid").val();
		var $s_pid = $("#s_pid");
		var p_depts = initDeptsByType($s_pid, {
			type: parseInt(init) - 1, 
			dtype: parseInt(initD),
			flag: true,
			defaultVal: defaultVal
		});
		$s_pid.change(function() {
			var pid = $(this).val();
			var p_dept = p_depts[pid];
			$("#pname").val(p_dept.name);
			$("#pid").val(pid);
		});
	}
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var name = $.trim($("#name").val());
		if(name == "") {
			$.prompt.message("部门名称不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if(type == 2 || type == 3 || type == 4) {
			var pid = $("#pid").val();
			if(pid == -1 || $.trim(pid) == "") {
				$.prompt.message("请选择一个归属部门！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
		}
		jsonAjax.post(path + "/sysDepartment/save.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				closeWin();
			} else {
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	/*$("#upload").click(function() {
		$.prompt.layerUpload();
	});*/
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});