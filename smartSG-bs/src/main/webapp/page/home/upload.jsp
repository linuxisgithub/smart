<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<meta charset="utf-8">
<title>上传附件</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${ctx }/tools/jqfileupload/css/jquery.fileupload.css">
</head>
<body>
<div class="container" style="width: 100%; margin: 0 auto; margin-top: 15px;">
    <span class="btn btn-success fileinput-button" style="float:left;">
        <span>+添加附件...</span> 
        <input id="fileupload" type="file" name="files" multiple>
    </span>
    <!-- <div class="fjwz">附件上传：最多上传4个</div> -->
    <span style="float:right;font-size:14px;">
	        已上传文件数：<span id="fileNum">0</span>
    </span>
    <br>
    <br>
    <table role="presentation" class="table">
    	<tbody class="files" id="files"></tbody>
    </table>
</div>
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script src="${ctx }/tools/jqfileupload/js/load-image.all.min.js"></script>
<script src="${ctx }/tools/jqfileupload/js/canvas-to-blob.min.js"></script>
<script src="${ctx }/tools/jqfileupload/js/jquery.fileupload-all-min.js"></script>
<script>
var path = $("#contextPath").val();
var fileInfos = "fileInfos";//显示上传附件名称的控件id
var fileNames = "fileNames";//保存上传附件信息的控件id
if("${param.fileInfos}" != "") {
	fileInfos = "${param.fileInfos}";
}
if("${param.fileNames}" != "") {
	fileNames = "${param.fileNames}";
}
var acceptType = 0;
var acceptFileTypes = new Array(/(\.|\/)(gif|jpe?g|png|bmp|docx?|xlsx?|pptx?|txt|log|ogg|spx|mp3|mp4)$/i,
		/(\.|\/)(gif|jpe?g|png|bmp)$/i);
if("${param.acceptType}" != "") {
	var _acceptType = "${param.acceptType}";
	if(!isNaN(_acceptType)) {
		acceptType = parseInt(_acceptType);
	}
}
var path = "${ctx }";
var isFrame = "${param.isFrame}";
var fileImg1 = "<img src='${ctx }/images/icons/accessory_icon1.png'/>";//无
var fileImg2 = "<img src='${ctx }/images/icons/accessory_icon2.png'/>";//有
/**------------将文件信息写会相应控件保存-----------**/
function returnFiles() {
	var files = "[";
	var names = $("#fileupload").data("names");
	var names_str = fileImg1;
	if(names && names.length > 0) {
		$.each(names, function (index, item) {
			files += JSON.stringify($("#fileupload").data(item));
			names_str += item;
			if(index < names.length - 1) {
				names_str += ";";
				files += ",";
			}
	    });
		names_str = fileImg2;
	}
	files += "]";
	if(typeof(isFrame) != "undefined" && (isFrame == 1 || isFrame == "1")) {
		parent.$('#' + fileNames).html(names_str);
		parent.$('#fileNamesDe').val(names);
		parent.$('#' + fileInfos).val(files);
	} else if(typeof(isFrame) != "undefined" && (isFrame == 2 || isFrame == "2")) {
		parent.$("#content_iframe").contents().find('#' + fileNames).html(names_str);
		parent.$("#content_iframe").contents().find('#' + fileInfos).val(files);
	} else {
		$('#fileNames').val(names_str);
		$('#fileInfos').val(files);
		if(typeof(isSaveBtnClick) != "undefined" && isSaveBtnClick == true) {
			//之前已经点击过完成按钮，把附件信息写回主体表单
			parent.$("#items"+index+"_annex").val($("#fileInfos").val());
		}
	}
}

