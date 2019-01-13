<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/page/chat/work/css/css.css">
<%-- <script type="text/javascript" src="${ctx}/page/chat/work/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/page/chat/work/js/jquery.nicescroll.js"></script> --%>

<!--头部图片背景开始-->
<div
	style="width:100%;height:140px;margin:0 auto;float: left; background-image:url(${pageContext.request.contextPath}/page/chat/work/images/work_qBG.jpg);"
	alt="背景图">
	<div id="loginName"
		style="width: 120px; height: 30px; float: left; border: 1px soild #000; position: relative; left: 295px; top: 110px; text-align: right; color: #333;"></div>
	<span style="position: absolute;top: 107px;left: 344px;color: #fff;" id="ddxUserName"></span>
	<img id="loginIcon"
		style="border-radius: 6px; width: 75px; height: 75px; position: absolute; top: 93px; left: 428px; cursor: pointer;" />
</div>
<!--头部图片背景结束-->
<!--中间内容开始-->
<div id="ddx_middle_gsq"
	style="background-color: #fff; height: auto !important; width: 530px;">
	<div class="chat-discussion" style="padding: 15px;"">
		<div id="list"></div>
	</div>
</div>
<!--中间内容结束-->
<div id="demo">
	<!--叮当享列表结束-->
	<div style="display: none;"> <img class="head" src="##list_icon##" alt=""
			style="width: 60px; height: 60px; margin-top: 14px;border-radius: 6px;" /></div>
	<div class="clearfix" style="display: none;overflow: hidden;position: relative;left: -12px;" >
		<div>
			<div class="main">
				<p class="txt" style="margin-top: 15px;font-size: 16px;">
					<span style="color:#3DA8F5;">##list_name##</span>
				</p>
				<div class="comment-list" style="cursor: pointer;" 
					id="##list_replyId##">
					<div class="comment-box clearfix" user="self"
						style="border-top: none;">
						<div class="comment-content">
							<p class="comment-text">##list_remark##</p>
						</div>
					</div>
				</div>
			</div>
			<div class="info clearfix">
				<span class="time">##list_time##</span>
			</div>
			<div class="text-box" style="margin-left: 15px;">
				<textarea  style="width: 95%;"  class="comment" maxlength="140" autocomplete="off"
					 id="##list_eid##">评论…</textarea>
				<button class="btn" style="margin-right: 5%;">回 复</button>
				<span class="word"><span class="length">0</span>/140</span>
			</div>
		</div>
	</div>
</div>
<!--右边主体结束-->