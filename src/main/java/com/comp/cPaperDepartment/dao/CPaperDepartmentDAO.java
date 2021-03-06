package com.comp.cPaperDepartment.dao;

import java.util.List;

import com.comp.cPaperDepartment.model.CPaperDepartment;
/**
 * @author	wzw
 * @time	2018-03-23 09:54:58
 */
public interface CPaperDepartmentDAO{
	
	public List<CPaperDepartment> getCPaperDepartmentList(CPaperDepartment cPaperDepartment);
	
	public List<CPaperDepartment> getCPaperDepartmentListByPage(CPaperDepartment cPaperDepartment);

	public CPaperDepartment getCPaperDepartmentById(long id);

    public long insertCPaperDepartment(CPaperDepartment cPaperDepartment);

    public int updateCPaperDepartmentById(CPaperDepartment cPaperDepartment);
    
    public int deleteCPaperDepartmentById(long id);
    
    public int deleteCPaperDepartmentByPaperId(String paperId);
}
