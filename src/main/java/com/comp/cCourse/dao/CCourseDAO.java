package com.comp.cCourse.dao;

import java.util.List;

import com.comp.cCourse.model.CCourse;
/**
 * @author	wzw
 * @time	2018-04-03 09:47:07
 */
public interface CCourseDAO{
	
	public List<CCourse> getCCourseList(CCourse cCourse);

	public CCourse getCCourseById(long id);

    public long insertCCourse(CCourse cCourse);

    public int updateCCourseById(CCourse cCourse);
    
    public int deleteCCourseById(long id);
}
