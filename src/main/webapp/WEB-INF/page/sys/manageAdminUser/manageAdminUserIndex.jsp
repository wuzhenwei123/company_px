<%@page import="com.base.utils.ConfigConstants"%>
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
	<c:set value="<%=ConfigConstants.DB_ROLE_ID%>" var="db_role_id"></c:set>
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
															<label class="control-label" for="adminName">登录账号：</label>
															<input type="text" class="form-control" id="adminName" name="adminName">
														</div>
														<div class="form-group">
															<label class="control-label" for="nickName">昵称：</label>
															<input type="text" class="form-control" id="nickName" name="nickName">
														</div>
														<div class="form-group">
															<label class="control-label" for="realName">真实姓名：</label>
															<input type="text" class="form-control" id="realName" name="realName">
														</div>
														<div class="form-group">
															<label class="control-label" for="mobile">手机：</label>
															<input type="text" class="form-control" id="mobile" name="mobile">
														</div>
<!-- 														<div class="form-group"> -->
<!-- 															<label class="control-label" for="phone">电话：</label> -->
<!-- 															<input type="text" class="form-control" id="phone" name="phone"> -->
<!-- 														</div> -->
														<div class="form-group">
															<label class="control-label" for="state">状态：</label>
															<select class="form-control" id="state" name="state">
																<option value="">--全部--</option>
																<option value="1">正常</option>
																<option value="0">禁用</option>
															</select>
														</div>
														<div class="form-group">
															<label class="control-label" for="pay_state">支付状态：</label>
															<select class="form-control" id="pay_state" name="pay_state">
																<option value="">--全部--</option>
																<option value="1">已支付</option>
																<option value="0">未支付</option>
															</select>
														</div>
														<c:if test="${not empty listRole }">
														<div class="form-group">
															<label class="control-label" for="role_id">角色：</label>
															<select class="form-control" id="role_id" name="role_id">
																<option value="">--全部--</option>
																<c:forEach items="${listRole}" var="role">
																	<option value="${role.roleId}">${role.roleName}</option>
																</c:forEach>
															</select>
														</div>
														</c:if>
<!-- 														<div class="form-group"> -->
<!-- 															<label class="control-label" for="sex">性别：</label> -->
<!-- 															<select class="form-control" id="sex" name="sex"> -->
<!-- 																<option value="">--全部--</option> -->
<!-- 																<option value="1">男</option> -->
<!-- 																<option value="0">女</option> -->
<!-- 															</select> -->
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
														    	<perm:tag permPath="/manageAdminUser/addManageAdminUser" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/manageAdminUser/removeAllManageAdminUser" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/manageAdminUser/getManageAdminUserList"
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
																        <th data-checkbox="true"></th>
																        <th data-field="adminName" data-align="center">登录账号</th>
																        <th data-field="nickName" data-align="center">昵称</th>
																        <th data-field="realName" data-align="center">真实姓名</th>
																        <th data-field="mobile" data-align="center">手机</th>
<!-- 																        <th data-field="phone" data-align="center">电话</th> -->
																        <th data-field="lastLogin" data-align="center" data-formatter="formatterLastLogin" data-sortable="true">最后登录日期</th>
																        <th data-field="loginIP" data-align="center">登录IP</th>
<!-- 																        <th data-field="pwdModifyTime" data-align="center" data-formatter="formatterPwdModifyTime" data-sortable="true">密码修改日期</th> -->
																        <th data-field="state" data-align="center" data-formatter="formatterState" data-sortable="true">状态</th>
																        <th data-field="createTime" data-align="center" data-formatter="formatterCreateTime" data-sortable="true">添加日期</th>
																        <th data-field="roleName" data-align="center">角色</th>
