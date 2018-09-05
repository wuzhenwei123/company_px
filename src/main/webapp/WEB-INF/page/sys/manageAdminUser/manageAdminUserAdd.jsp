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
							<form id="add_form" action="${ctx}/manageAdminUser/addManageAdminUser" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">登录账号：</label>
									<div class="col-md-7">
										<input type="text" id="adminName" name="adminName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">昵称：</label>
									<div class="col-md-7">
										<input type="text" id="nickName" name="nickName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">密码：</label>
									<div class="col-md-7">
										<input type="password" id="passwd" name="passwd" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">真实姓名：</label>
									<div class="col-md-7">
										<input type="text" id="realName" name="realName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">手机：</label>
									<div class="col-md-7">
										<input type="text" id="mobile" name="mobile" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">电话：</label>
									<div class="col-md-7">
										<input type="text" id="phone" name="phone" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label">状态：</label>
		                            <div class="col-sm-7">
		                                <select id="state" name="state" class="form-control">
		                                 	<option value="1">正常</option>
		                                 	<option value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
								<div class="form-group">
									<label class="col-md-3 control-label">头像：</label>
									<div class="col-md-7">
										<div class="fileinput fileinput-new" data-provides="fileinput">
											<div class="fileinput-preview thumbnail" data-trigger="fileinput">
												<img id="img_view" src="${ctx }/images/default_photo.png" style="width: 100px;height: 100px;">
											</div>
											<div>
												<span id="picker">选择图片</span>
											</div>
											<input type="hidden" id="headImg" name="headImg" class="form-control" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">角色：</label>
									<div class="col-md-7">
										<select id="role_id" name="role_id" class="form-control">
		                             	<c:forEach items="${roleList }" var="role">
		                             		<option value="${role.roleId }">${role.roleName }</option>
		                             	</c:forEach>
		                             	</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">性别</label>
									<div class="col-md-7">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default active" >
												<input name="sex" value="1" type="radio">男
											</label>
											<label class="btn btn-default">
												<input name="sex" value="0" type="radio"> 女
											</label>
										</div>
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
			upload('${ctx}','picker',callback,'','sys_user_head','pic');//上传插件
			$('#add_form').bootstrapValidator({
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
		        	 adminName: {
		                validators: {
		                    notEmpty: {
		                        message: '登录账号不能为空'
		                    },
		                    remote: {  
		                         url: '${ctx}/manageAdminUser/verifyAdminName',//验证地址
		                         message: '登录账号已存在',//提示消息
		                         delay :  800,
		                         type: 'POST'//请求方式
		                     },
		                }
		             },
		             realName: {
		                validators: {
		                    notEmpty: {
		                        message: '真实姓名不能为空'
		                    }
		                }
		             },
		        	 passwd: {
		                validators: {
		                    notEmpty: {
		                        message: '密码不能为空'
		                    }
		                }
		             },
		             IDNumber: {
		                validators: {
		                    notEmpty: {
		                        message: '身份证号不能为空'
		                    }
		                }
		             },
		             cardNumber: {
		                validators: {
		                    notEmpty: {
		                        message: '结算卡号不能为空'
		                    }
		                }
		             },
		             bankName: {
		                validators: {
		                    notEmpty: {
		                        message: '开户行名称不能为空'
		                    }
		                }
		             },
		             bankNo: {
		                validators: {
		                    notEmpty: {
		                        message: '开户行行号不能为空'
		                    }
		                }
		             },
		        	 lastLogin: {
		                validators: {
		                    notEmpty: {
		                        message: 'lastLogin不能为空'
		                    }
		                }
		             },
		        	 loginIP: {
		                validators: {
		                    notEmpty: {
		                        message: 'loginIP不能为空'
		                    }
		                }
		             },
		        	 pwdModifyTime: {
		                validators: {
		                    notEmpty: {
		                        message: 'pwdModifyTime不能为空'
		                    }
		                }
		             },
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: 'state不能为空'
		                    }
		                }
		             },
		        	 createTime: {
		                validators: {
		                    notEmpty: {
		                        message: 'createTime不能为空'
		                    }
		                }
		             },
		        	 createAdminName: {
		                validators: {
		                    notEmpty: {
		                        message: 'createAdminName不能为空'
		                    }
		                }
		             },
		        	 createrId: {
		                validators: {
		                    notEmpty: {
		                        message: 'createrId不能为空'
		                    }
		                }
		             },
		        	 headImg: {
		                validators: {
		                    notEmpty: {
		                        message: '头像不能为空'
		                    }
		                }
		             },
		        	 role_id: {
		                validators: {
		                    notEmpty: {
		                        message: 'role_id不能为空'
		                    }
		                }
		             },
		        	 sex: {
		                validators: {
		                    notEmpty: {
		                        message: 'sex不能为空'
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