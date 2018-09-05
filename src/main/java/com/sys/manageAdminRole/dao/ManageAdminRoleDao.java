package com.sys.manageAdminRole.dao;

import java.util.List;

import com.sys.manageAdminRole.model.ManageAdminRole;
import com.base.utils.ResponseList;
/**
 * @author	keeny
 * @time	2017-04-06 09:42:57
 */
public interface ManageAdminRoleDao{
	
	public ResponseList<ManageAdminRole> getManageAdminRoleList(ManageAdminRole manageAdminRole);

	public List<ManageAdminRole> getManageAdminRoleBaseList(ManageAdminRole manageAdminRole);

	public int getManageAdminRoleListCount(ManageAdminRole manageAdminRole);
	
	public ManageAdminRole getManageAdminRole(ManageAdminRole manageAdminRole);

    public int insertManageAdminRole(ManageAdminRole manageAdminRole);

    public int updateManageAdminRole(ManageAdminRole manageAdminRole);
    
    public int removeManageAdminRole(ManageAdminRole manageAdminRole);
}
