package com.sys.manageAdminRoleColumn.controller;

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
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageAdminRoleColumn.service.ManageAdminRoleColumnService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-04-06 10:12:45
 */
@Controller
@RequestMapping("/manageAdminRoleColumn")
public class ManageAdminRoleColumnController extends BaseController {

	// private static Logger logger =
	// Logger.getLogger(ManageAdminRoleColumnController.class); //Logger

	@Autowired
	private ManageAdminRoleColumnService manageadminrolecolumnService = null;

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageAdminRoleColumn/manageAdminRoleColumnIndex";
	}

	@Permission(value = "/manageAdminRoleColumn/addManageAdminRoleColumn")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageAdminRoleColumn/manageAdminRoleColumnAdd";
	}

	@Permission(value = "/manageAdminRoleColumn/updateManageAdminRoleColumn")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageAdminRoleColumn manageadminrolecolumn1 = requst2Bean(request, ManageAdminRoleColumn.class);
		ManageAdminRoleColumn manageadminrolecolumn = manageadminrolecolumnService
				.getManageAdminRoleColumn(manageadminrolecolumn1);
		model.addAttribute("manageadminrolecolumn", manageadminrolecolumn);
		return "/sys/manageAdminRoleColumn/manageAdminRoleColumnUpdate";
	}

	@Permission(value = "/manageAdminRoleColumn/getManageAdminRoleColumnList")
	@RequestMapping(value = "/getManageAdminRoleColumnList", method = RequestMethod.GET)
	public void getManageAdminRoleColumnList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminRoleColumn manageadminrolecolumn = requst2Bean(request, ManageAdminRoleColumn.class);
		int manageadminrolecolumnListCount = manageadminrolecolumnService
				.getManageAdminRoleColumnListCount(manageadminrolecolumn);
		ResponseList<ManageAdminRoleColumn> manageadminrolecolumnList = null;
		if (manageadminrolecolumnListCount > 0) {
			manageadminrolecolumnList = manageadminrolecolumnService
					.getManageAdminRoleColumnList(manageadminrolecolumn);
		} else {
			manageadminrolecolumnList = new ResponseList<ManageAdminRoleColumn>();
		}
		// 设置数据总数
		manageadminrolecolumnList.setTotalResults(manageadminrolecolumnListCount);

		writeSuccessMsg("ok", manageadminrolecolumnList, response);
	}

	@Permission(value = "/manageAdminRoleColumn/addManageAdminRoleColumn")
	@RequestMapping(value = "/addManageAdminRoleColumn", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageAdminRoleColumn manageadminrolecolumn = requst2Bean(request, ManageAdminRoleColumn.class);
		manageadminrolecolumnService.insertManageAdminRoleColumn(manageadminrolecolumn);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageAdminRoleColumn/updateManageAdminRoleColumn")
	@RequestMapping(value = "/updateManageAdminRoleColumn", method = RequestMethod.POST)
	public void updateManageAdminRoleColumn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminRoleColumn manageadminrolecolumn = requst2Bean(request, ManageAdminRoleColumn.class);
		int count = manageadminrolecolumnService.updateManageAdminRoleColumn(manageadminrolecolumn);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	@Permission(value = "/manageAdminRoleColumn/removeManageAdminRoleColumn")
	@RequestMapping(value = "/removeManageAdminRoleColumn", method = RequestMethod.POST)
	public void removeManageAdminRoleColumn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminRoleColumn manageadminrolecolumn = new ManageAdminRoleColumn();
		Integer id = RequestHandler.getInteger(request, "id");
		manageadminrolecolumn.setId(id);

		manageadminrolecolumnService.removeManageAdminRoleColumn(manageadminrolecolumn);
		writeSuccessMsg("删除成功", null, response);
	}

	@Permission(value = "/manageAdminRoleColumn/removeAllManageAdminRoleColumn")
	@RequestMapping(value = "/removeAllManageAdminRoleColumn", method = RequestMethod.POST)
	public void removeAllManageAdminRoleColumn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String ids = RequestHandler.getString(request, "ids");
		if (ids != null) {
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if (list != null) {
				for (Integer id : list) {
					ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
					manageAdminRoleColumn.setId(id);
					manageadminrolecolumnService.removeManageAdminRoleColumn(manageAdminRoleColumn);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
