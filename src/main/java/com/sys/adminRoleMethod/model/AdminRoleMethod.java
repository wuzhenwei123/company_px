package com.sys.adminRoleMethod.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 	
 * @author	keeny
 * @time	2017-04-08 15:44:19
 */
public class AdminRoleMethod extends BaseModel implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2164166993903675332L;
	/** 用户-方法 关联表 **/
	private Integer id;
	/** 角色ID **/
	private Integer roleId;
	/** 方法ID **/
	private Integer mid;
	/** 菜单ID **/
	private Integer columnId;
	/**  **/
	private String en_name;
	/** 权限名称 **/
	private String zh_name;
	/** action路径 **/
	private String actionPath;
	
	public String getEn_name() {
		return en_name;
	}
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	public String getZh_name() {
		return zh_name;
	}
	public void setZh_name(String zh_name) {
		this.zh_name = zh_name;
	}
	public String getActionPath() {
		return actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
	/**
	 * 用户-方法 关联表
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 用户-方法 关联表
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 角色ID
	 * @return roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * 角色ID
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * 方法ID
	 * @return mid
	 */
	public Integer getMid() {
		return mid;
	}
	/**
	 * 方法ID
	 */
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	/**
	 * 菜单ID
	 * @return columnId
	 */
	public Integer getColumnId() {
		return columnId;
	}
	/**
	 * 菜单ID
	 */
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
}