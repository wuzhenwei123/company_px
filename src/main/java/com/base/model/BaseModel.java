package com.base.model;

import com.github.pagehelper.PageHelper;

public class BaseModel {
	/** 取数据起始行，用于分页 **/
	private int offset;
	/** 取数据行数，用于分页 **/
	private int limit;
	/** 排序 **/
	private String sort;
	private String order;
	/**
	 * 排序
	 */
	public void orderBy() {
		PageHelper.orderBy(sort +" "+ order);
	}
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
