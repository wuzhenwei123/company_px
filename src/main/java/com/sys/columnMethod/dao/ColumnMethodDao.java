package com.sys.columnMethod.dao;

import java.util.List;

import com.sys.columnMethod.model.ColumnMethod;
import com.base.utils.ResponseList;
/**
 * @author	keeny
 * @time	2017-04-08 15:38:24
 */
public interface ColumnMethodDao{
	
	public ResponseList<ColumnMethod> getColumnMethodList(ColumnMethod columnMethod);

	public List<ColumnMethod> getColumnMethodBaseList(ColumnMethod columnMethod);

	public int getColumnMethodListCount(ColumnMethod columnMethod);
	
	public ColumnMethod getColumnMethod(ColumnMethod columnMethod);

    public int insertColumnMethod(ColumnMethod columnMethod);

    public int updateColumnMethod(ColumnMethod columnMethod);
    
    public int removeColumnMethod(ColumnMethod columnMethod);
}
