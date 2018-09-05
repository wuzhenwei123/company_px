package com.sys.columnMethod.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 操作权限	
 * @author	keeny
 * @time	2017-04-08 15:38:24
 */
public class ColumnMethod extends BaseModel implements java.io.Serializable{
	/** 方法id **/
	private Integer mid;
	/** 菜单主键 **/
	private Integer columnId;
	/**  **/
	private String en_name;
	/** 权限名称 **/
	private String zh_name;
	/** action路径 **/
	private String actionPath;
	/** 创建日期 **/
	private Date createTime;
		
	/**
	 * 方法id
	 * @return mid
	 */
	public Integer getMid() {
		return mid;
	}
	/**
	 * 方法id
	 */
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	/**
	 * 菜单主键
	 * @return columnId
	 */
	public Integer getColumnId() {
		return columnId;
	}
	/**
	 * 菜单主键
	 */
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	/**
	 * 
	 * @return en_name
	 */
	public String getEn_name() {
		return en_name;
	}
	/**
	 * 
	 */
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	/**
	 * 权限名称
	 * @return zh_name
	 */
	public String getZh_name() {
		return zh_name;
	}
	/**
	 * 权限名称
	 */
	public void setZh_name(String zh_name) {
		this.zh_name = zh_name;
	}
	/**
	 * action路径
	 * @return actionPath
	 */
	public String getActionPath() {
		return actionPath;
	}
	/**
	 * action路径
	 */
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
	/**
	 * 创建日期
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}