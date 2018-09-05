package com.sys.manageException.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 异常处理	
 * @author	keeny
 * @time	2017-03-23 13:11:26
 */
public class ManageException extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9206658114640546947L;
	/**  **/
	private Integer id;
	/** 异常类型名称 **/
	private String exception;
	/** 异常内容 **/
	private String content;
	/** 异常日期 **/
	private Date createTime;
	/** 处理备注 **/
	private String remark;
	/** 处理状态，0未处理1已处理 **/
	private Integer state;
	/** 简要说明 **/
	private String brief;
	private Date createTime1;//开始时间
	private Date createTime2;//结束时间
	
	public Date getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(Date createTime1) {
		this.createTime1 = createTime1;
	}
	public Date getCreateTime2() {
		return createTime2;
	}
	public void setCreateTime2(Date createTime2) {
		this.createTime2 = createTime2;
	}
	/**
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 异常类型名称
	 * @return exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * 异常类型名称
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
	/**
	 * 异常内容
	 * @return content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 异常内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 异常日期
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 异常日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 处理备注
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 处理备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 处理状态，0未处理1已处理
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 处理状态，0未处理1已处理
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 简要说明
	 * @return brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * 简要说明
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
}