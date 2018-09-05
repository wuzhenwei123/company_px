package com.sys.manageColumn.service;

import java.util.List;
import com.sys.manageColumn.model.ManageColumn;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-04-06 10:02:33
 */
public interface ManageColumnService {

    public ResponseList<ManageColumn> getManageColumnList(ManageColumn manageColumn);
    
    public List<ManageColumn> getManageColumnBaseList(ManageColumn manageColumn);
    
    public int getManageColumnListCount(ManageColumn manageColumn);

    public ManageColumn getManageColumn(ManageColumn manageColumn);

    public int insertManageColumn(ManageColumn manageColumn);

    public int updateManageColumn(ManageColumn manageColumn);
    
    public int removeManageColumn(ManageColumn manageColumn);
}
