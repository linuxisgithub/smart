$(function() {
	var path = $("#contextPath").val();
	var dept_init_finish = false;
	var currentId = $("#bid").val();
	var type = $("#type").val();
	if(type == 'sggl'){
		initDeptSggl();
	}else if(type == 'tw'/* || type == 'dish'*/){
		initDeptTw();
	}else if(type == 'sg'){
		initDeptSg();
	}


	$("#return_btn").click(function() {
		closeWin();
	});
	$("input[name='dept-all']").click(function() {
		$("input[name='dept-one']").prop("checked", this.checked);
		if(this.checked) {
			selectDeptIds = [];
			$.each(depts, function(index, item) {
				var deptId = item.eid;
				selectDeptIds.push(deptId)
			});
		} else {
			selectDeptIds = [];
		}
	});
	function initDeptSggl() {
		var url = path + "/sysDepartment/data/3/2.htm";
		jsonAjax.post(url, {}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					if (item['eid'] == currentId) {// 去除自己
						return true;
					}
					depts[item['eid']] = item;
					appendDept(item);
				});
				dept_init_finish = true;
			} else {
				$.prompt.message("获取食阁数据失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	function initDeptTw() {
		var url = path + "/tw/bindingData.htm";
		jsonAjax.post(url, {type:'tw'}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					if (item['eid'] == currentId) {// 去除自己
						return true;
					}
					depts[item['eid']] = item;
					appendDept(item);
				});
				dept_init_finish = true;
			} else {
				$.prompt.message("获取摊位数据失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	function initDeptSg() {
		var url = path + "/sg/twData.htm";
		jsonAjax.post(url, {type:'tw'}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					if (item['eid'] == currentId) {// 去除自己
						return true;
					}
					depts[item['eid']] = item;
					appendDept(item);
				});
				dept_init_finish = true;
			} else {
				$.prompt.message("获取摊位数据失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	var selectDeptIds = [];
	var depts = {}; // key为部门id,value为部门信息
	function appendDept(item) {
		var deptHtml = "";
		deptHtml += '<li>';
		deptHtml += '<div class="Li-left">'+item.name+'</div>';
		deptHtml += '<div class="Li-right">';
		deptHtml += '<label>';
		deptHtml += '<input class="person-checkbox dept" type="checkbox" name="dept-one" data-eid="'+item.eid+'" id="dept_'+item.eid+'"><span class="person-checkboxInput"></span>';
		deptHtml += '</label>';
		deptHtml += '</div>';
		deptHtml += '</li>';
		$("#dept_list").append(deptHtml);
		$("#dept_" + item.eid).click(function() {
			var deptId = $(this).data("eid");
			if(this.checked) {
				makeSelectAll();
			} else {
				$("input[name='dept-all']").prop("checked", false);
			}
			editSelectValue(this.checked,deptId);
		});
	}

	function editSelectValue(ckd,deptId) {
		if (ckd){
			selectDeptIds.push(deptId);
		}else{
			var i = $.inArray(deptId,selectDeptIds);
			selectDeptIds.splice($.inArray(deptId,selectDeptIds),1);
		}
	}

	function makeSelectAll() {
		var flag = false;
		var checkBoxs = $("input[name='dept-one']");
		$.each(checkBoxs, function(index, item) {
			if(item.checked == false) {
				flag = false;
				return false;
			}
			flag = true;
		});
		$("input[name='dept-all']").prop("checked", flag);
	}

	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var len = selectDeptIds.length;
		if(len == 0) {
			$.prompt.message("请至少选择一个需要复制应用至的食阁！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		/*var param = JSON.stringify(selectDeptIds);*/
		var deptIdsStr = selectDeptIds.join(",");
		var param = {currentId:currentId,deptIdsStr:deptIdsStr,type:type};
		jsonAjax.post(path + "/sg/apply/save.htm", param, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("复制应用成功！", $.prompt.msg);
				closeWin();
			} else {
				$(this).prop('disabled', false);
				$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
			}
		});
	});

	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});