package com.companymanagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.EmployeeDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.Account;
import com.companymanagement.model.Employee;
import com.companymanagement.service.EmployeeService;

@Service("EmployeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<Long, Employee> implements EmployeeService {

	@Autowired
	protected EmployeeDAO dao;

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
	public void saveOrUpdate(Employee employee) throws CompanyMgmtException {
		Employee findEmployee = findEmployeeByRegNo(employee.getRegNo());
		if (findEmployee != null) {
			findEmployee.setName(employee.getName());
			findEmployee.setAccount(employee.getAccount());
			findEmployee.setAge(employee.getAge());
			findEmployee.setContact(employee.getContact());
			findEmployee.setDepartment(employee.getDepartment());
			findEmployee.setRegNo(employee.getRegNo());
			dao.merge(findEmployee);
		} else {
			dao.persist(employee);
		}
	}

	@Override
	public void deleteByRegNo(Employee employee) throws CompanyMgmtException {
		Employee findEmployee = findEmployeeByRegNo(employee.getRegNo());
		if (findEmployee != null) {
			dao.remove(findEmployee);
		}
	}
	
	@Override
	public Employee findEmployeeByAccount(Account account) throws CompanyMgmtException {
		Map<String, Account> queryParams = new HashMap<String, Account>();
		queryParams.put("account", account);

		List<Employee> employees = findByNamedQueryAndNamedParams("Employee.findByAccount", queryParams);
		if (employees.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_EMPLOYEES_BY_SAME_ID");
		}
		if (employees.size() == 0) {
			return null;
		}
		return employees.get(0);
	}
	

	@Override
	public Employee findEmployeeByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<Employee> employees = findByNamedQueryAndNamedParams("Employee.findRegNo", queryParams);
		if (employees.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_EMPLOYEES_BY_SAME_NAME");
		}
		if (employees.size() == 0) {
			return null;
		}
		return employees.get(0);
	}

}
