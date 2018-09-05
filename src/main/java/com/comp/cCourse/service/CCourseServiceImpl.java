package com.comp.cCourse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.comp.cCourse.dao.CCourseDAO;
import com.comp.cCourse.model.CCourse;

/**
 * @author	wzw
 * @time	2018-04-03 09:47:08
 */
@Service
@Transactional
public class CCourseServiceImpl implements CCourseService{
	@Resource
    private CCourseDAO cCourseDAO;
    
    public List<CCourse> getCCourseList(CCourse cCourse) {
        return cCourseDAO.getCCourseList(cCourse);
    }

    public CCourse getCCourseById(long id) { 
        return cCourseDAO.getCCourseById(id);
    }

    public long insertCCourse(CCourse cCourse){
        return cCourseDAO.insertCCourse(cCourse);
    }

    public int updateCCourseById(CCourse cCourse){
        return cCourseDAO.updateCCourseById(cCourse);
    }
    
    public int deleteCCourseById(long id){
        return cCourseDAO.deleteCCourseById(id);
    }
}
