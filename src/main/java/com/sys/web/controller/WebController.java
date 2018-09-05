package com.sys.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.CookieUtil;
import com.base.utils.DateUtils;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.sys.manageUserLoginLog.service.ManageUserLoginLogService;

/**
 * @author keeny
 * @time 2017-03-16 18:23:41
 */
@Controller
public class WebController extends BaseController {
	@Autowired
	private ManageUserLoginLogService manageUserLoginLogService = null;// 登录记录

	@RequestMapping(value = "/")
	public String firstpage(HttpServletRequest request, HttpServletResponse response, Model model) {
		if (isLogin(request)) {
			return "redirect:/main";
		} else {
			return "/sys/login";
		}
	}

	/**
	 * 登录入口
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		if (isLogin(request)) {
			return "redirect:/main";
		} else {
			return "/sys/login";
		}
	}

	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/main";
	}

	/**
	 * 默认页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/welcome";
	}

	@RequestMapping(value = "/loginout")
	public String loginout(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 清除session
		// 清除cookie
		HttpSession session = request.getSession();
		session.removeAttribute(SessionName.ADMIN_USER_ID);
		session.removeAttribute(SessionName.ADMIN_USER);
		session.removeAttribute(SessionName.ADMIN_USER_NAME);
		return "redirect:/sys/login";
	}

	@RequestMapping(value = "/layout")
	public void layout(HttpServletRequest request, HttpServletResponse response, Model model) {
		Integer layer_val = RequestHandler.getInteger(request, "layer_val");
		String layer = "";
		if (layer_val == 1) {
			layer = "container";
		}
		if (layer_val == 2) {
			layer = "container-fluid";
		}
		request.getSession().setAttribute(SessionName.SYS_LAYOUT, layer);
		CookieUtil.setCookie(SessionName.SYS_LAYOUT, layer, 60 * 60 * 24 * 30 * 12, null, response);
		writeSuccessMsg("ok", null, response);
	}

	@RequestMapping(value = "/record")
	public void record(HttpServletRequest request, HttpServletResponse response, Model model) {
		manageUserLoginLogService.record(request);
	}


	private int getRandomNum() {
		int nextInt = RandomUtils.nextInt(1000);
		return nextInt;
	}
}
