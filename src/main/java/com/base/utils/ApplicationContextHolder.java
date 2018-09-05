package com.base.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class ApplicationContextHolder {
	public static Object getBean(String beanName) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(beanName);
	}
}
