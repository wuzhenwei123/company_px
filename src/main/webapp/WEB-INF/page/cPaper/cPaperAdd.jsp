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
	<link href="${ctx }/plus/ztree/css/metroStyle/metroStyle.css" rel="stylesheet" />
	<style type="text/css">
		body {
			background: #ffffff none repeat scroll 0 0!important;
		  	width: 97%!important;
		  	margin-left:5px!important;
		}
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
<body class="dig-body">
	<div class="main-content-wrapper">
		<div class="main-content">
			<section>
				<div class="container-fluid container-padded">
					<div class="row">
						<div class="col-md-12">
							<form id="add_form" action="${ctx}/cPaper/addCPaper" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">标题：</label>
									<div class="col-md-7">
										<input type="text" id="title" name="title" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">总分：</label>
									<div class="col-md-7">
										<input type="text" id="score" name="score" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">答题开始时间</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="startTime" name="startTime" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">答题结束时间：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="endTime" name="endTime" class="form-control"/>
									</div>
								</div>
<!-- 								<div class="form-group"> -->
<!-- 		                            <label class="col-sm-3 control-label" style="text-align: left;">状态：</label> -->
<!-- 		                            <div class="col-sm-7" style="width: 100%;"> -->
<!-- 		                                <select id="state" name="state" class="form-control" style="width: 100%;"> -->
<!-- 		                                 	<option value="1">正常</option> -->
<!-- 		                                 	<option value="0">禁用</option> -->
<!-- 		                                </select> -->
<!-- 		                        	</div> -->
<!-- 		                        </div> -->
								<div class="form-group">
		                            <label class="col-sm-3 control-label" style="text-align: left;">发送对象：</label>
		                            <div class="col-sm-7" style="width: 100%">
		                                <div class="widget-box" style="border: 3px solid #ddd;height: 100%;overflow: auto;margin-top: 10px;">
											<input type="hidden" id="departmentId" name="departmentId">
											<ul id="treeDemo" class="ztree" style="overflow: auto;"></ul>
										</div>
		                        	</div>
		                        </div>
								<div class="form-group">
									<div class="col-md-7 col-md-push-3">
										<button type="reset" class="btn btn-default">重置</button>
										<button type="submit" class="btn btn-primary" id="saveb">保存</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
	<script src="${ctx}/js/layui.js"></script> 
	<script type="text/javascript" src="${ctx}/plus/ztree/jquery.ztree.core.js"></script>  
	<script type="text/javascript" src="${ctx}/plus/ztree/jquery.ztree.excheck.js"></script> 
	<script type="text/javascript" src="${ctx}/plus/ztree/jquery.ztree.exedit.js"></script> 
	<script type="text/javascript">
		$(document).ready(function() {
	    	P.init_date_m("add_form","startTime");
	    	P.init_date_m("add_form","endTime");
	    	$.fn.zTree.init($("#treeDemo"), setting);
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 title: {
		                validators: {
		                    notEmpty: {
		                        message: '标题不能为空'
		                    }
		                }
		             },
		        	 score: {
		                validators: {
		                    notEmpty: {
		                        message: '请填写分数'
		                    }
		                }
		             },
		        	 startTime: {
		                validators: {
		                    notEmpty: {
		                        message: '请选择答题开始时间'
		                    }
		                }
		             },
		        	 endTime: {
		                validators: {
		                    notEmpty: {
		                        message: '请选择答题结束时间'
		                    }
		                }
		             }
		        }
		    }).on('success.form.bv', function(e) {
	            e.preventDefault();
	            var $form = $(e.target);
	            var bv = $form.data('bootstrapValidator');
	            var departmentId = $("#departmentId").val();
	            if(departmentId==""){
	            	layer.msg("请选择发送对象");
	            	return false;
	            }else{
	            	 $.post($form.attr('action'), $form.serialize(), function(result) {
	 	            	if(result.code == 1){
	 		                parent.sys.success(result.message,'',function(){
	 		                	P.close_win();
	 		                	parent.search();
	 		                });
	 	            	}else{
	 	            		parent.sys.error(result.message,'',function(){
	 		                });
	 	            	}
	 	            }, 'json');
	            }
	           
	        });
		});
		
		var zTree ;
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
			view : {
	            selectedMulti : true,     //可以多选
	            expandSpeed : 'fast',
			},
			check : {
	            enable : true,
	            chkStyle : "checkbox",    //复选框
	            chkboxType : {
	                "Y" : "s",
	                "N" : "ps"
	            }
	        },
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
	            onCheck : onCheck
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
		
		function onCheck(e, treeId, treeNode) {
		    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		    var nodes = zTree.getCheckedNodes(true);
		    v = "";
		    for (var i = 0, l = nodes.length; i < l; i++) {
		    	v += nodes[i].id + ",";   //获取所选节点的名称
		    }
		    if (v.length > 0)
		        v = v.substring(0, v.length - 1);
		    $("#departmentId").val(v);
		    $('#saveb').removeAttr("disabled");
		}
		
		
    </script>
</body>
</html>