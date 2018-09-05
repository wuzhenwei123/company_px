package com.comp.cPaperDepartment.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 试卷机构关系表	
 * @author	wzw
 * @time	2018-03-23 09:54:59
 */
public class CPaperDepartment extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 试卷id **/
	private String paperId;
	/** 机构id **/
	private String departmentId;
	
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
	
	private Integer questionCount;
	
	private boolean answerStatus;
	
	private Integer userId;
	private Long userAnserPaperId;
		
	public Long getUserAnserPaperId() {
		return userAnserPaperId;
	}
	public void setUserAnserPaperId(Long userAnserPaperId) {
		this.userAnserPaperId = userAnserPaperId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public boolean isAnswerStatus() {
		return answerStatus;
	}
	public void setAnswerStatus(boolean answerStatus) {
		this.answerStatus = answerStatus;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
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
	 * 机构id
	 * @return departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}
	/**
	 * 机构id
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}