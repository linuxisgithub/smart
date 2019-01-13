<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css" />
<style>
* {
	margin: 0px;
	padding: 0px;
	box-sizing: border-box;
}

select, input {
	font-family: '微软雅黑', arial, sans-serif;
	font-size: 14px;
	color: #464646;
}

body {
	height: 100%;
	font-family: '微软雅黑', arial, sans-serif;
	font-size: 14px;
	color: #464646;
}

.search {
	width: 50%;
	min-width: 680px;
	height: 36px;
	margin: auto;
	margin-top: 20px;
}

.business {
	width: 31%;
	margin-right: 2%;
	height: 36px;
	/* border: 1px solid #aaa; */
	float: left;
}

.business .label {
	width: 40%;
	background-color: #cae6fb;
	display: inline-block;
	height: 34px;
	border: 1px solid #aaa;
	line-height: 34px;
	padding: 0 15px;
	text-align: center;
}

.business select {
	width: 60%;
	height: 34px;
	border: 0px;
	line-height: 34px;
	/* padding-left: 6px; */
	float: right
}

.search-box {
	width: 33.3%;
	float: left;
	height: 36px;
	border: 1px solid #aaa;
}

.search-box .input-key {
	width: 65%;
	height: 34px;
	border: 0px;
	line-height: 36px;
	padding: 0 6px;
	float: left;
}

.search-box .s-btn {
	-webkit-appearance: none;
	background-color: #cae6fb;
	width: 35%;
	height: 34px;
	border: 0;
	font-size: 14px;
	color: #464646;
	letter-spacing: 6px;
	cursor: pointer;
	outline: none;
	padding-top: 4px\9;
	vertical-align: top;
	border-left: 1px solid #aaa;
	float: right;
}
.select2-container {
	position: relative;
	top: -2px;
	left: -4px;
}
.select2-container--default .select2-selection--single {
	border-radius:0px !important;
}
</style>
<title>表格样式</title>
</head>
<body>
<div class="mainRight-tab-conW" style="width: 100%;height:100%; padding: 5px 0px 0px 0px;overflow-y: hidden;">
	<div class="search">
		<form action="${ctx}/super/${type}.htm" method="post" id="mainForm" onsubmit="return false;">
			<div class="business">
				<label for="" class="label">主业务</label> 
				<select name="mainBus" id="mainBus" style="width: 120px;">
					<option value="0">请选择</option>
				</select>
			</div>
			<div class="business" id="subBusDiv">
				<label for="" class="label">子业务</label> 
				<select name="subBus" id="subBus"  style="width: 120px;">
					<option value="0">请选择</option>
				</select>
			</div>
			<div class="search-box">
				<input type="text" class="input-key" id="input-key" > 
				<input type="text" id="input-dateKey" class="input-key" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" style="display:none;">
				<input type="text" id="input-yearKey" class="input-key" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy'})" style="display:none;">
				<input type="text" id="input-year_monthKey" class="input-key" onfocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM'})" style="display:none;">
				<select id="select-key" style="width:145px;height:34px;display:none;">
					<option value="1" <c:if test="${model.stage eq 1}">selected="selected"</c:if>>初期沟通</option>
					<option value="2" <c:if test="${model.stage eq 2}">selected="selected"</c:if>>立项跟踪</option>
					<option value="3" <c:if test="${model.stage eq 3}">selected="selected"</c:if>>呈报方案</option>
					<option value="4" <c:if test="${model.stage eq 4}">selected="selected"</c:if>>商务谈判</option>
					<option value="5" <c:if test="${model.stage eq 5}">selected="selected"</c:if>>成交</option>
					<option value="6" <c:if test="${model.stage eq 6}">selected="selected"</c:if>>失效</option>
				</select>
				<input type="submit" class="s-btn" value="搜索" onsubmit="javascript:return false;" id="search_btn">
			</div>
		</form>
	</div>
	<div>
		<iframe id="super_iframe" name="super_iframe" src="" scrolling="no"
			style="width: 100%; height: 500px; border: 0px;"></iframe>
	</div>
</div>
<div style="display: none;" title="隐藏表单域">
	<input type="hidden" value="${type}" id="superType">
