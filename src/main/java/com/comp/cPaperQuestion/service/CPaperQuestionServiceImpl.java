package com.comp.cPaperQuestion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.comp.cPaperQuestion.dao.CPaperQuestionDAO;
import com.comp.cPaperQuestion.model.CPaperQuestion;

/**
 * @author	wzw
 * @time	2018-03-23 11:36:52
 */
@Service
@Transactional
public class CPaperQuestionServiceImpl implements CPaperQuestionService{
	@Resource
    private CPaperQuestionDAO cPaperQuestionDAO;
    
    public List<CPaperQuestion> getCPaperQuestionList(CPaperQuestion cPaperQuestion) {
        return cPaperQuestionDAO.getCPaperQuestionList(cPaperQuestion);
    }
    
    public List<CPaperQuestion> getCPaperQuestionListBySortId(CPaperQuestion cPaperQuestion) {
    	return cPaperQuestionDAO.getCPaperQuestionListBySortId(cPaperQuestion);
    }

    public CPaperQuestion getCPaperQuestionById(long id) { 
        return cPaperQuestionDAO.getCPaperQuestionById(id);
    }

    public long insertCPaperQuestion(CPaperQuestion cPaperQuestion){
        return cPaperQuestionDAO.insertCPaperQuestion(cPaperQuestion);
    }

    public int updateCPaperQuestionById(CPaperQuestion cPaperQuestion){
        return cPaperQuestionDAO.updateCPaperQuestionById(cPaperQuestion);
    }
    
    public int deleteCPaperQuestionById(long id){
        return cPaperQuestionDAO.deleteCPaperQuestionById(id);
    }
    
    public int getCPaperQuestionCount(CPaperQuestion cPaperQuestion){
    	 return cPaperQuestionDAO.getCPaperQuestionCount(cPaperQuestion);
    }
}
