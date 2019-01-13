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
				<input type="hidden" name="type" id="type" value="${model.type}">
				<input type="hidden" name="dtype" id="dtype" value="${model.dtype}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
				${model.type == 1 or model.type == 0 ? '公司层面':(model.type == 2 ? '本公司一级部门':(model.type == 3 ? '本公司二级部门':(model.type == 4 ? '本公司三级部门':'食阁拥有者')))}
				</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						部门名称：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}" 
							<c:if test="${model.name == '董事会' || model.name == '公司'}">readonly="readonly"</c:if>>
					</div>
				</div>
				<c:if test="${model.type == 2 || model.type == 3 || model.type == 4 || model.type == 5}">
				<div class="yj-add-divL">
					<div class="div-label">
							所属部门：
					</div>
					<div class="div-input">
						<select name="s_pid" id="s_pid"></select>
						<input type="hidden" name="pid" id="pid" value="${model.pid}">
						<input type="hidden" name="pname" id="pname" value="${model.pname}">
					</div>
				</div>
				</c:if>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
							正职称谓:
					</div>
					<div class="div-input">
						<select name="mainJob" id="mainJob">
							<c:if test="${model.type == 1 || model.type == 0}">
								<option value="0" <c:if test="${model.mainJob == 0}">selected="selected"</c:if>>董事长</option>
								<option value="1" <c:if test="${model.mainJob == 1}">selected="selected"</c:if>>总裁</option>
								<option value="2" <c:if test="${model.mainJob == 2}">selected="selected"</c:if>>总经理</option>
								<option value="3" <c:if test="${model.mainJob == 3}">selected="selected"</c:if>>总监</option>
							</c:if>
							<c:if test="${model.type == 2 || model.type == 3}">
								<option value="4" <c:if test="${model.mainJob == 4}">selected="selected"</c:if>>老板</option>
								<option value="2" <c:if test="${model.mainJob == 2}">selected="selected"</c:if>>总经理</option>
								<option value="5" <c:if test="${model.mainJob == 5}">selected="selected"</c:if>>部长</option>
								<option value="6" <c:if test="${model.mainJob == 6}">selected="selected"</c:if>>主任</option>
								<option value="7" <c:if test="${model.mainJob == 7}">selected="selected"</c:if>>管理员</option>
							</c:if>
							<c:if test="${model.type == 4}">
								<option value="8" <c:if test="${model.mainJob == 8}">selected="selected"</c:if>>组长</option>
							</c:if>
						</select>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">副职称谓：</div>
					<div class="div-input">
						<select name="lessJob" id="lessJob">
							<c:if test="${model.type == 1 || model.type == 0}">
								<option value="0" <c:if test="${model.lessJob == 0}">selected="selected"</c:if>>副董事长</option>
								<option value="1" <c:if test="${model.lessJob == 1}">selected="selected"</c:if>>副总裁</option>
								<option value="2" <c:if test="${model.lessJob == 2}">selected="selected"</c:if>>副总经理</option>
								<option value="3" <c:if test="${model.lessJob == 3}">selected="selected"</c:if>>副总监</option>
							</c:if>
							<c:if test="${model.type == 2 || model.type == 3}">
								<option value="2" <c:if test="${model.mainJob == 2}">selected="selected"</c:if>>副总经理</option>
								<option value="4" <c:if test="${model.mainJob == 4}">selected="selected"</c:if>>副经理</option>
								<option value="5" <c:if test="${model.mainJob == 5}">selected="selected"</c:if>>副部长</option>
								<option value="6" <c:if test="${model.mainJob == 6}">selected="selected"</c:if>>副主任</option>
								<option value="7" <c:if test="${model.mainJob == 7}">selected="selected"</c:if>>副管理员</option>
							</c:if>
							<c:if test="${model.type == 4}">
								<option value="8" <c:if test="${model.mainJob == 8}">selected="selected"</c:if>>副组长</option>
							</c:if>
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
<script type="text/javascript" src="${ctx}/js/dept/create.js"></script>
</body>
</html>