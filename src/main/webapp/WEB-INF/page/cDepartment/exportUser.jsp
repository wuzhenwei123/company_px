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
	 <link href="${ctx }/js/lib/webuploader/webuploader.css" rel="stylesheet">
	<style type="text/css">
		body {
			background: #ffffff none repeat scroll 0 0!important;
		  	width: 97%!important;
		  	margin-left:5px!important;
		}
		.div_allinline  
		{  
		    clear:both;  
		    vertical-align: middle;
		 }  
		  
		.subdiv_allinline  
		{  
		    display:inline-block;  
		    _display:inline;  
		    *display:inline;  
		    vertical-align: middle;
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
							<form id="add_form" action="${ctx}/manageAdminUser/importUser" method="post" class="form-horizontal">
								<div class="form-group">
									<div class="col-md-12 div_allinline">
										选择模板：<div id="picker" class="subdiv_allinline">选择模板</div>
										<div class="subdiv_allinline" style="border-radius:3px;width: 86px;height: 40px;color: #fff;float: right;top: 0px;text-align: center;" class="btn" onclick="downloadTemplete()"><div class="webuploader-pick">下载模板</div></div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">提示信息：</label>
									<div class="col-md-7">
										<textarea id="msg" readonly="readonly" style="width: 99%;height: 280px;color: red;resize: none;"></textarea>
									</div>
								</div>
								<input type="hidden" id="filePath" name="filePath">
								<div class="form-group">
									<div class="col-md-7 col-md-push-3">
										<button type="submit" class="btn btn-primary" id="submitButton" onclick="beforeAdd()">导入</button>
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
	<script type="text/javascript" src="<c:url value='/js/jquery.form2.96.js'/>"></script>
	<script type="text/javascript">
	 $(document).ready(function() {
	    	upload('${ctx}','picker',callback,'excel','sys_user_head','');
	 });
	 function callback(response){
	    	if(response.code == '1'){
				var fileList = response.list;
				if(fileList.length == 1){
					var newFileName = fileList[0].filePath;
					var fileName = fileList[0].fileName;
					$("#filePath").val(newFileName);
					$("#msg").val("上传成功："+fileName);
				}
			}else{
				layer.msg("上传失败");
			}
	    }
	 /** 下载模版 */
	 function downloadTemplete() {
	 	window.location.href="${ctx}/cDepartment/downloadExcel";
	 }
	 
	 /** 使用Jquery提交导入 */
	 function beforeAdd() {
	 	var fileName = $("#filePath").val();
	 	if (fileName.length < 1) {
	 		return $("#msg").val("请选择要导入的文件。");
	 	}
	 	$("#submitButton").attr("disabled",true);
	 	$("#msg").val("正在导入，请稍候...");
	 	$("#add_form").ajaxSubmit({
	         type: "post",
	         success: function(data) {
	         	var json = eval("("+data+")");
	        		$("#submitButton").attr("disabled",false);
	        		$("#msg").val(json.message);
	        		if (json.code == '1') {
	                	parent.search();
	        		}
	         }
	     });
	 }
    </script>
</body>
</html>