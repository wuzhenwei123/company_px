package com.sys.manageAdminRole.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 	
 * @author	keeny
 * @time	2017-04-06 09:42:57
 */
public class ManageAdminRole extends BaseModel implements java.io.Serializable{
	/** 角色主键 **/
	private Integer roleId;
	/** 角色名称 **/
	private String roleName;
	/** 创建日期 **/
	private Date createTime;
	/** 状态,1:正常，0:禁用 **/
	private Integer state;
		
	/**
	 * 角色主键
	 * @return roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * 角色主键
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * 角色名称
	 * @return roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	/**
	 * 状态,1:正常，0:禁用
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态,1:正常，0:禁用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
}