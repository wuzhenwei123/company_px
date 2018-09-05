package com.comp.cQuestion.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.comp.cQuestion.dao.CQuestionDAO;
import com.comp.cQuestion.model.CQuestion;

/**
 * @author	wzw
 * @time	2018-03-23 11:36:26
 */
@Service
@Transactional
public class CQuestionServiceImpl implements CQuestionService{
	@Resource
    private CQuestionDAO cQuestionDAO;
    
    public List<CQuestion> getCQuestionList(CQuestion cQuestion) {
        return cQuestionDAO.getCQuestionList(cQuestion);
    }

    public CQuestion getCQuestionById(String id) { 
        return cQuestionDAO.getCQuestionById(id);
    }

    public long insertCQuestion(CQuestion cQuestion){
        return cQuestionDAO.insertCQuestion(cQuestion);
    }

    public int updateCQuestionById(CQuestion cQuestion){
        return cQuestionDAO.updateCQuestionById(cQuestion);
    }
    
    public int deleteCQuestionById(String id){
        return cQuestionDAO.deleteCQuestionById(id);
    }
}
