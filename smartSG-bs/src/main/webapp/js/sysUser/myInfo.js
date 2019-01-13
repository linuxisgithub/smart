$(function() {
	var path = $("#contextPath").val();
	$("#return_btn").click(function() {
		closeWin();
	});
	
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var password = $("#password").val();
		var back_pass = password;
		if($.trim($("#password").val().length )< 6 || $.trim($("#password").val().length ) > 20){
			$.prompt.message("密码长度应为6-20位！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if(password != "******") {
			password = MD5Util(MD5Util(password));
			$("#password").val(password);
		}
		jsonAjax.post(path + "/sysUser/self/info/save.htm",$("#mainForm").serialize(), function(data,response) {
			if(data.status == 200) {
				parent.$.prompt.message("修改个人信息成功", $.prompt.msg);
				//parent.createTips.message("修改个人信息成功！", createTips.ok);
				closeWin();
			} else {
				$("#password").val(back_pass);
				parent.$.prompt.message("修改个人信息失败！", $.prompt.error);
				//parent.createTips.message("修改个人信息失败！", createTips.ok);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});