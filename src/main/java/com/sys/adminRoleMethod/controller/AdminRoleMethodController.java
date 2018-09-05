package com.sys.adminRoleMethod.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.base.perm.Permission;
import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.adminRoleMethod.service.AdminRoleMethodService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-04-08 15:44:19
 */
@Controller
@RequestMapping("/adminRoleMethod")
public class AdminRoleMethodController extends BaseController {
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/adminRoleMethod/adminRoleMethodIndex";
	}

	@Permission(value = "/adminRoleMethod/addAdminRoleMethod")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/adminRoleMethod/adminRoleMethodAdd";
	}

	@Permission(value = "/adminRoleMethod/updateAdminRoleMethod")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		AdminRoleMethod adminRoleMethod1 = requst2Bean(request, AdminRoleMethod.class);
		AdminRoleMethod adminRoleMethod = adminRoleMethodService.getAdminRoleMethod(adminRoleMethod1);
		model.addAttribute("adminRoleMethod", adminRoleMethod);
		return "/adminRoleMethod/adminRoleMethodUpdate";
	}

	@Permission(value = "/adminRoleMethod/getAdminRoleMethodList")
	@RequestMapping(value = "/getAdminRoleMethodList", method = RequestMethod.GET)
	public void getAdminRoleMethodList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		AdminRoleMethod adminRoleMethod = requst2Bean(request, AdminRoleMethod.class);
		int adminRoleMethodListCount = adminRoleMethodService.getAdminRoleMethodListCount(adminRoleMethod);
		ResponseList<AdminRoleMethod> adminRoleMethodList = null;
		if (adminRoleMethodListCount > 0) {
			adminRoleMethodList = adminRoleMethodService.getAdminRoleMethodList(adminRoleMethod);
		} else {
			adminRoleMethodList = new ResponseList<AdminRoleMethod>();
		}
		// 设置数据总数
		adminRoleMethodList.setTotalResults(adminRoleMethodListCount);

		writeSuccessMsg("ok", adminRoleMethodList, response);
	}

	@Permission(value = "/adminRoleMethod/addAdminRoleMethod")
	@RequestMapping(value = "/addAdminRoleMethod", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		AdminRoleMethod adminRoleMethod = requst2Bean(request, AdminRoleMethod.class);
		adminRoleMethodService.insertAdminRoleMethod(adminRoleMethod);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/adminRoleMethod/updateAdminRoleMethod")
	@RequestMapping(value = "/updateAdminRoleMethod", method = RequestMethod.POST)
	public void updateAdminRoleMethod(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		AdminRoleMethod adminRoleMethod = requst2Bean(request, AdminRoleMethod.class);
		int count = adminRoleMethodService.updateAdminRoleMethod(adminRoleMethod);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	@Permission(value = "/adminRoleMethod/removeAdminRoleMethod")
	@RequestMapping(value = "/removeAdminRoleMethod", method = RequestMethod.POST)
	public void removeAdminRoleMethod(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
		Integer id = RequestHandler.getInteger(request, "id");
		adminRoleMethod.setId(id);

		adminRoleMethodService.removeAdminRoleMethod(adminRoleMethod);
		writeSuccessMsg("删除成功", null, response);
	}

	@Permission(value = "/adminRoleMethod/removeAllAdminRoleMethod")
	@RequestMapping(value = "/removeAllAdminRoleMethod", method = RequestMethod.POST)
	public void removeAllAdminRoleMethod(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String ids = RequestHandler.getString(request, "ids");
		if (ids != null) {
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if (list != null) {
				for (Integer id : list) {
					AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
					adminRoleMethod.setId(id);
					adminRoleMethodService.removeAdminRoleMethod(adminRoleMethod);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
