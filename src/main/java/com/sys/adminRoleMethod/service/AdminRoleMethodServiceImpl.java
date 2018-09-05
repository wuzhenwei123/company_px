package com.sys.adminRoleMethod.service;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sys.adminRoleMethod.dao.AdminRoleMethodDao;
import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-08 15:44:19
 */
@Service
public class AdminRoleMethodServiceImpl implements AdminRoleMethodService{
	@Resource
    private AdminRoleMethodDao adminRoleMethodDao;
    
    public ResponseList<AdminRoleMethod> getAdminRoleMethodList(AdminRoleMethod adminRoleMethod) {
        return adminRoleMethodDao.getAdminRoleMethodList(adminRoleMethod);
    }
    
    public List<AdminRoleMethod> getAdminRoleMethodBaseList(AdminRoleMethod adminRoleMethod) {
        return adminRoleMethodDao.getAdminRoleMethodBaseList(adminRoleMethod);
    }
    
    public int getAdminRoleMethodListCount(AdminRoleMethod adminRoleMethod) {
        return adminRoleMethodDao.getAdminRoleMethodListCount(adminRoleMethod);
    }

    public AdminRoleMethod getAdminRoleMethod(AdminRoleMethod adminRoleMethod) { 
        return adminRoleMethodDao.getAdminRoleMethod(adminRoleMethod);
    }

    public int insertAdminRoleMethod(AdminRoleMethod adminRoleMethod){
        return adminRoleMethodDao.insertAdminRoleMethod(adminRoleMethod);
    }

    public int updateAdminRoleMethod(AdminRoleMethod adminRoleMethod){
        return adminRoleMethodDao.updateAdminRoleMethod(adminRoleMethod);
    }
    
    public int removeAdminRoleMethod(AdminRoleMethod adminRoleMethod){
        return adminRoleMethodDao.removeAdminRoleMethod(adminRoleMethod);
    }

	@Override
	public int removeAdminRoleMethodByRoleIdMid(AdminRoleMethod adminRoleMethod) {
		return adminRoleMethodDao.removeAdminRoleMethodByRoleIdMid(adminRoleMethod);
	}
    
}
