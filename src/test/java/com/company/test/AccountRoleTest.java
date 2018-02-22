package com.company.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.AccountRole;
import com.companymanagement.service.AccountRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class AccountRoleTest {

	@Autowired
	AccountRoleService accountRoleService;

	@Test
	@Rollback(value = false)
	public void createRole() {
		AccountRole ar1 = new AccountRole("Admin");
		AccountRole ar2 = new AccountRole("Vendor");
		AccountRole ar3 = new AccountRole("User");
		AccountRole ar4 = new AccountRole("Company");
		
		
		accountRoleService.saveOrUpdate(ar1);
		accountRoleService.saveOrUpdate(ar2);
		accountRoleService.saveOrUpdate(ar3);
		accountRoleService.saveOrUpdate(ar4);
	}

}
