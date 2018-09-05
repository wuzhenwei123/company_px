package com.sys.manageException.service;

import java.util.List;
import com.sys.manageException.model.ManageException;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-03-23 13:11:26
 */
public interface ManageExceptionService {

    public ResponseList<ManageException> getManageExceptionList(ManageException manageException);
    
    public List<ManageException> getManageExceptionBaseList(ManageException manageException);
    
    public int getManageExceptionListCount(ManageException manageException);

    public ManageException getManageException(ManageException manageException);

    public int insertManageException(ManageException manageException);

    public int updateManageException(ManageException manageException);
    
    public int removeManageException(ManageException manageException);
}
