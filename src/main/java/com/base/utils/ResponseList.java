package com.base.utils;

import java.util.ArrayList;

public class ResponseList<E> extends ArrayList<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long totalResults = 0L;

	public ResponseList() {
		this.totalResults = 0L;
	}

	public ResponseList(long paramLong) {
		this.totalResults = paramLong;
	}

	public long getTotalResults() {
		return this.totalResults;
	}

	public void setTotalResults(long paramLong) {
		this.totalResults = paramLong;
	}
}
