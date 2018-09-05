package com.sys.manageAdminRole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.adminRoleMethod.service.AdminRoleMethodService;
import com.sys.manageAdminRole.dao.ManageAdminRoleDao;
import com.sys.manageAdminRole.model.ManageAdminRole;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageAdminRoleColumn.service.ManageAdminRoleColumnService;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-06 09:42:57
 */
@Service
public class ManageAdminRoleServiceImpl implements ManageAdminRoleService{
	@Resource
    private ManageAdminRoleDao manageAdminRoleDao;
	@Autowired
	private ManageAdminRoleColumnService manageadminrolecolumnService = null;//角色-菜单关系
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;//角色-操作权限关系
    
    public ResponseList<ManageAdminRole> getManageAdminRoleList(ManageAdminRole manageAdminRole) {
        return manageAdminRoleDao.getManageAdminRoleList(manageAdminRole);
    }
    
    public List<ManageAdminRole> getManageAdminRoleBaseList(ManageAdminRole manageAdminRole) {
        return manageAdminRoleDao.getManageAdminRoleBaseList(manageAdminRole);
    }
    
    public int getManageAdminRoleListCount(ManageAdminRole manageAdminRole) {
        return manageAdminRoleDao.getManageAdminRoleListCount(manageAdminRole);
    }

    public ManageAdminRole getManageAdminRole(ManageAdminRole manageAdminRole) { 
        return manageAdminRoleDao.getManageAdminRole(manageAdminRole);
    }

    public int insertManageAdminRole(ManageAdminRole manageAdminRole){
        return manageAdminRoleDao.insertManageAdminRole(manageAdminRole);
    }

    public int updateManageAdminRole(ManageAdminRole manageAdminRole){
        return manageAdminRoleDao.updateManageAdminRole(manageAdminRole);
    }
    
    public int removeManageAdminRole(ManageAdminRole manageAdminRole){
    	
    	ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
    	manageAdminRoleColumn.setRoleId(manageAdminRole.getRoleId());
    	List<ManageAdminRoleColumn> manageAdminRoleColumnBaseList = manageadminrolecolumnService.getManageAdminRoleColumnBaseList(manageAdminRoleColumn);
    	for (ManageAdminRoleColumn manageAdminRoleColumn2 : manageAdminRoleColumnBaseList) {
    		manageadminrolecolumnService.removeManageAdminRoleColumn(manageAdminRoleColumn2);//角色菜单关系
		}
    	
    	AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
		adminRoleMethod.setRoleId(manageAdminRole.getRoleId());
		List<AdminRoleMethod> adminRoleMethodBaseList = adminRoleMethodService.getAdminRoleMethodBaseList(adminRoleMethod);
		for (AdminRoleMethod adminRoleMethod2 : adminRoleMethodBaseList) {
			adminRoleMethodService.removeAdminRoleMethod(adminRoleMethod2);//角色-操作权限
		}
		
        return manageAdminRoleDao.removeManageAdminRole(manageAdminRole);
    }
    
}
