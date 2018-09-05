package com.comp.cUserPaper.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 用户答题表	
 * @author	wzw
 * @time	2018-03-30 10:45:22
 */
public class CUserPaper extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 用户id **/
	private Integer userId;
	/** 试卷id **/
	private String paperId;
	/** 提交时间 **/
	private Date createTime;
	/** 得分 **/
	private Integer score;
	
	private String departmentIds;
	private String departmentName;
	private String userName;
		
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentIds() {
		return departmentIds;
	}
	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
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
	 * 用户id
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 试卷id
	 * @return paperId
	 */
	public String getPaperId() {
		return paperId;
	}
	/**
	 * 试卷id
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	/**
	 * 提交时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 提交时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 得分
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 得分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}