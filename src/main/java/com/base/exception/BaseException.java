package com.base.exception;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8335085163090129670L;
	private int code;
	private String msg;
	public BaseException(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
