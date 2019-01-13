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
				<input type="hidden" name="approvalId" id="approvalId" value="${model.approvalId }">
				<input type="hidden" name="btype" id="btype" value="${btype}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">创建报告</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">报告名称：</div>
					<div class="div-input">
						<input type="text" maxlength="15" name="title" id="title" value="${model.title}">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">发起部门：</div>
					<div class="div-input">
						<select name="s_deptId" id="s_deptId"></select>
						<input type="hidden" name="deptId" id="deptId" value="${model.deptId}">
						<input type="hidden" name="deptName" id="deptName" value="${model.deptName}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">发起人：</div>
					<div class="div-input">
						<select name="s_userId" id="s_userId"></select>
						<input type="hidden" name="userId" id="userId" value="${model.userId}">
						<input type="hidden" name="userName" id="userName" value="${model.userName}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">报告字数：</div>
					<div class="div-input">
						<select name="contentNum" id="contentNum">
							<option value="50"  <c:if test="${model.contentNum ==  50}">selected="selected"</c:if>>50字</option>
							<option value="100" <c:if test="${model.contentNum == 100}">selected="selected"</c:if>>100字</option>
							<option value="150" <c:if test="${model.contentNum == 150}">selected="selected"</c:if>>150字</option>
							<option value="200" <c:if test="${model.contentNum == 200}">selected="selected"</c:if>>200字</option>
							<option value="250" <c:if test="${model.contentNum == 250}">selected="selected"</c:if>>250字</option>
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="yj-hr"></div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">批审级数：</div>
					<div class="div-input">
						<select name="approvalLevel" id="approvalLevel">
							<option value="1" <c:if test="${model.approvalLevel == 1}">selected="selected"</c:if>>1级</option>
							<option value="2" <c:if test="${model.approvalLevel == 2}">selected="selected"</c:if>>2级</option>
							<option value="3" <c:if test="${model.approvalLevel == 3}">selected="selected"</c:if>>3级</option>
							<option value="4" <c:if test="${model.approvalLevel == 4}">selected="selected"</c:if>>4级</option>
							<option value="5" <c:if test="${model.approvalLevel == 5}">selected="selected"</c:if>>5级</option>
							<option value="6" <c:if test="${model.approvalLevel == 6}">selected="selected"</c:if>>6级</option>
						</select>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">批审流程：</div>
					<div class="div-input">
						<div class="div-img" id="add_appr">
							<img src="${ctx}/images/icons/check_icon2.png"/>
						</div>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">预览：</div>
					<div class="div-input">
						<div class="div-img" id="toShowPre">
							<img src="${ctx}/images/icons/check_icon2.png">
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
		<div class="yj-hr"></div>
	</div>
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
<script type="text/javascript" src="${ctx}/js/settings/right/create_report.js"></script>
</body>
</html>