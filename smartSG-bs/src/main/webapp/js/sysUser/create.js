$(function() {
	var path = $("#contextPath").val();
	var userType = $("#userType").val();
	var l = $("#l").val();
	var dtype;
	$("#return_btn").click(function() {
		closeWin();
	});
	$("#reset_btn").click(function() {
		$("#mainForm")[0].reset();
	});
	$("#level").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#sex").select2({
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
		if(deptId != "" || deptId != null) {
			var p_dept = p_depts[deptId];
			$("#deptName").val(p_dept.name);
			$("#deptId").val(deptId);
			$("#deptCode").val(p_dept.code);
		} else {
			$("#deptName").val("");
			$("#deptId").val(-1);
			$("#deptCode").val("");
		}
	});

	/*//提交表单前设置为不可用
	var userType = $("#userType").val();
	if(userType!=undefined&&userType==2){
		$("select[name=s_deptId]").attr("disabled",true);
		$("input[name=job]").attr("disabled",true);
		$("select[name=level]").attr("disabled",true);
		$("input[name=name]").attr("disabled",true);
		$("select[name=sex]").attr("disabled",true);
		$("select[name=education]").attr("disabled",true);
		$("input[name=telephone]").attr("disabled",true);
	}*/
	$("#account").change(function() {
		var account = $.trim($("#account").val());
		if(!validatePhone(account)){
			$.prompt.message("系统账号请输入手机号码！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		$("#telephone").val(account);
	});
	
	$("#telephone").change(function() {
		var telephone = $.trim($("#telephone").val());
		if(!validatePhone(telephone)){
			$.prompt.message("电话请输入手机号码！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		$("#account").val(telephone);
	});
	var userType = $("#userType").val();
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		/*//提交时设置为可用
		$("select[name=s_deptId]").attr("disabled",false);
		$("input[name=job]").attr("disabled",false);
		$("select[name=level]").attr("disabled",false);
		$("input[name=name]").attr("disabled",false);
		$("select[name=sex]").attr("disabled",false);
		$("select[name=education]").attr("disabled",false);
		$("input[name=telephone]").attr("disabled",false);*/
		
		if($("#s_deptId").val() == -1) {
			$.prompt.message("请选择一个部门！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#name").val()) == "") {
			$.prompt.message("姓名不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#account").val()) == "") {
			$.prompt.message("系统账号不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		var eid = $.trim($("#eid").val());
		if(eid == ""){
			if($.trim($("#password").val()) == "") {
				$.prompt.message("密码不能为空！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
			if(!validatePhone($.trim($("#account").val()))){
				$.prompt.message("系统账号请输入手机号码！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
		}
		if(!validatePhone($.trim($("#telephone").val()))){
			$.prompt.message("电话请输入手机号码！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#password").val().length )< 6 || $.trim($("#password").val().length ) > 20){
			$.prompt.message("密码长度应为6-20位！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		var password = $("#password").val();
		if (eid == "") {
			$("#showPassword").val(password);
		}
		var back_pass = password;
		if(password != "******") {
			password = MD5Util(MD5Util(password));
			$("#password").val(password);
		}
		var status = $("input[name=status]:checked").val();
		if(eid != "" && l == 3 && status == 2) {
			$.prompt.message("管理员账号不能停用！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		jsonAjax.post(path + "/sysUser/save.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				parent.refreshContent();
				closeWin();
			} else {
				$("#password").val(back_pass);
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
	$("#black_btn").click(function() {
		$(this).prop('disabled', true);
		if(eid != "" && l == 3) {
			$.prompt.message("管理员账号不能加入黑名单！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		layer.confirm("确认把该用户加入黑名单吗?", {title: "黑名单确认", btn: ['确认', '取消']}, function (index) {
			var eid = $.trim($("#eid").val());
			var param = {eid: eid};
			var path = $("#contextPath").val();
			jsonAjax.post(path + "/sysUser/toBlack.htm", param, function (response) {
				if (response.status == 200) {
					parent.$.prompt.message("加入成功！", $.prompt.ok);
					parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
					parent.refreshContent();
					closeWin();
					layer.close(index);
				} else {
					parent.$.prompt.message("加入失败：" + response.msg, $.prompt.error);
					$("#black_btn").prop('disabled', false);
					layer.close(index);
				}
				//window.location.href = window.location.href;
			});
		}, function (index) {
			$("#black_btn").prop('disabled', false);
			layer.close(index);
		});
		$("#black_btn").prop('disabled', false);
	});

});