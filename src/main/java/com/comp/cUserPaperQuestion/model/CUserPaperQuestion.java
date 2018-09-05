package com.comp.cUserPaperQuestion.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 用户答题明细	
 * @author	wzw
 * @time	2018-03-30 10:54:49
 */
public class CUserPaperQuestion extends BaseModel implements java.io.Serializable{
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
	/** 题目id **/
	private String questionId;
	/** 用户答案 **/
	private String answer;
	/** 正确答案 **/
	private String rightAnswer;
	/** 得分 **/
	private Integer score;
	/** 是否正确 0 错误 1 正确 **/
	private Integer isRight;
		
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
	 * 题目id
	 * @return questionId
	 */
	public String getQuestionId() {
		return questionId;
	}
	/**
	 * 题目id
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	/**
	 * 用户答案
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 用户答案
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 正确答案
	 * @return rightAnswer
	 */
	public String getRightAnswer() {
		return rightAnswer;
	}
	/**
	 * 正确答案
	 */
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
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
	/**
	 * 是否正确 0 错误 1 正确
	 * @return isRight
	 */
	public Integer getIsRight() {
		return isRight;
	}
	/**
	 * 是否正确 0 错误 1 正确
	 */
	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}