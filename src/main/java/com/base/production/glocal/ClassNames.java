package com.base.production.glocal;

public enum ClassNames {
	MODEL(1, "model", ".java"), 
	CONTROLLER(2, "controller", "Controller.java"), 
	DAO(3, "dao", "DAO.java"), 
	SERVICE(4, "service", "Service.java"), 
	SERVICE_IMPL(5, "service", "ServiceImpl.java"), 
	SQL(6, "sql", ".xml"), 
	JSP_INDEX(7, "page", "index.jsp"), 
	JSP_ADD(8, "page", "add.jsp"), 
	JSP_UPDATE(9, "page", "update.jsp");

	ClassNames(int index, String pakName, String methodName) {
		this.val = index;
		this.pakName = pakName;
		this.methodName = methodName;
	}

	public int getVal() {
		return this.val;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public String getPakName() {
		return this.pakName;
	}

	private int val;
	private String pakName;
	private String methodName;
}
