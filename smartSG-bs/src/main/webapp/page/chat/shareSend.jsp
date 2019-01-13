<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>通讯录列表</title>
<script type="text/javascript" src="${ctx}/chat/js/jquery-1.8.3.min.js"></script>

<link href="${ctx}/chat/css/tableCSS/bootstrap.min.css?v=3.4.0"
	rel="stylesheet">
<link
	href="${ctx}/chat/css/tableCSS/font-awesome/css/font-awesome.css?v=4.3.0"
	rel="stylesheet">
<link href="${ctx}/chat/css/tableCSS/style.css?v=2.2.0" rel="stylesheet">

<meta name="keywords" content=" ">
<meta name="description" content=" ">
<script type="text/javascript">
	$(function() {
		//单选
		$(":checkbox[type=checkbox]").click(function() {
			$(":checkbox[type=checkbox]").not($(this)).removeAttr("checked");
		});
	});
	
	function shareSend() {
		var result = {};
		result['ShareAppName'] = '云境';
		result['shareContent'] = decodeURI('${param.shareContent }');
		result['shareIconUrl'] = 'http://zschun.blob.core.chinacloudapi.cn/erp/ic_launcher_erp.png';
		result['shareTitle'] = decodeURI('${param.shareTitle }');
		result['shareUrl'] = '${param.shareUrl }';
		var toStr = $(":checkbox[type=checkbox]:checked").val();
		if(typeof(toStr) == "undefined") {
			return false;
		}
		var array = toStr.split("-");
		var message = {'attachment':{}};
		message['to'] = array[0];
		message['toName'] = array[1];
		message['from'] = "${currentUser.imAccount }";
		message.attachment['shareDic'] = result;
		var duerlType = '${param.duerlType }';
		if(duerlType!=null){
			parent.parent.parent.imShareSend(message);
		}else{
			parent.parent.imShareSend(message);
		}
	 	var index = parent.layer.getFrameIndex(window.name);  
	 	parent.layer.close(index); 
	}
	
</script>

</head>

<body>
	<div class="row">
		<div class="row">
			<!--自定义响应式开始-->
			<div class="col-lg-12" style="padding-bottom: 60px">
				<div class="ibox float-e-margins">
					<div class="ibox-content" style="margin-top: 15px;">

						<div>
							<section>
								<div class="tabs tabs-style-topline">

									<div class="content-wrap">
										<section id="section-topline-1">
											<table class="table table-striped table-bordered">
												<thead>
													<tr>
														<th></th>
														<th>姓名</th>
														<th>选择</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${users}" var="userMap">
														<c:forEach items="${userMap.value}" var="item" varStatus="status">
															<c:if test="${currentUser.imAccount ne item.imAccount}">
																<tr>
																	<td><c:if test="${status.count eq 1}">${userMap.key}</c:if></td>
																	<td>${item.name}</td>
																	<td><input type="checkbox" name="selectUsers_${status.count}"
																		value="${item.imAccount}-${item.name}" /></td>
																</tr>
															</c:if>
														</c:forEach>
													</c:forEach>
												</tbody>
											</table>
										</section>

									</div>
								</div>
							</section>
						</div>

						<div
							style=" height: 50px; background-color: rgb(255, 255, 255); top: 260px; border-top: 1px solid #E7E7E7;">
							<div class="ibox-tools">
								<span class="input-group-btn">
									<button type="button" onclick="shareSend()";
										class="btn btn-primary btn-sm"
										style="margin-top: 7px; width: 153px;">保<span class="wzkg"></span>存</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>