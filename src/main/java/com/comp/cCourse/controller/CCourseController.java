package com.comp.cCourse.controller;

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
import com.comp.cCourse.model.CCourse;
import com.comp.cCourse.service.CCourseService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-04-03 09:47:07
 */
@Controller
@RequestMapping("/cCourse")
public class CCourseController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CCourseController.class); //Logger
	
	@Autowired
	private CCourseService cCourseService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cCourse/cCourseIndex";
	}
	@Permission(value = "/cCourse/addCCourse")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cCourse/cCourseAdd";
	}
	@Permission(value = "/cCourse/updateCCourse")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		CCourse cCourse = cCourseService.getCCourseById(id);
		model.addAttribute("cCourse", cCourse);
		return "/cCourse/cCourseUpdate";
	}
	@Permission(value = "/cCourse/getCCourseList")
	@RequestMapping(value = "/getCCourseList", method = RequestMethod.GET)
	public void getCCourseList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CCourse cCourse = requst2Bean(request,CCourse.class);
		PageHelper.offsetPage(cCourse.getOffset(), cCourse.getLimit());
		if(cCourse.getSort() != null && cCourse.getOrder() != null){
			PageHelper.orderBy(cCourse.getSort() +" "+ cCourse.getOrder());
		}			
		List<CCourse> cCourseList = cCourseService.getCCourseList(cCourse);
		PageInfo<CCourse> pageInfo = new PageInfo<CCourse>(cCourseList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cCourse/addCCourse")
	@RequestMapping(value = "/addCCourse", method = RequestMethod.POST)
	public void addCCourse(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CCourse cCourse = requst2Bean(request,CCourse.class);
		cCourse.setCreateTime(new Date());
		cCourseService.insertCCourse(cCourse);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cCourse/updateCCourse")
	@RequestMapping(value = "/updateCCourse", method = RequestMethod.POST)
	public void updateCCourse(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CCourse cCourse = requst2Bean(request,CCourse.class);
		int count = cCourseService.updateCCourseById(cCourse);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cCourse/removeCCourse")
	@RequestMapping(value = "/removeCCourse", method = RequestMethod.POST)
	public void removeCCourse(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		cCourseService.deleteCCourseById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cCourse/removeAllCCourse")
	@RequestMapping(value = "/removeAllCCourse", method = RequestMethod.POST)
	public void removeAllCCourse(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					cCourseService.deleteCCourseById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
