package com.comp.cDepartment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.comp.cDepartment.dao.CDepartmentDAO;
import com.comp.cDepartment.model.CDepartment;

/**
 * @author	wzw
 * @time	2018-03-22 10:36:16
 */
@Service
@Transactional
public class CDepartmentServiceImpl implements CDepartmentService{
	@Resource
    private CDepartmentDAO cDepartmentDAO;
    
    public List<CDepartment> getCDepartmentList(CDepartment cDepartment) {
        return cDepartmentDAO.getCDepartmentList(cDepartment);
    }
    
    public List<CDepartment> getCDepartmentListBySort(CDepartment cDepartment) {
    	return cDepartmentDAO.getCDepartmentListBySort(cDepartment);
    }

    public CDepartment getCDepartmentById(String id) { 
        return cDepartmentDAO.getCDepartmentById(id);
    }

    public long insertCDepartment(CDepartment cDepartment){
        return cDepartmentDAO.insertCDepartment(cDepartment);
    }

    public int updateCDepartmentById(CDepartment cDepartment){
        return cDepartmentDAO.updateCDepartmentById(cDepartment);
    }
    
    public int deleteCDepartmentById(String id){
        return cDepartmentDAO.deleteCDepartmentById(id);
    }
    
    public int getCDepartmentCount(CDepartment cDepartment){
    	return cDepartmentDAO.getCDepartmentCount(cDepartment);
    }
    
    public int checkDepartmentCode(CDepartment cDepartment){
    	return cDepartmentDAO.checkDepartmentCode(cDepartment);
    }
}
