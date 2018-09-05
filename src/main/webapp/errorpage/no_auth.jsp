<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>${_title }</title>
<link href="favicon1.ico" rel="icon" type="image/x-icon" />
<link rel="stylesheet" href="${ctx }/css/lib/bootstrap/bootstrap.css" />
<link rel="stylesheet"
	href="${ctx }/css/lib/perfectscrollbar/perfect-scrollbar.css" />
<link rel="stylesheet"
	href="${ctx }/css/lib/font-awesome/font-awesome.min.css">
<link rel="stylesheet" href="${ctx }/css/lib/ionicons/ionicons.min.css">
<link rel="stylesheet" href="${ctx }/css/styles.css" id="theme-css" />
</head>
<body>
	<div class="container err-container">
		<div class="row">
			<div class="col-md-12">
				<div class="text-center">
					<i class="fa fa-warning fa-10x text-danger"></i>
					<h1>无操作权限</h1>
				</div>
			</div>
		</div>
	</div>
</body>
</html>