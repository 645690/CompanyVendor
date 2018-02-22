package com.company.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.Department;
import com.companymanagement.service.DepartmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class DepartmentTest {

	@Autowired
	DepartmentService departmentService;

	@Test
	@Rollback(value = false)
	public void createDepartment() {
		Department d1 = new Department("HR");
		Department d2 = new Department("OPERATIONS");
		Department d3 = new Department("IT");
		
		departmentService.saveOrUpdate(d1);
		departmentService.saveOrUpdate(d2);
		departmentService.saveOrUpdate(d3);
	}

}
