<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>修改密码</title>
<link href="${path}/tools/sui/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${path}/chat/js/ddx_gd/jquery.min.js"></script>
<script type="text/javascript" src="${path}/tools/sui/js/sui.min.js"></script>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript">

$(function() {
	if("${status}" == 200){
		parent.createTips.message("修改密码成功", createTips.ok);
		//关闭当前窗口
       	var index = parent.layer.getFrameIndex(window.name);
       	parent.layer.close(index);
	} else if("${status}" == 500){
		parent.createTips.message("修改密码失败！", createTips.ok);
   	} else if("${status}" == 501){
   		parent.createTips.message("修改密码失败,原密码错误！", createTips.ok);
    }
});

$(function(){
	var match = function(value, element, param) {
		value = trim(value);
		return value == $(element).parents('form').find("[name='"+param+"']").val();
	};
	$.validate.setRule("match", match, '两次密码不一致！');
}); 
</script>
</head>
<body>
<form class="sui-form form-horizontal sui-validate" id="jsForm"
	action="${path}/DDX/editPwd.htm" method="post" style="padding: 30px 0px 0px 0px;">
	<div class="control-group" style="margin: 0 auto; margin-bottom: 20px;">
		<label for="inputEmail" class="control-label"><em
			style="color: red;">*&nbsp;</em>原密码：</label>
		<div class="controls">
			<input type="password" id="oldPassword" name="oldPassword" class="input-large" style="height: 32px;width: 157px;"
				placeholder="请填写原密码" maxlength="20" data-rules="required"/>
		</div>
	</div>
	<div class="control-group" style="margin: 0 auto; margin-bottom: 20px;">
		<label for="inputEmail" class="control-label"><em
			style="color: red;">*&nbsp;</em>新密码：</label>
		<div class="controls">
			<input type="password" value="" id="newPassword" name="newPassword" class="input-large" style="height: 32px;width: 157px;"
				placeholder="请填写新密码" data-rules="required|minlength=6" maxlength="20" />

		</div>
	</div>
	<div class="control-group" style="margin: 0 auto; margin-bottom: 20px;">
		<label for="inputEmail" class="control-label"><em
			style="color: red;">*&nbsp;</em>确认密码：</label>
		<div class="controls">
			<input type="password" id="confirmPassword" name="confirmPassword" class="input-large" style="height: 32px;width: 157px;"
				placeholder="请填写确认密码" data-rules="required|match=newPassword|minlength=6" maxlength="20" />
		</div>
	</div>
	<div class="control-group" style="margin-left:85px; margin-bottom: 20px;">
		<label class="control-label"></label>
		<div class="controls">
			<button type="submit" class="sui-btn btn-large btn-primary">修&nbsp;&nbsp;改</button>
		</div>
	</div>
</form>

</body>
</html>
