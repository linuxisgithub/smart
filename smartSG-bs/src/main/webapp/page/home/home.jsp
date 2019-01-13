<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/workbechCSS.css">
<link rel="stylesheet" type="text/css" href="${ctx}/tools/calendar/css/calendar_s.css">
<script type="text/javascript" src="${ctx}/tools/echarts/3.6.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/tools/calendar/js/calendar.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<title>主面板</title>
</head>
<body style="background-color: #fff">
<div class="mainRight-tab-conW">				
	<div class="card-widgets">
		<!-- 考勤签到start -->
		<div class="task-card1">
			<div class="task-title">
				<span class="icon kqqd"></span>考勤签到
				<font class="today-date" id="today-date">星期三  2017-109</font>
			</div>
			<div class="task-content" id="needCheck" style="display: none;">
			</div>
			<div class="task-content" id="noNeedCheck" style="display: none;">
				<div class="chockin">
					<div style="line-height:28px;">
					<span><img src="${ctx}/images/workpench/chockin.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>下班打卡
					</div>
					<div style="text-align: center;">
						<span class="punch-card" style="background-color: #b7b7b7">上班打卡<br/>无需打卡</span>
					</div>
				</div>
				<div class="chockout">
					<div style="line-height:28px;">
					<span><img src="${ctx}/images/workpench/chockin.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>下班打卡
					</div>
					<div style="text-align: center;">
						<span class="punch-card" style="background-color: #b7b7b7">下班打卡<br/>无需打卡</span>
					</div>
				</div>
				
			</div>
		</div>
		<!-- 考勤签到end-->
		<!-- 待办事宜start -->
		<div class="task-card2">
			<div class="task-title">
				<span class="icon dbsy"></span>待办事宜
				<a href="javascript:;" class="more" onclick="moreFun(102)">更多</a>
			</div>
			<div class="task-content">
				<div class="affairs-title" >
					<ul>
						<li ><span class="yellow"></span>待我审批<span id="approNum">0</span></li>
						<li ><span class="blue"></span>抄送给我 <span id="ccNum">0</span></li>
						<li ><span class="green"></span>工作提醒 <span id="workNum">0</span></li>
					</ul>
					<div class="clear"></div>
				</div>
				<div style="margin-top: 12px;">
					<div class="affairs-charts">
						<span id="total_span"></span> 
						<div id="echartDiv"></div>
					</div>
					<div class="affairs-texts">
						<ul id="approval_li">
						</ul>
						<ul style="margin-top: 8px;" id="cc_li">
							<!-- <li onclick="showCc('+item.eid+','+item.type+')"  style="cursor: pointer;"><span class="blue"></span>1</li>
							<li onclick="showCc('+item.eid+','+item.type+')"  style="cursor: pointer;"><span class="blue"></span>2</li>
							<li onclick="showCc('+item.eid+','+item.type+')"  style="cursor: pointer;"><span class="blue"></span>3</li> -->
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				
			</div>
		</div>
		<!-- 待办事宜end-->
		<!-- 公告通知start-->
		<div class="task-card1">
			<div class="task-title">
				<span class="icon ggtz"></span>
				<c:choose>
					<c:when test="${USER_IN_SESSION.company.isSpec eq 1}">
						城南公告
					</c:when>
					<c:otherwise>
						公告通知
					</c:otherwise>
				</c:choose>
				<a href="javascript:;" class="more" onclick="moreFun(101)">更多</a>
			</div>
			<div class="task-content">
				<ul class="notices">
				</ul>
			</div>
		</div>
		<!-- 公告通知end-->
		<div class="clear"></div>
	</div>
	<c:choose>
		<c:when test="${USER_IN_SESSION.company.level eq 1}">
			<div class="card-widgets" style="margin-top: 12px;">
		<!-- 日常安排start-->
			<div class="task-card3">
				<div style="float: left;width: 50%;">
					<div class="task-title">
						<span class="icon rcap"></span>日常安排
						<span style="margin-left:12px;color: #3da8f5;font-size: 16px;" id="icon_time"></span>					
					</div>
					<div class="task-content" onclick="showDaily();" style="cursor: pointer;" title="日常安排明细">
						<input type="hidden" id="dailyInput"/>
						<ul class="plan-list" style="margin-top: -22px;">
							<!-- <li>
								<i class="plan-icon"></i>
								<span class="plan-time">08:00</span>
								<span class="plan-text">参加早会</span>
							</li> -->
						</ul>
					</div>
				</div>
				<div style="float: left;width: 50%;height:270px;position: relative;">
					<div id="demo" style="">
						<div id="calendar"></div>
						<div class="plan-add" onclick="toDaily()" >
							<img src="images/workpench/add.png">
						</div>
					</div>
					
				</div>
			</div>
			<!-- 日常安排end-->
		<!-- 项目任务start-->
			<div class="task-card4">
				<div class="task-title">
					<span class="icon xmrw"></span>项目任务
					<a href="javascript:;" class="more" onclick="moreFun(105)">更多</a>
				</div>
				<div class="task-content">
					<table class="mytable">
						<thead>
							<tr>
								<th>项目内容</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>完成情况</th>
							</tr>
						</thead>
						<tbody id="projectTbody">
							<!-- <tr>
								<td>新项目分工内容</td>
								<td>2017-12-06</td>
								<td>2017-12-06</td>
								<td>已完成</td>

							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
			<!-- 项目任务end-->
	</div>
		</c:when>
		<c:otherwise>
			<div class="card-widgets" style="margin-top: 12px;">
			<!-- 销售金额start -->
			<div class="task-card5" style="width: calc(27% - 11px);">
				<div class="stats-title" style="margin-left: 108px;">本月已完成：</div>
				<div class="task-content">
					<div class="stats-box">
						<div class="stats-icons">
							<img src="images/workpench/sales.png" width="50px" height="50px">
						</div>
						<div class="stats-number" style="color: #f67d20;" id="target_01">
						%
						</div>
						<div class="clear"></div>
					</div>
					<div class="stats-items">
						<ul>
							<li id="target_02">本月销售目标：  万元 </li>
							<li id="target_03">本月完成金额：0 万元</li>
							<li id="target_04">本月预计完成率：  % </li>
						</ul>
					</div>					
				</div>
			</div>
			<!-- 销售金额end -->
			<!-- 订单总数start -->
			<div class="task-card5" style="width: calc(28% - 11px);">
				<div class="stats-title" style="margin-left: 105px;">今日合同订单总额：</div>
				<div class="task-content">
					<div class="stats-box">
						<div class="stats-icons">
							<img src="images/workpench/order.png" width="50px" height="50px">
						</div>
						<div class="stats-number" style="color: #3da8f5;" id="order_01">
							 0
						</div>
						<div class="clear"></div>
					</div>
					<div class="stats-items">
						<ul>
							<li id="order_02"> 今日合同订单总额：0.0万元</li>
							<li id="order_03"> 日均完成目标：0.0万元</li>
							<li id="order_04"> 今日完成率：  % </li>
						</ul>
					</div>					
				</div>
			</div>
			<!-- 订单总数end -->
			<!-- 客户数start -->
			<div class="task-card5" style="width: calc(23% - 11px);">
				<div class="stats-title" style="margin-left: 106px;">今日成交客户数：</div>
				<div class="task-content">
					<div class="stats-box">
						<div class="stats-icons">
							<img src="images/workpench/customer.png" width="50px" height="50px">
						</div>
						<div class="stats-number" style="color: #62ca0d;" id="cus_01">
							0
						</div>
						<div class="clear"></div>
					</div>
					<div class="stats-items">
						<ul>
							<li id="cus_02">今日新增客户数：0</li>
							<li id="cus_03">当前客户公海数：0</li>
							<li id="cus_04">客户总数：  0</li>
						</ul>
					</div>					
				</div>
			</div>
			<!-- 客户数end -->
			<!-- 机会数start -->
			<div class="task-card5" style="width: calc(22% - 11px);">
				<div class="stats-title" style="margin-left: 102px;">今日新增机会数：</div>
				<div class="task-content">
					<div class="stats-box">
						<div class="stats-icons">
							<img src="images/workpench/chance.png" width="50px" height="50px">
						</div>
						<div class="stats-number" style="color: #fe6666;" id="chance_01">
						0
						</div>
						<div class="clear"></div>
					</div>
					<div class="stats-items">
						<ul>
							<li id="chance_02">当前机会公海数：0</li>
							<li id="chance_03">机会总数：  0</li>
						</ul>
					</div>					
				</div>
			</div>
			<!-- 机会数end -->
		</div>
		</c:otherwise>
	</c:choose>
