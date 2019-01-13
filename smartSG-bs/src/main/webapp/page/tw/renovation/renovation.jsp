<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/gundongtiao.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/tools/layui-v2.3.0/layui.css"/>
	<script type="text/javascript" src="${ctx}/tools/My97DatePicker/WdatePicker.js"></script>
	<title>表格样式</title>
	<style>
		.div-input input[type="text"]{
			width: 250px;
		}
		#info b{
			color: red;
		}
		.file-upload {
			position: relative;
			overflow: hidden;
			border: 1px solid silver;
			display: inline-block;
			padding: 35px 52px;
			border-radius: 3px;
			margin: 10px;
			margin-left:65px;
			float: left;
		}
		.file-upload-input {
			position: absolute;
			width: 999px;
			height: 999px;
			top: 0;
			right: 0;
			cursor: pointer;
			border: 0px solid #BFBFC1;
		}
		.imgDiv{
			width: 160px;
			height: 90px;
			border: 1px solid silver;
			margin: 10px;
			margin-left:65px;
			position:relative;
			float: left;
		}
		.imgs{
			width: 160px;
			height: 90px;
		}
		.delete{
			width:20px;
			height:20px;
			border-radius:60%;
			position:absolute;
			top:-10px;
			right:-10px;
		}
		a{
			color: #0000EE;
		}
	</style>
</head>
<body>
<div class="mainRight-tab">
	<div class="mainRight-tab-conW" style="height: 590px;">
		<div class="tab-pages" style="height: 85%;">
			<div id="img_upload" style="margin-top: 0px;height:30%;">
				<span style="font-size: 13px;">
					<div style="float: left;height: 30px;">上传图片：</div>
					<div>
					请上传摊位图片，此阶段仅支持上传一张图片（以便左右滑动时展示不同摊位，建议最少包含一张店面图与一张店在食阁的位置示意图），图片格式仅限PNG、
					JPG、JPEG格式。每张最大不超过1M，最优尺寸比“宽：高=16：9”，不符合此尺寸的图片将自动压缩与调整，图片会产生边框或变形
					</div>
				</span>
				<div style="float: left;">
					<div id="imgD0" class="imgDiv" ${empty imgs[0] ? 'style="display: none;"':''}>
						<input type="hidden" id="imgId0" value="${imgs[0].eid}">
						<input type="hidden" id="imgName0" value="${imgs[0].name}">
						<img src="${imgs[0].path}" id="img0" class="imgs">
						<a href="javascript:void(0)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="imgDel(0)"/></a>
					</div>
					<div id="fileD0" class="file-upload" ${!empty imgs[0] ? 'style="display: none;"':''}>
						<span class="file-upload-text" id="text0">上传图片</span>
						<input name="files" class="file-upload-input" id="file0" type="file" accept="image/jpg,image/jpeg,image/png">
					</div>
					<div class="layui-progress layui-progress-big" id="jdtD0" style="width: 160px;margin-top: 50px;margin-left: 65px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
			</div>
			<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
				<div id="info" style="height: 75%;">
					<span>详情页字段（<b>*</b> 为必填字段）：</span>
					<div class="div-input gundong" style="overflow: auto;height: 93%;width: 93%;border: 0.5px solid silver;margin-top: 3px;">
						<input type="hidden" name="eid" value="${model.eid}">
						<input type="hidden" name="bid" value="${twId}">
						<table style="width: 80%;margin-left: 7%;text-align: left;border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td style="width: 15%;"><b>*</b>卫生：</td>
								<td><select style="width: 250px;" name="health" id="s_health">
									<option value="1" ${model.health == 1 ? 'selected':''}>A</option>
									<option value="2" ${model.health == 2 ? 'selected':''}>B</option>
									<option value="3" ${model.health == 3 ? 'selected':''}>C</option>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width: 15%;"><b>*</b>摊位类别：</td>
								<td><select style="width: 250px;" id="s_type" name="type">
									<option value="1" ${model.type == 1 ? 'selected':''}>HALAL</option>
									<option value="2" ${model.type == 2 ? 'selected':''}>NON-HALAL</option>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width: 15%;"><b>*</b>主营品种：</td>
								<td><select style="width: 250px;" id="s_varieties"></select>
									<input type="hidden" name="varieties" id="varieties" value="${model.varieties}">
								</td>
							</tr>
							<tr>
								<td><b>*</b>其它经营品种：</td>
								<td><input type="text" name="otherVarieties" value="${model.otherVarieties}"></td>
							</tr>
							<tr>
								<td><b>*</b>本店最低消费：</td>
								<td><input type="text" name="minimum" value="${model.minimum}"></td>
							</tr>
							<tr>
								<td style="width: 15%;"><b>*</b>排队规则：</td>
								<td><select style="width: 250px;" id="s_lineup" name="lineup">
									<option value="1" ${model.lineup == 1 ? 'selected':''}>到店排队</option>
									<option value="2" ${model.lineup == 2 ? 'selected':''}>下单排队</option>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width: 15%;"><b>*</b>顾客端点餐流程：</td>
								<td><select style="width: 250px;" id="s_procedure" name="procedure">
									<option value="3" ${model.procedure == 3 ? 'selected':''}>系统默认点餐流程</option>
									<option value="1" ${model.procedure == 1 ? 'selected':''}>“一餐多菜”点餐</option>
									<option value="2" ${model.procedure == 2 ? 'selected':''}>“单品”点餐</option>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width: 15%;"><b>*</b>顾客端菜单样式：</td>
								<td><select style="width: 250px;" id="s_style" name="style">
									<option value="1" ${model.style == 1 ? 'selected':''}>有分组样式</option>
									<option value="2" ${model.style == 2 ? 'selected':''}>无分组样式</option>
								</select>
								</td>
							</tr>
							<c:forEach items="${fields}" var="f" varStatus="s">
								<tr>
									<td>${f.name}：</td>
									<td>
										<input type="hidden" name="FieldList[${s.index}].eid" value="${f.eid}">
										<input type="text" name="FieldList[${s.index}].myValue" value="${f.myValue ne null and f.myValue != '' ? f.myValue : f.defaultValue}">
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</form>
		</div>
		<footer style="float: right;">
			<button class="pagination_btn btn" style="padding: 0px 0px;height: 36px;" id="setting_btn">设置自定义字段</button>
			<button class="pagination_btn submit-btn" id="save_btn">保<span class='wzkg'></span>存</button>
			<button class="pagination_btn btn" style="padding: 0px 0px;height: 36px;" id="copy_btn">复制并应用于</button>
		</footer>
	</div>
