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
	<link rel="stylesheet" href="${ctx}/plus/layui/css/layui.css">
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
							<form id="add_form" action="${ctx}/cCourse/addCCourse" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">名称：</label>
									<div class="col-md-7">
										<input type="text" id="name" name="name" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">封面地址：</label>
									<div class="col-md-7">
										<table>
											<tbody>
												<tr>
													<td><img alt="" src="${ctx}/images/loading.gif"
														class="headimg" id="exe_img"
														style="display: none;width: 98px;height: 98px;"></td>
												</tr>
												<tr>
													<td>
														<div class="btn btn-inverse btn-mini" id="filePicker"
															style="line-height: 0.428571 !important;">
															<i class="icon-upload-alt"></i> 浏览
														</div>
													</td>
												</tr>

											</tbody>
										</table>
										<input type="hidden" id="imgUrl" name="imgUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									 <div class="col-sm-10" style="float:right;display:inline-block; ">
									
									 上传视频：<div class="layui-progress layui-progress-big" lay-filter="demo" lay-showPercent="true" style="display: inline-block;width: 60%;">
										<div class="layui-progress-bar" lay-percent="0%"></div>
									</div>
									<div class="btn btn-inverse btn-mini" id="filePicker1"style="line-height: 0.428571 !important;">浏览</div>
														
										<input type="hidden" id="videoUrl" name="videoUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label" >状态：</label>
		                            <div class="col-sm-7" >
		                                <select id="state" name="state" class="form-control">
		                                 	<option value="1">正常</option>
		                                 	<option value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
								<div class="form-group">
									<label class="col-md-3 control-label">描述：</label>
									<div class="col-md-7">
										<textarea rows="4" cols="" id="info" name="info" class="form-control"></textarea>
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
	<script src="${ctx}/plus/layui/layui.js"></script> 
	<script type="text/javascript">
		$(document).ready(function() {
			upload('${ctx}','filePicker',uploadCallBack,'','sys_user_head','');
			upload('${ctx}','filePicker1',uploadCallBack1,'video','sys_user_head','');
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 name: {
		                validators: {
		                    notEmpty: {
		                        message: 'name不能为空'
		                    }
		                }
		             },
		        	 imgUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'imgUrl不能为空'
		                    }
		                }
		             },
		        	 videoUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'videoUrl不能为空'
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
		
		function uploadCallBack(response){
			if(response.code == '1'){
				var fileList = response.list;
				if(fileList.length == 1){
					var filePath = fileList[0].filePath
					var fileId = fileList[0].fileId
					$("#exe_img").attr("src","${pic}"+filePath);
					$("#exe_img").show();
					$("#imgUrl").val(filePath);
				}
			}else{
				layer.msg("上传异常");
			}
		}
		function uploadCallBack1(response){
			if(response.code == '1'){
				var fileList = response.list;
				if(fileList.length == 1){
					var filePath = fileList[0].filePath;
					var fileId = fileList[0].fileId;
					var videoFileName = fileList[0].fileName;
					$("#mp4_img").attr("src","${ctx}/images/mp4.jpg");
					$("#mp4_img").show();
					$("#videoUrl").val(filePath);
				}
			}else{
				layer.msg("上传异常");
			}
		}
    </script>
</body>
</html>