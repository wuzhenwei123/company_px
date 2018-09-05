package com.base.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 
 * @author zhanglibo
 *
 */
public class StopWatchInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LogManager.getLogger(StopWatchInterceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long beginTime = System.currentTimeMillis();
		startTimeThreadLocal.set(beginTime);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();
		long beginTime = startTimeThreadLocal.get();
		long consumeTime = endTime - beginTime;
		if (consumeTime > 1000) {
			logger.error("[慢请求] 共 {} ms,{} {} 参数 {} headers {}", consumeTime, request.getMethod(), request.getRequestURI(),
					request.getParameterMap(), getHeadersInfo(request));
		}
	}

	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}