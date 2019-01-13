$(function() {
	var path = $("#contextPath").val();
	var default_kind = $("#kind").val();
	var default_useIds = $("#useIds").val();
	var dept_init_finish = false;
	var user_init_finish = false;
	initUser();
	initDept();
	setDefault();
	var resetFlag = true;
	function resetDefault() {
		resetFlag = true;
		setDefault();
	}
	function setDefault() {
		if(resetFlag == true) {
			resetFlag = false;
		}
		if(dept_init_finish == true && user_init_finish == true) {
			$("#dept_list").html("");
			$("#user_list").html("");
			$.each(depts, function(index, item) {
				appendDept(item);
			});
			
			var _useIds = default_useIds.split(",");
			if(default_kind == 1 || default_kind == 0) {
				/*$.each(initUsers, function(index, item) {
					appendUser(item);
				});*/
				$.each(depts, function(index, item) {
					var deptId = item.eid;
					$("#dept_" + deptId).prop("checked", true);
					addUserByDeptId(deptId);
				});
				$(".dept").prop("checked", true);
				$(".user").prop("checked", true);
			} else if(default_kind == 2) {
				for (var i = 0; i < _useIds.length; i++) {
					var deptId = _useIds[i];
					$("#dept_" + deptId).prop("checked", true);
					addUserByDeptId(deptId);
				}
				$("input[name='user-all']").prop("checked", true);
			} else if(default_kind == 3) {
				var deptIds = [];
				for (var i = 0; i < _useIds.length; i++) {
					var userId = _useIds[i];
					var user = initUsers[userId];
					if(user != undefined) {
						var flag = in_array(deptIds,user.deptId);
						if(!flag){
							deptIds.push(user.deptId);
						}
						/*var _deptIds = deptIds.join("-");
						if(_deptIds.indexOf(user.deptId) == -1) {
							deptIds.push(user.deptId);
						}*/
					}
				}
				for (var i = 0; i < deptIds.length; i++) {
					var deptId = deptIds[i];
					$("#dept_" + deptId).prop("checked", true);
					addUserByDeptId(deptId);
				}
				for (var i = 0; i < _useIds.length; i++) {
					var userId = _useIds[i];
					$("#user_" + userId).prop("checked", true);
				}
			}
			makeSelectAll("dept");
			makeSelectAll("user");
			if(default_kind == 2) {
				setAllSelect("user");
				setOneAllSelect("user");
			}
		} else {
			setTimeout(function() {
				setDefault();
			}, 100);
		}
	}
	
	//判断一个数字是否在数组里
	function in_array(arr,element){
		var flag = false;
		$.each(arr, function(index, deptId) {
			if (deptId == element) { 
				flag = true;
				return true;
			}
		});
		return flag;
	}
	
	$("#return_btn").click(function() {
		closeWin();
	});
	$("input[name='dept-all']").click(function() {
		$("input[name='dept-one']").prop("checked", this.checked);
		if(this.checked) {
			var _deptIds = selectDeptIds.join("-");
			$.each(users, function(deptId, _users) {
				if(!in_array(selectDeptIds,deptId)) {
					selectDeptIds.push(deptId);
					$.each(_users, function(index, item) {
						appendUser(item);
					});
				}
				/*if(_deptIds.indexOf(deptId) == -1) {
					selectDeptIds.push(deptId);
					$.each(_users, function(index, item) {
						appendUser(item);
					});
				}*/
			});
			makeSelectAll("user");
		} else {
			$("input[name='user-all']").prop("checked", false);
			selectDeptIds = [];
			$("#user_list").html("");
			first_flag = true;
		}
	});
	$("input[name='user-all']").click(function() {
		$("input[name='user-one']").prop("checked", this.checked);
	});
	
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var menuId = $("#menuId").val();
		var menuName = $("#menuName").val();
		var companyId = $("#companyId").val();
		var kind = default_kind;
		var deptAll = $("input[name='dept-all']").is(":checked");
		var userAll = $("input[name='user-all']").is(":checked");
		if(deptAll == true && userAll == true) {
			kind = 1;
		} else if(deptAll == false && userAll == true) {
			kind = 2;
		} else {
			kind = 3;
		}
		var param = {
			menuId: menuId,
			menuName: menuName,
			kind: kind,
			companyId: companyId
		};
		var deptIds = "";
		if(kind == 1) {
			var useUsers = [];
			var useUser = {
				useId: companyId,
				useName: "全公司",
				useDeptName: "全公司"
			};
			useUsers.push(useUser);
			param['useUsers'] = JSON.stringify(useUsers);
		} else if(kind == 2) {
			var checkList = $("input[name='dept-one']:checked");
			if(checkList.length == 0) {
				$.prompt.message("请至少选择一个部门！", $.prompt.msg);
				$(this).prop('disabled', false);
				return false;
			}
			var useUsers = [];
			$(checkList).each(function() {
				var deptId = $(this).data("eid");
				var dept = depts[deptId];
				var useUser = {
					useId: dept.eid,
					useName: dept.name,
					useDeptName: dept.name
				};
				useUsers.push(useUser);
			});
			param['useUsers'] = JSON.stringify(useUsers);
		} else if(kind == 3) {
			var checkList = $("input[name='user-one']:checked");
			if(checkList.length == 0) {
				$.prompt.message("请至少选择一个应用人员！", $.prompt.msg);
				$(this).prop('disabled', false);
				return false;
			}
			var useUsers = [];
			$(checkList).each(function() {
				var userId = $(this).data("eid");
				var user = initUsers[userId];
				var useUser = {
					useId: user.eid,
					useName: user.name,
					useDeptName: user.deptName
				};
				useUsers.push(useUser);
			});
			param['useUsers'] = JSON.stringify(useUsers);
		}
		jsonAjax.post(path + "/settings/right/save.htm", param, function(response) {
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
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
	
	function initDept() {
		var url = path + "/sysDepartment/data/-1/1.htm";
		jsonAjax.post(url, {}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					depts[item['eid']] = item;
					//appendDept(item);
				});
				dept_init_finish = true;
			} else {
				$.prompt.message("获取部门数据失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	function initUser() {
		var url = path + "/sysUser/data/list/-1.htm";
		jsonAjax.post(url, {}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					var dept_users = users[item['deptId']];
					if(users[item['deptId']] == undefined) {
						users[item['deptId']] = [];
					}
					users[item['deptId']].push(item);
					initUsers[item.eid] = item;
				});
				user_init_finish = true;
			} else {
				$.prompt.message("获取职员失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	var first_flag = true;
	var selectDeptIds = [];
	var depts = {};// key为部门id,value为部门信息
	var users = {};// key为部门id,value为所属部门的用户数组
	var initUsers = {}; // key为用户id,value为用户信息
	function addUserByDeptId(deptId) {
		selectDeptIds.push(deptId);
		if(first_flag == true) {
			first_flag = false;
			$("#user_list").html("");
		}
		var listUser = users[deptId];
		if(listUser != undefined) {
			$("input[name='user-all']").prop("checked", false);
			$.each(listUser, function(index, item) {
				appendUser(item);
			});
		}
	}
	function removeUserByDeptId(deptId) {
		var flag = false;
		for(var i = 0; i < selectDeptIds.length; i++) {
			if(selectDeptIds[i] == deptId) {
				selectDeptIds.splice(i, 1);
				flag = true;
				break;
			}
		}
		if(flag == true) {
			$(".dept_" + deptId).remove();
			makeSelectAll("user");
		}
	}
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
				addUserByDeptId(deptId);
				makeSelectAll("dept");
			} else {
				removeUserByDeptId(deptId);
				$("input[name='dept-all']").prop("checked", false);
			}
		});
	}
	function appendUser(item) {
		var html = "";
		html += '<li class="dept_'+item.deptId+'">';
		html += '<div class="Li-left">';
		html += '<span class="worker-name">'+item.name+'</span><span class="worker-job">'+item.job+'</span>';
		html += '</div>';
		html += '<div class="Li-right">';
		html += '<label>';
		html += '<input class="person-checkbox user dept_'+item.deptId+'" type="checkbox" name="user-one" data-deptId="'+item.deptId+'" data-eid="'+item.eid+'" id="user_'+item.eid+'"><span class="person-checkboxInput"></span>';
		html += '</label>';
		html += '</div>';
		html += '</li>';
		$("#user_list").append(html);
		$("#user_" + item.eid).click(function() {
			var userId = $(this).data("eid");
			if(!this.checked) {
				$("input[name='user-all']").prop("checked", false);
			} else {
				makeSelectAll("user");
			}
		});
	}
	function makeSelectAll(type) {
		var flag = false;
		var checkBoxs = $("input[name='"+type+"-one']");
		$.each(checkBoxs, function(index, item) {
			if(item.checked == false) {
				flag = false;
				return false;
			}
			flag = true;
		});
		$("input[name='"+type+"-all']").prop("checked", flag);
	}
	/**
	 * 设置全选框是否选中
	 */
	function setAllSelect(type, flag) {
		if(flag == undefined) {
			flag = true;
		}
		$("input[name='"+type+"-all']").prop("checked", flag);
	}
	/**
	 * 让所有的复选框都选中
	 */
	function setOneAllSelect(type, flag) {
		if(flag == undefined) {
			flag = true;
		}
		$("." + type).prop("checked", flag);
	}
});