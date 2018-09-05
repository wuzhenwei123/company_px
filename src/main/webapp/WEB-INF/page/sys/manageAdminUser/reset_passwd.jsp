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
							<form id="update_form" action="${ctx}/manageAdminUser/update_passwd" method="post" class="form-horizontal">
								<input id="adminId" name="adminId" value="${manageadminuser.adminId}" class="form-control" type="hidden">
								<div class="form-group">
									<label class="col-md-3 control-label">新密码</label>
									<div class="col-md-7">
										<input type="password" name="new_password_1" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">确认新密码</label>
									<div class="col-md-7">
										<input type="password" name="new_password_2" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-7 col-md-push-3">
										<button type="submit" class="btn btn-primary">更新</button>
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
			upload('${ctx}','picker',callback,'','sys_user_head','pic');//上传插件
			$('#update_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 adminId: {
		                validators: {
		                    notEmpty: {
		                        message: 'adminId不能为空'
		                    }
		                }
		             },
		            new_password_1: {
		                validators: {
		                    notEmpty: {
		                        message: '新密码不能为空'
		                    },
		                    stringLength: {
		                    	min: 6,
		                    	max: 16,
		                    	message: '密码长度6-16位'
	                    	}
		                }
		        	},
		        	new_password_2: {
		                validators: {
		                    notEmpty: {
		                        message: '确认密码不能为空'
		                    },
		                    stringLength: {
		                    	min: 6,
		                    	max: 16,
		                    	message: '密码长度6-16位'
	                    	},
		                    identical: {
		                    	field: 'new_password_1',
		                    	message: '两次密码不一致'
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
		function callback(response){
			if(response.code == '1'){
				var fileList = response.list;
				if(fileList.length == 1){
					var newFileName = fileList[0].filePath;
					var headimg = "${pic}"+newFileName;
					$("#img_view").attr("src",headimg);
					$("#headImg").val("${pic}"+newFileName);
				}
			}else{
				layer.msg("上传异常");
			}
		}
    </script>
</body>
</html>