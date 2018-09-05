package com.sys.manageAdminUser.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.adminRoleMethod.service.AdminRoleMethodService;
import com.sys.manageAdminRole.dao.ManageAdminRoleDao;
import com.sys.manageAdminRole.model.ManageAdminRole;
import com.sys.manageAdminRoleColumn.dao.ManageAdminRoleColumnDao;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageAdminRoleColumn.service.ManageAdminRoleColumnService;
import com.sys.manageAdminUser.dao.ManageAdminUserDao;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.MD5;
import com.base.utils.RedisCacheClient;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;
import com.comp.cDepartment.model.CDepartment;
import com.comp.cPaper.service.ExcelUtil;
import com.comp.cQuestion.model.CQuestion;

/**
 * @author	keeny
 * @time	2017-03-23 14:16:40
 */
@Service
public class ManageAdminUserServiceImpl implements ManageAdminUserService{
	@Resource
    private ManageAdminUserDao manageAdminUserDao;
	@Resource
    private ManageAdminRoleDao manageAdminRoleDao;//角色
	@Resource
    private ManageAdminRoleColumnDao manageAdminRoleColumnDao;//角色菜单权限
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;//角色权限
	@Autowired
	private RedisCacheClient redisCacheClient;
	
	public void login(HttpSession session, String adminName) {
		ManageAdminUser manageadminuser1 = new ManageAdminUser();
		manageadminuser1.setAdminName(adminName);
		ManageAdminUser manageadminuser = manageAdminUserDao.getManageAdminUser(manageadminuser1);		
		if(manageadminuser!=null){
			Integer role_id = manageadminuser.getRole_id();
			
			if(role_id!=null){
				ManageAdminRole manageAdminRole = new ManageAdminRole();
				manageAdminRole.setRoleId(role_id);
				ManageAdminRole manageAdminRole2 = manageAdminRoleDao.getManageAdminRole(manageAdminRole);
				manageadminuser.setRoleName(manageAdminRole2.getRoleName());
			}
			initLoginSession(session,manageadminuser);
			
			manageadminuser.setLastLogin(new Date());
			manageAdminUserDao.updateManageAdminUser(manageadminuser);
			
			this.initColumn(session);
			this.initOp(session);
		}
	}
	public void initLoginSession(HttpSession session,ManageAdminUser manageadminuser){
		session.setAttribute(SessionName.ADMIN_USER_ID, manageadminuser.getAdminId());
		session.setAttribute(SessionName.ADMIN_USER_NAME, manageadminuser.getAdminName());
		session.setAttribute(SessionName.ADMIN_USER, manageadminuser);
	}
	@Override
	public void initColumn(HttpSession session) {
		Object userObj = session.getAttribute(SessionName.ADMIN_USER);
		if(userObj!=null){
			ManageAdminUser manageadminuser = (ManageAdminUser) userObj;
			if(manageadminuser.getRole_id()!=null){
				ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
				manageAdminRoleColumn.setRoleId(manageadminuser.getRole_id());
				manageAdminRoleColumn.setUse_state(1);//已分配的
				manageAdminRoleColumn.setOrder("asc");
				manageAdminRoleColumn.setSort("sort_id");
				List<ManageAdminRoleColumn> manageAdminRoleColumnBaseList = manageAdminRoleColumnDao.getManageAdminRoleColumnBaseList(manageAdminRoleColumn);
				
				if(manageAdminRoleColumnBaseList!=null){
					List<ManageAdminRoleColumn> childColumnList = new ArrayList<ManageAdminRoleColumn>();
					List<ManageAdminRoleColumn> parentColumnList = new ArrayList<ManageAdminRoleColumn>();
					
					// 设置首页入口
					ManageAdminRoleColumn mainColummn = new ManageAdminRoleColumn();
					mainColummn.setParentColumnID(-1);
					mainColummn.setColumnId(-11);
					mainColummn.setColumnName("首页");
					mainColummn.setColumnImg("glyphicon glyphicon-home");
					ManageAdminRoleColumn mainChildColumn = new ManageAdminRoleColumn();
					mainChildColumn.setColumnName("首页");
					mainChildColumn.setColumnUrl("/main");
					mainChildColumn.setColumnId(0);
					mainChildColumn.setParentColumnID(mainColummn.getColumnId());
					List<ManageAdminRoleColumn> mainChildList = new ArrayList<>();
					mainChildList.add(mainChildColumn);
					mainColummn.setChildList(mainChildList);
					parentColumnList.add(mainColummn);
					// 设置首页入口
					
					for (ManageAdminRoleColumn manageAdminRoleColumn2 : manageAdminRoleColumnBaseList) {
						if(manageAdminRoleColumn2.getParentColumnID()!=null && manageAdminRoleColumn2.getParentColumnID() == -1){
							parentColumnList.add(manageAdminRoleColumn2);
						}else{
							childColumnList.add(manageAdminRoleColumn2);
						}
					}
					for (ManageAdminRoleColumn parentColumn : parentColumnList) {
						List<ManageAdminRoleColumn> childList = parentColumn.getChildList();
						if(childList == null)
							childList = new ArrayList<ManageAdminRoleColumn>();
						for (ManageAdminRoleColumn childColumn : childColumnList) {
							Integer parentColumnID = childColumn.getParentColumnID();
							if(parentColumnID!=null && parentColumnID.intValue() == parentColumn.getColumnId()){
								childList.add(childColumn);
							}
						}
						parentColumn.setChildList(childList);
					}
					session.setAttribute(SessionName.SYSTEM_COLUMN_LIST, parentColumnList);//系统菜单
				}
			}
		}
	}
	@Override
	public void initOp(HttpSession session) {
		Object userObj = session.getAttribute(SessionName.ADMIN_USER);
		if(userObj!=null){
			ManageAdminUser manageadminuser = (ManageAdminUser) userObj;
			AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
			adminRoleMethod.setRoleId(manageadminuser.getRole_id());
			List<AdminRoleMethod> adminRoleMethodBaseList = adminRoleMethodService.getAdminRoleMethodBaseList(adminRoleMethod);
			session.setAttribute(SessionName.USER_ROLE_LIST, adminRoleMethodBaseList);//权限列表
		}
	}
    public ResponseList<ManageAdminUser> getManageAdminUserList(ManageAdminUser manageAdminUser) {
        return manageAdminUserDao.getManageAdminUserList(manageAdminUser);
    }
    
