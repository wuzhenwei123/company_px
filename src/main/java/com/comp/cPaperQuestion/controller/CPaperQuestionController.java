package com.comp.cPaperQuestion.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sys.manageColumn.model.ManageColumn;

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
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-23 11:36:52
 */
@Controller
@RequestMapping("/cPaperQuestion")
public class CPaperQuestionController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CPaperQuestionController.class); //Logger
	
	@Autowired
	private CPaperQuestionService cPaperQuestionService = null;
	@Autowired
	private CPaperService cPaperService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cPaperQuestion/cPaperQuestionIndex";
	}
	@Permission(value = "/cPaperQuestion/addCPaperQuestion")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cPaperQuestion/cPaperQuestionAdd";
	}
	@Permission(value = "/cPaperQuestion/updateCPaperQuestion")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		CPaperQuestion cPaperQuestion = cPaperQuestionService.getCPaperQuestionById(id);
		model.addAttribute("cPaperQuestion", cPaperQuestion);
		return "/cPaperQuestion/cPaperQuestionUpdate";
	}
	@Permission(value = "/cPaperQuestion/getCPaperQuestionList")
	@RequestMapping(value = "/getCPaperQuestionList", method = RequestMethod.GET)
	public void getCPaperQuestionList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaperQuestion cPaperQuestion = requst2Bean(request,CPaperQuestion.class);
		PageHelper.offsetPage(cPaperQuestion.getOffset(), cPaperQuestion.getLimit());
		if(cPaperQuestion.getSort() != null && cPaperQuestion.getOrder() != null){
			PageHelper.orderBy(cPaperQuestion.getSort() +" "+ cPaperQuestion.getOrder());
		}			
		List<CPaperQuestion> cPaperQuestionList = cPaperQuestionService.getCPaperQuestionList(cPaperQuestion);
		PageInfo<CPaperQuestion> pageInfo = new PageInfo<CPaperQuestion>(cPaperQuestionList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cPaperQuestion/addCPaperQuestion")
	@RequestMapping(value = "/addCPaperQuestion", method = RequestMethod.POST)
	public void addCPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaperQuestion cPaperQuestion = requst2Bean(request,CPaperQuestion.class);
		cPaperQuestionService.insertCPaperQuestion(cPaperQuestion);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cPaperQuestion/updateCPaperQuestion")
	@RequestMapping(value = "/updateCPaperQuestion", method = RequestMethod.POST)
	public void updateCPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaperQuestion cPaperQuestion = requst2Bean(request,CPaperQuestion.class);
		int count = cPaperQuestionService.updateCPaperQuestionById(cPaperQuestion);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cPaperQuestion/removeCPaperQuestion")
	@RequestMapping(value = "/removeCPaperQuestion", method = RequestMethod.POST)
	public void removeCPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		String paperId = RequestHandler.getString(request, "paperId");
		cPaperQuestionService.deleteCPaperQuestionById(id);
		CPaperQuestion cPaperQuestion = new CPaperQuestion();
		cPaperQuestion.setPaperId(paperId);
		int count = cPaperQuestionService.getCPaperQuestionCount(cPaperQuestion);
		CPaper cPaper = new CPaper();
		cPaper.setId(paperId);
		cPaper.setQuestionCount(count);
		cPaperService.updateCPaperById(cPaper);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cPaperQuestion/removeAllCPaperQuestion")
	@RequestMapping(value = "/removeAllCPaperQuestion", method = RequestMethod.POST)
	public void removeAllCPaperQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					cPaperQuestionService.deleteCPaperQuestionById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
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
		String sign = RequestHandler.getString(request, "sign");
		String paperId = RequestHandler.getString(request, "paperId");
		Long id = RequestHandler.getLong(request, "id");// 当前ID

		CPaperQuestion cPaperQuestion = new CPaperQuestion();
		cPaperQuestion.setPaperId(paperId);
		cPaperQuestion.setSort("sortId");
		cPaperQuestion.setOrder("asc");
		List<CPaperQuestion> list = cPaperQuestionService.getCPaperQuestionListBySortId(cPaperQuestion);

		CPaperQuestion column = null;// 交换对象
		CPaperQuestion currentColumn = null;// 当前对象
		int currentIndex = 0;// 当前对象索引
		for (int i = 0; i < list.size(); i++) {
			CPaperQuestion column1 = list.get(i);
			if (column1.getId() == id.intValue()) {
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
			CPaperQuestion column2 = new CPaperQuestion();
			column2.setId(column.getId());
			column2.setSortId(currentColumn.getSortId());
			cPaperQuestionService.updateCPaperQuestionById(column2);// 更新序号

			column2 = new CPaperQuestion();
			column2.setId(currentColumn.getId());
			column2.setSortId(column.getSortId());
			cPaperQuestionService.updateCPaperQuestionById(column2);// 更新序号
		}

		writeSuccessMsg("ok", null, response);
	}
}
