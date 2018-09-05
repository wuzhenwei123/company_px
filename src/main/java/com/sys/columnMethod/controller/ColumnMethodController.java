package com.sys.columnMethod.controller;

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
import com.sys.columnMethod.model.ColumnMethod;
import com.sys.columnMethod.service.ColumnMethodService;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	keeny
 * @time	2017-04-08 15:38:24
 */
@Controller
@RequestMapping("/columnMethod")
public class ColumnMethodController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(ColumnMethodController.class); //Logger
	
	@Autowired
	private ColumnMethodService columnMethodService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/sys/columnMethod/columnMethodIndex";
	}
	@Permission(value = "/columnMethod/addColumnMethod")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		Integer columnId = RequestHandler.getInteger(request, "columnId");
		model.addAttribute("columnId", columnId);
		return "/sys/columnMethod/columnMethodAdd";
	}
	@Permission(value = "/columnMethod/updateColumnMethod")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model)
	{	
		ColumnMethod columnMethod1 = requst2Bean(request,ColumnMethod.class);
		ColumnMethod columnMethod = columnMethodService.getColumnMethod(columnMethod1);
		model.addAttribute("columnMethod", columnMethod);
		return "/sys/columnMethod/columnMethodUpdate";
	}
	@Permission(value = "/columnMethod/getColumnMethodList")
	@RequestMapping(value = "/getColumnMethodList", method = RequestMethod.GET)
	public void getColumnMethodList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ColumnMethod columnMethod = requst2Bean(request,ColumnMethod.class);
		int columnMethodListCount = columnMethodService.getColumnMethodListCount(columnMethod);
		ResponseList<ColumnMethod> columnMethodList = null;
		if ( columnMethodListCount > 0 )
		{
			columnMethodList = columnMethodService.getColumnMethodList(columnMethod);
		} else
		{
			columnMethodList = new ResponseList<ColumnMethod>();
		}
		// 设置数据总数
		columnMethodList.setTotalResults(columnMethodListCount);
		
		writeSuccessMsg("ok", columnMethodList, response);
	}
	@Permission(value = "/columnMethod/addColumnMethod")
	@RequestMapping(value = "/addColumnMethod", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ColumnMethod columnMethod = requst2Bean(request,ColumnMethod.class);
		columnMethod.setCreateTime(new Date());
		columnMethodService.insertColumnMethod(columnMethod);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/columnMethod/updateColumnMethod")
	@RequestMapping(value = "/updateColumnMethod", method = RequestMethod.POST)
	public void updateColumnMethod(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ColumnMethod columnMethod = requst2Bean(request,ColumnMethod.class);
		int count = columnMethodService.updateColumnMethod(columnMethod);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/columnMethod/removeColumnMethod")
	@RequestMapping(value = "/removeColumnMethod", method = RequestMethod.POST)
	public void removeColumnMethod(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ColumnMethod columnMethod = new ColumnMethod();
		Integer mid = RequestHandler.getInteger(request, "mid");
		columnMethod.setMid(mid);

		columnMethodService.removeColumnMethod(columnMethod);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/columnMethod/removeAllColumnMethod")
	@RequestMapping(value = "/removeAllColumnMethod", method = RequestMethod.POST)
	public void removeAllColumnMethod(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String mids = RequestHandler.getString(request, "mids");
		if(mids != null){
			List<Integer> list = JSONArray.parseArray(mids, Integer.class);
			if(list != null){
				for (Integer mid : list) {
					ColumnMethod columnMethod = new ColumnMethod();
					columnMethod.setMid(mid);
					columnMethodService.removeColumnMethod(columnMethod);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
