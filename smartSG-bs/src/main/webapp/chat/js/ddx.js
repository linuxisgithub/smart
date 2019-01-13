/**
 * Created by Chris on 2016/6/01.
 */
var projectName = $("#contextPath").val();
var defaultSingleIcon = projectName+"/chat/images/defaultSingleIcon.png";
var defaultGroupsIcon = projectName+"/chat/images/defaultGroupsIcon.png";
var deleteIcon = projectName+"/chat/images/delete.png";
var removeIcon = projectName+"/chat/images/remove.png";
var exitIcon = projectName+"/chat/images/exit.png";
var editIcon = projectName+"/chat/images/edit.png";
var agreeIcon = projectName+"/chat/images/agree.png";
var ws_url,im_url;
var im = {};
var only_im = 0;
var config = {
	currentUser: { //当前用户信息
        companyId: 1,
        imAccount:'',
        password:'',
        name: '',
        icon: defaultSingleIcon,
        dpName:''
    },
    pictureType:{ //图片类型
    	"jpg" : true,
        "png" : true,
        "gif" : true,
        "bmp" : true
    },   
    jsonGet: function (url, data, callback, error) {
        return $.ajax({
            type: 'GET',
            url: url,
			timeout:10000,
            async:false,
            data: data,
            dataType: 'json',
            success: callback,
            error: error
        });
    },
	jsonGetAsync: function (url, data, callback, error) {
		return $.ajax({
			type: 'GET',
			url: url,
			timeout:10000,
			async:true,
			data: data,
			dataType: 'json',
			success: callback,
			error: error
		});
	},
    jsonPost: function(url, data, callback, error){
        return $.ajax({
            type: 'POST',
			timeout:10000,
            url: url,
            async:false,
            data: data,
            dataType: 'json',
            success: callback,
            error: error
        });
    },
	jsonPostAsync: function(url, data, callback, error) {
		return $.ajax({
			type: 'POST',
			timeout: 10000,
			url: url,
			async: true,
			data: data,
			dataType: 'json',
			success: callback,
			error: error
		});
	}
}

var acceptedData = {};

var allMain,allContent,allContentItemDDX;
var mainDDX,mainTH,mainTXL,mainYYHY,mainW,mainFQQL,mainTJPY,mainSYS,mainSFK,mainYJFK;
var contentDDX,contentTH,contentTXL,contentYYHY,contentW,contentFQQL,contentTJPY,contentSYS,contentSFK,contentYJFK;
var contracts,groups;
var allChatContent,allPageContent;
var contentGZQL;

var	dateFormat = function(dateObj,format){ 
		var o = { 
		"M+" : dateObj.getMonth()+1, //month 
		"d+" : dateObj.getDate(), //day 
		"h+" : dateObj.getHours(), //hour 
		"m+" : dateObj.getMinutes(), //minute 
		"s+" : dateObj.getSeconds(), //second 
		"q+" : Math.floor((dateObj.getMonth()+3)/3), //quarter 
		"S" : dateObj.getMilliseconds() //millisecond 
		} 

		if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (dateObj.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		} 

		for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
		format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
		} 
		return format; 
		/**
		 * 	/示例： 
		 *  dateFormat(new Date(),"yyyy年MM月dd日"); 
		 *	dateFormat(new Date(),"MM/dd/yyyy"); 
		 *	dateFormat(new Date(),"yyyyMMdd")); 
		 *	dateFormat(new Date(),"yyyy-MM-dd hh:mm:ss");
		 *
		 * **/
	}

var emojisMap = {
	"1f60a":"😊",
	"1f603":"😃",
	"1f609":"😉",
	"1f636":"😮",
	"1f60b":"😋",
	"1f60e":"😎",
	"1f621":"😡",
	"1f616":"😖",
	"1f633":"😳",
	"1f61e":"😞",
	"1f62d":"😭",
	"1f610":"😐",
	"1f607":"😇",
	"1f601":"😬",
	"1f606":"😆",
	"1f631":"😱",
	"1f385":"🎅",
	"1f634":"😴",
	"1f615":"😕",
	"1f637":"😷",
	"1f62f":"😯",
	"1f60f":"😏",
	"1f611":"😑",
	"1f496":"💖",
	"1f494":"💔",
	"1f319":"🌙",
	"1f31f":"🌟",
	"1f31e":"🌞",
	"1f308":"🌈",
	"1f60d":"😍",
	"1f61a":"😚",
	"1f48b":"💋",
	"1f339":"🌹",
	"1f342":"🍂",
	"1f44d":"👍",
	"1f44e":"👎"
};



/**从配置文件中获取链接地址**/
var getUrls = function(){
	config.jsonGet(projectName + "/DDX/getUrls.htm", {}, function(datas){
		if(datas.status === 200){
			ws_url = datas.wsUrl;
			im_url = datas.imUrl;
		} else {
			tips.error(datas.status+":请求链接地址失败!");
		}
	}, function(e){
		tips.error("请求链接地址失败!");
	});
};

/**请求通讯录数据**/
var getContactsData = function(){
   config.jsonGet(projectName + "/DDX/contactList.htm?timestemp="+(new Date().getTime()), {}, function(datas){
    	if(datas.status === 200){
        	contacts = datas.contacts;
        	contentTXL.html("");
        	/*var sequenceHtml = '<div style="width:500px;text-align:center;height:30px;line-height:30px;font-size:18px;'
				+'float:left;padding:5px 15px 5px 15px;" id="friend_sequence">我的朋友</div>';
			contentTXL.append(sequenceHtml);*/
        	$.each(contacts,function(key,value){
        		//排序号
        		var sequence = key;
        		//添加排序号
        		addSequenceToTXLmain(sequence);
        		//用户json对象数组
        		var userArray = value;
        		$.map(userArray,function(user){
        			if(!user.icon){
        				user.icon = defaultSingleIcon;
        			}
					if(!user.dpName){
						user.dpName = "";
					}
					if(user.companyId != config.currentUser.companyId){
						user.dpName = "客户";
					}
        			if(user.userType != 3 && user.imAccount != config.currentUser.imAccount){
						//添加userType!=3 不是客服的用户
						addContactsToTXLmain(user);
					}
				})
        	});

			//去掉没有联系人的排序号
			$("div[id^='sequence_']").each(function(){
				var nextElementTagName = $(this).next().prop("tagName");
				if(nextElementTagName != "A"){
					$(this).remove();
				}
			})
        } else {
        	tips.error(datas.status+":请求通讯录数据失败!");
        }
    }, function(e){
        tips.error("请求通讯录数据失败!");
    });
};

/**请求部门通讯录数据**/
var getDeptInfoCount = function(){
   config.jsonGet(projectName + "/DDX/deptInfoCount.htm?timestemp="+(new Date().getTime()), {}, function(datas){
    	if(datas.status === 200){
        	var deptInfos = datas.deptInfo;
        	var deptInfoMain = $("#pageContent_TXL_GSTXL");
        	deptInfoMain.html("");
        	$.each(deptInfos,function(index, dept){
        		var html = '<a href="javascript:void(0);" id="item_GSTXL_'+dept.deptId+'" data-deptId="'+dept.deptId+'">';
        		html += '<div class="ddx_middle1" style="height: 50px;">';
        		html += '<div class="ddx_middle1nr" style="margin-top: 0px;">';
        		html += '<div class="ddx_middle1nr1" style="margin-top: 15px;">'+dept.deptName+'('+dept.count+')</div>';
        		html += '</div>';
        		html += '<div class="ddx_middle1sj" style="padding-top: 8px; width: 80px;margin-top: 0px;">';
        		html += '<div style="height: 40px; width: 30px; float: left; margin-left: 45px;">';
        		html += '<img src="'+projectName+'/chat/images/wo/jt.png" />';
        		html += '</div>';
        		html += '</div>';
        		html += '</div>';
        		html += '</a>';
        		deptInfoMain.append(html);
        		if(dept.count > 0) {
        			$("#item_GSTXL_" + dept.deptId).click(function() {
        				$("#page_TXL_GSTXL").hide();
        				$("#page_TXL_GSTXL_BMTXL").show();
        				var deptId = $(this).attr("data-deptId");
        				jsonAjax.post(path + "/DDX/getDeptContactList.htm", {deptId: deptId}, function(response) {
        					var contacts = response.deptContactList;
        					contentTXL_BM.html("");
        		        	$.each(contacts,function(key,value){
        		        		//排序号
        		        		var sequence = key;
        		        		//添加排序号
        		        		var sequenceHtml = '<div style="width:500px;background-color:#f1f0f6;'
        		        			+'float:left;padding:5px 15px 5px 15px;color:#999999;" id="sequence_'+sequence+'">'+sequence+'</div>';
        		        		contentTXL_BM.append(sequenceHtml);
        		        		
        		        		//用户json对象数组
        		        		var userArray = value;
        		        		$.map(userArray,function(user){
        		        			if(!user.icon){
        		        				user.icon = defaultSingleIcon;
        		        			}
        							if(!user.dpName){
        								user.dpName = "";
        							}
        							if(user.companyId != config.currentUser.companyId){
        								user.dpName = "客户";
        							}
        		        			if(user.userType != 3 && user.imAccount != config.currentUser.imAccount){
        								//添加userType!=2 不是客服的用户
        		        				addContactsToBMTXLmain(user);
        							}
        						})
        		        	});

        					//去掉没有联系人的排序号
        					$("div[id^='sequence_']").each(function(){
        						var nextElementTagName = $(this).next().prop("tagName");
        						if(nextElementTagName != "A"){
        							$(this).remove();
        						}
        					})
        				});
        			});
        		}
        	});

        }
    }, function(e){
        tips.error("请求据失败!");
    });
};

var kefuList = {};//保存客服信息
/**请求客服数据**/
var getkefuData = function(){
	config.jsonGet(projectName + "/DDX/kefuList.htm?timestemp="+(new Date().getTime()), {}, function(datas){
		if(datas.status === 200 && datas.contacts){
			$.map(datas.contacts,function(user){
				if(!user.icon){
					user.icon = defaultSingleIcon;
				}
				if( user.imAccount != config.currentUser.imAccount){
					addContactsToCXTDmain(user);
				}
				user['isClick'] = false;
				kefuList[user.imAccount] = user;
			});
		} else {
			tips.error(datas.status+":请求客服数据失败!");
		}
	}, function(e){
		tips.error("请求客服数据失败!");
	});

};



/**请求当前用户信息**/
var getCurrentUserInfo = function(){
	config.jsonPost(projectName + "/DDX/currentUser.htm?timestemp="+(new Date().getTime()), '', function(datas){
    	if(datas.status === 200){
			//alert(JSON.stringify(datas.user));
    		config.currentUser = datas.user;
			if(!config.currentUser.icon || $.trim(config.currentUser.icon) == ''){
				config.currentUser.icon = defaultSingleIcon;
			}
    		//设置 我 的个人信息
        	$("#item_W_info_icon").attr("src",config.currentUser.icon);
        	$("#item_W_info_name").text(config.currentUser.name);
        	/*if("normal_user"==config.currentUser.jobRole){
        		$("#item_W_info_jobRole").text('员工层');
        	}else{
        		$("#item_W_info_jobRole").text('管理层');
        	}*/
        	
        } else {
        	tips.error(datas.status+":请求当前用户信息失败!");
        }
    }, function(e){
        tips.error("请求当前用户信息失败!");
    });
}

/**请求当前用户参与的群组**/
var getGroupsData = function(){
	config.jsonGet(im_url+"group/users/"+config.currentUser.imAccount, {}, function(datas){
    	if(datas.status === 200){
			//清空工作群聊列表
			$("a[name='item_GZQL']").remove();
    		var groupsList = datas.groups;
    		var groupsIds = {};
    		for(var i=0,l=groupsList.length;i<l;i++){
    			var groupsData = groupsList[i];
    			//添加groupType=1普通群组并且去掉重复的群组
    			if(groupsData.groupType == 1 && !groupsIds[groupsData.groupId]){  
    				groupsIds[groupsData.groupId] = "1";
    				//添加到工作群组
    				var groups = {};
    				groups.groupsId = groupsData.groupId;
    				groups.name = groupsData.groupName;
    				groups.groupType = groupsData.groupType;
    				groups.icon = defaultGroupsIcon;
					groups.owner = groupsData.owner;
					//添加
    				addGroupsToGZQLmain(groups);
    			}
    		}
    		
        } else {
        	tips.error(datas.status+":请求群组数据失败!");
        }
    }, function(e){
        tips.error("请求群组数据失败!");
    });
}


/**请求群组成员信息**/
var getGroupsDetailData = function(groupsId){
	var groupsDetail;
	config.jsonGet(im_url+"group/"+groupsId+"/detail", {}, function(datas){
		if(datas.status === 200){
			//alert(JSON.stringify(datas));
			groupsDetail = datas;
		} else {
			tips.error(datas.status+":请求群组成员数据失败!");
		}
	}, function(e){
		tips.error("请求群组成员数据失败!");
	});
	return groupsDetail;
}

/**打开选择联系人窗口**/
var openSelectContactIframe = function(){
	var param = {'content':projectName + "/DDX/selectContacts.htm",'maxmin':false,'title':'请选择群组成员',
			'area':['450px','350px']};
	if(only_im == 0) {
		param.offset = [($(window).height()+70-350)/2, ($(window).width()+221-450)/2];
	}
	$.openIframe(param);
}
	
//创建群组
var createGroups = function(imAccounts){
	if(imAccounts){	
		//关闭选择联系人窗口
       	$("div[id^='layui-layer']").each(function(){
       		var openIframeId = $(this).attr("id");
       		if(openIframeId.indexOf("shade") == -1){
       			var index = openIframeId.replace("layui-layer","");
       			layer.close(index);
       		}
       	}); 
       	var param = {title: '请输入群组名称！', formType: 0};
       	if(only_im == 0) {
    		param.offset = [($(window).height()+60-160)/2, ($(window).width()+200-260)/2];
    	}
       	layer.prompt(param, function(groupName, index){
       		var members = JSON.stringify(imAccounts.split(","));
			//alert(members);
			var data = {"members":members,"owner":config.currentUser.imAccount,"groupName":groupName,"groupType":1};

			//发送请求
			config.jsonPostAsync(im_url+"group/"+config.currentUser.companyId, data, function(datas){
		    	if(datas.status === 200){
		    		//添加到工作群组
    				var groups = {};
    				groups.groupsId = datas.groupId;
    				groups.name = datas.groupName;
    				groups.groupType = datas.groupType;
    				groups.owner = datas.owner;
    				groups.icon = defaultGroupsIcon;
    				
    				addGroupsToGZQLmain(groups);

		    		layerMsg("创建群组[ "+groupName+" ]成功！");
		    		
		        } else {
		        	layerMsg(datas.status+":创建群组失败!");
		        }
		    	layer.close(index);
		    }, function(e){
		        layerMsg("创建群组失败!");
		    });
		});
	}
}


//连接聊天服务器
im.connect = function () {	
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
		var webSocketLinkUrl = ws_url+"?userId=" + config.currentUser.imAccount + "&pwd=" + config.currentUser.password + "&type=1";
    	//alert(webSocketLinkUrl);
        im.socket = new ReconnectingWebSocket(webSocketLinkUrl,null,{ isReconnect: false });//isReconnect断线后是否重连，false=不重连
        //im.socket = new WebSocket(webSocketLinkUrl);
        im.socket.onopen = function (event) {
        	//自己踢人
            //请求离线消息
            im.sendOfflientRequestMessage();
        };
        //接收消息回调
        im.socket.onmessage = function (event) {
			var responseMessageIds = [];// 响应消息id

            var message = JSON.parse(event.data);
            if (message) {
            	/*debugger*/
				console.log(JSON.stringify(message));
                //单聊
                if (message.type === 1) {
					var attachment = message.attachment;
					var isBurnAfterRead = "2";//1为阅后即焚消息 其他不是
					if(attachment != null && attachment != ""){
						isBurnAfterRead = attachment.isBurnAfterRead;
					}
					if(isBurnAfterRead == "1"){
						//阅后即焚 不立即相应服务器清除消息
						//alert("接收到火聊消息："+JSON.stringify(message));
						im.singleChatCallback(message);
					}else{
						//普通单聊
						//添加messageId到数组
						responseMessageIds.push(message.messageId);
						//alert("接收单聊消息："+JSON.stringify(message));
						im.singleChatCallback(message);
					}
                }
                //群聊
                else if (message.type === 3) {
					//添加messageId到数组
					responseMessageIds.push(message.messageId);
                    im.groupsChatCallback(message);
                }
                //离线消息
                else if (message.type === 10) {
					if(message.textMsg == null || message.textMsg == ""){return;}
					var msgJson =  JSON.parse(message.textMsg);

					if (msgJson) {
						$.each(msgJson, function (messageId, messageData) {
							var messageType = messageData.type;
							var messageFrom = messageData.from;
							if (messageType == "singleChat") {
								var attachment = messageData.attachment;
								var isBurnAfterRead = "2";//1为阅后即焚消息 其他不是
								if(attachment != null && attachment != ""){
									isBurnAfterRead = attachment.isBurnAfterRead;
								}
								if(isBurnAfterRead == "1"){
									//火聊
									//alert("接收到离线火聊消息："+JSON.stringify(messageData));
									var message = {textMsg : messageData.body,
										createTime:messageData.createTime,
										mediaType :messageData.mediaType,
										from : messageData.from,
										to:config.currentUser.imAccount,
										attachment:messageData.attachment,
										messageId:messageId
									};

									im.singleChatCallback(message);
								}else{
									//单聊
									//alert("接收离线单聊消息："+messageId+"->"+ JSON.stringify(messageData));
									//添加messageId到数组
									responseMessageIds.push(messageId);
									var message = {textMsg : messageData.body,
										createTime:messageData.createTime,
										mediaType :messageData.mediaType,
										from : messageData.from,
										to:config.currentUser.imAccount};

									im.singleChatCallback(message);
								}

							} else if (messageType == "groupChat") {
								//群聊
								//alert("接收离线群聊消息："+messageId+"->"+ JSON.stringify(messageData));
								//添加messageId到数组
								responseMessageIds.push(messageId);
								var message = {textMsg : messageData.body,
									createTime:messageData.createTime,
									mediaType :messageData.mediaType,
									from : messageData.from,
									groupId:messageData.groupId,
									to:config.currentUser.imAccount};
								im.groupsChatCallback(message);
							} else if (messageType == "addGroup") {
								//添加群组推送
								//重新加载工作群组
								getGroupsData();
								//添加messageId到数组
								responseMessageIds.push(messageId);
							} else if (messageType == "modifyGroup") {
								//修改群组推送
								//重新加载工作群组
								getGroupsData();
								//添加messageId到数组
								responseMessageIds.push(messageId);
							} else if(messageType == "delGroup"){
								//删除群组
								//重新加载工作群组
								getGroupsData();
								//添加messageId到数组
								responseMessageIds.push(messageId);
							}else if (messageFrom.indexOf("pushAdmin") != -1) {
								//alert("接收离线推送消息：" +messageId+" -> "+ JSON.stringify(messageData));
								if(messageData.type == "115"){
									//朋友验证消息
									var param = {messageId:messageId,friendCreateTime:messageData.friendCreateTime,friendIcon:messageData.friendIcon,friendImaccount:messageData.friendImaccount,friendId:messageData.friendId,friendName:messageData.friendName};
									addFriendApplyToXDPYmain(param);
								} else if(messageData.type == "117"){
									//更新通讯录推送
									getContactsData();
								} else if(messageData.type == "118" || messageData.type == "120"|| messageData.type == "121"|| messageData.type == "122"){
									//云镜OA-118=批审推送,120=抄送推送
									setApproveDot(messageData, messageId);
								} else if(messageData.type == "119"){
									//云镜OA-新的同事推送
									var param = {messageId:messageId,
											icon:messageData.icon,
											imAccount:messageData.imAccount,
											name:messageData.name,
											job:messageData.job,
											joinTime:messageData.joinTime};
									addManToXDTSmain(param);
								} else{
									//推送
									//pushMsgCallback.offlineCallback(messageId,messageData);
								}
							} else{
								//其他
								//添加messageId到数组
								responseMessageIds.push(messageId);
							}
						})
					}
				}
				//推送消息
                else if(message.type === 15){
					if(message.textMsg == null || message.textMsg == ""){return;}
					var msgJson =  JSON.parse(message.textMsg);
					if(msgJson.type == "115"){
						//朋友验证消息
						//var param = {messageId:message.messageId,companyId:msgJson.companyId,userId:msgJson.userId,selfId:msgJson.selfId,selfName:msgJson.selfName,imAccount:msgJson.hxAccount};
						var param = {messageId:message.messageId,friendCreateTime:msgJson.friendCreateTime,friendIcon:msgJson.friendIcon,friendImaccount:msgJson.friendImaccount,friendId:msgJson.friendId,friendName:msgJson.friendName};
						addFriendApplyToXDPYmain(param);
					} else if(msgJson.type == "117" || msgJson.type == "116"){
						//更新通讯录推送
//						$("div[id^='sequence_']").remove();
//						$("a[id^='item_TXL_']").remove();
						getContactsData();
					} else if(msgJson.type == "delGroup"){
						//删除群组
						//重新加载工作群组
						getGroupsData();
						//添加messageId到数组
						responseMessageIds.push(message.messageId);
						tips.success("群主解散了[" + msgJson.groupName + "]群");
						deleteItemOnclick(event, "item_DDX_" + msgJson.groupId);
					} else if(msgJson.type == "118" || msgJson.type == "120" ||  msgJson.type == "121"||  msgJson.type == "122"){
						//云镜OA-批审推送
						setApproveDot(msgJson, message.messageId);
					} else if(msgJson.type == "119"){
						//云镜OA-新的同事推送
						var param = {messageId:message.messageId,
							icon:msgJson.icon,
							imAccount:msgJson.imAccount,
							name:msgJson.name,
							job:msgJson.job,
							joinTime:msgJson.joinTime};
						addManToXDTSmain(param);
					}  else{
						//alert("接收推送消息："+JSON.stringify(message));
						//pushMsgCallback.immediatelyCallback(message);
					}
				}
				//群组操作的推送
				else if(message.type === 17){
					//添加messageId到数组
					responseMessageIds.push(message.messageId);
					//重新加载工作群组
					getGroupsData();
				}
            }
			//alert(JSON.stringify(responseMessageIds));
			if(responseMessageIds.length > 0){
				//发送应答消息（清空离线消息）
				im.sendResponseMessage(responseMessageIds);
			}
        };
       
        im.socket.onclose = function (event) {
        	if(wait_onclose>10){
        		//别人踢自己下线
        		//logout(); //退出
        	}
        	tips.warn("抱歉，即时通讯离线，当前页面暂时无法聊天!");
        };

        
        im.socket.onerror = function (event) {
            this.close();
            tips.error("连接聊天服务器失败!");
        };
    }
    else {
    	tips.error("抱歉，您的浏览器不支持WebSocket协议!");
    }
};

