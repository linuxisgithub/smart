<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<style type="text/css">
	body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";font-size:14px;}
	#allmap {width:100%;height:500px;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=DGDzNSYDFnInmGtEGwCG5muOy1wT5X7M"></script>
<title>表格样式</title>
</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<div style="padding-bottom: 6px;margin-top:-15px;">
			<span>点击地图展示详细地址:</span>&nbsp;&nbsp;<input id="localaddr" name="localaddr" style="border: 0px;width: 500px;" type="text" readonly>
			<input id="point" value="" type="hidden">
			<%--<button onclick="genStaticPic()">生成静态图</button><br>--%>
		</div>
		<div id="allmap" style="width:1000px;height:370px;"></div>
		<div style="padding-top:6px;">
			城市名: &nbsp;<input id="cityName" type="text" style="width:135px; margin-right:10px;height: 26px;" />
			<a href="javascript:void(0)" id="cxCity" onclick="theLocation()">查询</a>
			<%--<input type="button" value="查询" onclick="theLocation()" />--%>
		</div>
		<%--<div id="pic"></div>--%>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">确<span class="wzkg"></span>定</button>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>

<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript">
	$("#return_btn").click(function() {
		closeWin();
	});
	$("#save_btn").click(function() {
		var localaddr = $("#localaddr").val();
		if (localaddr != '') {
			parent.$("#address").val(localaddr);
		}
		closeWin();
	})
	$("#cityName").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			$("#cxCity").trigger("click");
		}
	});
	function closeWin() {
		var layerIndex = parent.layer.getFrameIndex(window.name);
		parent.layer.close(layerIndex);
	}
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(113.327361,23.131829);//广东省 广州市 天河区 华夏路 49号 emmmmmmm......
	map.centerAndZoom(point,16);//地图级别
	var geoc = new BMap.Geocoder();

	map.addControl(new BMap.MapTypeControl({
		mapTypes:[
			BMAP_NORMAL_MAP,
			BMAP_HYBRID_MAP
		]}));
	map.setCurrentCity("广州");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	function myFun(result){
		var cityName = result.name;
		map.setCenter(cityName);
		//alert("当前定位城市:"+cityName);
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);//ip定位

	function theLocation(){
		var city = document.getElementById("cityName").value;
		if(city != ""){
			map.centerAndZoom(city,11);      // 用城市名设置地图中心点
		}
	}

	var marker = null;
	map.addEventListener("click", function(e){
		var pt = e.point;
		if(marker!=null){
			marker.setPosition(pt);
			parsePoint(pt);
		}else{
			marker = new BMap.Marker(pt);
			map.addOverlay(marker);

			var label = new BMap.Label("...",{offset:new BMap.Size(20,-10)});
			marker.setLabel(label);

			marker.enableDragging();
			marker.addEventListener("dragend", function(e2){
				parsePoint(e2.point);
			});
			parsePoint(pt);
		}
	});

	function parsePoint(pt){
		//console.log('解析地址: ', pt.lng , ',' , pt.lat);
		document.getElementById('point').value = pt.lng + ',' + pt.lat;
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			var address = addComp.province + " " + addComp.city + " " + addComp.district + " " + addComp.street + " " + addComp.streetNumber;
			document.getElementById('localaddr').value = address;
			if(marker!=null){
				marker.getLabel().setContent(address);
			}
		});
	}

	function genStaticPic(){
		var img = new Image();
		var loc = document.getElementById('point').value;
		var params = "&width=300&height=300&center=" + loc + "&zoom=16&markers="+ loc;
		img.src = "http://api.map.baidu.com/staticimage/v2?ak=DGDzNSYDFnInmGtEGwCG5muOy1wT5X7M"+params;
		document.getElementById('pic').innerHTML='';
		document.getElementById('pic').appendChild(img);
	}
</script>
</body>
</html>