package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Company;
import com.companymanagement.model.Vendor;

public interface ApplicationRequestService extends BaseService{

	public void saveOrUpdate(ApplicationRequest appRequest) throws CompanyMgmtException;

	List<ApplicationRequest> findRequestByVendor(Vendor vendor) throws CompanyMgmtException;

	ApplicationRequest findbyUniqueRequest(String applicationNo) throws CompanyMgmtException;

	List<ApplicationRequest> findRequestByCompanyAndStatus(Company company, ApplicationStatus status)
			throws CompanyMgmtException;

	public void updateStatus(ApplicationRequest existingRequest, ApplicationStatus existingStatus);
}
