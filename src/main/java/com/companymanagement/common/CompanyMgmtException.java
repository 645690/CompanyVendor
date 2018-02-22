package com.companymanagement.common;

public class CompanyMgmtException extends RuntimeException {
	
	public CompanyMgmtException() {
		super();
	}

	public CompanyMgmtException(String message) {
		super(message);
	}

	public CompanyMgmtException(String message, Throwable t) {
		super(message, t);
	}

}
