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
						<div class="col-md-12" style="text-align: center;">
							<img alt="" src="${qr_code_url}">
							<div class="form-group">
								<div class="col-md-7 col-md-push-3">
									<button type="submit" class="btn btn-primary" onclick="downLoad(1)">下载（8CM）</button>
									<button type="submit" class="btn btn-primary" onclick="downLoad(2)">下载（12CM）</button>
									<button type="submit" class="btn btn-primary" onclick="downLoad(3)">下载（15CM）</button>
									<button type="submit" class="btn btn-primary" onclick="downLoad(4)">下载（30CM）</button>
									<button type="submit" class="btn btn-primary" onclick="downLoad(5)">下载（50CM）</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
	<script type="text/javascript">
		function downLoad(type){
			window.location.href="${ctx}/manageAdminUser/getpic?adminId=${adminId}&type="+type;
		}
    </script>
</body>
</html>