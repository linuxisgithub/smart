<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表格样式</title>
<style type="text/css">
</style>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW">
		<div class="tab-pages">
			<table id="pagination_table" style="margin-top:10px;">
				<thead>
					<tr>
						<th>特性名称(中文)</th>
						<th>特性名称(英文)</th>
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
				<c:forEach items="${models}" var="df">
					<tr>
						<td>${df.name}</td>
						<td>${df.enName}</td>
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
			</table>
		</div>
		<div class="grayline"></div>
		<div class="pages">
			<ul class="pagination" id="pagination"></ul>
			<button class="pagination_btn return_btn"  id="return_btn">返<span class='wzkg'></span>回</button>
			<button class="pagination_btn add_btn" id="add_btn">新增特性</button>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
	<input type="hidden" id="bid" value="${bid}">
	<input type="hidden" id="type" value="2">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
	var bid = $("#bid").val();
$(function() {

	$("#add_btn").click(function() {
		var row = $("#tbody").find("tr").length;
		if(row == 7){
			parent.$.prompt.message("菜品最多可有七条特性！", $.prompt.msg);
		}else{
			parent.$.prompt.layerUrl("${ctx}/dishFeatures/load.htm?type=2&bid="+bid);
		}
	});
	$("#return_btn").click(function () {
		window.history.back();
	})
});
	function toDetail(eid){
		parent.$.prompt.layerUrl("${ctx}/dishFeatures/load.htm?type=2&bid="+bid+"&eid="+eid);
	}

	function toPreview(eid,name){
		parent.$.prompt.layerUrl("${ctx}/dishFeaturesOption/preview.htm?eid="+eid+"&name="+name,600,400);
	}


function toDelete(eid){
	layer.confirm("确认删除该菜品吗?", { title: "删除确认",btn: ['确认','取消'] }, function (index) {
		var param = {eid: eid};
		var path = $("#contextPath").val();
		jsonAjax.post(path + "/dishFeatures/remove.htm", param, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("删除成功！", $.prompt.ok);
			} else {
				parent.$.prompt.message("删除失败：" + response.msg, $.prompt.error);
			}
			window.location.href = window.location.href;
			//$("#content_iframe").attr("src",path+"/dishFeatures/data/list.htm?bid="+bid);
		});
	}, function(index) {
		layer.close(index);
	});
}
</script>
</body>
</html>