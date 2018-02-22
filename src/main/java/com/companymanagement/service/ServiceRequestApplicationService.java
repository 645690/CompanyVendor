package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ServiceRequestApplication;

public interface ServiceRequestApplicationService extends BaseService {

	public void saveOrUpdate(ServiceRequestApplication serviceRequestApplication) throws CompanyMgmtException;

	public ServiceRequestApplication findServiceRequestApplicationByRegNo(Long regNo) throws CompanyMgmtException;

	void deleteByRegNo(ServiceRequestApplication serviceRequestApplication) throws CompanyMgmtException;

}
