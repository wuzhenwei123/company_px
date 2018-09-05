package com.comp.cPaper.dao;

import java.util.List;

import com.comp.cPaper.model.CPaper;
/**
 * @author	wzw
 * @time	2018-03-23 09:21:18
 */
public interface CPaperDAO{
	
	public List<CPaper> getCPaperList(CPaper cPaper);

	public CPaper getCPaperById(String id);

    public long insertCPaper(CPaper cPaper);

    public int updateCPaperById(CPaper cPaper);
    
    public int deleteCPaperById(String id);
}