var tips = {
	"success": function(msg) {
		createTips.message(msg, createTips.ok);
	},
	"warn": function(msg) {
		createTips.message(msg, createTips.warn);
	},
	"error": function(msg) {
		createTips.message(msg, createTips.warn);
		//createTips.message(msg, createTips.error);
	}
}
//计时器
var wait_onclose = 1;
waitOnclose();
function waitOnclose(){
	wait_onclose +=1;
	setTimeout(function() {
		waitOnclose();
	}, 1000);
}

/*
发送分享消息
*/
function imShareSend(message) {
	message['textMsg'] = "分享";
	message['mediaType'] = 1;
	message['socketType']= 1;
	im.sendChatMessage(message);
	im.addSendTextToChatContent(message);
}

/*
发送单聊消息
*/

im.sendChatMessage = function (message) {
        if (!window.WebSocket) {
            return;
        }
        if (im.socket.readyState == WebSocket.OPEN) {

			var rgExp = /\[[^\]]+\]/g;
			if(message.mediaType == 1){
				var arr = message.textMsg.match(rgExp);
				for (i in arr)
				{
					var str = arr[i].toString().replace("[","").replace("]","");
					var emoji = emojisMap[str];
					if(emoji){
						message.textMsg = message.textMsg.replace(arr[i].toString(),emoji);
					}
				}
			}
			var msg = {
					socketType : 1,
					from : message.from,
					mediaType : message.mediaType,
					to : message.to,
					textMsg : message.textMsg,
					attachment : {isBurnAfterRead : message.isBurnAfterRead, burnAfterReadTime : message.burnAfterReadTime}
			};
			if(message.attachment != undefined) {
				//分享
				var shareDic = JSON.stringify(message.attachment.shareDic);
				msg.attachment['shareDic'] = shareDic;
			}
            var jsonStr = JSON.stringify(msg);
            im.socket.send(jsonStr);

			var param = {};

			param.imAccount = message.to;
			param.name = getName(message.to);
			param.icon = getIcon(message.to);
			param.date = dateFormat(new Date(),"yyyy-MM-dd hh:mm:ss");
			param.unreadQuantity = 0;

            //添加到叮当享列表
    		if($("#item_DDX_"+message.to).html()){
				var txlName = $("#item_DDX_"+message.to).find("div[class='ddx_middle1nr1']").text();
				if(txlName) param.name = txlName;
				var imgPath = $("#item_DDX_"+message.to).find("div[class='ddx_middle1tp']").find("img").attr("src");
				if(imgPath) param.icon = imgPath;
    			//删除已有的提示
    			$("#item_DDX_"+message.to).remove();
    		}
    		//添加提示到叮当享列表
    		var promptMessage = '';
    		if(message.mediaType == 1){
    			promptMessage = message.textMsg;
    		}else if(message.mediaType == 2){
    			promptMessage = "[图片]";
    		}

			param.message = promptMessage;
    		addContentToDDXmain(param,"chat");
    		
        }
        else {
        	tips.error("WebSocket连接没有建立成功!");
        }
};


/*
发送群组消息
*/
im.sendGroupsMessage = function (message) {
        if (!window.WebSocket) {
            return;
        }
        if (im.socket.readyState == WebSocket.OPEN) {

			var rgExp = /\[[^\]]+\]/g;
			if(message.mediaType == 1){
				var arr = message.textMsg.match(rgExp);
				for (i in arr)
				{
					var str = arr[i].toString().replace("[","").replace("]","");
					var emoji = emojisMap[str];
					if(emoji){
						message.textMsg = message.textMsg.replace(arr[i].toString(),emoji);
					}
				}
			}

            var msg = {
                socketType : 1,
                from : message.from,
                mediaType : message.mediaType,
                type : 2,
                groupId : message.to,
                textMsg : message.textMsg
            };
            //node.imwrite.val('').focus();
            var jsonStr = JSON.stringify(msg);
            im.socket.send(jsonStr);
            //alert("sendGroups:"+jsonStr);
            
           //添加到叮当享列表
    		if($("#item_DDX_"+message.to).html()){
    			//删除已有的提示
    			$("#item_DDX_"+message.to).remove();
    		}
    		//添加提示到叮当享列表
    		var promptMessage = '';
    		if(message.mediaType == 1){
    			promptMessage = message.textMsg;
    		}else if(message.mediaType == 2){
    			promptMessage = "[图片]";
    		}
    		var param = {};
    		param.groupsId = message.to,
    		param.name = getGroupsName(message.to),
    		param.userName = config.currentUser.name,
    		param.message = promptMessage,
    		param.icon = getGroupsIcon(message.to),
    		param.date = dateFormat(new Date(),"yyyy-MM-dd hh:mm:ss"),
    		param.unreadQuantity = 0;
    		addContentToDDXmain(param,"groups");
    		
        }
        else {
            tips.error("WebSocket连接没有建立成功!");
        }
};

/*
发送离线请求消息
*/
im.sendOfflientRequestMessage = function () {
    if (!window.WebSocket) {
        return;
    }
    if (im.socket.readyState == WebSocket.OPEN) {
        var msg = {
            from: config.currentUser.imAccount,
            type: 9
        };
        var jsonStr = JSON.stringify(msg);
        //alert("发送离线请求消息："+jsonStr);
        im.socket.send(jsonStr);
    }
};

/*
发送应答响应消息
*/
im.sendResponseMessage = function (textMsg) {
    if (!window.WebSocket) {
        return;
    }
    if (im.socket.readyState == WebSocket.OPEN) {
        var msg = {
            from: config.currentUser.imAccount,
            type: 13,
            textMsg: textMsg,
        };
        var jsonStr = JSON.stringify(msg);
		//alert("发送应答消息："+jsonStr);
        im.socket.send(jsonStr);
    }
};

/*
单聊回调方法
*/
im.singleChatCallback = function (message) {
	//alert("单聊回调->"+JSON.stringify(message));

	if(message.from && message.to){
		//过滤响应消息		
		var chatMainId = "chat_"+message.from;	
		var unreadQuantity = 1;
		if($("#"+chatMainId).html()){			
			//如果当前聊天窗口已经打开不提示未读
			if($("#"+chatMainId).css("display") != "none"){
				unreadQuantity = 0;
			}
			//添加到聊天窗口
			var attachment = message.attachment;
			var isBurnAfterRead = "2";//1为阅后即焚消息 其他不是
			if(attachment != null && attachment != ""){
				isBurnAfterRead = attachment.isBurnAfterRead;
			}
			if(isBurnAfterRead == "1"){
				//阅后即焚消息
				if(message.mediaType == 1){
					addReceiveFireChatTextToChatContent(message);
				}else if(message.mediaType == 2){
					addReceiveFireChatImgToChatContent(message);
				}

			}else{
				if(message.mediaType == 1){
					addReceiveTextToChatContent(message);
				}else if(message.mediaType == 2){
					addReceiveImgToChatContent(message);
				}
			}
			
		}else{
			//没有聊天窗口先保存数据
			if(acceptedData[chatMainId]){
				acceptedData[chatMainId].push(message);
			}else{
				var data = [];
				data.push(message);
				acceptedData[chatMainId] = data;
			}
		}
		//alert("end"+JSON.stringify(acceptedData));
		
		if($("#item_DDX_"+message.from).html()){
			//获取原有的数量
			unreadQuantity += getDDXcontentUnreadQuantity("item_DDX_"+message.from);
			//删除已有的提示
			$("#item_DDX_"+message.from).remove();
		}
		
		//添加提示到叮当享列表
		var promptMessage = '';

		var attachment = message.attachment;
		var isBurnAfterRead = "2";//1为阅后即焚消息 其他不是
		if(attachment != null && attachment != ""){
			isBurnAfterRead = attachment.isBurnAfterRead;
		}
		if(isBurnAfterRead == "1"){
			//阅后即焚消息提示
			if(message.mediaType == 1){
				promptMessage = "您有一条阅后即焚消息";
			}else if(message.mediaType == 2){
				promptMessage = "您有一条阅后即焚消息";
			}
		}else{
			//普通消息显示消息内容
			if(message.mediaType == 1){
				promptMessage = message.textMsg;
			}else if(message.mediaType == 2){
				promptMessage = "[图片]";
			}
		}
		
		var param = {};
		param.imAccount = message.from,
		param.name = getName(message.from) != "" ? getName(message.from) : message.from,
		param.message = promptMessage,
		param.icon = getIcon(message.from),
		param.date = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss"),
		param.unreadQuantity = unreadQuantity;
		//alert(JSON.stringify(param));
		addContentToDDXmain(param,"chat");
	
	}
}

/*                                                                                              
群聊回调方法
*/
im.groupsChatCallback = function (message) {
	if(message.groupId && message.from && message.to){
		//过滤响应消息		
		var groupsMainId = "groups_"+message.groupId;	
		var unreadQuantity = 1;
		if($("#"+groupsMainId).html()){			
			//如果当前聊天窗口已经打开不提示未读
			if($("#"+groupsMainId).css("display") != "none"){
				unreadQuantity = 0;
			}
			//添加到聊天窗口
			if(message.mediaType == 1){
				addReceiveTextToGroupsContent(message);				
			}else if(message.mediaType == 2){
				addReceiveImgToGroupsContent(message);
			}
			
		}else{
			//没有聊天窗口先保存数据
			if(acceptedData[groupsMainId]){
				acceptedData[groupsMainId].push(message);
			}else{
				var data = [];
				data.push(message);
				acceptedData[groupsMainId] = data;
			}
		}
		
		if($("#item_DDX_"+message.groupId).html()){
			//获取原有的数量
			unreadQuantity += getDDXcontentUnreadQuantity("item_DDX_"+message.groupId);
			//删除已有的提示
			$("#item_DDX_"+message.groupId).remove();
		}
		
		//添加提示到叮当享列表
		var promptMessage = '';
		if(message.mediaType == 1){
			promptMessage = message.textMsg;
		}else if(message.mediaType == 2){
			promptMessage = "[图片]";
		}

		var param = {};
		param.groupsId = message.groupId,
        param.name = getGroupsName(message.groupId);
        if(param.name == message.groupId) {
        	getGroupsData();
        	param.name = getGroupsName(message.groupId);
        }
        param.userName = getName(message.from) != "" ? getName(message.from) : message.from,
        param.message = promptMessage,
        param.icon = getGroupsIcon(message.groupId),
        param.date = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss"),
        param.unreadQuantity = unreadQuantity;
		addContentToDDXmain(param,"groups");
	}
}

/*
离线回调方法
*/
im.offlineCallback = function (message) {
	//alert("离线回调->"+JSON.stringify(message));
}

/**将文本消息中的emoji编码转换为图片**/
var emojiConvert = function(text){
	var convertedText = "";
	config.jsonPost(projectName + "/DDX/ajaxConvert.htm", {"text":text}, function(datas){
		if(datas.status === 200){
			convertedText =  datas.convertedText;
		} else {
			convertedText = text;
			tips.error(datas.status+":emoji表情转换失败!");
		}
	}, function(e){
		tips.error("emoji表情转换失败!");
});
	return convertedText;
}

/**添加时间到聊天窗口中间区域**/
im.addDate = function(dateStr,contentId){
	//dateStr时间字符串，contentId聊天窗口中间区域ID
	var date = dateHandle(dateStr);
	//获取该聊天窗口中所有时间DIV	
	var thisContentAllDateDiv = $("#"+contentId).find("div[class='message-date']");
	var flag = 0;
	thisContentAllDateDiv.each(function(){
		if($(this).text() === date){
			flag = 1;
		}
	})
	if(flag == 0){
		//不存在相同的时间则添加
		var dateTempHtml = '<div style="width:210px;height:30px;float:left;margin-bottom:0px;margin-left:210px;">'
	        			+'<div class="message-date">'+date+'</div>'
	        			+'</div>';
		$("#"+contentId).append(dateTempHtml);
	}	
}

/**显示火聊消息**/
var showFireChatMsg = function(e,textMsg,messageId){
	//发送应答消息
	var responseArr = [messageId];
	im.sendResponseMessage(responseArr);
	//去掉点击事件
		$(e).attr("onclick","");
		$(e).css({"cursor":""});
	//显示信息
		var textMsg = emojiConvert(textMsg);
		$(e).html(textMsg);
	//设置倒计时
	var countDownSpan = $(e).parent().parent().parent().find("span[name='countDown']");
	var second = 10;
	var countDown = setInterval(function(){
		countDownSpan.html(second);
		if(second == 0){
			clearInterval(countDown);
			//如果消息上有时间 清除
			/*var preElement = $(e).parent().parent().parent().parent().prev();
			if(preElement&&preElement.find("div[class='message-date']").text() != null){
				preElement.remove();
			}*/
			//清除消息
			$(e).parent().parent().parent().parent().remove();
			return;
		}
		second--;
	}, 1000);
}

/**显示火聊img消息**/
var showFireChatImg = function(e,imgPath,messageId){
	//发送应答消息
	var responseArr = [messageId];
	im.sendResponseMessage(responseArr);
	//alert(JSON.stringify(responseArr));
	//显示img
	$(e).attr("src",imgPath);
	//dian ji fang da
	$(e).attr("onclick","enlargeImage(\'"+imgPath+"\')");
	//设置倒计时
	var countDownSpan = $(e).parent().parent().parent().parent().find("span[name='countDown']");
	var second = 10;
	var countDown = setInterval(function(){
		countDownSpan.html(second);
		second--;
		if(second == 0){
			clearInterval(countDown);
			//如果消息上有时间 清除
			/*var preElement = $(e)..parent().parent().parent().parent().parent().prev();
			 if(preElement&&preElement.find("div[class='message-date']").text() != null){
			 preElement.remove();
			 }*/
			//清除消息
			$(e).parent().parent().parent().parent().parent().remove();
			return;
		}
	}, 1000);
}

/**发送文本消息添加到单聊窗口**/
im.addSendTextToChatContent = function(message){
	//定位单聊窗口

	//emoji表情转换
	var textMsg = emojiConvert(message.textMsg);
	
	var chatMain = $("#chat_"+message.to);
	if(!chatMain.html()) {
		if(typeof(message.attachment) != "undefined" && typeof(message.attachment.shareDic) != "undefined"){
			changeToChatPage("main_TXL", message.to, message.toName);
		}
		chatMain = $("#chat_"+message.to);
	}
	if(chatMain.html()){
		//添加时间
		var nowDate = new Date();
		var nowDateStr = getCurrentDate(nowDate);
		im.addDate(nowDateStr,"chatContent_"+message.to);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.to);
		//alert(message.textMsg);
		//发送单聊文本消息模板
		var tempHtml;
		if(typeof(message.attachment) != "undefined" && typeof(message.attachment.shareDic) != "undefined"){
			//分享
			var share = message.attachment.shareDic;
			 tempHtml = '<div style="width:500px;">'
				+'<div class="chat-message" style="float:right;">'
				+'<img class="message-avatar" style="float:right;margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">	'
				+'<div class="message-content" style="margin-right:55px;">'	   
				+'<div style="text-align:right;">'
				+'</div>'
				+'<div class="message" style="padding:0px;">'
				+'<a href='+share.shareUrl+' target="_blank">'
				+'<div class="message-content" style="padding: 5px 5px 3px 5px;">'
				+'<div style="float: left;width: 16px;height: 16px;">'
				+'<img src='+share.shareIconUrl+' width="16px">'
				+'</div>'
				+'<div style="margin-left: 5px;font-size: 12px;color: #9c9c9c;">'+share.shareTitle+'</div>'
				+'<div style="background-color: #9ed486;padding: 2px 6px;">'+share.shareContent+'</div>'
				+'</a>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'<div style="clear:both;"></div>';
		}else{
		
			 tempHtml = '<div style="width:500px;">'
	        +'<div class="chat-message" style="float:right;">'
	        +'<img class="message-avatar" style=" float:right; margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">'			
	        +'<div class="message-content" style="margin-right:70px;">'
	        +'<div style="text-align:right;">'
	        +'</div>'
	        +'<div class="message" style="text-align: right;background-color: #b4e75b;">'
	        +'<span class="message-content">' + textMsg + '</span>'
	        +'</div>'
	        +'</div>'			
	        +'</div>'
	        +'</div>'
	        +'<div style="clear:both;"></div>';
		}
		//添加内容
		chatContent.append(tempHtml);	
				
	}
	
}

