<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/page/common/config.jsp"%>
<style>
	.left-side-inner a:focus{
		outline:none;
	}
</style>
<div class="left-side-wrapper">
	<div class="left-side sticky-left-side">
		<div class="left-side-inner">
			<ul class="nav nav-pills nav-stacked custom-nav">
				<c:forEach items="${system_columnList }" var="column">
					<c:set value="" var="p_li"></c:set>
					<c:if test="${pid == column.columnId}">
						<c:set value="nav-active current" var="p_li"></c:set>
					</c:if>
					<li class="menu-list ${p_li }">
						<a href="">
							<i class="${column.columnImg }"></i>
							<span>${column.columnName }</span> 
							<i class="ion ion-ios7-arrow-down pull-right"></i>
						</a>
						<ul class="sub-menu-list">
							<c:forEach items="${column.childList }" var="child">
							<c:set value="" var="c_li"></c:set>
							<c:if test="${cid == child.columnId}">
							<c:set value="active" var="c_li"></c:set>
							</c:if>
							<li class="${c_li }"><a href="${ctx }${child.columnUrl }">${child.columnName }</a></li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>