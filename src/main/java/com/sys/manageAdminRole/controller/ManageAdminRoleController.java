package com.sys.manageAdminRole.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Map;

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
import com.sys.columnMethod.model.ColumnMethod;
import com.sys.columnMethod.service.ColumnMethodService;
import com.sys.manageAdminRole.model.ManageAdminRole;
import com.sys.manageAdminRole.service.ManageAdminRoleService;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageAdminRoleColumn.service.ManageAdminRoleColumnService;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.sys.manageColumn.model.ManageColumn;
import com.sys.manageColumn.service.ManageColumnService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;
import com.base.controller.BaseController;
import com.base.exception.BaseException;

/**
 * @author keeny
 * @time 2017-04-06 09:42:57
 */
@Controller
@RequestMapping("/manageAdminRole")
public class ManageAdminRoleController extends BaseController {

	// private static Logger logger =
	// Logger.getLogger(ManageAdminRoleController.class); //Logger

	@Autowired
	private ManageAdminRoleService manageadminroleService = null;
	@Autowired
	private ManageAdminRoleColumnService manageadminrolecolumnService = null;// 角色菜单权限
	@Autowired
	private ManageAdminUserService manageadminuserService = null;// 用户
	@Autowired
	private ManageColumnService managecolumnService = null;// 菜单
	@Autowired
	private ColumnMethodService columnMethodService = null;// 菜单对应操作权限
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;// 角色操作权限

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageAdminRole/manageAdminRoleIndex";
	}

	@Permission(value = "/manageAdminRole/addManageAdminRole")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/sys/manageAdminRole/manageAdminRoleAdd";
	}

	@Permission(value = "/manageAdminRole/showColumn")
	@RequestMapping(value = "/showColumn", method = RequestMethod.GET)
	public String showColumn(HttpServletRequest request, HttpServletResponse response, Model model) {
		Integer roleId = RequestHandler.getInteger(request, "roleId");

		model.addAttribute("roleId", roleId);

		return "/sys/manageAdminRole/showColumn";
	}

	@RequestMapping(value = "/loadColumnData", method = RequestMethod.POST)
	public void loadColumnData(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		Integer roleId = RequestHandler.getInteger(request, "roleId");
		Integer pid = RequestHandler.getInteger(request, "pid");
		ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
		manageAdminRoleColumn.setRoleId(roleId);
		List<ManageAdminRoleColumn> roleColumnList = manageadminrolecolumnService
				.getManageAdminRoleColumnBaseList(manageAdminRoleColumn);// 角色菜单权限

		AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
		adminRoleMethod.setRoleId(roleId);
		List<AdminRoleMethod> roleMedList = adminRoleMethodService.getAdminRoleMethodBaseList(adminRoleMethod);// 角色-操作权限

		ManageColumn manageColumn = new ManageColumn();
		manageColumn.setUse_state(1);
		manageColumn.setState(1);
		manageColumn.setParentColumnID(pid);
		manageColumn.setOrder("asc");
		manageColumn.setSort("sort_id");
		List<ManageColumn> manageColumnBaseList = managecolumnService.getManageColumnBaseList(manageColumn);

		List<Map<String, Object>> nodesList = new ArrayList<Map<String, Object>>();
		for (ManageColumn manageColumn2 : manageColumnBaseList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pId", manageColumn2.getParentColumnID());
			map.put("id", manageColumn2.getColumnId());
			map.put("name", manageColumn2.getColumnName());
			map.put("open", true);// 默认打开
			map.put("checked", false);// 默认不选上

			if (manageColumn2.getState() == 0) {
				map.put("nocheck", true);// 禁止选中
			}
			// ---------------查询菜单对应方法
			ColumnMethod columnMethod = new ColumnMethod();
			columnMethod.setColumnId(manageColumn2.getColumnId());
			List<ColumnMethod> medList = columnMethodService.getColumnMethodBaseList(columnMethod);

			List<Map<String, Object>> medReList = new ArrayList<Map<String, Object>>();
			for (ColumnMethod med : medList) {
				Map<String, Object> medMap = new HashMap<String, Object>();
				medMap.put("mid", med.getMid());
				medMap.put("name", med.getZh_name());
				medMap.put("checked", false);// 默认不选上

				for (AdminRoleMethod method : roleMedList) {
					if (method.getMid().equals(med.getMid())) {
						medMap.put("checked", true);
						break;
					}
				}

				medReList.add(medMap);
			}
			// ---------------查询菜单对应方法
			map.put("meds", JSONArray.toJSON(medReList).toString());

			for (ManageAdminRoleColumn adminRoleColumn2 : roleColumnList) {// 遍历菜单，看是否自己有权限
				if (adminRoleColumn2.getColumnId().equals(manageColumn2.getColumnId())) {
					map.put("checked", true);
					break;
				}
			}

			nodesList.add(map);
		}
		writeJsonObject(nodesList, response);
	}

	/**
	 * 更新操作方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Permission(value = "/manageAdminRole/showColumn")
	@RequestMapping(value = "/updateRoleMed", method = RequestMethod.POST)
	public String updateRoleMed(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {

		Integer columnId = RequestHandler.getInteger(request, "columnId");// 菜单
		Integer roleId = RequestHandler.getInteger(request, "roleId");// 角色
		String mid = RequestHandler.getString(request, "mid");// 操作方法ID
		String ck = RequestHandler.getString(request, "ck");// 操作类型，删除 OR 添加

		if (ck.equals("add")) {
			// 开始处理菜单
			ColumnMethod columnMethod = new ColumnMethod();
			columnMethod.setMid(Integer.valueOf(mid));
			List<ColumnMethod> columnMethodBaseList = columnMethodService.getColumnMethodBaseList(columnMethod);// 操作方法所属菜单
			if (columnMethodBaseList != null) {
				ColumnMethod columnMethod2 = columnMethodBaseList.get(0);
				ManageAdminRoleColumn adminRoleColumn = new ManageAdminRoleColumn();
				adminRoleColumn.setRoleId(roleId);
				adminRoleColumn.setColumnId(columnMethod2.getColumnId());
				int adminRoleColumnListCount = manageadminrolecolumnService
						.getManageAdminRoleColumnListCount(adminRoleColumn);
				if (adminRoleColumnListCount == 0) {// 如果此操作方法上的菜单没有授权，这里要进行授权
					ManageAdminRoleColumn roleColumn1 = new ManageAdminRoleColumn();
					roleColumn1.setRoleId(roleId);
					roleColumn1.setColumnId(columnMethod2.getColumnId());
					manageadminrolecolumnService.insertManageAdminRoleColumn(roleColumn1);
				}

				// 开始检查 父节点是否授权
				ManageColumn manageColumn = new ManageColumn();
				manageColumn.setColumnId(columnMethod2.getColumnId());
				ManageColumn manageColumn2 = managecolumnService.getManageColumn(manageColumn);
				Integer parentColumnID = manageColumn2.getParentColumnID();

				adminRoleColumn = new ManageAdminRoleColumn();
				adminRoleColumn.setRoleId(roleId);
				adminRoleColumn.setColumnId(parentColumnID);
				adminRoleColumnListCount = manageadminrolecolumnService
						.getManageAdminRoleColumnListCount(adminRoleColumn);
				if (adminRoleColumnListCount == 0) {// 最上级未授权，这里开始授权
					ManageAdminRoleColumn roleColumn1 = new ManageAdminRoleColumn();
					roleColumn1.setRoleId(roleId);
					roleColumn1.setColumnId(parentColumnID);
					manageadminrolecolumnService.insertManageAdminRoleColumn(roleColumn1);
				}
			}

			AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
			adminRoleMethod.setRoleId(roleId);
			adminRoleMethod.setMid(Integer.valueOf(mid));
			adminRoleMethod.setColumnId(columnId);
			adminRoleMethodService.insertAdminRoleMethod(adminRoleMethod);// 插入操作权限
		}
		if (ck.equals("move")) {
			ColumnMethod columnMethod = new ColumnMethod();
			columnMethod.setMid(Integer.valueOf(mid));
			List<ColumnMethod> columnMethodBaseList = columnMethodService.getColumnMethodBaseList(columnMethod);// 操作方法所属菜单
			if (columnMethodBaseList != null) {
				boolean isDelColumn = true;// 是否要删除父授权
				ColumnMethod columnMethod2 = columnMethodBaseList.get(0);
				columnMethod = new ColumnMethod();
				columnMethod.setColumnId(columnMethod2.getColumnId());
				List<ColumnMethod> columnMethodBaseList2 = columnMethodService.getColumnMethodBaseList(columnMethod);// 此操作方法的兄弟操作方法
				for (ColumnMethod columnMethod3 : columnMethodBaseList2) {
					if (columnMethod3.getMid().intValue() == Integer.valueOf(mid).intValue()) {// 排除自己
						continue;
					}
					AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
					adminRoleMethod.setRoleId(roleId);
					adminRoleMethod.setMid(columnMethod3.getMid());
					int adminRoleMethodListCount = adminRoleMethodService.getAdminRoleMethodListCount(adminRoleMethod);
					if (adminRoleMethodListCount != 0) {// 有兄弟节点已被授权，不能删除父授权
						isDelColumn = false;
						break;
					}
				}
				if (isDelColumn) {
					// 开始检查 父节点是否授权
					ManageAdminRoleColumn adminRoleColumn = new ManageAdminRoleColumn();
					adminRoleColumn.setRoleId(roleId);
					adminRoleColumn.setColumnId(columnMethod2.getColumnId());
					manageadminrolecolumnService.removeAdminRoleColumnByRoleIdColumnId(adminRoleColumn);// 删除所属菜单权限

					boolean isDelParent = true;// 是否删除父授权
					ManageColumn manageColumn = new ManageColumn();
					manageColumn.setColumnId(columnMethod2.getColumnId());
					ManageColumn manageColumn2 = managecolumnService.getManageColumn(manageColumn);// 获取当前菜单

					Integer parentColumnID = manageColumn2.getParentColumnID();
					manageColumn = new ManageColumn();
					manageColumn.setParentColumnID(parentColumnID);
					List<ManageColumn> manageColumnBaseList = managecolumnService.getManageColumnBaseList(manageColumn);// 获取兄弟菜单
					if (manageColumnBaseList != null && manageColumnBaseList.size() == 1) {// 只有一个菜单，则父菜单取消授权
						isDelParent = true;
					} else {
						for (ManageColumn manageColumn3 : manageColumnBaseList) {// 兄弟节点，如果有一个兄弟节点授权了。则其父不取消授权
							if (manageColumn3.getColumnId().intValue() == manageColumn2.getColumnId().intValue()) {// 排除当前节点
								continue;
							}
							adminRoleColumn = new ManageAdminRoleColumn();
							adminRoleColumn.setRoleId(roleId);
							adminRoleColumn.setColumnId(manageColumn3.getColumnId());
							int adminRoleColumnListCount = manageadminrolecolumnService
									.getManageAdminRoleColumnListCount(adminRoleColumn);// 查看兄弟节点是否授权
							if (adminRoleColumnListCount != 0) {// 兄弟节点已授权，父节点不取消授权
								isDelParent = false;
								break;
							}
						}
					}
					if (isDelParent) {
						ManageAdminRoleColumn roleColumn = new ManageAdminRoleColumn();
						roleColumn.setRoleId(roleId);
						roleColumn.setColumnId(parentColumnID);
						manageadminrolecolumnService.removeAdminRoleColumnByRoleIdColumnId(roleColumn);
					}
				}
			}
			AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
			adminRoleMethod.setRoleId(roleId);
			adminRoleMethod.setMid(Integer.valueOf(mid));
			adminRoleMethodService.removeAdminRoleMethodByRoleIdMid(adminRoleMethod);
		}
		manageadminuserService.initOp(request.getSession());
		writeSuccessMsg("ok", null, response);
		return null;
	}

	@Permission(value = "/manageAdminRole/updateManageAdminRole")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageAdminRole manageadminrole1 = requst2Bean(request, ManageAdminRole.class);
		ManageAdminRole manageadminrole = manageadminroleService.getManageAdminRole(manageadminrole1);
		model.addAttribute("manageadminrole", manageadminrole);
		return "/sys/manageAdminRole/manageAdminRoleUpdate";
	}

	@Permission(value = "/manageAdminRole/getManageAdminRoleList")
	@RequestMapping(value = "/getManageAdminRoleList", method = RequestMethod.GET)
	public void getManageAdminRoleList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminRole manageadminrole = requst2Bean(request, ManageAdminRole.class);
		int manageadminroleListCount = manageadminroleService.getManageAdminRoleListCount(manageadminrole);
		ResponseList<ManageAdminRole> manageadminroleList = null;
		if (manageadminroleListCount > 0) {
			manageadminroleList = manageadminroleService.getManageAdminRoleList(manageadminrole);
		} else {
			manageadminroleList = new ResponseList<ManageAdminRole>();
		}
		// 设置数据总数
		manageadminroleList.setTotalResults(manageadminroleListCount);

		writeSuccessMsg("ok", manageadminroleList, response);
	}

	@Permission(value = "/manageAdminRole/addManageAdminRole")
	@RequestMapping(value = "/addManageAdminRole", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageAdminRole manageadminrole = requst2Bean(request, ManageAdminRole.class);
		manageadminrole.setCreateTime(new Date());
		manageadminroleService.insertManageAdminRole(manageadminrole);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageAdminRole/updateManageAdminRole")
	@RequestMapping(value = "/updateManageAdminRole", method = RequestMethod.POST)
	public void updateManageAdminRole(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminRole manageadminrole = requst2Bean(request, ManageAdminRole.class);
		int count = manageadminroleService.updateManageAdminRole(manageadminrole);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	/**
	 * 菜单授权
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Permission(value = "/manageAdminRole/showColumn")
	@RequestMapping(value = "/updateRoleColumnNew", method = RequestMethod.POST)
	public String updateRoleColumnNew(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {

		Integer roleId = RequestHandler.getInteger(request, "roleId");// 角色
		Integer columnId = RequestHandler.getInteger(request, "columnId");// 菜单ID
		String ck = RequestHandler.getString(request, "ck");// 操作类型，删除 OR 添加

		if (ck.equals("add")) {
			ManageColumn manageColumn = new ManageColumn();
			manageColumn.setColumnId(columnId);
			ManageColumn manageColumn2 = managecolumnService.getManageColumn(manageColumn);

			if (manageColumn2.getParentColumnID() == -1) {// 父菜单，下面所有授权
				manageColumn = new ManageColumn();
				manageColumn.setParentColumnID(columnId);
				List<ManageColumn> manageColumnBaseList = managecolumnService.getManageColumnBaseList(manageColumn);
				for (ManageColumn manageColumn3 : manageColumnBaseList) {
					ManageAdminRoleColumn roleColumn = new ManageAdminRoleColumn();
					roleColumn.setRoleId(roleId);
					roleColumn.setColumnId(manageColumn3.getColumnId());

					int adminRoleColumnListCount = manageadminrolecolumnService
							.getManageAdminRoleColumnListCount(roleColumn);
					if (adminRoleColumnListCount == 0) {
						manageadminrolecolumnService.insertManageAdminRoleColumn(roleColumn);// 插入操作权限
					}
				}
			} else {// 子菜单，要授权其父菜单

				Integer parentColumnID = manageColumn2.getParentColumnID();
				ManageAdminRoleColumn roleColumn1 = new ManageAdminRoleColumn();
				roleColumn1.setRoleId(roleId);
				roleColumn1.setColumnId(parentColumnID);
				int adminRoleColumnListCount = manageadminrolecolumnService
						.getManageAdminRoleColumnListCount(roleColumn1);
				if (adminRoleColumnListCount == 0) {// 父菜单没有授权，这里要授权
					manageadminrolecolumnService.insertManageAdminRoleColumn(roleColumn1);// 插入操作权限
				}
			}
			ManageAdminRoleColumn roleColumn = new ManageAdminRoleColumn();
			roleColumn.setRoleId(roleId);
			roleColumn.setColumnId(columnId);
			int adminRoleColumnListCount = manageadminrolecolumnService.getManageAdminRoleColumnListCount(roleColumn);
			if (adminRoleColumnListCount == 0) {
				manageadminrolecolumnService.insertManageAdminRoleColumn(roleColumn);// 插入操作权限
			}
		}
		if (ck.equals("move")) {
			ManageColumn manageColumn = new ManageColumn();
			manageColumn.setColumnId(columnId);
			ManageColumn manageColumn2 = managecolumnService.getManageColumn(manageColumn);

			if (manageColumn2.getParentColumnID() == -1) {// 父菜单，下面所有取消授权
				manageColumn = new ManageColumn();
				manageColumn.setParentColumnID(columnId);
				List<ManageColumn> manageColumnBaseList = managecolumnService.getManageColumnBaseList(manageColumn);
				for (ManageColumn manageColumn3 : manageColumnBaseList) {
					ManageAdminRoleColumn roleColumn = new ManageAdminRoleColumn();
					roleColumn.setRoleId(roleId);
					roleColumn.setColumnId(manageColumn3.getColumnId());
					manageadminrolecolumnService.removeAdminRoleColumnByRoleIdColumnId(roleColumn);
				}
			} else {// 如果其父菜单下只有一个子菜单，则把父菜单取消授权
				boolean isDelParent = true;// 是否删除父授权
				Integer parentColumnID = manageColumn2.getParentColumnID();
				manageColumn = new ManageColumn();
				manageColumn.setParentColumnID(parentColumnID);
				List<ManageColumn> manageColumnBaseList = managecolumnService.getManageColumnBaseList(manageColumn);
				if (manageColumnBaseList != null && manageColumnBaseList.size() == 1) {// 只有一个菜单，则父菜单取消授权
					isDelParent = true;
				} else {
					for (ManageColumn manageColumn3 : manageColumnBaseList) {// 兄弟节点，如果有一个兄弟节点授权了。则其父不取消授权
						if (manageColumn3.getColumnId().intValue() == columnId.intValue()) {// 排除当前节点
							continue;
						}
						ManageAdminRoleColumn adminRoleColumn = new ManageAdminRoleColumn();
						adminRoleColumn.setRoleId(roleId);
						adminRoleColumn.setColumnId(manageColumn3.getColumnId());
						int adminRoleColumnListCount = manageadminrolecolumnService
								.getManageAdminRoleColumnListCount(adminRoleColumn);// 查看兄弟节点是否授权
						if (adminRoleColumnListCount != 0) {// 兄弟节点已授权，父节点不取消授权
							isDelParent = false;
							break;
						}
					}
				}
				if (isDelParent) {
					ManageAdminRoleColumn roleColumn = new ManageAdminRoleColumn();
					roleColumn.setRoleId(roleId);
					roleColumn.setColumnId(parentColumnID);
					manageadminrolecolumnService.removeAdminRoleColumnByRoleIdColumnId(roleColumn);
				}
			}
			ManageAdminRoleColumn roleColumn = new ManageAdminRoleColumn();
			roleColumn.setRoleId(roleId);
			roleColumn.setColumnId(columnId);
			manageadminrolecolumnService.removeAdminRoleColumnByRoleIdColumnId(roleColumn);
		}

		manageadminuserService.initColumn(request.getSession());
		writeSuccessMsg("ok", null, response);

		return null;
	}

	@Permission(value = "/manageAdminRole/removeManageAdminRole")
	@RequestMapping(value = "/removeManageAdminRole", method = RequestMethod.POST)
	public void removeManageAdminRole(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminRole manageadminrole = new ManageAdminRole();
		Integer roleId = RequestHandler.getInteger(request, "roleId");

		ManageAdminUser manageAdminUser = new ManageAdminUser();
		manageAdminUser.setRole_id(roleId);
		int manageAdminUserListCount = manageadminuserService.getManageAdminUserListCount(manageAdminUser);

		ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
		manageAdminRoleColumn.setRoleId(roleId);
		int manageAdminRoleColumnListCount = manageadminrolecolumnService
				.getManageAdminRoleColumnListCount(manageAdminRoleColumn);

		if (manageAdminUserListCount == 0 && manageAdminRoleColumnListCount == 0) {
			manageadminrole.setRoleId(roleId);
			manageadminroleService.removeManageAdminRole(manageadminrole);
			writeSuccessMsg("删除成功", null, response);
		} else {
			writeErrorMsg("角色正在使用不能删除", null, response);
		}

	}

	@Permission(value = "/manageAdminRole/removeAllManageAdminRole")
	@RequestMapping(value = "/removeAllManageAdminRole", method = RequestMethod.POST)
	public void removeAllManageAdminRole(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String roleIds = RequestHandler.getString(request, "roleIds");
		if (roleIds != null) {
			List<Integer> list = JSONArray.parseArray(roleIds, Integer.class);
			if (list != null) {
				for (Integer roleId : list) {
					ManageAdminRole manageAdminRole = new ManageAdminRole();
					manageAdminRole.setRoleId(roleId);
					manageadminroleService.removeManageAdminRole(manageAdminRole);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
