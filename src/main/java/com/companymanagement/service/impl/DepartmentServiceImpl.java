package com.companymanagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.DepartmentDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.Department;
import com.companymanagement.service.DepartmentService;

@Service("DepartmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Long, Department> implements DepartmentService {

	@Autowired
	protected DepartmentDAO dao;

	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) dao);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		dao.setEntityManager(entityManager);
	}

	@Override
	@Transactional
	public void saveOrUpdate(Department department) throws CompanyMgmtException {
		Department findDepartment = findDepartment(department.getName());
		if (findDepartment != null) {
			findDepartment.setName(department.getName());
			dao.merge(findDepartment);
		} else {
			dao.persist(department);
		}
	}

	@Override
	public Department findDepartment(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<Department> department = findByNamedQueryAndNamedParams("Department.findName", queryParams);
		if (department.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_DEPARTMENT_BY_SAME_NAME");
		}
		if (department.size() == 0) {
			return null;
		}
		return department.get(0);
	}

	@Override
	public List<Department> findAllAllowedDepartments() throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		List<Department> departments = findByNamedQueryAndNamedParams("Department.findAllAllowed", queryParams);
		if (departments.size() == 0) {
			return null;
		}
		return departments;
	}

}