</div>


<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript">
var b1 = 0;
var b2 = 0;
var b3 = 0;
var calendarDate;
$(function() {
	var level = "${USER_IN_SESSION.company.level}";
	init.common();
	 if(level == 1) {
		init.oa();
	} else if(level == 2) {
		init.oa_sale();
	} else if(level == 3) {
		init.oa_cmr();
	} 
	
	var calendar = $('#calendar').calendar({
		height : 200,
		onSelected : function(view, date, data) {
			var month = date.getMonth();
			var monthStr = (date.getMonth()+1) + "";
			if(month < 9) {
				monthStr = "0" + monthStr;
			}
			var dateNum = date.getDate();
			var dateStr = dateNum + "";
			if(dateNum < 10) {
				dateStr = "0" + dateStr;
			}
			calendarDate = date.getFullYear()+'-'+monthStr+'-'+dateStr;
			init.data.daily(calendarDate);
		},
		onMouseenter: function(view, date, data) {
		}
	});

});
var workNumSetTimeoutNum = 0;
var init = {
	common: function() {
		init.data.workNum();
		init.data.workTime();
		init.data.myApproval();
		init.data.myCc();
		init.data.notice();
		init.data.echart();
	},
	oa: function() {
		init.data.project();
		init.data.daily();
	},
	oa_sale: function() {
		init.data.target();
		init.data.order();
		init.data.customer();
		init.data.chance();
	},
	oa_cmr: function() {
		init.data.target();
		init.data.order();
		init.data.customer();
		init.data.chance();
	},
	data: {
		// 获取工作提醒数量
		workNum: function() {
			setTimeout(function() {
				var workNum = parent.getCreateMsgNum();
				$("#workNum").text(workNum);
				b3 = 1;
				workNumSetTimeoutNum++;
				/* if(workNumSetTimeoutNum < 5) {
					init.data.workNum();
				} */
			}, 1000);
		},
		// 获取考勤信息
		workTime: function() {
			var url = "${ctx}/worktime/home/init.htm";
			configAjax.post(url, {}, function(response) {
				var data = response.data;
				appendWork(data);
			});
		},
		// 获取待我审批
		myApproval:function(){
			var url = "${ctx}/systemIndex/getMyApprvoal.htm";
			configAjax.get(url, {}, function(response) {
				var data = response.data;
				appendMyApproval(data);
			});
		},
		// 获取抄送给我的
		myCc:function(){
			var url = "${ctx}/systemIndex/getMyCc.htm";
			configAjax.get(url, {}, function(response) {
				var data = response.data;
				appendMyCc(data);
			});
		},
		//公司公告/部门通知
		notice:function(){
			var url = "${ctx}/systemIndex/getNotice.htm";
			configAjax.get(url,{}, function(response) {
				var data = response.data;
				appendNotice(data);
			});
		},
		//办公事宜统计饼图
		echart:function(){
			setTimeout(function(){
				if(b1 == 1 && b2 ==1 && b3 ==1){
					var num1= parseInt($("#approNum").text());
					var num2= parseInt($("#ccNum").text());
					var num3= parseInt($("#workNum").text());
					var total= num1 + num2 + num3; 
					appendEchart(total,num1,num2,num3);
				}else{
					init.data.echart();
				}
			},1000); 
		},
		//项目任务
		project:function(){
			var url = "${ctx}/systemIndex/getProject.htm";
			configAjax.get(url,{}, function(response) {
				var data = response.data;
				appendProject(data);
			});
		},
		//日常安排
		daily:function(date){
			var url = "${ctx}/systemIndex/getDaily.htm";
			configAjax.get(url,{"date":date}, function(response) {
				var data = response.data;
				appendDaily(data,data.date);
			});
		},
		//本月已完成
		target:function(){
			var url = "${ctx}/systemIndex/getTarget.htm";
			configAjax.get(url,{}, function(response) {
				var data = response.data;
				appendTarget(data);
			});
		},
		//今日合同订单总额
		order:function(){
			var url = "${ctx}/systemIndex/getOrder.htm";
			configAjax.get(url,{}, function(response) {
				var data = response.data;
				appendOrder(data);
			});
		},
		//今日成交客户数
		customer:function(){
			var url = "${ctx}/systemIndex/getCustomer.htm";
			configAjax.get(url,{}, function(response) {
				var data = response.data;
				appendCustomer(data);
			});
		},
		//今日转换机会数
		chance:function(){
			var url = "${ctx}/systemIndex/getChance.htm";
			configAjax.get(url,{}, function(response) {
				var data = response.data;
				appendChance(data);
			});
		}
	}
};

