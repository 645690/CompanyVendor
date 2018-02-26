package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Account;
import com.companymanagement.model.Company;
import com.companymanagement.model.Employee;

public interface EmployeeService extends BaseService {

	public void saveOrUpdate(Employee Employee) throws CompanyMgmtException;

	public Employee findEmployeeByRegNo(Long regNo) throws CompanyMgmtException;

	public Employee findEmployeeByAccount(Account account) throws CompanyMgmtException;

	void deleteByRegNo(Employee employee) throws CompanyMgmtException;

	public List<Employee> findEmployeeByCompany(Company company);
}
