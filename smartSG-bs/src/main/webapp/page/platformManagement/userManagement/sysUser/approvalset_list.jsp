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
<div class="person-box">
	<div class="person-title">选择批审人</div>
	<div class="person-tab-nav" id="person-tab-nav">
		<ul>
			<c:choose>
				<c:when test="${USER_IN_SESSION.company.isSpec == 1}">
					<li class="active" data-ref-tab="#ptab1" data-level="1">科室负责人</li>
					<li data-ref-tab="#ptab2" data-level="2">科室副职</li>
				</c:when>
				<c:otherwise>
					<li class="active" data-ref-tab="#ptab1" data-level="1">公司管理层</li>
					<li data-ref-tab="#ptab2" data-level="2">部门干部</li>
				</c:otherwise>
			</c:choose>
		</ul>
       <div class="clear"></div>
	</div>	
	<div class="person-tab-container" id="person-tab-container">
		<ul class="person-tab open" id='ptab1'>
		</ul>				
		<ul class="person-tab" id="ptab2">
		</ul>				
	</div>
	<div class="person-tab-bottom">
		终审：
		<label class="person-label">
			<input class="person-radio" type="radio" name="isEnd" value="1" <c:if test="${param.no eq 8}">checked="checked"</c:if>>
			<span class="person-radioInput"></span>
			<span style="margin:0px 8px;">是</span>
		</label>
		<label class="person-label">
			<input class="person-radio" type="radio" name="isEnd" value="0" <c:if test="${param.no ne 8}">checked="checked"</c:if>>
			<span class="person-radioInput"></span>
			<span style="margin:0px 10px;">否</span>
		</label>
		<button id="save_btn" style="cursor: pointer;"><img src="${ctx}/images/icons/confirm_icon2.png"></button>
	</div>

</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript">
var _path = $("#contextPath").val();
var index = "${param.lastIndex}";
var no = "${param.no}";
var users = {};
var approvalNnum = 8;
$(function() {
	initData();
	var userLevel = 1;
	$('#person-tab-nav li').click(function() {
		var $that = $(this);
		// 样式切换
		$('#person-tab-nav li').removeClass('active');
		$that.addClass('active');
		// 标签内容切换
		var tab = $(this).data('ref-tab');
		userLevel = $(this).data('level');
		if(tab) {
			$('#person-tab-container .person-tab').removeClass('open');
			$('#person-tab-container '+tab).addClass('open');
		}
	});
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		var userId = $("input[name=level_"+userLevel+"]:checked").val();
		if(userId == undefined) {
			$.prompt.message("请选择一个批审人", $.prompt.msg);
			$(this).prop('disabled', false);
			return false;
		}
		var user = users[userId];
		user['userId'] = userId;
		var isEnd = $("input[name=isEnd]:checked").val();
		if(no == approvalNnum) {
			isEnd = 1;
		}
		parent.createNext(index, no, isEnd, user);
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	});
});
function initData() {
	jsonAjax.post(_path + "/sysUser/data/level/1.htm", {}, function(response) {
		if(response.status == 200) {
			$.each(response.data, function(index, user) {
				users[user.eid] = user;
				appendUser($("#ptab1"), user, 1);
			});
		} else {
			$.prompt.message("获取数据失败：" + response.msg, $.prompt.error);
		}
	});
	jsonAjax.post(_path + "/sysUser/data/level/2.htm", {}, function(response) {
		if(response.status == 200) {
			$.each(response.data, function(index, user) {
				users[user.eid] = user;
				appendUser($("#ptab2"), user, 2);
			});
		} else {
			$.prompt.message("获取数据失败：" + response.msg, $.prompt.error);
		}
	});
}
function appendUser($tabId, user, level) {
	var html = '';
	html += '<li>';
	html += '<label class="person-label" style="cursor: pointer;">';
	html += '<input class="person-radio" type="radio" name="level_'+level+'" value="'+user.eid+'">';
	html += '<span class="person-radioInput" style="float:left;margin-top:13px;margin-left:-2px;"></span>';
	html += '<span class="person-name" style="float:left;" title="'+user.name+'">'+user.name+'</span>';
	
	html += '<span class="person-job" style="float:left;" title="'+user.job+'">'+user.job+'</span>';
	html += '</label>';
	html += '</li>';
	$tabId.append(html);
}
</script>
</body>
</html>