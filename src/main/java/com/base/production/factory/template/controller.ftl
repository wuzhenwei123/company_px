package ${packageName}.${pak};

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
import ${packageName}.model.${domainName?cap_first};
import ${packageName}.service.${domainName?cap_first}Service;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * ${description}
 * @time	${date}
 */
@Controller
@RequestMapping("/${domainName}")
public class ${domainName?cap_first}Controller extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(${domainName?cap_first}Controller.class); //Logger
	
	@Autowired
	private ${domainName?cap_first}Service ${domainName}Service = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/${domainName}/${domainName}Index";
	}
	@Permission(value = "/${domainName}/add${domainName?cap_first}")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/${domainName}/${domainName}Add";
	}
	@Permission(value = "/${domainName}/update${domainName?cap_first}")
	@RequestMapping(value = "/toUpdate/{${pkName}}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable ${pkUPType} ${pkName})
	{	
		${domainName?cap_first} ${domainName} = ${domainName}Service.get${domainName?cap_first}By${pkName?cap_first}(${pkName});
		model.addAttribute("${domainName}", ${domainName});
		return "/${domainName}/${domainName}Update";
	}
	@Permission(value = "/${domainName}/get${domainName?cap_first}List")
	@RequestMapping(value = "/get${domainName?cap_first}List", method = RequestMethod.GET)
	public void get${domainName?cap_first}List(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		${domainName?cap_first} ${domainName} = requst2Bean(request,${domainName?cap_first}.class);
		PageHelper.offsetPage(${domainName}.getOffset(), ${domainName}.getLimit());
		if(${domainName}.getSort() != null && ${domainName}.getOrder() != null){
			PageHelper.orderBy(${domainName}.getSort() +" "+ ${domainName}.getOrder());
		}			
		List<${domainName?cap_first}> ${domainName}List = ${domainName}Service.get${domainName?cap_first}List(${domainName});
		PageInfo<${domainName?cap_first}> pageInfo = new PageInfo<${domainName?cap_first}>(${domainName}List);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/${domainName}/add${domainName?cap_first}")
	@RequestMapping(value = "/add${domainName?cap_first}", method = RequestMethod.POST)
	public void add${domainName?cap_first}(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		${domainName?cap_first} ${domainName} = requst2Bean(request,${domainName?cap_first}.class);
	<#list columns as item>
		<#if item.name == 'createTime'>
		${domainName}.set${item.name?cap_first}(new Date());
    	</#if>
	</#list>
		${domainName}Service.insert${domainName?cap_first}(${domainName});
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/${domainName}/update${domainName?cap_first}")
	@RequestMapping(value = "/update${domainName?cap_first}", method = RequestMethod.POST)
	public void update${domainName?cap_first}(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		${domainName?cap_first} ${domainName} = requst2Bean(request,${domainName?cap_first}.class);
		int count = ${domainName}Service.update${domainName?cap_first}By${pkName?cap_first}(${domainName});
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/${domainName}/remove${domainName?cap_first}")
	@RequestMapping(value = "/remove${domainName?cap_first}", method = RequestMethod.POST)
	public void remove${domainName?cap_first}(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		${pkUPType} ${pkName} = RequestHandler.get${pkUPType}(request, "${pkName}");
		${domainName}Service.delete${domainName?cap_first}By${pkName?cap_first}(${pkName});
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/${domainName}/removeAll${domainName?cap_first}")
	@RequestMapping(value = "/removeAll${domainName?cap_first}", method = RequestMethod.POST)
	public void removeAll${domainName?cap_first}(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ${pkName}s = RequestHandler.getString(request, "${pkName}s");
		if(${pkName}s != null){
			List<${pkUPType}> list = JSONArray.parseArray(${pkName}s, ${pkUPType}.class);
			if(list != null){
				for (${pkUPType} ${pkName} : list) {
					${domainName}Service.delete${domainName?cap_first}By${pkName?cap_first}(${pkName});
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
