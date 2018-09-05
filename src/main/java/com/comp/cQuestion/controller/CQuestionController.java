package com.comp.cQuestion.controller;

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
import com.comp.cPaper.model.CPaper;
import com.comp.cPaper.service.CPaperService;
import com.comp.cPaperQuestion.model.CPaperQuestion;
import com.comp.cPaperQuestion.service.CPaperQuestionService;
import com.comp.cQuestion.model.CQuestion;
import com.comp.cQuestion.service.CQuestionService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-23 11:36:26
 */
@Controller
@RequestMapping("/cQuestion")
public class CQuestionController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CQuestionController.class); //Logger
	
	@Autowired
	private CQuestionService cQuestionService = null;
	@Autowired
	private CPaperService cPaperService = null;
	@Autowired
	private CPaperQuestionService cPaperQuestionService = null;

	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index/{paperId}", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String paperId)
	{
		
		CPaper cPaper = cPaperService.getCPaperById(paperId);
		model.addAttribute("cPaper", cPaper);
		return "/cQuestion/cQuestionIndex";
	}
	@Permission(value = "/cQuestion/addCQuestion")
	@RequestMapping(value = "/toAdd/{paperId}", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String paperId)
	{
		model.addAttribute("paperId", paperId);
		return "/cQuestion/cQuestionAdd";
	}
	@Permission(value = "/cQuestion/updateCQuestion")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String id)
	{	
		CQuestion cQuestion = cQuestionService.getCQuestionById(id);
		model.addAttribute("cQuestion", cQuestion);
		return "/cQuestion/cQuestionUpdate";
	}
	@Permission(value = "/cQuestion/getCQuestionList")
	@RequestMapping(value = "/getCQuestionList", method = RequestMethod.GET)
	public void getCQuestionList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CQuestion cQuestion = requst2Bean(request,CQuestion.class);
		PageHelper.offsetPage(cQuestion.getOffset(), cQuestion.getLimit());
		if(cQuestion.getSort() != null && cQuestion.getOrder() != null){
			PageHelper.orderBy(cQuestion.getSort() +" "+ cQuestion.getOrder());
		}			
		List<CQuestion> cQuestionList = cQuestionService.getCQuestionList(cQuestion);
		PageInfo<CQuestion> pageInfo = new PageInfo<CQuestion>(cQuestionList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cQuestion/addCQuestion")
	@RequestMapping(value = "/addCQuestion", method = RequestMethod.POST)
	public void addCQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String paperId = RequestHandler.getString(request, "paperId");
		CQuestion cQuestion = requst2Bean(request,CQuestion.class);
		long id = cQuestionService.insertCQuestion(cQuestion);
		if(id>0){
			CPaperQuestion cPaperQuestion = new CPaperQuestion();
			cPaperQuestion.setPaperId(paperId);
			int count = cPaperQuestionService.getCPaperQuestionCount(cPaperQuestion);
			cPaperQuestion.setQuestionId(cQuestion.getId());
			cPaperQuestion.setSortId(count+1);
			cPaperQuestionService.insertCPaperQuestion(cPaperQuestion);
			CPaper cPaper = new CPaper();
			cPaper.setId(paperId);
			cPaper.setQuestionCount(count+1);
			cPaperService.updateCPaperById(cPaper);
		}
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cQuestion/updateCQuestion")
	@RequestMapping(value = "/updateCQuestion", method = RequestMethod.POST)
	public void updateCQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CQuestion cQuestion = requst2Bean(request,CQuestion.class);
		int count = cQuestionService.updateCQuestionById(cQuestion);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cQuestion/removeCQuestion")
	@RequestMapping(value = "/removeCQuestion", method = RequestMethod.POST)
	public void removeCQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String id = RequestHandler.getString(request, "id");
		cQuestionService.deleteCQuestionById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cQuestion/removeAllCQuestion")
	@RequestMapping(value = "/removeAllCQuestion", method = RequestMethod.POST)
	public void removeAllCQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<String> list = JSONArray.parseArray(ids, String.class);
			if(list != null){
				for (String id : list) {
					cQuestionService.deleteCQuestionById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