/**发送火聊文本消息添加到单聊窗口**/
im.addSendFireChatTextToChatContent = function(message){
	//定位单聊窗口

	//emoji表情转换
	var textMsg = emojiConvert(message.textMsg);

	var chatMain = $("#chat_"+message.to);
	if(chatMain.html()){
		//添加时间
		var nowDate = new Date();
		var nowDateStr = getCurrentDate(nowDate);
		im.addDate(nowDateStr,"chatContent_"+message.to);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.to);
		//alert(message.textMsg);
		//发送单聊文本消息模板
		var tempHtml = '<div style="width:500px;">'
			+'<div class="chat-message" style="float:right;">'
			+'<img class="message-avatar" style=" float:right; margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">'
			+'<div class="message-content" style="margin-right:70px;">'
			+'<div style="text-align:right;">'
			+'</div>'
			+'<div class="message" style="text-align: right;background-color: #FF2F2F;">'
			+'<span class="message-content">' + textMsg + '</span>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'<div style="clear:both;"></div>'
		//添加内容
		chatContent.append(tempHtml);

	}

}

/**发送图片消息添加到单聊窗口**/
im.addSendImgToChatContent = function(message){
	//定位单聊窗口
	var chatMain = $("#chat_"+message.to);
	if(chatMain.html()){		
		//添加时间
		var nowDate = new Date();
		var nowDateStr = getCurrentDate(nowDate);
		im.addDate(nowDateStr,"chatContent_"+message.to);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.to);
		//发送单聊图片消息模板
		var tempHtml = '<div style="width:500px;">'
        +'<div class="chat-message" style="float:right;">'
        +'<img class="message-avatar" style=" float:right; margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">'			
        +'<div class="message-content" style="margin-right:70px;">'
        +'<div style="text-align:right;">'
        +'</div>'
        +'<div class="message" style="text-align: right;padding:0px 0px;">'
        +'<span class="message-content"><img onclick="enlargeImage(\''+message.textMsg.remoteUrl+'\')" style="width:80px;height:80px;cursor:pointer;" src="'+message.textMsg.remoteUrl+'" alt=""></span>'
        +'</div>'
        +'</div>'			
        +'</div>'
        +'</div>'
        +'<div style="clear:both;"></div>'			
		//添加内容
		chatContent.append(tempHtml);	
					
	
	}
	
}

/**发送图片消息添加到单聊窗口**/
im.addSendFireChatImgToChatContent = function(message){
	//alert(JSON.stringify(message));
	//定位单聊窗口
	var chatMain = $("#chat_"+message.to);
	if(chatMain.html()){
		//添加时间
		var nowDate = new Date();
		var nowDateStr = getCurrentDate(nowDate);
		im.addDate(nowDateStr,"chatContent_"+message.to);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.to);
		//发送单聊图片消息模板
		var tempHtml = '<div style="width:500px;">'
			+'<div class="chat-message" style="float:right;">'
			+'<img class="message-avatar" style=" float:right; margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">'
			+'<div class="message-content" style="margin-right:70px;">'
			+'<div style="text-align:right;">'
			+'</div>'
			+'<div class="message" style="text-align: right;background-color: #FF2F2F;padding:0px 0px;">'
			+'<span class="message-content"><img onclick="enlargeImage(\''+message.textMsg.remoteUrl+'\')" style="width:80px;height:80px;cursor:pointer;" src="'+message.textMsg.remoteUrl+'" alt=""></span>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'<div style="clear:both;"></div>'
		//添加内容
		chatContent.append(tempHtml);


	}

}

/**接收文本消息添加到单聊窗口**/
var addReceiveTextToChatContent = function(message){
	//定位单聊窗口
	var chatMain = $("#chat_"+message.from);

	//emoji表情转换
	var textMsg = emojiConvert(message.textMsg);

	if(chatMain.html()){			
	
		//添加时间
		var dateStr = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss");
		im.addDate(dateStr,"chatContent_"+message.from);//77777777777777
		//定位中间区域
		var chatContent = $("#chatContent_"+message.from);
		
		var icon = defaultSingleIcon;
		//从通讯录中获取用户对应得头像
		if(message.from){
			imgPath = getIcon(message.from);
			if(imgPath) icon = imgPath;
		}
		
		var tempHtml;
		if(typeof(message.attachment) != "undefined" && typeof(message.attachment.shareDic) != "undefined"){
			var share = JSON.parse(message.attachment.shareDic);
			 tempHtml = '<div style="width:500px;">'
				+'<div class="chat-message" style="float:left;">'
				+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">	'
				+'<div class="message-content" style="margin-left:55px;">'	   
				+'<div style="text-align:left;">'
				+'</div>'
				+'<div class="message" style="padding:0px;">'
				+'<a href='+share.shareUrl+' target="_blank">'
				+'<div class="message-content" style="padding: 5px 5px 3px 5px;">'
				+'<div style="float: left;width: 16px;height: 16px;">'
				+'<img src='+share.shareIconUrl+' width="16px">'
				+'</div>'
				+'<div style="margin-left: 5px;font-size: 12px;color: #9c9c9c;">'+share.shareTitle+'</div>'
				+'<div style="background-color: #9ed486;padding: 2px 6px;">'+share.shareContent+'</div>'
				+'</a>'
				+'</div>'	
				+'</div>'
				+'</div>'
				+'</div>'
				+'<div style="clear:both;"></div>';
		}else{
			 tempHtml = '<div style="width:500px;">'
				+'<div class="chat-message" style="float:left;">'
				+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">	'
				+'<div class="message-content" style="margin-left:55px;">'	   
				+'<div style="text-align:left;">'
				+'</div>'
				+'<div class="message" style="text-align: left;">'
				+'<span class="message-content">'+textMsg+'</span>'
				+'</div>'	
				+'</div>'
				+'</div>'
				+'</div>'
				+'<div style="clear:both;"></div>';
		}
		
		
		//添加内容
		chatContent.append(tempHtml);
		/**设置中间滚动条位置保持在底部**/
		$("#chatContent_"+message.from).scrollTop($("#chatContent_"+message.from)[0].scrollHeight);
	}
}

/**接收火聊文本消息添加到单聊窗口**/
var addReceiveFireChatTextToChatContent = function(message){
	//alert(JSON.stringify(message));
	//定位单聊窗口
	var chatMain = $("#chat_"+message.from);

	//emoji表情转换
	//var textMsg = emojiConvert(message.textMsg);

	if(chatMain.html()){

		//添加时间
		var dateStr = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss");
		im.addDate(dateStr,"chatContent_"+message.from);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.from);

		var icon = defaultSingleIcon;
		//从通讯录中获取用户对应得头像
		if(message.from){
			imgPath = getIcon(message.from);
			if(imgPath) icon = imgPath;
		}
		var tempHtml = '<div style="width:500px;">'

			+'<div class="chat-message" style="float:left;">'
			+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">	'
			+'<div class="message-content" style="margin-left:55px;">'
			+'<div style="text-align:left;">'
			+'</div>'
			+'<div class="message" style="text-align: left;">'
			+'<span class="message-content" style="cursor: pointer;" onclick="showFireChatMsg(this,\''+message.textMsg+'\',\''+message.messageId+'\')"><img  src="'+projectName+"/chat/images/talk/t1.png"+'" style="width:60px;height:20px;" alt=""></span>'
			+'</div>'
			+'</div>'
			+'<span style="margin-left: -10px;color: #FF0000;font-size: 12px;float: right;" name="countDown"></span>'
			+'</div>'

			+'</div>'
			+'<div style="clear:both;"></div>';
		//添加内容
		chatContent.append(tempHtml);
		/**设置中间滚动条位置保持在底部**/
		$("#chatContent_"+message.from).scrollTop($("#chatContent_"+message.from)[0].scrollHeight);
	}
}

/**接收图片消息添加到单聊窗口**/
var addReceiveImgToChatContent = function(message){	
	
	//定位单聊窗口
	var chatMain = $("#chat_"+message.from);
	
	if(chatMain.html()){			
	
		//添加时间
		var dateStr = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss");
		im.addDate(dateStr,"chatContent_"+message.from);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.from);
		
		var icon = defaultSingleIcon;
		//从通讯录中获取用户对应得头像
		if(message.from){
			imgPath = getIcon(message.from);
			if(imgPath) icon = imgPath;
		}

		var tempHtml = '<div style="width:500px;">'
		+'<div class="chat-message" style="float:left;">'
		+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">'			
		+'<div class="message-content" style="margin-left:55px;">'			   
		+'<div style="text-align:left;">'
		+'</div>'
		+'<div class="message" style="text-align: left;padding:0px 0px 0px 10px;background-color: #f7f4f4;border: 0px;">'
		+'<span class="message-content"><img onclick="enlargeImage(\''+JSON.parse(message.textMsg).remoteUrl+'\')" src="'+JSON.parse(message.textMsg).remoteUrl+'" style="width:80px;height:80px;cursor:pointer;" alt=""></span>'
		+'</div>'		
		+'</div>'
		+'</div>'
		+'</div>'
		+'<div style="clear:both;"></div>';
		
		//添加内容
		chatContent.append(tempHtml);
		/**设置中间滚动条位置保持在底部**/
		$("#chatContent_"+message.from).scrollTop($("#chatContent_"+message.from)[0].scrollHeight);
	}
}



/**接收图片消息添加到火聊窗口**/
var addReceiveFireChatImgToChatContent = function(message){

	//定位单聊窗口
	var chatMain = $("#chat_"+message.from);

	if(chatMain.html()){

		//添加时间
		var dateStr = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss");
		im.addDate(dateStr,"chatContent_"+message.from);
		//定位中间区域
		var chatContent = $("#chatContent_"+message.from);

		var icon = defaultSingleIcon;
		//从通讯录中获取用户对应得头像
		if(message.from){
			imgPath = getIcon(message.from);
			if(imgPath) icon = imgPath;
		}

		var tempHtml = '<div style="width:500px;">'
			+'<div class="chat-message" style="float:left;">'
			+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">'
			+'<div class="message-content" style="margin-left:55px;">'
			+'<div style="text-align:left;">'
			+'</div>'
			+'<div class="message" style="text-align: left;">'
			+'<span class="message-content"><img onclick="showFireChatImg(this,\''+JSON.parse(message.textMsg).remoteUrl+'\',\''+message.messageId+'\')" src="'+projectName+"/chat/images/talk/t2.png"+'" style="width:80px;height:80px;cursor:pointer;" alt=""></span>'
			+'</div>'
			+'</div>'
			+'<span style="margin-left: -10px;color: #FF0000;font-size: 12px;float: right;" name="countDown"></span>'
			+'</div>'
			+'</div>'
			+'<div style="clear:both;"></div>';

		//添加内容
		chatContent.append(tempHtml);
		/**设置中间滚动条位置保持在底部**/
		$("#chatContent_"+message.from).scrollTop($("#chatContent_"+message.from)[0].scrollHeight);
	}
}


/**发送文本消息添加到群聊窗口**/
im.addSendTextToGroupsContent = function(message){
	//alert("发送文本消息添加到群聊窗口");

	//emoji表情转换
	var textMsg = emojiConvert(message.textMsg);

	//定位群聊窗口
	var groupsMain = $("#groups_"+message.to);
	if(groupsMain.html()){		
		//添加时间
		var nowDate = new Date();
		var nowDateStr = getCurrentDate(nowDate);
		im.addDate(nowDateStr,"groupsContent_"+message.to);
		//定位中间区域
		var groupsContent = $("#groupsContent_"+message.to);
		//alert(message.textMsg);
		//发送单聊文本消息模板
		
		var tempHtml = '<div style="width:500px;">'
        +'<div class="chat-message" style="float:right;">'
        +'<img class="message-avatar" style=" float:right; margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">'			
        +'<div class="message-content" style="margin-right:70px;">'
       /* +'<div style="text-align:right;">'
        +'<c>'
        +'<a class="message-author" style="color: rgb(155, 155, 155);">'+config.currentUser.name+'</a>'
        +'</div>'*/ 
        +'<div class="message" style="text-align: right;background-color: #b4e75b;">'
        +'<span class="message-content">'+textMsg+'</span>'
        +'</div>'
        +'</div>'			
        +'</div>'
        +'</div>'
        +'<div style="clear:both;"></div>'			
		//添加内容
		groupsContent.append(tempHtml);					
	}	
}


/**发送图片消息添加到群聊窗口**/
im.addSendImgToGroupsContent = function(message){
	//alert("addSendImgToGroupsContent");
	//定位群聊窗口
	var groupsMain = $("#groups_"+message.to);
	if(groupsMain.html()){		
		//添加时间
		var nowDate = new Date();
		var nowDateStr = getCurrentDate(nowDate);
		im.addDate(nowDateStr,"groupsContent_"+message.to);
		//定位中间区域
		var groupsContent = $("#groupsContent_"+message.to);
		//发送群聊图片消息模板
		var tempHtml = '<div style="width:500px;">'
        +'<div class="chat-message" style="float:right;">'
        +'<img class="message-avatar" style=" float:right; margin-left: 10px;" src="'+config.currentUser.icon+'" alt="">'			
        +'<div class="message-content" style="margin-right:70px;">'
        /*+'<div style="text-align:right;">'
        +'<a class="message-author" style="color: rgb(155, 155, 155);">'+config.currentUser.name+'</a>'
        +'</div>'*/
        +'<div class="message" style="text-align: right;padding:0px 0px;">'
        +'<span class="message-content"><img onclick="enlargeImage(\''+message.textMsg.remoteUrl+'\')" style="width:80px;height:80px;cursor:pointer;" src="'+message.textMsg.remoteUrl+'" alt=""></span>'
        +'</div>'
        +'</div>'			
        +'</div>'
        +'</div>'
        +'<div style="clear:both;"></div>'			
		//添加内容
		groupsContent.append(tempHtml);						
	}	
}

/**接收文本消息添加到群聊窗口**/
var addReceiveTextToGroupsContent = function(message){	
	//alert("addReceiveTextToGroupsContent->"+JSON.stringify(message))

	//emoji表情转换
	var textMsg = emojiConvert(message.textMsg);

	//定位群聊窗口
	var groupsMain = $("#groups_"+message.groupId);
	
	if(groupsMain.html()){			
	
		//添加时间
		var dateStr = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss");
		im.addDate(dateStr,"groupsContent_"+message.groupId);
		//定位中间区域
		var groupsContent = $("#groupsContent_"+message.groupId);
		//alert(groupsContent.html());
		var icon = defaultSingleIcon;
		//从通讯录中获取用户对应得头像
		if(message.from){
			imgPath = getIcon(message.from);
			if(imgPath) icon = imgPath;
		}
		
		 var tempHtml = '<div style="width:500px;">'
			+'<div class="chat-message" style="float:left;">'
			+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">	'
			+'<div class="message-content" style="margin-left:55px;">'	   
			+'<div style="text-align:left;">'
			+'<a class="message-author" style="color: rgb(155, 155, 155);">'+(getName(message.from) != "" ? getName(message.from) : message.from)+'</a>'
			+'</div>'
			+'<div class="message" style="text-align: left;">'
			+'<span class="message-content">'+textMsg+'</span>'
			+'</div>'	
			+'</div>'
			+'</div>'
			+'</div>'
			+'<div style="clear:both;"></div>';
		//添加内容
		groupsContent.append(tempHtml);
		/**设置中间滚动条位置保持在底部**/
		$("#groupsContent_"+message.groupId).scrollTop($("#groupsContent_"+message.groupId)[0].scrollHeight);
	}
}

/**接收图片消息添加到群聊窗口**/
var addReceiveImgToGroupsContent = function(message){	
	
	//定位单聊窗口
	var groupsMain = $("#groups_"+message.groupId);
	
	if(groupsMain.html()){			
	
		//添加时间
		var dateStr = dateFormat(new Date(message.createTime),"yyyy-MM-dd hh:mm:ss");
		im.addDate(dateStr,"groupsContent_"+message.groupId);
		//定位中间区域
		var groupsContent = $("#groupsContent_"+message.groupId);
		
		var icon = defaultSingleIcon;
		//从通讯录中获取用户对应得头像
		if(message.from){
			imgPath = getIcon(message.from);
			if(imgPath) icon = imgPath;
		}

		var tempHtml = '<div style="width:500px;">'
		+'<div class="chat-message" style="float:left;">'
		+'<img class="message-avatar" style=" float: left; margin-right: 10px;" src="'+icon+'" alt="">'			
		+'<div class="message-content" style="margin-left:55px; background-color:#F7F4F4">'			   
		+'<div style="text-align:left;">'
		+'<a class="message-author" style="color: rgb(155, 155, 155);">'+(getName(message.from) != "" ? getName(message.from) : message.from)+'</a>'
		+'</div>'
		+'<div class="message" style="text-align: left;padding:0px 0px 0px 10px;background-color: #f7f4f4;border: 0px;">'
		+'<span class="message-content"><img onclick="enlargeImage(\''+JSON.parse(message.textMsg).remoteUrl+'\')" src="'+JSON.parse(message.textMsg).remoteUrl+'" style="width:80px;height:80px;cursor:pointer;" alt=""></span>'
		+'</div>'		
		+'</div>'
		+'</div>'
		+'</div>'
		+'<div style="clear:both;"></div>';
		
		//添加内容
		groupsContent.append(tempHtml);
		/**设置中间滚动条位置保持在底部**/
		$("#groupsContent_"+message.groupId).scrollTop($("#groupsContent_"+message.groupId)[0].scrollHeight);
	}
}
/**
 * 
 * //音频
        else if (message.mediaType === 3) { 
            var textMsg = JSON.parse(message.textMsg);
            log.content = '<audio controls="controls" src="" id="' + textMsg.remoteUrl + '"/>'
        }
        **/

/**放大二维码图片**/
var enlargeEWMImage = function(){
	var img = $("#qrcode_hide canvas");
	$("#qrcode_hide_div").show();
	var param = {
		type: 1,
		area:['200px','200px'],
		shade: false, //不显示遮罩
		title: false, //不显示标题
		content: img,
		cancel: function(index){
			$("#qrcode_hide_div").hide();
			layer.close(index);
		}
	};
	if(only_im == 0) {
		param.offset = [($(window).height()+70-200)/2, ($(window).width()+221-200)/2];
	}
	layer.open(param);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
}
var initEWMPic = function() {
	var ewm_info = config.currentUser.id + "&" + config.currentUser.imAccount + "&" + config.currentUser.name + "&cx:injoy365.cn";
	$("#qrcode").qrcode({
		render: "canvas", //table方式
		width: 50, //宽度
		height:50, //高度
		text: ewm_info //任意内容
	});
	//qrcode_hide用来放大
	$("#qrcode_hide").qrcode({
		render: "canvas", //table方式
		width: 200, //宽度
		height:200, //高度
		text: ewm_info //任意内容
	});
}

/**放大图片**/
var enlargeImage = function(imgPath){
	layer.open({
		  type: 1,
		  area:['600px','400px'],
		  shade: false, //不显示遮罩
		  title: false, //不显示标题
		  content: '<img src="'+imgPath+'" style="width:100%;height:100%;" alt="">',
		  cancel: function(index){
		    layer.close(index);
		  }
		});
}

