<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表格样式</title>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW">
		<div class="tab-pages">
			<table id="pagination_table">
				<thead>
					<tr>
						<th>应用功能</th>
						<th>授权应用</th>
						<th>应用部门</th>
						<th>应用人员</th>
						<th class="th-bgcolor2 tr_splc" style="width: 80px;">批审流程</th>
						<th class="th-bgcolor2" style="width: 80px;">编<span class='wzkg'></span>辑</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div class="grayline"></div>
		<div class="pages">
			<c:if test="${param.confirmId ne 20204}">
			<button class="pagination_btn pagination_btn_right submit-btn" id="confirm_btn">完<span class="wzkg"></span>成</button>
			</c:if>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js?type=settings"></script>
<script type="text/javascript">
$(function() {
	var path = $("#contextPath").val();
	var pid = '${pid}';
	var companyLevel = '${USER_IN_SESSION.company.level}';
	var companyId = "${USER_IN_SESSION.company.eid}";
	var isVip = '${USER_IN_SESSION.company.isVip}';
	jsonAjax.post(path + "/settings/right/list/"+pid+".htm",{}, function(response) {
		if(response.status == 200) {
			var bodyNode = $("tbody");
			var datas = {};
			var hasAppr = false;
			$.each(response.data, function(index, each) {
				var lenght = each.length;
				var flag = true;
				for (var i = 0; i < lenght; i++) {
					
					var $tr = $("<tr/>");
					var trData = each[i];
					/* if((companyLevel ==2&&(trData.eid == 10101||trData.eid == 10111)&&companyId!=204)||(companyLevel ==3&&(trData.eid == 10101||trData.eid == 10111)&&companyId!=204)) {
						return; 
					} */
					datas[trData.eid] = trData;
					if(flag == true) {
						flag = false;
						$("<td/>").attr("rowspan", lenght).text(index).appendTo($tr);
					}
					$("<td/>").text(trData['remark']).appendTo($tr);
					
					$("<td/>").text(trData['useDeptNames']).attr("title", trData['fullUseDeptNames']).appendTo($tr);
					
					
					$("<td/>").text(trData['useNames']).attr("title", trData['fullUseNames']).appendTo($tr);
					
					if(trData['hasAppr'] == 1) {
						var apprNode = $("<img/>").attr("src", "${ctx}/images/icons/check_icon2.png").attr("id", trData['eid']);
						apprNode.css("cursor", "pointer");
						apprNode.click(function() {
							var eid = $(this).attr("id");
							var item = datas[eid];
							toApproval(item);
						});
						var apprTd = $("<td/>");
						apprTd.addClass("tr_splc");
						apprNode.appendTo(apprTd);
						apprTd.appendTo($tr);
						hasAppr = true;
					} else {
						var apprNode = $("<img/>").attr("src", "${ctx}/images/icons/line.png");
						var apprTd = $("<td/>");
						apprTd.addClass("tr_splc");
						apprNode.appendTo(apprTd);
						apprTd.appendTo($tr);
					}
					var imgNode = $("<img/>").attr("src", "${ctx}/images/icons/gray_edit.png").attr("id", trData['eid']);
					imgNode.css("cursor", "pointer");
					imgNode.click(function() {
						var eid = $(this).attr("id");
						var item = datas[eid];
						toDetail(item);
					});
					var editTd = $("<td/>");
					imgNode.appendTo(editTd);
					editTd.appendTo($tr);
					$tr.appendTo(bodyNode);
				}
			});
			if(hasAppr == false) {
				$(".tr_splc").hide();
			}
		} else {
			$.prompt.message("获取数据失败：" + response.msg, $.prompt.error);
		}
	});
	function toDetail(item) {
		var title = item.name + "-" + item.remark;
		title = encodeURI(encodeURI(title));
		var name = item.name;
		name = encodeURI(encodeURI(name));
		var url = "${ctx}/settings/right/set.htm?eid=" + item.eid + "&title=" + title + "&name=" + name;
		url += "&kind=" + item.kind + "&useIds=" + item.useIds;
		parent.$.prompt.layerUrl2({url:url, width: 672, height: 498});
	}
	
	function toApproval(item) {
		if(item.eid == "10111") {
			// 公司公告
			var url = "${ctx}/approvalset/load/detail.htm?btype=" + item.btype + "&type=1";
			parent.$.prompt.layerUrl2({url: url, width: 800,height: 550});
		} else {
			var name = item.name;
			var title = name;
			var showTitle = 0;
			if(name == "销售申请") {
				title = "申请类型";
				showTitle = 1;
			} else if(name == "市场业务") {
				title = "业务类型";
				showTitle = 1;
			} else if(name == "客户事务") {
				title = "申请事由";
				showTitle = 1;
			} else if(name == "采购申请") {
				title = "申请类型";
				showTitle = 1;
			}
			name = encodeURI(encodeURI(name));
			title = encodeURI(encodeURI(title));
			var url = "${ctx}/approvalset/load/list.htm?btype=" + item.btype + "&name=" + name;
			url += "&title=" + title + "&showTitle=" + showTitle;
			parent.$.prompt.layerUrl2({url:url, width: 1250, shadeClose:false});
		}
	}
});
$(function() {
	var path = $("#contextPath").val();
	$("#confirm_btn").click(function() {
		$("#confirm_btn").prop('disabled', true);
		var confirmId = $("#confirmId").val();
		var hasConfirmId = $("#hasConfirmId").val();
		var confirmPid = $("#confirmPid").val();
		if(hasConfirmId == 0) {
			var param = {confirmId:confirmId, confirmPid:confirmPid};
			jsonAjax.post(path + "/sysSettings/menu/confirm.htm", param, function(response) {
				if(response.status == 200) {
					parent.$.prompt.message("确定成功！", $.prompt.ok);
					parent.confirmCallback(confirmId, confirmPid, response.data);
				} else {
					parent.$.prompt.message("确定失败：" + response.msg, $.prompt.error);
					$("#confirm_btn").prop('disabled', false);
				}
			});
		} else {
			$.prompt.message("您已经确定过了！", $.prompt.msg);
		}
	});
});
</script>
</body>
</html>