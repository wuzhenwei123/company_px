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
    <link rel="stylesheet" href="${ctx}/css/lib/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/css/lib/ztree/zTreeStyle/zTreeNnew.css"/>
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
													<h3 class="panel-title">权限管理</h3>
												</div>
												<div class="panel-body" style="overflow: auto;">
													<ul id="columnTree" class="ztree"></ul>
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
  	<script type="text/javascript" src="${ctx}/js/lib/ztree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/ztree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript">
	    var columnTree ;
	    var roleId;
	    var columnSetting = {
			async: {
				enable: true,
				type: "post",
				url: "${ctx}/manageAdminRole/loadColumnData?_t="+Math.random(),
				otherParam: ["roleId", "${roleId}"]
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				addDiyDom: addDiyDom,
				fontCss : {color:"#797979"}
			}
		};
		$(document).ready(function() {
			roleId = '${roleId}';
	        initColumnTree(roleId);
	    });
		function init(){
	    	document.location.reload();
	    }
		function initColumnTree(roleId){
	    	$.fn.zTree.init($("#columnTree"), columnSetting, null);
	    	columnTree = $.fn.zTree.getZTreeObj("columnTree");
	    }
		// 设置操作方法
	   function addDiyDom(treeId, treeNode) {
			var addHtml = "<perm:tag permPath='/columnMethod/addColumnMethod' ><img title='添加' src=\"${ctx}/images/add.png\" onclick='to_add("+treeNode.id+");' ></perm:tag>";//添加按钮 
			
	 	   	var medHtml ='';
			if(!treeNode.meds)
				return;
	 	  	var meds = JSON.parse(treeNode.meds);//操作集合
	 	  	
	 	   	for(var i =0 ; i< meds.length;i++){
	 		   var med = meds[i];
			   medHtml += "|<span class='med' >"+med.name+"</span><perm:tag permPath='/manageAdminRole/updateManageAdminRole' ><img src=\"${ctx}/images/edit.png\" title='编辑' onclick='toUpdate("+med.mid+")'/></perm:tag><perm:tag permPath='/manageAdminRole/removeManageAdminRole' ><img src=\"${ctx}/images/remove.png\" title='删除' onclick='del("+med.mid+")' /></perm:tag>";
	 	   	}
	 	   if(treeNode.level == 1){
		 	  	medHtml += "|"+addHtml;
	 	   }
	 	   var aObj = $("#" + treeNode.tId + "_a");
	 		if ($("#diyBtn_"+treeNode.id).length>0) return;
	 		var editStr = medHtml;
	 		aObj.append(editStr);
		};
		function to_add(columnId){
    	    layer.open({
    	        type: 2,
    	        skin: 'layui-layer-lan',
    	        title: '添加',
    	        zIndex:900,
    	        shadeClose: true,
    	        shade: false,
    	        area: ['60%', '80%'],
    	        content: '${ctx}/columnMethod/toAdd?columnId='+columnId
    	      });
    	}
    	function toUpdate(id){
    	    layer.open({
    	        type: 2,
    	        skin: 'layui-layer-lan',
    	        title: '更新',
    	        zIndex:900,
    	        shadeClose: true,
    	        shade: false,
    	        area: ['60%', '80%'],
    	        content: '${ctx}/columnMethod/toUpdate?mid='+id 
    	      });
    	}
    	function del(mid){
    		if (mid != ""){
    			sys.confirm(
        	    		'是否要删除',
    	    			'',
    	    			'删除',
    	    			'取消',
    	    		function(){
        	    			$.post("<c:url value='/columnMethod/removeColumnMethod'/>",
      	    		        	{
      	    						mid	:mid,
      	    						ranNum:Math.random()
      	    					},
      	    		        	function(data){
      	    			        	var result = eval('('+data+')'); 
      	    			            if (result.code == '1') {
      	    			            	sys.success(result.message,'',function(){
      	    			            		init();
      	    			                });
      	    			             } else {
      	    			             	sys.error(result.message,'',function(){
      	    			                });
      	    			             }
      	    			        });
    	    		},'')
	   	  	}
	  	 }
    </script>
</body>
</html>
