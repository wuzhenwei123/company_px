package com.sys.manageUserOpLog.controller;

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
import com.sys.manageUserOpLog.model.ManageUserOpLog;
import com.sys.manageUserOpLog.service.ManageUserOpLogService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-07-19 13:29:16
 */
@Controller
@RequestMapping("/manageUserOpLog")
public class ManageUserOpLogController extends BaseController {

	@Autowired
	private ManageUserOpLogService manageUserOpLogService = null;

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageUserOpLog/manageUserOpLogIndex";
	}

	@Permission(value = "/manageUserOpLog/addManageUserOpLog")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageUserOpLog/manageUserOpLogAdd";
	}

	@Permission(value = "/manageUserOpLog/updateManageUserOpLog")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageUserOpLog manageUserOpLog1 = requst2Bean(request, ManageUserOpLog.class);
		ManageUserOpLog manageUserOpLog = manageUserOpLogService.getManageUserOpLog(manageUserOpLog1);
		model.addAttribute("manageUserOpLog", manageUserOpLog);
		return "/sys/manageUserOpLog/manageUserOpLogUpdate";
	}

	@Permission(value = "/manageUserOpLog/getManageUserOpLogList")
	@RequestMapping(value = "/getManageUserOpLogList", method = RequestMethod.GET)
	public void getManageUserOpLogList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageUserOpLog manageUserOpLog = requst2Bean(request, ManageUserOpLog.class);
		int manageUserOpLogListCount = manageUserOpLogService.getManageUserOpLogListCount(manageUserOpLog);
		ResponseList<ManageUserOpLog> manageUserOpLogList = null;
		if (manageUserOpLogListCount > 0) {
			manageUserOpLogList = manageUserOpLogService.getManageUserOpLogList(manageUserOpLog);
		} else {
			manageUserOpLogList = new ResponseList<ManageUserOpLog>();
		}
		// 设置数据总数
		manageUserOpLogList.setTotalResults(manageUserOpLogListCount);

		writeSuccessMsg("ok", manageUserOpLogList, response);
	}

	@Permission(value = "/manageUserOpLog/addManageUserOpLog")
	@RequestMapping(value = "/addManageUserOpLog", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageUserOpLog manageUserOpLog = requst2Bean(request, ManageUserOpLog.class);
		manageUserOpLogService.insertManageUserOpLog(manageUserOpLog);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageUserOpLog/updateManageUserOpLog")
	@RequestMapping(value = "/updateManageUserOpLog", method = RequestMethod.POST)
	public void updateManageUserOpLog(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageUserOpLog manageUserOpLog = requst2Bean(request, ManageUserOpLog.class);
		int count = manageUserOpLogService.updateManageUserOpLog(manageUserOpLog);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	@Permission(value = "/manageUserOpLog/removeManageUserOpLog")
	@RequestMapping(value = "/removeManageUserOpLog", method = RequestMethod.POST)
	public void removeManageUserOpLog(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageUserOpLog manageUserOpLog = new ManageUserOpLog();
		Long id = RequestHandler.getLong(request, "id");
		manageUserOpLog.setId(id);

		manageUserOpLogService.removeManageUserOpLog(manageUserOpLog);
		writeSuccessMsg("删除成功", null, response);
	}

	@Permission(value = "/manageUserOpLog/removeAllManageUserOpLog")
	@RequestMapping(value = "/removeAllManageUserOpLog", method = RequestMethod.POST)
	public void removeAllManageUserOpLog(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String ids = RequestHandler.getString(request, "ids");
		if (ids != null) {
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if (list != null) {
				for (Long id : list) {
					ManageUserOpLog manageUserOpLog = new ManageUserOpLog();
					manageUserOpLog.setId(id);
					manageUserOpLogService.removeManageUserOpLog(manageUserOpLog);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
