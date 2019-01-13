$(function() {
	var path = $("#contextPath").val();
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
			var deptIds = [];
			for (var i = 0; i < _useIds.length; i++) {
				var userId = _useIds[i];
				var user = initUsers[userId];
				if(user != undefined) {
					var flag = in_array(deptIds,user.deptId);
					if(!flag){
						deptIds.push(user.deptId);
					}
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
			
			makeSelectAll("dept");
			makeSelectAll("user");
			
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
		var checkList = $("input[name='user-one']:checked");
		if(checkList.length == 0) {
			$.prompt.message("请至少选择一个收件人！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var useUsers = [];
		var useUsersStr = "";
		var useIds = "";
		$(checkList).each(function() {
			var userId = $(this).data("eid");
			var user = initUsers[userId];
			useUsersStr += user.name+"、";
			useIds += user.eid+",";
			var useUser = {
				eid: user.eid,
				name: user.name,
				imAccount : user.imAccount
			};
			useUsers.push(useUser);
		});
		var selectUsers = JSON.stringify(useUsers);
		useUsersStr =  useUsersStr != "" ? useUsersStr.substring(0,(useUsersStr.length - 1)) : useUsersStr;
		useIds = useIds != "" ? useIds.substring(0,(useIds.length - 1)) : useIds;
		parent.$("#s_receiveIds").val(useUsersStr);
		parent.$("#cc").val(selectUsers);
		parent.$("#useIds").val(useIds);
		closeWin();
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
	
	function initDept() {
		var url = path + "/sysDepartment/data/-1/-1.htm";
		jsonAjax.post(url, {}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					depts[item['eid']] = item;
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