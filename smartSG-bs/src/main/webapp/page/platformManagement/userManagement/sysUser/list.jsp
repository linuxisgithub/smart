<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
			<c:if test="${type ne 6}">
				<button class="pagination_btn add_btn" id="add_btn">
					新增${type == 2 ? '食阁管理公司':(type == 3 ? '食阁':(type == 4 ? '摊位':(type == 5 ? '食阁所有者':'公司内部')))}用户
				</button>
			</c:if>
		</div>
	</div>
</div>
<div style="display: none" alt="隐藏表单域">
	<input type="hidden" id="confirmId" value="${param.confirmId}">
	<input type="hidden" id="hasConfirmId" value="${param.hasConfirmId}">
	<input type="hidden" id="confirmPid" value="${param.confirmPid}">
</div>
<script type="text/javascript" src="${ctx}/tools/twbsPagination/js/paginationDemo.js"></script>
<script type="text/javascript">
$(function() {
	var type = ${type};
	var dtype = ${dtype};
	var str = '部门';
	if(type == 2){
		str = '食阁管理公司';
	}else if(type == 3){
		str = '食阁名称';
	}else if(type == 4){
		str = '摊位名称';
	}else if(type == 5){
		str = '食阁拥有主体';
	}
	var url = "${ctx}/sysUser/data/"+type+"/"+dtype+"/list.htm";
	if (type == 6){
        initBlack();
    }else{
        init();
    }
	function init(){
        initPagination($("#pagination"), {
            url: url,
            titles:[
                {label: "eid", visible:false},
                {label: "系统账号", width: "15%"},
                {label: "姓<span class='wzkg'></span>名", width: "10%"},
                {label: "所属" + str, width: "15%"},
                {label: "职<span class='wzkg'></span>务", width: "10%"},
                {label: "性<span class='wzkg'></span>别", width: "10%"},
                {label: "电<span class='wzkg'></span>话", width: "10%"},
                {label: "初始密码", width: "10%"},
                {label: "状<span class='wzkg'></span>态", width: "10%"},
                {label: "编<span class='wzkg'></span>辑", width: "10%", classs: ['th-bgcolor']},
            ],
            columns: [
                {field: "eid", visible:false},
                {field: "account"},
                {field: "name"},
                {field: "deptName"},
                {field: "job"},
                {field: "sex"},
                {field: "telephone"},
                {field: "showPassword"},
                {field: "status",format:format},
                {fhtml: "<img src='${ctx}/images/icons/gray_edit.png'/>", func: toDetail}
            ]
        });
        $("#add_btn").click(function() {
            parent.$.prompt.layerUrl("${ctx}/sysUser/load.htm?type=" + type, 1010);
        });
    }
    function initBlack(){
        initPagination($("#pagination"), {
            url: url,
            titles:[
                {label: "eid", visible:false},
                {label: "系统账号", width: "8%"},
                {label: "姓<span class='wzkg'></span>名", width: "8%"},
                {label: "用户类别", width: "14%"},
                {label: "所属部门", width: "14%"},
                {label: "职<span class='wzkg'></span>务", width: "10%"},
                {label: "性<span class='wzkg'></span>别", width: "5%"},
                {label: "电<span class='wzkg'></span>话", width: "10%"},
                {label: "初始密码", width: "8%"},
				{label: "状<span class='wzkg'></span>态", width: "7%"},
                {label: "编<span class='wzkg'></span>辑", width: "8%", classs: ['th-bgcolor']},
				{label: "删<span class='wzkg'></span>除", width: "8%", classs: ['th-bgcolor']}
            ],
            columns: [
                {field: "eid", visible:false},
                {field: "account"},
                {field: "name"},
                {field: "userType",forma:forma},
                {field: "deptName"},
                {field: "job"},
                {field: "sex"},
                {field: "telephone"},
                {field: "showPassword"},
                {field: "status",format:format},
				{fhtml: "<a>移出黑名单</a>", func: toBlack},
                {fhtml: "<img src='${ctx}/images/icons/clear_icon.png'/>", func: toDel}
            ]
        });
    }
	function toBlack(page){
		layer.confirm("确认把该用户移出黑名单吗?", { title: "移出确认",btn: ['确认','取消'] }, function (index) {
			var param = {eid: page.item.eid};
			var path = $("#contextPath").val();
			jsonAjax.post(path + "/sysUser/removeBlack.htm", param, function(response) {
				if(response.status == 200) {
					parent.$.prompt.message("移出成功！", $.prompt.ok);
					refreshList();
					layer.close(index);
				} else {
					parent.$.prompt.message("移出失败：" + response.msg, $.prompt.error);
					layer.close(index);
				}
				//window.location.href = window.location.href;
			});
		}, function(index) {
			layer.close(index);
		});
	}
	function toDel(page){
		layer.confirm("确认把该用户删除吗?", { title: "删除确认",btn: ['确认','取消'] }, function (index) {
			var param = {eid: page.item.eid};
			var path = $("#contextPath").val();
			jsonAjax.post(path + "/sysUser/remove.htm", param, function(response) {
				if(response.status == 200) {
					parent.$.prompt.message("删除成功！", $.prompt.ok);
					refreshList();
					layer.close(index);
				} else {
					parent.$.prompt.message("删除失败：" + response.msg, $.prompt.error);
					layer.close(index);
				}
				//window.location.href = window.location.href;
			});
		}, function(index) {
			layer.close(index);
		});
	}

	function toDetail(page) {
		parent.$.prompt.layerUrl("${ctx}/sysUser/load.htm?eid=" + page.item.eid, 1010);
	}
});
function format(item){
	if (item.status == 1){
		return '启用';
	}else{
		return '停用';
	}
}

function forma(item){
    var str = '';
    if(item.userType == 1){
        str = '本公司内部用户';
    }else if(item.userType == 2){
        str = '食阁管理公司用户';
    }else if(item.userType == 3){
        str = '食阁用户';
    }else if(item.userType == 4){
        str = '档口用户';
    }else if(item.userType == 5){
        str = '食阁拥有者用户';
    }
    return str;
}
</script>
</body>
</html>