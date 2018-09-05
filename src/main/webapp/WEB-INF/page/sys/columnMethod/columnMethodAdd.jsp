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
							<form id="add_form" action="${ctx}/columnMethod/addColumnMethod" method="post" class="form-horizontal">
								<input id="columnId" name="columnId" class="form-control" type="hidden" value="${columnId }">
								<div class="form-group">
									<label class="col-md-3 control-label">名称：</label>
									<div class="col-md-7">
										<input type="text" id="zh_name" name="zh_name" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">链接：</label>
									<div class="col-md-7">
										<input type="text" id="actionPath" name="actionPath" class="form-control"/>
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
	    	P.init_date("add_form","createTime");
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 mid: {
		                validators: {
		                    notEmpty: {
		                        message: 'mid不能为空'
		                    }
		                }
		             },
		        	 columnId: {
		                validators: {
		                    notEmpty: {
		                        message: 'columnId不能为空'
		                    }
		                }
		             },
		        	 en_name: {
		                validators: {
		                    notEmpty: {
		                        message: 'en_name不能为空'
		                    }
		                }
		             },
		        	 zh_name: {
		                validators: {
		                    notEmpty: {
		                        message: '名称不能为空'
		                    }
		                }
		             },
		        	 actionPath: {
		                validators: {
		                    notEmpty: {
		                        message: '链接不能为空'
		                    }
		                }
		             },
		        	 createTime: {
		                validators: {
		                    notEmpty: {
		                        message: 'createTime不能为空'
		                    }
		                }
		             }
		        }
		    }).on('success.form.bv', function(e) {
	            e.preventDefault();
	            var $form = $(e.target);
	            var bv = $form.data('bootstrapValidator');
	            $.post($form.attr('action'), $form.serialize(), function(result) {
	            	if(result.code == 1){
		                parent.sys.success(result.message,'',function(){
		                	P.close_win();
		                	parent.init();
		                });
	            	}else{
	            		parent.sys.error(result.message,'',function(){
		                });
	            	}
	            }, 'json');
	        });
		});
    </script>
</body>
</html>