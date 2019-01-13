<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>叮当享聊天首页</title>


<!-- 应付工作模块样式 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/css/css.css">
<link rel="stylesheet" type="text/css" href="${ctx }/chat/css/registerCss.css">
<!--引入叮当享样式文件-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/css/ddxCSS.css">
<!--引入叮当享对话页面样式文件-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/css/talkCSS.css">
<!--引入叮当JS文件-->
<script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/ddx.js"></script>
<!-- 日期插件 -->
<script type="text/javascript" src="${ctx}/tools/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/ddx_gd/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/chat/js/ddx_gd/jquery.nicescroll.js"></script>
<!-- im -->
<script
	src="${pageContext.request.contextPath}/chat/im/lib/reconnecting-websocket.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/chat/im/lib/lib.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/chat/im/lib/md5.js"></script>

<%-- <script src="${pageContext.request.contextPath}/chat/im/lib/layer/layer.min.js"></script> --%>

<!-- dialogBox弹出层插件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/chat/im/lib/dialogBox/css/jquery.dialogbox.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/chat/im/lib/dialogBox/js/jquery.dialogBox.js"></script>
<!-- dialogBox弹出层插件 -->
<script type="text/javascript"
	src="${ctx}/tools/jqueryQrcode/jquery.qrcode.min.js"></script>
<%-- <script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/pcmdata.min.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/bitstring.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/ogg.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/libspeex.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/util.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/types.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/speex.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/codec.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/decoder.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/encoder.js"></script>
<script
	src="${pageContext.request.contextPath}/chat/im/lib/audio/audio.js"></script> --%>
<!-- im -->
<script type="text/javascript">
	
</script>

<style type="text/css">
.quanliaoxinxi {
	width: 65px;
	height: 95px;
	float: left;
	padding-top: 10px;
	padding-right: 10px;
	margin-right: 15px;
	margin-bottom: 15px;
	padding-left: 10px;
	border-radius: 4px;
}

.quanliaoxinxi_img {
	width: 65px;
	height: 65px;
}

.quanliaoxinxi_text {
	padding-top: 8px;
	text-align: center;
	display: block;
	font-size: 12px;
}
</style>

</head>

