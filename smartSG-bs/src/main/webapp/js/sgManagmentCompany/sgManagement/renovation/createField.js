$(function() {
	var path = $("#contextPath").val();

	$("#fieldType").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#isShow").select2({
		minimumResultsForSearch: search_detault,
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
		if($.trim($("#name").val()) == "") {
			$.prompt.message("字段名称不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		jsonAjax.post(path + "/sgField/save.htm",$("#mainForm").serialize(), function(response) {
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