//选择图片
var selectImg = function(type,account){
	
    selectImgIframHtml = '<form action="' + projectName + "/DDX/uploadFile.htm" + '" method="post" enctype="multipart/form-data" id="uploadFileForm">'
              +'<input style="margin-left:20px; margin-top:15px; width:250px; height:25px" id="fileImg" type="file" name="file"/>'
              /*+'<input name="owner" type="hidden" value="' + config.currentUser.name + '"/>'*/
              +'<div id="imErrorMsg" style="color:red; text-align:center; display:none; font-size:10px; margin-top:5px"></div>'
              +'</form>';

		$('#dialogBox').dialogBox({
			hasClose: true,
			hasBtn: true,
			confirmValue: '发送',
			confirm: function(){
				  var file = document.getElementById('fileImg');
		            if(!file.files[0]){
		            	//$('#imErrorMsg').text("请选择需要发送的文件").show();
		            	//alert(请选择需要发送的文件后);
		            	layerMsg("请选择需要发送的图片", 200);
		                return;
		            }
		            var fileName = $.trim(file.files[0].name);
		            var suffix = fileName.substr(fileName.lastIndexOf('.') + 1).toLowerCase();
		            if (!(suffix in config.pictureType)) {
		                //$('#imErrorMsg').text("不支持此图片类型:" + suffix).show();
		            	layerMsg("不支持此图片类型："+suffix, 200);
		                return;
		            }
		            var param = {icon: 16,time:10000};
		            if(only_im == 0) {
		        		param.offset = [($(window).height()+60-50)/2, ($(window).width()+200-150)/2];
		        	}
		            var msgIndex = layer.msg('正在发送图片', param);

		            $('#uploadFileForm').ajaxSubmit({
		                success : function (data) {
		                    if (data.status === 200) {
		                    	textMsg = {remoteUrl : data.filePath, localUrl:'', name : '', length : 0, size : data.fileSize};
		                   	
		                    	if(type == "chat0"){
		                    		/**发送单聊消息**/
		                    		var message = {to : account,
			                    			from : config.currentUser.imAccount, 
			                    			textMsg : textMsg,
			                    			mediaType : 2};

		                    		im.sendChatMessage(message);
		                    		
		                    		//聊天窗口展示
		                    		im.addSendImgToChatContent(message);
		                    		/**设置中间滚动条位置保持在底部**/
		                    		$("#chatContent_"+account).scrollTop($("#chatContent_"+account)[0].scrollHeight);
		                    	}else if(type == "chat1"){
									/**发送火聊消息**/
									var message = {to : account,
										from : config.currentUser.imAccount,
										textMsg : textMsg,
										mediaType : 2,
										isBurnAfterRead : "1",
										burnAfterReadTime : "10"
									};

									im.sendChatMessage(message);

									//聊天窗口展示
									im.addSendFireChatImgToChatContent(message);
									/**设置中间滚动条位置保持在底部**/
									$("#chatContent_"+account).scrollTop($("#chatContent_"+account)[0].scrollHeight);
								}else if(type == "groups"){
		                    		//alert("群组发送图片");
		                    		/**发送群聊消息**/
		                    		var message = {to : account,
			                    			from : config.currentUser.imAccount, 
			                    			textMsg : textMsg,
			                    			mediaType : 2};

		                    		im.sendGroupsMessage(message);
		                    		
		                    		//聊天窗口展示
		                    		im.addSendImgToGroupsContent(message);
		                    		/**设置中间滚动条位置保持在底部**/
		                    		$("#groupsContent_"+account).scrollTop($("#groupsContent_"+account)[0].scrollHeight);
		                    	}                   	
		                    }
		                    
		                    layer.close(msgIndex);
		                }
		            });

		            
			},
			cancelValue: '取消',
			title: '',
			content: selectImgIframHtml
		});


};

//普通单聊和阅后即焚切换
var switchChatType = function(currDivId){
	if(currDivId){
		var imAccount = currDivId.replace("chatBottom0_","").replace("chatBottom1_","");
		if(currDivId.indexOf("chatBottom0_") != -1){
			$("#chatBottom0_"+imAccount).hide();
			$("#chatBottom1_"+imAccount).show();
			$("#chatTextarea1_"+imAccount).focus();
		}else if(currDivId.indexOf("chatBottom1_") != -1){
			$("#chatBottom1_"+imAccount).hide();
			$("#chatBottom0_"+imAccount).show();
			$("#chatTextarea0_"+imAccount).focus();
		}
	}
}

//获取头像
var getIcon = function(imAccount){
	var icon = defaultSingleIcon;
	//从通讯录中获取用户对应得头像
	var imgPath = $("#item_TXL_"+imAccount).find("div[class='ddx_middle1tp']").find("img").attr("src");
	if(imgPath) icon = imgPath;
	//从超享团队中获取用户对应得头像
	var imgPath2 = $("#item_CXTD_"+imAccount).find("div[class='ddx_middle1tp']").find("img").attr("src");
	if(imgPath2) icon = imgPath2;
	return icon;
}
//获取姓名
var getName = function(imAccount){
	var name = imAccount;
	//从通讯录中获取用户对应得头像
	var txlName = $("#item_TXL_"+imAccount).find("div[class='ddx_middle1nr1']").text();
	if(txlName) name = txlName;
	//从超享团队中获取用户对应得头像
	var txlName2 = $("#item_CXTD_"+imAccount).find("div[class='ddx_middle1nr1']").text();
	if(txlName2) name = txlName2;
	return name;
}

//获取群组头像
var getGroupsIcon = function(groupsId){
	var icon = defaultGroupsIcon;
	//从群组列表中获取用户对应得头像
	var imgPath = $("#item_GZQL_"+groupsId).find("div[class='ddx_middle1tp']").find("img").attr("src");
	if(imgPath) icon = imgPath;
	return icon;
}
//获取群组名称
var getGroupsName = function(groupsId){
	var name = groupsId;
	//从群组列表中获取对应头像
	var qlName = $("#item_GZQL_"+groupsId).find("div[class='ddx_middle1nr1']").text();
	if(qlName) name = qlName;
	return name;
}

/**获取当前时间**/
var getCurrentDate = function(dateObj){
	var nowDate = dateObj;
	var nowDateStr = nowDate.getFullYear()+"/"+parseInt(nowDate.getMonth()+1)+"/"+nowDate.getDate()+" "+nowDate.getHours()+":"+nowDate.getMinutes()+":"+nowDate.getSeconds();
	return nowDateStr; 
}

/**表情框开关**/
var faceBoxSwitch = function(faceDivId){
	//return;
	$("#"+faceDivId).toggle();
}

var addEmojiToTextArea = function(e,faceDivId){
	//alert(e+" "+faceDivId);
	if(e == "1f611"){
		e = "1f610";
	}
	var chatTextAreaId = faceDivId.replace("face","chatTextarea");
	var chatTextAreaText = $("#"+chatTextAreaId).val();
	$("#"+chatTextAreaId).val(chatTextAreaText+"["+e+"]");
	var groupsTextAreaId = faceDivId.replace("face_","groupsTextarea_");
	var groupsTextAreaText = $("#"+groupsTextAreaId).val();
	$("#"+groupsTextAreaId).val(groupsTextAreaText+"["+e+"]");
	//触发表情框
	faceBoxSwitch(faceDivId);
}

/**表情框加载表情**/
var addFaceToBox = function(faceDivId){	
	var faceTable = $("#"+faceDivId).find("table");
	var tr1 = $("<tr></tr>");
	var tr2 = $("<tr></tr>");
	/*var tr3 = $("<tr></tr>");*/
	emojisMap;
	var i = 1;
	$.each(emojisMap,function(name,value){
		var facePath = projectName+"/chat/images/emoji/emoji_"+name+".png";
		var td = '<td style="border:solid 1px #f7f4f4;width:22px;height:22px;"><img style="cursor:pointer;width:22px;height:22px;" id="emoji_'+name+'" onclick="addEmojiToTextArea(\''+name+'\',\''+faceDivId+'\')" src="'+facePath+'"/></td>';
		if(i <= 18){
			tr1.append(td);
		}else if(i <= 36){
			tr2.append(td);
		}/*else if(i <= 54){
			tr3.append(td);
		}*/
		i++;
	})

	faceTable.append(tr1);
	faceTable.append(tr2);
	/*faceTable.append(tr3);*/

}



/**创建聊天页面**/
var createChatPage = function(currentMainId,imAccount,name){
	var chatPageHtml = '<div class="ddx_content" id="chat_'+imAccount+'" name="chatPage">'
		  					+'<div class="ddx_header">'
		  						+'<div class="ddx_header_fh" name="'+currentMainId+'_chat"'
		  						+' onclick="changeToUpOneLevelPage(\'chat_'+imAccount+'\')" ><img src="'+projectName+'/chat/images/ddx/fh.png"></div>'
		  						+'<div class="ddx_header_wz2" id="chatTitle_'+imAccount+'">'+name+'</div>'
		  					+'</div>'		  					
		  					
		  					+'<div class="ddx_middle ddx_middle_chat" id="chatContent_'+imAccount+'" style="background-color:#f7f4f4;height: '+ddx_middle_chat_H+'px;width:500px;padding:4px 15px;">'
			  					+'<div class="chat-discussion">'					  				
		  					
			  					+'</div>'  
			  				+'</div>'
			  				
			  				+'<div class="ddx_footer" style="height:90px;" id="chatBottom_'+imAccount+'">'

								+'<div id="chatBottom0_'+imAccount+'">'
									+'<div class="chat-message-form" style="float:left;">'
										+'<div class="form-group">'
										+'<textarea class="form-control message-input" style="font-size:12px;resize: none;overflow:auto;width: 395px;"'
											+'name="message" placeholder="输入消息内容，按回车键发送" id="chatTextarea0_'+imAccount+'"></textarea>'
										+'</div>'
									+'</div>'

									+'<div style="height:90px;width:135px; float:left;background-color:#fff;">'
									+'<div style="height:43px;width:135px;float:left; ">'
										+'<a href="#" onclick="faceBoxSwitch(\'face0_'+imAccount+'\')"><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/bq01.png"/></div></a>'
										+'<a href="#" onclick="selectImg(\'chat0\',\''+imAccount+'\')"><div style="width:40px;height:43px;float:left;margin-left: 38px;"><img src="'+projectName+'/chat/images/talk/tp01.png"/></div></a>'
										/*+'<a href="#" ><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/hl01.png"/></div></a>'*/
/*										+'<a href="#" onclick="switchChatType(\'chatBottom0_'+imAccount+'\')"><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/hl01.png"/></div></a>'
*/									+'</div>'
									+'<div style="height:50px;width:123px;;float:left;"><button type="button" style="width: 106px;" class="btn" id="chatSendBtn0_'+imAccount+'">发送</button></div>'
									+'</div>'

									+'<div style="width:529px;height:88px;position: relative;top:-88px; background-color:#fcfcfc;display:none;" id="face0_'+imAccount+'">'
									+'<table style="border:solid 1px #ccc;position: absolute;padding:11px;">'
									+'</table>'
									+'</div>'

								+'</div>'

								+'<div id="chatBottom1_'+imAccount+'" style="display: none;">'
									+'<div class="chat-message-form" style="float:left;">'
									+'<div class="form-group">'
									+'<textarea class="form-control message-input" style="font-size:12px;resize: none;overflow:auto;width: 395px;"'
									+'name="message" placeholder="输入消息内容，按回车键发送" id="chatTextarea1_'+imAccount+'"></textarea>'
									+'</div>'
									+'</div>'

									+'<div style="height:90px;width:135px; float:left;background-color:#fff;">'
									+'<div style="height:43px;width:135px;float:left; ">'
									+'<a href="#" onclick="faceBoxSwitch(\'face1_'+imAccount+'\')"><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/bq02.png"/></div></a>'
									+'<a href="#" onclick="selectImg(\'chat1\',\''+imAccount+'\')"><div style="width:40px;height:43px;float:left;margin-left: 38px;"><img src="'+projectName+'/chat/images/talk/tp02.png"/></div></a>'
								/*	+'<a href="#" onclick="switchChatType(\'chatBottom1_'+imAccount+'\')"><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/qx02.png"/></div></a>'*/
									/*+'<a href="#" ><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/qx02.png"/></div></a>'*/
									+'</div>'
									+'<div style="height:50px;width:123px;;float:left;"><button type="button" style="width: 106px;background-color: #FF2323;" class="btn" id="chatSendBtn1_'+imAccount+'">发送</button></div>'
									+'</div>'

									+'<div style="width:529px;height:88px;position: relative;top:-88px; background-color:#fcfcfc;display:none;" id="face1_'+imAccount+'">'
									+'<table style="border:solid 1px #ccc;position: absolute;padding:11px;">'
									+'</table>'
									+'</div>'

								+'</div>'


			  				+'</div>'
			  				
			  			+'</div>';

	
	//隐藏一级页面
	//$("#homePage").hide();
	if(currentMainId.indexOf("page_") != -1 || currentMainId.indexOf("groupsInfo_")!= -1){
		$("#"+currentMainId).hide();
	}else{
		//隐藏一级页面
		$("#homePage").hide();
	}
	//添加聊天页面到叮当享内容DIV中
	$("#mainDDX").append(chatPageHtml);
	
	/*聊天页面中间内容滚动条设置*/
	niceScroll($("#chatContent_"+imAccount));		
	/**加载表情**/
	addFaceToBox("face0_"+imAccount);
	addFaceToBox("face1_"+imAccount);

	/**发送按钮0绑定事件(普通)**/
	$("#chatSendBtn0_"+imAccount).click(function(){
		/**获取输入框内容**/
		var textareaId = $(this).attr("id").replace("chatSendBtn0_","chatTextarea0_");
		var textareaContent = $("#"+textareaId).val();
		//发送消息为空提示
		if(!textareaContent || $.trim(textareaContent) == ""){
			layer.tips('说点什么吧~', '#'+textareaId, {
				time: 1000,
				tips: [1, ''] //位置，颜色
			});
			/**清空输入框**/
			$("#"+textareaId).val("");
			/**输入框获取焦点**/
			$("#"+textareaId).focus();
			return false;
		}

		/**发送单聊消息**/
		var message = new Object();
		message.mediaType = 1;
		message.to = imAccount,
		message.textMsg = textareaContent,
		message.from = config.currentUser.imAccount;
		im.sendChatMessage(message);
		
		//展示到聊天窗口中间区域
        im.addSendTextToChatContent(message);
		
		/**清空输入框**/
		$("#"+textareaId).val("");
		/**输入框获取焦点**/
		$("#"+textareaId).focus();				
		/**设置中间滚动条位置保持在底部**/
		$("#chatContent_"+imAccount).scrollTop($("#chatContent_"+imAccount)[0].scrollHeight);
	});

	/**发送按钮1绑定事件(火聊)**/
	$("#chatSendBtn1_"+imAccount).click(function(){
		/**获取输入框内容**/
		var textareaId = $(this).attr("id").replace("chatSendBtn1_","chatTextarea1_");
		var textareaContent = $("#"+textareaId).val();
		//发送消息为空提示
		if(!textareaContent || $.trim(textareaContent) == ""){
			layer.tips('说点什么吧~', '#'+textareaId, {
				time: 1000,
				tips: [1, ''] //位置，颜色
			});
			/**清空输入框**/
			$("#"+textareaId).val("");
			/**输入框获取焦点**/
			$("#"+textareaId).focus();
			return false;
		}

		/**发送单聊消息**/
		var message = new Object();
		message.mediaType = 1;
		message.to = imAccount,
		message.textMsg = textareaContent,
		message.from = config.currentUser.imAccount;
		message.isBurnAfterRead = "1"; // 1 = 阅后即焚
		message.burnAfterReadTime = "10"; // n秒后焚毁
		im.sendChatMessage(message);

		//展示到聊天窗口中间区域
		im.addSendFireChatTextToChatContent(message);

		/**清空输入框**/
		$("#"+textareaId).val("");
		/**输入框获取焦点**/
		$("#"+textareaId).focus();
		/**设置中间滚动条位置保持在底部**/
		$("#chatContent_"+imAccount).scrollTop($("#chatContent_"+imAccount)[0].scrollHeight);
	});
		
	
	/**绑定回车键发送**/
	$("#chatTextarea0_"+imAccount).keydown(function(event){
		if(event.keyCode==13){ 
			event.preventDefault();
			//触发发送按钮
			$("#chatSendBtn0_"+imAccount).trigger("click");
		}
	});

	/**绑定回车键发送**/
	$("#chatTextarea1_"+imAccount).keydown(function(event){
		if(event.keyCode==13){
			event.preventDefault();
			//触发发送按钮
			$("#chatSendBtn1_"+imAccount).trigger("click");
		}
	});

	/**加载未读数据**/
	var thisChatMainId = "chat_"+imAccount;
	var data = acceptedData[thisChatMainId];

	if(data){
		$.each(data,function(key,message){
			//alert(message);
			if(message.from == config.currentUser.imAccount){
				//自己发送的消息
				if(message.mediaType == 1) {
					im.addSendTextToChatContent(message);
				}
			}else{
				//接收到的消息
				var attachment = message.attachment;
				var isBurnAfterRead = "2";//1为阅后即焚消息 其他不是
				if(attachment != null && attachment != ""){
					isBurnAfterRead = attachment.isBurnAfterRead;
				}
				if(isBurnAfterRead == "1"){
					//阅后即焚消息
					if(message.mediaType == 1){
						addReceiveFireChatTextToChatContent(message);
					}else if(message.mediaType == 2){
						addReceiveFireChatImgToChatContent(message);
					}

				}else{
					//普通消息
					if(message.mediaType == 1){
						addReceiveTextToChatContent(message);
					}else if(message.mediaType == 2){
						addReceiveImgToChatContent(message);
					}
				}
			}
		});
		//清空
		acceptedData[thisChatMainId] = [];
	}

}

/**切换到聊天页面**/
var changeToChatPage = function(currentMainId,imAccount,name){
	var chatPageId = "chat_"+imAccount;
	var chatPage = $("#"+chatPageId);	
	if(chatPage.html()){
		//已经存在聊天页面
		var returnBtnName = chatPage.find("div[class='ddx_header_fh']").attr("name");		
		var str = returnBtnName.replace("_chat","");
		var newReturnBtnName = returnBtnName.replace(str,currentMainId);
		//设置返回按钮name属性
		chatPage.find("div[class='ddx_header_fh']").attr("name",newReturnBtnName);

		if(currentMainId.indexOf("page_") != -1 || currentMainId.indexOf("groupsInfo_")!= -1){
			$("#"+currentMainId).hide();
		}else{
			//隐藏一级页面
			$("#homePage").hide();
		}
		//显示聊天页面
		chatPage.show();
	}else{
		//没有聊天页面 创建一个
		createChatPage(currentMainId,imAccount,name);
	}

	/*聊天页面中间内容滚动条设置*/
	niceScroll($("#chatContent_"+imAccount));

	/**输入框获取焦点**/
	$("#chatTextarea0_"+imAccount).focus();
	$("#chatTextarea1_"+imAccount).focus();
	
	//设置叮当享列表未读消息数量
	setDDXcontentUnreadQuantity("item_DDX_"+imAccount,0);
	//设置叮当享底部未读消息总和数量
	setMainUnreadQuantity("main_DDX",getDDXmainTotalUnreadQuantity());
	
	var kefu = kefuList[imAccount];//点击的是不是客服
	if(kefu != undefined) {
		var isSpec = $("#isSpec").val();
		var lable = "欢迎使用个云境产品，";
		if(isSpec == 1) {
			lable = "";
		}
		var message = {
				createTime: new Date().getTime(),
				from:imAccount,
				mediaType:1,
				textMsg:"你好，"+lable+"在线客服时间为8:30-17:30(周一至周六)，我们会尽快处理，请问有什么可以帮到您？",
				to:config.currentUser.imAccount
		}
		im.sendChatMessage(message);
		im.addSendTextToChatContent(message);
	}
}

