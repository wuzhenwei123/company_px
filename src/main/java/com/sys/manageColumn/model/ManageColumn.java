package com.sys.manageColumn.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 	
 * @author	keeny
 * @time	2017-04-06 10:02:33
 */
public class ManageColumn extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8772769734674855916L;
	/** 菜单主键 **/
	private Integer columnId;
	/** 英文名称 **/
	private String column_en_name;
	/** 菜单名称 **/
	private String columnName;
	/** 菜单路径 **/
	private String columnUrl;
	/** 父菜单id **/
	private Integer parentColumnID;
	/** 状态，1:正常，0:禁用 **/
	private Integer state;
	/** 排序 **/
	private Integer sort_id;
	/** 菜单图片 **/
	private String columnImg;
	/** 是否分配1已分配0未分配 **/
	private Integer use_state;
	private String parentColumnName;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParentColumnName() {
		return parentColumnName;
	}
	public void setParentColumnName(String parentColumnName) {
		this.parentColumnName = parentColumnName;
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
	 * 英文名称
	 * @return column_en_name
	 */
	public String getColumn_en_name() {
		return column_en_name;
	}
	/**
	 * 英文名称
	 */
	public void setColumn_en_name(String column_en_name) {
		this.column_en_name = column_en_name;
	}
	/**
	 * 菜单名称
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * 菜单名称
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * 菜单路径
	 * @return columnUrl
	 */
	public String getColumnUrl() {
		return columnUrl;
	}
	/**
	 * 菜单路径
	 */
	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}
	/**
	 * 父菜单id
	 * @return parentColumnID
	 */
	public Integer getParentColumnID() {
		return parentColumnID;
	}
	/**
	 * 父菜单id
	 */
	public void setParentColumnID(Integer parentColumnID) {
		this.parentColumnID = parentColumnID;
	}
	/**
	 * 状态，1:正常，0:禁用
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态，1:正常，0:禁用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 排序
	 * @return sort_id
	 */
	public Integer getSort_id() {
		return sort_id;
	}
	/**
	 * 排序
	 */
	public void setSort_id(Integer sort_id) {
		this.sort_id = sort_id;
	}
	/**
	 * 菜单图片
	 * @return columnImg
	 */
	public String getColumnImg() {
		return columnImg;
	}
	/**
	 * 菜单图片
	 */
	public void setColumnImg(String columnImg) {
		this.columnImg = columnImg;
	}
	/**
	 * 是否分配1已分配0未分配
	 * @return use_state
	 */
	public Integer getUse_state() {
		return use_state;
	}
	/**
	 * 是否分配1已分配0未分配
	 */
	public void setUse_state(Integer use_state) {
		this.use_state = use_state;
	}
}