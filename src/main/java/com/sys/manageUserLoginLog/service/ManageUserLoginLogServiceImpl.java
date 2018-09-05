package com.sys.manageUserLoginLog.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sys.manageUserLoginLog.dao.ManageUserLoginLogDao;
import com.sys.manageUserLoginLog.model.ManageUserLoginLog;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;

/**
 * @author	keeny
 * @time	2017-07-20 11:19:16
 */
@Service
public class ManageUserLoginLogServiceImpl implements ManageUserLoginLogService{
	@Resource
    private ManageUserLoginLogDao manageUserLoginLogDao;
    
    public ResponseList<ManageUserLoginLog> getManageUserLoginLogList(ManageUserLoginLog manageUserLoginLog) {
        return manageUserLoginLogDao.getManageUserLoginLogList(manageUserLoginLog);
    }
    
    public List<ManageUserLoginLog> getManageUserLoginLogBaseList(ManageUserLoginLog manageUserLoginLog) {
        return manageUserLoginLogDao.getManageUserLoginLogBaseList(manageUserLoginLog);
    }
    
    public int getManageUserLoginLogListCount(ManageUserLoginLog manageUserLoginLog) {
        return manageUserLoginLogDao.getManageUserLoginLogListCount(manageUserLoginLog);
    }
	
	public ManageUserLoginLog getManageUserLoginLogById(long id){
		ManageUserLoginLog manageUserLoginLogQuery = new ManageUserLoginLog();
		manageUserLoginLogQuery.setId(id);
		return manageUserLoginLogDao.getManageUserLoginLog(manageUserLoginLogQuery);
	}
	
    public ManageUserLoginLog getManageUserLoginLog(ManageUserLoginLog manageUserLoginLog) { 
        return manageUserLoginLogDao.getManageUserLoginLog(manageUserLoginLog);
    }

    public long insertManageUserLoginLog(ManageUserLoginLog manageUserLoginLog){
        return manageUserLoginLogDao.insertManageUserLoginLog(manageUserLoginLog);
    }

    public int updateManageUserLoginLog(ManageUserLoginLog manageUserLoginLog){
        return manageUserLoginLogDao.updateManageUserLoginLog(manageUserLoginLog);
    }
    
    public int removeManageUserLoginLog(ManageUserLoginLog manageUserLoginLog){
        return manageUserLoginLogDao.removeManageUserLoginLog(manageUserLoginLog);
    }

	@Override
	public void record(HttpServletRequest request) {
		final String userAgent = request.getHeader("User-Agent");
		final String sessionId = request.getSession().getId();
		final Integer currentAdminUserId = getCurrentAdminUserId(request);
		final String currentAdminUserName = getCurrentAdminUserName(request);
		final String ipAddress = RequestHandler.getIpAddress(request);
		if (currentAdminUserId != null && ConfigConstants.SYS_LOG_STATE != null && ConfigConstants.SYS_LOG_STATE) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					ManageUserLoginLog manageUserLoginLog = new ManageUserLoginLog();
					manageUserLoginLog.setSession_id(sessionId);
					ManageUserLoginLog manageUserLoginLog2 = manageUserLoginLogDao.getManageUserLoginLog(manageUserLoginLog);
					if(manageUserLoginLog2 == null){
						manageUserLoginLog.setUser_id(currentAdminUserId.longValue());
						manageUserLoginLog.setUser_name(currentAdminUserName);
						manageUserLoginLog.setBrowser(RequestHandler.getBrowser(userAgent));
						manageUserLoginLog.setOs(RequestHandler.getOs(userAgent));
						manageUserLoginLog.setIp(ipAddress);
						manageUserLoginLog.setLogin_date(new Date());
						manageUserLoginLogDao.insertManageUserLoginLog(manageUserLoginLog);
					}else{
						Long user_id = manageUserLoginLog2.getUser_id();
						if(user_id.intValue() == currentAdminUserId){
							Date currentDate = new Date();
							manageUserLoginLog2.setLeave_date(new Date());
							Date login_date = manageUserLoginLog2.getLogin_date();
							if(login_date!=null){
								long loginTime = login_date.getTime();
								long currentTime = currentDate.getTime();
								
								long time = (currentTime - loginTime)/1000;
								manageUserLoginLog2.setLogin_time(time);
							}
							manageUserLoginLogDao.updateManageUserLoginLog(manageUserLoginLog2);
						}
					}
				}
			}).start();
		}
		
	}
	private Object getSession(HttpServletRequest request, String key) {
		if (key != null) {
			return request.getSession().getAttribute(key);
		}
		return null;
	}
	private Integer getCurrentAdminUserId(HttpServletRequest request) {
		Object obj = getSession(request, SessionName.ADMIN_USER_ID);
		if(obj != null){
			return Integer.valueOf(obj.toString());
		}
		return null;
	}
	private String getCurrentAdminUserName(HttpServletRequest request) {
		Object obj = getSession(request, SessionName.ADMIN_USER_NAME);
		if(obj != null){
			return obj.toString();
		}
		return null;
	}
}
