$(function() {
	var path = $("#contextPath").val();
    var deptId = $("#deptId").val();

	var defaultVal = $("#groupId").val();
	var $s_group = $("#s_group");
	var p_groups = initDishGroup($s_group, {
		deptId: deptId,
		flag: true,
		defaultVal: defaultVal
	});
	$s_group.change(function() {
		var groupId = $(this).val();
		if(groupId != "" || groupId != null) {
			var p_group = p_groups[groupId];
			$("#groupName").val(p_group.name);
			$("#groupId").val(groupId);
		} else {
			$("#groupName").val("");
			$("#groupId").val("");
		}
	});

	$("#return_btn").click(function() {
		closeWin();
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}

	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		/*if($.trim($("#name").val()) == "") {
			$.prompt.message("字段名称不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}*/
		jsonAjax.post(path + "/dishInfo/save.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				parent.refreshContent();
				closeWin();
			} else {
				$("#password").val(back_pass);
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
});