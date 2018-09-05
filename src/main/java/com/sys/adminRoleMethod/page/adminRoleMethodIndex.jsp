<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="sys.title" /></title>
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/js/plugins/My97DatePicker/skin/WdatePicker.css" rel="stylesheet">
    <link href="${ctx}/css/animate.min.css" rel="stylesheet">
    <link href="${ctx}/css/style.min.css" rel="stylesheet">
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<div class="panel">
    		<form class="form-horizontal" onsubmit="return false;">
    		<div class="panel-body">
			    <div class="row">
	                <div class="col-sm-3">
		    			<div class="form-group">
		    				<label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.id" />：</label>
		    				<div class="col-sm-5">
	                            <input type="text" id="id" name="id" class="form-control"> 
	                        </div>
		    			</div>
			    	</div>
	                <div class="col-sm-3">
		    			<div class="form-group">
		    				<label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.roleId" />：</label>
		    				<div class="col-sm-5">
	                            <input type="text" id="roleId" name="roleId" class="form-control"> 
	                        </div>
		    			</div>
			    	</div>
	                <div class="col-sm-3">
		    			<div class="form-group">
		    				<label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.mid" />：</label>
		    				<div class="col-sm-5">
	                            <input type="text" id="mid" name="mid" class="form-control"> 
	                        </div>
		    			</div>
			    	</div>
	                <div class="col-sm-3">
		    			<div class="form-group">
		    				<label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.columnId" />：</label>
		    				<div class="col-sm-5">
	                            <input type="text" id="columnId" name="columnId" class="form-control"> 
	                        </div>
		    			</div>
			    	</div>
			    </div>
    		</div>
    		<div class="panel-footer text-center">
    			<button type="button" onclick="search()" class="btn btn-success"><spring:message code="sys.main.query" /></button>
    			<button type="reset" class="btn btn-warning"><spring:message code="sys.main.reset" /></button>
    		</div>
    		</form>
    	</div>
    	<div class="panel">
    		<div class="panel-body">
	    		<div class="row">
	            	<div class="col-sm-12">
		    			<div id="toolbar">
					    	<perm:tag permPath="/adminRoleMethod/addAdminRoleMethod" >
					    	<button type="button" onclick="to_add()" class="btn btn-primary">
						        <i class="glyphicon glyphicon-plus"></i><spring:message code="sys.main.add" />
						    </button>
							</perm:tag>
							<perm:tag permPath="/adminRoleMethod/removeAllAdminRoleMethod" >
						    <button type="button" onclick="del_all()" class="btn btn-warning">
						        <i class="glyphicon glyphicon-trash"></i><spring:message code="sys.main.batch.delete" />
						    </button>
							</perm:tag>
						</div>
						<table id="table" data-toggle="table"
							data-toolbar="#toolbar"
					        data-url="${ctx}/adminRoleMethod/getAdminRoleMethodList"
					        data-pagination="true"
					        data-side-pagination="server"
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
							        <th data-field="id" data-align="center" data-sortable="true"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.id" /></th>
							        <th data-field="roleId" data-align="center" data-sortable="true"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.roleId" /></th>
							        <th data-field="mid" data-align="center" data-sortable="true"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.mid" /></th>
							        <th data-field="columnId" data-align="center" data-sortable="true"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.columnId" /></th>
									<th data-align="center" data-formatter="actionFormatter" data-events="actionEvents"><spring:message code="sys.main.operation" /></th>
							    </tr>
						    </thead>
						</table>
	            	</div>
	            </div>
    		</div>
    	</div>
    </div>
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/bootstrap.min.js"></script>
    <script src="${ctx}/js/content.min.js"></script>
    <script src="${ctx}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${ctx}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="${ctx}/js/plugins/bootstrap-table/locale/bootstrap-table-${sys_lang }.min.js"></script>
    <script src="${ctx}/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script src="${ctx}/js/common.js"></script>
    <script type="text/javascript">
	    var $table = $('#table');

    	//检索入口
    	function search(){
    		var queryParam = {
   				id:$("#id").val(),
   				roleId:$("#roleId").val(),
   				mid:$("#mid").val(),
   				columnId:$("#columnId").val()
    		}
    		$('#table').bootstrapTable('refresh', {query:queryParam});
    	}
    	//操作按钮设置
	    function actionFormatter(value, row, index) {
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/adminRoleMethod/updateAdminRoleMethod" >',
	            '<a class="edit" href="javascript:void(0)" title="<spring:message code="sys.main.edit" />">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/adminRoleMethod/removeAdminRoleMethod" >',
	            '<a class="remove" href="javascript:void(0)" title="<spring:message code="sys.main.delete" />">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>',
	            '</perm:tag>',
	            '</div>'
	        ].join('');
	    }
    	//操作事件
	    window.actionEvents = {
    	    'click .edit': function (e, value, row, index) {
    	        toUpdate(row.id);
    	    },
    	    'click .remove': function (e, value, row, index) {
    	    	sys.confirm(
	    			'<spring:message code="sys.main.layer.title" />',
	    			'<spring:message code="sys.main.confirm.delete" />?',
	    			'<spring:message code="sys.main.ok" />',
	    			'<spring:message code="sys.main.cancel" />',
	    		function(){
	    			del(row.id);
	    		},'')
    	    }
    	};
    	function to_add(){
    	    parent.layer.open({
    	        type: 2,
    	        skin: 'layui-layer-lan',
    	        title: '<spring:message code="sys.main.add" />',
    	        zIndex:2100,
    	        shadeClose: true,
    	        shade: false,
    	        maxmin: true, //开启最大化最小化按钮
    	        area: ['80%', '80%'],
    	        content: '${ctx}/adminRoleMethod/toAdd',
    	        end: function () {
    	        	search();
                }
    	      });
    	}
    	function toUpdate(id){
    	    parent.layer.open({
    	        type: 2,
    	        skin: 'layui-layer-lan',
    	        title: '<spring:message code="sys.main.edit" />',
    	        zIndex:2100,
    	        shadeClose: true,
    	        shade: false,
    	        maxmin: true, //开启最大化最小化按钮
    	        area: ['80%', '80%'],
    	        content: '${ctx}/adminRoleMethod/toUpdate?id='+id ,
    	        end: function () {
    	        	search();
                }
    	      });
    	}
    	function del(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/adminRoleMethod/removeAdminRoleMethod'/>",
	        	{
					id	:id,
					ranNum:Math.random()
				},
	        	function(data){
		        	var result = eval('('+data+')'); 
		            if (result.code == '1') {
		            	sys.msg('<spring:message code="sys.main.success" />');
	    	    		search();
		             } else {
		            	 sys.msg('<spring:message code="sys.main.fail" />');
		             }
		        });
	   	  	}
	  	 }
    	function del_all(){
   	    	var ids = getIdSelections();
   	    	if(ids && ids!=''){
	    		sys.confirm('<spring:message code="sys.main.layer.title" />',
	    				'<spring:message code="sys.main.confirm.delete" />?',
	    				'<spring:message code="sys.main.ok" />',
	    				'<spring:message code="sys.main.cancel" />',
	    			function(){
	    	    	$.post("<c:url value='/adminRoleMethod/removeAllAdminRoleMethod'/>",
	            	{
	    	    		ids :JSON.stringify(ids),
	    				ranNum:Math.random()},
	    	        	function(data){
	    		        	var result = eval('('+data+')'); 
	    		            if (result.code == '1') {
	    		            	sys.msg('<spring:message code="sys.main.success" />');
	    		            	search();
	    		             }
	    	        });
		    	},'')
   	    	}
    	}
    	// 以下是扩展
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
