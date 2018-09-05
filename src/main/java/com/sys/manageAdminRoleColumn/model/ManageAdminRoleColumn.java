package com.sys.manageAdminRoleColumn.model;

import java.util.Date;
import java.util.List;

import com.base.model.BaseModel;
/**
 * 	
 * @author	keeny
 * @time	2017-04-06 10:12:45
 */
public class ManageAdminRoleColumn extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5769248086169453340L;
	/** 菜单-角色关系表主键 **/
	private Integer id;
	/** 角色 **/
	private Integer roleId;
	/** 菜单 **/
	private Integer columnId;
	
	private String columnName;
	private String columnUrl;
	private Integer parentColumnID;
	private String columnImg;
	private String column_en_name;
	private Integer state;
	/** 排序 **/
	private Integer sort_id;
	
	public Integer getSort_id() {
		return sort_id;
	}
	public void setSort_id(Integer sort_id) {
		this.sort_id = sort_id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	/** 是否分配1已分配0未分配 **/
	private Integer use_state;
	private List<ManageAdminRoleColumn> childList;
	
	public List<ManageAdminRoleColumn> getChildList() {
		return childList;
	}
	public void setChildList(List<ManageAdminRoleColumn> childList) {
		this.childList = childList;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnUrl() {
		return columnUrl;
	}
	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}
	public Integer getParentColumnID() {
		return parentColumnID;
	}
	public void setParentColumnID(Integer parentColumnID) {
		this.parentColumnID = parentColumnID;
	}
	public String getColumnImg() {
		return columnImg;
	}
	public void setColumnImg(String columnImg) {
		this.columnImg = columnImg;
	}
	public String getColumn_en_name() {
		return column_en_name;
	}
	public void setColumn_en_name(String column_en_name) {
		this.column_en_name = column_en_name;
	}
	public Integer getUse_state() {
		return use_state;
	}
	public void setUse_state(Integer use_state) {
		this.use_state = use_state;
	}
	/**
	 * 菜单-角色关系表主键
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 菜单-角色关系表主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 角色
	 * @return roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * 角色
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * 菜单
	 * @return columnId
	 */
	public Integer getColumnId() {
		return columnId;
	}
	/**
	 * 菜单
	 */
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	@Override
	public String toString() {
		return "ManageAdminRoleColumn [id=" + id + ", roleId=" + roleId
				+ ", columnId=" + columnId + ", columnName=" + columnName
				+ ", columnUrl=" + columnUrl + ", parentColumnID="
				+ parentColumnID + ", columnImg=" + columnImg
				+ ", column_en_name=" + column_en_name + ", use_state="
				+ use_state + ", childList=" + childList + "]";
	}
	
}