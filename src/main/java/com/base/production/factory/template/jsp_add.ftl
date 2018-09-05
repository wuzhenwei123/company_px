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
	<title>$${jl}_title${jld}</title>
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
							<form id="add_form" action="$${jl}ctx${jld}/${domainName}/add${domainName?cap_first}" method="post" class="form-horizontal">
								<#list columns as item>
									<#if item.type == "Date">
								<div class="form-group">
									<label class="col-md-3 control-label">${item.javaName}：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="${item.javaName}" name="${item.javaName}" class="form-control"/>
									</div>
								</div>
									<#elseif item.javaName == "state">
								<div class="form-group">
		                            <label class="col-sm-3 control-label">状态：</label>
		                            <div class="col-sm-7">
		                                <select id="${item.javaName}" name="${item.javaName}" class="form-control">
		                                 	<option value="1">正常</option>
		                                 	<option value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
									<#else>
								<div class="form-group">
									<label class="col-md-3 control-label">${item.javaName}：</label>
									<div class="col-md-7">
										<input type="text" id="${item.javaName}" name="${item.javaName}" class="form-control"/>
									</div>
								</div>
									</#if>
								</#list>
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
		<#list columns as item>
	    	<#if item.type == "Date">
	    	P.init_date("add_form","${item.javaName}");
	    	</#if>
		</#list>
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        <#list columns as item>
		        	 ${item.javaName}: {
		                validators: {
		                    notEmpty: {
		                        message: '${item.javaName}不能为空'
		                    }
		                }
		            <#if (item_index+1) != (columns?size)>
		             },
		            </#if>
		            <#if (item_index+1) == (columns?size)>
		             }
		            </#if>
		        </#list>
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