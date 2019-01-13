<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
	<style>
		.div-input{
			width: auto!important;
		}

	</style>

</head>
<body>
<div class="mainRight-tab-conW layer">				
	<div class="tab-con text_right">
		<form action="${ctx}/sysUser/save.htm" method="post" id="mainForm" autocomplete="off">
			<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
			<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="companyId" id="companyId" value="${model.companyId}">
				<input type="hidden" name="userType" id="userType" value="${model.userType}">
				<input type="hidden" id="l" value="${model.level}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
					${model.userType == 1 ? '公司内部用户信息':(model.userType == 2 ? '食阁管理公司用户信息':(model.userType == 3 ? '食阁用户信息':(model.userType == 4 ? '摊位用户信息':'食阁所有者用户信息')))}
				</div>
			</div>
			
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">${model.userType == 1 ? '所属部门':(model.userType == 2 ? '所属食阁管理公司':(model.userType == 3 ? '所属食阁名称':(model.userType == 4 ? '所属摊位名称':'所属食阁拥有主体')))}：</div>
					<div class="div-input">
						<select name="s_deptId" id="s_deptId"></select>
						<input type="hidden" name="deptId" id="deptId" value="${model.deptId}">
						<input type="hidden" name="deptName" id="deptName" value="${model.deptName}">
						<input type="hidden" name="deptCode" id="deptCode" value="${model.deptCode}">
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
						<c:if test="${model.level != null && model.level == 3 }">
							<input type="hidden" name="level" value="${model.level}">
							<input type="text" value="系统管理员" disabled="disabled">
						</c:if>
						<c:if test="${model.level == null || model.level != 3 }">
							<select name="level" id="level">
								<option value="1" <c:if test="${model.level == 1}">selected="selected"</c:if>>领导层</option>
								<option value="2" <c:if test="${model.level == 2}">selected="selected"</c:if>>员工层</option>
							</select>
						</c:if>
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
					<div class="div-label">性<span class="wzkg"></span>别：</div>
					<div class="div-input">
						<select name="sex" id="sex">
							<option value="1" <c:if test="${model.sex == 1}">selected="selected"</c:if>>男</option>
							<option value="2" <c:if test="${model.sex == 2}">selected="selected"</c:if>>女</option>
						</select>
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
					<div class="div-label">系统账号：</div>
					<div class="div-input">
						<input type="text" name="account" id="account" value="${model.account}" autocomplete="off"/>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">初始密码：</div>
					<div class="div-input">
						<c:if test="${empty model.eid}">
                        	<input type="hidden" name="showPassword" id="showPassword" value="">
						</c:if>
						<c:choose>
							<c:when test="${empty model.eid}">
							<input type="text" onfocus="this.type='password'" name="password" id="password" value="" autocomplete="off"/>
							</c:when>
							<c:otherwise>
							<input type="text" onfocus="this.type='password'" name="password" id="password" value="******" autocomplete="off"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">账号授权：</div>
					<div class="div-input">
					<c:if test="${model.status eq 1 || model.status eq null}">
                        <input type="radio" name="status" value="1" checked="checked" /><span class="wzkg"></span>启用
                        <input type="radio" name="status" value="2" style="margin-left:20px;"/><span class="wzkg"></span>停用
                    </c:if>
                    <c:if test="${model.status eq 2}">
                        <input type="radio" name="status" value="1" /><span class="wzkg"></span><span class="wzkg"></span>启用
                        <input type="radio" name="status" value="2" checked="checked" style="margin-left:20px;"/><span class="wzkg"></span>停用
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
		<c:if test="${!empty model.eid}">
			<button class="appro_btn" id="black_btn">加入黑名单</button>
		</c:if>
		<button class="submit-btn" id="save_btn">保<span class="wzkg"></span>存</button>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/tools/md5/md5.js"></script>
<script type="text/javascript" src="${ctx}/js/sysUser/create.js"></script>
</body>
</html>