<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/page/chat/work/css/css.css">

<!--右边主体开始-->
<style>
.ddxs{
background-color: #fff; height:auto !important; 
		 float: left;padding: 18px 0px;
}
</style>
<!--叮当享列表开始-->
	
	<!--中间内容开始11-->
	<div class="ddxs ddx_middle_GZ_GZQ_detail ddx_middle">
		<div style="padding: 15px;">
		<div style="float: left;"><img class="head" id="detail_icon" style="border-radius: 6px;"/></div>
		<div id="list_detail" style="float:left;width: 430px;">
			<div class="clearfix" style="font-size: 16px;line-height: 120%;color: #333;">
						<p class="txt" style="margin-top: -1px;font-size: 16px;">
							<span style="color:#3DA8F5;" id="send_name"></span>
						</p>
						<div class="comment-list">
							<div class="comment-box clearfix" user="self"
								style="border-top: none;white-space: normal;word-break: break-all;word-wrap: break-word;">
								<p
									style="margin-bottom: 5px; padding-right: 0%;"
									id="detail_remark"></p>
							</div>
						</div>
					<div class="info clearfix">
						<input id="detail_eid" type="hidden" >
						<input id="detail_isFile" type="hidden" >
						<span class="time" id="detail_time" style="padding-left: 0px;"></span>
						<span   id="cfileSpan" style="width: 16px;height: 16px;margin-left: 10px;margin-top: 2px;"><img src="${ctx}/images/icons/accessory_icon1.png" alt="" id="cfile"></span>					
					</div>
					<div id="detail_recordList">
					
					
					</div>
					<div class="text-box" style="margin-left: 0px;">
						<textarea class="comment"  style="margin-top: 20px;width: 100%;" autocomplete="off" maxlength="140" id="show_textarea" >评论…</textarea>
						<button class="btn" id="detail_reply">回 复</button>
						<span class="word"><span class="length">0</span>/140</span>
					</div>
			</div>
		</div>
		</div>
		<!--评论例子开始-->
		
		<div id="recordDemo" style="display: none;">
		<div class="comment-list" style="border-bottom: solid 1px #d9d9d9;">
			<div class="mytou" ><img class="myhead" src="##record_icon##" alt=""></div>
			<div class="mynei comment-box clearfix" user="self" style="display: none;" >  
				<div ><span class="user" style="color: #3DA8F5;">##record_userName##</span></div>
				<div>##record_remark##</div>
	            <div class="comment-content">
	                <div class="comment-time" style="font-size: 14px;margin-left: 60px;">
	                   ##record_time##
	                </div>
	            </div>
			</div> 
			<style>
			.mytou{
				margin-top: 9px;
				float: left;
			 }
			 .mynei{
			 width: 100%;
			 border-top: none;
			 white-space: normal;
			 word-break: break-all;
			 word-wrap: break-word;
			 }
				.myhead{
				float: left;
				width: 40px;
				height: 40px;
				margin-right: 10px;
				margin-left: 10px;
				}
			</style>
		</div>
        </div>
		<!-- 评论例子结束 -->
	</div>
	<!--中间内容结束-->
<!--右边主体结束-->