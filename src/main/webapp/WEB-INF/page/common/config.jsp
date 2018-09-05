<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.base.utils.*"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tag/taglib.tld" prefix="perm"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- 项目路径 --%>
<c:set var="picURL" value="<%=ConfigConstants.FILE_SERVER %>"></c:set>
<c:set var="ctx" value="<%=request.getContextPath() %>"></c:set>
<c:set var="_title" value="后台信息管理系统" />
<%-- 图片根路径前缀 --%>
<c:set var="pic" value="${picURL }" />
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>
