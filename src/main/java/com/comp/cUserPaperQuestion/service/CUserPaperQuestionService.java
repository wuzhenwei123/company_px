package com.comp.cUserPaperQuestion.service;

import java.util.List;
import com.comp.cUserPaperQuestion.model.CUserPaperQuestion;

/**
 * @author	wzw
 * @time	2018-03-30 10:54:49
 */
public interface CUserPaperQuestionService {
	
	public List<CUserPaperQuestion> getCUserPaperQuestionList(CUserPaperQuestion cUserPaperQuestion);

	public CUserPaperQuestion getCUserPaperQuestionById(long id);

    public long insertCUserPaperQuestion(CUserPaperQuestion cUserPaperQuestion);

    public int updateCUserPaperQuestionById(CUserPaperQuestion cUserPaperQuestion);
    
    public int deleteCUserPaperQuestionById(long id);
}
