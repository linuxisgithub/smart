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
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
				 	摊位主营品种
				</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						主营品种名称：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						菜单样式：
					</div>
					<div class="div-input">
						<select name="style" id="style">
							<option value="1" ${model.style == 1 ? 'selected':''}>称重点餐专用菜单样式</option>
							<option value="2" ${model.style == 2 ? 'selected':''}>快餐点餐专用样式</option>
							<option value="3" ${model.style == 3 ? 'selected':''}>无分组菜单样式</option>
							<option value="4" ${model.style == 4 ? 'selected':''}>分组菜单样式</option>
						</select>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						状态：
					</div>
					<div class="div-input">
						<c:if test="${model.status eq 1 || model.status eq null}">
							<input type="radio" name="status" value="1" checked="checked" /><span class="wzkg"></span>启用
							<input type="radio" name="status" value="0" style="margin-left:20px;"/><span class="wzkg"></span>停用
						</c:if>
						<c:if test="${model.status eq 0}">
							<input type="radio" name="status" value="1" /><span class="wzkg"></span><span class="wzkg"></span>启用
							<input type="radio" name="status" value="0" checked="checked" style="margin-left:20px;"/><span class="wzkg"></span>停用
						</c:if>
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
<script type="text/javascript" src="${ctx}/js/platformManagement/booth/varieties/create.js"></script>
</body>
</html>