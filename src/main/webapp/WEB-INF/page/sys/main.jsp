<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>${_title }</title>
	<link href="favicon1.ico" rel="icon" type="image/x-icon" />
	<%@ include file="/WEB-INF/page/common/css.jsp" %>
</head>
<body>
	<!--头部导航开始-->
	<%@ include file="/WEB-INF/page/common/head.jsp" %>
	<!--头部导航开始-->
	<div class="${sys_layout }" id="content-container">
		<div class="content-wrapper">
			<div class="row">
				<div class="side-nav-content">
					 <!--左侧导航开始-->
			        <%@ include file="/WEB-INF/page/common/left.jsp" %>
			        <!--左侧导航结束-->
			        
			        <div class="main-content-wrapper">
						<div class="main-content">
							<section>
								<div class="container-fluid container-padded">
									<div class="row">
										<div class="col-md-12">
											<div class="panel panel-plain">
												<div class="panel-heading">
													<h3 class="panel-title">首页</h3>
												</div>
												<div class="panel-body" style="overflow: auto;">
<!-- 													<h3>欢迎使用${_title }</h3> -->
													<div id="main" style="width: 98%;height: 400px;">
													</div>
													<div id="main1" style="width: 98%;height: 400px;">
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
	<script src="${ctx}/js/echarts.min.js"></script>
	<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('main'));
	var myChart1 = echarts.init(document.getElementById('main1'));
	$(document).ready(function(){
		var roleId = '${admin_user.role_id}';
		if(roleId=="1"){
// 	    	searchData();
// 	    	searchData1();
		}
    });

	function searchData(){
		var index = layer.load(0, {shade: false});
		var year_str = $("#fYear_str").val();
		$.getJSON("${ctx}/txDataAnalysisController/userCountAnalysis",
    		{
				year_str:year_str,
				_t: Math.random()
	        },function(data){
            var result = data;
            if (result.code == 1) {
            	layer.close(index);
            	var json = eval("("+result.rows+")");
            	var option = "{title: {text: '"+json.year_str+"年用户累计数量',align: 'auto'},color: ['#feb830','#1eb4ec'],";
            	option = option+ "tooltip: {trigger: 'axis',axisPointer: { type: 'shadow' }},";
            	option = option+ "legend: {data: ['用户审核数量', '用户总数量'],align: 'auto',top: 30},";
            	option = option+ "grid: {left: '3%',right: '4%', bottom: '3%',containLabel: true},";
            	option = option+ "toolbox: {feature: {saveAsImage: {}}},xAxis: [{type: 'category',data: ["+json.mouthY_str+"]}],";
            	option = option+ "yAxis: [{type: 'value'}],";
            	option = option+ "series: [{name: '用户审核数量',label: {normal: {show: true,position: 'top'}},type: 'bar',data: ["+json.data_str+"]},";
            	option = option+ "{name: '用户总数量',label: {normal: {show: true,position: 'top'}},type: 'bar',data: ["+json.data_str1+"]}]}";
            	myChart.setOption(eval("("+option+")"));
            } else {
            	tipError("系统异常!");
            } 
	    });
	}
	function searchData1(){
		var index = layer.load(0, {shade: false});
		var year_str = $("#fYear_str").val();
		$.getJSON("${ctx}/txDataAnalysisController/orderCountAnalysis",
    		{
				year_str:year_str,
				_t: Math.random()
	        },function(data){
            var result = data;
            if (result.code == 1) {
            	layer.close(index);
            	var json = eval("("+result.rows+")");
            	var option = "{title: {text: '"+json.year_str+"年订单累计交易额',align: 'auto'},color: ['#feb830','#1eb4ec'],";
            	option = option+ "tooltip: {trigger: 'axis',axisPointer: { type: 'shadow' }},";
            	option = option+ "legend: {data: ['有效订单交易额', '总订单交易额'],align: 'auto',top: 30},";
            	option = option+ "grid: {left: '3%',right: '4%', bottom: '3%',containLabel: true},";
            	option = option+ "toolbox: {feature: {saveAsImage: {}}},xAxis: [{type: 'category',data: ["+json.mouthY_str+"]}],";
            	option = option+ "yAxis: [{type: 'value'}],";
            	option = option+ "series: [{name: '有效订单交易额',label: {normal: {show: true,position: 'top'}},type: 'bar',data: ["+json.data_str+"]},";
            	option = option+ "{name: '总订单交易额',label: {normal: {show: true,position: 'top'}},type: 'bar',data: ["+json.data_str1+"]}]}";
            	myChart1.setOption(eval("("+option+")"));
            } else {
            	tipError("系统异常!");
            } 
	    });
	}
    </script>
</body>
</html>