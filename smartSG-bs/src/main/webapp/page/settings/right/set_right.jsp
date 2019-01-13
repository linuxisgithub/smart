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
	<input type="hidden" name="eid" id="eid" value="${eid}">
</div>
<div class="apply-container">
	<div class="apply-content">
		<div class="title">
		${title}
		</div>
		<!-- <div class="hr" style="margin-bottom: 10px;"></div>
		<div class="range">
			<strong>应用范围：</strong>
			<label class="person-label"><input class="person-radio" id="kind_1" value="1" type="radio" name="apply-range" checked="checked"><span class="person-radioInput"></span><span style="margin:0px 25px 0px 8px;">全公司</span></label>
			<label class="person-label"><input class="person-radio" id="kind_2" value="2" type="radio" name="apply-range" ><span class="person-radioInput"></span><span style="margin:0px 25px 0px 8px;">部门</span></label>
			<label class="person-label"><input class="person-radio" id="kind_3" value="3" type="radio" name="apply-range" ><span class="person-radioInput"></span><span style="margin:0px 25px 0px 8px;">职员</span></label>

		</div> -->
		<!-- 应用部门start -->
		<div class="apply-div" style="float: left;position: relative;">
			<!-- <div class="shadow" id="dept_shade"></div> -->
			<div class="apply-tit">
				<div class="apply-titLeft">
					应用部门
				</div>
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
			<!-- <div class="shadow" id="user_shade"></div> -->
			<div class="apply-tit">
				<div class="apply-titLeft">
					应用人员
				</div>
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
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="menuId" value="${menuId}">
	<input type="hidden" id="menuName" value="${menuName}">
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="kind" value="${kind}">
	<input type="hidden" id="useIds" value="${useIds}">
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
<script type="text/javascript" src="${ctx}/js/settings/right/set_right.js"></script>
</body>
</html>