/**创建群组聊天页面**/
var createGroupsChatPage = function(currentMainId,groupsId,groupsName){
	
	var groupsPageHtml = '<div class="ddx_content" id="groups_'+groupsId+'" name="groupsPage">'
							+'<div class="ddx_header">'
								+'<div class="ddx_header_fh" name="'+currentMainId+'_groups"'
								+' onclick="changeToUpOneLevelPage(\'groups_'+groupsId+'\')" ><img src="'+projectName+'/chat/images/ddx/fh.png"></div>'
								+'<div class="ddx_header_wz2" id="groupsTitle_'+groupsId+'">'+groupsName+'</div>'
								+'<div class="ddx_header_fdj"><a href="#" onclick="changeToGroupsInfoPage(\''+groupsId+'\',\''+groupsName+'\')"><img src="'+projectName+'/chat/images/ql.png"/></a></div>'
							+'</div>'
							
						+'<div class="ddx_middle ddx_middle_chat" id="groupsContent_'+groupsId+'" style="background-color:#f7f4f4;height: '+ddx_middle_chat_H+'px;width:500px;padding: 4px 15px;">'
		  					+'<div class="chat-discussion">'					  				
		  					
		  					+'</div>'  
		  				+'</div>'
		  				
		  				+'<div class="ddx_footer" style="height:90px;" id="groupsBottom_'+groupsId+'">'			  				
		  					+'<div class="chat-message-form" style="float:left;">'
		  						+'<div class="form-group">'
		  						+'<textarea class="form-control message-input" style="font-size:12px;resize: none;overflow:auto;"'
		  							+'name="message" placeholder="输入消息内容，按回车键发送" id="groupsTextarea_'+groupsId+'"></textarea>'
		  						+'</div>' 			
		  					+'</div>'
		  					
		  					+'<div style="height:90px;width:100px; float:left;background-color:#fff;">'
	  						+'<div style="height:43px;width:100px;float:left; ">'
	  							+'<a href="#" onclick="faceBoxSwitch(\'face_'+groupsId+'\')"><div style="width:40px;height:43px;float:left;margin-left:8px;margin-right:5px;"><img src="'+projectName+'/chat/images/talk/bq01.png"/></div></a>'
	  							+'<a href="#" onclick="selectImg(\'groups\',\''+groupsId+'\')"><div style="width:40px;height:43px;float:left;"><img src="'+projectName+'/chat/images/talk/tp01.png"/></div></a>'
	  						+'</div>'
	  						+'<div style="height:50px;width:100px;;float:left;"><button type="button" class="btn" id="groupsSendBtn_'+groupsId+'">发送</button></div>'
	  						+'</div>'

								+'<div style="width:529px;height:88px;position: relative;top:-88px; background-color:#fcfcfc;display:none;" id="face_'+groupsId+'">'
		  						+'<table style="border:solid 1px #ccc;position: absolute;padding:11px;">'
		  						+'</table>'
		  					+'</div>'			  					
		  				+'</div>'
		  				
		  			+'</div>';
	
	if(currentMainId.indexOf("main_") != -1){
		//隐藏一级页面
		$("#homePage").hide();
	}else{
		//隐藏当前页面
		$("#"+currentMainId).hide();
	}	
	//添加群组聊天页面到叮当享内容DIV中
	$("#mainDDX").append(groupsPageHtml);
	/*聊天页面中间内容滚动条设置*/
	niceScroll($("#groupsContent_"+groupsId));

	/**加载表情**/
	addFaceToBox("face_"+groupsId);
	
	/**发送按钮绑定事件**/
	$("#groupsSendBtn_"+groupsId).click(function(){
		/**获取输入框内容**/
		var textareaId = $(this).attr("id").replace("groupsSendBtn_","groupsTextarea_");
		var textareaContent = $("#"+textareaId).val();

		//发送消息为空提示
		if(!textareaContent || $.trim(textareaContent) == ""){
			layer.tips('说点什么吧~', '#'+textareaId, {
				time: 1000,
				tips: [1, ''] //位置，颜色
			});
			return false;
		}


		/**发送群聊消息**/
		var message = new Object();
		message.mediaType = 1;
		message.to = groupsId,
		message.textMsg = textareaContent,
		message.from = config.currentUser.imAccount;

		im.sendGroupsMessage(message);
		
		//展示到聊天窗口中间区域
        im.addSendTextToGroupsContent(message);

		/**清空输入框**/
		$("#"+textareaId).val("");
		/**输入框获取焦点**/
		$("#"+textareaId).focus();
		/**设置中间滚动条位置保持在底部**/
		$("#groupsContent_"+groupsId).scrollTop($("#groupsContent_"+groupsId)[0].scrollHeight);

	});
	/**绑定回车键发送**/
	$("#groupsTextarea_"+groupsId).keydown(function(event){ 
		if(event.keyCode==13){ 
			event.preventDefault();
			//触发发送按钮
			$("#groupsSendBtn_"+groupsId).trigger("click");
		}
	});
	
	/**加载未读数据**/
	var thisGroupsMainId = "groups_"+groupsId;
	var data = acceptedData[thisGroupsMainId];
	if(data){
		$.each(data,function(key,message){
			//alert(message);
			if(message.mediaType == 1){
				//alert("加载未读消息addReceiveTextToGroupsContent1");
				addReceiveTextToGroupsContent(message);
			}else if(message.mediaType == 2){
				//alert("加载未读消息addReceiveImgToGroupsContent2");
				addReceiveImgToGroupsContent(message);
			}
		});
		//清空
		acceptedData[thisGroupsMainId] = [];
	}
	
}

/**切换到群组聊天页面**/
var changeToGroupsChatPage = function(currentMainId,groupsId,groupsName){
	var groupsPageId = "groups_"+groupsId;
	var groupsPage = $("#"+groupsPageId);	
	if(groupsPage.html()){
		
		//已经存在群组聊天页面
		var returnBtnName = groupsPage.find("div[class='ddx_header_fh']").attr("name");		
		var str = returnBtnName.replace("_groups","");
		var newReturnBtnName = returnBtnName.replace(str,currentMainId);
		//设置返回按钮name属性
		groupsPage.find("div[class='ddx_header_fh']").attr("name",newReturnBtnName);

		if(currentMainId.indexOf("main_") != -1){
			//隐藏一级页面
			$("#homePage").hide();
		}else{
			//隐藏当前页面
			$("#"+currentMainId).hide();
		}	

		//显示群组聊天页面
		groupsPage.show();
	}else{		
		//没有群组聊天页面 创建一个
		createGroupsChatPage(currentMainId,groupsId,groupsName);
	}

	/*聊天页面中间内容滚动条设置*/
	niceScroll($("#groupsContent_"+groupsId));
	/**输入框获取焦点**/
	$("#groupsTextarea_"+groupsId).focus();
	
	//设置叮当享列表未读消息数量
	setDDXcontentUnreadQuantity("item_DDX_"+groupsId,0);
	//设置叮当享底部未读消息总和数量
	setMainUnreadQuantity("main_DDX",getDDXmainTotalUnreadQuantity());
	
};


/**创建群组信息页面**/
var createGroupsInfoPage = function(currentMainId,groupsId,groupsName){

	var groupsDetailData = getGroupsDetailData(groupsId);
	if(groupsDetailData == null){
		return;
	}
	var members = groupsDetailData.members;
	var membersHtml = "";
	var ownerImAccount = groupsDetailData.owner;
	if(ownerImAccount && ownerImAccount != config.currentUser.imAccount){
		var fullname = getName(ownerImAccount);
		var shortName = fullname;
		if(fullname && fullname.length > 3){
			shortName = fullname.substring(0,3)+"...";
		}
		var icon = getIcon(ownerImAccount);
		membersHtml += '<div class="quanliaoxinxi" style="cursor:pointer;" onclick="changeToChatPage(\'groupsInfo_'+groupsId+'\',\''+ownerImAccount+'\',\''+fullname+'\')">'
			+'<div class="quanliaoxinxi_img"><img src="'+icon+'" width="65" height="65" /></div>'
			+'<div class="quanliaoxinxi_text" title="'+fullname+'">'+shortName+'</div>'
			+'</div>';
	}
	//alert(JSON.stringify(members));
	for(var i in members){
		var imAccount = members[i].userId;
		if(imAccount && imAccount != config.currentUser.imAccount){
			var fullname = getName(imAccount);
			var shortName = fullname;
			if(fullname && fullname.length > 3){
				shortName = fullname.substring(0,3)+"...";
			}
			var icon = getIcon(imAccount);
			membersHtml += '<div class="quanliaoxinxi" style="cursor:pointer;" onclick="changeToChatPage(\'groupsInfo_'+groupsId+'\',\''+imAccount+'\',\''+fullname+'\')">'
				+'<div class="quanliaoxinxi_img"><img src="'+icon+'" width="65" height="65" /></div>'
				+'<div class="quanliaoxinxi_text" title="'+fullname+'">'+shortName+'</div>'
				+'</div>';
		}
	}

	var groupsInfoPageHtml =  '<div class="ddx_content" id="groupsInfo_'+groupsId+'" name="groupsInfoPage">'
		+'<div class="ddx_header">'
		+'<div class="ddx_header_fh" name="'+currentMainId+'_groupsInfo"'
		+' onclick="changeToUpOneLevelPage(\'groupsInfo_'+groupsId+'\')" ><img src="'+projectName+'/chat/images/ddx/fh.png"></div>'
		+'<div class="ddx_header_wz2" id="groupsInfoTitle_'+groupsId+'">'+groupsName+'</div>'
		+'</div>'

		+' <div class="ddx_middle" id="groupsInfoContent_'+groupsId+'" style="background-color:#f7f4f4;height: 575px;width:500px;padding: 15px;">'
		+'<div class="chat-discussion">'

			+membersHtml

		+'</div>'
		+'</div>'

		+'</div>';

		//隐藏当前页面
		$("#"+currentMainId).hide();
		//显示创建的页面
		$("#mainDDX").append(groupsInfoPageHtml);
		/*聊天页面中间内容滚动条设置*/
		niceScroll($("#groupsInfoContent_"+groupsId));

}


/**切换到群组信息页面**/
var changeToGroupsInfoPage = function(groupsId,groupsName){
	var groupsInfoPageId = "groupsInfo_"+groupsId;
	var groupsInfoPage = $("#"+groupsInfoPageId);
	var currentMainId = "groups_"+groupsId;
	if(groupsInfoPage.html()){
		//已经存在页面
		var returnBtnName = groupsInfoPage.find("div[class='ddx_header_fh']").attr("name");
		var str = returnBtnName.replace("_groupsInfo","");
		var newReturnBtnName = returnBtnName.replace(str,currentMainId);
		//设置返回按钮name属性
		groupsInfoPage.find("div[class='ddx_header_fh']").attr("name",newReturnBtnName);
		//隐藏当前页面
		$("#"+currentMainId).hide();
		//显示群组信息页面
		groupsInfoPage.show();
	}else{
		//没有群组信息页面 创建一个
		createGroupsInfoPage(currentMainId,groupsId,groupsName);
	}
}

/**页面切换到上一级页面**/
var changeToUpOneLevelPage = function(currentPageId){
	var returnBtnName = $("#"+currentPageId).find("div[class='ddx_header_fh']").attr("name");
	//alert("切换到上一级页面 ->returnBtnName:"+returnBtnName+" currentPageId:"+currentPageId);
	//returnBtnName 返回按钮的name属性值,currentPageId 当前页面ID
	if(returnBtnName.indexOf("main_") != -1){
		//切换到一级页面
		changeToHomePage(returnBtnName,currentPageId);
	}else{
		//隐藏当前页面
		$("#"+currentPageId).hide();
		//显示一级页面
		var upOneLevelPageId = returnBtnName.substring(0,returnBtnName.lastIndexOf("_"));
		$("#"+upOneLevelPageId).show();	
	}	
}

/**页面切换到一级页面**/
var changeToHomePage = function(returnBtnName,currentPageId){
	//alert("切换到一级页面 ->returnBtnName:"+returnBtnName+" currentPageId:"+currentPageId);
	//returnBtnName 返回按钮的name属性值,currentPageId 当前页面ID
	//隐藏当前页面
	$("#"+currentPageId).hide();
	//显示一级页面
	$("#homePage").show();
	//选中对应的主体内容
	var mainId = returnBtnName.replace("_chat","").replace("_groups","").replace("_page","");
	$("#"+mainId).trigger("click");
} 

/**标签绑定点击事件**/
var bindOnClickToLabel = function(){
	//通讯录页面上方的标签
	$("a[id^='label_TXL']").bind("click",function(){
		var labelId = $(this).attr("id");
		if(labelId == "label_TXL_GZQL") {
			getGroupsData();
		}
		if(labelId == "label_TXL_GSTXL") {
			getDeptInfoCount();
		}
		if(labelId == "label_TXL_XDTS") {
			var quantity = getXDTSUnreadQuantity();
			setXDTSUnreadQuantity(0);
			//设置底部通讯录按钮未读数量
			setMainUnreadQuantity("main_TXL", 0);
			im.sendResponseMessage(newIds);
		}
		changeToPageByLabel(labelId);
	})
	//工作页面上方的标签
	$("a[id^='label_GZ']").bind("click",function(){
		var labelId = $(this).attr("id");
		changeToPageByLabel(labelId);
	})
	//我页面的标签
	$("a[id^='label_W']").bind("click",function(){
		var labelId = $(this).attr("id");
		if(labelId == "label_W_WDJY"){
			var param = {'content':projectName+'/Advise/list.htm?type=120','title':'我的建议',area:['940px','630px']};
			if(only_im == 0) {
				param.offset = [($(window).height()+70-630)/2, ($(window).width()+221-940)/2];
			}
			$.openIframe(param);
		}else if(labelId == "label_W_XGMM"){
			var param = {'content':projectName+'/DDX/toEditPwd.htm','title':'修改密码',area:['420px','300px'],maxmin :false};
			if(only_im == 0) {
				param.offset = [($(window).height()+70-300)/2, ($(window).width()+221-420)/2];
			}
			$.openIframe(param);
		}else{
			changeToPageByLabel(labelId);
		}
	})
	//个人信息
	/*$("#item_W_info").click(function(){
		$.openIframe({'content':projectName+'/DDX/userInfo.htm','title':'个人信息',area:['600px','500px'],maxmin :false});
	})*/
	$(".to_item_W_info").click(function(){
		var param = {'content':projectName+'/DDX/userInfo.htm','title':'个人信息',area:['1000px','500px'],maxmin :false};
		if(only_im == 0) {
			param.offset = [($(window).height()+70-500)/2, ($(window).width()+221-1000)/2];
		}
		$.openIframe(param);
	});
	
}


/**	切换到标签对应的页面切换**/
var changeToPageByLabel = function(labelId){
	var pageId = labelId.replace("label_","page_");
	var page = $("#"+pageId);
	if(page.html()){		
		//隐藏一级页面
		$("#homePage").hide();
		//显示点击标签的页面
		page.show();
	}
}

/**内容主体切换**/
var changeMain = function(mainId){		
	var contentId = mainId.replace("main_","content_");
	//隐藏已展示的内容
	allContent.each(function(i,e){
		var displayVal = $(e).css("display");			
		if(displayVal != "none"){
			$(e).hide();
		}
	});
	//展示当前选择的内容
	$("#"+contentId).show();
}
/**标题切换**/
var changeTitle = function(mainId){		
	var titleId = mainId.replace("main_","title_");
	var titleContent = $("#"+titleId).text();
	$("#title").text(titleContent);
}
/**日期处理**/
var dateHandle = function(dateStr){
	var dt = new Date(dateStr.replace(/-/g,"/"));
	var nowDt = new Date();
	if(dt.getFullYear() == nowDt.getFullYear() && parseInt(dt.getMonth()+1) == parseInt(nowDt.getMonth()+1) && dt.getDate() == nowDt.getDate()){
		//与当日年月日相同返回时分
		return dt.getHours()+":"+(dt.getMinutes() < 10 ? "0"+dt.getMinutes() : dt.getMinutes());
	}else{
		//与当日年月日不同返回年月日
		var year = (dt.getFullYear()+"").substring(2,4);
		return year+"/"+parseInt(dt.getMonth()+1)+"/"+dt.getDate();
	}
}

/**未读消息数量处理**/
var unreadQuantityHandle = function(quantity){
	if(parseInt(quantity) <= 99){
		return quantity;
	}else{
		return "99+";
	}
}

/**获取底部未读数量**/
var getMainUnreadQuantity = function(mainId){
	var divRedDot = $("#"+mainId).find("div[class='ddx_footer1hq']");
	if(divRedDot){
		if($.trim(divRedDot.text()) != ''&& parseInt(divRedDot.text()) > 0){
			return parseInt(divRedDot.text().replace("+",""));
		}else{
			return 0;
		}
	}else{
		return 0;
	}
}

/**设置底部未读数量**/
var setMainUnreadQuantity = function(mainId,quantity){
	var divRedDot = $("#"+mainId).find("div[class='ddx_footer1hq']");	
	divRedDot.remove();
	if(parseInt(quantity) > 0){		
		$("#"+mainId).find("div[class='ddx_footer1icon']").append('<div class="ddx_footer1hq">'+unreadQuantityHandle(quantity)+'</div>');
	}
	
	/**设置叮当享图标红点状态**/
	setDDXImgStatus();
}
	
/**获取叮当享内容中某一项未读数量**/
var getDDXcontentUnreadQuantity = function(contentId){
	var divRedDot = $("#"+contentId).find("div[class='ddx_iconRedDot']");
	if(divRedDot){
		if($.trim(divRedDot.text()) != ''&& parseInt(divRedDot.text()) > 0){
			return parseInt(divRedDot.text().replace("+",""));
		}else{
			return 0;
		}
	}else{
		return 0;
	}
}

/**设置叮当享内容中某一项未读数量**/
var setDDXcontentUnreadQuantity = function(contentId,quantity){
	var divRedDot = $("#"+contentId).find("div[class='ddx_iconRedDot']");
	divRedDot.remove();
	if(parseInt(quantity) > 0){
		$("#"+contentId).find("div[class='ddx_middle1tp']").append('<div class="ddx_iconRedDot">'+unreadQuantityHandle(quantity)+'</div>');
	}			
}

/**获取叮当享内容中所有项未读数量的总和**/
var getDDXmainTotalUnreadQuantity = function(){
	var quantity = 0;
	contentDDX.find("div[class='ddx_iconRedDot']").each(function(i,e){
		var divRedDot = $(e);
		if(divRedDot){
			if($.trim(divRedDot.text()) != ''&& parseInt(divRedDot.text()) > 0){
				quantity += parseInt(divRedDot.text().replace("+",""));
			}
		}
	});
	return quantity;
}
//<div class="txl_iconRedDot"></div>
/**设置新的朋友未读数量**/
var setXDPYUnreadQuantity = function(quantity){
	var divRedDot = $("#label_TXL_XDPY").find("div[class='ddx_iconRedDot']");
	divRedDot.remove();
	if(parseInt(quantity) > 0){
		$("#label_TXL_XDPY").find("div[class='ddx_middle1tp']").append('<div class="ddx_iconRedDot">'+unreadQuantityHandle(quantity)+'</div>');
	}
}

