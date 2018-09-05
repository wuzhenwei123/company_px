package com.comp.cQuestion.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 试题表	
 * @author	wzw
 * @time	2018-03-23 11:36:26
 */
public class CQuestion extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private String id;
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
	private String paperId;
	/** 排序 **/
	private Integer sortId;
	
	private String styleStr;
	
		
	public String getStyleStr() {
		return styleStr;
	}
	public void setStyleStr(String styleStr) {
		this.styleStr = styleStr;
	}
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
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
	 * 题干
	 * @return content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 题干
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 试题类型 1 选择 2 填空 3 问答
	 * @return style
	 */
	public Integer getStyle() {
		return style;
	}
	/**
	 * 试题类型 1 选择 2 填空 3 问答
	 */
	public void setStyle(Integer style) {
		this.style = style;
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
	 * 选项A
	 * @return optionA
	 */
	public String getOptionA() {
		return optionA;
	}
	/**
	 * 选项A
	 */
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	/**
	 * 选项B
	 * @return optionB
	 */
	public String getOptionB() {
		return optionB;
	}
	/**
	 * 选项B
	 */
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	/**
	 * 选项C
	 * @return optionC
	 */
	public String getOptionC() {
		return optionC;
	}
	/**
	 * 选项C
	 */
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	/**
	 * 选项D
	 * @return optionD
	 */
	public String getOptionD() {
		return optionD;
	}
	/**
	 * 选项D
	 */
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	/**
	 * 选项E
	 * @return optionE
	 */
	public String getOptionE() {
		return optionE;
	}
	/**
	 * 选项E
	 */
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	/**
	 * 选项F
	 * @return optionF
	 */
	public String getOptionF() {
		return optionF;
	}
	/**
	 * 选项F
	 */
	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}