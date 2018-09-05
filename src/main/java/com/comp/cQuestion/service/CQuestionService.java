package com.comp.cQuestion.service;

import java.util.List;
import com.comp.cQuestion.model.CQuestion;

/**
 * @author	wzw
 * @time	2018-03-23 11:36:26
 */
public interface CQuestionService {
	
	public List<CQuestion> getCQuestionList(CQuestion cQuestion);

	public CQuestion getCQuestionById(String id);

    public long insertCQuestion(CQuestion cQuestion);

    public int updateCQuestionById(CQuestion cQuestion);
    
    public int deleteCQuestionById(String id);
}
