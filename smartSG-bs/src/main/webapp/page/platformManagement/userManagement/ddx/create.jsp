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
	<div class="tab-con text_right">
		<form action="${ctx}/sysUser/save.htm" method="post" id="mainForm">
			<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
			<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="account" id="account" value="${model.account}">
				<input type="hidden" name="userType" id="userType" value="${model.userType}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
					${model.userType == 1 ? '公司内部用户信息':(model.userType == 2 ? '食阁管理公司用户信息':(model.userType == 3 ? '食阁用户信息':(model.userType == 4 ? '摊位用户信息':'食阁所有者用户信息')))}
				</div>
			</div>
			
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">部<span class="wzkg"></span>门：</div>
					<div class="div-input">
						<select name="s_deptId" id="s_deptId"></select>
						<input type="hidden" name="deptId" id="deptId" value="${model.deptId}">
						<input type="hidden" name="deptName" id="deptName" value="${model.deptName}">
					</div>
				</div>
				
				<div class="yj-add-divL">
					<div class="div-label">职<span class="wzkg"></span>务：</div>
					<div class="div-input">
						<input type="text" name="job" id="job" value="${model.job}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">层级归属：</div>
					<div class="div-input">
						<select name="level" id="level">
							<option value="1" <c:if test="${model.level == 1}">selected="selected"</c:if>>领导层</option>
							<option value="2" <c:if test="${model.level == 2}">selected="selected"</c:if>>员工层</option>
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">姓<span class="wzkg"></span>名：</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				
				<div class="yj-add-divL">
					<div class="div-label">电<span class="wzkg"></span>话：</div>
					<div class="div-input">
						<input type="text" name="telephone" id="telephone" value="${model.telephone}">
					</div>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="yj-hr"></div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">IM账号：</div>
					<div class="div-input">
						<input type="text" name="imAccount" id="imAccount" value="${model.imAccount}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">密<span class="wzkg"></span>码：</div>
					<div class="div-input">
						<input type="password" name="password" id="password" disabled="disabled" value="******">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">状<span class="wzkg"></span>态：</div>
					<div class="div-input">
					 <c:if test="${model.imStatus eq 1}">
                        <input type="radio" name="imStatus" value="1" checked="checked" /><span class="wzkg"></span>启用
                        <input type="radio" name="imStatus" value="0" style="margin-left:20px;"/><span class="wzkg"></span>停用
                    </c:if>
                    <c:if test="${model.imStatus eq 0 || model.imStatus eq null}">
                        <input type="radio" name="imStatus" value="1" /><span class="wzkg"></span>启用
                        <input type="radio" name="imStatus" value="0" checked="checked" style="margin-left:20px;"/><span class="wzkg"></span>停用
                    </c:if>
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
		<button class="submit-btn" id="save_btn">确定</button>
		<button class="return_btn" id="return_btn">返回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/sysUser/ddx_create.js"></script>
</body>
</html>