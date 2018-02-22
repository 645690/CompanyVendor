package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Account;
import com.companymanagement.model.Vendor;

public interface VendorService extends BaseService {

	Vendor findVendorByRegNo(Long regNo) throws CompanyMgmtException;

	void saveOrUpdate(Vendor vendor) throws CompanyMgmtException;

	void deleteByRegNo(Vendor vendor) throws CompanyMgmtException;

	Vendor findVendorByAccount(Account account) throws CompanyMgmtException;

	List<Vendor> findVendorsByStatus(String status) throws CompanyMgmtException;

	void acceptByRegNo(Long regNo) throws CompanyMgmtException;

	void rejectByRegNo(Long regNo) throws CompanyMgmtException;
}
