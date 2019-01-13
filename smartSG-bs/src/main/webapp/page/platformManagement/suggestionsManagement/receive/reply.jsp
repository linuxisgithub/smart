<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
<style type="text/css">
	input{
		border: none !important;
	}
</style>
</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
			<input type="hidden" name="bid" id="bid">
			<div class="yj-add-top">
				<div class="yj-add-title">回<span class="wzkg"></span>复</div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">回复内容：</div>
					<div class="div-textarea">
						<textarea name="content" maxlength="500" rows="15"></textarea>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">发<span class="wzkg"></span>送</button>
		<button class="return_btn" id="return_btn">关<span class="wzkg"></span>闭</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript">
$(function(){
	$("#return_btn").click(closeWin);
	$("#save_btn").click(function(){
		if($.trim($("textarea[name='content']").val()) == ""){
			jQuery.prompt.message("请填写回复内容！", $.prompt.warn);
			return false;
		}
		$("#save_btn").prop("disabled",true);
		$("#bid").val(parent.$("#eid").val());
		if($("#bid").val() != ""){
			jsonAjax.post("${ctx}/suggestions/reply.htm",$("#mainForm").serialize(), function(resp) {
				if(resp.status == 200) {
					parent.parent.layer.closeAll();
					parent.parent.refreshContent();
					jQuery.prompt.message("发送成功！", $.prompt.ok);
				} else {
					jQuery.prompt.message("发送失败："+ resp.msg, $.prompt.error);
					$("#save_btn").prop("disabled",false);
				}
			});
		}
	});
});
var closeWin = function (){
	var layerIndex = parent.layer.getFrameIndex(window.name);
	parent.layer.close(layerIndex);
}
</script>
</body>
</html>