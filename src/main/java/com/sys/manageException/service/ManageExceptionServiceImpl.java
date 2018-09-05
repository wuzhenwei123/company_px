package com.sys.manageException.service;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sys.manageException.dao.ManageExceptionDao;
import com.sys.manageException.model.ManageException;
import com.base.utils.ResponseList;

/**
 * @author	keeny
 * @time	2017-03-23 13:11:26
 */
@Service
public class ManageExceptionServiceImpl implements ManageExceptionService{
	@Resource
    private ManageExceptionDao manageExceptionDao;
    
    public ResponseList<ManageException> getManageExceptionList(ManageException manageException) {
        return manageExceptionDao.getManageExceptionList(manageException);
    }
    
    public List<ManageException> getManageExceptionBaseList(ManageException manageException) {
        return manageExceptionDao.getManageExceptionBaseList(manageException);
    }
    
    public int getManageExceptionListCount(ManageException manageException) {
        return manageExceptionDao.getManageExceptionListCount(manageException);
    }

    public ManageException getManageException(ManageException manageException) { 
        return manageExceptionDao.getManageException(manageException);
    }

    public int insertManageException(ManageException manageException){
        return manageExceptionDao.insertManageException(manageException);
    }

    public int updateManageException(ManageException manageException){
        return manageExceptionDao.updateManageException(manageException);
    }
    
    public int removeManageException(ManageException manageException){
        return manageExceptionDao.removeManageException(manageException);
    }
    
}
