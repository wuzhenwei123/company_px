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
	<title>${_title}</title>
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
													<h3 class="panel-title">查询</h3>
													<ul class="panel-tools pull-right">
														<li>
															<a href="javascript:;" class="btn btn-sm search-tools-btn"><i class="fa fa-chevron-down"></i></a>
														</li>
													</ul>
												</div>
													<form class="form-inline search-form" role="form" id="query_form">
													<div class="panel-body">
														<div class="form-group">
															<label class="control-label" for="title">标题：</label>
															<input type="text" class="form-control" id="title" name="title">
														</div>
														<div class="form-group">
															<label class="control-label" for="state">状态：</label>
															<select class="form-control" id="state" name="state">
																<option value="">--全部--</option>
																<option value="0">未发送</option>
																<option value="1">已发送</option>
																<option value="2">已回收</option>
															</select>
														</div>
														<div class="form-group">
															<button type="reset" class="btn btn-default">重置</button>
															<button type="button" class="btn btn-primary" onclick="search()">查询</button>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="panel panel-plain">
												<div class="panel-heading">数据列表</div>
									    		<div class="panel-body" style="padding-top: 0px;">
										    		<div class="row">
										            	<div class="col-sm-12">
											    			<div id="toolbar">
														    	<perm:tag permPath="/cPaper/addCPaper" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/cPaper/removeAllCPaper" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/cPaper/getCPaperList"
														        data-pagination="true"
														        data-show-refresh="true"
														        data-side-pagination="server"
														        data-query-params="queryParams"
														        data-page-list="[10, 50, 100, 200]"
														        data-show-toggle="true"
									           					data-show-columns="true"
									           					data-striped=true   
									           					data-sort-name='id'
									           					data-page-number=1
									           					data-page-size=10
									           					data-sort-order="desc"
									           					data-id-field="id"
														        >
															    <thead>
																    <tr>
																        <th data-checkbox="true"></th>
																        <th data-field="title" data-align="center">标题</th>
																        <th data-field="score" data-align="center">总分</th>
																        <th data-field="questionCount" data-align="center">题目数量</th>
																        <th data-field="createTime" data-sort-name="createTime" data-align="center" data-formatter="formatterCreateTime" >添加时间</th>
																        <th data-field="startTime" data-sort-name="startTime" data-align="center" data-formatter="formatterStartTime" >答题开始时间</th>
																        <th data-field="endTime" data-sort-name="endTime" data-align="center" data-formatter="formatterEndTime" >答题结束时间</th>
																        <th data-field="state" data-sort-name="state" data-align="center" data-formatter="formatterState" >状态</th>
																		<th data-align="center" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
																    </tr>
															    </thead>
															</table>
										            	</div>
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
    <script type="text/javascript">
	    var $table = $('#table');
		$(document).ready(function() {
	    	P.init_date("query_form","createTime");
	    	P.init_date("query_form","startTime");
	    	P.init_date("query_form","endTime");
	    });
    	//检索入口
    	function search(){
    		$table.bootstrapTable('refresh');
    	}
    	function queryParams(params){
    		var queryParam = {
				id:$("#id").val(),
				title:$("#title").val(),
				score:$("#score").val(),
				createTime:$("#createTime").val(),
				startTime:$("#startTime").val(),
				endTime:$("#endTime").val(),
				state:$("#state").val(),
				limit:params.limit,
   				offset:params.offset,
   				sort: params.sort,
   				order:	params.order
    		}
    		return queryParam;
    	}
    	//操作按钮设置
	    function actionFormatter(value, row, index) {
    		var addCQuestion = "";
    		var exportQuestion = "";
    		var sendQuestion = "";
    		var colceQuestion = "";
    		var updateCPaper = "";
    		var userAnswerList = "";
    		
    		if(row.state==0){
    			updateCPaper = '<a class="edit" href="javascript:void(0)" title="编辑">编辑</a> | ';
    			exportQuestion = '<a class="exportQuestion" href="javascript:void(0)" title="导入试题">导入试题</a> | ';
    			sendQuestion = '<a class="sendQuestion" href="javascript:void(0)" title="发布">发布</a> | ';
    		}
    		userAnswerList = '<a class="userAnswerList" href="javascript:void(0)" title="作答列表">作答列表</a>';
    		addCQuestion = '<a class="addQuestion" href="javascript:void(0)" title="试题列表">试题列表</a> | ';
    		colceQuestion = '<a class="colceQuestion" href="javascript:void(0)" title="回收">回收</a> | ';
    		
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/cPaper/updateCPaper" >',
	            updateCPaper,
	            '</perm:tag>',
	            '<perm:tag permPath="/cPaper/removeCPaper" >',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '删除',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/cQuestion/addCQuestion" >',
	            addCQuestion,
	            '</perm:tag>',
	            '<perm:tag permPath="/cPaper/exportQuestion" >',
	            exportQuestion,
	            '</perm:tag>',
	            '<perm:tag permPath="/cPaper/sendQuestion" >',
	            sendQuestion,
	            '</perm:tag>',
	            '<perm:tag permPath="/cPaper/sendQuestion" >',
	            colceQuestion,
	            '</perm:tag>',
	            '<perm:tag permPath="/cUserPaper/getCUserPaperList" >',
	            userAnswerList,
	            '</perm:tag>',
	            '</div>'
	        ].join('');
	    }
    	//操作事件
	    window.actionEvents = {
    	    'click .edit': function (e, value, row, index) {
    	        toUpdate(row.id);
    	    },
    	    'click .addQuestion': function (e, value, row, index) {
    	    	toAddQuestion(row.id);
    	    },
    	    'click .exportQuestion': function (e, value, row, index) {
    	    	exportQuestion(row.id);
    	    },
    	    'click .userAnswerList': function (e, value, row, index) {
    	    	userAnswerList(row.id,row.title);
    	    },
    	    'click .remove': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要删除',
	    			'',
	    			'删除',
	    			'取消',
	    		function(){
	    			del(row.id);
	    		},'')
    	    },
    	    'click .sendQuestion': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'确定要发布？',
	    			'',
	    			'确定',
	    			'取消',
	    		function(){
    	    		sendQuestion(row.id,1);
	    		},'')
    	    },
    	    'click .colceQuestion': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'确定要回收？',
	    			'',
	    			'确定',
	    			'取消',
	    		function(){
    	    		sendQuestion(row.id,2);
	    		},'')
    	    }
    	};
    	function to_add(){
    		layer.open({
				title : "添加",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/cPaper/toAdd'
			 });
    	}
    	function exportQuestion(id){
    		layer.open({
				title : "导入试题",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/cPaper/toExportQuestion/'+id 
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/cPaper/toUpdate/'+id 
			 });
    	}
    	function toAddQuestion(id){
    		layer.open({
				title : "添加试题",
			  	type: 2,
			  	zIndex:900,
			  	area: ['80%', '80%'],
			  	shade: false,
			  	content: '${ctx}/cPaper/toAddQuestion/'+id 
			 });
    	}
    	function userAnswerList(id,title){
    		layer.open({
				title : title,
			  	type: 2,
			  	zIndex:900,
			  	area: ['80%', '80%'],
			  	shade: false,
			  	content: '${ctx}/cPaper/userAnswerList/'+id 
			 });
    	}
    	function del(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/cPaper/removeCPaper'/>",
	        	{
					id	:id,
					ranNum:Math.random()
				},
	        	function(data){
		        	var result = eval('('+data+')'); 
		            if (result.code == '1') {
		            	sys.success(result.message,'',function(){
		                	search();
		                });
		             } else {
		             	sys.error(result.message,'',function(){
		                });
		             }
		        });
	   	  	}
	  	 }
    	function sendQuestion(id,state){
   		   if (id != ""){ 
   				$.post("<c:url value='/cPaper/sendQuestion'/>",
	        	{
					id	:id,
					state : state,
					ranNum:Math.random()
				},
	        	function(data){
		        	var result = eval('('+data+')'); 
		            if (result.code == '1') {
		            	sys.success(result.message,'',function(){
		                	search();
		                });
		             } else {
		             	sys.error(result.message,'',function(){
		                });
		             }
		        });
	   	  	}
	  	 }
    	function del_all(){
   	    	var ids = getIdSelections();
   	    	if(ids && ids!=''){
	    		sys.confirm('确认全部删除',
	    				'',
	    				'全部删除',
	    				'取消',
	    			function(){
	    	    	$.post("<c:url value='/cPaper/removeAllCPaper'/>",
	            	{
	    	    		ids :JSON.stringify(ids),
	    				ranNum:Math.random()},
	    	        	function(data){
    		        	var result = eval('('+data+')'); 
    		            if (result.code == '1') {
    		            	sys.success(result.message,'',function(){
			                	search();
			                });
    		             }else{
    		             	sys.error(result.message,'',function(){});
    		             }
	    	        });
		    	},'')
   	    	}
    	}
    	// 以下是扩展
	    function formatterCreateTime(createTime){
	    	return getFormatDateByLong(createTime,"yyyy-MM-dd hh:mm");
	    }
	    function formatterStartTime(startTime){
	    	return getFormatDateByLong(startTime,"yyyy-MM-dd hh:mm");
	    }
	    function formatterEndTime(endTime){
	    	return getFormatDateByLong(endTime,"yyyy-MM-dd hh:mm");
	    }
	    function formatterState(state){
	    	var s = "-";
	    	if(state!=null){
	    		if(state == '1'){
	    			s = '已发布';
	    		}
	    		if(state == '0'){
	    			s = '未发布';
	    		}
	    		if(state == '2'){
	    			s = '已回收';
	    		}
	    	}
	    	return s;	
	    }
	    /**
	     * 获取表格checkbox
	     * @returns
	     */
	    function getIdSelections() {
	        return $.map($table.bootstrapTable('getSelections'), function (row) {
	            return row.id;
	        });
	    }
    </script>
</body>
</html>
