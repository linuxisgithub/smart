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
<div class="mainRight-tab-conW layer">				
	<div class="tab-con">
		<form action="${ctx}/sysUser/save.htm" method="post" id="mainForm">
			<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
			<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
					企业信息
				</div>
			</div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">企业名称：</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}" style="width: 327px;">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">所属区域：</div>
					<div class="div-input">
						<input type="text" name="region" id="region" value="${model.region}" style="width: 327px;">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">超级用户密码：</div>
					<div class="div-input">
						<input type="password" name="adminPassword" id="adminPassword" value="******" style="width: 309px;">
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="layer-btn-w100 submit-btn" id="save_btn">确<span class="wzkg"></span>定</button>
		<button class="layer-btn-w100 return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript">
$(function() {
	var path = $("#contextPath").val();
	$("#return_btn").click(function() {
		closeWin();
	});
	
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		
		jsonAjax.post(path + "/settings/company/info/save.htm",$("#mainForm").serialize(), function(data,response) {
			if(data.status == 200) {
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				closeWin();
			} else {
				$.prompt.message("保存失败", $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});
</script>
</body>
</html>