function appendChance(data){
	if(data != null){
		$("#chance_01").text(data.today_add_count);
		$("#chance_02").text("当前机会公海数: "+data.public_cout);
		$("#chance_03").text("机会总数 : "+data.total_count);
	}
}

function appendCustomer(data){
	if(data != null){
		$("#cus_01").text(data.today_deal_count);
		$("#cus_02").text("今日新增客户数: "+data.today_add_count);
		$("#cus_03").text("当前客户公海数 : "+data.public_count);
		$("#cus_04").text("客户总数 : "+data.total_count);
	}
}

function appendOrder(data){
	if(data != null){
		$("#order_01").text(data.today_money+" 万");
		$("#order_02").text("今日合同订单总额 : "+data.today_money+" 万元");
		$("#order_03").text("日均完成目标 : "+data.dayAverMoney+" 万元");
		$("#order_04").text("今日完成率 : "+data.estimate);
	}
}

function appendTarget(data){
	if(data != null){
		$("#target_01").text(data.proportion);
		$("#target_02").text("本月销售目标 : "+data.month_plan+" 万元");
		$("#target_03").text("本月完成金额 : "+data.finish+" 万元");
		$("#target_04").text("本月预计完成率 : "+data.montnEstimate);
	}
}

function showDaily(){
	var dates = $("#dailyInput").val();
	parent.$.prompt.layerUrl("${ctx}/systemIndex/toDaily.htm?date="+dates,1150,530);
}

