<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
	<style type="text/css">
		.div-label b{
			color: red;
		}
	</style>
</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="deptId" id="deptId" value="${model.deptId}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
					菜品基本信息
				</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>菜品中文名：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>菜品英文名：
					</div>
					<div class="div-input">
						<input type="text" name="enName" id="enName" value="${model.enName}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>分组：
					</div>
					<div class="div-input">
						<select id="s_group"></select>
						<input type="hidden" name="groupId" id="groupId" value="${model.groupId}">
						<input type="hidden" name="groupName" id="groupName" value="${model.groupName}">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>单位：
					</div>
					<div class="div-input">
						<input type="text" name="unit" id="unit" value="${model.unit}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>销售价：
					</div>
					<div class="div-input">
						<input type="text" name="sellingPrice" id="sellingPrice" value="${model.sellingPrice}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						卡路里：
					</div>
					<div class="div-input">
						<input type="text" name="calorie" id="calorie" value="${model.calorie}">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>准备时间：
					</div>
					<div class="div-input">
						<input type="text" name="preparationTime" id="preparationTime" value="${model.preparationTime}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						<b>*</b>成本：
					</div>
					<div class="div-input">
						<input type="text" name="cost" id="cost" value="${model.cost}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						剩余数量：
					</div>
					<div class="div-input">
						<input type="text" name="number" id="number" value="${model.number}">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">
						特别说明：
					</div>
					<div class="div-input">
						<input type="text" style="width: 103%;" name="description" id="description" value="${model.description}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						英文特别说明：
					</div>
					<div class="div-input">
						<input type="text" style="width: 103%;" name="enDescription" id="enDescription" value="${model.enDescription}">
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
<script type="text/javascript" src="${ctx}/js/tw/dishManagement/create.js"></script>
</body>
</html>