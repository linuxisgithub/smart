$(function() {
	var height = $(window).height();
	height = height - 60 - 60;
	$(".settings-table").css("height", height + "px");
	
	var isEdit = $("#isEdit").val();
	
	var path = $("#contextPath").val();
	findDeptsByType($("#s_deptId"),-1);
	
	$("#shareBtn").click(function(){
		var eid = $("#eid").val();
		var btype = $("#btype").val();
		var share_summary = encodeURI(encodeURI($("#share_summary").val()));
		var url = _path + '/share/toShareShow.htm?type=' + btype + '&eid='+eid+'&share_summary='+share_summary;
		parent.$.prompt.layerUrl2({url: url, width: 450,height:280,title:"分享到"});
	});
	
	$("#return_btn").click(function() {
		closeWin();
	});
	$("#reset_btn").click(function() {
		$("#mainForm")[0].reset();
	});
	
	$("#marry").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#sex").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#jobStatus").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#education").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#level").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#contract").select2({
		minimumResultsForSearch: search_detault,
	});
	
	var defaultVal = $("#deptId").val();
	var $s_deptId = $("#s_deptId");
	var p_depts = initDeptsByType($s_deptId, {
		type: -1, 
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
	if($("#eid").val() == null || $("#eid").val() == ''){
		$("#account").change(function() {
			var account = $.trim($("#account").val());
			if(!validatePhone(account)){
				$.prompt.message("系统账号请输入手机号码！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
			$("#telephone").val(account);
			$("#imAccount").val("oa_"+account);
		});
		
		$("#telephone").change(function() {
			var telephone = $.trim($("#telephone").val());
			if(!validatePhone(telephone)){
				$.prompt.message("联系电话请输入手机号码！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
			$("#account").val(telephone);
			$("#imAccount").val("oa_"+telephone);
		});
		$("#password").change(function() {
			var password = $.trim($("#password").val());
			$("#imPassword").val(password);
		});
	}else{
		$("#password").change(function() {
			var password = $.trim($("#password").val());
			$("#imPassword").val(password);
		});
	}
	var userType = $("#userType").val();
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		if($.trim($("#name").val()) == "") {
			$.prompt.message("姓名不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($("#s_deptId").val() == -1) {
			$.prompt.message("请选择一个部门！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#idCard").val()) == "") {
			$.prompt.message("身份证号不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		var telephone = $.trim($("#telephone").val());
		if(!validatePhone(telephone)) {
			$.prompt.message("请输入正确的手机号码！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		} 
		var email = $.trim($("#email").val());
		if(!validateEmail(email)) {
			$.prompt.message("请输入正确的邮箱！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		} 
		var salary = $.trim($("#salary").val());
		if(!validateDecimal(salary, 9, 2)) {
			$.prompt.message("薪酬请输入最长9位整数和2位小数！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		} 
		if($.trim($("#employTime").val()) == "") {
			$.prompt.message("入职日期不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#account").val()) == "") {
			$.prompt.message("系统账号不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}else{
			if(!validatePhone($.trim($("#account").val()))) {
				$.prompt.message("系统账号只能是手机号码！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
		}
		if($.trim($("#password").val().length )< 6 || $.trim($("#password").val().length ) > 20){
			$.prompt.message("密码长度应为6-20位！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		/*if($("#eid").val() == ""){
			if($.trim($("#password").val()) == "") {
				$.prompt.message("密码不能为空！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
		}*/
		var password = $("#password").val();
		var back_pass = password;
		if(password != "******") {
			password = MD5Util(MD5Util(password));
			$("#password").val(password);
		}
		var eid = $.trim($("#eid").val());
		var status = $("input[name=status]:checked").val();
		if(eid != "" && userType == 2 && status == 2) {
			$.prompt.message("管理员账号不能停用！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		jsonAjax.post(path + "/sysUser/save.htm",$("#mainForm").serialize(), function(data,response) {
			if(data.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				if(isEdit==1){
					window.top.refreshIframeContent();
					closeWin();
				}else{
					if($("#eid").val() != ""){
						parent.refreshContent();
						closeWin();
					}else{
						window.location.href = path+"/staffExper/load.htm?id="+data.data;
					}
				}
			} else {
				$("#password").val(back_pass);
				$.prompt.message("保存失败：" + data.msg, $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});