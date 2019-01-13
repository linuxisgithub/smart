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
		<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="bid" id="bid" value="${model.bid}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
				 	添加字段
				</div>
			</div>
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">
						字段名称：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						默认值：
					</div>
					<div class="div-input">
						<%--<select name="defaultValue" id="defaultValue">--%>
							<input type="text" name="defaultValue" id="defaultValue" value="${model.defaultValue}">
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">
						字段类型：
					</div>
					<div class="div-input">
						<select name="fieldType" id="fieldType">
							<option value="1" ${model.fieldType == 1 ? 'selected':''}>文本</option>
							<option value="2" ${model.fieldType == 2 ? 'selected':''}>数字</option>
							<option value="3" ${model.fieldType == 3 ? 'selected':''}>货币</option>
							<option value="4" ${model.fieldType == 4 ? 'selected':''}>日期</option>
						</select>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						是否显示：
					</div>
					<div class="div-input">
						<select name="isShow" id="isShow">
							<option value="1" ${model.isShow == 1 ? 'selected':''}>是</option>
							<option value="2" ${model.isShow == 2 ? 'selected':''}>否</option>
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">确<span class="wzkg"></span>定</button>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/sgManagmentCompany/sgManagement/renovation/createField.js"></script>
</body>
</html>