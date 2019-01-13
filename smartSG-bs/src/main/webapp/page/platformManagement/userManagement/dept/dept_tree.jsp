<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<script type="text/javascript" src="${ctx}/tools/echarts/2.2.7/echarts-all.js"></script>
<title>架构一览</title>
<script type="text/javascript">
var comLevel = JSON.parse('${comLevel}');
var firstLevel = JSON.parse('${firstLevel}');
var secLevel = JSON.parse('${secLevel}');
var thirdLevel = JSON.parse('${thirdLevel}');
var company = JSON.parse('${company}');
var root = JSON.parse('${root}');
var maxNum = parseInt('${maxNum}');
var labelFromatter = {
    normal: {
        color: '#FFFFFF',
        label: {
            show: true
        },
        borderWidth: 1
    },
    emphasis: {
        label: {
            show: true,
            color: '#FFFFFF',
            textStyle: {
                color: '#000000',
                fontSize: 12,
                fontWeight:  'bolder'
            }
        },
        borderWidth: 1,
        color: '#FFFFFF',
    }
};
var data = [];
var treeData = {
	name: root.name,
	itemStyle: labelFromatter,
	children: []
};
$.each(comLevel, function(index, com) {
	var com_child = {
		name: com.name,
		itemStyle: labelFromatter,
		children: []
	};
	var first_childs = firstLevel[com.eid];
	if(first_childs != undefined) {
		$.each(first_childs, function(first_index, first) {
			var first_child = {
				name: first.name,
				itemStyle: labelFromatter,
				children: []
			};
			var second_childs = secLevel[first.eid];
			if(second_childs != undefined) {
				$.each(second_childs, function(second_index, second) {
					var second_child = {
						name: second.name,
						itemStyle: labelFromatter,
						children: []
					};
					var out_childs = thirdLevel[second.eid];
					if(out_childs != undefined) {
						$.each(out_childs, function(out_index, out) {
							var out_child = {
								name: out.name,
								itemStyle: labelFromatter,
								children: []
							};
							second_child.children.push(out_child);
						});
					}
					first_child.children.push(second_child);
				});
			}
			com_child.children.push(first_child);
		});
	}
	treeData.children.push(com_child);
});
var treename = "公司架构一览";
data.push(treeData);
var option = {
    calculable : false,
    series : [
        {
            name:treename,
            type:'tree',
            orient: 'vertical',  // vertical horizontal
            rootLocation: {x: 'center', y: '30'}, // 根节点位置  {x: 'center',y: 10}
            layerPadding: 45,
            nodePadding: 20,
            symbol: 'rectangle',
            symbolSize: [100, 40],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        color: '#FFFFFF',
                        textStyle: {
                            color: '#000000',
                            fontSize: 12,
                            fontWeight:  'bolder'
                        },
                        borderWidth: 1
                    },
                    lineStyle: {
                        color: '#000',
                        width: 1,
                        type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
                    }
                },
                emphasis: {
	                label: {
                        show: true,
                        color: '#FFFFFF',
                        textStyle: {
                            color: '#000000',
                            fontSize: 15,
                            fontWeight:  'bolder'
                        }
                    },
                    borderWidth: 1,
                    color: '#FFFFFF',
                }
            },
            data: data
            /* data: [
                {
                    name: '董事会啊啊啊啊啊',
                    itemStyle: labelFromatter,
                    children: [
                        {
                            name: '小米',
                            itemStyle: labelFromatter,
                            children: [
                                {
                                    name: '小米1',
                                    itemStyle: labelFromatter
                                },
                                {
                                    name: '小米2',
                                    itemStyle: labelFromatter
                                },
                                {
                                    name: '小米3',
                                    itemStyle: labelFromatter
                                }
                            ]
                        },
                        {
                            name: '苹果',
                            itemStyle: labelFromatter
                        },
                        {
                            name: '华为',
                            itemStyle: labelFromatter
                        },
                        {
                            name: '联想',
                            itemStyle: labelFromatter
                        }
                    ]
                }
            ] */
        }
    ]
};
	                    
$(function() {
	var chart_width = (maxNum + 1) * 120;
	if(chart_width > $("#main").width()) {
		$("#main").css("width", chart_width + "px");
	}
	var myChart = echarts.init(document.getElementById('main'));
	myChart.setOption(option);
});                    
</script>
</head>
<body>
<div class="mainRight-tab-conW" style="height: calc(100% + 60px);">
	<div class="tab-con">
		<form action="${ctx}/comNotice/save.htm" method="post" id="mainForm">
			<div class="yj-add-top">
				<div class="yj-add-title">
						公司架构一览
				</div>
			</div>
			<div class="yj-add-div0" style="overflow: auto;">
				<div id="main" style="height:500px;width: 100%"></div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
</body>
</html>