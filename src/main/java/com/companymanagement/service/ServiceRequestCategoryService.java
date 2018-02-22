package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ServiceRequestCategory;

public interface ServiceRequestCategoryService extends BaseService {

	public void saveOrUpdate(ServiceRequestCategory serviceRequestCategory) throws CompanyMgmtException;

	public ServiceRequestCategory findServiceRequestCategoryByName(String name) throws CompanyMgmtException;

	void deleteByName(String name) throws CompanyMgmtException;

}
