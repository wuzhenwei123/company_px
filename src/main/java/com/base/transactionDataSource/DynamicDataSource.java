package com.base.transactionDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private Object writeDataSource; // 写数据源
	private List<Object> readDataSourceList; // 读数据源
	private static List<Object> localDatasourceStore = new ArrayList<Object>();
	private static Random random = new Random();
	@Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), writeDataSource);
        if(readDataSourceList != null && readDataSourceList.size()!=0) {
        	for (int i =0;i<readDataSourceList.size();i++) {
        		Object object = readDataSourceList.get(i);
        		targetDataSources.put(DynamicDataSourceGlobal.READ.name()+"_"+i, object);
        		localDatasourceStore.add(object);
			}
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
	@Override
	protected Object determineCurrentLookupKey() {
		DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
		if (dynamicDataSourceGlobal == null
				|| dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE) {
			return DynamicDataSourceGlobal.WRITE.name();
		}
		String name = DynamicDataSourceGlobal.READ.name();
		if(localDatasourceStore.size()!=0){
			int size = localDatasourceStore.size();
			int randomNum = random.nextInt(size);
			name = name+"_"+randomNum;
		}
		return name;
	}
	public Object getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(Object writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	public List<Object> getReadDataSourceList() {
		return readDataSourceList;
	}

	public void setReadDataSourceList(List<Object> readDataSourceList) {
		this.readDataSourceList = readDataSourceList;
	}
}
