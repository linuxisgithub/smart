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
		.div-input b{
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
			margin-left:30px;
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
			margin-left:30px;
			position:relative;
			float: left;
		}
		.imgs{
			width: 160px;
			height: 90px;
			/*width: 10rem;
			height: 5.63rem;*/
			/*object-fit: contain;*/
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
			<div id="img_upload" style="margin-top: 0px;height: 26%;">
				<span style="font-size: 13px;">上传图片：请上传食阁图片，最少1张，最多5张，仅限JPG、JPEG、PNG格式，每张最大不超过2M，最优尺寸比“宽：高=16：9”，不符合此尺寸的图片,将自动压缩与调整。</span>
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
					<div class="layui-progress layui-progress-big" id="jdtD0" style="width: 160px;margin-top: 50px;margin-left: 30px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
				<div style="float: left;">
					<div id="imgD1" class="imgDiv" ${empty imgs[1] ? 'style="display: none;"':''}>
						<input type="hidden" id="imgId1" value="${imgs[1].eid}">
						<input type="hidden" id="imgName1" value="${imgs[1].name}">
						<img src="${imgs[1].path}" id="img1" class="imgs">
						<a href="javascript:void(0)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="imgDel(1)"/></a>
					</div>
					<div id="fileD1" class="file-upload" ${!empty imgs[1] ? 'style="display: none;"':''}>
						<span class="file-upload-text">上传图片</span>
						<input name="files" class="file-upload-input" id="file1" type="file" accept="image/jpg,image/jpeg,image/png">
					</div>
					<div class="layui-progress layui-progress-big" id="jdtD1" style="width: 160px;margin-top: 50px;margin-left: 30px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
				<div style="float: left;">
					<div id="imgD2" class="imgDiv" ${empty imgs[2] ? 'style="display: none;"':''}>
						<input type="hidden" id="imgId2" value="${imgs[2].eid}">
						<input type="hidden" id="imgName2" value="${imgs[2].name}">
						<img src="${imgs[2].path}" id="img2" class="imgs">
						<a href="javascript:void(0)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="imgDel(2)"/></a>
					</div>
					<div id="fileD2" class="file-upload" ${!empty imgs[2] ? 'style="display: none;"':''}>
						<span class="file-upload-text">上传图片</span>
						<input name="files" class="file-upload-input" id="file2" type="file" accept="image/jpg,image/jpeg,image/png">
					</div>
					<div class="layui-progress layui-progress-big" id="jdtD2" style="width: 160px;margin-top: 50px;margin-left: 30px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
				<div style="float: left;">
					<div id="imgD3" class="imgDiv" ${empty imgs[3] ? 'style="display: none;"':''}>
						<input type="hidden" id="imgId3" value="${imgs[3].eid}">
						<input type="hidden" id="imgName3" value="${imgs[3].name}">
						<img src="${imgs[3].path}" id="img3" class="imgs">
						<a href="javascript:void(0)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="imgDel(3)"/></a>
					</div>
					<div id="fileD3" class="file-upload" ${!empty imgs[3] ? 'style="display: none;"':''}>
						<span class="file-upload-text">上传图片</span>
						<input name="files" class="file-upload-input" id="file3" type="file" accept="image/jpg,image/jpeg,image/png">
					</div>
					<div class="layui-progress layui-progress-big" id="jdtD3" style="width: 160px;margin-top: 50px;margin-left: 30px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
				<div style="float: left;">
					<div id="imgD4" class="imgDiv" ${empty imgs[4] ? 'style="display: none;"':''}>
						<input type="hidden" id="imgId4" value="${imgs[4].eid}">
						<input type="hidden" id="imgName4" value="${imgs[4].name}">
						<img src="${imgs[4].path}" id="img4" class="imgs">
						<a href="javascript:void(0)"><img src="${ctx}/images/icons/clear_icon.png" class="delete" onclick="imgDel(4)"/></a>
					</div>
					<div id="fileD4" class="file-upload" ${!empty imgs[4] ? 'style="display: none;"':''}>
						<span class="file-upload-text">上传图片</span>
						<input name="files" class="file-upload-input" id="file4" type="file" accept="image/jpg,image/jpeg,image/png">
					</div>
					<div class="layui-progress layui-progress-big" id="jdtD4" style="width: 160px;margin-top: 50px;margin-left: 30px;display: none" lay-filter="demo" lay-showPercent="yes">
						<div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div>
					</div>
				</div>
			</div>
			<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
				<div id="info" style="height: 79%;">
					<span>食阁详情页字段设置：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" id="toMap">点击此处可自动链接至地图/手动设置地图位置</a></span>
					<div class="div-input gundong" style="overflow: auto;height: 93%;width: 93%;border: 0.5px solid silver;margin-top: 3px;">
						<input type="hidden" name="eid" value="${model.eid}">
						<input type="hidden" id="bid" value="${model.deptId}">
						<table style="width: 80%;margin-left: 7%;text-align: left;border-collapse:separate; border-spacing:0px 10px;">
							<tr>
								<td style="width: 15%;"><b>*</b>HALAL：</td>
								<td><select style="width: 250px;" id="s_halal" name="halal">
									<option value="1" ${model.halal == 1 ? 'selected':''}>HALAL</option>
									<option value="2" ${model.halal == 2 ? 'selected':''}>NON-HALAL</option>
									<option value="3" ${model.halal == 3 ? 'selected':''}>MIX</option>
								</select>
								</td>
							</tr>
							<tr>
								<td><b>*</b>地址：</td>
								<td><input type="text" name="address" id="address" value="${model.address}"></td>
							</tr>
							<tr>
								<td><b>*</b>邮编：</td>
								<td><input type="text" name="zipCode" id="zipCode" value="${model.zipCode}"></td>
							</tr>
							<tr>
								<td><b>*</b>类型：</td>
								<td><select style="width: 250px;" id="s_type"></select>
									<input type="hidden" name="type" id="type" value="${model.type}"></td>
								</td>
							</tr>
							<tr>
								<td><b>*</b>摊位数：</td>
								<td><input type="text" name="activationNum" value="${model.activationNum}" style="border-width: 0px;" readonly></td>
							</tr>
							<tr>
								<td><b>*</b>营业时间：</td>
								<td><input type="text" name="startTime" value="${model.startTime}" style="width: 30px;min-width: 105px;" class="Wdate" onfocus="WdatePicker({readOnly:true,dateFmt:'HH : mm'})">&nbsp;至&nbsp;&nbsp;
									<input type="text" name="endTime" value="${model.endTime}"style="float: none;width: 30px;min-width: 105px;" class="Wdate" onfocus="WdatePicker({readOnly:true,dateFmt:'HH : mm'})"></td>
							</tr>
							<c:forEach items="${fields}" var="f" varStatus="s">
								<tr>
									<td>${f.name}：</td>
									<td>
										<input type="hidden" name="sgFieldList[${s.index}].eid" value="${f.eid}">
										<input type="text" name="sgFieldList[${s.index}].myValue" value="${f.myValue ne null and f.myValue != '' ? f.myValue : f.defaultValue}">
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
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/tools/layui-v2.3.0/layui.js"></script>
<script type="text/javascript">
	var element;
	$(function() {
		var defaultVal = $("#type").val();
		var $s_type = $("#s_type");
		var bid = $("#bid").val();
		var s_types = initSgType($s_type, {
			flag: true,
			defaultVal: defaultVal
		});
		$s_type.change(function() {
			var eid = $(this).val();
			$("#type").val(eid);
		});
		layui.use('element', function(){
			element = layui.element;
		});

		$("#s_halal").select2({minimumResultsForSearch: search_detault,});

		$("#save_btn").click(function() {
			$(this).prop('disabled', true);
			jsonAjax.post("${ctx}/sg/sgInfo/save.htm",$("#mainForm").serialize(), function(response) {
				if(response.status == 200) {
					$.prompt.message("保存成功！", $.prompt.ok);
					$("#content_iframe").attr("src",parent.$("#content_iframe").attr("src"));
				} else {
					$.prompt.message("保存失败：" + response.msg, $.prompt.error);
					$(this).prop('disabled', false);
				}
			});
		});
		$("#toMap").click(function() {
			$.prompt.layerUrl("${ctx}/sg/to/map.htm",1050,500);
		});
		$("#setting_btn").click(function () {
			window.location.href = "${ctx}/sgField/list.htm?bid="+bid;
		})
		$("#copy_btn").click(function () {
			var url = "${ctx}/sg/to/apply.htm?bid="+bid+"&type=sggl";
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
				/*	$("#img"+i).attr("src", "");
                    $("#imgId"+i).val("");
                    $("#imgName"+i).val("");
                    $("#fileD"+i).show();
                    $("#imgD"+i).hide();*/
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
		var maxSize = 2*1024*1024;
		if(size>maxSize){
			$.prompt.message("图片最大不超过2M！", $.prompt.error);
			return false;
		}
		var bid = $("#bid").val();// 获取食阁ID
		//将name 和 files 添加到formData中，键值对形式
		formData.append("files", files);
		formData.append("name", name);
		formData.append("bid", bid);
		formData.append("dtype", 1);
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


	/*	$("#file0").change(function(){
        /!*var objUrl = getObjectURL(this.files[0]) ;//获取文件信息
        //console.log("objUrl = "+objUrl);
        if (objUrl) {
            $("#img0").attr("src", objUrl);
            $("#fileD0").hide();
            $("#imgD0").show();
        }*!/
    });*/
	/*function getObjectURL(file) {
        var url = null;
        var size = file.size;
        var maxSize = 2*1024*1024;
        if(size>maxSize){
            $.prompt.message("图片最大不超过2M！", $.prompt.error);
            return false;
        }
        if (window.createObjectURL!=undefined) {
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }*/
</script>
</body>
</html>