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
							<form id="update_form" action="${ctx}/manageAdminRole/updateManageAdminRole" method="post" class="form-horizontal">
								<input type="hidden" id="roleId" name="roleId" value="${manageadminrole.roleId}" class="form-control"/>
								<div class="form-group">
									<label class="col-md-3 control-label">角色名称：</label>
									<div class="col-md-7">
										<input type="text" id="roleName" name="roleName" value="${manageadminrole.roleName}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label" style="text-align: left;">状态：</label>
		                            <div class="col-sm-7" style="width: 100%;">
		                                <select id="state" name="state" class="form-control" style="width: 100%;">
		                                 	<option <c:if test="${manageadminrole.state== 1}" >selected="selected"</c:if>  value="1">正常</option> 
		                        			<option <c:if test="${manageadminrole.state== 0}" >selected="selected"</c:if> value="0">禁用</option>
		                                </select>
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
	    	P.init_date("update_form","createTime");
			$('#update_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 roleId: {
		                validators: {
		                    notEmpty: {
		                        message: 'roleId不能为空'
		                    }
		                }
		             },
		        	 roleName: {
		                validators: {
		                    notEmpty: {
		                        message: 'roleName不能为空'
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
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: 'state不能为空'
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