    public List<ManageAdminUser> getManageAdminUserBaseList(ManageAdminUser manageAdminUser) {
        return manageAdminUserDao.getManageAdminUserBaseList(manageAdminUser);
    }
    
    public int getManageAdminUserListCount(ManageAdminUser manageAdminUser) {
        return manageAdminUserDao.getManageAdminUserListCount(manageAdminUser);
    }

    public ManageAdminUser getManageAdminUser(ManageAdminUser manageAdminUser) { 
        return manageAdminUserDao.getManageAdminUser(manageAdminUser);
    }

    public int insertManageAdminUser(ManageAdminUser manageAdminUser){
        return manageAdminUserDao.insertManageAdminUser(manageAdminUser);
    }
    
    public int checkAdminName(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.checkAdminName(manageAdminUser);
    }

    public int updateManageAdminUser(ManageAdminUser manageAdminUser){
        return manageAdminUserDao.updateManageAdminUser(manageAdminUser);
    }
    
    public int unBindWx(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.unBindWx(manageAdminUser);
    }
    
    public int updateDefaultDB(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.updateDefaultDB(manageAdminUser);
    }
    
    public int updateManageAdminUserByOpenId(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.updateManageAdminUserByOpenId(manageAdminUser);
    }
    
    public int removeManageAdminUser(ManageAdminUser manageAdminUser){
        return manageAdminUserDao.removeManageAdminUser(manageAdminUser);
    }
    
