package com.comp.cPaper.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.alibaba.fastjson.JSONObject;
import com.base.perm.Permission;
import com.comp.cDepartment.model.CDepartment;
import com.comp.cDepartment.service.CDepartmentService;
import com.comp.cPaper.model.CPaper;
import com.comp.cPaper.service.CPaperService;
import com.comp.cPaper.service.CPaperServiceImpl;
import com.comp.cPaperDepartment.model.CPaperDepartment;
import com.comp.cPaperDepartment.service.CPaperDepartmentService;
import com.comp.cPaperQuestion.model.CPaperQuestion;
import com.comp.cPaperQuestion.service.CPaperQuestionService;
import com.comp.cQuestion.model.CQuestion;
import com.comp.cQuestion.service.CQuestionService;
import com.comp.cUserPaper.model.CUserPaper;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-03-23 09:21:18
 */
@Controller
@RequestMapping("/cPaper")
public class CPaperController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(CPaperController.class); //Logger
	
	@Autowired
	private CPaperService cPaperService = null;
	@Autowired
	private CPaperDepartmentService cPaperDepartmentService = null;
	@Autowired
	private CDepartmentService cDepartmentService = null;
	@Autowired
	private CQuestionService cQuestionService = null;
	@Autowired
	private CPaperQuestionService cPaperQuestionService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cPaper/cPaperIndex";
	}
	@Permission(value = "/cPaper/addCPaper")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/cPaper/cPaperAdd";
	}
	@Permission(value = "/cPaper/updateCPaper")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String id)
	{	
		CPaper cPaper = cPaperService.getCPaperById(id);
		
		CPaperDepartment cPaperDepartment = new CPaperDepartment();
		cPaperDepartment.setPaperId(id);
		List<CPaperDepartment> list = cPaperDepartmentService.getCPaperDepartmentList(cPaperDepartment);
		String departmentId = null;
		if(list!=null&&list.size()>0){
			for(CPaperDepartment pd:list){
				if(departmentId==null){
					departmentId = pd.getDepartmentId();
				}else{
					departmentId = departmentId + "," + pd.getDepartmentId();
				}
			}
		}
		
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("cPaper", cPaper);
		return "/cPaper/cPaperUpdate";
	}
	@Permission(value = "/cPaper/getCPaperList")
	@RequestMapping(value = "/getCPaperList", method = RequestMethod.GET)
	public void getCPaperList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		
		ManageAdminUser manageadminuser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		
		CPaper cPaper = requst2Bean(request,CPaper.class);
		
		if(manageadminuser.getRole_id().intValue()!=Integer.valueOf(ConfigConstants.SUPER_MANAGE_ROLE).intValue()){
			cPaper.setCreateDepartmentIds(manageadminuser.getDepartmentIds());
		}
		
		PageHelper.offsetPage(cPaper.getOffset(), cPaper.getLimit());
		if(cPaper.getSort() != null && cPaper.getOrder() != null){
			PageHelper.orderBy(cPaper.getSort() +" "+ cPaper.getOrder());
		}			
		List<CPaper> cPaperList = cPaperService.getCPaperList(cPaper);
		PageInfo<CPaper> pageInfo = new PageInfo<CPaper>(cPaperList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/cPaper/addCPaper")
	@RequestMapping(value = "/addCPaper", method = RequestMethod.POST)
	public void addCPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ManageAdminUser manageadminuser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		String departmentId = RequestHandler.getString(request, "departmentId");
		String endTime = RequestHandler.getString(request, "endTime");
		String startTime = RequestHandler.getString(request, "startTime");
		Integer state = RequestHandler.getInteger(request, "state");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		CPaper cPaper = requst2Bean(request,CPaper.class);
		cPaper.setState(state);
		try {
			cPaper.setStartTime(sf.parse(startTime));
			cPaper.setEndTime(sf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		cPaper.setCreateDepartmentIds(manageadminuser.getDepartmentIds());
		cPaper.setCreateTime(new Date());
		cPaperService.insertCPaper(cPaper);
		if(StringUtils.isNotBlank(cPaper.getId())){
			String[] departmentIds = departmentId.split(",");
			for(String str:departmentIds){
				CPaperDepartment cPaperDepartment = new CPaperDepartment();
				cPaperDepartment.setPaperId(cPaper.getId());
				cPaperDepartment.setDepartmentId(str);
				cPaperDepartmentService.insertCPaperDepartment(cPaperDepartment);
			}
		}
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/cPaper/updateCPaper")
	@RequestMapping(value = "/updateCPaper", method = RequestMethod.POST)
	public void updateCPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String departmentId = RequestHandler.getString(request, "departmentId");
		String endTime = RequestHandler.getString(request, "endTime");
		String startTime = RequestHandler.getString(request, "startTime");
		Integer state = RequestHandler.getInteger(request, "state");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		
		CPaper cPaper = requst2Bean(request,CPaper.class);
		
		cPaper.setState(state);
		try {
			cPaper.setStartTime(sf.parse(startTime));
			cPaper.setEndTime(sf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		int count = cPaperService.updateCPaperById(cPaper);
		if(count == 1){
			//删除原来的
			cPaperDepartmentService.deleteCPaperDepartmentByPaperId(cPaper.getId());
			String[] departmentIds = departmentId.split(",");
			for(String str:departmentIds){
				CPaperDepartment cPaperDepartment = new CPaperDepartment();
				cPaperDepartment.setPaperId(cPaper.getId());
				cPaperDepartment.setDepartmentId(str);
				cPaperDepartmentService.insertCPaperDepartment(cPaperDepartment);
			}
			
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/cPaper/removeCPaper")
	@RequestMapping(value = "/removeCPaper", method = RequestMethod.POST)
	public void removeCPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String id = RequestHandler.getString(request, "id");
		cPaperService.deleteCPaperById(id);
		cPaperDepartmentService.deleteCPaperDepartmentByPaperId(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/cPaper/sendQuestion")
	@RequestMapping(value = "/sendQuestion", method = RequestMethod.POST)
	public void sendQuestion(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		CPaper cPaper = requst2Bean(request,CPaper.class);
		cPaperService.updateCPaperById(cPaper);
		if(cPaper.getState().intValue()==1){
			writeSuccessMsg("发送成功", null, response);
		}else if(cPaper.getState().intValue()==2){
			writeSuccessMsg("回收成功", null, response);
		}
	}
	@Permission(value = "/cPaper/removeAllCPaper")
	@RequestMapping(value = "/removeAllCPaper", method = RequestMethod.POST)
	public void removeAllCPaper(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<String> list = JSONArray.parseArray(ids, String.class);
			if(list != null){
				for (String id : list) {
					cPaperService.deleteCPaperById(id);
					cPaperDepartmentService.deleteCPaperDepartmentByPaperId(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
	
	@Permission(value = "/cDepartment/getRootNode")
	@RequestMapping(value = "/getRootNode", method = RequestMethod.GET)
	public String getRootNode(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String paperId = RequestHandler.getString(request, "paperId");
		JSONArray array = new JSONArray(); 
		try{
			
			CPaperDepartment cPaperDepartment = new CPaperDepartment();
			cPaperDepartment.setPaperId(paperId);
			List<CPaperDepartment> list = cPaperDepartmentService.getCPaperDepartmentList(cPaperDepartment);
			
			CDepartment cDepartment = requst2Bean(request,CDepartment.class);
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
	                if(list!=null&&list.size()>0){
	        			for(CPaperDepartment pd:list){
	        				if(cc.getId().equals(pd.getDepartmentId())){
	        					jsonObject.put("checked", true);
	        					break;
	        				}
	        			}
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
	
	@Permission(value = "/cQuestion/addCQuestion")
	@RequestMapping(value = "/toAddQuestion/{paperId}", method = RequestMethod.GET)
	public String toAddQuestion(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String paperId)
	{
		
		CPaper cPaper = cPaperService.getCPaperById(paperId);
		model.addAttribute("cPaper", cPaper);
		return "/cPaper/cQuestionIndex";
	}
	@Permission(value = "/cUserPaper/getCUserPaperList")
	@RequestMapping(value = "/userAnswerList/{paperId}", method = RequestMethod.GET)
	public String userAnswerList(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String paperId)
	{
		
		CPaper cPaper = cPaperService.getCPaperById(paperId);
		model.addAttribute("cPaper", cPaper);
		
		return "/cPaper/cUserPaperIndex";
	}
	@Permission(value = "/cPaper/exportQuestion")
	@RequestMapping(value = "/toExportQuestion/{paperId}", method = RequestMethod.GET)
	public String toExportQuestion(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String paperId)
	{
		
		CPaper cPaper = cPaperService.getCPaperById(paperId);
		model.addAttribute("cPaper", cPaper);
		return "/cPaper/exportQuestion";
	}
	@Permission(value = "/cPaper/exportQuestion")
	@RequestMapping(value = "/exportQuestion", method = RequestMethod.POST)
	public String exportQuestion(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String filePath = RequestHandler.getString(request, "filePath");
		String paperId = RequestHandler.getString(request, "paperId");
		InputStream fis = null;
		try{
			
			String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
			
			fis = new FileInputStream(new File(saveFilePath + filePath));
			POIFSFileSystem fileSystem = new POIFSFileSystem(fis);
			fis.close();
			Workbook book = new HSSFWorkbook(fileSystem);
			//检测excel信息
			JSONObject json = cPaperService.checkExcel(book);
			StringBuilder allMsg = (StringBuilder)json.get("allMsg"); // 错误消息
			if (allMsg.length() > 0) { // 如果有错误消息，则返回
				writeErrorMsg(allMsg.toString(), null, response);
			} else { // 如果没有错误，则插入数据库
				int i = 1;
				List<CQuestion> list = (List<CQuestion>)json.get("result");
				for(CQuestion q:list){
					cQuestionService.insertCQuestion(q);
					CPaperQuestion cPaperQuestion = new CPaperQuestion();
					cPaperQuestion.setPaperId(paperId);
					cPaperQuestion.setQuestionId(q.getId());
					cPaperQuestion.setSortId(i++);
					cPaperQuestionService.insertCPaperQuestion(cPaperQuestion);
				}
				CPaperQuestion cPaperQuestion = new CPaperQuestion();
				cPaperQuestion.setPaperId(paperId);
				int count = cPaperQuestionService.getCPaperQuestionCount(cPaperQuestion);
				CPaper cPaper = new CPaper();
				cPaper.setId(paperId);
				cPaper.setQuestionCount(count);
				cPaperService.updateCPaperById(cPaper);
				writeSuccessMsg("导入成功", null, response);
			}
		}catch(Exception e){
			if (fis != null) {// 关闭流
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			writeErrorMsg("导入失败，系统异常", null, response);
			e.printStackTrace();
		}
		return "/cPaper/exportQuestion";
	}
	
	@Permission(value = "/cPaper/exportQuestion")
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public String downloadExcel(HttpServletRequest request, HttpServletResponse response, Model model)
	{
	
		try{
			String basePath = request.getSession().getServletContext().getRealPath("/");
			model.addAttribute("name", "paperImportTemplate.xls");
			model.addAttribute("link", basePath + "/upload/paperImportTemplate.xls");
		}catch(Exception e){
			writeSuccessMsg("nodata", null, response);
			e.printStackTrace();
		}
		return "/common/excelup";
	}
}
