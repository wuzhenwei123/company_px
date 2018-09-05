package com.base.transactionDataSource;

public class DynamicDataSourceHolder<T> {
	private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

	private DynamicDataSourceHolder() {
	}

	public static void putDataSource(DynamicDataSourceGlobal dataSource) {
		holder.set(dataSource);
	}

	public static DynamicDataSourceGlobal getDataSource() {
		return holder.get();
	}

	public static void clearDataSource() {
		holder.remove();
	}
}
