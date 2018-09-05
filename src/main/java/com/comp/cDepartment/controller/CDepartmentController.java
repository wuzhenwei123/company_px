package com.comp.cDepartment.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sys.manageAdminRole.model.ManageAdminRole;
import com.sys.manageAdminRole.service.ManageAdminRoleService;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.perm.Permission;
import com.comp.cDepartment.model.CDepartment;
import com.comp.cDepartment.service.CDepartmentService;
import com.base.utils.ConfigConstants;
import com.base.utils.RedisCacheClient;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-22 10:36:16
 */
@Controller
@RequestMapping("/cDepartment")
public class CDepartmentController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CDepartmentController.class); //Logger
	
	@Autowired
	private CDepartmentService cDepartmentService = null;
	@Autowired
	private ManageAdminRoleService manageadminroleService = null;// 角色
	@Autowired
	private ManageAdminUserService manageadminuserService = null;
	@Autowired
	private RedisCacheClient redisCacheClient;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		//获取角色列表
		ManageAdminRole manageAdminRole = new ManageAdminRole();
		manageAdminRole.setState(1);
		List<ManageAdminRole> listRole = manageadminroleService.getManageAdminRoleBaseList(manageAdminRole);
		model.addAttribute("listRole", listRole);
		return "/cDepartment/cDepartmentIndex";
	}
	@Permission(value = "/cDepartment/addCDepartment")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cDepartment/cDepartmentAdd";
	}
	@Permission(value = "/cDepartment/updateCDepartment")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String id)
	{	
		CDepartment cDepartment = cDepartmentService.getCDepartmentById(id);
		model.addAttribute("cDepartment", cDepartment);
		return "/cDepartment/cDepartmentUpdate";
	}
	@Permission(value = "/cDepartment/getCDepartmentList")
	@RequestMapping(value = "/getCDepartmentList", method = RequestMethod.GET)
	public void getCDepartmentList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CDepartment cDepartment = requst2Bean(request,CDepartment.class);
		PageHelper.offsetPage(cDepartment.getOffset(), cDepartment.getLimit());
		if(cDepartment.getSort() != null && cDepartment.getOrder() != null){
			PageHelper.orderBy(cDepartment.getSort() +" "+ cDepartment.getOrder());
		}			
		List<CDepartment> cDepartmentList = cDepartmentService.getCDepartmentList(cDepartment);
		PageInfo<CDepartment> pageInfo = new PageInfo<CDepartment>(cDepartmentList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cDepartment/addCDepartment")
	@RequestMapping(value = "/addCDepartment", method = RequestMethod.POST)
	public void addCDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CDepartment cDepartment = requst2Bean(request,CDepartment.class);
		cDepartmentService.insertCDepartment(cDepartment);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cDepartment/updateCDepartment")
	@RequestMapping(value = "/updateCDepartment", method = RequestMethod.POST)
	public void updateCDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CDepartment cDepartment = requst2Bean(request,CDepartment.class);
		int count = cDepartmentService.updateCDepartmentById(cDepartment);
		if(count == 1){
			//加入rides
			CDepartment cDepartment4 = cDepartmentService.getCDepartmentById(cDepartment.getId());
			redisCacheClient.set(cDepartment4.getCode(), cDepartment4);
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cDepartment/updateCDepartment")
	@RequestMapping(value = "/updateCDepartment1", method = RequestMethod.POST)
	public void updateCDepartment1(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer sortIdn = RequestHandler.getInteger(request, "sortIdn");
		String idn = RequestHandler.getString(request, "idn");
		CDepartment cDepartment = requst2Bean(request,CDepartment.class);
		int count = cDepartmentService.updateCDepartmentById(cDepartment);
		if(count == 1){
			CDepartment cDepartment1 = new CDepartment();
			cDepartment1.setId(idn);
			cDepartment1.setSortId(sortIdn);
			cDepartmentService.updateCDepartmentById(cDepartment1);
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cDepartment/removeCDepartment")
	@RequestMapping(value = "/removeCDepartment", method = RequestMethod.POST)
	public void removeCDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String id = RequestHandler.getString(request, "id");
		
		ManageAdminUser manageAdminUser = new ManageAdminUser();
		manageAdminUser.setDepartmentId(id);
		int count = manageadminuserService.getManageAdminUserListCount(manageAdminUser);
		if(count==0){
			CDepartment cDepartment4 = cDepartmentService.getCDepartmentById(id);
			cDepartmentService.deleteCDepartmentById(id);
			//移除rides
			redisCacheClient.delete(cDepartment4.getCode());
			
			writeSuccessMsg("删除成功", null, response);
		}else{
			writeErrorMsg("删除失败", null, response);
		}
		
	}
	@Permission(value = "/cDepartment/removeAllCDepartment")
	@RequestMapping(value = "/removeAllCDepartment", method = RequestMethod.POST)
	public void removeAllCDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<String> list = JSONArray.parseArray(ids, String.class);
			if(list != null){
				for (String id : list) {
					cDepartmentService.deleteCDepartmentById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
	
	@Permission(value = "/cDepartment/getRootNode")
	@RequestMapping(value = "/getRootNode", method = RequestMethod.GET)
	public String getRootNode(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		
		ManageAdminUser manageadminuser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		
		JSONArray array = new JSONArray(); 
		try{
			CDepartment cDepartment = requst2Bean(request,CDepartment.class);
			
			if(manageadminuser.getRole_id().intValue()!=Integer.valueOf(ConfigConstants.SUPER_MANAGE_ROLE).intValue()){
				cDepartment.setParentIds(manageadminuser.getDepartmentIds());
			}
			
			cDepartment.setSort("sort_id");
			cDepartment.setOrder("asc");
			List<CDepartment> yycourseclassifyList = cDepartmentService.getCDepartmentListBySort(cDepartment);
			if(yycourseclassifyList!=null&&yycourseclassifyList.size()>0){
				for (CDepartment cc : yycourseclassifyList) { 
	                JSONObject jsonObject = new JSONObject();  
	                jsonObject.put("id",cc.getId());  
	                jsonObject.put("name",cc.getName());  
	                jsonObject.put("pId",cc.getParentId());
	                jsonObject.put("pIds",cc.getParentIds());
	                jsonObject.put("sort_id",cc.getSortId());
	                if("0".equals(cc.getParentId())){
	                	jsonObject.put("open", true);
	                }else{
	                	jsonObject.put("open", false);
	                }
	                array.add(jsonObject);  
	            }  
			}
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control","no-cache");
			response.getWriter().write(array.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Permission(value = "/cDepartment/addNodes")
	@RequestMapping(value = "/toAddLevelCourse", method = RequestMethod.GET)
	public String toAddLevelCourse(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		Integer level = RequestHandler.getInteger(request, "level");
		String pId = RequestHandler.getString(request, "pId");
		model.addAttribute("pId", pId);
		model.addAttribute("level", level);
		return "/cDepartment/addLevelDepartment";
	}
	
	@Permission(value = "/cDepartment/addNodes")
	@RequestMapping(value = "/addNodes", method = RequestMethod.POST)
	public String addNodes(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		try
		{	
			String pId = RequestHandler.getString(request, "parentId");
			String name = RequestHandler.getString(request, "name");
			String code = RequestHandler.getString(request, "code");
			Integer level = RequestHandler.getInteger(request, "level");
			CDepartment cDepartment = new CDepartment();
			cDepartment.setName(name);
			cDepartment.setCode(code);
			cDepartment.setLevel(level!=null?(level+1):null);
			cDepartment.setParentId(pId);
			//排序
			CDepartment cDepartment1 = new CDepartment();
			cDepartment1.setParentId(pId);
			int count = cDepartmentService.getCDepartmentCount(cDepartment1);
			cDepartment.setSortId(count);
			Long id = cDepartmentService.insertCDepartment(cDepartment);
			
			CDepartment cDepartment3 = new CDepartment();
			CDepartment cDepartment2 = cDepartmentService.getCDepartmentById(pId);
			if(StringUtils.isNotBlank(cDepartment2.getParentIds())){
				cDepartment3.setParentIds(cDepartment2.getParentIds()+","+cDepartment.getId());
			}else{
				cDepartment3.setParentIds(cDepartment2.getId()+","+cDepartment.getId());
			}
			cDepartment3.setId(cDepartment.getId());
			cDepartmentService.updateCDepartmentById(cDepartment3);
			//加入rides
			CDepartment cDepartment4 = cDepartmentService.getCDepartmentById(pId);
			redisCacheClient.set(cDepartment4.getCode(), cDepartment4);
			writeSuccessMsg("成功", id, response);
		} catch (Exception e)
		{
			e.printStackTrace();
			writeErrorMsg("系统异常", null, response);
		}
		return null;
	}
	
	@Permission(value = "/cDepartment/updateCDepartment")
	@RequestMapping(value = "/toUpdateLevelCourse", method = RequestMethod.GET)
	public String toUpdateLevelCourse(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		Integer level = RequestHandler.getInteger(request, "level");
		String pId = RequestHandler.getString(request, "pId");
		String id = RequestHandler.getString(request, "id");
		CDepartment cDepartment = cDepartmentService.getCDepartmentById(id);
		model.addAttribute("cDepartment", cDepartment);
		model.addAttribute("pId", pId);
		model.addAttribute("level", level);
		return "/cDepartment/updateLevelDepartment";
	}
	
	/**
	 * 校验机构编号是否重复
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkDepartmentCode", method = RequestMethod.GET)
	public String checkDepartmentCode(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String id = RequestHandler.getString(request, "id");
		String code = RequestHandler.getString(request, "code");
		CDepartment cDepartment = new CDepartment();
		if(StringUtils.isNotBlank(id)){
			cDepartment.setId(id);
		}
		cDepartment.setCode(code);
		int count = cDepartmentService.checkDepartmentCode(cDepartment);
		if(count==0){
			writeSuccessMsg("成功", id, response);
		}else{
			writeErrorMsg("机构编号重复", null, response);
		}
		return null;
	}
	
	@Permission(value = "/manageAdminUser/importManageAdminUser")
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public String downloadExcel(HttpServletRequest request, HttpServletResponse response, Model model)
	{
	
		try{
			String basePath = request.getSession().getServletContext().getRealPath("/");
			model.addAttribute("name", "paperImportTemplate.xls");
			model.addAttribute("link", basePath + "/upload/userImportTemplate.xls");
		}catch(Exception e){
			writeSuccessMsg("nodata", null, response);
			e.printStackTrace();
		}
		return "/common/excelup";
	}
}
