package com.company.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.Account;
import com.companymanagement.model.Company;
import com.companymanagement.model.Employee;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class EmployeeServiceTest {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AccountRoleService accountRoleService;

	@Autowired
	CompanyService companyService;
	
	@Test
	@Rollback(value = false)
	public void create() {
		Long id = 1L;
		String name = "john1";
		Employee employee = new Employee(id, name);
		employee.setAge(22);
		employee.setContact("Contact 1");
		
		Account acc = new Account("test", "test");
		acc.setAccountRole(accountRoleService.findAccountRole("Company"));
		employee.setAccount(acc);
		
		Company com = companyService.findCompanyByRegNo(4L);
		employee.setCompany(com);
		
		employeeService.saveOrUpdate(employee);
		Employee findEmployee = employeeService.findEmployeeByRegNo(id);
		assertEquals(employee, findEmployee);
	}


	@Test
	@Rollback(value = false)
	public void update() {
		String name = "john10";
		Employee employee = new Employee(8L, "John10");
		employeeService.saveOrUpdate(employee);
		Employee findEmployee = employeeService.findEmployeeByRegNo(8L);
		assertEquals(employee, findEmployee);
	}

	@Test
	@Rollback(value = false)
	public void delete() {
		Long id = 8L;
		String name = "john10";
		Employee employee = new Employee(id, name);
		employeeService.saveOrUpdate(employee);
		employeeService.deleteByRegNo(employee);
		Employee venUpdated = (Employee) employeeService.findEmployeeByRegNo(id);
		assertEquals(null, venUpdated);
	}
}
