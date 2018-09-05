<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.base.utils.ConfigConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"  %>
<%@page import="org.apache.commons.logging.LogFactory"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="x-ua-compatible" content="ie=7" />
<title>简表导出</title>
</head>
<body style="font-size:12px;">
<%
Object nameObj=request.getAttribute("name");
if(nameObj!=null){
	String name=request.getAttribute("name").toString();
	String path=request.getAttribute("link").toString();
	response.setContentType( " APPLICATION/OCTET-STREAM " );
	response.setHeader("Content-disposition","attachment;   filename="+new String(name.getBytes("UTF-8"),"iso8859-1"));
	FileInputStream  fis=new  FileInputStream(path);
	OutputStream  os=response.getOutputStream();
	boolean flag=true;
	try{
		int byteRead;   
		while(-1!=(byteRead=fis.read())){   
			os.write(byteRead); 
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		os.flush();
		out.clear();
		out=pageContext.pushBody();
		os.close();   
		if(fis!=null){
			fis.close();
		}
	}
}
%>
</body>
</html>