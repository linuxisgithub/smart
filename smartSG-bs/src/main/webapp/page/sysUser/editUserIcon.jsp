<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>修改个人信息</title>
<link href="${path}/tools/sui/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${path}/chat/js/ddx_gd/jquery.min.js"></script>
<script type="text/javascript" src="${path}/tools/sui/js/sui.min.js"></script>
<script type="text/javascript" src="${path}/js/imagecropper.js"></script>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<script type="text/javascript">
//用户头像
var userIcon = "${icon}";

$(function() {
	
	init();
	
    //展示当前头像
    if(userIcon == null || userIcon == ""){
        userIcon = "${ctx}/chat/images/defaultSingleIcon.png";
    }
    $("#preview").attr("src",userIcon);
    $("#sureImg").attr("src",userIcon);
});

</script>
</head>
<body style="background-color: #fff;">

	<form  enctype="multipart/form-data" id="jsForm" >
		<input id="img_type" name="img_type" type="hidden"/>
		<input id="img_size" name="img_size"  type="hidden"/>
		<input id="img_name"  name="img_name"  type="hidden"/>
		<input id="img_files" name="img_files"  type="hidden"/>
	</form>
	

	<div style="float: left;margin-left: 50px;">
		<div id="container">
       <a  id="selectBtn" href="javascript:void(0);" class="sui-btn btn-large btn-primary" onclick="document.getElementById('input').click();">选择图片</a>
       <a  id="saveBtn" href="javascript:void(0);" style="width: 110px;"  class="sui-btn btn-large btn-primary"  onclick="doImage();">确定剪裁</a>
        <input type="file" id="input" size="10" accept="image/gif, image/jpeg,image/bmp, image/png"  style="visibility:hidden;" onchange="selectImage(this.files)" />

        <div id="wrapper">
            <canvas id="cropper"></canvas>
			
            <span id="status" style="position:absolute;left:350px;top:10px;width:100px;"></span>
            <div id="previewContainer">
            <img id="sureImg" style="max-width: none;" width="100" height="100">
				<span style="position:absolute;left:20px;top:120px;width:70px;text-align:center;" id="touText">头像预览</span>
				<div style="display: none;">
				<canvas id="preview180" width="180" height="180" class="preview"></canvas>
	                <canvas id="preview100" width="100" height="100" style="position:absolute;left:230px;top:0px;" class="preview"></canvas>
	                <span style="position:absolute;left:230px;top:110px;width:100px;text-align:center;">中尺寸图片 100x100像素</span>
	
	                <canvas id="preview50" width="50" height="50" style="position:absolute;left:255px;top:150px;" class="preview"></canvas>
	                <span style="position:absolute;left:245px;top:210px;width:70px;text-align:center;">小尺寸图片 50x50像素</span>
           		</div>
            </div>
        </div>
	
	
    </div>
	</div>
	<div class="layer-footer-btn" style="line-height: 0px;">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">保<span class="wzkg"></span>存</button>
		<span class="wzkg"></span>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	</div>
	<input id="contextPath" value="${ctx}" type="hidden">
<script type="text/javascript">
var cropper;

function init()
{   
    //绑定
    cropper = new ImageCropper(300, 300, 180, 180);
    cropper.setCanvas("cropper");
    cropper.addPreview("preview180");
    cropper.addPreview("preview100");
    cropper.addPreview("preview50");
    //检测用户浏览器是否支持imagecropper插件
    if(!cropper.isAvaiable())
    {
        alert("Sorry, your browser doesn't support FileReader, please use Firefox3.6+ or Chrome10+ to run it.");
    }
}

//打开本地图片
function selectImage(fileList)
{
    cropper.loadImage(fileList[0]);
}


//剪裁图片
function doImage()
{
	
	//选个你需要的大小
    var imgData = cropper.getCroppedImageData(100, 100);
	//预览
	$("#sureImg").attr("src",imgData);
    
 	 //判断是否有选择上传文件
    var imgPath = $("#input").val();
    if (imgPath == "") {
    	 parent.$.prompt.message("请选择图片剪裁！", $.prompt.warn);
    	 $("#sureImg").attr("src",userIcon);
        return;
    }
    
    //图片全路径+名称
    var file = $("#input").val();
    //图片类型
    var img_type = file.substr(file.indexOf("."));
    
    //64位base图片编码 
    //计算size
    var img =  imgData.substr(imgData.indexOf(',') + 1);
    var equalIndex= img.indexOf('=');
    if(img.indexOf('=')>0)
    {
    	img=img.substring(0, equalIndex);
    }
   
    var strLength=img.length;
    var img_size=parseInt(strLength-(strLength/8)*2);
    
    
    $("#img_name").val(file);
    $("#img_size").val(img_size);
    $("#img_type").val(img_type);
    $("#img_files").val(img);
    
}

var path = $("#contextPath").val();

$("#return_btn").click(function() {
	closeWin();
});

$("#save_btn").click(function() {
	$(this).prop('disabled', true);
	doImage();
	var imgPath = $("#input").val();
	if (imgPath == "") {
    	$(this).prop('disabled', false);
		return false;
    }
	
	jsonAjax.post(path + "/DDX/editUserIcon.htm",$("#jsForm").serialize(), function(data,response) {
		if(data.status == 200) {
			parent.$.prompt.message("修改个人头像成功！", $.prompt.msg);
			parent.top.updateIcon(data.data);
			window.parent.getCurrentUserInfo();
			closeWin();
		} else {
			parent.$.prompt.message("修改个人头像失败！", $.prompt.error);
			$("#save_btn").prop('disabled', false);
		}
	});
});

function closeWin() {
	var layerIndex = parent.layer.getFrameIndex(window.name);
	parent.layer.close(layerIndex);
}

</script>
</body>
<style>

a
{
    text-decoration: none;
    color: #333;
}

a:hover
{
    text-decoration: none;
    color: #f00;
}

#container
{
    position: absolute;
    top: 20px;
}

#wrapper
{
    position: absolute;
    left: 0px;
    top: 40px;
}

#cropper
{
    position: absolute;
    left: 0px;
    top: 0px;
    border: 1px solid #ccc;
}

#previewContainer
{
    position: absolute;
    top: 100px;
    left: 390px;
}

.preview
{
    border: 1px solid #ccc;
}

#selectBtn
{
    position: absolute;
    left: 0px;
    top: 0px;
    width: 119px;
    height: 27px;

}

#saveBtn
{
    position: absolute;
    left: 150px;
    top: 0px;
    width: 67px;
    height: 27px;

}

#rotateLeftBtn
{
    position: absolute;
    left: 0px;
    top: 315px;
    width: 100px;
    height: 22px;
    padding-left: 25px;
    padding-top: 2px;

}

#rotateRightBtn
{
    position: absolute;
    left: 225px;
    top: 315px;
    width: 50px;
    height: 22px;
    padding-right: 25px;
    padding-top: 2px;

}

</style>
</html>
