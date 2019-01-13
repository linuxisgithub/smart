<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无权访问页面</title>
</head>
<body style="background-color: #fff">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript">
	parent.$.prompt.message("您没有权限使用该功能！", $.prompt.msg);
</script>
</body>
</html>