<!-- 																        <th data-field="sex" data-formatter="formatterSex" data-align="center" data-sortable="true">性别</th> -->
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
	    	P.init_date("query_form","lastLogin");
	    	P.init_date("query_form","pwdModifyTime");
	    	P.init_date("query_form","createTime");
	    });
    	//检索入口
    	function search(){
    		$table.bootstrapTable('refresh');
    	}
    	function queryParams(params){
    		var queryParam = {
				adminId:$("#adminId").val(),
				pay_state:$("#pay_state").val(),
				adminName:$("#adminName").val(),
				nickName:$("#nickName").val(),
				passwd:$("#passwd").val(),
				realName:$("#realName").val(),
				mobile:$("#mobile").val(),
				phone:$("#phone").val(),
				lastLogin:$("#lastLogin").val(),
				loginIP:$("#loginIP").val(),
				pwdModifyTime:$("#pwdModifyTime").val(),
				state:$("#state").val(),
				createTime:$("#createTime").val(),
				createAdminName:$("#createAdminName").val(),
				createrId:$("#createrId").val(),
				headImg:$("#headImg").val(),
				role_id:$("#role_id").val(),
				sex:$("#sex").val(),
				limit:params.limit,
   				offset:params.offset,
   				sort: params.sort,
   				order:	params.order
    		}
    		return queryParam;
    	}
    	//操作按钮设置
	    function actionFormatter(value, row, index) {
	    	var upTitle='';
	    	var ewmTitle='';
	    	var setParent='';
	    	if(row.role_id=="<%=ConfigConstants.DB_ROLE_ID %>"){
	    		upTitle = ' | <a class="setDefault" href="javascript:void(0)" title="设为默认拓展员"><i class="glyphicon glyphicon-leaf"></i></a>';
	    		ewmTitle = ' | <a class="getQRcode" href="javascript:void(0)" title="获取二维码"><i class="glyphicon glyphicon-picture"></i></a>';
	    	}
	    	if(row.role_id=="<%=ConfigConstants.TWO_DB_ROLE_ID %>"){
	    		ewmTitle = ' | <a class="getQRcode" href="javascript:void(0)" title="获取二维码"><i class="glyphicon glyphicon-picture"></i></a>';
	    	}
	    	if(row.role_id=="<%=ConfigConstants.LS_ROLE_ID %>"||row.role_id=="<%=ConfigConstants.DL_ROLE_ID %>"){
		    	setParent = ' | <a class="setParent" href="javascript:void(0)" title="设置上级"><i class="glyphicon glyphicon-user"></i></a>';
	    	}
    		var checkTitle = ' | <a class="checkState" href="javascript:void(0)" title="审核"><i class="glyphicon glyphicon-check"></i></a>';
    		var reset_passwd = ' | <a class="reset_password" href="javascript:void(0)" title="重置密码"><i class="fa fa-fw fa-unlock-alt"></i></a>';
    		var wholesaler = ' | <a class="wholesaler" href="javascript:void(0)" title="批发商">批发商</a>';
    		var dealer = ' | <a class="dealer" href="javascript:void(0)" title="零售商">零售商</a>';
    		var agent = ' | <a class="agent" href="javascript:void(0)" title="电商代理">电商代理</a>';
    		
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/manageAdminUser/updateManageAdminUser" >',
	            '<a class="edit" href="javascript:void(0)" title="编辑">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/removeManageAdminUser" >',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>',
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/setDefaultUser" >',
	            upTitle,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/getQRcode" >',
	            ewmTitle,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/checkState" >',
	            checkTitle,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/setParent" >',
	            setParent,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/reset_passwd" >',
	            reset_passwd,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/wholesaler" >',
	            wholesaler,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/wholesaler" >',
	            dealer,
	            '</perm:tag>',
	            '<perm:tag permPath="/manageAdminUser/wholesaler" >',
	            agent,
	            '</perm:tag>',
	            '</div>'
	        ].join('');
	    }
    	//操作事件
	    window.actionEvents = {
    		'click .reset_password': function (e, value, row, index) {
    			reset_password(row.adminId);
    	    },
    		'click .wholesaler': function (e, value, row, index) {
    			var url = "${ctx}/manageAdminUser/wholesaler/"+row.adminId+"/<%=ConfigConstants.PF_ROLE_ID %>";
    			open(url);
    	    },
    		'click .dealer': function (e, value, row, index) {
    			var url ="${ctx}/manageAdminUser/wholesaler/"+row.adminId+"/<%=ConfigConstants.LS_ROLE_ID %>";
    			open(url);
    	    },
    		'click .agent': function (e, value, row, index) {
    			var url = "${ctx}/manageAdminUser/wholesaler/"+row.adminId+"/<%=ConfigConstants.DL_ROLE_ID %>";
    			open(url);
    	    },
    	    'click .edit': function (e, value, row, index) {
    	        toUpdate(row.adminId);
    	    },
    	    'click .setParent': function (e, value, row, index) {
    	        toSetParent(row.adminId);
    	    },
    	    'click .remove': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要删除',
	    			'',
	    			'删除',
	    			'取消',
	    		function(){
	    			del(row.adminId);
	    		},'')
    	    },
    	    'click .checkState': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要审核',
	    			'',
	    			'确定',
	    			'取消',
	    		function(){
    	    			checkState(row.adminId);
	    		},'')
    	    },
    	    'click .setDefault': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要设为默认拓展业务员',
	    			'',
	    			'是',
	    			'否',
	    		function(){
    	    		setDefaultUser(row.adminId);
	    		},'')
    	    },
    	    'click .getQRcode': function (e, value, row, index) {
    	    	getQRcode(row.adminId);
    	    }
    	};
	 // 重置密码
    	function reset_password(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageAdminUser/reset_passwd?adminId='+id 
			 });
    	}
		 function open(url){
			 layer.open({
					title : "列表",
				  	type: 2,
				  	zIndex:900,
				  	area: ['80%', '80%'],
				  	shade: false,
				  	content: url 
				 });
		 }
    	function to_add(){
    		layer.open({
				title : "添加",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageAdminUser/toAdd'
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageAdminUser/toUpdate?adminId='+id 
			 });
    	}
    	function toSetParent(id){
    		layer.open({
				title : "设置上级",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageAdminUser/toSetParent?adminId='+id 
			 });
    	}
    	function getQRcode(id){
    		layer.open({
				title : "获取二维码",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageAdminUser/getQRcode?adminId='+id 
			 });
    	}
    	function del(adminId){
   		   if (adminId != ""){ 
   				$.post("<c:url value='/manageAdminUser/removeManageAdminUser'/>",
	        	{
					adminId	:adminId,
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
    	function checkState(adminId){
   		   if (adminId != ""){ 
   				$.post("<c:url value='/manageAdminUser/checkState'/>",
	        	{
					adminId	:adminId,
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
    	function setDefaultUser(adminId){
   		   if (adminId != ""){ 
   				$.post("<c:url value='/manageAdminUser/setDefaultUser'/>",
	        	{
					adminId	:adminId,
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
	    	    	$.post("<c:url value='/manageAdminUser/removeAllManageAdminUser'/>",
	            	{
	    	    		adminIds :JSON.stringify(ids),
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
    	function formatterSex(sex){
	    	var s = "-";
	    	if(sex!=null){
	    		if(sex == '1'){
	    			s = '男';
	    		}
	    		if(sex == '0'){
	    			s = '女';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterLastLogin(lastLogin){
	    	return getFormatDateByLong(lastLogin,"yyyy-MM-dd hh:mm:ss");
	    }
	    function formatterPwdModifyTime(pwdModifyTime){
	    	return getFormatDateByLong(pwdModifyTime,"yyyy-MM-dd hh:mm:ss");
	    }
	    function formatterState(state){
	    	var s = "-";
	    	if(state!=null){
	    		if(state == '1'){
	    			s = '已审核';
	    		}
	    		if(state == '0'){
	    			s = '未审核';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterDB(is_default_db){
	    	var s = "-";
	    	if(is_default_db!=null){
	    		if(is_default_db == '1'){
	    			s = '是';
	    		}
	    		if(is_default_db == '0'){
	    			s = '否';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterPayState(pay_state){
	    	var s = "-";
	    	if(pay_state!=null){
	    		if(pay_state == '1'){
	    			s = '已支付';
	    		}
	    		if(pay_state == '0'){
	    			s = '未支付';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterCreateTime(createTime){
	    	return getFormatDateByLong(createTime,"yyyy-MM-dd hh:mm:ss");
	    }
	    /**
	     * 获取表格checkbox
	     * @returns
	     */
	    function getIdSelections() {
	        return $.map($table.bootstrapTable('getSelections'), function (row) {
	            return row.adminId;
	        });
	    }
    </script>
</body>
</html>
