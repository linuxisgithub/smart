<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/page/chat/work/css/css.css">

<!--右边主体开始-->

<!--叮当享列表开始-->
<div class="ddx_content" style="margin-top: 0px;border-top-color: #3DA8F6;">
	<!--头部图片背景开始-->
	<div id="my_work_img"
		style="width:101%;height:140px;margin:0 auto;float: left; background-image:url(${pageContext.request.contextPath}/page/chat/work/images/work_qBG.jpg);"
		alt="背景图">
		<div id="my_loginName"
			style="width: 120px; height: 30px; float: left; border: 1px soild #000; position: relative; left: 295px; top: 110px; text-align: right; color: #333;"></div>
		<span style="position: absolute;top: 107px;left: 344px;color: #fff;" id="ddxmyUserName"></span>
		<img id="my_loginIcon" 
			style="border-radius: 6px;width: 75px; height: 75px;position: absolute; top: 93px; left: 428px;" />
	</div>
	<div
		style="width: 100%; height: 0px; margin: 0 auto; float: left; background-color: #fff;"></div>
	<!--头部图片背景结束-->

	<!--中间内容开始-->
	<div class="ddx_middle ddx_middle_GZ_GZQ_self" id="ddx_middle"
		style="background-color: #fff; height: 405px; width: 500px; overflow-y:scroll;overflow-x:hidden;">

		<!---->
		<div class="chat-discussion" style="padding: 15px;">
			<div id="my_list">

			</div>
		</div>
		<!---->
	</div>


	<!--底部内容开始-->
	<!--<div class="ddx_footer" style="height:90px;">
		  
		  </div>-->
	<!--底部内容结束-->
	
	<!-- 例子开始 -->
	<div id="my_demo" style="display: none;">
	
		<div class=" clearfix" >
					<div style="width: 75px; height: 60px; float: left;">
						<div
							style="width: 30px; height: 60px; float: left; font-size: 28px;">##my_day##</div>
						<div
							style="width: 35px; height: 50px; float: left; margin-top: 16px;margin-left: 10px;">##my_month##月</div>
					</div>
					<div  style="margin-left: 10px;float: left;width: 83%;">
						<div style="margin: 15px 15px 0 15px;margin-bottom: 10px;">
							<div class="comment-list"  btype="##my_btype##"   id="##my_eid##" style="cursor: pointer;">
								<div class="comment-box clearfix" user="self"
									style="border-top: none;white-space: normal;word-break: break-all;word-wrap: break-word;">
									<div class="comment-content">
										<p class="comment-text"
											style="margin-top: 8px; margin-bottom: 8px;">##my_title##</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
	</div>
	<!-- 例子结束 -->

</div>
<!--叮当享列表结束-->

<div style="clear: both;"></div>


<!--右边主体结束-->