function appendDaily(data,dates){
	$("#icon_time").text(dates);
	$("#dailyInput").val(data.showDate);
	$(".plan-list").html("");
	var model = data.jsonArray;
	if(model != ""){
		var html="";
		$.each(model,function(index,item){ 	
			var daily = item.daily;
			daily = daily.substring(0,10);
			html+='<li><i class="plan-icon"></i><span class="plan-time">'+item.time+'</span><span class="plan-text">'+daily+'</span></li>';
		});
		$(".plan-list").html(html);
	}else{
		$(".plan-list").html('<li><i class="plan-icon"></i><span class="plan-time"></span><span class="plan-text">无安排</span></li>');
	}
}

//跳转添加日常安排
function toDaily(){
	if(calendarDate == undefined)
		 calendarDate = "";
	parent.$.prompt.layerUrl("${ctx}/systemIndex/toDaily.htm?date="+calendarDate,1150,530);
}

function appendProject(data){
	var model = data.projectModel;
	var html="";
	var indexTotal;
	var items = [
		{value:1,name:"提前完成"},
		{value:2,name:"完成"},
		{value:3,name:"超期完成"},
		{value:4,name:"未完成"},
		{value:5,name:"项目终止"}
	];
	$.each(model,function(index,item){ 	
		if(item.remark != null){
			if(item.btype==24){
				html += '<tr><td>'+item.remark+'</td><td>'+item.startTime+'</td><td>'+item.endTime+'</td><td>'+item.status+'</td></tr> ';
			}else{
				html += '<tr><td>'+item.remark+'</td><td>'+item.startTime+'</td><td>'+item.endTime+'</td><td><select class="status_select" style="height:20px;width:100px;" name="status" data-eid="'+item.eid+'" data-btype="'+item.btype+'" id=status_'+index + '>';
				html +='<option value="-1">请选择</option>';
				$.each(items,function(index,_item){ 
					html += '<option value="'+_item.value+'"';
					if(_item.value == item.status) {
						html += " selected = 'selected'";
					}
					html += ">"+_item.name+"</option>";
				});
				html += '</select>'+'</td></tr>';
			}
		}
	});
	$("#projectTbody").html(html);
	$(".status_select").select2({
		minimumResultsForSearch: search_detault,
	});
	
	$(".status_select").change(function(){
		var eid = $(this).data("eid");
		var btype = $(this).data("btype");
		var status = $(this).val();
		var url;
		if(btype==25){
			url = "${ctx}/workTask/updateIndexStatus.htm";
		}else if(btype == 26){
			url = "${ctx}/project/updateIndexStatus.htm";
		}
		jsonAjax.post(url, {eid:eid,status:status}, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("修改成功！", $.prompt.msg);
			} else {
				parent.$.prompt.message("修改失败：" + response.msg, $.prompt.msg);
			}
		});
	});
}





