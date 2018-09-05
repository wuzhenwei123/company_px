package com.comp.cUserPaper.service;

import java.util.List;
import com.comp.cUserPaper.model.CUserPaper;

/**
 * @author	wzw
 * @time	2018-03-30 10:45:22
 */
public interface CUserPaperService {
	
	public List<CUserPaper> getCUserPaperList(CUserPaper cUserPaper);
	
	public List<CUserPaper> getCUserPaperListByDepartment(CUserPaper cUserPaper);

	public CUserPaper getCUserPaperById(long id);

    public long insertCUserPaper(CUserPaper cUserPaper);

    public int updateCUserPaperById(CUserPaper cUserPaper);
    
    public int deleteCUserPaperById(long id);
}