/**获取新的朋友未读数量**/
var getXDPYUnreadQuantity = function(){
	var divRedDot = $("#label_TXL_XDPY").find("div[class='ddx_iconRedDot']");
		if(divRedDot){
			if($.trim(divRedDot.text()) != ''&& parseInt(divRedDot.text()) > 0){
				return parseInt(divRedDot.text().replace("+",""));
			}else{
				return 0 ;
			}
		}
}

/**设置新的同事未读数量**/
var setXDTSUnreadQuantity = function(quantity){
	var divRedDot = $("#label_TXL_XDTS").find("div[class='ddx_iconRedDot']");
	divRedDot.remove();
	if(parseInt(quantity) > 0){
		$("#label_TXL_XDTS").find("div[class='ddx_middle1tp']").append('<div class="ddx_iconRedDot">'+unreadQuantityHandle(quantity)+'</div>');
	}
}

/**获取新的同事未读数量**/
var getXDTSUnreadQuantity = function(){
	var divRedDot = $("#label_TXL_XDTS").find("div[class='ddx_iconRedDot']");
	if(divRedDot){
		if($.trim(divRedDot.text()) != ''&& parseInt(divRedDot.text()) > 0){
			return parseInt(divRedDot.text().replace("+",""));
		}else{
			return 0 ;
		}
	}
}

/**列表内容删除**/

var deleteItemOnclick = function(event,itemId){
	//取消默认事件
	event.preventDefault();		//标准
	event.returnValue = false 	//IE
	//取消事件向上冒泡
	event.stopPropagation();    //标准
	event.cancelBubble = true;  //IE

	var itemDiv = $("#"+itemId);
	if(itemDiv){
		itemDiv.remove();
	}
	//设置底部未读数量
	setMainUnreadQuantity("main_DDX",getDDXmainTotalUnreadQuantity());
}

/**群组列表操作**/
var editGroupsName = function(event,itemId, owner){
	//取消默认事件
	event.preventDefault();		//标准
	event.returnValue = false 	//IE
	//取消事件向上冒泡
	event.stopPropagation();    //标准
	event.cancelBubble = true;  //IE
	
	var groupType = isSysGroup(owner);
	if(groupType == 1 || groupType == 2 || groupType == 3) {
		if(!isAdmin(config.currentUser.imAccount)) {
			layerMsg("系统群不能修改！");
			return;
		}
	}
	var itemDiv = $("#"+itemId);
	if(itemDiv){
		var groupsId = itemId.replace("item_GZQL_","");
		//alert("修改群组名称："+groupsId);
		$("div[id^='dialogBox_']").remove();
		var time = new Date().getTime();
		$("body").append('<div id="dialogBox_'+time+'"></div>');
		var param = {title: '请输入群组名称！', formType: 0};
		if(only_im == 0) {
			param.offset = [($(window).height()+60-160)/2, ($(window).width()+200-260)/2];
		}
		layer.prompt(param, function(groupName, index){
			//发送请求
			var newGroupsName = groupName;
			var data = {"user":config.currentUser.imAccount,"groupName":newGroupsName};
			//newGroupsName=encodeURIComponent(newGroupsName);
			$.ajax({
				type: 'PUT',
				async:true,
				//url: im_url+"group/"+groupsId+"?groupName="+newGroupsName+"&user="+config.currentUser.imAccount,
				url: im_url+"group/"+groupsId,
				data: data,
				dataType: 'json',
				processData: true,
				contentType: 'application/x-www-form-urlencoded',
				success: function(datas){
					if(datas.status === 200){
						layerMsg("修改群组名称成功！");
						//重新加载工作群组
						getGroupsData();
						//修改已经创建的群组相关页面Title
						$("#groupsTitle_"+groupsId).text(newGroupsName);
						$("#groupsInfoTitle_"+groupsId).text(newGroupsName);
					} else {
						layerMsg(datas.status+":修改群组名称失败!", 200);
					}
					layer.close(index);
				},
				error: function(error) {
					layerMsg("修改群组名称失败!");
				},
				timeout:60000
			});
		});
		
	}
}

var dissolveGroups = function(event,itemId, owner){
	//取消默认事件
	event.preventDefault();		//标准
	event.returnValue = false 	//IE
	//取消事件向上冒泡
	event.stopPropagation();    //标准
	event.cancelBubble = true;  //IE
	
	var groupType = isSysGroup(owner);
	if(groupType == 1 || groupType == 2 || groupType == 3) {
		if(!isAdmin(config.currentUser.imAccount)) {
			layerMsg("系统群不能解散！");
			return;
		}
	}
	
	var itemDiv = $("#"+itemId);
	if(itemDiv){
		var groupsId = itemId.replace("item_GZQL_","");
		$("div[id^='dialogBox_']").remove();
		var time = new Date().getTime();
		$("body").append('<div id="dialogBox_'+time+'"></div>');
		var param = {btn: ['确定','取消']};
		if(only_im == 0) {
			param.offset = [($(window).height()+60-160)/2, ($(window).width()+200-260)/2];
		}
		layer.confirm('确定解散该群组吗？', param, function(){
			$.ajax({
				type: 'DELETE',
				async:true,
				url: im_url+"group/"+groupsId+"/"+config.currentUser.imAccount+"/delgroup",
				data: '',
				dataType: 'json',
				processData: false,
				contentType: 'application/json',
				success: function(datas){
					if(datas.status === 200){
						layerMsg("解散群组成功！");
						//重新加载工作群组
						$("#item_DDX_" + groupsId).remove();
						getGroupsData();
					} else {
						layerMsg(datas.status+":解散群组失败!");
					}
				},
				error: function(error) {
					layerMsg("解散群组失败!");
				},
				timeout:60000
			});
		}, function(){
		});
	}
}

var exitGroups = function(event,itemId, owner){
	//取消默认事件
	event.preventDefault();		//标准
	event.returnValue = false 	//IE
	//取消事件向上冒泡
	event.stopPropagation();    //标准
	event.cancelBubble = true;  //IE
	var groupType = isSysGroup(owner);
	if(groupType == 1 || groupType == 2) {
		layerMsg("系统群不能退出！");
		return;
	}
	var itemDiv = $("#"+itemId);
	if(itemDiv){
		var groupsId = itemId.replace("item_GZQL_","");
		$("div[id^='dialogBox_']").remove();
		var time = new Date().getTime();
		$("body").append('<div id="dialogBox_'+time+'"></div>');

		layer.confirm('确定退出该群组吗？', {btn: ['确定','取消'],offset: [($(window).height()+60-160)/2, ($(window).width()+200-260)/2]}, function(){
			$.ajax({
				type: 'DELETE',
				async:true,
				url: im_url+"group/"+groupsId+"/"+config.currentUser.imAccount+"/exit",
				data: '',
				dataType: 'json',
				processData: false,
				contentType: 'application/json',
				success: function(datas){
					if(datas.status === 200){
						layerMsg("退出群组成功！");
						
						$("#item_DDX_" + groupsId).remove();
						//重新加载工作群组
						getGroupsData();
					} else {
						layerMsg(datas.status+":退出群组失败!");
					}
				},
				error: function(error) {
					layerMsg("退出群组失败!");
				},
				timeout:60000
			});
		}, function(){
		});
	}
}

var showOperateArea = function(itemId){
	var itemDiv = $("#"+itemId);
	if(itemDiv){
		var operateArea = itemDiv.find("div[name='operateArea']")[0];
		$(operateArea).css({"display":""});
	}
}

var hideOperateArea = function(itemId){
	var itemDiv = $("#"+itemId);
	if(itemDiv){
		var operateArea = itemDiv.find("div[name='operateArea']")[0];
		$(operateArea).css({"display":"none"});
	}
}

/**验证账号是否存在**/
var verifyImAccountExist = function(imAccount){
	var result = true;//存在
	config.jsonGet(projectName+"/DDX/verifyTelephone.htm", {"telephone":imAccount}, function(datas){
		if(datas != null && datas.result != null){
			result = datas.result;
		}
	}, function(e){
		tips.error("验证朋友账号是否存在失败!");
	});
	return result;
}

/**验证账号是否为朋友**/
var verifyFriendExist = function(imAccount){
	var result = true;//已经是朋友
	config.jsonGet(projectName+"/DDX/judgeFriends.htm", {"telephone":imAccount}, function(datas){
			if(datas != null && datas.result != null){
				result = datas.result;
			}
	}, function(e){
		tips.error("验证是否为朋友失败!");
	})
	return result;
}

/**发送朋友验证**/
var sendAddFriendVerify = function(imAccount){
	var result = false;
	config.jsonGet(projectName+"/DDX/sendMsg.htm", {"imAccount":imAccount}, function(datas){
		if(datas.status === 200){
			//发送成功
			result = true;
		} else {
			tips.error("验证是否为朋友失败!");
		}
	}, function(e){
		tips.error("验证是否为朋友失败!");
	})
	return result;
}

/***添加朋友**/
var addFriend = function(){
	//打开输入朋友账号对话框
	
	$("div[id^='dialogBox_']").remove();
	var time = new Date().getTime();
	$("body").append('<div id="dialogBox_'+time+'"></div>');

	$('#dialogBox_'+time).dialogBox({
		hasClose: true,
		hasBtn: true,
		confirmValue: '确定',
		cancelValue: '取消',
		title: '请输对方账号',
		content: '<input type="text" maxlength="11" id="friendImAccount" style="width: 215px;" autofocus="autofocus" />',
		confirm: function(){
			var friendImAccount = $("#friendImAccount").val();
			if($.trim(friendImAccount) == ""){
				layerMsg("添加失败：您输入的对方账号不存在！", 270);
				return;
			}else{
				if(config.currentUser.imAccount.indexOf(friendImAccount) != -1) {
					layerMsg("添加失败：不能添加自己为朋友！", 270);
					return
				} else if(!verifyImAccountExist(friendImAccount)){//验证对方账号是否存在
					layerMsg("添加失败：您输入的对方账号不存在！", 270);
					return
				}else{
					//验证是否已经是朋友
					var result2 = verifyFriendExist(friendImAccount);
					if(result2 == true){
						layerMsg("添加失败：您输入的对方账号已经是朋友！", 270);
						return;
					}else{
						//不为朋友:发送朋友验证
						var result3 = sendAddFriendVerify(friendImAccount);
						if(result3 == false){
							layerMsg("发送添加朋友验证失败！", 200);
							return;
						}else{
							layerMsg("发送添加朋友验证成功！", 200);
							return;
						}
					}
				}
			}

		}
	});

	
		$($("section[class='dialog-box effect-fade show']")[0]).remove();

}

/**发送朋友验证通过消息**/
var sendVerfyPassMsg = function(imAccount){
	var msgContent = "我通过了你的好友验证请求，现在我们可以开始聊天了";
	var message = new Object();
	message.mediaType = 1;
	message.to = imAccount,
		message.textMsg = msgContent,
		message.from = config.currentUser.imAccount;
	im.sendChatMessage(message);
	//设置到未读数据
	var thisChatMainId = "chat_"+imAccount;
	acceptedData[thisChatMainId] = [message];
}

/**通过朋友验证**/
var agreeFriendApply = function(event,friendId,messageId,friendImaccount){
	//取消默认事件
	event.preventDefault();		//标准
	event.returnValue = false 	//IE
	//取消事件向上冒泡
	event.stopPropagation();    //标准
	event.cancelBubble = true;  //IE
	//处理验证
	config.jsonGet(projectName+"/DDX/accept.htm?accept=1&friendId="+friendId+"&friendImaccount="+friendImaccount, '', function(datas){
		if(datas.status === 200){
			//alert(JSON.stringify(datas));
			//成功
			layerMsg("通过添加朋友申请成功！", 200);
			//发送应答消息
			var responseArr = [messageId];
			im.sendResponseMessage(responseArr);
			//删除朋友验证
			$("#item_XDPY_"+friendImaccount).remove();
			//设置通讯录页面新的朋友未读数量
			var quantity = getXDPYUnreadQuantity();
			setXDPYUnreadQuantity(quantity - 1);
			//设置底部通讯录按钮未读数量
			setMainUnreadQuantity("main_TXL",quantity - 1);
			//刷新通讯录
//			$("div[id^='sequence_']").remove();
//			$("a[id^='item_TXL_']").remove();
			getContactsData();
			//发送验证通过消息
			sendVerfyPassMsg(friendImaccount);
		} else {
			tips.error("通过添加朋友申请失败!");
		}
	}, function(e){
		tips.error("通过添加朋友申请失败!");
	})
}

/**叮当享主体内容添加**/
var addContentToDDXmain = function(param,type){
	//alert(param);
	if(param.message == null) param.message = "";
	if(type == "chat"){
		//单聊
		var name = param.name;
		if(name.endsWith("_erp")) {
			name = name.substring(0, name.length - 4);
			param.name = name;
		}
		if(name.endsWith("_erp_crm")) {
			name = name.substring(0, name.length - 8);
			param.name = name;
		}
		if(name.endsWith("_erp_Blotter")) {
			name = name.substring(0, name.length - 12);
			param.name = name;
		}
		if(param.imAccount == config.currentUser.imAccount) {
			return;
		}
		var contentHtml = '<a href="#" id="item_DDX_'+param.imAccount+'" name="item_DDX" onmouseover="showOperateArea(\'item_DDX_'+param.imAccount+'\')" onmouseout="hideOperateArea(\'item_DDX_'+param.imAccount+'\')">'
	  	+'<div class="ddx_middle1">'
		  	+'<div class="ddx_middle1tp">'
			    +'<img src="'+param.icon+'" style="border-radius:6px;width:50px;height:50px;">'
			    +(parseInt(param.unreadQuantity) > 0 ? '<div class="ddx_iconRedDot">'+unreadQuantityHandle(param.unreadQuantity)+'</div>' : '')
			 +'</div>'
			 +'<div class="ddx_middle1nr">'
			    +'<div class="ddx_middle1nr1">'+param.name+'</div>'
				+'<div class="ddx_middle1nr2">'+(param.message.length > 20? param.message.substring(0,20)+"..." : param.message)+'</div>'
			 +'</div>'
			 +'<div class="ddx_middle1sj">'+dateHandle(param.date)+'</div>'
			+'</div>'
			+'<div name="operateArea" style=" float: right;margin-top: -35px;margin-right: 15px;display: none;">'
			+'<img src="'+deleteIcon+'" title="删除" onclick="deleteItemOnclick(event,\'item_DDX_'+param.imAccount+'\')" />'
			+'</div>'
		+'</a>';
		
		contentDDX.prepend(contentHtml);

		//绑定点击事件
		$("#item_DDX_"+param.imAccount).on("click",function(){
			var thisItemId = $(this).attr("id");
			//切换到聊天页面
			changeToChatPage("main_DDX",param.imAccount,param.name);
		})
		
	}else if(type == "groups"){
		//群聊
		
		var contentHtml = '<a href="#" id="item_DDX_'+param.groupsId+'" name="item_DDX" onmouseover="showOperateArea(\'item_DDX_'+param.groupsId+'\')" onmouseout="hideOperateArea(\'item_DDX_'+param.groupsId+'\')" >'
	  	+'<div class="ddx_middle1">'
		  	+'<div class="ddx_middle1tp">'
			    +'<img src="'+param.icon+'" style="border-radius:6px;width:50px;height:50px;">'
			    +(parseInt(param.unreadQuantity) > 0 ? '<div class="ddx_iconRedDot">'+unreadQuantityHandle(param.unreadQuantity)+'</div>' : '')
			 +'</div>'
			 +'<div class="ddx_middle1nr">'
			    +'<div class="ddx_middle1nr1">'+param.name+'</div>'
				+'<div class="ddx_middle1nr2">'+param.userName+":"+(param.message.length > 20? param.message.substring(0,20)+"..." : param.message)+'</div>'
			 +'</div>'
			 +'<div class="ddx_middle1sj">'+dateHandle(param.date)+'</div>'
			+'</div>'
			+'<div name="operateArea" style=" float: right;margin-top: -35px;margin-right: 15px;display: none;">'
			+'<img src="'+deleteIcon+'" title="删除" onclick="deleteItemOnclick(event,\'item_DDX_'+param.groupsId+'\')" />'
			+'</div>'
		+'</a>';
		
		contentDDX.prepend(contentHtml);

		//绑定点击事件
		$("#item_DDX_"+param.groupsId).on("click",function(){
			var thisItemId = $(this).attr("id");
			//切换到聊天页面
			changeToGroupsChatPage("main_DDX",param.groupsId,param.name);
		})
		
	}
	
	//设置底部未读数量
	setMainUnreadQuantity("main_DDX",getDDXmainTotalUnreadQuantity());
}

/*var contactType0ImagePath = projectName+"/chat/images/tongxunlu/txl0.png";
var contactType1ImagePath = projectName+"/chat/images/tongxunlu/txl1.png";
var contactType0 ="客户";
var contactType1 = "本公司";*/


/*通讯录排序号添加**/
var addSequenceToTXLmain = function(sequence){
	var sequenceHtml = '<div style="width:500px;background-color:#f1f0f6;'
		+'float:left;padding:5px 15px 5px 15px;color:#999999;" id="sequence_'+sequence+'">'+sequence+'</div>';
	
	contentTXL.append(sequenceHtml);
}

/**通讯录联系人添加**/
var addContactsToTXLmain = function(param){
	var contactsHtml = 	'<a href="#" id="item_TXL_'+param.imAccount+'" name="item_TXL">'
							+'<div class="ddx_middle1">'
								+'<div class="ddx_middle1tp">'
									+'<img src="'+param.icon+'" '
									+'style="border-radius:6px;width:50px;height:50px;">'
								+'</div>'
								+'<div class="ddx_middle1nr">'
									+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.name+'</div>'
								+'</div>'
								

								+'<div class="ddx_middle1sj" style="padding-top:8px;">'
									+'<div style="height:20px;width:120px;float:right;font-size:14px;margin-top:10px;">'
									+param.job+'</div>'
								+'</div>'
								
							+'</div>'
						+'</a>';

	contentTXL.append(contactsHtml);
	
	//绑定点击事件
	$("#item_TXL_"+param.imAccount).on("click",function(){
		var thisItemId = $(this).attr("id");
		//createChatPage("main_TXL",param.imAccount,param.name);
		//切换到聊天页面
		changeToChatPage("main_TXL",param.imAccount,param.name);
	});
}

