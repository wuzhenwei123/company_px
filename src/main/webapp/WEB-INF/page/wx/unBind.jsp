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
    <title>${_title}--解绑</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weixin/style.css">
    <link href="${ctx}/css/wx/weui.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery.min.js" ></script>
    <script src="${ctx}/js/lib/layer/layer.js" ></script>
    <script src="${ctx}/js/weixin/bind.js" ></script>
</head>
<body class="body">
<%@ include file="/WEB-INF/page/common/share.jsp" %>
<div class="box">
	<input type="hidden" id="projectpath" value="${ctx}"/>
	<div class="ch_logo" style="background-image:url(${ctx}/images/wx/logo.png);"></div>
	<div class="ch_logo_box">
        <h2 class="ch_login_rem">解绑说明：</h2>
        <div class="ch_login_rem_text">
            <p>1.解绑成功后，将不再拥有微信菜单的查看权限。需要再次登录绑定。</p>
            <p>2.再次登录需要重新输入手机号和密码。</p>
        </div>
        <div class="ch_btn_box">
        	<a class="ch_btn green" href="javascript:void(0);" onclick="showConfirm()">解绑</a>
        </div>
    </div>
</div>
<div class="weui_dialog_confirm" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd">
          <strong class="weui_dialog_title">您确定需要解绑吗？</strong>
        </div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog default" onclick="closeConfirm()">取消</a>
            <a href="javascript:;" class="weui_btn_dialog primary" onclick="unbind()">确定</a>
        </div>
    </div>
</div>
<div class="weui_dialog_alert">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
    	<div class="weui_dialog_hd">
    		<strong class="weui_dialog_title" id="msg">由于您已成功绑定<br/>系统自动转向解绑页面</strong>
      	</div>
        <div class="weui_dialog_ft">
            <a href="javascript:closeAlert();" class="weui_btn_dialog primary">确定</a>
        </div>
    </div>
</div>
</body>
</html>
