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
							<form id="add_form" action="${ctx}/cQuestion/addCQuestion" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">题干：</label>
									<div class="col-md-7">
										<textarea rows="4" id="content" name="content" class="form-control"></textarea>
									</div>
								</div>
								<input type="hidden" id="paperId" name="paperId" value="${paperId}">
								<div class="form-group">
									<label class="col-md-3 control-label">类型：</label>
									<div class="col-md-7">
										<select class="form-control" id="style" name="style" onchange="hideOption(this.value)">
											<option value="1">单择题</option>
											<option value="2">多选题</option>
											<option value="3">问答题</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">分值：</label>
									<div class="col-md-7">
										<input type="text" id="score" name="score" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">正确答案：</label>
									<div class="col-md-7">
										<input type="text" id="rightAnswer" name="rightAnswer" class="form-control"/>
									</div>
								</div>
								<div id="xx">
								
								
								<div class="form-group">
									<label class="col-md-3 control-label">选择A：</label>
									<div class="col-md-7">
										<input type="text" id="optionA" name="optionA" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选择B：</label>
									<div class="col-md-7">
										<input type="text" id="optionB" name="optionB" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选择C：</label>
									<div class="col-md-7">
										<input type="text" id="optionC" name="optionC" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选择D：</label>
									<div class="col-md-7">
										<input type="text" id="optionD" name="optionD" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选择E：</label>
									<div class="col-md-7">
										<input type="text" id="optionE" name="optionE" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选择F：</label>
									<div class="col-md-7">
										<input type="text" id="optionF" name="optionF" class="form-control"/>
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
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 content: {
		                validators: {
		                    notEmpty: {
		                        message: '题干不能为空'
		                    }
		                }
		             },
		        	 score: {
		                validators: {
		                    notEmpty: {
		                        message: '分值不能为空'
		                    }
		                }
		             },
		        	 rightAnswer: {
		                validators: {
		                    notEmpty: {
		                        message: '正确答案不能为空'
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
		
		function hideOption(v){
			if(v！="3"){
				$("#xx").show();
			}else{
				$("#xx").hide();
			}
		}
    </script>
</body>
</html>