function removeObject(arrays, obj) {
	var num = -1;
	$.each(arrays, function (index, item) {
		if(item == obj) {
			num = index;
		}
    });
	if(num != -1) {
		arrays.splice(num,1);
	}
}
/**------------格式化字节数-----------**/
function formateFileSize(size) {
	if(size < 1024 && false) {
		return Math.round(size * 100) / 100;
	} else if(size < 1024*1024){
		return Math.round(size/1024 * 100) / 100 + "KB";
	} else if(size < 1024*1024*1024) {
		return Math.round(size/(1024*1024) * 100) /100 + "MB";
	} else {
		return Math.round(size/(1024*1024*1024) * 100) + "GB";
	}
};
$(function () {
    var url = path + "/annex/upload.htm";
    //初始化上传按钮
    var uploadButton = $('<button/>').addClass('btn btn-sm btn-success upload').css("margin-left","10px").text('上传').on('click', function () {
        if($("#fileupload").data("names").length >= 100) {
        	alert("最多只能上传100个文件");
    		return;
        } else {
        	var $this = $(this), data = $this.data();
	        data.submit().always(function () {
	        	$this.prop('disabled', true);
		        $(data.context.children()[2]).find("div.progress-bar").css('width', '100%');
	        });
        }
    });
    //初始化取消按钮
    var cancelButton = $('<button/>').addClass('btn btn-sm btn-warning cancel').css("margin-left","10px").text('取消').on('click', function () {
    	var $this = $(this), data = $this.data();
    	data.abort();
    	data.context.remove();
	});
    //初始化删除按钮
    var deleteButton = $('<button/>').addClass('btn btn-sm btn-danger delete').css("margin-left","10px").text('删除').on('click', function () {
    	var $this = $(this), delData = $this.data();
    	var fileData = delData.result.data;
    	$.ajax({
			'url': path + "/annex/removeUpload.htm",
			'type': "post",
			'data':{"name": fileData[0].name},
			'dataType':'json',
			'success':function(data,statusText){
				if(data.status == 200 && data.data == true) {
					$this.prop('disabled', true);
					$(delData.context.children()[1]).find('strong').text("文件删除成功.");
					
					$("#fileupload").removeData(fileData[0].srcName);
					removeObject($("#fileupload").data("names"), fileData[0].srcName);
					$("#fileNum").text($("#fileupload").data("names").length);
					returnFiles();
					delData.context.remove();//移除当前行
				}
			},
			'error':function(xmlHttp,e1,e2){
				alert("系统异常，请稍后再试！");
			}
		});
	});
    var defaultImg = "<img src='${ctx}/images/icons/filePic.png' width='80' height='56'/>";
    $("#fileupload").data("names",[]);//初始化names空数组
    if((isFrame == 1 || isFrame == "1") || (isFrame == 2 || isFrame == "2")) {
    	var _fileInfos = ""
    	if(isFrame == 1 || isFrame == "1") {
    		_fileInfos = parent.$('#' + fileInfos).val();
    	} else {
    		_fileInfos = parent.$("#content_iframe").contents().find('#' + fileInfos).val();
    	}
    	
    	if(typeof(_fileInfos) != "undefined" && _fileInfos != "") {
    		var files = JSON.parse(_fileInfos);
    		$.each(files, function (index, file) {
    			var data = {
   					result:{}
    			};
    			data.context = $('<tr/>').appendTo('#files');
    			data.result.data = new Array(file);
    			
    			var picNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","35%").append($('<span/>').addClass("preview"));
        		picNode.appendTo(data.context);
        		
        		if (file.showType == 2) {
        			var img = "<img src='"+file.path+"' width='80' height='56'/>";
                	$(data.context.children()[0]).find('span').prepend(img);//显示预览图
                } else {
                	$(data.context.children()[0]).find('span').prepend(defaultImg);
                }
                 
                var nameNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","35%").append($('<p/>').text(file.srcName));
                /* $("<strong/>").addClass("error text-danger").appendTo(nameNode); */
                nameNode.appendTo(data.context);
                
       		 	var link = $('<a>').attr('target', '_blank').prop('href', file.path);
                $(data.context.children().children()[1]).wrap(link);//将图片包裹在<a></a>标签内
                $(data.context.children()[1]).find('strong').text("文件上传完成.");
                
                var sizeNode = $("<td/>").css("vertical-align", "middle").css("text-align","left").css("width","15%").append($("<p/>").addClass("size").text(formateFileSize(file.fileSize)));
                sizeNode.appendTo(data.context);
                
             	var btnNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","15%").css("min-width", "100px");
             	deleteButton.clone(true).data(data).appendTo(btnNode)
               	btnNode.appendTo(data.context);
             	
             	$("#fileupload").data(file.srcName, file);
                $("#fileupload").data("names").push(file.srcName);
                $("#fileNum").text($("#fileupload").data("names").length);
            });
    	}
    }
    
    var autoUpload = true;//是否自动上传
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        autoUpload: autoUpload,//是否自动上传
        //acceptFileTypes: /(\.|\/)(gif|jpe?g|png|bmp|docx?|xlsx?|pptx?|txt|log|ogg|spx|mp3|mp4)$/i,//限制附件类型
        acceptFileTypes: acceptFileTypes[acceptType],//限制附件类型
        maxFileSize: 30*1024*1024,//限制文件大小：30*1024*1024 = 30MB
        disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
        previewMaxWidth: 80,
        previewMaxHeight: 80,
        previewCrop: false
    }).on('fileuploadadd', function (e, data) {
    	data.context = $('<tr/>').appendTo('#files');
    	$.each(data.files, function (index, file) {
    		var picNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","35%").append($('<span/>').addClass("preview"));
    		picNode.appendTo(data.context);
    		
            var nameNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","35%").append($('<p/>').text(file.name));
           /*  $("<strong/>").addClass("error text-danger").appendTo(nameNode); */
            nameNode.appendTo(data.context);
            
            var sizeNode = $("<td/>").css("vertical-align", "middle").css("text-align","left").css("width","15%").append($("<p/>").addClass("size").text(formateFileSize(file.size)));
            /* var barNode = $("<div/>").addClass("progress progress-striped active").prop("role","progressbar")
            	.append($("<div/>").addClass("progress-bar progress-bar-success").css("width","0%"));
            barNode.appendTo(sizeNode); */
            sizeNode.appendTo(data.context);
           	/* var btnNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","25%").css("min-width", "190px")
           		.append(uploadButton.clone(true).data(data))
           		.append(cancelButton.clone(true).data(data)); */
         	var btnNode = $('<td/>').css("vertical-align", "middle").css("text-align","left").css("width","15%").css("min-width", "100px");
           	if(autoUpload == false) {
           		uploadButton.clone(true).data(data).appendTo(btnNode)
           	}
           	cancelButton.clone(true).data(data).appendTo(btnNode)
           	btnNode.appendTo(data.context);
        });
    }).on('fileuploadprocessalways', function (e, data) {
    	//选择文件之后触发，在fileuploadadd之后
        var file = data.files[0];
        if (file.preview) {
        	$(data.context.children()[0]).find('span').prepend(file.preview);//显示预览图
        } else {
        	$(data.context.children()[0]).find('span').prepend(defaultImg);
        }
        if (file.error) {
        	$.prompt.message(file.name + "上传失败：" + file.error, $.prompt.msg);
        	$(data.context.children()[1]).find('strong').text(file.error);//显示错误信息，如：文件类型不能上传
        	$(data.context.children()[3]).find('button.upload').prop('disabled', true);
        }
        if(data.autoUpload) {
        	$(data.context.children()[2]).find("div.progress-bar").css('width', '100%');
        	$(data.context.children()[3]).find('button.upload').prop('disabled', true);
        }
    }).on('fileuploadprogressall', function (e, data) {
    	//整体进度条
       	/* var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .progress-bar').css('width',progress + '%'); */
    }).on('fileuploaddone', function (e, data) {
    	//上传成功
    	if(data.result.status == 200) {
	        $.each(data.result.data, function (index, file) {
	            if (file.path) {
	                var link = $('<a>').attr('target', '_blank').prop('href', file.path);
	                $(data.context.children().children()[1]).wrap(link);//将图片包裹在<a></a>标签内
	            }
	            $(data.context.children()[1]).find('strong').text("文件上传完成.");
	            $(data.context.children()[3]).find('button.cancel').remove();
	            //$(data.context.children()[3]).find('button.cancel').prop('disabled', true);
	            deleteButton.clone(true).data(data).appendTo(data.context.children()[3]);
	            $("#fileupload").data(file.srcName, file);
	            $("#fileupload").data("names").push(file.srcName);
	            $("#fileNum").text($("#fileupload").data("names").length);
	            returnFiles();
	        });
    	} else {
    		$.prompt.message("上传失败：" + data.result.msg, $.prompt.error);
    	}
        
    }).on('fileuploadfail', function (e, data) {
    	//上传失败
        $.each(data.files, function (index) {
        	$(data.context.children()[1]).find('strong').text('文件上传失败.');
        });
    }).prop('disabled', !$.support.fileInput)
    	.parent().addClass($.support.fileInput ? undefined : 'disabled');
});
</script>
</body>
</html>