/**部门通讯录联系人添加**/
var addContactsToBMTXLmain = function(param){
	var contactsHtml = 	'<a href="#" id="item_BMTXL_'+param.imAccount+'" name="item_TXL_BM">'
							+'<div class="ddx_middle1">'
								+'<div class="ddx_middle1tp">'
									+'<img src="'+param.icon+'" '
									+'style="border-radius:6px;width:50px;height:50px;">'
								+'</div>'
								+'<div class="ddx_middle1nr">'
									+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.name+'</div>'
								+'</div>'
								

								+'<div class="ddx_middle1sj" style="padding-top:8px;">'
									+'<div style="height:20px;width:120px;float:right;font-size:14px;margin-top:10px;">'
									+param.job+'</div>'
								+'</div>'
								
							+'</div>'
						+'</a>';

	contentTXL_BM.append(contactsHtml);
	
	//绑定点击事件
	$("#item_BMTXL_"+param.imAccount).on("click",function(){
		var thisItemId = $(this).attr("id");
		//切换到聊天页面
		$("#page_TXL_GSTXL_BMTXL").hide();
		changeToChatPage("main_TXL",param.imAccount,param.name);
	});
}
/**新的同事通讯录联系人添加**/
var addContactsToXDTSmain = function(param){
	if(param.icon == "") {
		param.icon = defaultSingleIcon;
	}
	var contactsHtml = 	'<a href="#" id="item_XDTS_'+param.imAccount+'" name="item_TXL_XDTS">'
							+'<div class="ddx_middle1">'
								+'<div class="ddx_middle1tp">'
									+'<img src="'+param.icon+'" '
									+'style="border-radius:6px;width:50px;height:50px;">'
								+'</div>'
								+'<div class="ddx_middle1nr">'
									+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.name+'</div>'
								+'</div>'
								

								+'<div class="ddx_middle1sj" style="padding-top:8px;">'
									+'<div style="height:20px;width:120px;float:right;font-size:14px;margin-top:10px;">'
									+param.job+'</div>'
								+'</div>'
								
							+'</div>'
						+'</a>';

	var contentTXL_XDTS = $("#pageContent_TXL_XDTS");
	contentTXL_XDTS.append(contactsHtml);
	//绑定点击事件
	$("#item_XDTS_"+param.imAccount).on("click",function(){
		var thisItemId = $(this).attr("id");
		//切换到聊天页面
		$("#page_TXL_XDTS").hide();
		changeToChatPage("main_TXL",param.imAccount,param.name);
	});
}

/**超享团队客服添加**/
var addContactsToCXTDmain = function(param){
	var contactsHtml = 	'<a href="#" id="item_CXTD_'+param.imAccount+'" name="item_CXTD">'
		+'<div class="ddx_middle1">'
		+'<div class="ddx_middle1tp">'
		+'<img src="'+param.icon+'" '
		+'style="border-radius:6px;width:50px;height:50px;">'
		+'</div>'
		+'<div class="ddx_middle1nr">'
		+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.name+'</div>'
		+'</div>'
		+'</div>'
		+'</a>';

	contentCXTD.append(contactsHtml);

	//绑定点击事件
	$("#item_CXTD_"+param.imAccount).on("click",function(){
		var thisItemId = $(this).attr("id");
		//切换到聊天页面
		changeToChatPage("page_TXL_CXTD",param.imAccount,param.name);
	})
}

/**工作群聊列表内容添加**/
var addGroupsToGZQLmain = function(param){
	
	/*var groupsHtml = 	'<a href="#" id="item_GZQL_'+param.groupsId+'" name="item_GZQL" onmouseover="showOperateArea(\'item_GZQL_'+param.groupsId+'\')" onmouseout="hideOperateArea(\'item_GZQL_'+param.groupsId+'\')">'
							+'<div class="ddx_middle1">'
								+'<div class="ddx_middle1tp">'
									+'<img src="'+param.icon+'" '
									+'style="border-radius:6px;width:50px;height:50px;">'
								+'</div>'
								+'<div class="ddx_middle1nr">'
									+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.name+'</div>'
								+'</div>'

								+'<div name="operateArea" style="float: right;margin-top: 35px;margin-right: -3px;display: none">'
								+'<img src="'+editIcon+'" title="修改群名称" onclick="editGroupsName(event,\''+param.groupsId+'\')" style="margin-right: 5px;"/>'
								+ (param.owner == config.currentUser.imAccount ? '<img src="'+removeIcon+'" title="解散群组" onclick="dissolveGroups(event,\''+param.groupsId+'\')"/>'
									:  '<img src="'+exitIcon+'" title="退出群组" onclick="exitGroups(event,\''+param.groupsId+'\')"/>')
								+'</div>'

							+'</div>'
						+'</a>';*/
	var ownerType = isSysGroup(param.owner);
	if((ownerType == 1 || ownerType == 2 || ownerType == 3) && !isAdmin(config.currentUser.imAccount)) {
		if(param.name.indexOf("_") != -1) {
			param.name = param.name.substring(0, param.name.length - 5);
		}
	}
	var groupsHtml = 	'<a href="#" id="item_GZQL_'+param.groupsId+'" name="item_GZQL" onmouseover="showOperateArea(\'item_GZQL_'+param.groupsId+'\')" onmouseout="hideOperateArea(\'item_GZQL_'+param.groupsId+'\')">'
							+'<div class="ddx_middle1">'
								+'<div class="ddx_middle1tp">'
									+'<img src="'+param.icon+'" '
									+'style="border-radius:6px;width:50px;height:50px;">'
								+'</div>'
								+'<div class="ddx_middle1nr">'
									+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.name+'</div>'
								+'</div>';
	if(isAdmin(config.currentUser.imAccount)) {
		//当前账号是管理员
		groupsHtml = groupsHtml	+''
								+'<div name="operateArea" style="float: right;margin-top: 35px;margin-right: -3px;display: none">'
								+'<img src="'+editIcon+'" title="修改群名称" onclick="editGroupsName(event,\''+param.groupsId+'\',\''+param.owner+'\')" style="margin-right: 5px;"/>'
								+ (param.owner == config.currentUser.imAccount ? '<img src="'+removeIcon+'" title="解散群组" onclick="dissolveGroups(event,\''+param.groupsId+'\',\''+param.owner+'\')"/>'
										:  '<img src="'+exitIcon+'" title="退出群组" onclick="exitGroups(event,\''+param.groupsId+'\',\''+param.owner+'\')"/>')
								+'</div>';
	} else {
		if(ownerType == 1 || ownerType == 2) {
			//行业的系统群 || 综合所有行业的系统群(用户不可退群)
			groupsHtml = groupsHtml	+''
									+'<div name="operateArea" style="float: right;margin-top: 35px;margin-right: -3px;display: none">'
									+'</div>';
		} else if(ownerType == 3) {
			//综合所有行业的系统群(用户可退群)
			groupsHtml = groupsHtml	+''
									+'<div name="operateArea" style="float: right;margin-top: 35px;margin-right: -3px;display: none">'
									+'<img src="'+exitIcon+'" title="退出群组" onclick="exitGroups(event,\''+param.groupsId+'\',\''+param.owner+'\')"/>'
									+'</div>';
		} else {
			//非系统群
			groupsHtml = groupsHtml	+''
									+'<div name="operateArea" style="float: right;margin-top: 35px;margin-right: -3px;display: none">'
									+'<img src="'+editIcon+'" title="修改群名称" onclick="editGroupsName(event,\''+param.groupsId+'\',\''+param.owner+'\')" style="margin-right: 5px;"/>'
									+ (param.owner == config.currentUser.imAccount ? '<img src="'+removeIcon+'" title="解散群组" onclick="dissolveGroups(event,\''+param.groupsId+'\',\''+param.owner+'\')"/>'
											:  '<img src="'+exitIcon+'" title="退出群组" onclick="exitGroups(event,\''+param.groupsId+'\',\''+param.owner+'\')"/>')
									+'</div>';
		}
	}
	groupsHtml = groupsHtml	+'</div></a>';
	contentGZQL.append(groupsHtml);
	//绑定点击事件
	$("#item_GZQL_"+param.groupsId).on("click",function(){
		var thisItemId = $(this).attr("id");
		//切换到聊天页面
		changeToGroupsChatPage("page_TXL_GZQL",param.groupsId,param.name);
	})
	
}

var userInfo = {};
/**根据用户ID获取用户信息 JSON**/
var getUserDataByUserId = function(userId){
	config.jsonGet(projectName+"/DDX/getUserByUserId.htm", {"userId":userId}, function(datas){
		if(datas.status === 200){
			userInfo.name = datas.name;
			userInfo.imAccount = datas.imAccount;
			userInfo.companyId = datas.companyId;
			userInfo.icon = defaultSingleIcon;
			if(datas.icon){
				userInfo.icon = datas.icon;
			}
		} else {
			tips.error("新的朋友列表获取用户数据失败!");
		}
	}, function(e){
		tips.error("新的朋友列表获取用户数据失败!");
	})
}

/**新的朋友列表内容添加**/
var addFriendApplyToXDPYmain = function(param){
	if($("#item_XDPY_"+param.friendImaccount).html()){return;}
	var friendApplyHtml = '<a href="#" id="item_XDPY_'+param.friendImaccount+'" name="item_XDPY" onmouseover="showOperateArea(\'item_XDPY_'+param.friendImaccount+'\')" onmouseout="hideOperateArea(\'item_XDPY_'+param.friendImaccount+'\')">'
		+'<div class="ddx_middle1">'
		+'<div class="ddx_middle1tp">'
		+'<img src="'+($.trim(param.friendIcon) == ""? defaultSingleIcon : param.friendIcon)+'" '
		+'style="border-radius:6px;width:50px;height:50px;">'
		+'</div>'
		+'<div class="ddx_middle1nr">'
		+'<div class="ddx_middle1nr1" style="margin-top:15px;">'+param.friendName+'</div>'
		+'</div>'
		+'<div name="operateArea" style="float: right;margin-top: 35px;margin-right: -3px;display: none">'
		+'<img src="'+agreeIcon+'" title="同意" onclick="agreeFriendApply(event,\''+param.friendId+'\',\''+param.messageId+'\',\''+param.friendImaccount+'\')"/>'
		+'</div>'
		+'</div>'
		+'</a>';
	contentXDPY.append(friendApplyHtml);

	//设置通讯录页面新的朋友未读数量
	var quantity = getXDPYUnreadQuantity();
	setXDPYUnreadQuantity(quantity + 1);
	//设置底部通讯录按钮未读数量
	setMainUnreadQuantity("main_TXL",quantity + 1);
}

/**新的同事列表内容添加**/
var newIds = [];
var addManToXDTSmain = function(param){
	var joinTime = param.joinTime;
    var nowTime = new Date();
    var diff = nowTime.getTime() - new Date(joinTime).getTime(); // 时间差的毫秒数    
    var days=Math.floor(diff/(24*3600*1000)); // 计算出相差天数 
    if(days >= 7) {
    	var ids = [];
    	ids.push(param.messageId);
    	im.sendResponseMessage(ids);
    } else {
    	newIds.push(param.messageId);
    }
	if($("#item_XDTS_"+param.imAccount).html()){return;}

	addContactsToXDTSmain(param);
	//设置通讯录页面新的同事未读数量
	var quantity = getXDTSUnreadQuantity();
	setXDTSUnreadQuantity(quantity + 1);
	//设置底部通讯录按钮未读数量
	setMainUnreadQuantity("main_TXL",quantity + 1);
}

/**切换底部按钮样式**/
var changeBottomIcon = function(mainId){
	var footerDiv = $("#"+mainId).find("div[class='ddx_footer1icon']");
	if(footerDiv){
		//将选择状态的图标切换为未选择状态图标
		var allFooterDiv = $("div[class='ddx_footer1icon']");
		allFooterDiv.each(function(i,e){
			var imgPath = $(e).find("img").attr("src");
			if(imgPath.lastIndexOf("A.") != -1){
				//图标是以A结尾的
				var imgName=imgPath.substring(imgPath.lastIndexOf("/")+1,imgPath.lastIndexOf(".")-1);			
				var imgPathNoA = imgPath.replace(imgName+"A",imgName);
				$(e).find("img").attr("src",imgPathNoA);
			}			
		})
		//将当前选择的按钮切换为选择状态图标
		var imgPath = footerDiv.find("img").attr("src");
		if(imgPath && imgPath.lastIndexOf("A.") == -1){
			//图标不是以A结尾的
			var imgName=imgPath.substring(imgPath.lastIndexOf("/")+1,imgPath.lastIndexOf("."));
			var imgPathA = imgPath.replace(imgName,imgName+"A");
			footerDiv.find("img").attr("src",imgPathA);
		}		
	}
}

/**滚动条**/
var niceScroll = function(selector){
	selector.niceScroll({  
	    cursorcolor:"#bbbcbe",  
	    cursoropacitymax:1,  
	    touchbehavior:false,  
	    cursorwidth:"6px",  
	    cursorborder:"0",  
	    cursorborderradius:"5px",
	    autohidemode: "scroll",
	    hidecursordelay: "40"
	}); 
}

/**设置叮当享图标红点状态**/
var setDDXImgStatus = function(){
	var headerRedDot = $("#header_DDX_RedDot");
	var kefuRedDot = $("#kefu_DDX_RedDot");
	//获取底部未读数量总数
	var quantity = 0;
	allMain.each(function(){
		var mainId = $(this).attr("id");
		quantity += getMainUnreadQuantity(mainId);
	})
	if(quantity == 0){
		headerRedDot.hide();
		kefuRedDot.hide();
	}else{
		headerRedDot.show();
		kefuRedDot.show();
	}
}

/****/
var updateCurrentUserInfo = function(userId){
	if(userId == config.currentUser.id){
		//重新获取个人信息
		getCurrentUserInfo();
	}
}

/***********************************************文档加载完成后执行开始************************************************/
$(function() {
	var _only_im = $("#only_im").val();
	if(_only_im != undefined && _only_im != "") {
		only_im = 1;
	}
	$.extend({
        alert : function(msg) {
            layer.alert(msg ? msg : '', {
                icon : false,
                skin : 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
            })
        },
        // 1=操作成功，2=操作失败，3=警告，
        msg : function(msg, opt) {
            var defaults = {
                time : 2000
            };
            var setts = $.extend(defaults, opt);
            layer.msg(msg ? msg : '', setts);
        },
        openIframe : function(opt) {
            /**屏幕分辨率高*/
            var screenHeight = window.screen.height;
            /** 屏幕分辨率的宽 */
            var screenWidth = window.screen.width;
            var defaults = {
                load : 0,
                type : 2,
                title : '系统弹出层',
                shadeClose : true,
                shade : 0.1,
                maxmin : true, //开启最大化最小化按钮
                area : 'auto',
                content : [ 'http://layer.layui.com' ],
                scrollbar : true
            }
            var _settings = $.extend(defaults, opt);
            var _array = _settings.area;
            var _width = parseInt(_settings.area[0].replace("px", ""));
            var _height = parseInt(_settings.area[1].replace("px", ""));
            if (_width > screenWidth) {
                _array[0] = screenWidth + "px";
            }
            if (_height > screenHeight - 200) {
                _array[1] = (screenHeight - 200) + "px";
            }
            _settings.area = _array;

            layer.open(_settings);
        },
        loadIframe : function() {
            var index = layer.load(2, {
                shade : 0.3
            });
        },
        tipMsg : function(opt) {
            var defaults = {
                msg : ''
            }
            var _setting = $.extend(defaults, opt);
            layer.msg(_setting.msg, {
                offset : ['220px' , '50%'],
                shift : 5,
                skin : 'layui-layer-demo',
                area : [ '350px', '45px' ],
                closeBtn : true
            });
        }
    });
	
	if ($.browser && $.browser.msie && $.browser.version.substr(0,1) < 7){
	$('li').has('ul').mouseover(function(){
		$(this).children('ul').css('visibility','visible');
		}).mouseout(function(){
		$(this).children('ul').css('visibility','hidden');
		});
	}

	/* Mobile */
	$('#menu-wrap').prepend('<div id="menu-trigger">Menu</div>');
	$("#menu-trigger").on('click', function(){
		$("#menu").slideToggle();
	});

	// iPad
	var isiPad = navigator.userAgent.match(/iPad/i) != null;
	if (isiPad) $('#menu ul').addClass('no-transition');
	
	allMain = $("a[id^='main_']");
	allContent = $("div[id^='content_']");
	mainDDX = $("#main_DDX");
	mainTH = $("#main_TH");
	mainTXL = $("#main_TXL");
	mainYYHY = $("#main_YYHY");
	mainW = $("#main_W");
	mainFQQL = $("#main_FQQL");
	mainTJPY = $("#main_TJPY");
	mainSYS = $("#main_SYS");
	mainSFK = $("#main_SFK");
	mainYJFK = $("#main_YJFK");
	contentDDX = $("#content_DDX");
	contentTH = $("#content_TH");
	contentTXL = $("#friend_content_TXL");
	contentTXL_BM = $("#pageContent_TXL_GSTXL_BMTX");
	contentYYHY = $("#content_YYHY");
	contentW = $("#content_W");
	contentFQQL = $("#content_FQQL");
	contentTJPY = $("#content_TJPY");
	contentSYS = $("#content_SYS");
	contentSFK = $("#content_SFK");
	contentYJFK = $("#content_YJFK");
	allContentItemDDX =contentDDX.find("a[name='item_DDX']");
	allChatContent = $("div[id^='chatContent_']");
	allPageContent = $("div[id^='pageContent_']");
	contentGZQL = $("#pageContent_TXL_GZQL");
	contentCXTD = $("#pageContent_TXL_CXID");
	contentXDPY = $("#pageContent_TXL_XDPY");
	/*一级页面中间内容滚动条设置*/
	niceScroll(allContent);		
	/*二级页面中间内容滚动条设置*/
	niceScroll(allPageContent);	
	
	/**所有主体内容切换按钮绑定点击事件（底部和右上角按钮）**/
	allMain.click(function(){
		var thisMainId = $(this).attr("id");
		$("#sendBtton").hide();
		$("#title").show();
		if("main_GZ" == thisMainId){
			$("#menu-wrap").hide();
			$("#menu-gsq").show();
			$("#return1").hide();
			showgsq();
			$("#work_remark").val("")
			$("#work_annex").val("")
			var ctxPath = $("#path").val();
			$("#work_fileImg").attr("stc",ctxPath+ "/images/icons/accessory_icon1.png");
			deleteFile();
		}else{
			$("#menu-gsq").hide();
			$("#menu-wrap").show();
			$("#return1").hide();
		}
		//页面主体切换 
		changeMain(thisMainId);	
		//标题切换
		changeTitle(thisMainId);
		//底部按钮图标切换
		changeBottomIcon(thisMainId);
		$("#title").css("margin-left", "49px");
	});
		
	
	/**叮当享内容的每一项绑定点击事件**/
	allContentItemDDX.on("click",function(){
		var thisItemId = $(this).attr("id");
	})


	/**获取链接地址**/
	getUrls();
	/**请求当前用户信息**/
	getCurrentUserInfo();
	/**请求通讯录数据(先请求用户信息，通讯录需要过滤当前用户)**/
	getContactsData();
	/**获取部门用户数**/
	getDeptInfoCount();
	/**请求群组数据**/
	getGroupsData();
	/**请求客服**/
	getkefuData();
	/**一级页面标签绑定点击事件**/
	bindOnClickToLabel();
	/**用户二维码**/
	initEWMPic();
	/**连接聊天服务器**/
	//config.currentUser.imAccount = "test1236_2";
	//config.currentUser.pwd = im.md5(im.md5("123456"));
	im.connect();
	
});
/***********************************************文档加载完成后执行结束************************************************/
var layerMsg = function(title, width) {
	if(width == undefined) {
		width = 150;
	}
	var param = {};
	if(only_im == 0) {
		param.offset = [($(window).height()+60-50)/2, ($(window).width()+200-width)/2];
	}
	layer.msg(title, param);
}
var layerSuccMsg = function(title, width) {
	if(width == undefined) {
		width = 150;
	}
	var param = {
		icon : 1
	};
	if(only_im == 0) {
		param.offset = [($(window).height()+60-50)/2, ($(window).width()+200-width)/2];
	}
	layer.msg(title, param);
}
var layerErrorMsg = function(title, width) {
	if(width == undefined) {
		width = 150;
	}
	var param = {
		icon : 2
	};
	if(only_im == 0) {
		param.offset = [($(window).height()+60-50)/2, ($(window).width()+200-width)/2];
	}
	layer.msg(title, param);
}
var isAdmin = function(account) {
	if(account.indexOf("100000000") == -1) {
		return false;
	}
	if(account == "10000000001" || account == "10000000002" || account == "10000000003" ||
			account == "10000000004" || account == "10000000005" || account == "10000000006" ||
			account == "10000000007" || account == "10000000008" || account == "10000000009" ||
			account == "10000000010" || account == "10000000011" || account == "10000000012" ||
			account == "10000000013" || account == "10000000014" || account == "10000000015" ||
			account == "10000000016" || account == "10000000017" || account == "10000000018" ||
			account == "10000000019" || account == "10000000020" || account == "10000000021" ||
			account == "10000000022" || account == "10000000023" || account == "10000000024" ||
			account == "10000000000") {
		return true;
	} else {
		return false;
	}
}
var isSysGroup = function(owner) {
	if(owner.indexOf("100000000") == -1) {
		return 0;
	}
	if(owner == "10000000007" || owner == "10000000008" || owner == "10000000009" ||
			owner == "10000000010" || owner == "10000000011" || owner == "10000000012" ||
			owner == "10000000013" || owner == "10000000014" || owner == "10000000015" ||
			owner == "10000000016" || owner == "10000000017" || owner == "10000000018" ||
			owner == "10000000019" || owner == "10000000020" || owner == "10000000021" ||
			owner == "10000000022" || owner == "10000000023" || owner == "10000000024") {
		return 1;//系统行业群：用户不能退出
	} else if(owner == "10000000001" || owner == "10000000002" || owner == "10000000003") {
		return 2;//系统商务群：用户不能退出
	} else if(owner == "10000000004" || owner == "10000000005" || owner == "10000000006") {
		return 3;//系统推广群：用户可退出
	}
	return 0;//普通群
}

