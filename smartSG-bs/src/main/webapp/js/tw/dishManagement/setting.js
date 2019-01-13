var path = $("#contextPath").val();
var bid = $("#bid").val();
$(function() {
	var setMealBtn = false;

	$("#s_type").select2({minimumResultsForSearch: search_detault,});

	$("#return_btn").click(function() {
		window.history.back();
	});
	$("#addGroup_btn").click(function() {
		$(this).prop('disabled', true);
		var name = $("#groupName").val();
		if ($.trim(name) == "") {
			$.prompt.message("分组名称不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		var url = path+"/dishGroup/save.htm";
		jsonAjax.post(url, {name:name,bid:bid}, function(response) {
			if(response.status == 200) {
				var dg = response.data;
				$("#groups").append('<div class="group" id="'+dg.eid+'">'+dg.name+'<a href="javascript:void(0)"><img src="'+path+'/images/icons/clear_icon.png" class="delete"  onclick="del('+dg.eid+')"/></a></div>');
				$.prompt.message("添加分组成功！", $.prompt.ok);
				$("#groupName").val("");
			} else {
				$.prompt.message("添加分组失败：" + response.msg, $.prompt.msg);
			}
		});
		$("#addGroup_btn").prop('disabled', false);
	});

	$(".setMealInput").change(function(){
		setMealBtn = true;
	});

	$("#setMeal_btn").click(function() {
		if (!setMealBtn){
			$.prompt.message("您已经保存过了^_^！", $.prompt.msg);
			return false;
		}
		$(this).prop('disabled', true);
		jsonAjax.post(path+"/setMeal/save.htm",$("#setMealForm").serialize(), function(response) {
			if(response.status == 200) {
				$.prompt.message("保存成功！", $.prompt.ok);
				$("#setMeal_btn").prop('disabled', false);
				setMealBtn = false;
			} else {
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				$("#setMeal_btn").prop('disabled', false);
			}
		});
	});

	$("#addFeatures_btn").click(function() {
		parent.$.prompt.layerUrl(path+"/dishFeatures/load.htm?type=1&bid="+bid);
	});

});
function toDetail(eid){
	parent.$.prompt.layerUrl(path+"/dishFeatures/load.htm?type=1&bid="+bid+"&eid="+eid);
}

function toPreview(eid,name){
	parent.$.prompt.layerUrl(path+"/dishFeaturesOption/preview.htm?eid="+eid+"&name="+name,600,400);
}

function delGroup(id){
	jsonAjax.post(path+"/dishGroup/remove.htm", {eid:id}, function(response) {
		if(response.status == 200) {
			$.prompt.message("删除分组成功！", $.prompt.ok);
			$("#"+id).remove();
		} else {
			$.prompt.message("删除分组失败：" + response.msg, $.prompt.error);
		}
	});
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