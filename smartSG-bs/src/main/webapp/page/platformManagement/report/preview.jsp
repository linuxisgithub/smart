<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css"/>
<title>表格样式</title>
    <style>
        tr{
            text-align: center;
        }

    </style>
</head>
<body>
<div class="mainRight-tab-conW">
    <div class="tab-con">
        <h2 align="center" style="margin-bottom:10px;">${reportName}</h2>
        <table border="1" style="width: 100%;border-collapse:collapse;">
            <tr style="width: 100%;">
                <c:forEach items="${fields}" var="f">
                    <td>${f.name}</td>
                </c:forEach>
            </tr>

        </table>
    </div>
</div>
<div class="layer-footer-btn">
    <div class="footer-btn-right">
        <button class="return_btn" id="return_btn">返<span class="wzkg"></span>回</button>
    </div>
    <div class="clear"></div>
</div>
<script type="text/javascript">
    $("#return_btn").click(function() {
        var layerIndex = parent.layer.getFrameIndex(window.name);
        parent.layer.close(layerIndex);
    });
</script>
</body>
</html>