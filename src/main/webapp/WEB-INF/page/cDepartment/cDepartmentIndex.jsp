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
	<link href="${ctx }/plus/ztree/css/metroStyle/metroStyle.css" rel="stylesheet" />
	<style type="text/css">
	   	.ztree li a.curSelectedNode{
	   		background-color: #FFFFFF;
	   		border: 0px solid #FFFFFF;
	   		text-decoration: none;
	   	}
	   	.ztree li a{
	   		text-decoration: none;
	   	}
	   	.ztree li a:hover{
	   		text-decoration: none;
	   	}
	 	.ztree li ul .med{
	   		color:blue;
	   	}
	   	.line{
	   		height: 100%;
	   	}
	   	.refreshbtn{
	   		position: absolute;
	   		margin-top: -40px;
	   		right: 20px;
	   	}
   </style> 
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
			        <input type="hidden" id="departmentId">
			        <input type="hidden" id="departmentIds" value="${admin_user.departmentIds}">
			        <input type="hidden" id="departmentParentId">
			        <input type="hidden" id="isParent" value="1">
			        <div class="main-content-wrapper">
						<div class="main-content">
							<div class="col-md-3" style="padding-right: 5px;">
								<div class="widget-box" style="border: 3px solid #ddd;height: 100%;overflow: auto;margin-top: 10px;">
									<ul id="treeDemo" class="ztree" style="overflow: auto;"></ul>
								</div>
							</div>
							<div class="col-md-9" style="padding-left: 5px;">
								<section>
									<div class="container-fluid container-padded" style="padding-left: 5px;">
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
																<label class="control-label" for="name">用户名：</label>
																<input type="text" class="form-control" id="realName" name="realName">
															</div>
															<div class="form-group">
																<label class="control-label" for="level">工号：</label>
																<input type="text" class="form-control" id="jobNumber" name="jobNumber">
															</div>
															<div class="form-group">
																<label class="control-label" for="parentId">手机：</label>
																<input type="text" class="form-control" id="mobile" name="mobile">
															</div>
															<div class="form-group">
																<label class="control-label" for="role_id">角色：</label>
																<select class="form-control" id="role_id" name="role_id">
																	<option value="">--全部--</option>
																	<c:forEach items="${listRole}" var="role">
																		<option value="${role.roleId}">${role.roleName}</option>
																	</c:forEach>
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
																	<perm:tag permPath="/manageAdminUser/importManageAdminUser" >
																    <button type="button" onclick="importUser()" class="btn btn-info">
																        <i class="glyphicon glyphicon-import"></i>导入用户
																    </button>
																	</perm:tag>
																</div>
																<table id="table" data-toggle="table"
																	data-toolbar="#toolbar"
															        data-url="${ctx}/manageAdminUser/getManageAdminUserListDepartment"
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
																	        <th data-field="realName" data-align="center">姓名</th>
																	        <th data-field="jobNumber" data-align="center">工号</th>
																	        <th data-field="mobile" data-align="center">手机</th>
																	        <th data-field="departmentName" data-align="center">部门</th>
																	        <th data-field="roleName" data-align="center">角色</th>
																	        <th data-field="state" data-align="center" data-formatter="formatterState" data-sortable="true">状态</th>
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
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
	<script src="${ctx}/js/layui.js"></script> 
	<script type="text/javascript" src="${ctx}/plus/ztree/jquery.ztree.core.js"></script>  
	<script type="text/javascript" src="${ctx}/plus/ztree/jquery.ztree.excheck.js"></script> 
	<script type="text/javascript" src="${ctx}/plus/ztree/jquery.ztree.exedit.js"></script> 
    <script type="text/javascript">
    var zTree ;
    var treeNode1;
    var $table = $('#table');
	var setting = {
		async : {
			enable : true,//开启异步加载处理
			url : encodeURI(encodeURI("${ctx}/cDepartment/getRootNode")),
			autoParam : [ "id" ],
			dataFilter : filter,
			contentType : "application/json",
			type : "get"
		},
		<perm:tag permPath="/cDepartment/updateCDepartment" >
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			fontCss : {color:"#797979"}
		},
		edit : {
			enable: true, //单独设置为true时，可加载修改、删除图标  
			isMove: true
		},
		</perm:tag>
		
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeRemove : beforeRemove,
			beforeDrop: zTreeBeforeDrop,
			beforeEditName: beforeEditName,
			beforeClick : beforeClick  //单击事件  
		}
	};
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for (var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
		 var oldPid=treeNodes[0].pId;
	     var targetPid=targetNode.pId;
	     if(oldPid!=targetPid){
	        alert("只能在同一模块下面移动位置");
	        return false;
	     }
	     if(oldPid=="root"||targetPid=="root"){
	        alert("只能移动子模块的节点。");
	     	return false;
	     }
	     if("inner"==moveType){
	    	 alert("只能在同一模块下面移动位置");
		     return false;
	     }
	     var param = "id=" + treeNodes[0].id+"&sortId="+targetNode.sort_id+"&idn="+targetNode.id+"&sortIdn="+treeNodes[0].sort_id; 
	     $.post(encodeURI(encodeURI("${ctx}/cDepartment/updateCDepartment1?"+ param)), function(data) {
         	var result = eval('('+data+')'); 
	            if (result.code == '1') {
	            	return true;
	            }else{
	            	return false;
	            }
         });
	}
	function beforeRemove(treeId, treeNode) {
		if(treeNode.isParent){
			layer.msg("请先删除下属机构");
        	return false;
		}else{
			if (confirm("确认删除节点【" + treeNode.name + "】吗?")) { 
	            var param = "id=" + treeNode.id;  
	            $.post(encodeURI(encodeURI("${ctx}/cDepartment/removeCDepartment?"+ param)), function(data) {
	            	var result = eval('('+data+')'); 
		            if (result.code == '1') {
		            	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		            	zTree.removeNode(treeNode);
		            }else{
		            	layer.msg("请先删除机构下用户");
		            	return false;
		            }
	            });  
	        } else {  
	            return false;  
	        } 
		}
		return false;  
	}
	function beforeClick(treeId, treeNode) {
		$("#departmentId").val(treeNode.id);
// 		alert(treeNode.pId);
// 		$("#departmentIds").val(treeNode.pId);
		if(typeof(treeNode.pIds)!="undefined"){
			$("#departmentIds").val(treeNode.pIds);
		}else{
			$("#departmentIds").val(treeNode.id);
		}
		if(treeNode.isParent){
			$("#isParent").val(1);
		}else{
			$("#isParent").val(0);
		}
		search();
		$(".node_name").css("color","#797979");
		$("#"+treeNode.tId+"_span").css("color","red");
	}
	
	function beforeEditName(treeId, treeNode, newName) {
		treeNode1 = treeNode;
		layer.open({
			title : "修改",
		  	type: 2,
		  	zIndex:900,
		  	area: ['60%', '80%'],
		  	shade: false,
		  	content: '<c:url value="/cDepartment/toUpdateLevelCourse?id='+treeNode.id+'&level='+treeNode.level+'&pId='+treeNode.id+'"/>'
		 });
		return true;
	}
	
	function beforeRename(treeId, treeNode, newName) {
		if (newName.length == 0) {
			layer.msg("节点名称不能为空.");
			return false;
		}
		if(treeNode.level<3){
			var param = "id=" + treeNode.id + "&name=" + newName;
			$.post(encodeURI(encodeURI("${ctx}/cDepartment/updateCDepartment?"
					+ param)));
		}else{
			layer.open({
				title : "修改",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '<c:url value="/cDepartment/toUpdateLevelCourse?id='+treeNode.id+'&level='+treeNode.level+'&pId='+treeNode.id+'"/>'
			 });
		}
		return true;
	}

	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function() {
				treeNode1 = treeNode;
				layer.open({
					title : "添加",
				  	type: 2,
				  	zIndex:900,
				  	area: ['60%', '80%'],
				  	shade: false,
				  	content: '<c:url value="/cDepartment/toAddLevelCourse?level='+treeNode.level+'&pId='+treeNode.id+'"/>'
				 });
			});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting);
		layer.config({extend: '../extend/layer.ext.js'});
