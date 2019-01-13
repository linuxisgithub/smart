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


	var params;
	var url;

	var obj = "";
	function save() {
    	
    	$('input:checkbox:checked').each(function(){
    		var eid = $(this).val();
			var name = $(this).data("uname");
			var imAccount = $(this).data("imAccount");
            obj += '{"imAccount":'+imAccount+',' +' "eid":' + eid + ',' + '"name":' + '"' + name + '"' + '},';
          });
    	obj = obj.substring(0, obj.length - 1);
    	var sendjSon = "[" + obj + "]";
    	
		var remark = $(window.parent.document).find("#work_remark").val();
		var annnex = $(window.parent.document).find("#work_annex").val();
		
		params = {"remark":remark,"receiveIds":sendjSon,"annex":annnex};
		url = "${ctx}/ddxAffair/save.htm";
		
		
		$.post(url,params,function(rst){
			if(rst){
				if(rst.status == 200){
					
					 obj = "";
		    		 $(window.parent.document).find("#work_remark").val("");
		    		 $(window.parent.document).find("#work_annex").val("");
		    		 $(window.parent.document).find("#work_fileImg").attr("src","${ctx}/images/icons/accessory_icon1.png");
					
					//关闭当前窗口
					var layerIndex = parent.layer.getFrameIndex(window.name);
					parent.layer.close(layerIndex);
					
					 parent.createTips.message("发送成功！", createTips.ok); 
					
				}else{
					parent.createTips.message("发送失败！", createTips.warn);
				}
			}
		},'json');
		
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
													<c:forEach items="${users}" var="users">
														<c:forEach items="${users.value}" var="item"
															varStatus="status">
															<c:if test="${currentUser.imAccount ne item.imAccount}">
																<tr>
																	<td><c:if test="${status.count eq 1}">${users.key}</c:if></td>
																	<td>${item.name}</td>
																	<td><input type="checkbox" name="selectUsers1"
																		value="${item.eid}" data-uname="${item.name}" data-imAccount="${item.imAccount}"/></td>
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
							style="width: 420px; height: 50px; position: fixed;padding-right: 140px; background-color: rgb(255, 255, 255); top: 260px; border-top: 1px solid #E7E7E7;">
							<div class="ibox-tools">
								<span class="input-group-btn">
									<button onclick="save();" type="button"
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