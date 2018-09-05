package com.comp.cUserPaper.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sys.manageAdminUser.model.ManageAdminUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.base.perm.Permission;
import com.comp.cUserPaper.model.CUserPaper;
import com.comp.cUserPaper.service.CUserPaperService;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-30 10:45:22
 */
@Controller
@RequestMapping("/cUserPaper")
public class CUserPaperController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CUserPaperController.class); //Logger
	
	@Autowired
	private CUserPaperService cUserPaperService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cUserPaper/cUserPaperIndex";
	}
	@Permission(value = "/cUserPaper/addCUserPaper")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cUserPaper/cUserPaperAdd";
	}
	@Permission(value = "/cUserPaper/updateCUserPaper")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		CUserPaper cUserPaper = cUserPaperService.getCUserPaperById(id);
		model.addAttribute("cUserPaper", cUserPaper);
		return "/cUserPaper/cUserPaperUpdate";
	}
	@Permission(value = "/cUserPaper/getCUserPaperList")
	@RequestMapping(value = "/getCUserPaperList", method = RequestMethod.GET)
	public void getCUserPaperList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CUserPaper cUserPaper = requst2Bean(request,CUserPaper.class);
		PageHelper.offsetPage(cUserPaper.getOffset(), cUserPaper.getLimit());
		if(cUserPaper.getSort() != null && cUserPaper.getOrder() != null){
			PageHelper.orderBy(cUserPaper.getSort() +" "+ cUserPaper.getOrder());
		}			
		
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		if(StringUtils.isNotBlank(adminUser.getDepartmentIds())){
			cUserPaper.setDepartmentIds(adminUser.getDepartmentIds());
		}
		
		List<CUserPaper> cUserPaperList = cUserPaperService.getCUserPaperListByDepartment(cUserPaper);
		PageInfo<CUserPaper> pageInfo = new PageInfo<CUserPaper>(cUserPaperList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cUserPaper/addCUserPaper")
	@RequestMapping(value = "/addCUserPaper", method = RequestMethod.POST)
	public void addCUserPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CUserPaper cUserPaper = requst2Bean(request,CUserPaper.class);
		cUserPaper.setCreateTime(new Date());
		cUserPaperService.insertCUserPaper(cUserPaper);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cUserPaper/updateCUserPaper")
	@RequestMapping(value = "/updateCUserPaper", method = RequestMethod.POST)
	public void updateCUserPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CUserPaper cUserPaper = requst2Bean(request,CUserPaper.class);
		int count = cUserPaperService.updateCUserPaperById(cUserPaper);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cUserPaper/removeCUserPaper")
	@RequestMapping(value = "/removeCUserPaper", method = RequestMethod.POST)
	public void removeCUserPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		cUserPaperService.deleteCUserPaperById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cUserPaper/removeAllCUserPaper")
	@RequestMapping(value = "/removeAllCUserPaper", method = RequestMethod.POST)
	public void removeAllCUserPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					cUserPaperService.deleteCUserPaperById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
