package com.companymanagement.service;

import com.companymanagement.model.AccountRole;
import com.companymanagement.model.Department;

public interface DepartmentService extends BaseService {

	Department findDepartment(String Department);

	void saveOrUpdate(Department Department);

}
