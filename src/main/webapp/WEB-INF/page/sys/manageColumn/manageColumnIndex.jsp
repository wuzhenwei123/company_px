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
															<label class="control-label" for="columnName">菜单名称：</label>
															<input type="text" class="form-control" id="columnName" name="columnName">
														</div>
														<div class="form-group">
															<label class="control-label" for="parentColumnID">父菜单：</label>
															<select id="parentColumnID" name="parentColumnID" class="form-control"> 
										                        <option value="">--全部--</option>
										                        <option value="-1">父菜单</option>
										                        <c:forEach items="${parentList }" var="column">
										                        <option value="${column.columnId }">
										                        &nbsp;&nbsp;|-${column.columnName }
										                        </option> 
										                        </c:forEach> 
										                	</select>  
														</div>
														<div class="form-group">
															<label class="control-label" for="state">状态：</label>
															<select class="form-control" id="state" name="state">
																<option value="">--全部--</option>
																<option value="1">正常</option>
																<option value="0">禁用</option>
															</select>
														</div>
<!-- 														<div class="form-group"> -->
<!-- 															<label class="control-label" for="use_state">use_state：</label> -->
<!-- 															<input type="text" class="form-control" id="use_state" name="use_state"> -->
<!-- 														</div> -->
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
														    	<perm:tag permPath="/manageColumn/addManageColumn" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/manageColumn/removeAllManageColumn" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/manageColumn/getManageColumnList"
														        data-pagination="true"
														        data-show-refresh="true"
														        data-side-pagination="server"
														        data-query-params="queryParams"
														        data-page-list="[10, 50, 100, 200]"
														        data-show-toggle="true"
									           					data-show-columns="true"
									           					data-striped=true   
									           					data-sort-name='sort_id'
									           					data-page-number=1
									           					data-page-size=10
									           					data-sort-order="asc"
									           					data-id-field="id"
														        >
															    <thead>
																    <tr>
																        <th data-checkbox="true"></th>
																        <th data-field="columnName" data-align="center">菜单名称</th>
																        <th data-field="columnUrl" data-align="center">链接</th>
																        <th data-field="parentColumnName" data-align="center">父菜单</th>
																        <th data-field="state" data-align="center" data-formatter="formatterState" data-sortable="true">状态</th>
																        <th data-field="sort_id" data-align="center" data-formatter="formatterSort" data-sortable="true">排序</th>
																        <th data-field="columnImg" data-align="center" data-formatter="formatterImg">菜单图片</th>
																        <th data-field="remark" data-align="center">备注</th>
<!-- 																        <th data-field="use_state" data-align="center" data-sortable="true">use_state</th> -->
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
	    });
    	//检索入口
    	function search(){
    		$table.bootstrapTable('refresh');
    	}
    	function queryParams(params){
    		var queryParam = {
				columnId:$("#columnId").val(),
				column_en_name:$("#column_en_name").val(),
				columnName:$("#columnName").val(),
				columnUrl:$("#columnUrl").val(),
				parentColumnID:$("#parentColumnID").val(),
				state:$("#state").val(),
				sort_id:$("#sort_id").val(),
				columnImg:$("#columnImg").val(),
				use_state:$("#use_state").val(),
				limit:params.limit,
   				offset:params.offset,
   				sort: params.sort,
   				order:	params.order
    		}
    		return queryParam;
    	}
    	//操作按钮设置
	    function actionFormatter(value, row, index) {
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/manageColumn/updateManageColumn" >',
	            '<a class="edit" href="javascript:void(0)" title="编辑">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/manageColumn/removeManageColumn" >',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>',
	            '</perm:tag>',
	            '</div>'
	        ].join('');
	    }
    	//操作事件
	    window.actionEvents = {
    	    'click .edit': function (e, value, row, index) {
    	        toUpdate(row.columnId);
    	    },
    	    'click .remove': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要删除',
	    			'',
	    			'删除',
	    			'取消',
	    		function(){
	    			del(row.columnId);
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
			  	content: '${ctx}/manageColumn/toAdd'
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageColumn/toUpdate?columnId='+id 
			 });
    	}
    	function del(columnId){
   		   if (columnId != ""){ 
   				$.post("<c:url value='/manageColumn/removeManageColumn'/>",
	        	{
					columnId	:columnId,
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
	    	    	$.post("<c:url value='/manageColumn/removeAllManageColumn'/>",
	            	{
	    	    		columnIds :JSON.stringify(ids),
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
       	function sort(sign,id){
    		var tabObj = getTabObj();
    		var sortName = tabObj.sortName;
    		var sortOrder = tabObj.sortOrder;
			var sortParam = '';
			sortParam = sortParam + "&sort=" + sortName;
			sortParam = sortParam + "&order=" + sortOrder;
			sortParam = sortParam + "&id=" + id;
			sortParam = sortParam + "&sign=" + sign;
    		$.ajax({
        		url:"${ctx}/manageColumn/sortColumn",
        		dataType:'json',
        		type:'post',
        		data:sortParam,
        		error:function(){
        			sys.error('排序失败','','');
        		},
        		success:function(data){
        			search();
        		}
        	})
    	}
    	// 以下是扩展
    	function formatterSort(value, row, index){
	    	return [
		            '<div class="btn-group">',
		            '<a onclick="sort(\'down\','+row.columnId+')" class="btn btn-white btn-xs fa fa-caret-down"></a>',
		            '<a onclick="sort(\'up\','+row.columnId+')" class="btn btn-white btn-xs fa fa-caret-up"></a>',
		            '</div>'
		        ].join('');
	    }
    	function formatterImg(img){
	    	var s = "-";
	    	if(img!= ''){
	    		s = '<i class="'+img + '"/>';
	    	}
	    	return s;	
	    }
	    function formatterState(state){
	    	var s = "-";
	    	if(state!=null){
	    		if(state == '1'){
	    			s = '正常';
	    		}
	    		if(state == '0'){
	    			s = '禁用';
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
	            return row.columnId;
	        });
	    }
	    function getTabObj() {
	        return $table.bootstrapTable('getOptions');
	    }
    </script>
</body>
</html>
