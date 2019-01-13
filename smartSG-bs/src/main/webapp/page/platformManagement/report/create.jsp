<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<%--<script type="text/javascript" src="${ctx}/tools/jqueryMultiselect2side/jquery.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/tools/jqueryMultiselect2side/jquery.multiselect2side.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/jqueryMultiselect2side/jquery.multiselect2side.css" />--%>
	<link rel="stylesheet" type="text/css" href="${ctx}/tools/formSelects/formSelects-v4.css"/>
<title>表格样式</title>
</head>
<body>
<div class="mainRight-tab-conW">				
	<div class="tab-con">
		<form action="" method="post" id="mainForm" onsubmit="javascript:return false;">
			<div style="display: none;" alt="隐藏表单域">
				<input type="hidden" name="eid" id="eid" value="${model.eid}">
				<input type="hidden" id="options" value="${model.liOption}">
			</div>
			<div class="yj-add-top">
				<div class="yj-add-title">
				 	${empty model.eid ? '新增报表':'编辑报表'}
				</div>
			</div>
			<div class="yj-add-div2">
				<div class="yj-add-divL">
					<div class="div-label">
						报表名称：
					</div>
					<div class="div-input">
						<input type="text" name="name" id="name" value="${model.name}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						报表描述：
					</div>
					<div class="div-input">
						<input type="text" name="description" id="description" value="${model.description}">
					</div>
				</div>
				<div class="yj-add-divL">
					<div class="div-label">
						关联对象：
					</div>
					<div class="div-input">
						<select name="kind" id="kind">
							<c:if test="${type eq 'sg'}">
								<option value="2" ${model.kind == 2 ? 'selected':''}>摊位</option>
								<option value="3" ${model.kind == 3 ? 'selected':''}>食阁</option>
							</c:if>
							<c:if test="${type eq 'tw'}">
								<option value="1" ${model.kind == 1 ? 'selected':''}>顾客APP用户</option>
								<option value="4" ${model.kind == 4 ? 'selected':''}>菜品</option>
							</c:if>
						</select>
					</div>
				</div>
				<div class="clear"></div>
			</div>

			<div class="yj-hr"></div>
			<div class="yj-add-div0">
				<div class="yj-add-divL">
					<div class="div-label">
						设置报表字段：
					</div>
					<select name="liOption" xm-select="selectId" xm-select-skin="normal" xm-select-search xm-select-search-type="dl">
					</select>
				</div>
				<div class="clear"></div>
			</div>

		<%--	<div class="yj-hr"></div>
			<div class="yj-add-div0">
                <div class="yj-add-divL" id="a">
                    <div class="div-label">
						设置报表字段：
                    </div>
                    <select name="liOption" id='liOption' multiple='multiple' size='8' >
                    </select>
                </div>
                <div class="clear"></div>
			</div>--%>
		</form>
	</div>
</div>
<div class="layer-footer-btn">
	<div class="footer-btn-right">
		<button class="submit-btn" id="save_btn">确<span class="wzkg"></span>定</button>
		<button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
	</div>
	<div class="clear"></div>
</div>
<link rel="stylesheet" type="text/css" href="${ctx}/tools/select2/css/select2.min.css">
<script type="text/javascript" src="${ctx}/tools/layer-v3.0.3/layer_demo.js"></script>
<script type="text/javascript" src="${ctx}/tools/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script src="${ctx}/tools/formSelects/formSelects-v4.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/platformManagement/report/create.js"></script>
<script>
	formSelects.render('selectId');
</script>
</body>
</html>