<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>建议添加</title>
<meta name="keywords" content=" ">
<meta name="description" content=" ">
<script type="text/javascript">
$(function() {
	if("${createStatus}" == "200"){
		window.parent.parent.$.createTips({"msg":"保存我的建议成功","type":"success"});
		
		//关闭当前窗口
		window.parent.cloaseCreate();
		
	}else if("${createStatus}" == "500"){
		window.parent.parent.$.createTips({"msg":"保存我的建议失败","type":"error"});
	}
	//初始化上传附件插件
// 	initJQueryFiler("file_input");
});

$(function(){
    //jquery.validate
	$("#jsForm").validate({
		submitHandler: function() {
			//验证通过后 的js代码写在这里
			numSubmitHandler++;$('button[type=submit]').prop({disabled: 'disabled'});$("#jsForm").submit();
		}
	})
})
</script>
</head>

<body >
<div >
	<form action="${ctx}/Advise/save.htm" method="post" class="bl-form bl-formhor" id="jsForm">
		<input type="hidden" name="type" id="type" value="${query.type }">
        <ul>
            <li class="bl-form-group">
                <label><em>*&nbsp;</em>标题：</label>
                <div class="controls">
				  <input  class="fn-tinput" name="name" value="${advise.name }" required />
                </div>
            </li>
            <li class="bl-form-group">
                <label><em>*&nbsp;</em>内容：</label>
                <div class="controls editor">
                    <textarea  name="remark" value="${advise.remark }" style="padding:6px;height: 30%;" required placeholder="请输入您想输入的内容" maxlength="200"></textarea>
                </div>
            </li>
            <li class="bl-form-group bl-form-btns">
                <label class="fn-vhid">提交：</label>
                <div class="controls">
                    <button class="submitButton"  type="submit">保<span class="wzkg"></span>存</button>
                </div>
            </li>
        </ul>
    </form>
</div>
</body>
</html>
