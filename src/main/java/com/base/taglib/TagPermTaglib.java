package com.base.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.base.utils.SessionName;
import com.sys.adminRoleMethod.model.AdminRoleMethod;

public class TagPermTaglib extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	private String permPath;
	
	/** 操作权限BEN **/
//	private static AdminRoleMethodService adminRoleMethodService = (AdminRoleMethodService) ApplicationContextHolder.getBean("adminRoleMethodService");
	
	public String getPermPath() {
		return permPath;
	}

	public void setPermPath(String permPath) {
		this.permPath = permPath;
	}

	public int doEndTag() throws JspException {
		int result = SKIP_BODY;
		BodyContent bodyContent = this.getBodyContent();
		// 获得标签体文本
		String tagBody = bodyContent.getString();
		try {
			HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
			List<AdminRoleMethod> mlist = (List<AdminRoleMethod>)request.getSession().getAttribute(SessionName.USER_ROLE_LIST);
			for (AdminRoleMethod adminRoleMethod : mlist) {
				if(adminRoleMethod.getActionPath()!=null && adminRoleMethod.getActionPath().equals(permPath)){
					JspWriter out = this.bodyContent.getEnclosingWriter();
					out.print(tagBody);
					break;
				}
			}
		} catch (IOException e) {
			throw new JspException(e);
		}
		return result;
	}

	public int doStartTag() throws JspException {
		// SKIP_BODY, EVAL_BODY_INCLUDE, EVAL_BODY_BUFFERED
		int result = EVAL_BODY_BUFFERED;
		return result;
	}

}