</div>
<div style="display: none;" alt="隐藏表单域">
	<input type="hidden" id="twId" value="${twId}">
	<input type="hidden" id="type" value="${type}">
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/tools/layui-v2.3.0/layui.js"></script>
<script type="text/javascript">
	var element;
	$(function() {
		var defaultVal = $("#varieties").val();
		var $s_varieties = $("#s_varieties");
		var bid = $("#twId").val();
		var s_varietiess = initTwVarieties($s_varieties, {
			flag: true,
			defaultVal: defaultVal
		});
		$s_varieties.change(function() {
			var eid = $(this).val();
			$("#varieties").val(eid);
		});
		layui.use('element', function(){
			element = layui.element;
		});

		$("#s_health").select2({minimumResultsForSearch: search_detault,});
		$("#s_type").select2({minimumResultsForSearch: search_detault,});
		$("#s_lineup").select2({minimumResultsForSearch: search_detault,});
		$("#s_procedure").select2({minimumResultsForSearch: search_detault,});
		$("#s_style").select2({minimumResultsForSearch: search_detault,});

		$("#save_btn").click(function() {
			$(this).prop('disabled', true);
			jsonAjax.post("${ctx}/tw/renovation/save.htm",$("#mainForm").serialize(), function(response) {
				if(response.status == 200) {
					$.prompt.message("保存成功！", $.prompt.ok);
					$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				} else {
					$.prompt.message("保存失败：" + response.msg, $.prompt.error);
					$(this).prop('disabled', false);
				}
			});
		});
		$("#setting_btn").click(function () {
			window.location.href = "${ctx}/sgField/list.htm?bid="+bid;
		})
		$("#copy_btn").click(function () {
			var type = $("#type").val();
			var url = "${ctx}/sg/to/apply.htm?bid="+bid+"&type="+type;
			parent.$.prompt.layerUrl2({url:url, width: 336, height: 498});
		})

		$(".imgs").each(function(index,e){
			$(e).click(function(){
				var str = $(e).attr("src");
				//放大5.5倍
				parent.$.prompt.layerHtml({content:"<img src='"+str+"' style='width: 880px;height: 495px;'>",width:880,height:495});
			})
		});

		$(".file-upload-input").each(function(index,e){
			$(e).change(function(){
				if($(e).val() != ""){
					fileLoad($(e),index);
				}
			});
		});
	});

	function imgDel(i){
		var eid = $("#imgId"+i).val();
		var name = $("#imgName"+i).val();
		var param = {eid: eid, name: name};
		jsonAjax.post("${ctx}/img/removeUpload.htm", param, function(response) {
			if(response.status == 200) {
				parent.$.prompt.message("删除图片成功！", $.prompt.ok);
				window.location.href = window.location.href;
			} else {
				parent.$.prompt.message("删除图片失败：" + response.msg, $.prompt.error);
			}
		});
	}

	function fileLoad(ele,i){
		var formData = new FormData();//创建一个formData对象
		var name = $(ele).val();// 获取传入元素的val
		var files = $(ele)[0].files[0];// 获取files
		var size = files.size;
		var maxSize = 1*1024*1024;
		if(size>maxSize){
			$.prompt.message("图片最大不超过1M！", $.prompt.error);
			return false;
		}
		var bid = $("#twId").val();// 获取摊位ID
		//将name 和 files 添加到formData中，键值对形式
		formData.append("files", files);
		formData.append("name", name);
		formData.append("bid", bid);
		formData.append("dtype", 2);
		$.ajax({
			url: "${ctx}/img/upload.htm",
			type: 'POST',
			data: formData,
			processData: false,// 告诉jQuery不要去处理发送的数据
			contentType: false,// 告诉jQuery不要去设置Content-Type请求头
			beforeSend: function (xmlHttp) {
				xmlHttp.loadIndex = layer.load();
				$("#jdtD"+i).show();
				element.progress('demo', '40%');
			},
			success: function (response) {
				element.progress('demo', '80%');
				$("#img"+i).attr("src", response.data.path);
				$("#imgId"+i).val(response.data.eid);
				$("#imgName"+i).val(response.data.name);
				element.progress('demo', '100%');
				$("#fileD"+i).hide();
				$("#imgD"+i).show();
				parent.$.prompt.message("图片上传成功！", $.prompt.ok);
				/*window.location.href = window.location.href;*/
			} ,
			error : function (response) {
				parent.$.prompt.message("图片上传失败：" + response.msg, $.prompt.msg);
			},
			complete: function(xmlHttp, textStatus) {
				layer.close(xmlHttp.loadIndex);
				$("#jdtD"+i).hide();
				element.progress('demo', '0%');
			}
		});
	}
</script>
</body>
</html>