</div>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function() {
	var height = $(".mainRight-tab-conW").height();
	$("#super_iframe").css("height", (height - 42) + "px");
	var firstMenus = {};
	var secondMenus = {};
	var subMenus = {};
	var s_param ="";
	var companyLevel = ${USER_IN_SESSION.company.level};
	var isVip = ${USER_IN_SESSION.company.isVip};
	$("#subBus").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#subBus").change(function() {
		
		var subBusVal = $(this).val();
		  //业绩报告：10602   财务113
		if(subBusVal!=undefined&&subBusVal!=""&&(subBusVal=='10602'|| subBusVal=='11301' ||subBusVal=='11305'||subBusVal=='11302')){
			$("#input-dateKey").show();
			$("#input-key").hide(); 
			$("#input-yearKey").hide();
			$("#select-key").hide();
			$("#input-year_monthKey").hide();
			//采购订单11102
		}else if(subBusVal!=undefined&&subBusVal!=""&&subBusVal=='11102'){
			$("#input-dateKey").show();
			$("#input-key").hide(); 
			$("#input-yearKey").hide();
			$("#select-key").hide();
			$("#input-year_monthKey").hide();
			//采购退货11103
		}else if(subBusVal!=undefined&&subBusVal!=""&&subBusVal=='11103'){
			$("#input-dateKey").show();
			$("#input-key").hide(); 
			$("#input-yearKey").hide();
			$("#select-key").hide();
			$("#input-year_monthKey").hide();
			// 10606销售退货 
		}else if(subBusVal!=undefined&&subBusVal!=""&&subBusVal=='10606'){
			$("#input-dateKey").show();
			$("#input-key").hide(); 
			$("#input-yearKey").hide();
			$("#select-key").hide();
			$("#input-year_monthKey").hide();
			 // 业绩管理10603
		}else if(subBusVal!=undefined&&subBusVal!=""&&(subBusVal=='10603'||subBusVal=='11304')){
			$("#input-dateKey").hide();
			$("#input-key").hide();
			$("#input-yearKey").show();
			$("#select-key").hide();
			$("#input-year_monthKey").hide();
		//客户机会10802 
		}else if(subBusVal!=undefined&&subBusVal!=""&&subBusVal=='10802'){
			$("#input-dateKey").hide();
			$("#input-key").hide();
			$("#input-yearKey").hide();
			$("#select-key").show();
			$("#input-year_monthKey").hide();
		}else if(subBusVal!=undefined&&subBusVal!=""&&(subBusVal=='1022'||subBusVal=='11303')){
			//薪资管理1022
			$("#input-dateKey").hide();
			$("#input-key").hide();
			$("#input-yearKey").hide();
			$("#select-key").hide();
			$("#input-year_monthKey").show();
		}else{
			$("#input-dateKey").hide();
			$("#input-key").show();
			$("#input-yearKey").hide();
			$("#select-key").hide();
			$("#input-year_monthKey").hide();
		}
	});
	
	var superType=$("#superType").val();
	$("#search_btn").click(function() {
		var mainEid = $("#mainBus").val();
		var eid = $("#subBus").val();
		if(mainEid=='0'){
			$.prompt.message("请选择主业务！", $.prompt.error);
		}
		if(mainEid!='0'&&eid=='0'){
			$.prompt.message("请选择子业务！", $.prompt.error);
		}
		var menu = subMenus[eid];
		var yearId = $("#super_iframe").contents().find("#year").length;
		var monthId = $("#super_iframe").contents().find("#month").length;
		if(menu != undefined) {
			var menuUrl = menu.menuUrl
			if(eid=='11202'){
				var yearId = $("#super_iframe").contents().find("#year").length;
				var monthId = $("#super_iframe").contents().find("#month").length;
				if((yearId==1)||(yearId==0&&monthId==0)){
					menuUrl = "/marketgoods/wareHousedMonthList.htm";
				}else{
					menuUrl = "/marketgoods/wareHousedYearList.htm";
				}
			}
			if(eid=='11104'){
				var yearId = $("#super_iframe").contents().find("#year").length;
				var monthId = $("#super_iframe").contents().find("#month").length;
				if((yearId==1)||(yearId==0&&monthId==0)){
					menuUrl = "/procurementorder/summaryMonthList.htm";
				}else{
					menuUrl = "/procurementorder/summaryYearList.htm";
				}
			}
			if(eid=='1022'){
				var yearId = $("#super_iframe").contents().find("#year").length;
				var monthId = $("#super_iframe").contents().find("#month").length;
				if((yearId==1)||(yearId==0&&monthId==0)){
					menuUrl = "/salary/monthList.htm";
				}else{
					menuUrl = "/salary/yearList.htm";
				}
			}
			var url;
			if(menuUrl.indexOf("?")!=-1){
				url = "${ctx}" + menuUrl+"&isSearch=1&superType="+superType;
			}else{
				url = "${ctx}" + menuUrl+"?isSearch=1&superType="+superType;
			}
			
			 s_param = "";
			if(eid=='10602' || eid=='10606'|| eid=="11102" || eid=="11103" || eid=='11301' ||eid=='11305'||eid=='11302'){
				s_param = $("#input-dateKey").val();
			}else if(eid=='10603'||eid=='11304'){
				s_param = $("#input-yearKey").val();
			}else if(eid=='11303'){
				s_param = $("#input-year_monthKey").val();
			}else if(eid=='10802'){
				s_param = $("#select-key").val();
			}else if(eid=='1022'){
				if($("#input-year_monthKey").val()){
					s_param = $("#input-year_monthKey").val();
				}else if($("#input-yearKey").val()){
					s_param = $("#input-yearKey").val();
				}
			}else{
				s_param = $("#input-key").val();
			}
			if(s_param != undefined && s_param != "") {
				s_param = encodeURI(encodeURI(s_param));
				url = appendParam(url, "s_param", s_param);
			}
			$("#super_iframe").attr("src", url);
		}
	});
	$("#mainBus").change(function() {
		var mainEid = $(this).val();
		$("#subBus").html("");
		$("#subBus").append("<option value='0'>请选择</option>");
		var childs = secondMenus[mainEid];
		if(childs != undefined) {
			for (var i = 0; i < childs.length; i++) {
				var child = childs[i];
				if((companyLevel==2||companyLevel==3)&&isVip==0&&child.eid==11202){
					continue;
				}else{
					$("#subBus").append("<option value='"+child.eid+"'>"+ child.name + "</option>");
				}
			}
		}
		$("#subBus").select2({
			minimumResultsForSearch: search_detault,
		});
	});
	$.ajax({
		'url' : "${ctx}/super/busMenu/main.htm",
		'type' : "post",
		'async' : true,
		'dataType' : 'json',
		'success' : function(response) {
			if (response.status == 200) {
				var _firstMenus = response.data;
				$("#mainBus").html("");
				$("#mainBus").append("<option value='0'>请选择</option>");
				for (var i = 0; i < _firstMenus.length; i++) {
					var _menu = _firstMenus[i];
					firstMenus[_firstMenus[i].eid] = _menu;
					var mainEid = _menu.eid;
					var mainName = _menu.name;
					if((companyLevel==2&&mainEid==104)||mainEid==109||(isVip==0&&mainEid==111)
							||(companyLevel==1&&mainEid==112)||(companyLevel==3&&isVip==0&&mainEid==104)
							||(isVip==0&&mainEid==113)){
						continue;
					}else{
						/* if(companyLevel==3&&mainEid==101){
							mainName='通知•文件';
						}else if(companyLevel==2&&mainEid==101){
							mainName='部门通知';
						} */
						$("#mainBus").append("<option value='"+mainEid+"'>"+ mainName + "</option>");
					}
				}
				$("#mainBus").select2({
					minimumResultsForSearch: search_detault,
				});

			} else {
				$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
			}
		}
	});
	$.ajax({
		'url' : "${ctx}/super/busMenu/sub.htm",
		'type' : "post",
		'async' : true,
		'dataType' : 'json',
		'data' : {"superType":superType},
		'success' : function(response) {
			if (response.status == 200) {
				$.each(response.data, function(index, item) {
					subMenus[item.eid] = item;
					var childs = secondMenus[item.pid];
					if (childs == undefined) {
						childs = [];
						childs.push(item);
						secondMenus[item.pid] = childs;
					} else {
						childs.push(item);
					}
				});
			} else {
				$.prompt.message("获取菜单失败：" + response.msg, $.prompt.msg);
			}
		}
	});
	
	/* $("#subBusDiv").click(function(){
		var mainValue = $("#mainBus").val();
		if(mainValue=='0'){
			$.prompt.message("请选择主业务！", $.prompt.error);
		}
	}); */
	
	
	
});
/**
 * 给URL追加参数
 */
function appendParam(url, param_name, param_value) {
	var _url = url;
	var index = _url.indexOf("?");
	if(index != -1) {
		_url = _url + "&" + param_name + "=" + param_value;
	} else {
		_url = _url + "?" + param_name + "=" + param_value;
	}
	return _url;
}
</script>
</body>
</html>