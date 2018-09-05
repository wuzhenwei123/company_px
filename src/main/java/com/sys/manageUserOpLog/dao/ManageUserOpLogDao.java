package com.sys.manageUserOpLog.dao;

import java.util.List;

import com.sys.manageUserOpLog.model.ManageUserOpLog;
import com.base.utils.ResponseList;
/**
 * @author	keeny
 * @time	2017-07-19 13:29:16
 */
public interface ManageUserOpLogDao{
	
	public ResponseList<ManageUserOpLog> getManageUserOpLogList(ManageUserOpLog manageUserOpLog);

	public List<ManageUserOpLog> getManageUserOpLogBaseList(ManageUserOpLog manageUserOpLog);

	public int getManageUserOpLogListCount(ManageUserOpLog manageUserOpLog);
	
	public ManageUserOpLog getManageUserOpLog(ManageUserOpLog manageUserOpLog);

    public long insertManageUserOpLog(ManageUserOpLog manageUserOpLog);

    public int updateManageUserOpLog(ManageUserOpLog manageUserOpLog);
    
    public int removeManageUserOpLog(ManageUserOpLog manageUserOpLog);
}
