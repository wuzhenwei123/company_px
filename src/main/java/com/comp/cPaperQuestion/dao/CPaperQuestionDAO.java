package com.comp.cPaperQuestion.dao;

import java.util.List;

import com.comp.cPaperQuestion.model.CPaperQuestion;
/**
 * @author	wzw
 * @time	2018-03-23 11:36:52
 */
public interface CPaperQuestionDAO{
	
	public List<CPaperQuestion> getCPaperQuestionList(CPaperQuestion cPaperQuestion);
	
	public List<CPaperQuestion> getCPaperQuestionListBySortId(CPaperQuestion cPaperQuestion);

	public CPaperQuestion getCPaperQuestionById(long id);

    public long insertCPaperQuestion(CPaperQuestion cPaperQuestion);

    public int updateCPaperQuestionById(CPaperQuestion cPaperQuestion);
    
    public int deleteCPaperQuestionById(long id);
    
    public int getCPaperQuestionCount(CPaperQuestion cPaperQuestion);
}
