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
							<form id="update_form" action="${ctx}/cQuestion/updateCQuestion" method="post" class="form-horizontal">
								<input type="hidden" id="id" name="id" value="${cQuestion.id}" class="form-control"/>
								<div class="form-group">
									<label class="col-md-3 control-label">题干：</label>
									<div class="col-md-7">
										<textarea rows="4" id="content" name="content" class="form-control">${cQuestion.content}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">类型：</label>
									<div class="col-md-7">
										<select class="form-control" id="style" name="style" onchange="hideOption(this.value)">
											<option value="1" <c:if test="${cQuestion.style== 1}" >selected="selected"</c:if>>单选题</option>
											<option value="2" <c:if test="${cQuestion.style== 2}" >selected="selected"</c:if>>多选题</option>
											<option value="3" <c:if test="${cQuestion.style== 3}" >selected="selected"</c:if>>问答题</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">分值：</label>
									<div class="col-md-7">
										<input type="text" id="score" name="score" value="${cQuestion.score}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">正确答案：</label>
									<div class="col-md-7">
										<input type="text" id="rightAnswer" name="rightAnswer" value="${cQuestion.rightAnswer}" class="form-control"/>
									</div>
								</div>
								<div id="xx">
								<div class="form-group">
									<label class="col-md-3 control-label">选项A：</label>
									<div class="col-md-7">
										<input type="text" id="optionA" name="optionA" value="${cQuestion.optionA}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选项B：</label>
									<div class="col-md-7">
										<input type="text" id="optionB" name="optionB" value="${cQuestion.optionB}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选项C：</label>
									<div class="col-md-7">
										<input type="text" id="optionC" name="optionC" value="${cQuestion.optionC}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选项D：</label>
									<div class="col-md-7">
										<input type="text" id="optionD" name="optionD" value="${cQuestion.optionD}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选项E：</label>
									<div class="col-md-7">
										<input type="text" id="optionE" name="optionE" value="${cQuestion.optionE}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">选项F：</label>
									<div class="col-md-7">
										<input type="text" id="optionF" name="optionF" value="${cQuestion.optionF}" class="form-control"/>
									</div>
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
			hideOption('${cQuestion.style}');
			$('#update_form').bootstrapValidator({
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
			if(v!="3"){
				$("#xx").show();
			}else{
				$("#xx").hide();
			}
		}
    </script>
</body>
</html>