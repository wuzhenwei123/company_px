$(function() {
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 try{
        		 if(search()){
        			 search();
        		 }
        	 }catch(e){
        	 }
        }
    };
    $(".search-tools-btn").click(function(){
    	$(".search-form").slideToggle();
    });
});
function layout(ctx,v){
	$.get(ctx+"/layout",{layer_val:v},function(data){
		if(data.code == '1'){
			location.reload();
		}
	}, 'json');
}

var sys = {
	tip:function(title,text){
		swal({
		  title: title,
		  text: text,
		  timer: 2000,
		  showConfirmButton: false
		});
	},
	success:function(title,text,fun){
		swal({title:title, text:text,type:"success"}).then(function() {
			call_back(fun);
		})
	},
	warning:function(title,text,fun){
		swal({title:title, text:text,type:"warning"}).then(function() {
			call_back(fun);
		})
	},
	error:function(title,text,fun){
		swal({title:title, text:text,type:"error"}).then(function() {
			call_back(fun);
		})
	},
	info:function(title,text,fun){
		swal({title:title, text:text,type:"info"}).then(function() {
			call_back(fun);
		})
	},
	question:function(title,text,fun){
		swal({title:title, text:text,type:"question"}).then(function() {
			call_back(fun);
		})
	},
	alert:function(title,fun){
		swal({title:title}).then(function() {
			call_back(fun);
		})
	},
	/**
	 * 确认提示
	 * @param title		标题
	 * @param text	    内容
	 * @param ok_text	确认提示文字
	 * @param cancel_text内容提示文字
	 * @param ok		确认回调
	 * @param cancel	取消回调
	 */
	confirm: function (title,text,ok_text,cancel_text, ok, cancel) {
		if(ok_text == ''){
			ok_text = "确认";
		}
		if(cancel_text == ''){
			cancel_text = "取消";
		}
		swal({
			  title: title,
			  text: text,
			  type: "question",
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: ok_text,
			  cancelButtonText: cancel_text,
			  closeOnConfirm: false,
			  closeOnCancel: false
			}).then(function(isConfirm) {
			  if (isConfirm) {
				  call_back(ok);
			  } else {
				  call_back(cancel);
			  }
			  swal.close();
		});
    }
}
function call_back(fun){
	if(typeof(fun)!="undefined"){ 
	   setTimeout(fun,500)
	}
}
var P = {
	close_win:function(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);//关闭窗口
	},
	init_date:function(form,obj){
		$('#'+obj).datetimepicker({
			 minView: "month", //选择期再跳转选择秒
	        language: 'zh-CN',
	        autoclose: true,
	        todayHighlight: true,
	        todayBtn: true,
	        format: 'yyyy-mm-dd'
//	        	format: 'yyyy-mm-dd hh:ii:ss'
	    }).on('hide',function(e) { 
	    	$('#'+form).data('bootstrapValidator') 
	    	.updateStatus(obj, 'NOT_VALIDATED',null) 
	    	.validateField(obj); 
    	});	
	},
	init_date_m:function(form,obj){
		$('#'+obj).datetimepicker({
	        language: 'zh-CN',
	        autoclose: true,
	        todayHighlight: true,
	        todayBtn: true,
	        format: 'yyyy-mm-dd hh:ii'
	    }).on('hide',function(e) { 
	    	$('#'+form).data('bootstrapValidator') 
	    	.updateStatus(obj, 'NOT_VALIDATED',null) 
	    	.validateField(obj); 
    	});	
	}
}
// 格式化日期
function genDateTimeAll(data) {
	if (data == null) {
		return "";
	} else {
		var c = new Date();
		c.setTime(data.time);
		var mon = c.getMonth() + 1;
		if (mon < 10) {
			mon = "0" + mon;
		}
		var dat = c.getDate();
		if (dat < 10) {
			dat = "0" + dat;
		}
		var hour = c.getHours();
		if (hour < 10) {
			hour = "0" + hour;
		}
		var min = c.getMinutes();
		if (min < 10) {
			min = "0" + min;
		}
		var sec = c.getSeconds();
		if (sec < 10) {
			sec = "0" + sec;
		}
		return c.getFullYear() + "-" + mon + "-" + dat + " " + hour + ":" + min
				+ ":" + sec;
	}
}
// 格式化日期
function getDateTimeMMDDHHMM(data) {
	if (data == null) {
		return "";
	} else {
		var c = new Date();
		c.setTime(data.time);
		var mon = c.getMonth() + 1;
		if (mon < 10) {
			mon = "0" + mon;
		}
		var dat = c.getDate();
		if (dat < 10) {
			dat = "0" + dat;
		}
		var hour = c.getHours();
		if (hour < 10) {
			hour = "0" + hour;
		}
		var min = c.getMinutes();
		if (min < 10) {
			min = "0" + min;
		}
		var sec = c.getSeconds();
		if (sec < 10) {
			sec = "0" + sec;
		}
		return mon + "-" + dat + " " + hour + ":" + min;
	}
}
// 秒 转时间
function getTime(data) {
	if (data == null) {
		return "";
	} else {
		var c = new Date();
		c.setTime(data);
		var min = c.getMinutes();
		if (min < 10) {
			min = "0" + min;
		}
		var sec = c.getSeconds();
		if (sec < 10) {
			sec = "0" + sec;
		}
		return min + ":" + sec;
	}
}
// 格式化日期 年月日
function genDateTimeYMD(data) {
	if (data == null) {
		return "";
	} else {
		var c = new Date();
		c.setTime(data.time);
		var mon = c.getMonth() + 1;
		if (mon < 10) {
			mon = "0" + mon;
		}
		var dat = c.getDate();
		if (dat < 10) {
			dat = "0" + dat;
		}
		return c.getFullYear() + "-" + mon + "-" + dat;
	}
}
// 扩展Date的format方法
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
/**
 * 转换日期对象为日期字符串
 * 
 * @param date
 *            日期对象
 * @param isFull
 *            是否为完整的日期数据, 为true时, 格式如"2000-03-05 01:05:04" 为false时, 格式如
 *            "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
	var pattern = "";
	if (isFull == true || isFull == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	} else {
		pattern = "yyyy-MM-dd";
	}
	return getFormatDate(date, pattern);
}
/**
 * 转换当前日期对象为日期字符串
 * 
 * @param date
 *            日期对象
 * @param isFull
 *            是否为完整的日期数据, 为true时, 格式如"2000-03-05 01:05:04" 为false时, 格式如
 *            "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatNowDate(isFull) {
	return getSmpFormatDate(new Date(), isFull);
}
/**
 * 转换long值为日期字符串
 * 
 * @param l
 *            long值
 * @param isFull
 *            是否为完整的日期数据, 为true时, 格式如"2000-03-05 01:05:04" 为false时, 格式如
 *            "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDateByLong(l, isFull) {
	return getSmpFormatDate(new Date(l), isFull);
}
/**
 * 转换long值为日期字符串
 * 
 * @param l
 *            long值
 * @param pattern
 *            格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDateByLong(l, pattern) {
	var t;
	if(!l || l == ''){
		t = '-';
	}else{
		t = getFormatDate(new Date(l), pattern);
	}
	return t;
}
/**
 * 转换日期对象为日期字符串
 * 
 * @param l
 *            long值
 * @param pattern
 *            格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
	if (date == undefined) {
		date = new Date();
	}
	if (pattern == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	}
	return date.format(pattern);
}