//点击菜单
function moreFun(id){
	parent.$("#"+id).trigger("click");
}

function appendEchart(total,num1,num2,num3){
	var myChart = echarts.init(document.getElementById('echartDiv'));
	option3.series[0].data[0].value=num1;
	option3.series[0].data[1].value=num2;
	option3.series[0].data[2].value=num3;
	myChart.setOption(option3);
	var $span = $("#total_span");
	$span.text(total);
}

function appendMyApproval(data){
	var count = data.approvalCount;
	var model = data.approvalModel;
	$("#approNum").text(count);
	var html="";
	$.each(model,function(index,item){ 	
		html += '<li onclick="showApro('+item.eid+','+item.type+')" style="cursor: pointer;"><span class="yellow"></span>'+item.title+'</li>';
	});
	$("#approval_li").html(html);
	b1 = 1;
}

function appendMyCc(data){ 
	var count = data.ccCount;
	var model = data.ccModel;
	$("#ccNum").text(count);
	var html="";
	$.each(model,function(index,item){ 	
		html += '<li onclick="showCc('+item.eid+','+item.type+')"  style="cursor: pointer;"><span class="blue"></span>'+item.title+'</li>';
	});
	$("#cc_li").html(html);
	b2 = 1;
}

function appendNotice(data){ 
	var model = data.noticeModel;
	var html="";
	$.each(model,function(index,item){ 	
		html += '<li onclick="showNotice('+item.eid+','+item.type+')"  style="cursor: pointer;" ><span class="grey"></span>'+item.title+'<span class="notices-date">'+item.applyDate+'</span></li>';
	});
	$(".notices").html(html);
}

function showCc(eid,type){
	parent.$.prompt.layerUrl("${ctx}/report/loadCc.htm?eid=" +eid+"&type="+type);
}

