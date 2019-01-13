<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/settings.css"/>
<title>表格样式</title>
</head>
<body>
<div style="display: none;" alt="隐藏表单域">
</div>
<div class="apply-container" style="width: 360px;">
	<div class="apply-content">
		<div class="title">
		应用设置
		</div>
		<%-- <div class="hr"></div>
		<div class="range" style="height: 55px;line-height: 55px;margin: 0 auto;width: 100%;">
			<strong>应用范围：</strong>
			<label class="person-label">
				<input class="person-radio" id="kind_1" value="1" type="radio" name="apply-range" <c:if test="${model.useKind eq 1}">checked="checked"</c:if>/>
				<span class="person-radioInput"></span>
				<span style="margin:0px 25px 0px 8px;">全公司</span>
			</label>
			<label class="person-label">
				<input class="person-radio" id="kind_2" value="2" type="radio" name="apply-range" <c:if test="${model.useKind eq 2}">checked="checked"</c:if>>
				<span class="person-radioInput"></span>
				<span style="margin:0px 25px 0px 8px;">部门</span>
			</label>

		</div> --%>
		<!-- 应用部门start -->
		<div class="apply-div" style="position: relative;height: 299px;margin: 0 auto;width: 100%;">
			<!-- <div class="shadow" id="dept_shade"></div> -->
			<div class="apply-tit">
				<div class="apply-titLeft">
					应用部门
				</div>
				<div class="apply-titright" style="margin-right: 28px;">
					<label>
						<span style="margin:0px 10px 0px 0px;">
							<c:choose>
							<c:when test="${USER_IN_SESSION.company.isSpec == 1}">
								<th>全机构</th>
							</c:when>
							<c:otherwise>
								<th>全公司</th>
							</c:otherwise>
						</c:choose>
						</span><input class="person-radio" type="radio" name="dept-one" value="-1" id="dept-all"><span class="person-radioInput"></span>
					</label>
				</div>
				<div class="clear"></div>

			</div>
			<div class="apply-listbox" style="height: 258px;">
				<ul id="dept_list"></ul>
			</div>
		</div>
		<!-- 应用部门end -->
	</div>
	<div class="clear"></div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" name="eid" id="eid" value="${model.eid}">
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" name="useKind" id="kind" value="${model.useKind}">
	<input type="hidden" name="useId" id="useId" value="${model.useId}">
	<input type="hidden" name="type" id="type" value="${type}">
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="layer-btn-w100 submit-btn" id="save_btn">确定</button>
		<button class="layer-btn-w100 return_btn" id="return_btn">返回</button>
	</div>
	<div class="clear"></div>
</div>

<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript">
$(function() {
	var path = $("#contextPath").val();
	var eid = $("#eid").val();
	var default_kind = $("#kind").val();
	var default_useId = $("#useId").val();
	var type = $("#type").val();
	var companyId = $("#companyId").val();
	var isSpec = "${USER_IN_SESSION.company.isSpec}";
	var path = $("#contextPath").val();
	var depts = {};// key为部门id,value为部门信息
	initDept();
	setDefault();
	function setDefault() {
		if(dept_init_finish == true) {
			if(type == 1) {
				if(default_kind == 2) {
					$("#dept_" + default_useId).prop("checked", "checked");
				} else if(default_kind == 1){
					$("#dept-all").prop("checked", "checked");
				}
			} else if(type == 2) {
				var _useId = parent.$("#content_iframe").contents().find("#useId").val();
				var _useKind = parent.$("#content_iframe").contents().find("#useKind").val();
				if(_useKind == 1) {
					$("#dept-all").prop("checked", "checked");
				} else {
					$("#dept_" + _useId).prop("checked", "checked");
				}
			}
		} else {
			setTimeout(function() {
				setDefault();
			}, 100);
		}
	}
	var dept_init_finish = false;
	function initDept() {
		var url = path + "/sysDepartment/data/-1.htm";
		jsonAjax.post(url, {}, function(response) {
			if(response.status == 200) {
				$.each(response.data, function(index, item) {
					depts[item['eid']] = item;
					appendDept(item);
				});
				dept_init_finish = true;
			} else {
				$.prompt.message("获取部门数据失败：" + response.msg, $.prompt.msg);
			}
		});
	}
	function appendDept(item) {
		var deptHtml = "";
		deptHtml += '<li>';
		deptHtml += '<div class="Li-left">'+item.name+'</div>';
		deptHtml += '<div class="Li-right">';
		deptHtml += '<label>';
		deptHtml += '<input class="person-radio" type="radio" name="dept-one" data-eid="'+item.eid+'" id="dept_'+item.eid+'" value="'+item.eid+'"><span class="person-radioInput"></span>';
		deptHtml += '</label>';
		deptHtml += '</div>';
		deptHtml += '</li>';
		$("#dept_list").append(deptHtml);
	}
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var deptId = $('input:radio[name="dept-one"]:checked').val();
		if(deptId == undefined) {
			$.prompt.message("请选择一个应用部门！", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var useName = "全公司";
		if(isSpec == 1){
			useName = "全机构";
		}
		var useId = companyId;
		var kind = default_kind;
		if(deptId == -1) {
			kind = 1;
		} else {
			kind = 2;
			useId = deptId;
			var dept = depts[useId];
			useName = dept.name;
		}
		
		if(type == 1) {
			var param = {
				useKind: kind,
				useId: useId,
				useName: useName,
				eid: eid
			};
			jsonAjax.post(path + "/work/settings/update/use.htm", param, function(response) {
				if(response.status == 200) {
					parent.top.$.prompt.message("保存成功！", $.prompt.msg);
					parent.refreshContent();
					closeWin();
				} else {
					$("#save_btn").prop('disabled', false);
					$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
				}
			});
		} else if(type == 2) {
			parent.$("#content_iframe").contents().find("#useId").val(useId);
			parent.$("#content_iframe").contents().find("#useName").val(useName);
			parent.$("#content_iframe").contents().find("#use_btn_text").text(useName);
			parent.$("#content_iframe").contents().find("#use_btn_text").show();
			parent.$("#content_iframe").contents().find("#useKind").val(kind);
			parent.$("#content_iframe").contents().find("#use_btn").hide();
			closeWin();
		}
	});
	$("#return_btn").click(function() {
		closeWin();
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});
</script>
</body>
</html>