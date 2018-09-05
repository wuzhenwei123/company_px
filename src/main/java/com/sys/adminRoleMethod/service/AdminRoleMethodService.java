package com.sys.adminRoleMethod.service;

import java.util.List;
import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-08 15:44:19
 */
public interface AdminRoleMethodService {

    public ResponseList<AdminRoleMethod> getAdminRoleMethodList(AdminRoleMethod adminRoleMethod);
    
    public List<AdminRoleMethod> getAdminRoleMethodBaseList(AdminRoleMethod adminRoleMethod);
    
    public int getAdminRoleMethodListCount(AdminRoleMethod adminRoleMethod);

    public AdminRoleMethod getAdminRoleMethod(AdminRoleMethod adminRoleMethod);

    public int insertAdminRoleMethod(AdminRoleMethod adminRoleMethod);

    public int updateAdminRoleMethod(AdminRoleMethod adminRoleMethod);
    
    public int removeAdminRoleMethod(AdminRoleMethod adminRoleMethod);
    public int removeAdminRoleMethodByRoleIdMid(AdminRoleMethod adminRoleMethod);
}