// 		searchData(1,0);
	});
	
	function setTreeNodeName(name){
		treeNode1.name = name;
	}
	
	//检索入口
	function search(){
		$table.bootstrapTable('refresh');
	}
	function queryParams(params){
		var queryParam = {
			jobNumber:$("#jobNumber").val(),
			realName:$("#realName").val(),
			mobile:$("#mobile").val(),
			state:$("#state").val(),
			createTime:$("#createTime").val(),
			role_id:$("#role_id").val(),
			sex:$("#sex").val(),
			departmentId:$("#departmentId").val(),
			departmentIds:$("#departmentIds").val(),
			departmentParentId:$("#departmentParentId").val(),
			isParent:$("#isParent").val(),
			limit:params.limit,
				offset:params.offset,
				sort: params.sort,
				order:	params.order
		}
		return queryParam;
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
 		var departmentId = $("#departmentId").val();
 		if(departmentId!=""){
 			layer.open({
				title : "添加",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/manageAdminUser/toAddFromDepartment?departmentId='+departmentId
			 });
 		}else{
 			layer.msg("请选择机构");
 		}
 		
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
 	function search(){
		$table.bootstrapTable('refresh');
	}
 	
 	function setTreeNodeName(name){
 		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
 		treeNode1.name = name;
    	zTree.updateNode(treeNode1);
 	}
 	
 	function importUser(){
 		layer.open({
			title : "导入用户",
		  	type: 2,
		  	zIndex:900,
		  	area: ['60%', '80%'],
		  	shade: false,
		  	content: '${ctx}/manageAdminUser/toImportUser' 
		 });
 	}
    </script>
</body>
</html>
