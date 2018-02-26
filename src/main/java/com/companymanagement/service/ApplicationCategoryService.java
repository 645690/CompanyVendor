package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ApplicationCategory;

public interface ApplicationCategoryService extends BaseService{

	public void saveOrUpdate(ApplicationCategory appCategory) throws CompanyMgmtException;

	ApplicationCategory findbyUniqueCategory(String category) throws CompanyMgmtException;
}
