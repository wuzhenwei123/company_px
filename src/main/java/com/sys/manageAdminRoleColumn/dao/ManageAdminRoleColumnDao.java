package com.sys.manageAdminRoleColumn.dao;

import java.util.List;

import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.base.utils.ResponseList;
/**
 * @author	keeny
 * @time	2017-04-06 10:12:45
 */
public interface ManageAdminRoleColumnDao{
	
	public ResponseList<ManageAdminRoleColumn> getManageAdminRoleColumnList(ManageAdminRoleColumn manageAdminRoleColumn);

	public List<ManageAdminRoleColumn> getManageAdminRoleColumnBaseList(ManageAdminRoleColumn manageAdminRoleColumn);

	public int getManageAdminRoleColumnListCount(ManageAdminRoleColumn manageAdminRoleColumn);
	
	public ManageAdminRoleColumn getManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn);

    public int insertManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn);

    public int updateManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn);
    
    public int removeManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn);
    
    public int removeAdminRoleColumnByRoleIdColumnId(ManageAdminRoleColumn manageAdminRoleColumn);
}
