<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
   <%@ include file="/WEB-INF/page/common/config.jsp" %>
<header>
	<nav class="navbar navbar-default navbar-transparent">
		<div class="${sys_layout }" id="nav-container">
			<div class="navbar-header">
				<button class="navbar-toggle navbar-toggle-settings" data-target="#top-navbar" data-toggle="collapse" type="button">
					<c:if test="${not empty admin_user.headImg }">
                   	<img alt="image" class="user-settings-pic" src="${admin_user.headImg}" />
		    		</c:if>
		    		<c:if test="${empty admin_user.headImg }">
                   	<img alt="image" class="user-settings-pic" src="${ctx }/images/default_photo.png" />
		    		</c:if>
				</button>
				<a class="navbar-brand logo" href="javascript:void(0);">
					<img src="${ctx }/images/logo.png" alt="系统LOGO" width="175">
				</a>
				<div class="navbar-side-menu-toggle">
					<button class="toggle-btn" type="button">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
				</div>
			</div>
			<div class="collapse navbar-collapse" id="top-navbar">
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${sys_layout == 'container' }">
					<li><a href="javascript:layout('${ctx }',2);" style="color: #ffffff !important;"><i class="ion-arrow-expand"></i>切换</a></li>
					</c:if>
					<c:if test="${sys_layout == 'container-fluid' }">
					<li><a href="javascript:layout('${ctx }',1);" style="color: #ffffff !important;"><i class="ion-arrow-shrink"></i>切换</a></li>
					</c:if>
					<li><a href="${ctx }/main" style="color: #ffffff !important;"><i class="fa fa-home"></i>首页</a></li>
<!-- 					<li><a href="app_support.html" target="_blank"><i class="fa fa-support"></i> <span class="notification-title">搜索</span></a></li> -->
<!-- 					<li class="dropdown"> -->
<!-- 						<a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-bell"></i> <span class="notification-title">个人消息</span><span class="badge badge-danger notification-badge">6</span></a> -->
<!-- 						<div class="dropdown-menu notification-menu"> -->
<!-- 						<div class="panel panel-plain m-bot0"> -->
<!-- 						<div class="list-group"> -->
<!-- 						<a href="page_notifications.html" class="list-group-item"> -->
<!-- 						<i class="fa fa-ban text-danger"></i> Nikita Williams rejected an Action Step from you -->
<!-- 						</a> -->
<!-- 						<a href="page_notifications.html" class="list-group-item"> -->
<!-- 						<i class="fa fa-check-circle text-success"></i> You accepted an Action Step from Scott Marshall -->
<!-- 						</a> -->
<!-- 						</div> -->
<!-- 						<div class="panel-footer p-0"> -->
<!-- 						<a href="page_notifications.html" class="btn btn-link btn-block m-bot0">View all</a> -->
<!-- 						</div> -->
<!-- 						</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#"">
							<c:if test="${not empty admin_user.headImg }">
	                    	<img alt="image" class="user-settings-pic" src="${admin_user.headImg}" />
				    		</c:if>
				    		<c:if test="${empty admin_user.headImg }">
	                    	<img alt="image" class="user-settings-pic" src="${ctx }/images/default_photo.png" />
				    		</c:if>
							<c:if test="${not empty admin_user.nickName }">
	                        	${admin_user.nickName}
	                        </c:if>
	                        <c:if test="${empty admin_user.nickName }">
	                        	${admin_user_name }
	                        </c:if>
							<i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
						<li><a href="${ctx }/manageAdminUser/toUpdateInfo">账号设置</a></li>
						<li class="divider"></li>
						<li><a href="${ctx }/loginout">退出</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</header>