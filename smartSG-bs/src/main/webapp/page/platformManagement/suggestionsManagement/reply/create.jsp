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
			<div class="yj-add-top">
				<div class="yj-add-title">收件箱</div>
				<div style="float: right;min-width: 80px;font-weight: 600;">
					日<span class="wzkg"></span>期：<input style="outline: none;" readonly="readonly" value="${model.replyDate }">
				</div>
			</div>
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">收件人：</div>
					<div class="div-input">
						<input type="text" readonly="readonly" value="${model.receiveName}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">意见类型：</div>
					<div class="div-input">
						<input type="text" readonly="readonly" value="${model.kindName}">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">回复内容：</div>
					<div class="div-textarea">
						<textarea readonly="readonly" maxlength="500" rows="15">${model.content}</textarea>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript">
$(function(){
	$("#return_btn").click(function() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	});
});
</script>
</body>
</html>