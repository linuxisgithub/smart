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
<div class="mainRight-tab">
	<div class="mainRight-tab-conW">
		<div class="tab-pages">
			<table id="pagination_table" style="margin-top:10px;"></table>
		</div>
		<div class="grayline"></div>
		<div class="pages">
			<ul class="pagination" id="pagination"></ul>
			<div class="add-area">
				<button class="pagination_btn pagination_btn_right add_btn" id="add_btn">新增二级部门</button>
				<ul class="business-menu">
					<li onclick="openUrl(1);"><a href="javascript:void(0);">食阁</a></li>
					<li onclick="openUrl(2);"><a href="javascript:void(0);">本公司部门</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var url = "${ctx}/sysDepartment/data/3/list.htm";
	initPagination($("#pagination"), {
		url: url,
		titles:[
			{label: "eid", visible:false},
			{label: "部门名称", width: "18%"},
            {label: "部门编号", width: "18%"},
			{label: "归属部门", width: "18%", order: "asc", orderName: "pname"},
			{label: "正职称谓", width: "18%"},
			{label: "副职称谓", width: "18%"},
			{label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']}
		],
		columns: [
			{field: "eid", visible:false},
			{field: "name"},
            {field: "code"},
			{field: "pname"},
			{field: "mainJob"},
			{field: "lessJob"},
			{fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toDetail}
		]
	});
	function toDetail(page) {
		parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=3&eid=" + page.item.eid + "&dtype=" + page.item.dtype);
	}
});
function openUrl(type){
    if(type == 2){
        parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=3&dtype=1");
    }else{
        parent.$.prompt.layerUrl("${ctx}/sysDepartment/load.htm?type=3&dtype=2");
    }
}
</script>
</body>
</html>