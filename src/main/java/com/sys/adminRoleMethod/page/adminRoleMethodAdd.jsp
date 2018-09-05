<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="sys.title" /></title>
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/css/animate.min.css" rel="stylesheet">
    <link href="${ctx}/js/plugins/My97DatePicker/skin/WdatePicker.css" rel="stylesheet">
    <link href="${ctx}/css/style.min.css" rel="stylesheet">
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<div class="panel">
    		<form class="form-horizontal m-t" id="commentForm" method="post">
    		<div class="panel-body">
			    <div class="row">
			    	<div class="col-sm-12">
                		<div class="form-group">
                             <label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.id" />：</label>
                             <div class="col-sm-8">
                                 <input id="id" name="id" class="form-control" type="text">
                                 <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> <spring:message code="sys.main.required" /></span>
                             </div>
                         </div>
                		<div class="form-group">
                             <label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.roleId" />：</label>
                             <div class="col-sm-8">
                                 <input id="roleId" name="roleId" class="form-control" type="text">
                                 <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> <spring:message code="sys.main.required" /></span>
                             </div>
                         </div>
                		<div class="form-group">
                             <label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.mid" />：</label>
                             <div class="col-sm-8">
                                 <input id="mid" name="mid" class="form-control" type="text">
                                 <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> <spring:message code="sys.main.required" /></span>
                             </div>
                         </div>
                		<div class="form-group">
                             <label class="col-sm-3 control-label"><spring:message code="com.sys.adminRoleMethod.model.AdminRoleMethod.columnId" />：</label>
                             <div class="col-sm-8">
                                 <input id="columnId" name="columnId" class="form-control" type="text">
                                 <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> <spring:message code="sys.main.required" /></span>
                             </div>
                         </div>
			    	</div>
			    </div>
    		</div>
    		<div class="panel-footer text-center">
    			<button type="submit" class="btn btn-primary"><spring:message code="sys.main.save" /></button>
    			<button type="button" onclick="closeWin()" class="btn btn-warning"><spring:message code="sys.main.cancel" /></button>
    		</div>
    		</form>
    	</div>
    </div>
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/bootstrap.min.js"></script>
    <script src="${ctx}/js/content.min.js"></script>
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/js/plugins/validate/messages_${sys_lang}.min.js"></script>
    <script src="${ctx}/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script src="${ctx}/js/common.js"></script>
    <script type="text/javascript">
    	function closeWin(){
    		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);//关闭窗口
    	}
	    $.validator.setDefaults({
	        highlight: function(e) {
	            $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
	        },
	        success: function(e) {
	            e.closest(".form-group").removeClass("has-error").addClass("has-success")
	        },
	        errorElement: "span",
	        errorPlacement: function(e, r) {
	            e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
	        },
	        errorClass: "help-block m-b-none",
	        validClass: "help-block m-b-none"
	    }),
	    $(document).ready(function() {
	        var e = "<i class='fa fa-times-circle'></i> ";
	        $("#commentForm").validate({
	            rules: {
	            	id: "required",
	            	roleId: "required",
	            	mid: "required",
	            	columnId: "required"
	            },
	            submitHandler : function(){
	            	var data = $('#commentForm').serialize();
	            	$.ajax({
	            		url:"${ctx}/adminRoleMethod/addAdminRoleMethod",
	            		dataType:'json',
	            		type:'post',
	            		data:data,
	            		error:function(){
	            			sys.msg('<spring:message code="sys.main.fail" />');
	            		},
	            		success:function(data){
	            			closeWin();
	            			sys.msg('<spring:message code="sys.main.success" />');
	            		}
	            	})
	            	return false;//阻止表单提交
	            }
	        })
	    });
    </script>
</body>
</html>
