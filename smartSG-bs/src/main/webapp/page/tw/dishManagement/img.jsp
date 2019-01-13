<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/tools/layui-v2.3.0/layui.css"/>
	<title>表格样式</title>
	<style>
		.file-upload {
			position: relative;
			overflow: hidden;
			border: 1px solid silver;
			display: inline-block;
			padding: 102px 171px;
			border-radius: 3px;
			margin: 10px;
			margin-left:55px;
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
			width: 400px;
			height: 225px;
			border: 1px solid silver;
			margin: 10px;
			margin-left:55px;
			position:relative;
			float: left;
		}
		.imgs{
			width: 400px;
			height: 225px;
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
	<div class="mainRight-tab-conW">
		<div class="tab-pages" style="height: 87%;">
			<div style="text-align: center;font-size: 21px;font-weight: bold;">菜品图片</div>
			<div id="img_upload" style="margin-top: 10px;width: 100%;height: 70%;">
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
					<div class="layui-progress layui-progress-big" id="jdtD0" style="width: 400px;margin-top: 117px;margin-left: 55px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
				<div style="float: left;">
					<div id="imgD1" class="imgDiv" ${empty imgs[1] ? 'style="display: none;"':''}>
						<input type="hidden" id="imgId1" value="${imgs[1].eid}">
						<input type="hidden" id="imgName1" value="${imgs[1].name}">
						<img src="${imgs[1].path}" id="img1" class="imgs">
						<a href="javascript:void(1)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="imgDel(1)"/></a>
					</div>
					<div id="fileD1" class="file-upload" ${!empty imgs[1] ? 'style="display: none;"':''}>
						<span class="file-upload-text" id="text1">上传图片</span>
						<input name="files" class="file-upload-input" id="file1" type="file" accept="image/jpg,image/jpeg,image/png">
					</div>
					<div class="layui-progress layui-progress-big" id="jdtD1" style="width: 400px;margin-top: 117px;margin-left: 55px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
			</div>
			<div>
				<span>说明：每个菜品至少要上传一张，最多可上传两张图片，默认第一张为顾客端菜品列表以及其它地方菜品的展示图片，两张图可作为菜品详情页的轮播图。</span><br>
				<span>建议尺寸比例为16：9，若不满足该条件，自动按比例压缩，可能会有失真现象。</span>
			</div>
		</div>
		<footer style="float: right;">
			<%--<button class="submit-btn" id="save_btn">保<span class='wzkg'></span>存</button>--%>
			<button class="return_btn" id="return_btn">返<span class='wzkg'></span>回</button>
		</footer>
	</div>
</div>
<div style="display: none;" alt="隐藏表单域">
	<input type="hidden" id="bid" value="${eid}">
</div>

<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/layui-v2.3.0/layui.js"></script>
<script type="text/javascript">
	var element;
	$(function() {
		layui.use('element', function(){
			element = layui.element;
		});

		$("#return_btn").click(function() {
			closeWin();
		});

		/*$("#save_btn").click(function() {
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
		});*/

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
		var bid = $("#bid").val();// 获取菜品EID
		//将name 和 files 添加到formData中，键值对形式
		formData.append("files", files);
		formData.append("name", name);
		formData.append("bid", bid);
		formData.append("dtype", 3);
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
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
</script>
</body>
</html>