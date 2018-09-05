<%@page import="com.base.utils.GetOpenIdUntil"%>
<%@page import="com.base.utils.RequestHandler"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String weixinCode = RequestHandler.getString(request, "code");
	String state = RequestHandler.getString(request, "state");
	if (null == weixinCode || "".equals(weixinCode.trim())) {
		String backUrl = GetOpenIdUntil.getAuthorizeCode(state);
		response.sendRedirect(backUrl);
		return;
	}
%>
