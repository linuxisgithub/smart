<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<title>表格样式</title>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW" style="height: 635px;">
	    <form  action="" method="post" id="queryForm" onsubmit="return false;">
		    <div style="display: none;" alt="隐藏表单域">
				<input type="hidden" id="sgId" value="${sgId}">
			</div>
			<div class="yj-add-div1">
				<div class="yj-add-divL" style="width: 37%;">
					<div class="div-label" style="min-width: 55px;margin-left: 75px;">报表类型：</div>
					<div class="div-input" style="width: 55%;">
						<select name="s_report" id="s_report">
						</select>
					</div>
				</div>
				<div class="yj-add-divL" style="padding-left: 80px;width: 54%;">
					<div class="div-label" style="min-width: 55px;">日<span class="wzkg"></span>期：</div>
					<div class="div-input">
						<input type="text" name="" value="" class="Wdate" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})">至
						<input type="text" name="" value="" style="float: none" class="Wdate" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})">
					</div>
					<button class="search_btn" style="margin: 0px 0px 0px 0px;" id="searchBtn">检<span class="wzkg"></span>索</button>
				</div>
				<div class="clear"></div>
		    </div>
		</form>
		<div class="tab-pages">
			<table id="pagination_table"></table>
		</div>
		<div class="grayline"></div>
		<div class="pages">
			<ul class="pagination" id="pagination"></ul>
		</div>
	</div>
</div>
<div style="display: none;" alt="隐藏表单域">
	<input type="hidden" id="btype" value="45">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript" src="${ctx}/tools/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/tools/select2/js/select2.min.js"></script>	
<script type="text/javascript" src="${ctx }/js/main.js"></script>
<script type="text/javascript">
$(function() {
	var $s_report = $("#s_report");
	var reports = initReport($s_report, {kind: 2,flag: true});
	$s_report.change(function(){
		var rid = $(this).val();
		var name = reports[rid].name;
		alert(name);
	})


	$("#searchBtn").click(function() {
		alert("待开发");
	});
});


</script>
</body>
</html>