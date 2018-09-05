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
	</style>
</head>
<body class="dig-body">
	<div class="main-content-wrapper">
		<div class="main-content">
			<section>
				<div class="container-fluid container-padded">
					<div class="row">
						<div class="col-md-12">
							<form id="add_form" action="${ctx}/cDepartment/addNodes" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">机构名称：</label>
									<div class="col-md-7">
										<input type="text" id="name" name="name" class="form-control"/>
									</div>
								</div>
								<input type="hidden" id="parentId" name="parentId" value="${pId}"/>
								<input type="hidden" id="level" name="level" value="${level}"/>
								<div class="form-group">
									<label class="col-md-3 control-label">机构代码：</label>
									<div class="col-md-7">
										<input type="text" id="code" name="code" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-7 col-md-push-3">
										<button type="reset" class="btn btn-default">重置</button>
										<button type="submit" class="btn btn-primary">保存</button>
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
	<script type="text/javascript">
		$(document).ready(function() {
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 id: {
		                validators: {
		                    notEmpty: {
		                        message: 'id不能为空'
		                    }
		                }
		             },
		        	 name: {
		                validators: {
		                    notEmpty: {
		                        message: 'name不能为空'
		                    }
		                }
		             },
		        	 level: {
		                validators: {
		                    notEmpty: {
		                        message: 'level不能为空'
		                    }
		                }
		             },
		        	 parentId: {
		                validators: {
		                    notEmpty: {
		                        message: 'parentId不能为空'
		                    }
		                }
		             },
		        	 code: {
		                validators: {
		                    notEmpty: {
		                        message: 'code不能为空'
		                    }
		                }
		             },
		        	 sortId: {
		                validators: {
		                    notEmpty: {
		                        message: 'sortId不能为空'
		                    }
		                }
		             }
		        }
		    }).on('success.form.bv', function(e) {
	            e.preventDefault();
	            var $form = $(e.target);
	            var bv = $form.data('bootstrapValidator');
	            $.get("${ctx}/cDepartment/checkDepartmentCode?code="+document.getElementById("code").value,function(data){
	            	var json = eval("("+data+")");
	            	if(json.code=="1"){
	            		$.post($form.attr('action'), $form.serialize(), function(result) {
	    	            	var zTree = parent.$.fn.zTree.getZTreeObj("treeDemo");
	    	            	if(result.code == 1){
	    		                parent.sys.success(result.message,'',function(){
	    							zTree.addNodes(parent.treeNode1, {
	    								pId : parent.treeNode1.id,
	    								id : result.rows,
	    								name : document.getElementById("name").value
	    							}, true);
	    		                	P.close_win();
	    		                });
	    	            	}else{
	    	            		parent.sys.error(result.message,'',function(){
	    		                });
	    	            	}
	    	            }, 'json');
	            	}else{
	            		parent.sys.error("机构编号重复，请修改",'',function(){
		                });
	            	}
	            });
	            
	        });
		});
    </script>
</body>
</html>