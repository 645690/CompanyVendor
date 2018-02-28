package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.AccountRole;
import com.companymanagement.model.Department;

public interface DepartmentService extends BaseService {

	Department findDepartment(String Department);

	void saveOrUpdate(Department Department);

	List<Department> findAllAllowedDepartments() throws CompanyMgmtException;

}