<body>
	<input type="hidden" id="path" value="${ctx}">
	<!--叮当享列表开始-->
	<div class="ddx_content" id="homePage">

		<!--------------------------------------------头部内容开始------------------------------------------>
		<div class="ddx_header">
			<!-- 返回 -->
			<div class="ddx_header_fh" style="display: none;"   id="return1" onclick="return1();" >
				 <img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
				 <input type="hidden" id="returnType">
			</div>
			<!-- 标题 -->
			<div class="ddx_header_wz" id="title">即时通讯</div>

			<!--头部右上角添加效果开始-->
			<div class="ddx_header_fdj" id="menu-wrap">
				<ul id="menu">
					<li><a href="#"><img
							src="${pageContext.request.contextPath}/chat/images/ddx/tj.png" /></a>
						<ul>
							<li><a href="#" onclick="openSelectContactIframe()"> <img
									src="${pageContext.request.contextPath}/chat/images/ddx/tianjia/01.png"
									style="position: relative; top: 5px; padding-right: 10px; height: 18px; width: 18px">
									<span id="title_FQQL">发起群聊</span> <input hidden="hidden"
									id="selectContactsImAccount" />
							</a></li>
							<%-- <li><a href="#" onclick="addFriend()"> <img
									src="${pageContext.request.contextPath}/chat/images/ddx/tianjia/02.png"
									style="position: relative; top: 5px; padding-right: 10px; height: 18px; width: 18px">
									<span id="title_TJPY">添加朋友</span>
							</a></li> --%>

						</ul></li>
				</ul>
			</div>
			<!--头部右上角添加效果结束-->
			
			<!--头部右上角添加效果开始-->
			<div class=ddx_header_fdj1 id="menu-gsq" style="display: none;" onclick="saveWork();">
				<a href="#"><img width="22"
							src="${pageContext.request.contextPath}/chat/images/ddx/work/publish.png" /></a>
			</div>
			<div  id="sendBtton" 
				style="font-size: 18px; margin-top: 15px;float:right;  margin-right:16px;  cursor: pointer;display: none;color: #FFFFFF;"
				onclick="openSendframe()">发送</div> 
		</div>
		<!-------------------------------------------------头部内容结束------------------------------------------>



		<!-------------------------------------------------中间内容开始------------------------------------------>

		<!-- 叮当享内容开始 -->
		<div class="ddx_middle" id="content_DDX">

		</div>
		<!-- 叮当享内容结束 -->

		<!-- 通话内容开始 -->
		<div class="ddx_middle" id="content_TH" style="display: none;">

		</div>
		<!-- 通话内容结束 -->

		<!-- 通讯录内容开始 -->
		<div class="ddx_middle" id="content_TXL" style="display: none;">

			<%-- <a href="#" id="label_TXL_XDPY">
				<div class="ddx_middle1">
					<div class="ddx_middle1tp">
						<img
							src="${pageContext.request.contextPath}/chat/images/tongxunlu/01.png"
							style="border-radius: 6px; width: 50px; height: 50px;">
						<!--图片头像大小显示为50px*50px的，要控制下-->
					</div>
					<div class="ddx_middle1nr">
						<div class="ddx_middle1nr1" style="margin-top: 15px;">新的朋友</div>
					</div>
				</div>
			</a>  --%>
			<a href="#" id="label_TXL_XDTS">
				<div class="ddx_middle1">
					<div class="ddx_middle1tp">
						<img
							src="${pageContext.request.contextPath}/chat/images/tongxunlu/01.png"
							style="border-radius: 6px; width: 50px; height: 50px;">
					</div>
					<div class="ddx_middle1nr">
						<div class="ddx_middle1nr1" style="margin-top: 15px;">新的同事</div>
					</div>
				</div>
			</a> 
			<a href="#" id="label_TXL_GSTXL">
				<div class="ddx_middle1">
					<div class="ddx_middle1tp">
						<img
							src="${pageContext.request.contextPath}/chat/images/tongxunlu/contactlist.png"
							style="border-radius: 6px; width: 50px; height: 50px;">
					</div>
					<div class="ddx_middle1nr">
						<div class="ddx_middle1nr1" style="margin-top: 15px;">
						<c:choose>
						<c:when test="${USER_IN_SESSION.company.isSpec eq 1}">
							通讯录
						</c:when>
						<c:otherwise>
							公司通讯录
						</c:otherwise>
						</c:choose>	
						</div>
					</div>
				</div>
			</a> 
			<a href="#" id="label_TXL_GZQL">
				<div class="ddx_middle1">
					<div class="ddx_middle1tp">
						<img
							src="${pageContext.request.contextPath}/chat/images/tongxunlu/02.png"
							style="border-radius: 6px; width: 50px; height: 50px;">
						<!--图片头像大小显示为50px*50px的，要控制下-->
					</div>
					<div class="ddx_middle1nr">
						<div class="ddx_middle1nr1" style="margin-top: 15px;">工作群聊</div>
					</div>
				</div>
			</a>

			<a href="#" id="label_TXL_CXTD">
				<div class="ddx_middle1">
					<div class="ddx_middle1tp">
						<img
							src="${pageContext.request.contextPath}/chat/images/tongxunlu/04.png"
							style="border-radius: 6px; width: 50px; height: 50px;">
						<!--图片头像大小显示为50px*50px的，要控制下-->
					</div>
					<div class="ddx_middle1nr">
						<div class="ddx_middle1nr1" style="margin-top: 15px;">
						<c:choose>
						<c:when test="${USER_IN_SESSION.company.isSpec eq 1}">
							城南服务
						</c:when>
						<c:otherwise>
							云境服务
						</c:otherwise>
						</c:choose>	
						</div>
					</div>
				</div>
			</a>


			<div id="friend_content_TXL"></div>
			<div id="strange_content_TXL"></div>
		</div>
		<!-- 通讯录内容结束 -->

		<!-- 工作内容开始 -->
		 <div  class="ddx_middle" id="content_GZ" style="display: none;">
			<!-- 自己和别人列表 -->
			<%@ include file="/page/chat/work/index0.jsp"%>
		</div> 
		 <div class="ddx_middle" id="content_GZXQ" style="display: none;">
			<!-- 详情 -->
			<%@ include file="/page/chat/work/index2.jsp"%>
		</div> 
		 <div class="ddx_middle" id="content_GZMY" style="display: none;">
			<!-- 自己的发送的工作列表 -->
			<%@ include file="/page/chat/work/index3.jsp"%>
		</div> 
		 <div class="ddx_middle" id="content_GZSAVE" style="display: none;">
			<!-- 新增页面 -->
			<%@ include file="/page/chat/work/index4.jsp"%>
		</div> 
		<!-- 工作内容结束 -->

		<!-- 我内容开始 -->
		<div class="ddx_middle" id="content_W" style="display: none;">

			<div
				style="width: 500px; background-color: #f1f0f6; float: left; padding: 5px 15px 5px 15px; color: #999999; height: 10px;"></div>

			<a href="#" id="item_W_info">
				<div class="ddx_middle1" style="border-bottom: none; height: 102px;">
					<span class="to_item_W_info">
						<div class="ddx_middle1tp" style="width: 80px; height: 80px;">
							<img id="item_W_info_icon"
								src="${pageContext.request.contextPath}/chat/images/defaultSingleIcon.png"
								style="border-radius: 4px; width: 80px; height: 80px;">
							<!--图片头像大小显示为80px*80px的，要控制下-->
						</div>
						<div class="ddx_middle1nr" style="width: 300px;">
							<div class="ddx_middle1nr1"
								style="width: 300px; margin-top: 5px;">
								<span id="item_W_info_name"></span>
							</div>
							<div class="ddx_middle1nr2"
								style="width: 300px; color: #5b5b5b; font-size: 14px;"></div>
						</div>
					</span>
					<div class="ddx_middle1sj"
						style="padding-top: 8px; height: 80px; margin-left: 29px; width: 80px;">
						<div class="to_item_W_info"
							style="height: 40px; width: 75px; float: left; margin-top: 18px;">
							<img
								src="${pageContext.request.contextPath}/chat/images/wo/jt.png" />
						</div>
					</div>
					<div id="qrcode_hide_div" style="display: none;">
						<span id="qrcode_hide"> </span>
					</div>
				</div>
			</a>

			<div
				style="width: 500px; background-color: #f1f0f6; float: left; padding: 5px 15px 5px 15px; color: #999999; height: 10px; border-bottom: solid 1px #d9d9d9;"></div>
			<a href="#" id="label_W_XGMM">
				<div class="ddx_middle1">
					<div class="ddx_middle1tp">
						<img
							src="${pageContext.request.contextPath}/chat/images/wo/09.png"
							style="width: 50px; height: 50px;">
						<!--icon大小显示为50px*50px的，要控制下-->
					</div>
					<div class="ddx_middle1nr">
						<div class="ddx_middle1nr1" style="margin-top: 15px;">修改密码</div>
					</div>
					<div class="ddx_middle1sj" style="padding-top: 8px; width: 80px;">
						<div
							style="height: 40px; width: 30px; float: left; margin-left: 45px;">
							<img
								src="${pageContext.request.contextPath}/chat/images/wo/jt.png" />
						</div>
					</div>
				</div>
			</a>

		</div>
		<!-- 我内容结束 -->
		<!--------------------------------------------------中间内容结束-------------------------------------------->


		<!------------------------------------------------底部内容开始--------------------------------------------->
		<div class="ddx_footer">
			<a href="#" id="main_DDX">
				<div class="ddx_footer1"
					style="margin-left: 13px; margin-right: 13px;">
					<div class="ddx_footer1icon">
						<img
							src="${pageContext.request.contextPath}/chat/images/ddx/ddx01.png">
					</div>
					<div class="ddx_footer1wz">
						<span id="title_DDX">即时通讯</span>
					</div>
				</div>
			</a> <a href="#" id="main_TXL">
				<div class="ddx_footer1"
					style="margin-left: 13px; margin-right: 13px;">
					<div class="ddx_footer1icon">
						<img
							src="${pageContext.request.contextPath}/chat/images/ddx/ddx03.png">
					</div>
					<div class="ddx_footer1wz">
						<span id="title_TXL">通讯录</span>
					</div>
				</div>
			</a> <a href="#" id="main_GZ">
				<div class="ddx_footer1"
					style="margin-left: 13px; margin-right: 13px;">
					<div class="ddx_footer1icon">
						<img
							src="${pageContext.request.contextPath}/chat/images/ddx/ddx06.png">
					</div>
					<div class="ddx_footer1wz">
						<span id="title_GZ">
						<c:choose>
						<c:when test="${USER_IN_SESSION.company.isSpec eq 1}">
							工作圈
						</c:when>
						<c:otherwise>
							公司圈
						</c:otherwise>
						</c:choose>	
						</span>
					</div>
				</div>
			</a>
			<a href="#" id="main_W">
				<div class="ddx_footer1" style="margin-left: 13px;">
					<div class="ddx_footer1icon">
						<img
							src="${pageContext.request.contextPath}/chat/images/ddx/ddx05.png">
					</div>
					<div class="ddx_footer1wz">
						<span id="title_W">我</span>
					</div>
				</div>
			</a>
		</div>
		<!--------------------------------------------------底部内容结束------------------------------------------->


	</div>
	<!--叮当享列表结束-->

	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";

		$(function() {

			var s = "";
			var theDate = new Date();
			s += theDate.getFullYear() + "-";
			s += (theDate.getMonth() + 1) + "-";
			s += theDate.getDate();

			$(".time").val(s);
		});

		var uploadIndex = -1;

		/** ------------上传附件-----------* */
		function upload(fileNames, fileInfos) {
			if (uploadIndex != -1) {
				layer.restore(uploadIndex);
			} else {
				uploadIndex = layer
						.open({
							title : "附件上传",
							type : 2,
							area : [ '60%', '60%' ],
							offset : [
									($(window).height() + 70 - ($(window)
											.height() * 0.6)) / 2,
									($(window).width() + 221 - ($(window)
											.width() * 0.6)) / 2 ],
							maxmin : true,// true=不显示最大最小化按钮
							closeBtn : 1,// 0=不显示关闭按钮
							content : path
									+ '/page/upload.jsp?isFrame=true&fileNames='
									+ fileNames + '&fileInfos=' + fileInfos+"&acceptType=1",
							cancel : function() {
								uploadIndex = -1;
							}
						});
			}
		};
	</script>
	<!-- 公司圈 -->

	<!-- 新的朋友页面 -->
	<%-- <div class="ddx_content" id="page_TXL_XDPY" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="main_TXL_page"
				onclick="changeToHomePage('main_TXL_page','page_TXL_XDPY')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_TXL_XDPY">新的朋友</div>
		</div>
		<div class="ddx_middle ddx_middle_txl" id="pageContent_TXL_XDPY">
			<!-- 列表内容 -->
		</div>
	</div> --%>
	<!-- 新的朋友页面 -->
	
	<!-- 新的朋友页面 -->
	<div class="ddx_content" id="page_TXL_XDTS" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="main_TXL_page"
				onclick="changeToHomePage('main_TXL_page','page_TXL_XDTS')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_TXL_XDTS">新的同事</div>
		</div>
		<div class="ddx_middle ddx_middle_txl" id="pageContent_TXL_XDTS">
			<!-- 列表内容 -->
		</div>
	</div>
	<!-- 新的朋友页面 -->

	<!-- 公司通讯录 -->
	<div class="ddx_content" id="page_TXL_GSTXL" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="main_TXL_page"
				onclick="changeToHomePage('main_TXL_page','page_TXL_GSTXL')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_TXL_GSTXL">公司通讯录</div>
		</div>
		<div class="ddx_middle ddx_middle_txl" id="pageContent_TXL_GSTXL">
			<!-- 列表内容 -->
		</div>
	</div>
	<!-- 公司通讯录 -->
	
	<!-- 部门通讯录 -->
	<div class="ddx_content" id="page_TXL_GSTXL_BMTXL" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="page_TXL_GSTXL_BMTXL"
				onclick="changeToUpOneLevelPage('page_TXL_GSTXL_BMTXL')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_TXL_GSTXL_BMTXL">部门通讯录</div>
		</div>
		<div class="ddx_middle ddx_middle_txl" id="pageContent_TXL_GSTXL_BMTX">
			<!-- 列表内容 -->
		</div>
	</div>
	<!-- 公司通讯录 -->
	
	
	<!-- 工作群聊页面 -->
	<div class="ddx_content" id="page_TXL_GZQL" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="main_TXL_page"
				onclick="changeToHomePage('main_TXL_page','page_TXL_GZQL')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_TXL_GZQL">工作群聊</div>
		</div>
		<div class="ddx_middle ddx_middle_txl" id="pageContent_TXL_GZQL"
			style="height: 604px;">
			<!-- 列表内容 -->
			<div></div>
		</div>
	</div>
	<!-- 工作群聊页面 -->

	<!-- 工作群聊页面 -->
	<div class="ddx_content" id="page_TXL_CXTD" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="main_TXL_page"
				onclick="changeToHomePage('main_TXL_page','page_TXL_CXTD')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_TXL_CXTD">
			<c:choose>
				<c:when test="${USER_IN_SESSION.company.isSpec eq 1}">
					城南客服
				</c:when>
				<c:otherwise>
					超享团队
				</c:otherwise>
			</c:choose>
			</div>
		</div>
		<div class="ddx_middle ddx_middle_txl" id="pageContent_TXL_CXID"
			style="height: 604px;">
			<!-- 列表内容 -->
		</div>
	</div>
	<!-- 工作群聊页面 -->

	<!-- 邀请注册页面 -->
	<div class="ddx_content" id="page_W_YQJR" name="page"
		style="display: none;">
		<div class="ddx_header">
			<div class="ddx_header_fh" name="main_W_page"
				onclick="changeToHomePage('main_W_page','page_W_YQJR')">
				<img src="${pageContext.request.contextPath}/chat/images/ddx/fh.png">
			</div>
			<div class="ddx_header_wz2" id="pageTitle_W_YQJR">邀请加入</div>
		</div>
		<div class="ddx_middle" id="pageContent_W_YQJR"
			style="height: 604px; text-align: center; margin-right: auto; margin-left: auto;">
			<%--<img alt="" src="${pageContext.request.contextPath}/DDX/inviteRegister.htm" style="width: 300px;height: 300px;margin-top: 100px;">--%>
			<div style="margin-top: 50px;">
				<h3>扫一扫二维码</h3>
			</div>
			<!-- 列表内容 -->
		</div>
	</div>
	<!-- 邀请加入页面 -->



	<!--客服小条条开始-->
	<div style="display: none;">
		<div class="ddx_kefu" id="ddx_kefu">
			<div class="ddx_kefuL">
				<a href="#"><img
					src="${pageContext.request.contextPath}/chat/images/index/lyl.png" /></a>
				<div id="kefu_DDX_RedDot"
					style="position: relative; top: -40px; left: 55px; width: 10px; height: 10px; border-radius: 180px; background-color: #FF0000; display: none;"></div>
			</div>

			<div class="ddx_kefuR">
				<a href="#"><img
					src="${pageContext.request.contextPath}/chat/images/ddx/DDXgt.png"></a>
			</div>
		</div>
	</div>
	<!--客服小条条结束-->

	<!-- 用于dialogBox插件 -->
	<div id="dialogBox"></div>
	
</body>
</html>