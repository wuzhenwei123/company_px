package com.base.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.comp.cDepartment.model.CDepartment;
import com.comp.cDepartment.service.CDepartmentService;


public class GetDepartmentToRedis implements ApplicationListener<ContextRefreshedEvent> {

	protected static Logger logger = Logger.getLogger(GetDepartmentToRedis.class);
	
	@Autowired
	private CDepartmentService cDepartmentService = null;
	@Autowired
	private RedisCacheClient redisCacheClient;
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
        	init();
        }
    }
	
	public void init(){
		try{
			CDepartment cDepartment = new CDepartment();
			List<CDepartment> list = cDepartmentService.getCDepartmentList(cDepartment);
			if(list!=null&&list.size()>0){
				for(CDepartment department:list){
					redisCacheClient.set(department.getCode(), department);
				}
			}
		}catch(Exception e){
			logger.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
