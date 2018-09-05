package com.comp.cPaperDepartment.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.base.perm.Permission;
import com.comp.cPaperDepartment.model.CPaperDepartment;
import com.comp.cPaperDepartment.service.CPaperDepartmentService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-23 09:54:58
 */
@Controller
@RequestMapping("/cPaperDepartment")
public class CPaperDepartmentController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CPaperDepartmentController.class); //Logger
	
	@Autowired
	private CPaperDepartmentService cPaperDepartmentService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cPaperDepartment/cPaperDepartmentIndex";
	}
	@Permission(value = "/cPaperDepartment/addCPaperDepartment")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cPaperDepartment/cPaperDepartmentAdd";
	}
	@Permission(value = "/cPaperDepartment/updateCPaperDepartment")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		CPaperDepartment cPaperDepartment = cPaperDepartmentService.getCPaperDepartmentById(id);
		model.addAttribute("cPaperDepartment", cPaperDepartment);
		return "/cPaperDepartment/cPaperDepartmentUpdate";
	}
	@Permission(value = "/cPaperDepartment/getCPaperDepartmentList")
	@RequestMapping(value = "/getCPaperDepartmentList", method = RequestMethod.GET)
	public void getCPaperDepartmentList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaperDepartment cPaperDepartment = requst2Bean(request,CPaperDepartment.class);
		PageHelper.offsetPage(cPaperDepartment.getOffset(), cPaperDepartment.getLimit());
		if(cPaperDepartment.getSort() != null && cPaperDepartment.getOrder() != null){
			PageHelper.orderBy(cPaperDepartment.getSort() +" "+ cPaperDepartment.getOrder());
		}			
		List<CPaperDepartment> cPaperDepartmentList = cPaperDepartmentService.getCPaperDepartmentList(cPaperDepartment);
		PageInfo<CPaperDepartment> pageInfo = new PageInfo<CPaperDepartment>(cPaperDepartmentList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cPaperDepartment/addCPaperDepartment")
	@RequestMapping(value = "/addCPaperDepartment", method = RequestMethod.POST)
	public void addCPaperDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaperDepartment cPaperDepartment = requst2Bean(request,CPaperDepartment.class);
		cPaperDepartmentService.insertCPaperDepartment(cPaperDepartment);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cPaperDepartment/updateCPaperDepartment")
	@RequestMapping(value = "/updateCPaperDepartment", method = RequestMethod.POST)
	public void updateCPaperDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaperDepartment cPaperDepartment = requst2Bean(request,CPaperDepartment.class);
		int count = cPaperDepartmentService.updateCPaperDepartmentById(cPaperDepartment);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cPaperDepartment/removeCPaperDepartment")
	@RequestMapping(value = "/removeCPaperDepartment", method = RequestMethod.POST)
	public void removeCPaperDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		cPaperDepartmentService.deleteCPaperDepartmentById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cPaperDepartment/removeAllCPaperDepartment")
	@RequestMapping(value = "/removeAllCPaperDepartment", method = RequestMethod.POST)
	public void removeAllCPaperDepartment(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					cPaperDepartmentService.deleteCPaperDepartmentById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
