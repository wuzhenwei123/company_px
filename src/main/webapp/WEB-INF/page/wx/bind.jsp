<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>${_title}--绑定</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weixin/style.css">
    <script src="${ctx}/js/jquery.min.js" ></script>
    <script src="${ctx}/js/lib/layer/layer.js" ></script>
    <script src="${ctx}/js/weixin/bind.js?t=<%=Math.random()%>" ></script>
</head>
<body class="body">
	<%@ include file="/WEB-INF/page/common/share.jsp" %>
<div class="box">
	<input type="hidden" id="projectpath" value="${ctx}"/>
	<input type="hidden" id="openId" value="${openId}"/>
	<div class="ch_logo" style="background-image:url(${ctx}/images/wx/logo.png);"></div>
	<div class="ch_logo_box">
    	<div class="ch_login_ui">
        	<div class="ch_login_input ch_login_in ui_lo_name ch_lo_box"><input class="ch_input ch_lo_inpput" type="text" id="userName" placeholder="手机号"/></div>
            <div class="ch_login_input ch_login_in ui_lo_pwd"><input class="ch_input ch_lo_inpput" type="password" id="password" placeholder="密码" /></div>
        </div>
        <div class="ch_btn_box">
        	<a class="ch_btn green" href="javascript:void(0);" onclick="tijiao()">绑定账号</a>
        </div>
        <h2 class="ch_login_rem">绑定说明：</h2>
        <div class="ch_login_rem_text">
            <p>1.首次输入账号及密码，您的微信账号会自动关联。</p>
            <p>2.再次登录无需重新输入。</p>
            <p>3.如通过其他手持设备使用同一微信号访问此平台，亦可自动关联。</p>
        </div>
    </div>
</div>

</body>
</html>
