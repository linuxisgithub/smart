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
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="useIds" value="${useIds}">
</div>
<div class="apply-container">
	<div class="apply-content">
		<div class="title">收件人</div>
		<!-- 应用部门start -->
		<div class="apply-div" style="float: left;position: relative;">
			<div class="apply-tit">
				<div class="apply-titLeft">部门</div>
				<div class="apply-titright" style="margin-right: 28px;">
					<label>
						<span style="margin:0px 10px 0px 0px;">全部部门</span><input class="person-checkbox" type="checkbox" name="dept-all" ><span id="dept_all" class="person-checkboxInput"></span>
					</label>
				</div>
				<div class="clear"></div>

			</div>
			<div class="apply-listbox">
				<ul id="dept_list"></ul>
			</div>
		</div>
		<!-- 应用部门end -->
		<!-- 应用人员start -->
		<div class="apply-div" style="float:right;position: relative;">
			<div class="apply-tit">
				<div class="apply-titLeft">人员</div>
				<div class="apply-titright" style="margin-right: 28px;">
					<label>
						<span style="margin:0px 10px 0px 0px;">全部人员</span><input class="person-checkbox" type="checkbox" name="user-all" ><span class="person-checkboxInput"></span>
					</label>
				</div>
				<div class="clear"></div>

			</div>
			<div class="apply-listbox">
				<div class="apply-listmenu">
					<span class="worker-name">姓名</span><span class="worker-job">职务</span>
				</div>
				<ul id="user_list"></ul>
			</div>
		</div>
		<!-- 应用人员end -->
	</div>
	<div class="clear"></div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="layer-btn-w100 submit-btn" id="save_btn">确<span class="wzkg"></span>定</button>
		<button class="layer-btn-w100 return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/platformManagement/suggestions/select.js"></script>
</body>
</html>