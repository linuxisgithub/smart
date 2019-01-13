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
	<input type="hidden" id="bid" value="${bid}">
	<input type="hidden" id="type" value="${type}">
</div>
<div class="apply-container" style="width: 335px;">
	<div class="apply-content">
		<div class="title">
		复制并应用于
		</div>
		<!-- 应用部门start -->
		<div class="apply-div" style="float: left;position: relative;">
			<div class="apply-tit">
				<div class="apply-titLeft">
					应用${type == 'sggl' ? '食阁':'摊位'}
				</div>
				<div class="apply-titright" style="margin-right: 28px;">
					<label>
						<span style="margin:0px 10px 0px 0px;">全部${type == 'sggl' ? '食阁':'摊位'}</span><input class="person-checkbox" type="checkbox" name="dept-all" ><span id="dept_all" class="person-checkboxInput"></span>
					</label>
				</div>
				<div class="clear"></div>
			</div>
			<div class="apply-listbox">
				<ul id="dept_list"></ul>
			</div>
		</div>
		<!-- 应用部门end -->
	</div>
	<div class="clear"></div>
</div>
<div style="display: none" alt="隐藏表单域">
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
<script type="text/javascript" src="${ctx}/js/sgManagmentCompany/sgManagement/apply.js"></script>
</body>
</html>