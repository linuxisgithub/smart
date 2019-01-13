<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
<style type="text/css">
	input{
		border-radius:4px;
		border: 1px solid #aaa;
		height: 34px;
	}
	.group{
		height: 30px;
		border: 1px solid #797979;
		margin-top: 13px;
		margin-bottom: 7px;
		margin-left: 13px;
		text-align: center;
		line-height: 30px;
		border-radius: 5px;
		float: left;
		padding: 0px 7px;
	}
	.delete{
		width:20px;
		height:20px;
		border-radius:60%;
		position:relative;
		top:-10px;
		right:-15px;
	}
	table thead tr th{
		height: 30px!important;
	}
	table tbody tr td{
		height: 30px!important;
	}
	.myBtn{
		background-color: white;
		border: 1px solid #A9A9A9;
		height: 25px;
		border-radius:4px;
		background-color:#3da8f5;
		color: #fff;
	}
</style>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW">
		<div class="tab-pages">
			<div style="height: 13%;margin-top:10px;">
				<div style="background-color: #F2F2F2;height: 100%;">
					<div style="height: 100%;padding-left: 10px;padding-top:12px;font-size: 16px;">
						分组设置：<input type="text" id="groupName" placeholder="请输入组别名称">&nbsp;&nbsp;&nbsp;
						<button class="myBtn" id="addGroup_btn">&nbsp;添加分组&nbsp;</button>
					</div>

				</div>
			</div>
			<div style="min-height: 12%;overflow:auto;margin-top: -10px;border-radius: 15px;background-color: white;border: 1px solid #f2f2f2;" id="groups">
				<c:forEach items="${dgList}" var="dg">
					<div class="group" id="${dg.eid}">${dg.name}
						<a href="javascript:void(0)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="delGroup(${dg.eid})"/></a>
					</div>
				</c:forEach>
			</div>
			<div style="background-color: #F2F2F2;height: 9%;margin-top:10px;padding-left: 10px;padding-top: 18px;font-size: 16px;">
				<form action="" method="post" id="setMealForm" onsubmit="javascript:return false;">
				套餐参数设置：&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" name="eid" value="${setMeal.eid}">
					<input type="hidden" name="bid" id="bid" value="${bid}">
				快餐类型：<select id="s_type" name="type" style="width: 120px;" class="setMealInput">
							<option value="1" ${setMeal.type == 1 ? 'selected':''}>快餐类型</option>
							<option value="2" ${setMeal.type == 2 ? 'selected':''}>称重类型</option>
						 </select>&nbsp;&nbsp;&nbsp;&nbsp;
				单位数量：<input type="text" name="unitNumber" value="${setMeal.unitNumber}" class="setMealInput" style="width: 90px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				计价单位：<input type="text" name="unit" value="${setMeal.unit}" class="setMealInput" style="width: 90px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				平均准备时长：<input type="text" name="preparationTime" class="setMealInput" value="${setMeal.preparationTime}" style="width: 90px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button  class="myBtn" style="width: 60px;" id="setMeal_btn">&nbsp;保&nbsp;</span>存&nbsp;</button>
				</form>
			</div>
			<div style="border: 0px solid #858585;margin-top: 10px;max-height: 66%;">
				<table style="width: 99.9%;">
					<thead>
						<tr>
							<th>特性名称</th>
							<th>选项类型</th>
							<th>选项个数</th>
							<th>默认选项</th>
							<th>对应金额</th>
							<th>是否选用</th>
							<th class="th-bgcolor">编辑</th>
							<th class="th-bgcolor">预览</th>
							<th class="th-bgcolor">删除</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${dfList}" var="df">
							<tr>
								<td>${df.name}</td>
								<td>${df.optionType ==1 ?'单选':'多选'}</td>
								<td>${df.optionNumber}</td>
								<td>${df.optionStr}</td>
								<td>${df.money}</td>
								<td>${df.isSelected ==1 ?'选用':'不选用'}</td>
								<td><a href="javascript:void(0)"><img src='${ctx}/images/icons/gray_edit.png' onclick="toDetail(${df.eid})"/></a></td>
								<td><a href="javascript:void(0)"><img src='${ctx}/images/icons/see.png' onclick="toPreview(${df.eid},'${df.name}')"/></a></td>
								<td><a href="javascript:void(0)"><img src='${ctx}/images/icons/clear_icon.png' onclick="toDelete(${df.eid})"/></a></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="8" align="center">
								<button style="margin:5px 0px;" class="myBtn" id="addFeatures_btn">&nbsp;添加自定义特性&nbsp;</button>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div class="grayline"></div>
		<div class="pages">
			<ul class="pagination" id="pagination"></ul>
			<button class="pagination_btn return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
			<%--<button class="pagination_btn submit-btn" id="save_btn">保存设置</button>--%>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/tw/dishManagement/setting.js"></script>
</body>
</html>