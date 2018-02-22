package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Account;
import com.companymanagement.model.Company;
import com.companymanagement.model.Vendor;

public interface CompanyService extends BaseService {

	Company findCompanyByRegNo(Long regNo) throws CompanyMgmtException;

	void saveOrUpdate(Company company) throws CompanyMgmtException;

	void deleteByRegNo(Company company) throws CompanyMgmtException;

	List<Company> findCompaniesByStatus(String status) throws CompanyMgmtException;

	Company findCompanyByAccount(Account account) throws CompanyMgmtException;

	void acceptByRegNo(Long regNo) throws CompanyMgmtException;

	void rejectByRegNo(Long regNo) throws CompanyMgmtException;

	
}
