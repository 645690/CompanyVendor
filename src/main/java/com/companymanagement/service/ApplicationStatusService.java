package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ApplicationStatus;

public interface ApplicationStatusService extends BaseService{

	public void saveOrUpdate(ApplicationStatus appStatus) throws CompanyMgmtException;

	ApplicationStatus findbyUniqueStatus(String status) throws CompanyMgmtException;
}
