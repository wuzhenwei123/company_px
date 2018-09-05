package com.sys.manageAdminUser.dao;

import java.util.List;

import com.sys.manageAdminUser.model.ManageAdminUser;
import com.base.utils.ResponseList;
/**
 * @author	keeny
 * @time	2017-03-23 14:16:39
 */
public interface ManageAdminUserDao{
	
	public ResponseList<ManageAdminUser> getManageAdminUserList(ManageAdminUser manageAdminUser);
	
	public ResponseList<ManageAdminUser> getManageAdminUserNoInRelationList(ManageAdminUser manageAdminUser);

	public List<ManageAdminUser> getManageAdminUserBaseList(ManageAdminUser manageAdminUser);

	public int getManageAdminUserListCount(ManageAdminUser manageAdminUser);
	
	public ManageAdminUser getManageAdminUser(ManageAdminUser manageAdminUser);
	
	public ManageAdminUser getManageAdminUserById(Integer adminId);

    public int insertManageAdminUser(ManageAdminUser manageAdminUser);

    public int updateManageAdminUser(ManageAdminUser manageAdminUser);
    
    public int checkAdminName(ManageAdminUser manageAdminUser);
    
    public int updateDefaultDB(ManageAdminUser manageAdminUser);
    
    public int updateManageAdminUserByOpenId(ManageAdminUser manageAdminUser);
    
    public int unBindWx(ManageAdminUser manageAdminUser);
    
    public int removeManageAdminUser(ManageAdminUser manageAdminUser);
}
