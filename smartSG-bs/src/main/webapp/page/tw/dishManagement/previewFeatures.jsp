<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/gundongtiao.css"/>
<title>表格样式</title>
</head>
<body style="background-color: white;">
<div style="height: 7%;padding: 10px;border-bottom: 0.5px solid #CCCCCC;">
	<h2>${name}:</h2>
</div>
<div style="overflow: auto;font-size:11px;" class="gundong">
	<c:forEach items="${models}" var="opt">
		<div style="height: 40px;line-height:40px;border-bottom: 0.5px solid #CCCCCC;padding-left: 60px;padding-right: 60px;">
			<h2 style="float: left;width: 50%;">${opt.name}<font style="font-size: 9px;float: right;">${opt.isDefault == 1?'(默认)':''}</font></h2>
			<h2 style="float: right;">S$ ${opt.money}</h2>
		</div>
	</c:forEach>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript">
	$(function(){
		$("#return_btn").click(function() {
			closeWin();
		});
		function closeWin() {
			var layerIndex = parent.layer.getFrameIndex(window.name);
			parent.layer.close(layerIndex);
		}
	})
</script>
</body>
</html>