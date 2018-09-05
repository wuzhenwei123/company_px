package com.comp.cUserPaperQuestion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.comp.cUserPaperQuestion.dao.CUserPaperQuestionDAO;
import com.comp.cUserPaperQuestion.model.CUserPaperQuestion;

/**
 * @author	wzw
 * @time	2018-03-30 10:54:49
 */
@Service
@Transactional
public class CUserPaperQuestionServiceImpl implements CUserPaperQuestionService{
	@Resource
    private CUserPaperQuestionDAO cUserPaperQuestionDAO;
    
    public List<CUserPaperQuestion> getCUserPaperQuestionList(CUserPaperQuestion cUserPaperQuestion) {
        return cUserPaperQuestionDAO.getCUserPaperQuestionList(cUserPaperQuestion);
    }

    public CUserPaperQuestion getCUserPaperQuestionById(long id) { 
        return cUserPaperQuestionDAO.getCUserPaperQuestionById(id);
    }

    public long insertCUserPaperQuestion(CUserPaperQuestion cUserPaperQuestion){
        return cUserPaperQuestionDAO.insertCUserPaperQuestion(cUserPaperQuestion);
    }

    public int updateCUserPaperQuestionById(CUserPaperQuestion cUserPaperQuestion){
        return cUserPaperQuestionDAO.updateCUserPaperQuestionById(cUserPaperQuestion);
    }
    
    public int deleteCUserPaperQuestionById(long id){
        return cUserPaperQuestionDAO.deleteCUserPaperQuestionById(id);
    }
}
