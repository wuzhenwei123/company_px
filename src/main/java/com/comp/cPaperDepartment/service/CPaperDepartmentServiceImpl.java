package com.comp.cPaperDepartment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.comp.cPaperDepartment.dao.CPaperDepartmentDAO;
import com.comp.cPaperDepartment.model.CPaperDepartment;

/**
 * @author	wzw
 * @time	2018-03-23 09:54:59
 */
@Service
@Transactional
public class CPaperDepartmentServiceImpl implements CPaperDepartmentService{
	@Resource
    private CPaperDepartmentDAO cPaperDepartmentDAO;
    
    public List<CPaperDepartment> getCPaperDepartmentList(CPaperDepartment cPaperDepartment) {
        return cPaperDepartmentDAO.getCPaperDepartmentList(cPaperDepartment);
    }
    
    public List<CPaperDepartment> getCPaperDepartmentListByPage(CPaperDepartment cPaperDepartment) {
    	return cPaperDepartmentDAO.getCPaperDepartmentListByPage(cPaperDepartment);
    }

    public CPaperDepartment getCPaperDepartmentById(long id) { 
        return cPaperDepartmentDAO.getCPaperDepartmentById(id);
    }

    public long insertCPaperDepartment(CPaperDepartment cPaperDepartment){
        return cPaperDepartmentDAO.insertCPaperDepartment(cPaperDepartment);
    }

    public int updateCPaperDepartmentById(CPaperDepartment cPaperDepartment){
        return cPaperDepartmentDAO.updateCPaperDepartmentById(cPaperDepartment);
    }
    
    public int deleteCPaperDepartmentById(long id){
        return cPaperDepartmentDAO.deleteCPaperDepartmentById(id);
    }
    
    public int deleteCPaperDepartmentByPaperId(String paperId){
    	return cPaperDepartmentDAO.deleteCPaperDepartmentByPaperId(paperId);
    }
}
