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
	<header>
		<nav class="navbar navbar-default navbar-transparent">
			<div class="container">
				<div class="navbar-header">
					<button class="navbar-toggle" data-target="#top-navbar"
						data-toggle="collapse" type="button">
						<span class="sr-only">Toggle navigation</span> <i
							class="ion ion-ios7-gear-outline"></i>
					</button>
					<a class="navbar-brand logo" href="${ctx }/">
						<img src="${ctx }/images/logo.png" alt="系统LOGO" width="175">
					</a>
				</div>
			</div>
		</nav>
	</header>
	<div class="container err-container">
		<div class="row">
			<div class="col-md-12">
				<div class="text-center">
					<i class="fa fa-warning fa-10x text-danger"></i>
					<h1>服务器繁忙，请联系系统管理员.</h1>
					<a href="${ctx }/main" class="btn btn-primary"><i class="icon-home"></i>首页</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>