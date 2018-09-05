package com.base.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.base.utils.ConfigConstants;


public class StartupListener extends ContextLoaderListener {
	protected final Logger logger = LogManager.getLogger(getClass());
	private static ServletContext servletContext;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("web contextDestroyed-----");
		super.contextDestroyed(event);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("web contextInitialized-----");
		ConfigConstants.init();
		super.contextInitialized(event);
	}

}
