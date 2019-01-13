<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/bootstrap-v3.3.7/bootstrap.min.css"/>
<title>表格样式</title>
	<style>
		.ipt input{
			width: 100%!important;
			border: 0px!important;
		}
		.ipt tr{
			text-align: center!important;
		}
		.ipt td{
			padding:0px!important;
		}
		h4{
			margin-top:0px!important;
			font-size:15px;
		}
		.yj-add-div2{
			padding:7px 0px;
		}

	</style>
</head>
<body style="overflow: hidden">
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<form action="${ctx}/comNotice/save.htm" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" name="type" id="type" value="${model.type}">
				<input type="hidden" name="dtype" id="dtype" value="${model.dtype}">
			</div>
			<div class="yj-add-top" style="height: 45px;">
				<div class="yj-add-title">
				${model.type == 2 ? '食阁管理公司':(model.type == 3 ? '食阁':(model.type == 4 ? '摊位':'食阁拥有者'))}
				</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						${model.type == 2 ? '公司名称':(model.type == 3 ? '食阁':(model.type == 4 ? '摊位英文名称':'公司名称'))}：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				<c:if test="${model.type == 4}">
					<div class="yj-add-divL">
						<div class="div-label">
							摊位中文名称：
						</div>
						<div class="div-input">
							<input type="text" name="nameCh" id="nameCh" value="${model.nameCh}">
						</div>
					</div>
				</c:if>
				<c:if test="${model.type == 2 || model.type == 3 || model.type == 4 || model.type == 5}">
					<div class="yj-add-divL">
						<div class="div-label">
								所属部门：
						</div>
						<div class="div-input">
							<select name="s_pid" id="s_pid" ${USER_IN_SESSION.user.userType eq 2 and model.type == 3 ? 'disabled':''}></select>
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
								<option value="4" <c:if test="${model.mainJob == 4}">selected="selected"</c:if>>老板</option>
								<option value="2" <c:if test="${model.mainJob == 2}">selected="selected"</c:if>>总经理</option>
								<option value="6" <c:if test="${model.mainJob == 6}">selected="selected"</c:if>>主任</option>
								<option value="7" <c:if test="${model.mainJob == 7}">selected="selected"</c:if>>管理员</option>
						</select>
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">副职称谓：</div>
					<div class="div-input">
						<select name="lessJob" id="lessJob">
								<option value="2" <c:if test="${model.lessJob == 2}">selected="selected"</c:if>>副总经理</option>
								<option value="4" <c:if test="${model.lessJob == 4}">selected="selected"</c:if>>副经理</option>
								<option value="6" <c:if test="${model.lessJob == 6}">selected="selected"</c:if>>副主任</option>
								<option value="7" <c:if test="${model.lessJob == 7}">selected="selected"</c:if>>副管理员</option>

						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			

                <div class="yj-hr"></div>
                <div class="yj-add-div2">
					<h4>食阁管理公司信息</h4>
					<c:if test="${model.type == 2}">
						<input type="hidden" name="sggl.eid" id="sggl_eid" value="${model.sggl.eid}">
						<table class="table table-bordered ipt" style="margin-bottom: 10px;">
							<tr>
								<td>公司ID</td>
								<td colspan="2"><input type="text" name="sggl.myId" id="sggl_myId" value="${model.sggl.myId}"></td>
								<td>地址</td>
								<td colspan="2"><input type="text" name="sggl.address" id="sggl_address" value="${model.sggl.address}"></td>
							</tr>
							<tr>
								<td>邮编</td>
								<td><input type="text" name="sggl.zipCode" id="sggl_zipCode" value="${model.sggl.zipCode}"></td>
								<td>银行卡持有人</td>
								<td><input type="text" name="sggl.bankCardHavePeople" id="sggl_bankCardHavePeople" value="${model.sggl.bankCardHavePeople}"></td>
								<td>银行账户</td>
								<td><input type="text" name="sggl.bankCardAccount" id="sggl_bankCardAccount" value="${model.sggl.bankCardAccount}"></td>
							</tr>
							<tr>
								<td>开户行</td>
								<td><input type="text" name="sggl.bank" id="sggl_bank" value="${model.sggl.bank}"></td>
								<td>已激活食阁用户数</td>
								<td><input type="text" name="sggl.maxNum" id="sggl_maxNum" value="${model.sggl.maxNum}"></td>
								<td>最大食阁用户数</td>
								<td><input type="text" name="sggl.activationNum" id="sggl_activationNum" value="${model.sggl.activationNum}"></td>
							</tr>
						</table>
					</c:if>
					<c:if test="${model.type == 3}">
						<input type="hidden" name="sg.eid" id="sg_eid" value="${model.sg.eid}">
						<table class="table table-bordered ipt" style="margin-bottom: 10px;">
							<tr>
								<td>食阁ID</td>
								<td colspan="2"><input type="text" name="sg.myId" id="sg_myId" value="${model.sg.myId}"></td>
								<td>地址</td>
								<td colspan="2"><input type="text" name="sg.address" id="sg_address" value="${model.sg.address}"></td>
							</tr>
							<tr>
								<td>食阁类型</td>
								<td><select style="width: 100%;border: 0px;" id="sg_type"></select>
									<input type="hidden" name="sg.type" id="sgType" value="${model.sg.type}"></td>
								<td>HALAL/NON/MIX</td>
								<td><select style="width: 100%;border: 0px;" name="sg.halal" id="sg_halal">
										<option value="1" ${model.sg.halal == 1 ? 'selected':''}>HALAL</option>
										<option value="2" ${model.sg.halal == 2 ? 'selected':''}>NON-HALAL</option>
										<option value="3" ${model.sg.halal == 3 ? 'selected':''}>MIX</option>
								    </select>
								</td>
								<td>邮编</td>
								<td><input type="text" name="sg.zipCode" id="sg_zipCode" value="${model.sg.zipCode}"></td>
							</tr>
							<tr>
								<td>食阁所有者</td>
								<td><input type="text" name="sg.have" id="sg_have" value="${model.sg.have}"></td>
								<td>最大档口数</td>
								<td><input type="text" name="sg.maxNum" id="sg_maxNum" value="${model.sg.maxNum}"></td>
								<td>已激活档口数</td>
								<td><input type="text" name="sg.activationNum" id="sg_activationNum" value="${model.sg.activationNum}"></td>
							</tr>
						</table>
					</c:if>
					<c:if test="${model.type == 4}">
						<input type="hidden" name="tw.eid" id="tw_eid" value="${model.tw.eid}">
						<table class="table table-bordered ipt" style="margin-bottom: 10px;">
							<tr>
								<td>摊位ID</td>
								<td colspan="2"><input type="text" name="tw.myId" id="tw_myId" value="${model.tw.myId}"></td>
								<td>门牌号</td>
								<td colspan="2"><input type="text" name="tw.houseNumber" id="tw_houseNumber" value="${model.tw.houseNumber}"></td>
							</tr>
							<tr>
								<td>支付宝用户名称</td>
								<td colspan="2"><input type="text" name="tw.alipayName" id="tw_alipayName" value="${model.tw.alipayName}"></td>
								<td>支付宝账户</td>
								<td colspan="2"><input type="text" name="tw.alipayAccount" id="tw_alipayAccount" value="${model.tw.alipayAccount}"></td>
							</tr>
							<tr>
								<td>银行账户</td>
								<td colspan="2"><input type="text" name="tw.bankCardAccount" id="tw_bankCardAccount" value="${model.tw.bankCardAccount}"></td>
								<td>开户行</td>
								<td colspan="2"><input type="text" name="tw.bank" id="tw_bank" value="${model.tw.bank}"></td>
							</tr>
						</table>
					</c:if>
					<c:if test="${model.type == 5}">
						<input type="hidden" name="sgyyz.eid" id="sgyyz_eid" value="${model.sgyyz.eid}">
						<table class="table table-bordered ipt" style="margin-bottom: 10px;">
							<tr>
								<td>公司ID</td>
								<td colspan="2"><input type="text" name="sgyyz.myId" id="sgyyz_myId" value="${model.sgyyz.myId}"></td>
								<td>地址</td>
								<td colspan="2"><input type="text" name="sgyyz.address" id="sgyyz_address" value="${model.sgyyz.address}"></td>
							</tr>
							<tr>
								<td>邮编</td>
								<td colspan="2"><input type="text" name="sgyyz.zipCode" id="sgyyz_zipCode" value="${model.sgyyz.zipCode}"></td>
								<td>银行卡持有人</td>
								<td colspan="2"><input type="text" name="sgyyz.bankCardHavePeople" id="sgyyz_bankCardHavePeople" value="${model.sgyyz.bankCardHavePeople}"></td>
							</tr>
							<tr>
								<td>银行账户</td>
								<td colspan="2"><input type="text" name="sgyyz.bankCardAccount" id="sgyyz_bankCardAccount" value="${model.sgyyz.bankCardAccount}"></td>
								<td>开户行</td>
								<td colspan="2"><input type="text" name="sgyyz.bank" id="sgyyz_bank" value="${model.sgyyz.bank}"></td>
							</tr>
						</table>
					</c:if>
					<h4>联系人信息</h4>
					<table class="table table-bordered ipt">
						<thead>
							<td>联系人</td>
							<td>职位</td>
							<c:if test="${model.type == 4}">
								<td>身份证</td>
							</c:if>
							<td>手机</td>
							<td>座机</td>
							<td>邮件</td>
						</thead>
						<tr>
							<td><input type="text" name="contactsList[0].name" id="contactsList0_name" value="${model.contactsList[0].name}">
								<input type="hidden" name="contactsList[0].eid" id="contactsList0_eid" value="${model.contactsList[0].eid}"></td>
							<td><input type="text" name="contactsList[0].job" id="contactsList0_job" value="${model.contactsList[0].job}"></td>
							<c:if test="${model.type == 4}">
								<td><input type="text" name="contactsList[0].idCard" id="contactsList0_idCard" value="${model.contactsList[0].idCard}"></td>
							</c:if>
							<td><input type="text" name="contactsList[0].phone" id="contactsList0_phone" value="${model.contactsList[0].phone}"></td>
							<td><input type="text" name="contactsList[0].telephone" id="contactsList0_telephone" value="${model.contactsList[0].telephone}"></td>
							<td><input type="text" name="contactsList[0].email" id="contactsList0_email" value="${model.contactsList[0].email}"></td>
						</tr>
						<tr>
							<td><input type="text" name="contactsList[1].name" id="contactsList1_name" value="${model.contactsList[1].name}">
								<input type="hidden" name="contactsList[1].eid" id="contactsList1_eid" value="${model.contactsList[1].eid}"></td>
							<td><input type="text" name="contactsList[1].job" id="contactsList1_job" value="${model.contactsList[1].job}"></td>
							<c:if test="${model.type == 4}">
								<td><input type="text" name="contactsList[1].idCard" id="contactsList1_idCard" value="${model.contactsList[1].idCard}"></td>
							</c:if>
							<td><input type="text" name="contactsList[1].phone" id="contactsList1_phone" value="${model.contactsList[1].phone}"></td>
							<td><input type="text" name="contactsList[1].telephone" id="contactsList1_telephone" value="${model.contactsList[1].telephone}"></td>
							<td><input type="text" name="contactsList[1].email" id="contactsList1_email" value="${model.contactsList[1].email}"></td>
						</tr>
					</table>
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
<script type="text/javascript" src="${ctx}/js/dept/create_sg.js"></script>
</body>
</html>