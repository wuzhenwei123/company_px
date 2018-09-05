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
							<form id="add_form" action="${ctx}/manageUserLoginLog/addManageUserLoginLog" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">user_id：</label>
									<div class="col-md-7">
										<input type="text" id="user_id" name="user_id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">user_name：</label>
									<div class="col-md-7">
										<input type="text" id="user_name" name="user_name" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">content：</label>
									<div class="col-md-7">
										<input type="text" id="content" name="content" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">login_date：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="login_date" name="login_date" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">leave_date：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="leave_date" name="leave_date" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">login_time：</label>
									<div class="col-md-7">
										<input type="text" id="login_time" name="login_time" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">ip：</label>
									<div class="col-md-7">
										<input type="text" id="ip" name="ip" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">browser：</label>
									<div class="col-md-7">
										<input type="text" id="browser" name="browser" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">os：</label>
									<div class="col-md-7">
										<input type="text" id="os" name="os" class="form-control"/>
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
	    	P.init_date("add_form","login_date");
	    	P.init_date("add_form","leave_date");
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
		        	 user_id: {
		                validators: {
		                    notEmpty: {
		                        message: 'user_id不能为空'
		                    }
		                }
		             },
		        	 user_name: {
		                validators: {
		                    notEmpty: {
		                        message: 'user_name不能为空'
		                    }
		                }
		             },
		        	 content: {
		                validators: {
		                    notEmpty: {
		                        message: 'content不能为空'
		                    }
		                }
		             },
		        	 login_date: {
		                validators: {
		                    notEmpty: {
		                        message: 'login_date不能为空'
		                    }
		                }
		             },
		        	 leave_date: {
		                validators: {
		                    notEmpty: {
		                        message: 'leave_date不能为空'
		                    }
		                }
		             },
		        	 login_time: {
		                validators: {
		                    notEmpty: {
		                        message: 'login_time不能为空'
		                    }
		                }
		             },
		        	 ip: {
		                validators: {
		                    notEmpty: {
		                        message: 'ip不能为空'
		                    }
		                }
		             },
		        	 browser: {
		                validators: {
		                    notEmpty: {
		                        message: 'browser不能为空'
		                    }
		                }
		             },
		        	 os: {
		                validators: {
		                    notEmpty: {
		                        message: 'os不能为空'
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
		                	parent.search();
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