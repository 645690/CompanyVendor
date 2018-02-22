package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Company;

public interface CompanyService extends BaseService {

	Company findCompanyByRegNo(Long regNo) throws CompanyMgmtException;

	void saveOrUpdate(Company company) throws CompanyMgmtException;

	void deleteByRegNo(Company company) throws CompanyMgmtException;

	
}
