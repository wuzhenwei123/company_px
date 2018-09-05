package com.base.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.base.perm.Permission;
import com.base.utils.CookieUtil;
import com.base.utils.ResultRsp;
import com.base.utils.SessionName;
import com.base.utils.StandardOutput;
import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageUserLoginLog.service.ManageUserLoginLogService;
import com.sys.manageUserOpLog.service.ManageUserOpLogService;

@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter{
	protected final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private ManageUserOpLogService manageUserOpLogService = null;//操作记录
	@Autowired
	private ManageUserLoginLogService manageUserLoginLogService = null;//登录记录
	@Inject
	private ThreadPoolTaskExecutor taskExecutor;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		
		// 请求的虚拟目录
		String basePath = request.getContextPath();
		String requestPath = request.getServletPath();
		try {
			List<AdminRoleMethod> roleList = (List<AdminRoleMethod>)session.getAttribute(SessionName.USER_ROLE_LIST);//权限列表
			Object adminUserIdObj = session.getAttribute(SessionName.ADMIN_USER_ID);
			if(adminUserIdObj == null){//未登录
				//在检查cookie
				// 未登录  
                StringBuilder builder = new StringBuilder();  
                builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
                builder.append("window.top.location.href=\"");  
                builder.append(basePath);  
                builder.append("/login\";</script>");  
                write(response, builder.toString());  
                return false;  
			}else{
				init_layout(request);
				
				manageUserOpLogService.recordLog(request);
				manageUserLoginLogService.record(request);
				log.info(">>" + requestPath);
				
				request.setAttribute("requestPath", requestPath);
				setColumnVal(request);
				//已登录
				if(handler instanceof HandlerMethod){
					HandlerMethod method = (HandlerMethod)handler;
					Permission permission = method.getMethodAnnotation(Permission.class);  
					if(permission == null){
						return super.preHandle(request, response, handler);
					}else{
						// 进行权限校验
						String value = permission.value();//可以有多个用逗号隔开
						
						for (AdminRoleMethod adminRoleMethod : roleList) {
							String actionPath = adminRoleMethod.getActionPath();
							if(actionPath!=null && value !=null){
								if(value.contains(actionPath)){
									return true;
								}
							}
						}
						web_redirect(response,basePath+"/errorpage/no_auth.jsp");
						// 到这里应该没有权限了
						return false;
					}
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	private void init_layout(HttpServletRequest request){
		Object session_sysLayout = request.getSession().getAttribute(SessionName.SYS_LAYOUT);
		if(session_sysLayout==null){
			String cookie_sysLayout = CookieUtil.getCookie(request, SessionName.SYS_LAYOUT);
			if(StringUtils.isBlank(cookie_sysLayout)){
				cookie_sysLayout = "container-fluid";
			}
			request.getSession().setAttribute(SessionName.SYS_LAYOUT, cookie_sysLayout);
		}
	}
	private void setColumnVal(HttpServletRequest request){
		String requestPath = request.getServletPath();
		Integer pid = null;
		Integer cid = null;
		List<ManageAdminRoleColumn> parentColumnList = (List<ManageAdminRoleColumn>) request.getSession().getAttribute(SessionName.SYSTEM_COLUMN_LIST);
		if(parentColumnList!=null&&parentColumnList.size()>0){
			END:
			for (ManageAdminRoleColumn adminRoleColumn : parentColumnList) {
				Integer parentColumnID = adminRoleColumn.getParentColumnID();
				if(parentColumnID != null && parentColumnID == -1){
					List<ManageAdminRoleColumn> childList = adminRoleColumn.getChildList();
					if(childList!=null ){
						for (ManageAdminRoleColumn manageAdminRoleColumn : childList) {
							if(manageAdminRoleColumn.getColumnUrl().equals(requestPath)){
								pid = manageAdminRoleColumn.getParentColumnID();
								cid = manageAdminRoleColumn.getColumnId();
								break END;
							}
						}
					}
				}
			}
		}
		
		request.setAttribute("pid", pid);
		request.setAttribute("cid", cid);
	}
	private void write(HttpServletResponse response,String msg) throws IOException{
		PrintWriter out = response.getWriter();  
        out.print(msg);  
        out.close();
	}
	
	protected void writeErrorMsg(String message, Object data,
			HttpServletResponse paramHttpServletResponse) {
		ResultRsp localObject = new ResultRsp("-1", message, data);
		StandardOutput.printObject(paramHttpServletResponse, localObject);
	}
	private void web_redirect(HttpServletResponse response,String url) throws IOException{
		StringBuilder builder = new StringBuilder();  
        builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
        builder.append("window.location.href=\"");  
        builder.append(url);  
        builder.append("\"</script>");  
        write(response,builder.toString());  
	}
}
