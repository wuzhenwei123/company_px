package com.sys.manageUserLoginLog.controller;

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
import com.sys.manageUserLoginLog.model.ManageUserLoginLog;
import com.sys.manageUserLoginLog.service.ManageUserLoginLogService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-07-20 11:19:16
 */
@Controller
@RequestMapping("/manageUserLoginLog")
public class ManageUserLoginLogController extends BaseController {
	@Autowired
	private ManageUserLoginLogService manageUserLoginLogService = null;

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageUserLoginLog/manageUserLoginLogIndex";
	}

	@Permission(value = "/manageUserLoginLog/addManageUserLoginLog")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageUserLoginLog/manageUserLoginLogAdd";
	}

	@Permission(value = "/manageUserLoginLog/updateManageUserLoginLog")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageUserLoginLog manageUserLoginLog1 = requst2Bean(request, ManageUserLoginLog.class);
		ManageUserLoginLog manageUserLoginLog = manageUserLoginLogService.getManageUserLoginLog(manageUserLoginLog1);
		model.addAttribute("manageUserLoginLog", manageUserLoginLog);
		return "/sys/manageUserLoginLog/manageUserLoginLogUpdate";
	}

	@Permission(value = "/manageUserLoginLog/getManageUserLoginLogList")
	@RequestMapping(value = "/getManageUserLoginLogList", method = RequestMethod.GET)
	public void getManageUserLoginLogList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageUserLoginLog manageUserLoginLog = requst2Bean(request, ManageUserLoginLog.class);
		int manageUserLoginLogListCount = manageUserLoginLogService.getManageUserLoginLogListCount(manageUserLoginLog);
		ResponseList<ManageUserLoginLog> manageUserLoginLogList = null;
		if (manageUserLoginLogListCount > 0) {
			manageUserLoginLogList = manageUserLoginLogService.getManageUserLoginLogList(manageUserLoginLog);
		} else {
			manageUserLoginLogList = new ResponseList<ManageUserLoginLog>();
		}
		// 设置数据总数
		manageUserLoginLogList.setTotalResults(manageUserLoginLogListCount);

		writeSuccessMsg("ok", manageUserLoginLogList, response);
	}

	@Permission(value = "/manageUserLoginLog/addManageUserLoginLog")
	@RequestMapping(value = "/addManageUserLoginLog", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageUserLoginLog manageUserLoginLog = requst2Bean(request, ManageUserLoginLog.class);
		manageUserLoginLogService.insertManageUserLoginLog(manageUserLoginLog);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageUserLoginLog/updateManageUserLoginLog")
	@RequestMapping(value = "/updateManageUserLoginLog", method = RequestMethod.POST)
	public void updateManageUserLoginLog(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageUserLoginLog manageUserLoginLog = requst2Bean(request, ManageUserLoginLog.class);
		int count = manageUserLoginLogService.updateManageUserLoginLog(manageUserLoginLog);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	@Permission(value = "/manageUserLoginLog/removeManageUserLoginLog")
	@RequestMapping(value = "/removeManageUserLoginLog", method = RequestMethod.POST)
	public void removeManageUserLoginLog(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageUserLoginLog manageUserLoginLog = new ManageUserLoginLog();
		Long id = RequestHandler.getLong(request, "id");
		manageUserLoginLog.setId(id);

		manageUserLoginLogService.removeManageUserLoginLog(manageUserLoginLog);
		writeSuccessMsg("删除成功", null, response);
	}

	@Permission(value = "/manageUserLoginLog/removeAllManageUserLoginLog")
	@RequestMapping(value = "/removeAllManageUserLoginLog", method = RequestMethod.POST)
	public void removeAllManageUserLoginLog(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String ids = RequestHandler.getString(request, "ids");
		if (ids != null) {
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if (list != null) {
				for (Long id : list) {
					ManageUserLoginLog manageUserLoginLog = new ManageUserLoginLog();
					manageUserLoginLog.setId(id);
					manageUserLoginLogService.removeManageUserLoginLog(manageUserLoginLog);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
