<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script>
$(function(){

	//附件
	$("#showFile").click(function() {
		$.prompt.layerUpload({
			title : "选择附件",
			isFrame : 1,
			fileInfos:"work_annex",
			fileNames:"showFile",
		});
	});


});


</script>
<style type="text/css">
.LT_con {
	clear: both;
}

.LT_con p {
	line-height: 1.5em;
	font-size: 1.2em/1.5em;
	color: #646464;
	margin: 0px;
}

.pTop25 {
	padding-top: 25px !important;
}

.pBottom25 {
	padding-bottom: 10px !important;
}

.LT_share {
	width: 96%;
	padding: 0 2%;
}

.LT_share_top {
	width: 96%;
	padding: 15px 5px 0px 5px;
}

.LT_share_wz {
	width: 100%;
	font-size: 14px;
}

.LT_share_wzL {
	width: 49%;
	float: left;
}

.LT_share_wzr {
	width: 49%;
	float: right;
}

.LT_share_wzLong {
	width: 100%;
	float: left;
}

.LT_hr1 {
	height: 1px;
	width: 100%;
	background-color: #e4e4e4;
	margin: 0.4em 0;
}
.ddx_content_d{
	height: 322px !important; overflow-y: hidden;overflow-x: hidden;
}
</style>
	<div class="ddx_content_d ddx_content" name="page" >
		<div class="ddx_middle" id="pageContent_GZ_SWTJ">
			<form id="SWTJ_form">
				<!--表格分享开始-->
				<div class="pBottom25"></div>
				<div class="LT_share">

					<div class="LT_share_top">

						<div class="LT_share_wz">
						    <div style="float: left;">内容：</div>
						    
						    <div style="float: left;">
								<textarea rows="10"  id="work_remark" name="remark" maxlength="250"    style="resize: none;border: 0px;width: 430px;"></textarea>
							</div>
						   
						</div>
						<div class="LT_con"></div>
						<div class="LT_hr1"></div>
						
						<div class="yj-add-divR text_right">
							<textarea rows="5" cols="" id="work_annex" name="annex"
						style="width: 100%; display: none"></textarea>
						<input type="hidden" id="fileNamesDe">
							附件：<span style="margin:0px 30px 0px 5px; cursor: pointer; " id="showFile">
							<img src="${ctx}/images/icons/accessory_icon1.png" alt="" id="work_fileImg">
						</span>
						</div>
						<div class="LT_con"></div>
						

					</div>
				</div>

				<div class="pBottom25"></div>
			</form>
			<!--表格分享结束-->
		</div>
	</div>
	<!-- 事务报告页面 -->