package com.sys.manageAdminUser.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;

import com.sys.manageAdminUser.model.ManageAdminUser;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-03-23 14:16:40
 */
public interface ManageAdminUserService {
	/**
	 * 登录
	 * @param session
	 * @param adminName
	 */
	public void login(HttpSession session,String adminName);
	/**初始化菜单权限**/
	public void initColumn(HttpSession session);
	/**初始化操作权限**/
	public void initOp(HttpSession session);
	
    public ResponseList<ManageAdminUser> getManageAdminUserList(ManageAdminUser manageAdminUser);
    
    public List<ManageAdminUser> getManageAdminUserBaseList(ManageAdminUser manageAdminUser);
    
    public int getManageAdminUserListCount(ManageAdminUser manageAdminUser);

    public ManageAdminUser getManageAdminUser(ManageAdminUser manageAdminUser);

    public int insertManageAdminUser(ManageAdminUser manageAdminUser);
    
    public int checkAdminName(ManageAdminUser manageAdminUser);

    public int updateManageAdminUser(ManageAdminUser manageAdminUser);
    
    public int updateDefaultDB(ManageAdminUser manageAdminUser);
    
    public int updateManageAdminUserByOpenId(ManageAdminUser manageAdminUser);
    
    public int unBindWx(ManageAdminUser manageAdminUser);
    
    public int removeManageAdminUser(ManageAdminUser manageAdminUser);
    
    public ManageAdminUser getManageAdminUserById(Integer adminId);
    
    public JSONObject checkExcel(Workbook book);
}
