package com.sys.manageColumn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.adminRoleMethod.service.AdminRoleMethodService;
import com.sys.columnMethod.model.ColumnMethod;
import com.sys.columnMethod.service.ColumnMethodService;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageAdminRoleColumn.service.ManageAdminRoleColumnService;
import com.sys.manageColumn.dao.ManageColumnDao;
import com.sys.manageColumn.model.ManageColumn;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-06 10:02:33
 */
@Service
public class ManageColumnServiceImpl implements ManageColumnService{
	@Resource
    private ManageColumnDao manageColumnDao;
	@Autowired
	private ManageAdminRoleColumnService manageadminrolecolumnService = null;//角色-菜单关系
	@Autowired
	private ColumnMethodService columnMethodService = null;//菜单-操作权限关系
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;//角色-操作权限关系
	
    public ResponseList<ManageColumn> getManageColumnList(ManageColumn manageColumn) {
        return manageColumnDao.getManageColumnList(manageColumn);
    }
    
    public List<ManageColumn> getManageColumnBaseList(ManageColumn manageColumn) {
        return manageColumnDao.getManageColumnBaseList(manageColumn);
    }
    
    public int getManageColumnListCount(ManageColumn manageColumn) {
        return manageColumnDao.getManageColumnListCount(manageColumn);
    }

    public ManageColumn getManageColumn(ManageColumn manageColumn) { 
        return manageColumnDao.getManageColumn(manageColumn);
    }

    public int insertManageColumn(ManageColumn manageColumn){
        return manageColumnDao.insertManageColumn(manageColumn);
    }

    public int updateManageColumn(ManageColumn manageColumn){
        return manageColumnDao.updateManageColumn(manageColumn);
    }
    
    public int removeManageColumn(ManageColumn manageColumn){
    	
    	
    	ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
    	manageAdminRoleColumn.setColumnId(manageColumn.getColumnId());
    	List<ManageAdminRoleColumn> manageAdminRoleColumnBaseList = manageadminrolecolumnService.getManageAdminRoleColumnBaseList(manageAdminRoleColumn);
    	for (ManageAdminRoleColumn manageAdminRoleColumn2 : manageAdminRoleColumnBaseList) {
    		manageadminrolecolumnService.removeManageAdminRoleColumn(manageAdminRoleColumn2);//角色菜单关系
		}
    	
    	ColumnMethod columnMethod = new ColumnMethod();
    	columnMethod.setColumnId(manageColumn.getColumnId());
    	List<ColumnMethod> columnMethodBaseList = columnMethodService.getColumnMethodBaseList(columnMethod);
    	for (ColumnMethod columnMethod2 : columnMethodBaseList) {
    		
    		AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
    		adminRoleMethod.setMid(columnMethod2.getMid());
    		List<AdminRoleMethod> adminRoleMethodBaseList = adminRoleMethodService.getAdminRoleMethodBaseList(adminRoleMethod);
    		for (AdminRoleMethod adminRoleMethod2 : adminRoleMethodBaseList) {
    			adminRoleMethodService.removeAdminRoleMethod(adminRoleMethod2);//角色-操作权限
			}
    		
    		columnMethodService.removeColumnMethod(columnMethod2);//菜单-操作权限关系
		}
    	
        return manageColumnDao.removeManageColumn(manageColumn);
    }
    
}
