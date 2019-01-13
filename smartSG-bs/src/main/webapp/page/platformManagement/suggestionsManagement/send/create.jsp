<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
<style type="text/css">
	#s_receiveIds{
		float: left;
		width: 380px;
		height: 33px;
		border: solid 1px #ccc;
    	border-radius: 5px;
    	cursor: pointer;
    	padding: 10px;
	}
</style>
</head>
<body>
<div class="mainRight-tab-conW">
	<div class="tab-con">
		<form action="${ctx}/comNotice/save.htm" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">发送消息</div>
				<div style="float: right;min-width: 80px;font-weight: 600;">
					日<span class="wzkg"></span>期：<input style="outline: none;border: none;" value="${model.createTime }">
				</div>
			</div>
			<div class="yj-add-div1">
				<div class="yj-add-divL">
					<div class="div-label">收件人：</div>
					<input id="s_receiveIds" readonly="readonly" value="${ccModel }">
					<input type="hidden" name="cc" id="cc">
					<input type="hidden" id="useIds" value="${model.cc}">
				</div>
				<div class="yj-add-divL">
					<div class="div-label">消息类型：</div>
					<select name="kind" id="s_kind" style="width:170px;">
					</select>
					<input type="hidden" id="i_kind" value="${model.kind }">
				</div>
				<div class="clear"></div>
			</div>
			<div class="yj-hr"></div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">意见内容：</div>
					<div class="div-textarea">
						<textarea name="content" maxlength="500" id="s_content" rows="15">${model.content }</textarea>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">发<span class="wzkg"></span>送</button>
		<button class="reset-btn" style="display: none;" id="clear_btn">清<span class="wzkg"></span>空</button>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/platformManagement/suggestions/create.js"></script>
</body>
</html>