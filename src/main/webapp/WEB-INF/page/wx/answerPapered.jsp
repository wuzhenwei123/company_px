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
    <title>${_title}--测评</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weixin/style.css">
    <script src="${ctx}/js/jquery.min.js" ></script>
     <script src="${ctx}/js/lib/layer/layer.js" ></script>
</head>
<body class="body">
<div class="box">
<%@ include file="/WEB-INF/page/common/share.jsp" %>
    <input type="hidden" id="projectpath" value="${ctx}" />
    <div class="test_sj_top" id="timer_tips">已作答
        <strong id="minute_show"></strong>
        <strong id="second_show"></strong>
    </div>
    <div class="ch_user_box">
        <h1 class="content_title" style="text-align:center;">${cPaper.title}</h1>
        <!-- 题类型 -->
        <c:if test="${not empty listQuestion}">
        	<c:set var="sum" value="0"></c:set>
            <c:forEach items="${listQuestion}" var="question" >
                    <!-- 题 -->
               	<c:set var="sum" value="${sum+1}"></c:set>
               	<c:choose>
               		<c:when test="${question.style==1||question.style==2}">
               			<div class="question_list">
                      <p>
                              ${sum}、${question.content}
                          <c:if test="${question.style ==1}">(单选)</c:if>
                          <c:if test="${question.style ==2}">(多选)</c:if>
                          	(&nbsp;<span id="${question.id}_answer_span" style="display: none;"></span>)
                      </p>

                      <div class="as_box">
                          <!-- 选项1 -->
                          <c:if test="${not empty question.optionA}">
                              <label class="ui_line_label ui_checked">
                                   <div class="ui_center_left">
                                       <c:if test="${question.style == 1}">
                                           <input class="ui_checkbox" type="radio"
                                           		 disabled="true"
                                                  name="${question.questionId}"
                                                  <c:if test="${fn:contains(question.userAnswer, 'A')}">checked="checked"</c:if>
                                                  value="A"/>
                                          <i class="icon_input redio_icon"></i>
                                       </c:if>
                                       <c:if test="${question.style == 2}">
                                           <input class="ui_checkbox" type="checkbox"
                                           			disabled="true"
                                                  name="${question.questionId}"
                                                  <c:if test="${fn:contains(question.userAnswer, 'A')}">checked="checked"</c:if>
                                                  value="A"/>
                                          <i class="icon_input check_icon"></i>
                                       </c:if>
                                   </div>
                                   <div class="ui_center ui_cell_flex question_as"> A、question.optionA</div>
                               </label>
                          </c:if>
                          <c:if test="${not empty question.optionB}">
                              <label class="ui_line_label ui_checked">
                                   <div class="ui_center_left">
                                       <c:if test="${question.style == 1}">
                                           <input class="ui_checkbox" type="radio"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'B')}">checked="checked"</c:if>
                                                  value="B"/>
                                          <i class="icon_input redio_icon"></i>
                                       </c:if>
                                       <c:if test="${question.style == 2}">
                                           <input class="ui_checkbox" type="checkbox"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'B')}">checked="checked"</c:if>
                                                  value="B"/>
                                          <i class="icon_input check_icon"></i>
                                       </c:if>
                                   </div>
                                   <div class="ui_center ui_cell_flex question_as"> B、question.optionB</div>
                               </label>
                          </c:if>
                          <c:if test="${not empty question.optionC}">
                              <label class="ui_line_label ui_checked">
                                   <div class="ui_center_left">
                                       <c:if test="${question.style == 1}">
                                           <input class="ui_checkbox" type="radio"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'C')}">checked="checked"</c:if>
                                                  value="C"/>
                                          <i class="icon_input redio_icon"></i>
                                       </c:if>
                                       <c:if test="${question.style == 2}">
                                           <input class="ui_checkbox" type="checkbox"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'C')}">checked="checked"</c:if>
                                                  value="C"/>
                                          <i class="icon_input check_icon"></i>
                                       </c:if>
                                   </div>
                                   <div class="ui_center ui_cell_flex question_as"> C、question.optionC</div>
                               </label>
                          </c:if>
                          <c:if test="${not empty question.optionD}">
                              <label class="ui_line_label ui_checked">
                                   <div class="ui_center_left">
                                       <c:if test="${question.style == 1}">
                                           <input class="ui_checkbox" type="radio"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'D')}">checked="checked"</c:if>
                                                  value="D"/>
                                          <i class="icon_input redio_icon"></i>
                                       </c:if>
                                       <c:if test="${question.style == 2}">
                                           <input class="ui_checkbox" type="checkbox"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'D')}">checked="checked"</c:if>
                                                  value="D"/>
                                          <i class="icon_input check_icon"></i>
                                       </c:if>
                                   </div>
                                   <div class="ui_center ui_cell_flex question_as"> D、question.optionD</div>
                               </label>
                          </c:if>
                          <c:if test="${not empty question.optionE}">
                              <label class="ui_line_label ui_checked">
                                   <div class="ui_center_left">
                                       <c:if test="${question.style == 1}">
                                           <input class="ui_checkbox" type="radio"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'E')}">checked="checked"</c:if>
                                                  value="E"/>
                                          <i class="icon_input redio_icon"></i>
                                       </c:if>
                                       <c:if test="${question.style == 2}">
                                           <input class="ui_checkbox" type="checkbox"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'E')}">checked="checked"</c:if>
                                                  value="E"/>
                                          <i class="icon_input check_icon"></i>
                                       </c:if>
                                   </div>
                                   <div class="ui_center ui_cell_flex question_as"> E、question.optionE</div>
                               </label>
                          </c:if>
                          <c:if test="${not empty question.optionF}">
                              <label class="ui_line_label ui_checked">
                                   <div class="ui_center_left">
                                       <c:if test="${question.style == 1}">
                                           <input class="ui_checkbox" type="radio"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'F')}">checked="checked"</c:if>
                                                  value="F"/>
                                          <i class="icon_input redio_icon"></i>
                                       </c:if>
                                       <c:if test="${question.style == 2}">
                                           <input class="ui_checkbox" type="checkbox"
                                                  name="${question.questionId}"
                                                  disabled="true"
                                                  <c:if test="${fn:contains(question.userAnswer, 'F')}">checked="checked"</c:if>
                                                  value="F"/>
                                          <i class="icon_input check_icon"></i>
                                       </c:if>
                                   </div>
                                   <div class="ui_center ui_cell_flex question_as"> F、question.optionF</div>
                               </label>
                          </c:if>
                      </div>
                     </div>
               		</c:when>
               		<c:otherwise>
               			<div class="question_list">
		               		<p>
		                             ${sum}、${question.content} (问答)
		                     </p>
		                     	<div class="marginTop16 add_qb_form">
		                    <textarea readonly="readonly" class="text_input" name="${question.questionId}" style="height:100px;overflow: visible;">${question.userAnswer}</textarea>
		                </div>
                     </div>
               		</c:otherwise>
               	</c:choose>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>
