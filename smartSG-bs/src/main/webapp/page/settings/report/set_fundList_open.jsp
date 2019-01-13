<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>


</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con text_right">
		<form action="" method="post" id="mainForm">
			<!-- 此密码框是为了解决浏览器自动为下面的密码框带出数据 -->
			<input type="password" name="aotoCode" id="aotoCode" style="height: 0px;border: 0px;padding: 0px;margin: 0px;"/>
			<div class="yj-add-top" style="border-bottom:0px;">
				<div class="yj-add-title">
					资金附件
				</div>
				<div class="yj-add-title_L" style="margin-top: 7px;">
					<div ><strong>报告名称：</strong><span id="typeName">${title}</span></div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="business-table">
			<table>
				<thead>
					<tr>
						<th>名<span class="wzkg"></span>称</th>
						<th>规<span class="wzkg"></span>格</th>
						<th>单<span class="wzkg"></span>位</th>
						<th>数<span class="wzkg"></span>量</th>
						<th>单<span class="wzkg"></span>价</th>
						<th>金<span class="wzkg"></span>额</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				
			</table>
			</div>
		</form>
	</div>
</div>
</body>
</html>