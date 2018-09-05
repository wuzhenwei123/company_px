package com.base.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CookieUtil {
	/**
	 * 
	 * @time : 2016年11月4日 下午5:17:39
	 * @param key
	 * @param value
	 * @param expriy 秒为单位 (-1为内存cookie，0为删除)
	 * @param domain
	 * @param response
	 */
	public static void setCookie(String key, String value, int expriy, String domain, HttpServletResponse response) {
		try {
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(expriy);
			cookie.setPath("/");
			if(domain!=null)
				cookie.setDomain(domain);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getCookie(HttpServletRequest request, String key) {
		String cookieValue = null;
		try {
			// 根据cookieName取cookieValue
			Cookie cookies[] = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (key.equals(cookies[i].getName())) {
						cookieValue = cookies[i].getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cookieValue;

	}

	public static void clearCookie(HttpServletResponse response, String key, String domain) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

}
