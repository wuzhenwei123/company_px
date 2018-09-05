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
	<title>${_title }</title>
	<%@ include file="/WEB-INF/page/common/css.jsp" %>
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
										<div class="col-md-12 page-title">
											<h4>账号设置</h4>
											<hr>
										</div>
									</div>
								</div>
								<div class="container-fluid container-padded">
								<div class="row">
								<div class="col-md-12">
								<div class="panel panel-plain">
								<div class="panel-body">
									<ul class="nav nav-tabs tab-drop">
									<li class="active"><a href="#profile-settings" data-toggle="tab"><i class="fa fa-user fa-fw"></i> 账号设置</a></li>
									<li><a href="#change-password" data-toggle="tab"><i class="fa fa-edit fa-fw"></i> 密码设置</a></li>
<!-- 									<li onclick="showPay()"><a href="#change-payqrcode" data-toggle="tab"><i class="fa fa-edit fa-fw"></i> 收款二维码设置</a></li> -->
									</ul>
								<div class="panel panel-default m-bot0">
									<div class="panel-body tab-content">
										<div class="tab-pane active" id="profile-settings">
											<form id="user_info_form" action="${ctx}/manageAdminUser/updateManageAdminUserInfo" method="post" class="form-horizontal">
												<div class="form-group">
												<label class="col-md-3 control-label">头像</label>
												<div class="col-md-7">
													<div class="fileinput fileinput-new" data-provides="fileinput">
														<div class="fileinput-preview thumbnail" data-trigger="fileinput">
															<img id="img_view" src="${manageadminuser.headImg }" style="width: 100px;height: 100px;">
														</div>
														<div>
															<span id="picker">选择图片</span>
														</div>
														<input type="hidden" id="headImg" name="headImg" value="${manageadminuser.headImg }">
													</div>
												</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">登录账号</label>
													<div class="col-md-7">
														<input type="text" value="${manageadminuser.adminName }" class="form-control" disabled= "disabled" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">昵称</label>
													<div class="col-md-7">
														<input type="text" name="nickName" id="nickName" value="${manageadminuser.nickName }" class="form-control" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">真实姓名</label>
													<div class="col-md-7">
														<input type="text" name="realName" id="realName" value="${manageadminuser.realName }" class="form-control" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">电话</label>
													<div class="col-md-7">
													<input type="text" name="phone" value="${manageadminuser.phone }" class="form-control" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">性别</label>
													<div class="col-md-7">
														<div class="btn-group" data-toggle="buttons">
															<label class="btn btn-default <c:if test="${manageadminuser.sex == 1 }">active</c:if>" >
																<input name="sex" value="1" type="radio">男
															</label>
															<label class="btn btn-default <c:if test="${manageadminuser.sex == 0 }">active</c:if>">
																<input name="sex" value="0" type="radio"> 女
															</label>
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="col-md-7 col-md-push-3">
														<button type="submit" class="btn btn-primary">保存</button>
													</div>
												</div>
											</form>
										</div>
									<div class="tab-pane" id="change-password">
										<form id="pwd_form" action="${ctx}/manageAdminUser/updateManageAdminUserPwd" class="form-horizontal">
											<div class="form-group">
												<label class="col-md-3 control-label">当前密码</label>
												<div class="col-md-7">
													<input type="password" name="old_password" class="form-control">
												</div>
											</div>
											<hr>
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
													<button type="submit" class="btn btn-primary">保存</button>
												</div>
											</div>
										</form>
									</div>
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
		$(document).ready(function() {
			upload('${ctx}','picker',callback,'','sys_user_head','pic');//上传插件
			
		    $('#user_info_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	nickName: {
		                validators: {
		                    notEmpty: {
		                        message: '昵称不能为空'
		                    }
		                }
		            }
		        }
		    }).on('success.form.bv', function(e) {
	            e.preventDefault();
	            var $form = $(e.target);
	            var bv = $form.data('bootstrapValidator');
	            $.post($form.attr('action'), $form.serialize(), function(result) {
	                sys.success(result.message,'',function(){
	                	location.reload();
	                });
	            }, 'json');
	        });
		    $('#pwd_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	old_password: {
		                validators: {
		                    notEmpty: {
		                        message: '当前密码不能为空'
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
	                    	},
	                    	different: {
	                    		field: 'old_password',
	                    		message: '不能和当前密码相同'
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
		                    },
	                    	different: {
	                    		field: 'old_password',
	                    		message: '不能和当前密码相同'
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
		                sys.success(result.message,'',function(){
		                	$("#pwd_form")[0].reset();
		                });
	            	}else{
		                sys.error(result.message,'',function(){
		                	$("#pwd_form")[0].reset();
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
		function callback1(response){
			if(response.code == '1'){
				var fileList = response.list;
				if(fileList.length == 1){
					var newFileName = fileList[0].filePath;
					var headimg = "${pic}"+newFileName;
					$("#img_pay_qrcode").attr("src",headimg);
					$("#pay_qrcode").val("${pic}"+newFileName);
					$.post("<c:url value='/manageAdminUser/updateManageAdminPayqrcode'/>",
			       	{
						pay_qrcode : "${pic}"+newFileName,
						 _t:Math.random()},
				       	function(data){
				        	var result = eval('('+data+')'); 
				            if (result.code == '1') {
				            	layer.msg('上传成功');
				            } else {
				            	 layer.msg(result.m);
				            }
			       });
				}
			}else{
				layer.msg("上传异常");
			}
		}
		function showPay(){
			upload('${ctx}','picker1',callback1,'','sys_user_head','pic');//上传插件
		}
    </script>
</body>
</html>