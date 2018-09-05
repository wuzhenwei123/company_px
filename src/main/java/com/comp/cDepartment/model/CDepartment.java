package com.comp.cDepartment.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 机构体系表	
 * @author	wzw
 * @time	2018-03-22 10:36:16
 */
public class CDepartment extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private String id;
	/** 机构名称 **/
	private String name;
	/** 级别 0 最高 **/
	private Integer level;
	/** 父id **/
	private String parentId;
	/** 机构编码 **/
	private String code;
	/** 排序 **/
	private Integer sortId;
	private String parentIds;
		
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	/**
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 机构名称
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 机构名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 级别 0 最高
	 * @return level
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 级别 0 最高
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 父id
	 * @return parent_id
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 父id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 机构编码
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 机构编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 排序
	 * @return sort_id
	 */
	public Integer getSortId() {
		return sortId;
	}
	/**
	 * 排序
	 */
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}