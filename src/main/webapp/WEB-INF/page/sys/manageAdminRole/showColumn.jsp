<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${_title }</title>
   	<%@ include file="/WEB-INF/page/common/css.jsp" %>
    <link rel="stylesheet" href="${ctx}/css/lib/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/css/lib/ztree/zTreeStyle/zTreeNnew.css"/>
   	<style type="text/css">
		body {
			background: #ffffff none repeat scroll 0 0!important;
		  	width: 97%!important;
		  	margin-left:5px!important;
		}
	</style>
</head>
<body class="dig-body">
	<div class="main-content-wrapper">
		<div class="main-content">
			<section>
				<div class="container-fluid container-padded">
					<div class="row">
						<div class="col-md-12">
							<div class="tabs-container">
			                    <ul class="nav nav-tabs">
			                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">菜单/操作权限</a>
			                        </li>
			                    </ul>
			                    <div class="tab-content">
			                        <div id="tab-1" class="tab-pane active">
			                            <div class="panel-body" style="overflow: auto;">
			                               <ul id="columnTree" class="ztree"></ul>
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
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
  	<script type="text/javascript" src="${ctx}/js/lib/ztree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/ztree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript">
    	function closeWin(){
    		P.close_win();
    	}
	    var columnTree ;
	    var roleId;
	    var columnSetting = {
    		check: {
				enable: true,
				chkboxType:{ "Y":"ps", "N":"ps"}
			},
    		async: {
    			enable: true,
    			type: "post",
    			url: "${ctx}/manageAdminRole/loadColumnData",
    			otherParam: ["roleId", "${roleId}"]
    		},
		    callback: {
				onCheck: columnTreeCheck
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
	    function initColumnTree(roleId){
	    	$.fn.zTree.init($("#columnTree"), columnSetting, null);
	    	columnTree = $.fn.zTree.getZTreeObj("columnTree");
	    }
	    function columnTreeCheck(event,treeId,treeNode){
			if (treeNode.checked) {
				ck = 'add';
			}else{
				ck = 'move';
			}
			$.post("<c:url value='/manageAdminRole/updateRoleColumnNew'/>",
        	{
    		roleId : roleId,
    		columnId : treeNode.id,
    		ck:ck,
			 _t:Math.random()},
        	function(data){
	        	var result = eval('('+data+')'); 
	            if (result.code == '1') {
	            	parent.sys.tip('操作成功','');
	             } else {
	            	 parent.sys.tip('操作失败','');
	             }
        });
		}
		// 设置操作方法
	   function addDiyDom(treeId, treeNode) {
		   var columnId = treeNode.id;
		   var medHtml ='';
		   var meds = JSON.parse(treeNode.meds);//操作集合
		   if(meds.length == 0)
			   return;
		   for(var i =0 ; i< meds.length;i++){
			   var med = meds[i];
			   if(med.checked){// 是否选中
				   medHtml += "|<span class='med' >"+med.name+"</span><input type='checkbox' id='med_"+med.mid+"' onclick='updateRoleMed("+columnId+","+med.mid+")' checked='checked' value='"+med.mid+"' name='opName' />";
			   }else{
				   medHtml += "|<span class='med' >"+med.name+"</span><input type='checkbox' id='med_"+med.mid+"' onclick='updateRoleMed("+columnId+","+med.mid+")' value='"+med.mid+"' name='opName' />";
			   }
		   }
		   	
			var aObj = $("#" + treeNode.tId + "_a");
			if ($("#diyBtn_"+treeNode.id).length>0) return;
			var editStr = medHtml;
			aObj.append(editStr);
		};
		//更新方法
		function updateRoleMed(columnId,mid){
			var medObj = $("#med_"+mid);
			var ck;
			if (medObj.is(':checked')) {
				ck = 'add';
			}else{
				ck = 'move';
			}
			 $.post("<c:url value='/manageAdminRole/updateRoleMed'/>",
	        	{
	    		roleId : '${roleId}',
	    		mid : mid,
	    		columnId:columnId,
	    		ck:ck,
				 _t:Math.random()},
	        	function(data){
		        	var result = eval('('+data+')'); 
		        	if (result.code == '1') {
		            	parent.sys.tip('操作成功','');
		             } else {
		            	 parent.sys.tip('操作失败','');
		             }
	        });
		}
    </script>
</body>
</html>
