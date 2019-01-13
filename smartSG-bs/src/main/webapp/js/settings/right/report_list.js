$(function() {
	var path = $("#contextPath").val();
	var list_type = $("#list_type").val();
	var datas = {};
	jsonAjax.post(path + "/approvalset/report/list.htm",{}, function(response) {
		if(response.status == 200) {
			var bodyNode = $("tbody");
			$.each(response.data, function(index, each) {
				var $tr = $("<tr/>");
				var trData = each;
				datas[trData['eid']] = trData;
				
				$("<td/>").text(trData['title']).attr("id", trData['eid'] + "_title").appendTo($tr);
				$("<td/>").text(trData['deptName']).attr("id", trData['eid'] + "_dept").appendTo($tr);
				$("<td/>").text(trData['userName']).attr("id", trData['eid'] + "_user").appendTo($tr);
				$("<td/>").text(trData['contentNum']).attr("id", trData['eid'] + "_contentNum").appendTo($tr);
				
				$fundListTd = $("<td/>").text(trData['fundList'] == "1" ? "选用" : "不选用").attr("data-eid", trData['eid']);
				$fundListTd.attr("id", trData['eid'] + "_fundList");
				$fundListTd.css("cursor", "pointer");
				$fundListTd.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					toChangeFundList(item);
				});
				$fundListTd.appendTo($tr);
				
				$("<td/>").text(trData['approvalNo']).attr("id", trData['eid'] + "_approvalNo").appendTo($tr);
				$("<td/>").text(trData['suggestNum']).attr("id", trData['eid'] + "_suggestNum").appendTo($tr);
				
				var apprNode = $("<img/>").attr("src", path + "/images/icons/check_icon2.png").attr("data-eid", trData['eid']);
				
				apprNode.css("cursor", "pointer");
				apprNode.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					toApproval(item);
				});
				var apprTd = $("<td/>");
				apprTd.attr("id", trData['eid'] + "_to_approval");
				apprNode.appendTo(apprTd);
				apprTd.appendTo($tr);
				
				$("<td/>").text(trData['status'] == "1" ? "启用" : "停用").attr("id", trData['eid'] + "_status").appendTo($tr);
				
				var preNode = $("<img/>").attr("src", path + "/images/icons/see.png").attr("data-eid", trData['eid']);
				preNode.css("cursor", "pointer");
				preNode.click(function() {
					var eid = $(this).data("eid");
					var item = datas[eid];
					toShowPre(item);
				});
				var preTd = $("<td/>");
				preNode.appendTo(preTd);
				preTd.appendTo($tr);
				
				if(list_type == "edit") {
					var imgNode = $("<img/>").attr("src", path + "/images/icons/gray_edit.png").attr("data-eid", trData['eid']);
					imgNode.css("cursor", "pointer");
					imgNode.click(function() {
						toDetail($(this));
					});
					var editTd = $("<td/>").attr("id", trData['eid'] + "_edit_td");
					imgNode.appendTo(editTd);
					editTd.appendTo($tr);
				}
				$tr.appendTo(bodyNode);
			});
			if(list_type == "create") {
				var $tr = $("<tr/>").attr("id", "add_tr");
				var addTd = $("<td/>").attr("colspan", 10);
				var addHtml = '<div class="setting_btn" id="add_btn">';
				addHtml += '<img src="'+ path +'/images/icons/settingAdd_icon.png" style="position: relative;top:3px;">';
				addHtml += '<span style="margin-left:10px;">新增报告</span>';
				addHtml += '</div>';
				
				addHtml += '<div class="setting_btn" id="save_btn" style="display: none;">';
				addHtml += '<img src="'+ path +'/images/icons/save.png" style="position: relative;top:3px;">';
				addHtml += '<span style="margin-left:10px;">保存报告</span>';
				addHtml += '</div>';
				addTd.append(addHtml);
				addTd.appendTo($tr);
				$tr.appendTo(bodyNode);
				$("#add_btn").click(function() {
					toAdd();
				});
			}
		} else {
			$.prompt.message("获取数据失败：" + response.msg, $.prompt.error);
		}
	});
	// 编辑
	var isEdit = false;
	function toDetail($this) {
		if(isEdit == false) {
			isEdit = true;
			var eid = $this.data("eid");
			var item = datas[eid];
			
			$("#approvalId").val(eid);
			$("#approvalPerson").val(item.approvalPerson);
			
			var html = "";
			html = '<div id="save_btn" class="setting_btn" style="border:1px solid #9e9e9e;height:24px;line-height:24px;width:50px;">';
			/*html += '<img src="'+ path +'/images/icons/save.png" style="position: relative;top:0px;">';*/
			html += '<span">保存</span>';
			html += '</div>';
			$("#"+eid+"_"+"edit_td").html(html);
			$("#save_btn").click(function() {
				doSave();
			});
			
			html = "";
			html += '<input type="text" name="title" id="title" value="'+item.title+'">';
			$("#"+eid+"_"+"title").html(html);
			
			html = "";
			html += '<select id="s_dept"><option value="-1">请选择</option></select>';
			html += '<input type="hidden" name="deptId" id="deptId">';
			html += '<input type="hidden" name="deptName" id="deptName">';
			$("#"+eid+"_"+"dept").html(html);
			
			html = "";
			html += '<select id="s_user"><option value="-1">请选择</option></select>';
			html += '<input type="hidden" name="userId" id="userId">';
			html += '<input type="hidden" name="userName" id="userName">';
			$("#"+eid+"_"+"user").html(html);
			
			$("#s_user").select2({
				minimumResultsForSearch: search_detault,
			});
			$("#s_dept").select2({
				minimumResultsForSearch: search_detault,
			});
			initDept();
			initUser();
			deptChange();
			setEidtDefault(item);
			
			html = "";
			html += '<select name="contentNum" id="contentNum" style="width: 85px;height: 26px;">';
			html += '<option value="250"' + (item.contentNum == 250 ? "selected=\"selected\"" : "")+ '>250字符</option>';
			html += '<option value="350"' + (item.contentNum == 350 ? "selected=\"selected\"" : "")+ '>350字符</option>';
			html += '<option value="500"' + (item.contentNum == 550 ? "selected=\"selected\"" : "")+ '>500字符</option>';
			html += '</select>';
			$("#"+eid+"_"+"contentNum").html(html);
			
			html = "";
			/*html += '<select name="approvalNo" id="approvalNo" style="width: 60px;height: 26px;">';
			html += '<option value="1"' + (item.approvalNo == 1 ? "selected=\"selected\"" : "")+ '>1 级</option>';
			html += '<option value="2"' + (item.approvalNo == 2 ? "selected=\"selected\"" : "")+ '>2 级</option>';
			html += '<option value="3"' + (item.approvalNo == 3 ? "selected=\"selected\"" : "")+ '>3 级</option>';
			html += '<option value="4"' + (item.approvalNo == 4 ? "selected=\"selected\"" : "")+ '>4 级</option>';
			html += '<option value="5"' + (item.approvalNo == 5 ? "selected=\"selected\"" : "")+ '>5 级</option>';
			html += '<option value="6"' + (item.approvalNo == 6 ? "selected=\"selected\"" : "")+ '>6 级</option>';
			html += '</select>';*/
			html += item.approvalNo;
			$("#"+eid+"_"+"approvalNo").html(html);
			
			html = "";
			html += '<select name="suggestNum" id="suggestNum" style="width: 85px;height: 26px;">';
			html += '<option value="80"' + (item.suggestNum == 80 ? "selected=\"selected\"" : "")+ '>80字符</option>';
			html += '<option value="120"' + (item.suggestNum == 120 ? "selected=\"selected\"" : "")+ '>120字符</option>';
			html += '<option value="160"' + (item.suggestNum == 160 ? "selected=\"selected\"" : "")+ '>160字符</option>';
			html += '</select>';
			$("#"+eid+"_"+"suggestNum").html(html);
			
			html = "";
			html += '<img alt="" src="'+path+'/images/icons/check_icon2.png">';
			$("#"+eid+"_"+"to_approval").html(html);
			$("#"+eid+"_"+"to_approval").click(function() {
				toApproval();
			});
			
			html = "";
			html += '<select name="status" id="status" style="width: 60px;height: 26px;">';
			html += '<option value="1"' + (item.status == 1 ? "selected=\"selected\"" : "")+ '>启用</option>';
			html += '<option value="0"' + (item.status == 0 ? "selected=\"selected\"" : "")+ '>停用</option>';
			html += '</select>';
			$("#"+eid+"_"+"status").html(html);
			
			$("#toEditApproval").click(function() {
				toApproval();
			});
			$("#suggestNum").select2({
				minimumResultsForSearch: search_detault,
			});
			$("#contentNum").select2({
				minimumResultsForSearch: search_detault,
			});
			$("#status").select2({
				minimumResultsForSearch: search_detault,
			});
			/*$("#approvalNo").select2({
				minimumResultsForSearch: search_detault,
			});*/
			$("#s_user").select2({
				minimumResultsForSearch: search_detault,
			});
			$("#s_dept").select2({
				minimumResultsForSearch: search_detault,
			});
		} else {
			$.prompt.message("请先保存修改的数据！", $.prompt.msg);
		}
	}
	function initEditDept(deptId) {
		
	}
	// 添加
	function toAdd(item) {
		//$("#add_tr").remove();
		$("#add_btn").hide();
		$("#save_btn").show();
		$("#save_btn").click(function() {
			doSave();
		});
		var newHtml = "";
		newHtml += '<tr id="new_tr">';
		newHtml += '<td><input type="text" name="title" id="title"></td>';
		newHtml += '<td>';
		newHtml += '<select id="s_dept"><option value="-1">请选择</option></select>';
		newHtml += '<input type="hidden" name="deptId" id="deptId">';
		newHtml += '<input type="hidden" name="deptName" id="deptName">';
		newHtml += '</td>';
		newHtml += '<td>';
		newHtml += '<select id="s_user"><option value="-1">请选择</option></select>';
		newHtml += '<input type="hidden" name="userId" id="userId">';
		newHtml += '<input type="hidden" name="userName" id="userName">';
		newHtml += '</td>';
		
		/*newHtml += '<td><input type="text" name="contentNum" id="contentNum" style="width: 80px;"></td>';*/
		newHtml += '<td>';
		newHtml += '<select name="contentNum" id="contentNum" style="width: 85px;height: 26px;">';
		newHtml += '<option value="250">250字符</option>';
		newHtml += '<option value="350">350字符</option>';
		newHtml += '<option value="500">500字符</option>';
		newHtml += '</select>';
		newHtml += '</td>';
		
		
		newHtml += '<td style="cursor: pointer;" id="select_btn">';
		newHtml += '<div class="select_btn" id="fundList_btn"><label style="margin-right: 10px;cursor: pointer;">请选择</label></div>';
		newHtml += '<span id="fundList_btn_text" style="display: none;"></span>';
		newHtml += '<input type="hidden" name="fundList" id="fundList">';
		newHtml += '</td>';
		newHtml += '<td>';
		newHtml += '<select name="approvalNo" id="approvalNo" style="width: 60px;height: 26px;">';
		newHtml += '<option value="1">1 级</option>';
		newHtml += '<option value="2">2 级</option>';
		newHtml += '<option value="3">3 级</option>';
		newHtml += '<option value="4">4 级</option>';
		newHtml += '<option value="5">5 级</option>';
		newHtml += '<option value="6">6 级</option>';
		newHtml += '<option value="7">7 级</option>';
		newHtml += '<option value="8">8 级</option>';
		newHtml += '</select>';
		newHtml += '</td>';
		
		/*newHtml += '<td><input type="text" name="suggestNum" id="suggestNum" style="width: 80px;"></td>';*/
		newHtml += '<td>';
		newHtml += '<select name="suggestNum" id="suggestNum" style="width: 85px;height: 26px;">';
		newHtml += '<option value="80">80字符</option>';
		newHtml += '<option value="120">120字符</option>';
		newHtml += '<option value="160">160字符</option>';
		newHtml += '</select>';
		newHtml += '</td>';
		
		newHtml += '<td style="cursor: pointer;" id="toApproval"><img alt="" src="'+path+'/images/icons/check_icon2.png"></td>';
		newHtml += '<td>';
		newHtml += '<select name="status" id="status" style="width: 60px;height: 26px;">';
		newHtml += '<option value="1">启用</option>';
		newHtml += '<option value="0">停用</option>';
		newHtml += '</select>';
		newHtml += '</td>';
		newHtml += '<td style="cursor: pointer;" id="addShowPre"><img alt="" src="'+path+'/images/icons/see.png"></td>';
		newHtml += '</tr>';
		
		$("#add_tr").before(newHtml);
		$("#select_btn").click(function() {
			var approvalNo = $("#approvalNo").val();
			var url = path + "/approvalset/report/fundList/set.htm?approvalNo=" + approvalNo;
			var title = $("#title").val();
			if(title != undefined && title != "") {
				title = encodeURI(encodeURI(title));
				url = url + "&title=" + title;
			}
			var fundList = $("#fundList").val();
			if(fundList != "") {
				url += "&fundList=" + fundList;
			}
			parent.$.prompt.layerUrl2({url: url, width: 400,height: 190});
		});
		$("#addShowPre").click(function() {
			addShowPre();
		});
		$("#toApproval").click(function() {
			toApproval();
		});
		$("#suggestNum").select2({
			minimumResultsForSearch: search_detault,
		});
		$("#contentNum").select2({
			minimumResultsForSearch: search_detault,
		});
		$("#status").select2({
			minimumResultsForSearch: search_detault,
		});
		$("#approvalNo").select2({
			minimumResultsForSearch: search_detault,
		});
		$("#s_user").select2({
			minimumResultsForSearch: search_detault,
		});
		$("#s_dept").select2({
			minimumResultsForSearch: search_detault,
		});
		initDept();
		initUser();
		deptChange();
	}
	// 改变资金附件
	function toChangeFundList(item) {
		var url = path + "/approvalset/report/fundList/set.htm?eid=" + item.eid;
		parent.$.prompt.layerUrl2({url: url, width: 400,height: 190});
	}
	
	var depts = {};// key为部门id,value为部门信息
	var users = {};// key为部门id,value为所属部门的用户数组
	var initUsers = {}; // key为用户id,value为用户信息
	function deptChange() {
		$("#s_dept").change(function() {
			var eid = $(this).val();
			var dept = depts[eid];
			if(dept == undefined) {
				$("#deptId").val("-1");
				$("#deptName").val("请选择");
			} else {
				$("#deptId").val(dept.eid);
				$("#deptName").val(dept.name);
			}
			$("#userId").val("-1");
			$("#userName").val("请选择");
			var _users = users[eid];
			var html = '<option value="-1">请选择</option>';
			$("#s_user").html(html);
			if(_users != undefined) {
				$.each(_users, function(index, item) {
					html = '<option value="'+item.eid+'">'+item.name+'</option>';
					$("#s_user").append(html);
				});
			}
			$("#s_user").select2({
				minimumResultsForSearch: search_detault,
			});
			$("#s_user").change(function() {
				var userId = $(this).val();
				var user = initUsers[userId];
				if(user == undefined) {
					$("#userId").val("-1");
					$("#userName").val("请选择");
				} else {
					$("#userId").val(user.eid);
					$("#userName").val(user.name);
				}
			});
		});
	}
	function setEidtDefault(item) {
		if(init_dept_finish == true && init_user_finish == true) {
			$("#s_dept").val(item.deptId).trigger("change");
			$("#s_user").val(item.userId).trigger("change");
		} else {
			setTimeout(function() {
				setEidtDefault(item);
			}, 100);
		}
	}
	var init_dept_finish = false;
	var init_user_finish = false;
	function initDept() {
		var url = path + "/sysDepartment/data/-1.htm";
		jsonAjax.post(url, {}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					depts[item['eid']] = item;
					var html = '<option value="'+item['eid']+'">'+item['name']+'</option>';
					$("#s_dept").append(html);
				});
				$("#s_dept").select2({
					minimumResultsForSearch: search_detault,
				});
				init_dept_finish = true;
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
				init_user_finish = true;
			} else {
				$.prompt.message("获取职员失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	// 添加预览
	function addShowPre() {
		var fundList = $("#fundList").val();
		if(fundList == "") {
			$.prompt.message("请先选择资金附件！", $.prompt.msg);
			return false;
		}
		var title = $("#title").val();
		var approvalNo = $("#approvalNo").val();
		openPre(fundList == 1 ? "fund_y" : "fund_n", approvalNo, title);
	}
	// 预览
	function toShowPre(item) {
		openPre(item.fundList == 1 ? "fund_y" : "fund_n", item.approvalNo, item.title);
	}
	function openPre(fundList, approvalNo, title) {
		var preImages = {
			"fund_y": {1: "fund_y_1", 2: "fund_y_2", 3: "fund_y_3", 4: "fund_y_4", 5: "fund_y_5", 6: "fund_y_6"},
			"fund_n": {1: "fund_n_1", 2: "fund_n_2", 3: "fund_n_3", 4: "fund_n_4", 5: "fund_n_5", 6: "fund_n_6"}
		}
		var img = preImages[fundList][approvalNo];
		if(title == undefined || title == "") {
			title = "XXXXXX报告";
		}
		var content = '<div style="overflow-y: auto;overflow-x: hidden;height: 99%">';
		content += '<div style="text-align: center;height: 50px;line-height: 50px;font-size: 35px;font-weight:600;margin: 30 0 10 0;">'+title+'</div>';
		content += '<img src="'+path+'/images/pre/'+img+'.jpg"></div>';
		var options = {
			content: content,
			width:980
		};
		parent.$.prompt.layerHtml(options);
	}
	// 批审流程
	function toApproval(item) {
		var url = path + "/approvalset/load/detail.htm";
		if(item && item.eid != undefined) {
			url += "?type=1&eid=" + item.eid;
		} else {
			url += "?type=2";
		}
		parent.$.prompt.layerUrl2({url: url, width: 800,height: 550});
	}
	function doSave() {
		$("#save_btn").prop('disabled', true);
		var deptId = $.trim($("#deptId").val());
		var title = $.trim($("#title").val());
		if(deptId == "" || deptId == -1) {
			$.prompt.message("请选择报告部门！", $.prompt.msg);
			$("#save_btn").prop('disabled', false);
			return false;
		}
		if(title == "") {
			$.prompt.message("报告名称不能为空！", $.prompt.msg);
			$("#save_btn").prop('disabled', false);
			return false;
		}
		
		var userId = $.trim($("#userId").val());
		if(userId == "" || userId == -1) {
			$.prompt.message("请选择报告人！", $.prompt.msg);
			$("#save_btn").prop('disabled', false);
			return false;
		}
		var userId = $.trim($("#userId").val());
		if(userId == "" || userId == -1) {
			$.prompt.message("请选择报告人！", $.prompt.msg);
			$("#save_btn").prop('disabled', false);
			return false;
		}
		var approvalPerson = $.trim($("#approvalPerson").val());
		if(approvalPerson == "") {
			$.prompt.message("请设置批审流程！", $.prompt.msg);
			$("#save_btn").prop('disabled', false);
			return false;
		}
		
		// 同步请求
		var isExistName = false;
		jsonAjax.asyncPost(path + "/approvalset/isExistName.htm", $("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				if(response.data > 0) {
					isExistName = true;
				}
			}
		});
		if(isExistName == true) {
			$.prompt.message("报告名称已经存在！", $.prompt.msg);
			$("#save_btn").prop('disabled', false);
			return false;
		}
		
		jsonAjax.post(path + "/approvalset/report/save.htm", $("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("保存成功！", $.prompt.msg);
				window.location.href = window.location.href;
			} else {
				$("#save_btn").prop('disabled', false);
				$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
			}
		});
	}
});
