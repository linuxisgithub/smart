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
	if(type == 3) {
		var defaultVal = $("#sgType").val();
		var $sg_type = $("#sg_type");
		var sg_types = initSgType($sg_type, {
			flag: true,
			defaultVal: defaultVal
		});
		$sg_type.change(function() {
			var eid = $(this).val();
			$("#sgType").val(eid);
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
		var pid = $("#pid").val();
		if(pid == -1 || $.trim(pid) == "") {
			$.prompt.message("请选择一个归属部门！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if(type == 4) {
			var nameCh = $("#nameCh").val();
			if($.trim(nameCh) == "") {
				$.prompt.message("请填写摊位中文名称！", $.prompt.error);
				$(this).prop('disabled', false);
				return false;
			}
		}
		jsonAjax.post(path + "/sysDepartment/save.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				if (parent.userType == 2 && type == 3){//食阁管理公司平台添加食阁 动态添加二级菜单
					var ulEle = parent.$("#203").next();// 食阁管理菜单(一级菜单)的下一个兄弟节点（ul）
					var menu = response.data;
					ulEle.append(" <li id='"+menu.id+"'>"+menu.name+"<span class='im-redNum_2' id='"+menu.id+"_dot' style='display: none;'></span></li>");
					parent.index_config.allMenus[menu.id] = menu;
					parent.index_config.firstMenus[menu.id] = menu;
					parent.index_config.secMenus[203].push(menu);
					parent.index_config.secMenuAction();
				}
				closeWin();
			} else {
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});

	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});