package com.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestHandler {

	public static int PAGE_NO = 1;
	public static int PAGE_SIZE = 10;

	public static Integer getPageNo(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		int i = 1;
		if (str != null) {
			i = Integer.parseInt(str);
			i = (i < 1) ? 1 : i;
		}
		return Integer.valueOf(i);
	}

	public static Integer getPageSize(HttpServletRequest paramHttpServletRequest, String paramString) {
		Integer localInteger = getInteger(paramHttpServletRequest, paramString);
		int i = (localInteger == null) ? PAGE_SIZE : localInteger.intValue();
		return Integer.valueOf(i);
	}

	public static int getFromByPage(int paramInt1, int paramInt2) {
		if (paramInt2 == 0)
			paramInt2 = PAGE_SIZE;
		int i = 0;
		paramInt1 = (paramInt1 < 1) ? 1 : paramInt1;
		i = (paramInt1 - 1) * paramInt2;
		return (i++);
	}

	public static int getToByPage(int paramInt1, int paramInt2) {
		int i = 0;
		i = paramInt1 + paramInt2;
		return (i--);
	}

	public static int getIbatisFromByPage(String paramString, int paramInt) {
		int i = 0;
		if (paramString != null) {
			int j = Integer.parseInt(paramString);
			j = (j < 1) ? 1 : j;
			i = (j - 1) * paramInt;
		}
		return i;
	}

	public static String getString(HttpServletRequest paramHttpServletRequest, String paramString) {
		return getParameter(paramHttpServletRequest, paramString);
	}

	public static Integer getInteger(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		Integer localInteger = strToInteger(str);
		if (localInteger == null)
			localInteger = boolToInteger(str);
		return localInteger;
	}

	public static Double getDouble(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		Double localDouble = strToDouble(str);
		if (localDouble == null)
			localDouble = boolToDouble(str);
		return localDouble;
	}

	public static Float getFloat(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		return strToFloat(str);
	}

	public static Boolean getBoolean(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		return strToBool(str);
	}

	public static Date getDate(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		return strToDate(str);
	}

	public static Short getShort(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = getParameter(paramHttpServletRequest, paramString);
		return strToShort(str);
	}

	public static Float strToFloat(String paramString) {
		if (paramString == null)
			return null;
		Float localFloat = null;
		try {
			localFloat = new Float(paramString);
		} catch (Exception localException) {
			localFloat = null;
		}
		return localFloat;
	}

	public static Integer strToInteger(String paramString) {
		if (paramString == null)
			return null;
		Integer localInteger = null;
		try {
			localInteger = new Integer(paramString);
		} catch (Exception localException) {
			localInteger = null;
		}
		return localInteger;
	}

	public static Double strToDouble(String paramString) {
		if (paramString == null)
			return null;
		Double localDouble = null;
		try {
			localDouble = new Double(paramString);
		} catch (Exception localException) {
			localDouble = null;
		}
		return localDouble;
	}

	public static Integer boolToInteger(String paramString) {
		if (paramString == null)
			return null;
		Integer localInteger = null;
		if (paramString.equalsIgnoreCase("true"))
			localInteger = Integer.valueOf(1);
		else if (paramString.equalsIgnoreCase("false"))
			localInteger = Integer.valueOf(0);
		return localInteger;
	}

	public static Double boolToDouble(String paramString) {
		if (paramString == null)
			return null;
		Double localDouble = null;
		if (paramString.equalsIgnoreCase("true"))
			localDouble = Double.valueOf(1);
		else if (paramString.equalsIgnoreCase("false"))
			localDouble = Double.valueOf(0);
		return localDouble;
	}

	public static Boolean strToBool(String paramString) {
		if (paramString == null)
			return null;
		Boolean localBoolean = null;
		try {
			localBoolean = new Boolean(paramString);
		} catch (Exception localException) {
			localBoolean = null;
		}
		return localBoolean;
	}

	public static Short strToShort(String paramString) {
		if (paramString == null)
			return null;
		Short localShort = null;
		try {
			localShort = new Short(paramString);
		} catch (Exception localException) {
			localShort = null;
		}
		return localShort;
	}

	public static Date strToDate(String paramString) {
		if (paramString == null)
			return null;
		Date localDate = null;
		if ((paramString.indexOf("-") == 4) && (paramString.lastIndexOf("-") == 7))
			try {
				int i = Integer.parseInt(paramString.substring(0, 4));
				int j = Integer.parseInt(paramString.substring(5, 7)) - 1;
				int k = Integer.parseInt(paramString.substring(8));
				Calendar localCalendar = Calendar.getInstance();
				localCalendar.set(i, j, k);
				localDate = new Date(localCalendar.getTimeInMillis());
			} catch (Exception localException) {
				localDate = null;
			}
		return localDate;
	}

	public static String getParameter(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = paramHttpServletRequest.getParameter(paramString);
		if (StringUtils.isNotBlank(str)) {
			str = str.trim();
			try {
				str = URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}

		return str;
	}

	public static String[] getCheckBox(HttpServletRequest paramHttpServletRequest, String paramString) {
		String[] arrayOfString = paramHttpServletRequest.getParameterValues(paramString);
		if ((arrayOfString == null) || (arrayOfString.length == 0))
			arrayOfString = null;
		return arrayOfString;
	}

	public static String getInput(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = null;
		String[] arrayOfString = getCheckBox(paramHttpServletRequest, paramString);
		if ((arrayOfString != null) && (arrayOfString.length != 0)) {
			StringBuffer localStringBuffer = new StringBuffer();
			for (int i = 0; i < arrayOfString.length; ++i)
				localStringBuffer.append(arrayOfString[i]);
			str = localStringBuffer.toString();
		}
		return str;
	}

	public static String getAllString(HttpServletRequest paramHttpServletRequest, String paramString) {
		String str = paramHttpServletRequest.getParameter(paramString);
		if ((str == null) || (str.trim().equals("")))
			str = (String) paramHttpServletRequest.getAttribute(paramString);
		else
			str.trim();
		return str;
	}

	public static Integer[] getParameterValues(HttpServletRequest paramHttpServletRequest, String paramString) {
		Integer[] arrayOfInteger = null;
		String[] arrayOfString = paramHttpServletRequest.getParameterValues(paramString);
		if (arrayOfString != null) {
			arrayOfInteger = new Integer[arrayOfString.length];
			for (int i = 0; i < arrayOfString.length; ++i)
				arrayOfInteger[i] = new Integer(arrayOfString[i]);
		}
		return arrayOfInteger;
	}

	public static Long getLong(HttpServletRequest request, String paramString) {
		String str = getParameter(request, paramString);
		Integer localInteger = strToInteger(str);
		if (localInteger == null) {
			localInteger = boolToInteger(str);
			return null;
		} else {
			return localInteger.longValue();
		}
	}
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	public static String getBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		return UserAgentParser.getBrowser(userAgent);
	}
	public static String getBrowser(String userAgent) {
		return UserAgentParser.getBrowser(userAgent);
	}
	public static String getOs(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		return UserAgentParser.getOS(userAgent);
	}
	public static String getOs(String userAgent) {
		return UserAgentParser.getOS(userAgent);
	}
}