    public ManageAdminUser getManageAdminUserById(Integer adminId){
    	return manageAdminUserDao.getManageAdminUserById(adminId);
    }
    
    public JSONObject checkExcel(Workbook book){
    	JSONObject json = new JSONObject();
    	StringBuilder allMsg = new StringBuilder();
    	try{
    		if(book == null) {
    			allMsg.append("Excel对象为空，请检查。" + "\n");
    			return null;
    		}
    		Sheet sheet = book.getSheetAt(0);
    		int lastRowNum = sheet.getLastRowNum();	// 最后一行行号
    		if(lastRowNum > 3003) {
    			allMsg.append("一次导入不能超过3000行，请分批次导入。" + "\n");
    			return null;
    		}
    		List<ManageAdminRole> roleList = manageAdminRoleDao.getManageAdminRoleBaseList(new ManageAdminRole());
    		List<ManageAdminUser> result = new ArrayList<ManageAdminUser>();
    		int currentRow = 1; // 当前行索引
    		while((currentRow = ExcelUtil.hasThreeContinuousEmptyRows(sheet, currentRow)) != -1) {
    			Row row = sheet.getRow(currentRow);	// 得到行对象
    			ManageAdminUser user = (ManageAdminUser) rowToPojo(row,ManageAdminUser.class,sheet.getSheetName(),allMsg,roleList);
    			result.add(user);
    			currentRow ++;
    		}
    		if(result.size()<1){
    			allMsg.append("导入内容不能为空。" + "\n");
    		}
    		json.put("allMsg", allMsg);
    		json.put("result", result);
    		return json;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
	 * 根据规则，将一个Row对象转换成Pojo对象
	 * @param row 行对象
	 * @param ruleList 行规则
	 * @param clazz 要转换成的对象的字节码
	 * @param sheetName 标签页的名称
	 * @return Object 返回的Pojo类对象
	 * @author HuKaiXuan 2014-5-14 上午10:20:35
	 */
	private Object rowToPojo(Row row,Class<?> clazz, String sheetName,StringBuilder allMsg,List<ManageAdminRole> roleList) throws Exception {
		Set<String> setJobNumber = new HashSet<String>();
		Object obj = clazz.newInstance();
		String errorMsg = "导入失败：";
		for(int i = 0; i < 6 ; i ++ ) {
			// 给对象赋值
			String setMethodName = null;// set方法名
			String getMethodName = null;// get方法名
			
			String columnNum = "A";
			String columnName = null;
			if(i==0){
				columnName = "姓名";
				setMethodName = "setRealName";
				getMethodName = "getRealName";
			}else if(i==1){
				columnNum = "B";
				columnName = "工号";
				setMethodName = "setJobNumber";
				getMethodName = "getJobNumber";
			}else if(i==2){
				columnNum = "C";
				columnName = "性别";
				setMethodName = "setSexStr";
				getMethodName = "getSexStr";
			}else if(i==3){
				columnNum = "D";
				columnName = "手机号";
				setMethodName = "setMobile";
				getMethodName = "getMobile";
			}else if(i==4){
				columnNum = "E";
				columnName = "部门编号";
				setMethodName = "setDepartmentId";
				getMethodName = "getDepartmentId";
			}else if(i==5){
				columnNum = "F";
				columnName = "角色编码";
				setMethodName = "setRole_id";
				getMethodName = "getRole_id";
			}
			
			errorMsg = sheetName + "第" + (row.getRowNum() + 1) + "行、第" + columnNum + "列（" + columnName + "）：" + "\n";
			boolean usedMsg = false;	// 是否用过消息了，确保errorMsg只被添加一次
			Cell cell =row.getCell(i); // 单元格
			
			String value = ExcelUtil.getCellStringValue(cell);	// 获取String类型的值
			Class<?> returnType = clazz.getMethod(getMethodName, new Class[]{}).getReturnType(); // 返回值类型
			Method setMethod = clazz.getMethod(setMethodName,returnType);
			if(returnType == String.class) {				// String类型
				setMethod.invoke(obj,new Object[]{value});	// 调用set方法赋值
			}else if(returnType == Integer.class) {		// Integer类型
				Integer param = Double.valueOf(value).intValue();
				setMethod.invoke(obj,new Object[]{param});
			}
			ManageAdminUser user = (ManageAdminUser) obj;
			if("A".equals(columnNum)) {
				 String realName = user.getRealName();
				 if (realName==null) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　姓名不能为空，请检查。" + "\n");
				}else{
					user.setRealName(realName); 
				 }
			 }else if("B".equals(columnNum)) {
				 String jobNumber = user.getJobNumber();
				 if (jobNumber==null) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　工号不能为空，请检查。" + "\n");
				}else{
					int len = setJobNumber.size();
					setJobNumber.add(jobNumber);
					int len1 = setJobNumber.size();
					if(len==len1){
						if(!usedMsg) {
							allMsg.append(errorMsg);
							usedMsg = true;
						}
						allMsg.append("　　工号重复，请检查。" + "\n");
					}else{
						user.setJobNumber(jobNumber);
					}
				 }
			 }else if("C".equals(columnNum)) {
				 String sexStr = user.getSexStr();
				 if(!"男".equals(sexStr) && !"女".equals(sexStr)) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　性别不能为空" + "\n");
				 } else {
					 if("男".equals(sexStr)){
						 user.setSex(1); 
					 }else if("女".equals(sexStr)){
						 user.setSex(0); 
					 }
				 }
			 }else if("D".equals(columnNum)) {
				 String mobile = user.getMobile();
				 if (!StringUtils.isNotBlank(mobile)) {
					if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　手机号不能为空，请检查。" + "\n");
				}else{
					String regex = "^((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17[013678])|(18[0,5-9])|(19([0-9])))\\d{8}$";
					Pattern p = Pattern.compile(regex);
			        Matcher m = p.matcher(mobile);
			        boolean isMatch = m.matches();
					if(isMatch){
						user.setMobile(mobile);
						user.setPhone(mobile);
						user.setAdminName(mobile);
						user.setPasswd(new MD5().getMD5ofStr(mobile.substring(mobile.length()-6, mobile.length())));
						user.setCreaterId(40);
					}else{
						if(!usedMsg) {
							allMsg.append(errorMsg);
							usedMsg = true;
						}
						allMsg.append("　　手机号格式不正确，请检查。" + "\n");
					}
					
				 }
			 }else if("E".equals(columnNum)) {
				 String departmentId = user.getDepartmentId();
				 if (!StringUtils.isNotBlank(departmentId)) {
					 if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　机构编码不能为空，请检查。" + "\n");
				}else{
					Object obj_s = redisCacheClient.get(departmentId);
					if(obj_s!=null){
						CDepartment cDepartment = (CDepartment)obj_s;
						user.setDepartmentId(cDepartment.getId());
						user.setDepartmentIds(cDepartment.getParentIds());
						user.setCreateTime(new Date());
					}
				}
			 }else if("F".equals(columnNum)) {
				 Integer role_id = user.getRole_id();
				 if (role_id==null) {
					 if(!usedMsg) {
						allMsg.append(errorMsg);
						usedMsg = true;
					}
					allMsg.append("　　角色编码不能为空，请检查。" + "\n");
				}else{
					boolean b = false;
					for(ManageAdminRole role:roleList){
						if(role.getRoleId().intValue()==role_id.intValue()){
							b = true;
							break;
						}
					}
					
					if(b){
						user.setRole_id(role_id);
					}else{
						if(!usedMsg) {
							allMsg.append(errorMsg);
							usedMsg = true;
						}
						allMsg.append("　　角色编码不存在，请检查。" + "\n");
					}
				}
			 }
		}
		return obj;
	}
}
