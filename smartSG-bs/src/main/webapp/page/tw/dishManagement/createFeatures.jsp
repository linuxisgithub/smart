<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/gundongtiao.css"/>
<title>表格样式</title>
	<style type="text/css">
		.div-label b{
			color: red;
		}
		.myBtn{
			background-color: white;
			border: 1px solid #A9A9A9;
			height: 25px;
			border-radius:4px;
			background-color:#3da8f5;
			color: #fff;
		}
		table thead tr th{
			border: 1px solid;
			height: 30px;
		}
		table tbody tr td{
			border: 1px solid;
			height: 30px;
		}
		table input{
			height: 100%;
			width: 100%;
			border: 0px;
			text-align: center;
		}
		.isDefault{
			width: 42%;
			height: 100%;
			border: 0px;
			text-align: center;
		}
	</style>
</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="bid" id="bid" value="${model.bid}">
				<input type="hidden" name="type" id="type" value="${model.type}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
					菜品特性
				</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						特性名称(中文)：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				<c:if test="${model.type==2}">
					<div class="yj-add-divL">
						<div class="div-label">
							特性名称(英文)：
						</div>
						<div class="div-input">
							<input type="text" name="enName" id="enName" value="${model.enName}">
						</div>
					</div>
				</c:if>
				<div class="yj-add-divL">
					<div class="div-label">
						选项类型：
					</div>
					<div class="div-input">
						<select id="s_optionType" name="optionType">
							<option value="1" ${model.optionType == 1 ?'selected':''}>单选</option>
							<option value="2" ${model.optionType == 2 ?'selected':''}>多选</option>
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						对应金额：
					</div>
					<div class="div-input">
						<input type="text" name="money" id="money" value="${model.money}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						是否选用：
					</div>
					<div class="div-input">
						<select id="s_isSelected" name="isSelected">
							<option value="1" ${model.isSelected == 1 ?'selected':''}>选用</option>
							<option value="2" ${model.isSelected == 2 ?'selected':''}>不选用</option>
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="div-label" style="float: none;">
				选项信息:
			</div>
			<div style="overflow:auto;height: 190px;" class="gundong">
				<table style="border: 0.5px solid #858585;width: 100%;border-collapse: collapse;font-size: 13px;text-align: center;">
					<thead>
					<tr>
						<th width="8%">选项值</th>
						<th width="25%">选项名称(中文)</th>
						<th width="25%">选项名称(英文)</th>
						<th width="17%">是否默认</th>
						<th width="25%">对应金额(S$)</th>
						<%--<th>编辑选项</th>--%>
					</tr>
					</thead>
					<tbody id="tbody_update">
						<c:forEach items="${model.options_update}" var="opt" varStatus="s">
							<tr><td><input type="hidden" name="options_update[${s.index}].eid" value="${opt.eid}">
								<input type="text" name="options_update[${s.index}].optionValue" readonly value="${opt.optionValue}"></td>
								<td><input type="text" name="options_update[${s.index}].name" value="${opt.name}"></td>
								<td><input type="text" name="options_update[${s.index}].enName" value="${opt.enName}"></td>
								<td><select class="isDefault" name="options_update[${s.index}].isDefault">
									<option value="1" ${opt.isDefault == 1 ?'selected':''}>默认</option>
									<option value="2" ${opt.isDefault == 2 ?'selected':''}>不默认</option>
									</select>
								</td>
								<td><input type="text" name="options_update[${s.index}].money" value="${opt.money}"></td>
							</tr>
						</c:forEach>
					</tbody>
					<tbody id="tbody_insert">
					</tbody>
					<tfoot>
					<tr>
						<td colspan="5" align="center">
							<button style="margin:5px 0px;" class="myBtn" id="addFeaturesOption_btn">&nbsp;添加选项&nbsp;</button>
						</td>
					</tr>
					</tfoot>
				</table>
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
<script type="text/javascript" src="${ctx}/js/tw/dishManagement/createFeatures.js"></script>
</body>
</html>