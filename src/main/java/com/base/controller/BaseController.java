package com.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.base.utils.ConfigConstants;
import com.base.utils.MD5;
import com.base.utils.RequestHandler;
import com.base.utils.ResultRsp;
import com.base.utils.SessionName;
import com.base.utils.StandardOutput;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.wx.service.WeiXinService;

/**
 * 
 * @author zhanglib
 *
 */
@Controller
public class BaseController {
	protected static MD5 MD5 = new MD5();
	protected final Logger logger = LogManager.getLogger(getClass());
	final static String[] patterns = { "yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd", "yyyy/MM/dd HH:mm:ss" };// 限定日期的格式字符串数组

	
	@Autowired
	private WeiXinService weiXinService;
	
	public static <T> T requst2Bean(HttpServletRequest request, Class<T> bean) {
		T t = null;
		try {
			t = bean.newInstance();
			Enumeration<?> parameterNames = request.getParameterNames();
			DateConverter convert = new DateConverter();// 写一个日期转换器
			convert.setPatterns(patterns);
			ConvertUtils.register(convert, Date.class);
			while (parameterNames.hasMoreElements()) {
				String name = (String) parameterNames.nextElement();
				String value = RequestHandler.getString(request, name);
				if (value != null && !"".equals(value)) {
					BeanUtils.setProperty(t, name, value);// 使用BeanUtils来设置对象属性的值
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;

	}
	protected void writeSuccessMsg(String message, Object data, HttpServletResponse response) {
		ResultRsp localObject = new ResultRsp("1", message, data);
		StandardOutput.printObject(response, localObject);
	}

	protected void writeJsonObject(Object data, HttpServletResponse response) {
		StandardOutput.printObject(response, data);
	}

	protected void writeObject(Object data, HttpServletResponse response) {
		StandardOutput.printBaseObject(response, data);
	}

	protected void writeErrorMsg(String message, Object data, HttpServletResponse response) {
		ResultRsp localObject = new ResultRsp("-1", message, data);
		StandardOutput.printObject(response, localObject);
	}

	protected void addSession(HttpServletRequest request, String key, Object value) {
		if (key != null) {
			request.getSession().setAttribute(key, value);
		}
	}

	protected void removeSession(HttpServletRequest request, String key) {
		if (key != null) {
			request.getSession().removeAttribute(key);
		}
	}

	protected Object getSession(HttpServletRequest request, String key) {
		if (key != null) {
			return request.getSession().getAttribute(key);
		}
		return null;
	}

	protected Integer getCurrentAdminUserId(HttpServletRequest request) {
		Object obj = getSession(request, SessionName.ADMIN_USER_ID);
		if (obj != null) {
			return Integer.valueOf(obj.toString());
		}
		return null;
	}
	protected ManageAdminUser getCurrentAdminUser(HttpServletRequest request) {
		Object obj = getSession(request, SessionName.ADMIN_USER);
		if (obj != null) {
			ManageAdminUser manageAdminUser = (ManageAdminUser) obj;
			return manageAdminUser;
		}
		return null;
	}
	/**
	 * 是否批发商
	 * @param request
	 * @return
	 */
	protected boolean isPF(HttpServletRequest request) {
		ManageAdminUser currentAdminUser = getCurrentAdminUser(request);
		Integer role_id = currentAdminUser.getRole_id();
		if(role_id.intValue() == Integer.valueOf(ConfigConstants.PF_ROLE_ID)) {//批发商
			return true;
		}
		return false;
	}
	/**
	 * 是否零售商
	 * @param request
	 * @return
	 */
	protected boolean isLS(HttpServletRequest request) {
		ManageAdminUser currentAdminUser = getCurrentAdminUser(request);
		Integer role_id = currentAdminUser.getRole_id();
		if(role_id.intValue() == Integer.valueOf(ConfigConstants.LS_ROLE_ID)) {//零售商
			return true;
		}
		return false;
	}
	/**
	 * 电商代理
	 * @param request
	 * @return
	 */
	protected boolean isDL(HttpServletRequest request) {
		ManageAdminUser currentAdminUser = getCurrentAdminUser(request);
		Integer role_id = currentAdminUser.getRole_id();
		if(role_id.intValue() == Integer.valueOf(ConfigConstants.DL_ROLE_ID)) {//电商代理
			return true;
		}
		return false;
	}
	/**
	 * 是否客服专员
	 * @param request
	 * @return
	 */
	protected boolean isKF(HttpServletRequest request) {
		ManageAdminUser currentAdminUser = getCurrentAdminUser(request);
		Integer role_id = currentAdminUser.getRole_id();
		if(role_id.intValue() == Integer.valueOf(ConfigConstants.KF_ROLE_ID)) {//电商代理
			return true;
		}
		return false;
	}
	/**
	 * 是否拓展业务员
	 * @param request
	 * @return
	 */
	protected boolean isDB(HttpServletRequest request) {
		ManageAdminUser currentAdminUser = getCurrentAdminUser(request);
		Integer role_id = currentAdminUser.getRole_id();
		if(role_id.intValue() == Integer.valueOf(ConfigConstants.DB_ROLE_ID)) {//电商代理
			return true;
		}
		return false;
	}
	protected String getCurrentAdminUserName(HttpServletRequest request) {
		Object obj = getSession(request, SessionName.ADMIN_USER_NAME);
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	protected void web_redirect(HttpServletResponse response, String url) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
		builder.append("window.location.href=\"");
		builder.append(url);
		builder.append("\"</script>");
		PrintWriter out = response.getWriter();
		out.print(builder.toString());
		out.close();
	}

	protected boolean isLogin(HttpServletRequest request) {
		Integer currentAdminUserId = getCurrentAdminUserId(request);
		if (currentAdminUserId == null)
			return false;
		else
			return true;
	}
	
	/**
	 * 微信分享获取的参数
	 * @param request
	 */
	protected void getJsticket(HttpServletRequest request){
//		String   url  = request.getScheme()+"://"; //请求协议 http 或 https  
//		url+=request.getHeader("host");  // 请求服务器  
		String url = ConfigConstants.URL_PATH;
		url+=request.getRequestURI();     // 工程名    
		if(request.getQueryString()!=null) //判断请求参数是否为空
		url+="?"+request.getQueryString();   // 参数 
		System.out.println("url================="+url);
		try {
			Map<String,String> map = weiXinService.getJsticket(ConfigConstants.APPID, ConfigConstants.APPSECRET, url);
			request.setAttribute("noncestr", map.get("noncestr"));
			request.setAttribute("timestamp", map.get("timestamp"));
			request.setAttribute("signature", map.get("signature"));
			request.setAttribute("appid", ConfigConstants.APPID);
			request.setAttribute("server_href", ConfigConstants.URL_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        } 
    }
	
	public String getMoney(Long money){
		String totalFeeStr1 = null;
		if(money!=null&&money>0){
			if(String.valueOf(money).length()>2){
				Long fen = money%100;
				if(fen>0&&fen<10){
					totalFeeStr1 = money/100 + ".0" + fen;
				}else if(fen>=10){
					totalFeeStr1 = money/100 + "." + fen;
				}else{
					totalFeeStr1 = money/100 + ".00";
				}
			}else if(String.valueOf(money).length()==1){
				totalFeeStr1 = "0.0"+money;
			}else{
				totalFeeStr1 = "0."+money;
			}
		}else{
			totalFeeStr1 = "0.00";
		}
		return totalFeeStr1;
	}
}
