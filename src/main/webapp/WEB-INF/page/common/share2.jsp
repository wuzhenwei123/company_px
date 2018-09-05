<%@page import="com.base.utils.ConfigConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
var ua = navigator.userAgent.toLowerCase();
if (ua.match(/MicroMessenger/i) != "micromessenger") {//非微信
 	window.location.href="http://tx.mievie.com/tip.jsp";
}

wx.config({
    debug: false,
    appId: '${appid}', 
    timestamp: ${timestamp}, 
    nonceStr: '${noncestr}', 
    signature: '${signature}',
    jsApiList: [
    	'onMenuShareTimeline',
		'onMenuShareAppMessage',
		'chooseImage',
		'uploadImage',
		'hideMenuItems'
    ] 
});
wx.ready(function(){
	wx.hideMenuItems({
	    menuList: [
	    	'menuItem:share:qq',
	    	'menuItem:share:weiboApp',
	    	'menuItem:favorite',
	    	'menuItem:editTag',
	    	'menuItem:delete',
	    	'menuItem:copyUrl',
	    	'menuItem:originPage',
	    	'menuItem:readMode',
	    	'menuItem:openWithQQBrowse',
	    	'menuItem:openWithSafari',
	    	'menuItem:share:email',
	    	'menuItem:share:brand'
	    ]
	});
});
if (typeof WeixinJSBridge == "undefined"){
   if( document.addEventListener ){
       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   }else if (document.attachEvent){
       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   }
}else{
   onBridgeReady();
}

function onBridgeReady(){
		wx.onMenuShareTimeline({
		    title: '【${manageAdminUser.realName}】邀您加入易付通购电', // 分享标题
		    link: '${server_href}/weixin/toMyQRcodeFriend?openId=${manageAdminUser.openId}', // 分享链接
		    imgUrl: '${server_href}/images/wx/k_logo_img.jpg', // 分享图标
		    success: function () { 
		    },
		    cancel: function () { 
		    }
		});
		wx.onMenuShareAppMessage({
		    title: '【${manageAdminUser.realName}】邀您加入易付通购电', // 分享标题
		    desc: '', // 分享描述
		    link: '${server_href}/weixin/toMyQRcodeFriend?openId=${manageAdminUser.openId}', // 分享链接
		    imgUrl: '${server_href}/images/wx/k_logo_img.jpg', // 分享图标
		    type: 'link', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		    },
		    cancel: function () { 
		    }
		});
}
</script>