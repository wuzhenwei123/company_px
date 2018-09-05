package com.comp.cPaper.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;
import com.comp.cPaper.model.CPaper;
import com.comp.cQuestion.model.CQuestion;

/**
 * @author	wzw
 * @time	2018-03-23 09:21:18
 */
public interface CPaperService {
	
	public List<CPaper> getCPaperList(CPaper cPaper);

	public CPaper getCPaperById(String id);

    public long insertCPaper(CPaper cPaper);

    public int updateCPaperById(CPaper cPaper);
    
    public int deleteCPaperById(String id);
    
    public JSONObject checkExcel(Workbook book);
}
