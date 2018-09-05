package com.sys.manageUserLoginLog.dao;

import java.util.List;

import com.sys.manageUserLoginLog.model.ManageUserLoginLog;
import com.base.utils.ResponseList;
/**
 * @author	keeny
 * @time	2017-07-20 11:19:16
 */
public interface ManageUserLoginLogDao{
	
	public ResponseList<ManageUserLoginLog> getManageUserLoginLogList(ManageUserLoginLog manageUserLoginLog);

	public List<ManageUserLoginLog> getManageUserLoginLogBaseList(ManageUserLoginLog manageUserLoginLog);

	public int getManageUserLoginLogListCount(ManageUserLoginLog manageUserLoginLog);
	
	public ManageUserLoginLog getManageUserLoginLog(ManageUserLoginLog manageUserLoginLog);

    public long insertManageUserLoginLog(ManageUserLoginLog manageUserLoginLog);

    public int updateManageUserLoginLog(ManageUserLoginLog manageUserLoginLog);
    
    public int removeManageUserLoginLog(ManageUserLoginLog manageUserLoginLog);
}
