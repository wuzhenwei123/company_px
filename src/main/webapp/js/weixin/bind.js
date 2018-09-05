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

function closeWin(){
	WeixinJSBridge.call('closeWindow');
}

function onBridgeReady(){
}

var flag = true;

function tijiao() {
	var userName = $.trim($("#userName").val());
	if (userName == "") {
		layer.msg("请输入手机号");
		return false;
	}

	var password = $.trim($("#password").val());
	if (password == "") {
		layer.msg("请输入密码");
		return false;
	}
	
	if(flag){
		flag = false;
		$.post($("#projectpath").val() + "/weixin/bindUser", {
			userName : userName,
			password : password,
			openId : $("#openId").val(),
			_t : Math.random()
		}, function(data) {
			flag = true;
			console.log(data);
			var result = eval("(" + data + ")");
			if (result.code == 1) {
				layer.msg("恭喜您，绑定成功！");
				setTimeout("closeWin()",3000);
			}else if(result.code == -1){
				layer.msg("请输入手机号！");
			}else if(result.code == -2){
				layer.msg("请输入密码！");
			}else if(result.code == -4){
				layer.msg("手机号不存在！");
			}else if(result.code == -6){
				layer.msg("手机号被冻结，请联系管理员！");
			}else if(result.code == -7){
				layer.msg("账号已经绑定，请解绑！");
			}else{
				layer.msg("手机号异常，请联系管理员！");
			}
		});
	}
	
}

function closeAlert(){
	$(".weui_dialog_alert").hide();
}

function showConfirm(){
	$(".weui_dialog_confirm").show();
}

function unbind(){
	$.post($("#projectpath").val() + "/weixin/unBind", {
		_t : Math.random()
	}, function(data) {
		var result = eval("(" + data + ")");
		if (result.code == 1) {
			$(".weui_dialog_confirm").hide();
			layer.msg("解绑成功！");
			setTimeout("closeWin()",3000);
		}else{
			layer.msg("解绑失败！");
		}
	});
}

function closeConfirm(){
	$(".weui_dialog_confirm").hide();
}