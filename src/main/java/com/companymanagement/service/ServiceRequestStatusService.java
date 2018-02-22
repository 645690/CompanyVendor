package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ServiceRequestStatus;

public interface ServiceRequestStatusService extends BaseService {

	public void saveOrUpdate(ServiceRequestStatus serviceRequestStatus) throws CompanyMgmtException;

	public ServiceRequestStatus findServiceRequestStatusByName(String name) throws CompanyMgmtException;

	void deleteByName(String name) throws CompanyMgmtException;

}
