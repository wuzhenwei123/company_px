package com.sys.manageException.controller;

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
import com.sys.manageException.model.ManageException;
import com.sys.manageException.service.ManageExceptionService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-03-23 13:11:26
 */
@Controller
@RequestMapping("/manageException")
public class ManageExceptionController extends BaseController {

	@Autowired
	private ManageExceptionService manageexceptionService = null;

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageException/manageExceptionIndex";
	}

	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageException/manageExceptionAdd";
	}

	@Permission(value = "/manageException/updateManageException")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageException manageexception1 = requst2Bean(request, ManageException.class);
		ManageException manageexception = manageexceptionService.getManageException(manageexception1);
		model.addAttribute("manageexception", manageexception);
		return "/sys/manageException/manageExceptionUpdate";
	}

	@RequestMapping(value = "/toShow", method = RequestMethod.GET)
	public String toShow(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageException manageexception1 = requst2Bean(request, ManageException.class);
		ManageException manageexception = manageexceptionService.getManageException(manageexception1);
		model.addAttribute("manageexception", manageexception);
		return "/sys/manageException/manageExceptionShow";
	}

	@Permission(value = "/manageException/getManageExceptionList")
	@RequestMapping(value = "/getManageExceptionList", method = RequestMethod.GET)
	public void getManageExceptionList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageException manageexception = requst2Bean(request, ManageException.class);
		int manageexceptionListCount = manageexceptionService.getManageExceptionListCount(manageexception);
		ResponseList<ManageException> manageexceptionList = null;
		if (manageexceptionListCount > 0) {
			manageexceptionList = manageexceptionService.getManageExceptionList(manageexception);
		} else {
			manageexceptionList = new ResponseList<ManageException>();
		}
		// 设置数据总数
		manageexceptionList.setTotalResults(manageexceptionListCount);

		writeSuccessMsg("ok", manageexceptionList, response);
	}

	@Permission(value = "/manageException/addManageException")
	@RequestMapping(value = "/addManageException", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageException manageexception = requst2Bean(request, ManageException.class);
		manageexception.setCreateTime(new Date());
		manageexceptionService.insertManageException(manageexception);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageException/updateManageException")
	@RequestMapping(value = "/updateManageException", method = RequestMethod.POST)
	public void updateManageException(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageException manageexception = requst2Bean(request, ManageException.class);
		int count = manageexceptionService.updateManageException(manageexception);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	@Permission(value = "/manageException/removeManageException")
	@RequestMapping(value = "/removeManageException", method = RequestMethod.POST)
	public void removeManageException(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageException manageexception = new ManageException();
		Integer id = RequestHandler.getInteger(request, "id");
		manageexception.setId(id);

		manageexceptionService.removeManageException(manageexception);
		writeSuccessMsg("删除成功", null, response);
	}

	@Permission(value = "/manageException/removeAllManageException")
	@RequestMapping(value = "/removeAllManageException", method = RequestMethod.POST)
	public void removeAllManageException(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String ids = RequestHandler.getString(request, "ids");
		if (ids != null) {
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if (list != null) {
				for (Integer id : list) {
					ManageException manageException = new ManageException();
					manageException.setId(id);
					manageexceptionService.removeManageException(manageException);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
