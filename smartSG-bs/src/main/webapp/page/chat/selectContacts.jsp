<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>通讯录列表</title>
<script type="text/javascript" src="${ctx}/chat/js/jquery-1.8.3.min.js"></script>

<link href="${ctx}/chat/css/tableCSS/bootstrap.min.css?v=3.4.0"rel="stylesheet">
<link href="${ctx}/chat/css/tableCSS/font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
<link href="${ctx}/chat/css/tableCSS/style.css?v=2.2.0" rel="stylesheet">


<meta name="keywords" content=" ">	
<meta name="description" content=" ">
<script type="text/javascript">
	var selectUsersImAccount = "";
	var selectUsersName = "";
	
    function beforeCheckSelect(){    	    	
    	var setSelectUsers = $('input:checkbox:checked');
    	if (typeof(setSelectUsers) == "undefined" || setSelectUsers.length < 1) { 
        	//window.parent.layer.msg("请选择群组成员");
        	window.parent.layerMsg("请选择群组成员");
        	return ;
    	}
    	var selectUsersData = new Array();
    	setSelectUsers.each(function(){
    		var idName = $(this).val();
    		var index = idName.indexOf(",");
    		var imAccount = idName.substring(0,index);
    		var name = idName.substring(index+1,idName.length);
    		if(imAccount && name){
 				selectUsersImAccount += imAccount+"_";
 	        	selectUsersName += name+",";
 	        	var userObj = new Object(); 
 	        	userObj.imAccount = imAccount; 
 	        	userObj.name = name;
 	        	selectUsersData.push(userObj);  			
    		}   		
    	})
    	    	
    	//去重复   	
    	var uniqueSelectUsersImAccount = "";
		var uniqueSelectUsersName = "";	

  		var uniqueSelectUsersData = [];
  		var idJson = {};
  		for(var i = 0; i < selectUsersData.length; i++){
  			if(!idJson[selectUsersData[i].imAccount]){
  				idJson[selectUsersData[i].imAccount] = 1;
  				uniqueSelectUsersImAccount += selectUsersData[i].imAccount+",";
  				uniqueSelectUsersName += selectUsersData[i].name+",";
  			}
  		} 	 
    	    	
    	selectUsersImAccount = uniqueSelectUsersImAccount.substring(0,uniqueSelectUsersImAccount.length-1);
    	selectUsersName = uniqueSelectUsersName.substring(0,uniqueSelectUsersName.length-1);

    	//设置选择的联系人
   	    /**1123123，2123123，3123，4123**/
       	//window.parent.setSelectContactsImAccount(selectUsersImAccount); 
    	//打开输入群组名称窗口
		window.parent.createGroups(selectUsersImAccount);
        }

    $(function(){
    	
    	/**员工全选/反选**/ 
    	var checkAll = $("#checkAll");
    	var checkboxes = $("input[name='selectUsers1']");    	
    	checkAll.on("click",function(obj){
    		checkboxes.prop('checked', this.checked);
   		})
		checkboxes.on("click",function(obj){
			var checkedboxes = $("input[name='selectUsers1']:checked");
			checkAll.prop('checked', checkedboxes.length == checkboxes.length);
   		})
   		
    })
     
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
													<c:if test="${users ne null}">
														<tr>
															<td>全选</td>
															<td></td>
															<td><input type="checkbox" id="checkAll"/></td>
														</tr>
													</c:if>
													<c:forEach items="${users}" var="users" >
														<c:forEach items="${users.value}" var="item" varStatus="status">
															<c:if test="${currentUser.user.imAccount ne item.imAccount && item.userType < 3}">
																<!-- 过滤自己的账号和客服 -->
																<tr>
																	<td><c:if test="${status.count eq 1}">${users.key}</c:if></td>
																	<td>${item.name}</td>
																	<td><input type="checkbox" name="selectUsers1"
																		value="${item.imAccount},${item.name}" /></td>
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

								<div style="width: 400px;height: 50px;position: fixed;background-color: rgb(255, 255, 255);top: 260px;border-top: 1px solid #E7E7E7;">
									<div class="ibox-tools">
										<span class="input-group-btn" >
											<button onclick="beforeCheckSelect()"
											type="button" class="btn btn-success btn-sm"  style="margin-top: 7px;margin-left: 256px;">
											保<span class="wzkg"></span>存
											</button>											
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