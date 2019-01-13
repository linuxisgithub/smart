$(function() {
	var path = $("#contextPath").val();
 	var type = $("#type").val();
	$("#s_optionType").select2({minimumResultsForSearch: search_detault,});
	$("#s_isSelected").select2({minimumResultsForSearch: search_detault,});
	isDetaultChange();

	$("#return_btn").click(function() {
		closeWin();
	});

	$("#addFeaturesOption_btn").click(function() {
		var row_update = $("#tbody_update").find("tr").length;
		var row_insert = $("#tbody_insert").find("tr").length+1;
		var row = row_insert + row_update;
		var i = row_insert-1;
		$("#tbody_insert").append('<tr><td><input type="text" name="options_insert['+i+'].optionValue" readonly value="'+row+'"></td>' +
			'<td><input type="text" name="options_insert['+i+'].name"></td>' +
			'<td><input type="text" name="options_insert['+i+'].enName"></td>' +
			'<td><select class="isDefault" name="options_insert['+i+'].isDefault"><option value="1">默认</option><option value="2" selected>不默认</option></select></td>' +
			'<td><input type="text" name="options_insert['+i+'].money"></td></tr>');
		isDetaultChange();
	});

	$("#s_optionType").change(function() {
		if($(this).val() == 1 ){
			$(".isDefault").each(function () {
				$(this).val(2);
			})
		}
	})

	function isDetaultChange(){
		$(".isDefault").change(function() {
			var optionType = $("#s_optionType").val();
			if (optionType == 1){
				var $elements = $(".isDefault");
				/*var count = 0;
				$elements.each(function () {
					var $this = $(this);
					if($this.val()==1){
						count++;
					}
				})
				if (count>1){
					if ($(this).val() == 1 ){
						$(this).val(2);
					}
					$.prompt.message("该特性为单选，有且只能有一个默认值！", $.prompt.error);
				}*/
				if ($(this).val() == 1 ){
					$elements.each(function () {
						$(this).val(2);
					})
					$(this).val(1);
				}
			}
		});
	}

	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		/*if($.trim($("#name").val()) == "") {
			$.prompt.message("字段名称不能为空！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}*/
		var optionType = $("#s_optionType").val();
		var $elements = $(".isDefault");
		var count = 0;
		$elements.each(function () {
			var $this = $(this);
			if($this.val()==1){
				count++;
			}
		})
		if (count<1){
			if(optionType == 1){
				$.prompt.message("请设置一个默认值！", $.prompt.error);
			}else{
				$.prompt.message("请最少设置一个默认值！", $.prompt.error);
			}
			$(this).prop('disabled', false);
			return false;
		}
		jsonAjax.post(path + "/dishFeatures/save.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("保存成功！", $.prompt.ok);
				var bid = $("#bid").val();
				if (type == 1){
					parent.$("#content_iframe").attr("src",path+"/dish/setting.htm?type=1&bid="+bid);
				} else{
					parent.$("#content_iframe").attr("src",path+"/dishFeatures/data/list.htm?bid="+bid);
					//parent.refreshContent();
				}
				closeWin();
			} else {
				$.prompt.message("保存失败：" + response.msg, $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});