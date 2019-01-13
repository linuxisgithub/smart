$(function() {
	var path = $("#contextPath").val();
	var kind = $("#kind").val();
	$("#return_btn").click(function() {
		closeWin();
	});
	initField(kind);
	$("#kind").select2({
		minimumResultsForSearch: search_detault,
	});
	$("#kind").change(function() {
		$("#liOption").empty();
		$(".ms2side__div").remove();
		initField($(this).val());
	});
	function initField(kind){
		jsonAjax.post(path + "/field/" + kind + "/data.htm", {}, function(response) {
			if(response.status == 200) {
				var datas = response.data;
				var data = [];
				//var htmls = "";
				for (var i = 0;i<datas.length;i++){
					var opt = {name:datas[i].name,value:datas[i].eid};
					data.push(opt);
					//htmls += "<option value='"+datas[i].eid+"'>"+datas[i].name+"</option>"
				}
				formSelects.data('selectId', 'local', {
					arr: data
				});
				//$("#liOption").append(htmls);
				var options = $("#options").val();
				if (options != null && options != ''){//修改赋值

					formSelects.value('selectId', JSON.parse(options));
				}
				/*$("#liOption").multiselect2side({
					selectedPosition: 'right',
					moveOptions: false,
					labelsx: '待选区',
					labeldx: '已选区'
				});*/
			} else {
				parent.$.prompt.message("获取报表字段数据失败" + response.msg, $.prompt.error);
			}
		});

	}

	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		if($.trim($("#name").val()) == "") {
			$.prompt.message("报表名称不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#description").val()) == "") {
			$.prompt.message("报表描述不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		//var liOption = $("#liOption").val();
		var liOption = formSelects.value('selectId');
		if(liOption == null || liOption == ''|| liOption == '[]') {
			$.prompt.message("请选择报表字段！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		jsonAjax.post(path + "/report/save.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				parent.refreshContent();
				closeWin();
			} else {
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				$(this).prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});