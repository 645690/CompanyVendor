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
import com.companymanagement.model.AccountRole;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class AccountTest {

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRoleService accountRoleService;

	@Test
	@Rollback(value = false)
	public void createAccount() {
		Account acc = new Account("username1", "password1");
		AccountRole ar = accountRoleService.findAccountRole("User");
		acc.setAccountRole(ar);
		accountService.create(acc);

		Account acc2 = new Account("username2", "password2");
		AccountRole ar2 = accountRoleService.findAccountRole("User");
		acc2.setAccountRole(ar2);
		accountService.create(acc2);

		Account findAcc = accountService.findAccount("username1", "password1");
		assertEquals(acc, findAcc);

		Account findAcc2 = accountService.findAccount("username2", "password2");
		assertEquals(acc2, findAcc2);

		AccountRole findAr = accountRoleService.findAccountRole("User");
		assertEquals(ar, findAr);
	}

	@Test
	@Rollback(value = false)
	public void update() {
		Account acc = new Account("username1", "password4");
		AccountRole ar = accountRoleService.findAccountRole("User");
		acc.setAccountRole(ar);
		accountService.update(acc);

		Account findAcc = accountService.findAccount("username1", "password4");
		assertEquals(acc, findAcc);
	}
}
