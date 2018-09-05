package com.sys.manageColumn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.base.perm.Permission;
import com.sys.manageColumn.model.ManageColumn;
import com.sys.manageColumn.service.ManageColumnService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-04-06 10:02:33
 */
@Controller
@RequestMapping("/manageColumn")
public class ManageColumnController extends BaseController {

	@Autowired
	private ManageColumnService managecolumnService = null;

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageColumn manageColumn = new ManageColumn();
		manageColumn.setState(1);
		manageColumn.setParentColumnID(-1);
		List<ManageColumn> parentList = managecolumnService.getManageColumnBaseList(manageColumn);// 一级菜单
		model.addAttribute("parentList", parentList);
		return "/sys/manageColumn/manageColumnIndex";
	}

	@Permission(value = "/manageColumn/addManageColumn")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageColumn manageColumn = new ManageColumn();
		manageColumn.setState(1);
		manageColumn.setParentColumnID(-1);
		List<ManageColumn> parentList = managecolumnService.getManageColumnBaseList(manageColumn);// 一级菜单
		model.addAttribute("parentList", parentList);
		return "/sys/manageColumn/manageColumnAdd";
	}

	@Permission(value = "/manageColumn/updateManageColumn")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageColumn manageColumn = new ManageColumn();
		manageColumn.setState(1);
		manageColumn.setParentColumnID(-1);
		List<ManageColumn> parentList = managecolumnService.getManageColumnBaseList(manageColumn);// 一级菜单
		model.addAttribute("parentList", parentList);

		ManageColumn managecolumn1 = requst2Bean(request, ManageColumn.class);
		ManageColumn managecolumn = managecolumnService.getManageColumn(managecolumn1);
		model.addAttribute("managecolumn", managecolumn);
		return "/sys/manageColumn/manageColumnUpdate";
	}

	@Permission(value = "/manageColumn/getManageColumnList")
	@RequestMapping(value = "/getManageColumnList", method = RequestMethod.GET)
	public void getManageColumnList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageColumn managecolumn = requst2Bean(request, ManageColumn.class);
		int managecolumnListCount = managecolumnService.getManageColumnListCount(managecolumn);
		ResponseList<ManageColumn> managecolumnList = null;
		if (managecolumnListCount > 0) {
			managecolumnList = managecolumnService.getManageColumnList(managecolumn);
		} else {
			managecolumnList = new ResponseList<ManageColumn>();
		}
		// 设置数据总数
		managecolumnList.setTotalResults(managecolumnListCount);

		writeSuccessMsg("ok", managecolumnList, response);
	}

	@Permission(value = "/manageColumn/addManageColumn")
	@RequestMapping(value = "/addManageColumn", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageColumn managecolumn = requst2Bean(request, ManageColumn.class);
		managecolumn.setUse_state(1);
		managecolumnService.insertManageColumn(managecolumn);
		if (managecolumn.getColumnId() != null) {
			ManageColumn managecolumn1 = new ManageColumn();
			managecolumn1.setColumnId(managecolumn.getColumnId());
			managecolumn1.setSort_id(managecolumn.getColumnId());
			managecolumnService.updateManageColumn(managecolumn1);
		}
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageColumn/updateManageColumn")
	@RequestMapping(value = "/updateManageColumn", method = RequestMethod.POST)
	public void updateManageColumn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageColumn managecolumn = requst2Bean(request, ManageColumn.class);
		int count = managecolumnService.updateManageColumn(managecolumn);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	/**
	 * 菜单排序
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/sortColumn", method = RequestMethod.POST)
	public void sortColumn(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageColumn managecolumn = requst2Bean(request, ManageColumn.class);
		String sign = RequestHandler.getString(request, "sign");
		Integer id = RequestHandler.getInteger(request, "id");// 当前ID

		List<ManageColumn> list = managecolumnService.getManageColumnBaseList(managecolumn);

		ManageColumn column = null;// 交换对象
		ManageColumn currentColumn = null;// 当前对象
		int currentIndex = 0;// 当前对象索引
		for (int i = 0; i < list.size(); i++) {
			ManageColumn column1 = list.get(i);
			if (column1.getColumnId() == id.intValue()) {
				currentIndex = i;
				break;
			}
		}

		if (sign.equals("up")) {// 上移动
			if (currentIndex == 0) {// 已经是第一个了不能向上移动
				// writeSuccessMsg("已经是第一个了",null, response);
			} else {
				column = list.get(currentIndex - 1);// 获取上一个对象
				currentColumn = list.get(currentIndex);// 当前对象
			}
		} else if (sign.equals("down")) {// 下移动
			if (currentIndex == (list.size() - 1)) {// 已经是最后一个了不能向下移动
				// writeSuccessMsg("已经是最后一个了",null, response);
			} else {
				column = list.get(currentIndex + 1);// 获取下一个对象
				currentColumn = list.get(currentIndex);// 当前对象
			}
		}
		if (column != null && currentColumn != null) {
			ManageColumn column2 = new ManageColumn();
			column2.setColumnId(column.getColumnId());
			column2.setSort_id(currentColumn.getSort_id());
			managecolumnService.updateManageColumn(column2);// 更新序号

			column2 = new ManageColumn();
			column2.setColumnId(currentColumn.getColumnId());
			column2.setSort_id(column.getSort_id());
			managecolumnService.updateManageColumn(column2);// 更新序号
		}

		writeSuccessMsg("ok", null, response);
	}

	@Permission(value = "/manageColumn/removeManageColumn")
	@RequestMapping(value = "/removeManageColumn", method = RequestMethod.POST)
	public void removeManageColumn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageColumn managecolumn = new ManageColumn();
		Integer columnId = RequestHandler.getInteger(request, "columnId");
		managecolumn.setColumnId(columnId);

		managecolumnService.removeManageColumn(managecolumn);
		writeSuccessMsg("删除成功", null, response);
	}

	@Permission(value = "/manageColumn/removeAllManageColumn")
	@RequestMapping(value = "/removeAllManageColumn", method = RequestMethod.POST)
	public void removeAllManageColumn(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String columnIds = RequestHandler.getString(request, "columnIds");
		if (columnIds != null) {
			List<Integer> list = JSONArray.parseArray(columnIds, Integer.class);
			if (list != null) {
				for (Integer columnId : list) {
					ManageColumn manageColumn = new ManageColumn();
					manageColumn.setColumnId(columnId);
					managecolumnService.removeManageColumn(manageColumn);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
