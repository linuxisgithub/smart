<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>我的建议列表</title>
<meta name="keywords" content=" ">
<meta name="description" content=" ">
</head>
<body>
	<div class="row">
		<div class="row">
			<!--自定义响应式开始-->
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="queryForm"  method="post"
							action="${ctx}/Advise/list.htm"/>
							<input type="hidden" name="type" id="type" value=120>
							<div class="row1">
								<div class="col-sm-1 m-b-xs">
									<div data-toggle="buttons" class="btn-group">
										<label class="btn btn-sm btn-white"> <input
											type="radio" id="option1" name="options">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题
										</label>
									</div>
								</div>
								<div class="col-sm-5 m-b-xs">
									<input type="text" placeholder="请输入标题关键词"
										class="input-sm form-control" name="name"
										value="${query.name}">
								</div>
								<div class="col-sm-1">
									<div data-toggle="buttons" class="btn-group">
										<label class="btn btn-sm btn-white"> <input
											type="radio" id="option1" name="options">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期
										</label>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="input-group">
										<input class="Wdate input-sm form-control"
											style="height: 30px;" name="date" value="${query.date}"
											onFocus="WdatePicker({lang:'zh-cn'})" /> <span
											class="input-group-btn">
											<button type="submit" class="btn btn-sm btn-primary"
												>搜索</button>
										</span>
										<span class="input-group-btn">
											<button	onclick="$.openIframe({'content':'${ctx}/Advise/toCreate.htm?type=120','title':'我的建议添加',area:['700px','500px']})"
													type="button" class="btn btn-sm btn-primary"
													style="width: 60px; margin-left: 5px;">
													<i class="fa fa-plus"></i>&nbsp;添加
												</button>
										</span>
									</div>
								</div>
							
							</div>
						</form>

						<div>
							<section>
								<div class="tabs tabs-style-topline">
									<div class="content-wrap-test">
										<section id="section-topline-1">
											<table class="table table-striped table-bordered">
												<!--公司公告列表-->
												<thead>
													<tr>
														<th>日期</th>
														<th>用户名</th>
														<th>标题</th>
														<!-- <th>回复内容</th> -->
														<!-- <th>回复时间</th> -->
														<!-- <th>处理人</th> -->
														<th>处理状态</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${page.result}" var="item" varStatus="stat">
														<tr id="${ item.id }">
															<td>${item.createTime}</td>
															<td>${item.userName}</td>
															<td>${item.name}</td>
															<%-- <td>${item.replyContent}</td> --%>
															<%-- <td>${item.replyTime}</td> --%>
															<%-- <td>${item.replyUserName}</td> --%>
															<td>
																<c:if test="${item.status == 0 || item.status == '' || item.status == null}">
																	<span style="color: #FF0000;">未处理</span>
																</c:if> 
																<c:if test="${item.status == 1}">
																	<span style="color: #008000;">已处理</span>
																</c:if>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</section>

										<div style="font-size: 12px;">
											<div id="pagination"></div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/js/server/list/table_qh/cbpFWTabs.js"></script>
	<script src="${ctx}/js/server/list/table_qh/modernizr.custom.js"></script>
	<script >
		(function($, undefined) {
			'use strict';
			
			$(function(){
				
				$('tbody tr').click(function(event) {
					event.preventDefault();
					event.stopPropagation();
					$.openIframe({
						content:'${ctx}/Advise/show.htm?id=' + event.currentTarget.id,
						title:'我的建议详情',
						area:['680px','500px']
					})
				})
				
			})
			
		})(jQuery)	
	
	</script>
	
</body>
</html>