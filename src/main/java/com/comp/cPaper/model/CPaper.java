package com.comp.cPaper.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 试卷表	
 * @author	wzw
 * @time	2018-03-23 09:21:18
 */
public class CPaper extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private String id;
	/** 标题 **/
	private String title;
	/** 分值 **/
	private Integer score;
	/** 创建时间 **/
	private Date createTime;
	/** 开始答题时间 **/
	private Date startTime;
	/** 结束答题时间 **/
	private Date endTime;
	/** 状态 0 未发布 1 发布 2 回收 **/
	private Integer state;
	
	private String departmentId;
	private Integer questionCount;
	private String createDepartmentIds;
		
	public String getCreateDepartmentIds() {
		return createDepartmentIds;
	}
	public void setCreateDepartmentIds(String createDepartmentIds) {
		this.createDepartmentIds = createDepartmentIds;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
	 * 标题
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 分值
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 分值
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 创建时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 开始答题时间
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 开始答题时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 结束答题时间
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 结束答题时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 状态 0 未发布 1 发布 2 回收
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态 0 未发布 1 发布 2 回收
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}