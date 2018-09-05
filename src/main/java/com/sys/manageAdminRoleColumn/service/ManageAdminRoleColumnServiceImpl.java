package com.sys.manageAdminRoleColumn.service;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sys.manageAdminRoleColumn.dao.ManageAdminRoleColumnDao;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-06 10:12:45
 */
@Service
public class ManageAdminRoleColumnServiceImpl implements ManageAdminRoleColumnService{
	@Resource
    private ManageAdminRoleColumnDao manageAdminRoleColumnDao;
    
    public ResponseList<ManageAdminRoleColumn> getManageAdminRoleColumnList(ManageAdminRoleColumn manageAdminRoleColumn) {
        return manageAdminRoleColumnDao.getManageAdminRoleColumnList(manageAdminRoleColumn);
    }
    
    public List<ManageAdminRoleColumn> getManageAdminRoleColumnBaseList(ManageAdminRoleColumn manageAdminRoleColumn) {
        return manageAdminRoleColumnDao.getManageAdminRoleColumnBaseList(manageAdminRoleColumn);
    }
    
    public int getManageAdminRoleColumnListCount(ManageAdminRoleColumn manageAdminRoleColumn) {
        return manageAdminRoleColumnDao.getManageAdminRoleColumnListCount(manageAdminRoleColumn);
    }

    public ManageAdminRoleColumn getManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn) { 
        return manageAdminRoleColumnDao.getManageAdminRoleColumn(manageAdminRoleColumn);
    }

    public int insertManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn){
        return manageAdminRoleColumnDao.insertManageAdminRoleColumn(manageAdminRoleColumn);
    }

    public int updateManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn){
        return manageAdminRoleColumnDao.updateManageAdminRoleColumn(manageAdminRoleColumn);
    }
    
    public int removeManageAdminRoleColumn(ManageAdminRoleColumn manageAdminRoleColumn){
        return manageAdminRoleColumnDao.removeManageAdminRoleColumn(manageAdminRoleColumn);
    }

	@Override
	public int removeAdminRoleColumnByRoleIdColumnId( ManageAdminRoleColumn manageAdminRoleColumn) {
		return manageAdminRoleColumnDao.removeAdminRoleColumnByRoleIdColumnId(manageAdminRoleColumn);
	}
    
}
