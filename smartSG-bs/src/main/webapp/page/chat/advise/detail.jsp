<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>我的建议详情</title>
<meta name="keywords" content=" ">
<meta name="description" content=" ">
</head>

<body>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<table class="table table-striped table-bordered" style="margin-top: 20px;width: 100%;">
							<thead>
								<tr>
									<td><span style="color:#000;margin-right: 10px;">用户名：</span>${data.userName}</td>
									<td><span style="color:#000;margin-right: 10px;">创建时间：</span>${data.createTime}</td>
								</tr>
								<tr>
									<td><span style="color:#000;margin-right: 10px;">标&nbsp;&nbsp;&nbsp;题：</span>${data.name}</td>
									<td><span style="color:#000;margin-right: 10px;">处理状态：</span><c:if test="${data.status == 0}">未处理</c:if> 
										<c:if test="${data.status == 1}">已处理</c:if></td>
								</tr>

								<tr>
									<td colspan="2"><span style="color:#000;margin-right: 10px;">内&nbsp;&nbsp;&nbsp;容：</span>${data.remark}</td>
								</tr>
								
								<%-- <tr>
									<td>回复人</td>
									<td>${data.replyUserName}</td>
									<td>回复时间</td>
									<td>${data.replyTime}</td>
								</tr>
								<tr>
									<td>回复内容</td>
									<td colspan="3">${data.replyContent}</td>
								</tr> --%>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>