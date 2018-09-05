package com.sys.manageUserOpLog.service;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sys.columnMethod.model.ColumnMethod;
import com.sys.columnMethod.service.ColumnMethodService;
import com.sys.manageColumn.model.ManageColumn;
import com.sys.manageColumn.service.ManageColumnService;
import com.sys.manageUserOpLog.dao.ManageUserOpLogDao;
import com.sys.manageUserOpLog.model.ManageUserOpLog;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ConfigConstants;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;

/**
 * @author keeny
 * @time 2017-07-19 13:29:16
 */
@Service
public class ManageUserOpLogServiceImpl implements ManageUserOpLogService {
	@Resource
	private ManageUserOpLogDao manageUserOpLogDao;
	@Autowired
	private ManageColumnService managecolumnService = null;
	@Autowired
	private ColumnMethodService columnMethodService = null;

	@Override
	public void recordLog(HttpServletRequest request) {
		final Map<String, String[]> requestParam = request.getParameterMap();
		final Map<String, String> headersInfo = getHeadersInfo(request);
		final String requestPath = request.getServletPath();
		final Object userNameObj = request.getSession().getAttribute(SessionName.ADMIN_USER_NAME);
		final Object userIdObj = request.getSession().getAttribute(SessionName.ADMIN_USER_ID);

		if (userIdObj != null && ConfigConstants.SYS_LOG_STATE != null && ConfigConstants.SYS_LOG_STATE) {
			boolean flage = false;
			if (!requestPath.contains(".")) {
				flage = true;
			}
			if (flage) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// 后台
						StringBuffer enContent = new StringBuffer();
						StringBuffer zhContent = new StringBuffer();
						int user_type = 2;
						ManageColumn manageColumn = new ManageColumn();
						manageColumn.setColumnUrl(requestPath);
						List<ManageColumn> manageColumnBaseList = managecolumnService.getManageColumnBaseList(manageColumn);
						if (manageColumnBaseList != null && manageColumnBaseList.size() != 0) {// 菜单
							ManageColumn manageColumn2 = manageColumnBaseList.get(0);
							zhContent.append("【" + manageColumn2.getColumnName() + "】");// 记录
						} else {// 不是菜单，可能是操作
							ColumnMethod columnMethod = new ColumnMethod();
							columnMethod.setActionPath(requestPath);
							List<ColumnMethod> columnMethodBaseList = columnMethodService.getColumnMethodBaseList(columnMethod);
							if (columnMethodBaseList != null && columnMethodBaseList.size() != 0) {
								ColumnMethod columnMethod2 = columnMethodBaseList.get(0);
								Integer columnId = columnMethod2.getColumnId();

								ManageColumn manageColumn1 = new ManageColumn();
								manageColumn1.setColumnId(columnId);
								ManageColumn manageColumn2 = managecolumnService.getManageColumn(manageColumn1);
								zhContent.append("【" + manageColumn2.getColumnName() + "】->" + columnMethod2.getZh_name());// 记录
							}

						}
						Long userId = Long.valueOf(userIdObj.toString());
						if (!enContent.toString().equals("") || !zhContent.toString().equals("")) {
							ManageUserOpLog manageUserOpLog = new ManageUserOpLog();
							try {
//								String headJson = JSONObject.toJSONString(headersInfo);
//								String jsonString = JSONObject.toJSONString(requestParam);
								manageUserOpLog.setContent(zhContent.toString());
								manageUserOpLog.setContent_en(enContent.toString());
//								manageUserOpLog.setContent(zhContent.toString()+"param="+jsonString+"head="+headJson);
//								manageUserOpLog.setContent_en(enContent.toString()+"param="+jsonString+"head="+headJson);
								manageUserOpLog.setCreate_time(new Date());
								manageUserOpLog.setUser_id(userId);
								manageUserOpLog.setUser_name(userNameObj.toString());
								manageUserOpLog.setUser_type(user_type);
								manageUserOpLog.setRequestPath(requestPath);
								manageUserOpLogDao.insertManageUserOpLog(manageUserOpLog);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		}
	}

	public ResponseList<ManageUserOpLog> getManageUserOpLogList(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.getManageUserOpLogList(manageUserOpLog);
	}

	public List<ManageUserOpLog> getManageUserOpLogBaseList(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.getManageUserOpLogBaseList(manageUserOpLog);
	}

	public int getManageUserOpLogListCount(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.getManageUserOpLogListCount(manageUserOpLog);
	}

	public ManageUserOpLog getManageUserOpLogById(long id) {
		ManageUserOpLog manageUserOpLogQuery = new ManageUserOpLog();
		manageUserOpLogQuery.setId(id);
		return manageUserOpLogDao.getManageUserOpLog(manageUserOpLogQuery);
	}

	public ManageUserOpLog getManageUserOpLog(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.getManageUserOpLog(manageUserOpLog);
	}

	public long insertManageUserOpLog(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.insertManageUserOpLog(manageUserOpLog);
	}

	public int updateManageUserOpLog(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.updateManageUserOpLog(manageUserOpLog);
	}

	public int removeManageUserOpLog(ManageUserOpLog manageUserOpLog) {
		return manageUserOpLogDao.removeManageUserOpLog(manageUserOpLog);
	}
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
