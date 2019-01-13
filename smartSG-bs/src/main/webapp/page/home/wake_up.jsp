<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="Expires" CONTENT="-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="Refresh" content="0;cxapp://sale.cn/gZcxSale8"/>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/tools/jquery/1.10.2/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/css/invite.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function(){
		var android = "http://zhushou.360.cn/detail/index/soft_id/3930893?recrefer=SE_D_";
		var ios = "https://itunes.apple.com/cn/app/%E5%8F%AE%E5%BD%93%E4%BA%AB%E9%94%80%E5%94%AE%E9%80%9A/id1316908938";
		$("#AndroidUrl").click(function (){
	    	window.location.href = android;
		});
		
		$("#IosUrl").click(function (){
			window.location.href = ios;
		})
		
	});
</script>
<title>叮当享销售通</title>
</head>
<body>
<div class="wraper">
		<div class="context">
			<div class="div-left">
				<!-- <p>下载销售通</p> -->
				<div class="download">
					<button id="AndroidUrl">
						<img src="${pageContext.request.contextPath}/image/android.png">
					</button>
				</div>
			</div>
			<div class="div-left">
				<!-- <p>下载销售通</p> -->
				<div class="download">
					<button id="IosUrl">
						<img src="${pageContext.request.contextPath}/image/ios.png">
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>