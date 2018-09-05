package com.comp.cPaperQuestion.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 试卷题目关系表	
 * @author	wzw
 * @time	2018-03-23 11:36:52
 */
public class CPaperQuestion extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 试卷id **/
	private String paperId;
	/** 题目id **/
	private String questionId;
	/** 排序 **/
	private Integer sortId;
	/** 题干 **/
	private String content;
	/** 试题类型 1 选择 2 填空 3 问答 **/
	private Integer style;
	/** 分值 **/
	private Integer score;
	/** 正确答案 **/
	private String rightAnswer;
	/** 选项A **/
	private String optionA;
	/** 选项B **/
	private String optionB;
	/** 选项C **/
	private String optionC;
	/** 选项D **/
	private String optionD;
	/** 选项E **/
	private String optionE;
	/** 选项F **/
	private String optionF;
	
	private String userAnswer;
	
		
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStyle() {
		return style;
	}
	public void setStyle(Integer style) {
		this.style = style;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getOptionF() {
		return optionF;
	}
	public void setOptionF(String optionF) {
		this.optionF = optionF;
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
	 * 排序
	 * @return sortId
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