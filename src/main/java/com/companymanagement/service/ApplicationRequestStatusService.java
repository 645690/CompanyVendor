package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ApplicationRequestStatus;

public interface ApplicationRequestStatusService extends BaseService {

	public void saveOrUpdate(ApplicationRequestStatus applicationRequestStatus) throws CompanyMgmtException;

	public ApplicationRequestStatus findApplicationRequestStatusByName(String name) throws CompanyMgmtException;

	void deleteByName(String name) throws CompanyMgmtException;

}