function showNotice(eid,type){
	if(type == 2){
		parent.$.prompt.layerUrl("${ctx}/comNotice/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,530);
	}else{
		parent.$.prompt.layerUrl("${ctx}/deptNotice/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,530);
	}
}
// TODO
function showApro(eid,type){
	if(type == 2) {
		//公司公告
		parent.$.prompt.layerUrl("${ctx}/comNotice/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,530);
	} else if(type == 3) {
		//部门通知
		parent.$.prompt.layerUrl("${ctx}/deptNotice/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,530);
	}else if(type == 4){
		//文件中心
		parent.$.prompt.layerUrl("${ctx}/fileCenter/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,600);
	}else if(type == 69){
		parent.$.prompt.layerUrl("${ctx}/designatednotice/load.htm?isApproval=1&eid=" + eid,1000,530);
	}else if(type == 9) {
		//我的报告
		parent.$.prompt.layerUrl("${ctx}/report/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,616);
	} else if(type == 10) {
		//临时报告
		parent.$.prompt.layerUrl("${ctx}/tempReport/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,616);
	}else if(type == 20){
		parent.$.prompt.layerUrl("${ctx}/cusApply/load.htm?isApproval=1&eid=" + eid,1000,530);
	} else if(type == 11) {
		parent.$.prompt.layerUrl("${ctx}/dimission/load.htm?isApproval=1&eid=" + eid,1082,616);
	} else if(type == 12) {
		parent.$.prompt.layerUrl("${ctx}/entry/load.htm?isApproval=1&eid=" + eid,1082,616);
	} else if(type == 13) {
		parent.$.prompt.layerUrl("${ctx}/salarychange/load.htm?isApproval=1&eid=" + eid,1082,616);
	}else if(type == 14){
		parent.$.prompt.layerUrl("${ctx}/jobChange/load.htm?isApproval=1&eid=" + eid,1082,616);
	}else if(type ==19){
		parent.$.prompt.layerUrl("${ctx}/marketbus/load.htm?isApproval=1&eid=" + eid,1000,616);
	}else if(type == 5) {
		parent.$.prompt.layerUrl("${ctx}/workLog/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",960,530);
	} else if(type == 6) {
		//请假申请
		parent.$.prompt.layerUrl("${ctx}/holiday/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",960,630);
	} else if(type == 7) {
		//差旅申请
		parent.$.prompt.layerUrl("${ctx}/travel/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,616);
	} else if(type == 8) {
		//借支申请
		parent.$.prompt.layerUrl("${ctx}/loan/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,600);
	} else if(type == 16) {
		parent.$.prompt.layerUrl("${ctx}/arrair/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,600);
	} else if(type == 17) {
		parent.$.prompt.layerUrl( "${ctx}/overappaly/load.htm?isApproval=1&eid=" + eid+"&isHomePage=1",1000,600);
	}else if(type ==18){
		parent.$.prompt.layerUrl("${ctx}/saleApply/load.htm?isApproval=1&eid=" + eid,1000,600);
	}
}

function appendWork(data) {
	$("#today-date").html(getWeekDay(data.currDate + " 00:00:00") + "&nbsp; " + data.currDate);
	if(data.needSign == 1) {
		$("#needCheck").show();
		$("#noNeedCheck").hide();
		$("#needCheck").html("");
		if(data.signIn != 1) {
			var html = '<div class="chockin">';
			html += '<div style="line-height:28px;">';
			html += '<span><img src="${ctx}/images/workpench/chockin.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>上班打卡';
			html += '</div>';
			html += '<div style="text-align: center;">';
			html += '<span class="punch-card" data-kind="upTime">上班打卡<br/>'+data.workTime.startTime+'</span>';
			html += '</div>';
			html += '</div>';
			$("#needCheck").append(html);
		} else {
			var html = '<div class="chockin">';
			html += '<div style="line-height:28px;">';
			html += '<span><img src="${ctx}/images/workpench/chockin.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>';
			html += '上班打卡时间 &nbsp; ' + dateFtt("hh:mm", data.upTime);
			html += '</div>';
			html += '<div class="location"><img src="${ctx}/images/workpench/place.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>';
			html += data.upName + '</div>';
			html += '</div>';
			$("#needCheck").append(html);
		}
		if(data.signOut != 1) {
			var html = '<div class="chockout">';
			html += '<div style="line-height:28px;">';
			html += '<span><img src="${ctx}/images/workpench/chockin.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>下班打卡';
			html += '</div>';
			html += '<div style="text-align: center;">';
			html += '<span class="punch-card" data-kind="outTime">下班打卡<br/>'+data.workTime.endTime+'</span>';
			html += '</div>';
			html += '</div>';
			$("#needCheck").append(html);
		} else {
			var html = '<div class="chockout">';
			html += '<div style="line-height:28px;">';
			html += '<span><img src="${ctx}/images/workpench/chockin.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>';
			html += '下班打卡时间 &nbsp; ' + dateFtt("hh:mm", data.outTime);
			html += '</div>';
			html += '<div class="location"><img src="${ctx}/images/workpench/place.png" style="vertical-align: middle;margin-right: 10px;margin-top: -5px"></span>';
			html += data.outName + '</div>';
			html += '</div>';
			$("#needCheck").append(html);
		}
		$(".punch-card").click(function() {
			var kind = $(this).data("kind");
			if(kind == "upTime") {
				signIn(); // 签到
			} else {
				signOut(); // 签退
			}
		});
	} else {
		$("#needCheck").hide();
		$("#noNeedCheck").show();
	}
}
var signInFlag = false;
// 签到
function signIn() {
	if(signInFlag == false) {
		signInFlag = true;
		jsonAjax.post("${ctx}/worktime/upWork.htm", {}, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("签到成功！", $.prompt.msg);
				init.data.workTime();
			} else {
				parent.$.prompt.message("签到失败：" + response.msg, $.prompt.msg);
				signInFlag = false;
			}
		});
	}
}
var signOutFlag = false;
// 签退
function signOut() {
	if(signOutFlag == false) {
		signOutFlag = true;
		jsonAjax.post("${ctx}/worktime/outWork.htm", {}, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("签退成功！", $.prompt.msg);
				init.data.workTime();
			} else {
				parent.$.prompt.message("签退失败：" + response.msg, $.prompt.msg);
				signOutFlag = false;
			}
		});
	}
}
function getWeekDay(strDate){
	var date = getDate(strDate);
	var weekDay = date.getDay();
	if (weekDay == 1) { 
    	weekDayString = "星期一"; 
   	} else if (weekDay == 2) { 
    	weekDayString = "星期二"; 
    } else if (weekDay == 3) { 
        weekDayString = "星期三"; 
    } else if (weekDay == 4) { 
        weekDayString = "星期四"; 
    } else if (weekDay == 5) { 
        weekDayString = "星期五"; 
    } else if (weekDay == 6) { 
        weekDayString = "星期六"; 
    } else if (weekDay == 7) { 
        weekDayString = "星期日"; 
    }
	return weekDayString;
} 
function getDate(strDate) {
    var date = new Date(Date.parse(strDate.replace(/-/g, "/")));
    return date;
}
function dateFtt(fmt, date) {
	date = getDate(date);
	var o = {   
    	"M+" : date.getMonth()+1,                 //月份   
    	"d+" : date.getDate(),                    //日   
    	"h+" : date.getHours(),                   //小时   
    	"m+" : date.getMinutes(),                 //分   
    	"s+" : date.getSeconds(),                 //秒   
    	"q+" : Math.floor((date.getMonth()+3)/3), //季度   
    	"S"  : date.getMilliseconds()             //毫秒   
  	};   
  	if(/(y+)/.test(fmt))   
    	fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  	for(var k in o)   
    	if(new RegExp("("+ k +")").test(fmt))   
  	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  	return fmt;   
}
var configAjax = {
		/**----post：发送post异步请求----**/
		post: function(url, param, successFun, errorFun) {
			jsonAjax.ajax("post", url, param, true, successFun, errorFun);
		},
		/**----get：发送get异步请求----**/
		get: function(url, param, successFun, errorFun) {
			jsonAjax.ajax("get", url, param, true, successFun, errorFun);
		},
		ajax: function(type, url, param, async, successFun, errorFun) {
			if(errorFun == undefined || typeof(errorFun) != "function") {
				errorFun = jsonAjax.errorFun;
			}
			$.ajax({  
				type: type,
		        url: url,
		        data: param,
		        async: async,
		        dataType: "json",
		        success: successFun,
		        error: errorFun,
		    });
		},
		errorFun: function(xmlHttp, textStatus, e) {
			if(layer) {
	    		$.prompt.message("网络繁忙，请稍候!", $.prompt.error);
	    	} else {
	    		alert("网络繁忙，请稍候!");
	    	}
		}
	};
	
var option3 = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            name:'待办事宜',
	            type:'pie',
	            radius: ['40%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                    show: false,
	                    textStyle: {
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                { name:'待我审批',itemStyle: {
	                    normal: {
	                        color: '#F67D20'
	                    }
	                }},
	                {name:'抄送给我',itemStyle: {
	                    normal: {
	                        color: '#2CAAFE'
	                    }
	                }},
	                { name:'工作提醒',itemStyle: {
	                    normal: {
	                        color: '#62CA0D'
	                    }
	                }}
	            ]
	        }
	    ]
	};
	

</script>
</body>
</html>