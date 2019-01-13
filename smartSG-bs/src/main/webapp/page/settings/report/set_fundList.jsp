<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<form action="${ctx}/comNotice/save.htm" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="type" id="type" value="${type}">
				<input type="hidden" name="oldFundList" id="oldFundList" value="${model.fundList}">
				<input type="hidden" name="approvalNo" id="approvalNo" value="${model.approvalNo}">
				<input type="hidden" name="title" id="title" value="${model.title}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">资金附件</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL" style="text-align: center;">
					<input type="radio" name="fundList" value="1" <c:if test="${model.fundList eq 1}">checked="checked"</c:if>>&nbsp;&nbsp;选用
				</div>
				<div class="yj-add-divL" style="text-align: center;">
					<input type="radio" name="fundList" value="0" <c:if test="${model.fundList eq 0}">checked="checked"</c:if>>&nbsp;&nbsp;不选用
				</div>
				<div class="yj-add-divL" style="text-align: center;" id="toShowPre">
					<a style="text-decoration: underline;color: #83c7f9;">附件预览</a>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
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
	var oldFundList = $("#oldFundList").val();
	$("#save_btn").click(function() {
		$("#save_btn").prop('disabled', true);
		var fund_list = $('input:radio[name="fundList"]:checked').val();
		if(eid != "") {
			if(fund_list != oldFundList) {
				jsonAjax.post(path + "/approvalset/report/fundList/set.htm", $("#mainForm").serialize(), function(response) {
					if(response.status == 200) {
						parent.$.prompt.message("保存成功！", $.prompt.msg);
						parent.top.refreshContent();
						closeWin();
					} else {
						$("#save_btn").prop('disabled', false);
						$.prompt.message("保存失败：" + response.msg, $.prompt.msg);
					}
				});
			} else {
				closeWin();
			}
		} else {
			parent.$("#content_iframe").contents().find("#fundList").val(fund_list);
			parent.$("#content_iframe").contents().find("#fundList_btn").remove();
			$text = parent.$("#content_iframe").contents().find("#fundList_btn_text");
			$text.text(fund_list == 1 ? "选用" : "不选用");
			$text.show();
			closeWin();
		}
	});
	var approvalNo = $("#approvalNo").val();
	$("#toShowPre").click(function() {
		var title = $("#title").val();
		var fund_list = $('input:radio[name="fundList"]:checked').val();
		openPre(fund_list == 1 ? "fund_y" : "fund_n", approvalNo, title);
	});
	function openPre(fundList, approvalNo, title) {
		var preImages = {
			"fund_y": {1: "fund_y_1", 2: "fund_y_2", 3: "fund_y_3", 4: "fund_y_4", 5: "fund_y_5", 6: "fund_y_6"},
			"fund_n": {1: "fund_n_1", 2: "fund_n_2", 3: "fund_n_3", 4: "fund_n_4", 5: "fund_n_5", 6: "fund_n_6"}
		}
		var img = preImages[fundList][approvalNo];
		if(title == undefined || title == "") {
			title = "XXXXXX报告";
		}
		/* var content = '<div style="overflow-y: auto;overflow-x: hidden;height: 99%">';
		content += '<div style="text-align: center;height: 50px;line-height: 50px;font-size: 35px;font-weight:600;margin: 30 0 10 0;">'+title+'</div>';
		content += '<img src="'+path+'/images/pre/'+img+'.jpg"></div>';
		var options = {
			content: content,
			width:980
		};
		parent.$.prompt.layerHtml(options); */
		title = encodeURI(encodeURI(title));
		parent.$.prompt.layerUrl("${ctx}/capitalDetail/loadSetFundList.htm?title="+title,900,500);
		
	}
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