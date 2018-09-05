package com.sys.manageUserOpLog.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 操作日志	
 * @author	keeny
 * @time	2017-07-19 13:29:16
 */
public class ManageUserOpLog extends BaseModel implements java.io.Serializable{
	/**  **/
	private Long id;
	/** 用户ID **/
	private Long user_id;
	/** 用户名称 **/
	private String user_name;
	/** 用户类型1前台用户2后台用户 **/
	private Integer user_type;
	/** 内容 **/
	private String content;
	/** 日期 **/
	private Date create_time;
	/** 请求路径 **/
	private String requestPath;
	/** 英文内容 **/
	private String content_en;
	private String create_time1;
	private String create_time2;
	
	public String getCreate_time1() {
		return create_time1;
	}
	public void setCreate_time1(String create_time1) {
		this.create_time1 = create_time1;
	}
	public String getCreate_time2() {
		return create_time2;
	}
	public void setCreate_time2(String create_time2) {
		this.create_time2 = create_time2;
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
	 * 用户类型1前台用户2后台用户
	 * @return user_type
	 */
	public Integer getUser_type() {
		return user_type;
	}
	/**
	 * 用户类型1前台用户2后台用户
	 */
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
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
	 * 日期
	 * @return create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}
	/**
	 * 日期
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	/**
	 * 请求路径
	 * @return requestPath
	 */
	public String getRequestPath() {
		return requestPath;
	}
	/**
	 * 请求路径
	 */
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	/**
	 * 英文内容
	 * @return content_en
	 */
	public String getContent_en() {
		return content_en;
	}
	/**
	 * 英文内容
	 */
	public void setContent_en(String content_en) {
		this.content_en = content_en;
	}
}