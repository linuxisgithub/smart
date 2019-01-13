$(function() {
	var path = $("#contextPath").val();
	var type = $("#type").val();

	$("#return_btn").click(function() {
		closeWin();
	});

	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}

	$("#username").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			//触发登录按钮
			$("#binding_btn").trigger("click");
		}
	});
	$("#password").keydown(function(event){
		if(event.keyCode==13){
			event.preventDefault();
			//触发登录按钮
			$("#binding_btn").trigger("click");
		}
	});
	$("#binding_btn").click(function() {
		var name = $('#username').val();
		if(name == null || name == ""){
			//提示错误
			$.prompt.message("手机号或登录账号不能为空！", $.prompt.msg);
			return false;
		}
		if (parent.account == name) {
            $.prompt.message("不能绑定当前摊位账号！", $.prompt.msg);
            return false;
        }
		var password = $('#password').val();
		if(password == null || password == ""){
			$.prompt.message("密码不能为空！", $.prompt.msg);
			return false;
		}
		$.ajax({
			'url' : path+"/tw/binding.htm",
			'type' : "post",
			'async' : true,
			'data' : {account: $("#username").val(),password: MD5Util(MD5Util($("#password").val())),type:type},
			'dataType' : 'json',
			'beforeSend': function(xmlHttp) {
				xmlHttp.loadIndex = layer.load();
			},
			'success' : function(response, statusText) {
				if(response.status == 200) {
					if(response.data.result == 0) {
					    var menu = response.data.menu;
						var addEle;
					    if (type == 'tw'){
							addEle = parent.$("#addTW");
						} else{
							addEle = parent.$("#addSG");
						}
                        addEle.before(" <li id='"+menu.id+"'>"+menu.name+"<span class='im-redNum_2' id='"+menu.id+"_dot' style='display: none;'></span></li>");
                        parent.index_config.allMenus[menu.id] = menu;
                        parent.index_config.firstMenus[menu.id] = menu;
                        parent.index_config.secMenus[menu.pid].push(menu);
						parent.$('.mainLeft-setting-menu .menu-tit2 li').unbind();
                        parent.index_config.secMenuAction();
						parent.$.prompt.message("绑定成功！", $.prompt.ok);
						closeWin();
					} else {
						$.prompt.message("绑定失败：" + response.data.msg, $.prompt.msg);
					}
				} else {
					$.prompt.message("绑定失败：网络繁忙，请稍候", $.prompt.error);
				}
			},
			'error' : function(xmlHttp, e1, e2) {
				$.prompt.message("绑定失败：网络繁忙，请稍候", $.prompt.error);
			},
			'complete': function(xmlHttp, textStatus) {
				layer.close(xmlHttp.loadIndex);
			}
		});
	});
});