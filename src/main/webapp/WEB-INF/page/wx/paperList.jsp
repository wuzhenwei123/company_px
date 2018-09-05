<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="author" content="www.10086.com">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="#e8447e">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" CONTENT="-1">
    <meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta name="description" content="">
    <meta name="Keywords" content="">
    <title>${_title}--测评列表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weixin/style.css">
    <script src="${ctx}/js/jquery.min.js" ></script>
    <script src="${ctx}/js/lib/layer/layer.js" ></script>
</head>
<body class="body">
<div class="box">
	<%@ include file="/WEB-INF/page/common/share.jsp" %>
    <div class="ch_user_box">
        <div class="ch_ui_box marginTop16 u_jf_box" id="item">
            <c:if test="${not empty list}">
                <c:forEach items="${list}" var="exam">
                    <c:if test="${empty exam.userAnserPaperId}">
                    	<a href="${ctx}/weixin/toAnswerPaper?paperId=${exam.paperId}" class="ui_cell ui_cells">
	                        <div class="ui_center ui_cell_flex">${exam.title}<span style="font-size: 12px;">&nbsp;(未作答)</span></div>
	                        <div class="ui_cell_ft color_909090"><font class="blue_color"><fmt:formatDate value="${exam.createTime}" pattern="yyyy-MM-dd HH:mm"/></font></div>
	                    </a>
                    </c:if>
                    <c:if test="${not empty exam.userAnserPaperId}">
                    	<a href="${ctx}/weixin/toAnswerPapered?paperId=${exam.paperId}" class="ui_cell ui_cells">
	                        <div class="ui_center ui_cell_flex">${exam.title}<span style="font-size: 12px;">&nbsp;(已作答)</span></div>
	                        <div class="ui_cell_ft color_909090"><font class="blue_color"><fmt:formatDate value="${exam.createTime}" pattern="yyyy-MM-dd HH:mm"/></font></div>
	                    </a>
                    </c:if>
                    
                </c:forEach>
            </c:if>
            <c:if test="${empty list}">
                <a href="javascript:void(0)" class="ui_cell ui_cells pm_list_box cp_list">
                    <div class="ui_center ui_cell_flex" style="text-align: center;">
                        <div class="u_jf_pm_title" style="font-size: 18px;text-align: center;">暂无内容</div>
                    </div>
                </a>
            </c:if>
            <div style="text-align: center;font-size: 14px;display: none;" id="loading"><img src="${ctx}/images/loading.gif"/><br>加载中...</div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var stop = true;
    var page = 1;

    $(window).on("scroll", function () {
        totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        if ($(document).height() <= totalheight) {
            if (stop) {
                stop = false;
                $("#loading").show();
                page = parseInt(page, 10) + 1;
                loadData(page);
            }
        }
    });
    function renderTpl(tpl, op) {
        return tpl.replace(/\{(\w+)\}/g, function (e1, e2) {
            return op[e2] != null ? op[e2] : "";
        });
    }
    var itemTpl = "<a href='${ctx}/weixin/toExamAnswerPaper?paperId={id}&answerStatus={answerStatus}' class='ui_cell ui_cells'><div class='ui_center ui_cell_flex'>{name}</div><div class='ui_cell_ft color_909090'><font class='blue_color'>{createDate}</font></div></a>";
    var itemTpl1 = "<a href='${ctx}/weixin/toExamPaper?paperId={id}&answerStatus={answerStatus}' class='ui_cell ui_cells'><div class='ui_center ui_cell_flex'>{name}</div><div class='ui_cell_ft color_909090'><font class='blue_color'>{createDate}</font></div></a>";
    /**
     * 载入内容
     * @return {[type]} [description]
     */
    function loadData(page) {
        $.get("${ctx}/weixin/myPaperListJson?pageNo=" + page + "&_t=" + Math.random(), function (data, status) {
            $("#loading").hide();
            var json = eval("(" + data + ")");
            if (json.message == "ok") {
                var testData = json.items
                for (var i in testData) {
                	if(testData[i].answerStatus==true){
                    	$("#item").append(renderTpl(itemTpl, testData[i]));
                	}else{
                		$("#item").append(renderTpl(itemTpl1, testData[i]));
                	}
                }
                stop = true;
            } else if (json.message == "end") {
                layer.msg("数据刷新完毕");
            }
        });
    }

</script>
</body>
</html>
