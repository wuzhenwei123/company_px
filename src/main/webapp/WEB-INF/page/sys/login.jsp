<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>登录-${_title }</title>
	<link href="favicon1.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" href="${ctx }/css/lib/bootstrap/bootstrap.css" />
	<link rel="stylesheet" href="${ctx }/css/lib/perfectscrollbar/perfect-scrollbar.css" />
	<link rel="stylesheet" href="${ctx }/css/lib/font-awesome/font-awesome.min.css">
	<link rel="stylesheet" href="${ctx }/css/lib/ionicons/ionicons.min.css">
	<link rel="stylesheet" href="${ctx }/css/styles.css" id="theme-css" />
</head>
<body style="background:url(${ctx}/images/pattern_2x.jpg);">
<header>
	<nav class="navbar navbar-default navbar-transparent">
		<div class="container">
			<div class="navbar-header">
<!-- 				<button class="navbar-toggle" data-target="#top-navbar" data-toggle="collapse" type="button"> -->
<!-- 					<span class="sr-only">Toggle navigation</span> -->
<!-- 					<i class="ion ion-ios7-gear-outline"></i> -->
<!-- 				</button> -->
<!-- 				<a class="navbar-brand logo" href="javascript:void(0);"> -->
<!-- 					<img src="${ctx }/images/logo.png" alt="系统LOGO" width="175"> -->
<!-- 				</a> -->
			</div>
<!-- 			<div class="collapse navbar-collapse" id="top-navbar"> -->
<!-- 				<ul class="nav navbar-nav navbar-right"> </ul> -->
<!-- 			</div> -->
		</div>
	</nav>
</header>
<div class="container form-container">
	<div class="row">
		<div class="col-xs-12 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4">
			<div class="panel panel-default panel-more-shadow">
				<div class="panel-body">
					<div class="panel-desc text-center">${_title }</div>
					<hr>
					<form role="form" action="${ctx }/manageAdminUser/login" method="post">
						<div class="form-group">
							<input type="text" class="form-control input-lg" value="admin" id="adminName" name="adminName" placeholder="请输入用户名">
						</div>
						<div class="form-group">
							<input type="password" class="form-control input-lg" value="mv_123" id="passwd" name="passwd" placeholder="请输入密码">
						</div>
						<button type="submit" class="btn btn-lg btn-primary btn-block">登录</button>
					</form>
				</div>
				<div class="panel-footer text-center" style="color: red;">
					<p><c:if test="${not empty error }"><i class="fa fa-info-circle"></i> ${error }</c:if></p>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx }/js/lib/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${ctx }/js/lib/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx }/js/lib/slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${ctx }/js/lib/tabdrop/bootstrap-tabdrop.js" type="text/javascript"></script>
<script src="${ctx }/js/scripts.min.js" type="text/javascript"></script>
</body>
</html>