/*********公司圈JS*********/
/**
 * Created by an.han on 14-2-20.
 */

var share_title;
var share_summary;
var share_Url;

window.onload = function() {
	
	//详情弹出附件
	$("#cfileSpan").click(function(){
		var isFile = $("#detail_isFile").val()
		if(isFile == "1"){
			var bid = $("#detail_eid").val();
			var ctxPath = $("#path").val();
			$.prompt.layerUrl(ctxPath + '/annex/list.htm?bid=' + bid + "&btype=35",null,null, "查看附件");
		}
	});
	

	// 点击列表自己头像显示自己提交的工作
	$("#loginIcon").on('click', function() {

		$("#page_GZ_GZQ").hide();
		$("#page_GZ_GZQMY").show();
		$("#page_GZ_GZQXQ").hide();

		// 查询自己创建的工作
		getMyWord();

		var list = $("#my_list");

		$.each(list, function(index, e) {

			// 详情
			var detail = $(e).find(".comment-list");

			// 详情
			$(detail).click(function() {

				var id = $(this).attr("id");

				$("#page_GZ_GZQ").hide();
				$("#page_GZ_GZQXQ").show();
				$("#page_GZ_GZQMY").hide();


				showDetail(id,3);

			});

		});

	});

	// 详情内点击评论输入框展开事件
	$("#show_textarea").on('click', function() {

		$(this).parent().addClass("text-box-on");

		$(this).val($(this).val() == '评论…' ? '' : $(this).val());

	});

	// 详情内点击评论输入框收缩事件
	$("#show_textarea").blur(function() {

		var value = $(this).val();

		if (value == '') {
			$(this).val("评论…");
			$(this).parent().removeClass("text-box-on");
		}

	});

	// 详情内评论
	$("#detail_reply").click(
			function() {

				var ctxPath = $("#path").val();

				var cssl = $(this).css("btn-off");

				// 按钮灰色不能提交(字数超过140)
				if (cssl == '') {

					// 评论内容
					var reply_content = $("#show_textarea").val();
					var eid = $("#detail_eid").val();
					var type = $("#detail_type").val();

					$.post(ctxPath + "/workRecord/record.htm", {
						"l_bid" : eid,
						"l_type" : 35,
						"s_remark" : reply_content
					}, function(rst) {

						if (rst.status == 200) {
							layerSuccMsg("评论成功", 180);

							var icon = rst.data.icon;
							var reply_time = rst.data.createTimeForMat;

							var recordHtml = $("#recordDemo").html();

							var userName = '我';
							var userIcon = '';

							// 默认头像
							if (icon == ''
									|| icon== null
									|| icon== "null") {
								userIcon = ctxPath
										+ '/chat/images/defaultSingleIcon.png';
							} else {
								userIcon = icon;
							}

							var recordList = recordHtml.replace(
									'##record_remark##', reply_content)
									.replace('##record_time##', reply_time)
									.replace('##record_icon##', userIcon)
									.replace('##record_userName##', userName)
									.replace('none', 'block');

							$("#detail_recordList").append(recordList);

							$("#show_textarea").val('');
						}

					}, 'json');
				}
			});

	// 详情内评论字数输入框监控
	$("#show_textarea").keyup(function() {
		var val = this.value;
		var len = val.length;
		var els = this.parentNode.children;
		var btn = els[1];
		var word = els[2];
		if (len <= 0 || len > 140) {
			btn.className = 'btn btn-off';
		} else {
			btn.className = 'btn';
		}
		word.innerHTML = len + '/140';
	});

	niceScroll($("#ddx_middle_gsq"));
	//niceScroll($(".ddx_middle_GZ_GZQ_detail"));
	niceScroll($(".ddx_middle_GZ_GZQ_self"));

	if ($.browser.msie && $.browser.version.substr(0, 1) < 7) {
		$('li').has('ul').mouseover(function() {
			$(this).children('ul').css('visibility', 'visible');
		}).mouseout(function() {
			$(this).children('ul').css('visibility', 'hidden');
		});
	}

	/* Mobile */
	$('#menu-wrap').prepend('<div id="menu-trigger">Menu</div>');
	$("#menu-trigger").on('click', function() {
		$("#menu").slideToggle();
	});

	// iPad
	var isiPad = navigator.userAgent.match(/iPad/i) != null;
	if (isiPad)
		$('#menu ul').addClass('no-transition');

	// 格式化日期
	function formateDate(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		var h = date.getHours();
		var mi = date.getMinutes();
		m = m > 9 ? m : '0' + m;
		return y + '-' + m + '-' + d + ' ' + h + ':' + mi;
	}

};

//显示保存内容
function saveWork(){
	changeToListPage("content_GZXQ","content_GZMY","content_GZ","content_GZSAVE");
	$("#title").hide();
	$("#menu-gsq").hide();
	$("#returnType").val('4');
	$("#return1").show();
	$("#sendBtton").show();
}

// 页面隐藏和显示 TODO 
function changeToListPage(hide, hide2,hide3,show) {

	$("#" + hide).hide();
	$("#" + hide2).hide();
	$("#" + hide3).hide();
	$("#" + show).show();
	var ctxPath = $("#path").val();
	$("#work_remark").val("")
	$("#work_annex").val("")
	$("#work_fileImg").attr("stc",ctxPath+ "/images/icons/accessory_icon1.png");
}

function return1(){
	var type = $("#returnType").val();
	deleteFile();
	if(type == "1"){
		changeToListPage("content_GZXQ","content_GZMY","content_GZSAVE","content_GZ");
		$("#return1").hide();
		$("#sendBtton").hide();
		$("#menu-gsq").show();
		$("#title").css({"margin-left":"49px"});
	}else if(type == "2"){
		changeToListPage("content_GZXQ","content_GZMY","content_GZSAVE","content_GZ");
		$("#menu-gsq").show();
		$("#return1").hide();
		$("#sendBtton").hide();
		$("#title").css({"margin-left":"49px"});
	}else if(type == "3"){
		$("#returnType").val('1');
		$("#menu-gsq").hide();
		changeToListPage("content_GZXQ","content_GZ","content_GZSAVE","content_GZMY");
	}else if(type == "4"){
		$("#title").show();
		$("#sendBtton").hide();
		$("#return1").hide();
		$("#menu-gsq").show();
		changeToListPage("content_GZXQ","content_GZMY","content_GZSAVE","content_GZ");
		$("#work_remark").val("")
	}
}

//  详情
function showDetail(eid,type) {

	var ctxPath = $("#path").val();
	$("#menu-gsq").hide();
	$("#return1").show();
	$("#returnType").val(type);
	
	$("#title").css({"margin-left":"4px"});

	changeToListPage("content_GZ","content_GZMY","content_GZSAVE","content_GZXQ");
	
	$.ajax({
		'url' : ctxPath + "/workRecord/findDetail/" + eid + ".htm",
		'type' : "POST",
		'async' : false,
		'dataType' : 'json',
		'success' : function(rst) {

			var obj = rst.data;

			// 头像和姓名
			var icon = obj['sendIcon'];

			// 设置eid
			$("#detail_eid").val(obj['eid']);

			var userIcon = '';

			// 默认头像
			if (icon == '' || icon == null || icon == "null") {
				userIcon = ctxPath + '/chat/images/defaultSingleIcon.png';
			} else {
				userIcon = icon;
			}


			$("#detail_remark").text(obj['remark']);
			$("#detail_icon").attr("src", userIcon);
			$("#send_name").text(obj['sendName']);
			$("#detail_time").text(obj['createTime']);

			// 附件列表
			var annexList = obj['annexList'];
			// 评论列表
			var recordList = obj['recordList'];

			var $detail_address = $("#detail_address").children();

			$.each($detail_address, function(i, e) {
				
				$(e).click(function(){
					
					var address = $(this).data("ars");
					
					window.open(address);
					
				});
				
			});
			
			// 附件
			if(annexList.length > 0){
				$("#cfile").attr("src",ctxPath + "/images/icons/accessory_icon2.png")
				$("#cfileSpan").css("cursor","pointer");
				$("#detail_isFile").val("1")
			}else{
				$("#cfile").attr("src",ctxPath + "/images/icons/accessory_icon1.png")
				$("#cfileSpan").css("cursor","default");
				$("#detail_isFile").val("2")
			}
			/*var fileStr = "";
			var imgStr = "";
			var x = 0;
			$.each(annexList, function(i, e) {
				x++;
				var showType = e['showType'];
				var srcName = e['srcName'];
				var path = e['path'];
				if (showType == 2) {
					imgStr += '<a href="' + path + '"><img class="c_img" src="'
							+ path + '" alt="' + srcName + '"/></a>';
				}else{
					fileStr+='<a href="' + path + '"><img class="head" src="'
					+ path + '" alt="' + srcName + '"/></a>'
				}
				$("#detail_img").html(imgStr);
				$("#detail_file").html(fileStr);
			});
*/
			// 评论
			var recordListHtml = "";
			$.each(recordList, function(i, e) {

				// 评论例子HTML
				var recordHtml = $("#recordDemo").html();
				var userId = e['userId'];

				// 登录用户
				var loginUser = obj['loginUser'];

				var userName = '';
				var userIcon = '';

				if (loginUser['eid'] == userId) {
					userName = '我';
					var loginIcon = loginUser['icon'];
					if (loginIcon == '' || loginIcon == null
							|| loginIcon == "null") {
						userIcon = ctxPath
								+ '/chat/images/defaultSingleIcon.png';
					} else {
						userIcon = loginIcon;
					}
				} else {

					userName = e['userName'];

					var notImIcon = e['icon'];

					if (notImIcon == '' || notImIcon == null
							|| notImIcon == "null") {
						userIcon = ctxPath
								+ '/chat/images/defaultSingleIcon.png';
					} else {
						userIcon = notImIcon;
					}

				}

				var recordList = recordHtml.replace('##record_remark##',
						e['remark'])
						.replace('##record_time##', e['createTime']).replace(
								'##record_icon##', userIcon).replace(
								'##record_userName##', userName).replace(
								'none', 'block');

				recordListHtml += recordList;

			});

			$("#detail_recordList").html('');
			$("#detail_recordList").html(recordListHtml);

		}
	});
}




// 查询自己发送的工作
function getMyWord() {

	var path = $("#path").val();

	$("#return1").show();
	$("#returnType").val("2");
	$("#menu-gsq").hide();
	$("#title").css({"margin-left":"4px"});

	changeToListPage("content_GZ","content_GZXQ","content_GZSAVE","content_GZMY");
	
	$.ajax({
		'url' : path + "/workRecord/findPageMyWord/1.htm",
		'type' : "post",
		'async' : false,
		'dataType' : 'json',
		'success' : function(rst, statusText) {
			var list = rst.data;

			var ouer = rst.otherData.loginUser;

			var icon1 = '';

			// 默认头像
			if (ouer['icon'] == '' || ouer['icon'] == null
					|| ouer['icon'] == "null") {
				icon1 = path + '/chat/images/defaultSingleIcon.png';
			} else {
				icon1 = ouer['icon'];
			}

			$("#my_loginIcon").attr("src", icon1);
			$("#ddxmyUserName").text(ouer.name);

			if (list.length == 0) {
				/* parent.createTips.message("暂无数据！", createTips.warn);*/
				 layerMsg("暂无数据!", 120);
			}

			var all_list = "";

			for (var i = 0; i < list.length; i++) {
				var obj = list[i];

				// 单条工作的例子HTML
				var list_content = $("#my_demo").html();

				var createTime = obj['createTime'];

				var month = createTime.substring(0, 2);

				var day = createTime.substring(3, 5);

				var eid = obj['eid'];
				
				var remark = obj['remark'];
				if(remark == null || remark == 'null') {
					remark = "";
				}
				var remark25;
				if(remark.length > 25){
					 remark25 = remark.substring(0,25);
					 remark25 += "..."
				}else{
					remark25 = remark;
				}

				var list_html = list_content.replace('none', "block").replace(
						'##my_eid##', eid).replace('##my_title##', remark25).replace('##my_day##', day)
						.replace('##my_month##', month);

				var list_aa = list_html.replace('##my_eid##', eid);

				all_list += list_aa;

			}

			$("#my_list").html(all_list);
		},
		'error' : function() {
			layerMsg("数据加载失败");
		},
	});

}

// 查询发送给自己和自己创建的工作
function getMyWordAndReceive() {

	var path = $("#path").val();

	$.ajax({
		'url' : path + "/workRecord/findPageMyWordAndReceive/1.htm",
		'type' : "post",
		'async' : false,
		'dataType' : 'json',
		'success' : function(rst) {
			var list = rst.data;
			var ouer = rst.otherData.loginUser;

			var icon1 = '';

			// 默认头像
			if (ouer['icon'] == '' || ouer['icon'] == null
					|| ouer['icon'] == "null") {
				icon1 = path + '/chat/images/defaultSingleIcon.png';
			} else {
				icon1 = ouer['icon'];
			}

			$("#loginIcon").attr("src", icon1);
			$("#ddxUserName").text(ouer.name);

			if (list.length == 0) {
				 /*parent.createTips.message("暂无数据！", createTips.warn);*/
				layerMsg("暂无数据!", 120);
			}

			var all_list = "";

			for (var i = 0; i < list.length; i++) {
				var obj = list[i];

				// 单条工作的例子HTML
				var list_content = $("#demo").html();

				var createTime = obj['createTime'];
				var eid = obj['eid'];
				var icon = obj['send_icon'];
				var userId = obj['userId'];
				var uname = obj['send_name'];

				var remark = obj['remark'];
				if(remark == null || remark == 'null') {
					remark = "";
				}
				 var remark25;
					
				if(remark.length > 25){
					 remark25 = remark.substring(0,25);
					 remark25 += "..."
				}else{
					remark25 = remark;
				}
				
				
				var userIcon = '';

				// 默认头像
				if (icon == '' || icon == null || icon == "null") {
					userIcon = path + '/chat/images/defaultSingleIcon.png';
				} else {
					userIcon = icon;
				}

				var list_html = list_content.replace("##list_icon##", userIcon)
						.replace("##list_replyId##",
								eid).replace('##list_name##', uname).replace(
								"##list_time##", createTime)
								.replace('none',
								"block").replace('##list_remark##',remark25)
						.replace('##list_eid##', eid).replace('none',
						"block");

				all_list += list_html;

			}

			$("#list").html(all_list);
		},
		'error' : function() {
			layerMsg("数据加载失败");
		},
	});

};

//点击底部公司圈
function showgsq() {

	// 查询发送给自己和自己创建的工作
	getMyWordAndReceive();

	var list = $("#list");

	$.each(list, function(index, e) {

		// 评论输入框
		var textArea = $(e).find(".comment");
		// 回复按钮
		var reply = $(e).find(".btn");
		// 详情
		var detail = $(e).find(".comment-list");

		// 详情
		$(detail).click(function() {

			var id = $(this).attr("id");


			showDetail(id,1);

		});

		// 为每条数据父级添加鼠标获取焦点事件
		$(textArea).focus(function() {

			$(this).parent().addClass("text-box-on");

			$(this).val($(this).val() == '评论…' ? '' : $(this).val());

		});

		// 为每条数据父级添加鼠标失去焦点事件
		$(textArea).blur(function() {

			var value = $(this).val();

			if (value == '') {
				$(this).val("评论…");
				$(this).parent().removeClass("text-box-on");
			}

		});

		// 列表绑定回复处理事件
		$(reply).click(function() {

			var cssl = $(this).css("btn-off");

			// 按钮灰色不能提交(字数超过140)
			if (cssl == '') {

				// 获取评论内容
				var reply_content = $(this).prev().val();

				var $text_div = $(this);

				// 当前记录id
				// prev() 返回前一个同级dom 元素
				var eid = $(this).prev().attr("id");
				var path = $("#path").val();
				$.post(path + "/workRecord/record.htm", {
					"l_bid" : eid,
					"l_type" : 35,
					"s_remark" : reply_content
				}, function(rst) {

					if (rst.status == 200) {
						layerSuccMsg("评论成功", 180);

						$text_div.prev().val("评论…");
						$text_div.parent().removeClass("text-box-on");

					}
					;

				}, 'json');
			}

		});

		// 评论按键事件
		$(textArea).keyup(function() {
			var val = this.value;
			var len = val.length;
			var els = this.parentNode.children;
			var btn = els[1];
			var word = els[2];
			if (len <= 0 || len > 140) {
				btn.className = 'btn btn-off';
			} else {
				btn.className = 'btn';
			}
			word.innerHTML = len + '/140';
		});
	});

}


/**打开选择联系人窗口**/
var openSendframe = function(){
	
	var swtj_remark = $("#work_remark").val();
	
	if (swtj_remark.length == 0) {
		parent.createTips.message("内容不能为空！", createTips.warn);
		return false;
	}
	var param = {'content':projectName + "/DDX/selectSend.htm",'maxmin':false,'title':'请选择接收人',
			'area':['450px','350px']};
	if(only_im == 0) {
		param.offset = [($(window).height()+70-350)/2, ($(window).width()+221-450)/2];
	}
	$.openIframe(param);
}

var deleteFile = function(){
	
	var path = $("#path").val();
	var name = $("#fileNamesDe").val()
	
	if(name != ""){
		$.ajax({
			'url': path + "/annex/removeUpload.htm",
			'type': "post",
			'data':{"name": name},
			'dataType':'json',
			'success':function(data){},
			'error':function(xmlHttp,e1,e2){
				alert("系统异常，请稍后再试！");
			}
		});
	}
	
}

