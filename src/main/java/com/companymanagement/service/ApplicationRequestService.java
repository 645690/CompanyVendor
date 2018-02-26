package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ServiceRequest;

public interface ApplicationRequestService extends BaseService {

	public void saveOrUpdate(ApplicationRequest applicationRequest) throws CompanyMgmtException;

	public ApplicationRequest findApplicationRequestByRegNo(Long regNo) throws CompanyMgmtException;

	void deleteByRegNo(ApplicationRequest applicationRequest) throws CompanyMgmtException;

}
