package com.comp.cUserPaper.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.comp.cUserPaper.dao.CUserPaperDAO;
import com.comp.cUserPaper.model.CUserPaper;

/**
 * @author	wzw
 * @time	2018-03-30 10:45:22
 */
@Service
@Transactional
public class CUserPaperServiceImpl implements CUserPaperService{
	@Resource
    private CUserPaperDAO cUserPaperDAO;
    
    public List<CUserPaper> getCUserPaperList(CUserPaper cUserPaper) {
        return cUserPaperDAO.getCUserPaperList(cUserPaper);
    }
    
    public List<CUserPaper> getCUserPaperListByDepartment(CUserPaper cUserPaper) {
    	return cUserPaperDAO.getCUserPaperListByDepartment(cUserPaper);
    }

    public CUserPaper getCUserPaperById(long id) { 
        return cUserPaperDAO.getCUserPaperById(id);
    }

    public long insertCUserPaper(CUserPaper cUserPaper){
        return cUserPaperDAO.insertCUserPaper(cUserPaper);
    }

    public int updateCUserPaperById(CUserPaper cUserPaper){
        return cUserPaperDAO.updateCUserPaperById(cUserPaper);
    }
    
    public int deleteCUserPaperById(long id){
        return cUserPaperDAO.deleteCUserPaperById(id);
    }
}
