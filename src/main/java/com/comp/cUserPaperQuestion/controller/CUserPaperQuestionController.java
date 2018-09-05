package com.comp.cUserPaperQuestion.controller;

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
import com.comp.cUserPaperQuestion.model.CUserPaperQuestion;
import com.comp.cUserPaperQuestion.service.CUserPaperQuestionService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-30 10:54:49
 */
@Controller
@RequestMapping("/cUserPaperQuestion")
public class CUserPaperQuestionController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CUserPaperQuestionController.class); //Logger
	
	@Autowired
	private CUserPaperQuestionService cUserPaperQuestionService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cUserPaperQuestion/cUserPaperQuestionIndex";
	}
	@Permission(value = "/cUserPaperQuestion/addCUserPaperQuestion")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cUserPaperQuestion/cUserPaperQuestionAdd";
	}
	@Permission(value = "/cUserPaperQuestion/updateCUserPaperQuestion")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		CUserPaperQuestion cUserPaperQuestion = cUserPaperQuestionService.getCUserPaperQuestionById(id);
		model.addAttribute("cUserPaperQuestion", cUserPaperQuestion);
		return "/cUserPaperQuestion/cUserPaperQuestionUpdate";
	}
	@Permission(value = "/cUserPaperQuestion/getCUserPaperQuestionList")
	@RequestMapping(value = "/getCUserPaperQuestionList", method = RequestMethod.GET)
	public void getCUserPaperQuestionList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CUserPaperQuestion cUserPaperQuestion = requst2Bean(request,CUserPaperQuestion.class);
		PageHelper.offsetPage(cUserPaperQuestion.getOffset(), cUserPaperQuestion.getLimit());
		if(cUserPaperQuestion.getSort() != null && cUserPaperQuestion.getOrder() != null){
			PageHelper.orderBy(cUserPaperQuestion.getSort() +" "+ cUserPaperQuestion.getOrder());
		}			
		List<CUserPaperQuestion> cUserPaperQuestionList = cUserPaperQuestionService.getCUserPaperQuestionList(cUserPaperQuestion);
		PageInfo<CUserPaperQuestion> pageInfo = new PageInfo<CUserPaperQuestion>(cUserPaperQuestionList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cUserPaperQuestion/addCUserPaperQuestion")
	@RequestMapping(value = "/addCUserPaperQuestion", method = RequestMethod.POST)
	public void addCUserPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CUserPaperQuestion cUserPaperQuestion = requst2Bean(request,CUserPaperQuestion.class);
		cUserPaperQuestionService.insertCUserPaperQuestion(cUserPaperQuestion);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cUserPaperQuestion/updateCUserPaperQuestion")
	@RequestMapping(value = "/updateCUserPaperQuestion", method = RequestMethod.POST)
	public void updateCUserPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CUserPaperQuestion cUserPaperQuestion = requst2Bean(request,CUserPaperQuestion.class);
		int count = cUserPaperQuestionService.updateCUserPaperQuestionById(cUserPaperQuestion);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cUserPaperQuestion/removeCUserPaperQuestion")
	@RequestMapping(value = "/removeCUserPaperQuestion", method = RequestMethod.POST)
	public void removeCUserPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		cUserPaperQuestionService.deleteCUserPaperQuestionById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cUserPaperQuestion/removeAllCUserPaperQuestion")
	@RequestMapping(value = "/removeAllCUserPaperQuestion", method = RequestMethod.POST)
	public void removeAllCUserPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					cUserPaperQuestionService.deleteCUserPaperQuestionById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
