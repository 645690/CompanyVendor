package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ApplicationRequestCategory;

public interface ApplicationRequestCategoryService extends BaseService {

	public void saveOrUpdate(ApplicationRequestCategory applicationRequestCategory) throws CompanyMgmtException;

	public ApplicationRequestCategory findApplicationRequestCategoryByName(String name) throws CompanyMgmtException;

	void deleteByName(String name) throws CompanyMgmtException;

}
