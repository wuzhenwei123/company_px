package com.sys.columnMethod.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.adminRoleMethod.service.AdminRoleMethodService;
import com.sys.columnMethod.dao.ColumnMethodDao;
import com.sys.columnMethod.model.ColumnMethod;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-08 15:38:24
 */
@Service
public class ColumnMethodServiceImpl implements ColumnMethodService{
	@Resource
    private ColumnMethodDao columnMethodDao;
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;//角色-操作权限关系
    
    public ResponseList<ColumnMethod> getColumnMethodList(ColumnMethod columnMethod) {
        return columnMethodDao.getColumnMethodList(columnMethod);
    }
    
    public List<ColumnMethod> getColumnMethodBaseList(ColumnMethod columnMethod) {
        return columnMethodDao.getColumnMethodBaseList(columnMethod);
    }
    
    public int getColumnMethodListCount(ColumnMethod columnMethod) {
        return columnMethodDao.getColumnMethodListCount(columnMethod);
    }

    public ColumnMethod getColumnMethod(ColumnMethod columnMethod) { 
        return columnMethodDao.getColumnMethod(columnMethod);
    }

    public int insertColumnMethod(ColumnMethod columnMethod){
        return columnMethodDao.insertColumnMethod(columnMethod);
    }

    public int updateColumnMethod(ColumnMethod columnMethod){
        return columnMethodDao.updateColumnMethod(columnMethod);
    }
    
    public int removeColumnMethod(ColumnMethod columnMethod){
    	AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
		adminRoleMethod.setMid(columnMethod.getMid());
		List<AdminRoleMethod> adminRoleMethodBaseList = adminRoleMethodService.getAdminRoleMethodBaseList(adminRoleMethod);
		for (AdminRoleMethod adminRoleMethod2 : adminRoleMethodBaseList) {
			adminRoleMethodService.removeAdminRoleMethod(adminRoleMethod2);//角色-操作权限
		}
		
        return columnMethodDao.removeColumnMethod(columnMethod);
    }
    
}
