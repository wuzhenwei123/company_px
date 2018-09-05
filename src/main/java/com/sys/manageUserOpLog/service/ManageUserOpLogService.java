package com.sys.manageUserOpLog.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sys.manageUserOpLog.model.ManageUserOpLog;
import com.base.utils.ResponseList;

/**
 * @author keeny
 * @time 2017-07-19 13:29:16
 */
public interface ManageUserOpLogService {
	/**
	 * 日志记录
	 * 
	 * @param request
	 */
	public void recordLog(HttpServletRequest request);

	public ResponseList<ManageUserOpLog> getManageUserOpLogList(ManageUserOpLog manageUserOpLog);

	public List<ManageUserOpLog> getManageUserOpLogBaseList(ManageUserOpLog manageUserOpLog);

	public int getManageUserOpLogListCount(ManageUserOpLog manageUserOpLog);

	public ManageUserOpLog getManageUserOpLogById(long id);

	public ManageUserOpLog getManageUserOpLog(ManageUserOpLog manageUserOpLog);

	public long insertManageUserOpLog(ManageUserOpLog manageUserOpLog);

	public int updateManageUserOpLog(ManageUserOpLog manageUserOpLog);

	public int removeManageUserOpLog(ManageUserOpLog manageUserOpLog);
}
