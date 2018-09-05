package com.comp.cDepartment.dao;

import java.util.List;

import com.comp.cDepartment.model.CDepartment;
/**
 * @author	wzw
 * @time	2018-03-22 10:36:16
 */
public interface CDepartmentDAO{
	
	public List<CDepartment> getCDepartmentList(CDepartment cDepartment);
	
	public List<CDepartment> getCDepartmentListBySort(CDepartment cDepartment);

	public CDepartment getCDepartmentById(String id);

    public long insertCDepartment(CDepartment cDepartment);

    public int updateCDepartmentById(CDepartment cDepartment);
    
    public int deleteCDepartmentById(String id);
    
    public int getCDepartmentCount(CDepartment cDepartment);
    
    public int checkDepartmentCode(CDepartment cDepartment);
}
