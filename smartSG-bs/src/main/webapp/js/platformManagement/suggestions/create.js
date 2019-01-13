var path = $("#contextPath").val();
$(function() {

	initDictionary($("#s_kind"), {type:'XXLX',key:'XXLX'});//初始化消息类型

	if($("#eid").val() == ""){
		$("#clear_btn").show();
		$("#clear_btn").click(function(){
			$("#mainForm")[0].reset();
			$("select").val(-1).trigger('change');
			$("input[type='hidden']").val("");
		});
		$("#s_receiveIds").click(function(){
			var url = path + "/suggestions/select.htm";
			url += "?useIds="+$("#useIds").val();
			$.prompt.layerUrl2({url: url, width: 672, height: 498});
		});
	}else{
		ajaxObj['XXLX'].done(function(){
			$("#s_kind").val($("#i_kind").val()).trigger("change");
		});
		//TODO
	}
	
	$("#return_btn").click(function() {
		closeWin();
	});
	
	$("#save_btn").click(function() {
		$(this).prop('disabled', true);
		if($("#eid").val() == "" && $.trim($("#cc").val()) == ""){
			$.prompt.message("请选择收件人！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($("#s_kind").val() == -1){
			$.prompt.message("请选择消息类型！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		if($.trim($("#s_content").val()) == "") {
			$.prompt.message("请填写意见内容！", $.prompt.error);
			$(this).prop('disabled', false);
			return false;
		}
		
		jsonAjax.post(path + "/suggestions/send.htm",$("#mainForm").serialize(), function(response) {
			if(response.status == 200) {
				$(this).prop('disabled', false);
				parent.$.prompt.message("发送成功！", $.prompt.ok);
				parent.$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				parent.refreshContent();
				closeWin();
			} else {
				$.prompt.message("发送失败：" + response.msg, $.prompt.error);
				$("#save_btn").prop('disabled', false);
			}
		});
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
});