package com.sys.manageUserLoginLog.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 登录日志	
 * @author	keeny
 * @time	2017-07-20 11:19:16
 */
public class ManageUserLoginLog extends BaseModel implements java.io.Serializable{
	/**  **/
	private Long id;
	/** 用户ID **/
	private Long user_id;
	/** 用户名称 **/
	private String user_name;
	/** 内容 **/
	private String content;
	/** 登录日期 **/
	private Date login_date;
	/** 离开日期 **/
	private Date leave_date;
	/** 登录时长(秒) **/
	private Long login_time;
	/** 登录IP **/
	private String ip;
	/** 浏览器版本 **/
	private String browser;
	/** 操作系统 **/
	private String os;
	private String session_id;
	
		
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	/**
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 用户ID
	 * @return user_id
	 */
	public Long getUser_id() {
		return user_id;
	}
	/**
	 * 用户ID
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	/**
	 * 用户名称
	 * @return user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * 用户名称
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * 内容
	 * @return content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 登录日期
	 * @return login_date
	 */
	public Date getLogin_date() {
		return login_date;
	}
	/**
	 * 登录日期
	 */
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	/**
	 * 离开日期
	 * @return leave_date
	 */
	public Date getLeave_date() {
		return leave_date;
	}
	/**
	 * 离开日期
	 */
	public void setLeave_date(Date leave_date) {
		this.leave_date = leave_date;
	}
	/**
	 * 登录时长(秒)
	 * @return login_time
	 */
	public Long getLogin_time() {
		return login_time;
	}
	/**
	 * 登录时长(秒)
	 */
	public void setLogin_time(Long login_time) {
		this.login_time = login_time;
	}
	/**
	 * 登录IP
	 * @return ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 登录IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 浏览器版本
	 * @return browser
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * 浏览器版本
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * 操作系统
	 * @return os
	 */
	public String getOs() {
		return os;
	}
	/**
	 * 操作系统
	 */
	public void setOs(String os) {
		this.os = os;
	}
}