<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<title>表格样式</title>

</head>
<body>
<div class="mainRight-tab-conW layer">				
	<div class="tab-con">
		<form action="${ctx}/sysUser/save.htm" method="post" id="mainForm">
			<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
			<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="account" id="account" value="${model.account}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
					个人信息
				</div>
			</div>
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">姓<span class="wzkg"></span><span class="wzkg"></span>名：</div>
					<div class="div-input">
						${model.name}
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">联系电话：</div>
					<div class="div-input">
						${model.telephone}
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">部<span class="wzkg"></span><span class="wzkg"></span>门：</div>
					<div class="div-input">
						${model.deptName}
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">职<span class="wzkg"></span><span class="wzkg"></span>位：</div>
					<div class="div-input">
						${model.job}
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			
			<%-- <div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">性<span class="wzkg"></span><span class="wzkg"></span>别：</div>
					<div class="div-input">
						<c:if test="${model.sex == 1}">男</c:if>
						<c:if test="${model.sex == 2}">女</c:if>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">出生年月：</div>
					<div class="div-input">
						${model.birthDate}
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">学<span class="wzkg"></span><span class="wzkg"></span>历：</div>
					<div class="div-input">
						<c:if test="${model.education == 1}">初中</c:if></option>
						<c:if test="${model.education == 2}">高中</c:if></option>
						<c:if test="${model.education == 3}">大专</c:if></option>
						<c:if test="${model.education == 4}">本科</c:if></option>
						<c:if test="${model.education == 5}">硕士</c:if></option>
						<c:if test="${model.education == 6}">博士</c:if></option>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">联系电话：</div>
					<div class="div-input">
						${model.telephone}
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div> --%>
			
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">登录密码：</div>
					<div class="div-input">
						<input type="password" name="password" id="password" value="******">
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">保<span class="wzkg"></span>存</button>
		<span class="wzkg"></span>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/sysUser/myInfo.js"></script>
</body>
</html>