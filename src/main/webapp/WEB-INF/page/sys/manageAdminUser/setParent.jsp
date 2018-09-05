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
	<link href="favicon1.ico" rel="icon" type="image/x-icon" />
	<%@ include file="/WEB-INF/page/common/css.jsp" %>
	<style type="text/css">
		body {
			background: #ffffff none repeat scroll 0 0!important;
		  	width: 97%!important;
		  	margin-left:5px!important;
		}
		.fixed-table-toolbar{
			display: none;
		}
		.fixed-table-container{
			margin-top: 10px;
		}
	</style>
</head>
<body class="dig-body">
	<c:set value="<%=ConfigConstants.PF_ROLE_ID%>" var="pf"></c:set>
	<c:set value="<%=ConfigConstants.DL_ROLE_ID%>" var="dl"></c:set>
	<c:set value="<%=ConfigConstants.LS_ROLE_ID%>" var="ls"></c:set>
	<div class="main-content-wrapper">
		<div class="main-content">
			<section>
				<div class="container-fluid container-padded">
					<div class="panel panel-plain">
						<div class="panel-heading">数据列表</div>
			    		<div class="panel-body" style="padding-top: 0px;">
				    		<div class="row">
				            	<div class="col-sm-6">
									<table id="table1" data-toggle="table"
										data-toolbar="#toolbar"
								        data-url="${ctx}/manageAdminUser/getAdminUserListNoInRelation"
								        data-pagination="true"
								        data-show-refresh="true"
								        data-side-pagination="server"
								        data-query-params="queryParams"
								        data-page-list="[10, 50, 100, 200]"
								        data-show-toggle="true"
			           					data-show-columns="true"
			           					data-striped=true   
			           					data-sort-name='adminId'
			           					data-page-number=1
			           					data-page-size=10
			           					data-sort-order="desc"
			           					data-id-field="id"
								        >
									    <thead>
										    <tr>
										        <th data-field="nickName" data-align="center">昵称</th>
										        <th data-field="realName" data-align="center">真实姓名</th>
										        <th data-field="mobile" data-align="center">手机</th>
										        <th data-field="roleName" data-align="center">角色</th>
												<th data-align="center" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
										    </tr>
									    </thead>
									</table>
				            	</div>
				            	<div class="col-md-6">
									<table id="table2" data-toggle="table"
										data-toolbar="#toolbar"
								        data-url="${ctx}/ecMchRelation/getEcMchRelationList"
								        data-pagination="true"
								        data-show-refresh="true"
								        data-side-pagination="server"
								        data-query-params="queryParams1"
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
										    	<c:if test="${role_id_s==pf}">
											        <th data-field="realName" data-align="center">批发商</th>
										    	</c:if>
										    	<c:if test="${role_id_s==ls}">
											        <th data-field="realName" data-align="center">零售商</th>
										    	</c:if>
										        <th data-field="sortId" data-formatter="formatterSortId" data-align="center">优先级</th>
												<th data-align="center" data-formatter="actionFormatter1" data-events="actionEvents1">操作</th>
										    </tr>
									    </thead>
									</table>
								</div>
				            </div>
			    		</div>
			    	</div>
				</div>
			</section>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
	<script type="text/javascript">
	function queryParams(params){
		var queryParam = {
			Promoter_id:'{admin_user.adminId}',
			pay_state:'1',
			adminName:$("#adminName").val(),
			nickName:$("#nickName").val(),
			realName:$("#realName").val(),
			mobile:$("#mobile").val(),
			state:'1',
			role_id:'${role_id_s}',
			limit:params.limit,
			offset:params.offset,
			sort: params.sort,
			order:	params.order
		}
		return queryParam;
	}
	
	//操作按钮设置
    function actionFormatter(value, row, index) {
    	var len = '${fn:length(listRela)}';
    	var roleId = '${manageadminuser.role_id}';
		if(roleId=='${ls}'&&len<2){
			return [
	            '<div style="min-width: 50px;">',
	            '<a class="setParent" href="javascript:void(0)" title="设为上级">',
	            '<i class="glyphicon glyphicon-circle-arrow-right"></i>',
	            '</a>',
	            '</div>'
	        ].join('');
		}else if(roleId=='${dl}'&&len<1){
			return [
		            '<div style="min-width: 50px;">',
		            '<a class="setParent" href="javascript:void(0)" title="设为上级">',
		            '<i class="glyphicon glyphicon-circle-arrow-right"></i>',
		            '</a>',
		            '</div>'
		        ].join('');
		}
    }
	//操作按钮设置
    function actionFormatter1(value, row, index) {
		if(row.sortId>1){
			return [
		            '<div style="min-width: 50px;">',
		            '<a class="setFirst" href="javascript:void(0)" title="设为优先">',
		            '<i class="glyphicon glyphicon-circle-arrow-up"></i>',
		            '</a>',
		            '</div>'
		        ].join('');
		}
        
    }
	
  //操作事件
    window.actionEvents = {
	    'click .setParent': function (e, value, row, index) {
	    	sys.confirm(
	    		'是否要设为上级，设置后不可修改',
    			'',
    			'是',
    			'否',
    		function(){
	    			setParent(row.adminId);
    		},'')
	    }
	};
    window.actionEvents1 = {
   		'click .setFirst': function (e, value, row, index) {
   			setFirst(row.parentId);
   	    }
	};
	
  	function setParent(parentId){
	    $.post("<c:url value='/weixin/setParent'/>",
				{
					parentId : parentId,
					flag : 1,
					adminId : '${manageadminuser.adminId}',
					 _t:Math.random()},
			       	function(data){
			        	var result = eval('('+data+')'); 
			            if (result.c == '0') {
			            	location.reload();
			             } else {
			            	 sys.error(result.m);
			             }
		       });
  	}
  	function search(){
  		$('#table1').bootstrapTable('refresh');
  		$('#table2').bootstrapTable('refresh');
	}
	function queryParams1(params){
		var queryParam = {
// 			id:$("#id").val(),
			adminId:'${manageadminuser.adminId}',
// 			parentId:$("#parentId").val(),
			sortId:$("#sortId").val(),
			limit:params.limit,
				offset:params.offset,
				sort: params.sort,
				order:	params.order
		}
		return queryParam;
	}
	function setFirst(parentId){
		$.post("<c:url value='/weixin/setFirst'/>",
			{
				parentId : parentId,
				adminId : '${manageadminuser.adminId}',
				 _t:Math.random()},
		       	function(data){
		        	var result = eval('('+data+')'); 
		            if (result.c == '0') {
		            	location.reload();
		             } else {
		            	 sys.error(result.m);
		             }
	       });
	}
	function formatterSortId(sortId){
    	var s = "-";
    	if(sortId!=null){
    		if(sortId == '1'){
    			s = '高';
    		}
    		if(sortId == '2'){
    			s = '低';
    		}
    	}
    	return s;	
    }
    </script>
</body>
</html>