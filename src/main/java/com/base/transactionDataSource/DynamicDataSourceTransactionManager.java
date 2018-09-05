package com.base.transactionDataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager{
	private static final long serialVersionUID = -6409782256906776985L;
	private static final String[] SQL_PRE_STR = {"update","insert","save","add","remove","delete"};
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        boolean readOnly = definition.isReadOnly();
        if(readOnly) {
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.READ);
        } else {
        	String name = definition.getName();
        	String medName = name.substring(name.lastIndexOf(".")+1, name.length());
        	medName = medName.toLowerCase();
        	boolean isWrite = false;
        	for (String medStr : SQL_PRE_STR) {
				if(medName.startsWith(medStr)){
					isWrite = true;
					break;
				}
			}
        	if(isWrite){
        		DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.WRITE);
        	}else{
        		DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.READ);
        	}
        }
        super.doBegin(transaction, definition);
    }
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DynamicDataSourceHolder.clearDataSource();
    }
}
