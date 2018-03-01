package com.company.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.Account;
import com.companymanagement.model.AccountRole;
import com.companymanagement.model.ApplicationCategory;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Department;
import com.companymanagement.model.NotificationPreferedType;
import com.companymanagement.model.ServiceRequestStatus;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.ApplicationCategoryService;
import com.companymanagement.service.ApplicationRequestService;
import com.companymanagement.service.ApplicationStatusService;
import com.companymanagement.service.DepartmentService;
import com.companymanagement.service.NotificationPreferedTypeService;
import com.companymanagement.service.ServiceRequestStatusService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class A_Initialization {

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRoleService accountRoleService;

	@Autowired
	ApplicationRequestService requestService;

	@Autowired
	ApplicationStatusService statusService;

	@Autowired
	ApplicationCategoryService categoryService;

	@Autowired
	ServiceRequestStatusService serviceRequestStatusService;

	@Autowired
	NotificationPreferedTypeService ntpService;

	@Autowired
	DepartmentService departmentService;

	@Test
	@Rollback(value = false)
	public void initialize() {
		createRole();
		createAccount();
		testCategoryCreation();
		testStatusCreation();
		testSRStatusCreation();
		notificationPreferedType();
		departmentTest();
	}

	@Test
	@Rollback(value = false)
	public void createRole() {
		AccountRole ar1 = new AccountRole("systemadmin");
		AccountRole ar2 = new AccountRole("vendor");
		AccountRole ar3 = new AccountRole("user");
		AccountRole ar4 = new AccountRole("companyadmin");
		AccountRole ar5 = new AccountRole("employee");

		accountRoleService.saveOrUpdate(ar1);
		accountRoleService.saveOrUpdate(ar2);
		accountRoleService.saveOrUpdate(ar3);
		accountRoleService.saveOrUpdate(ar4);
		accountRoleService.saveOrUpdate(ar5);
	}

	@Test
	@Rollback(value = false)
	public void createAccount() {
		Account acc = new Account("songnian.tay@cognizant.com", "s");
		AccountRole ar = accountRoleService.findAccountRole("systemadmin");
		acc.setAccountRole(ar);
		accountService.create(acc);
	}

	@Test
	@Rollback(value = false)
	public void testCategoryCreation() {
		ApplicationCategory category = new ApplicationCategory();
		category.setCategoryName("Business");
		category.setCreatedBy("ARS");
		category.setCreatedDate(new Date());
		categoryService.saveOrUpdate(category);

		ApplicationCategory category1 = new ApplicationCategory();
		category1.setCategoryName("IT");
		category1.setCreatedBy("ARS");
		category1.setCreatedDate(new Date());
		categoryService.saveOrUpdate(category1);

		ApplicationCategory category2 = new ApplicationCategory();
		category2.setCategoryName("HR");
		category2.setCreatedBy("ARS");
		category2.setCreatedDate(new Date());
		categoryService.saveOrUpdate(category2);
	}

	@Test
	@Rollback(value = false)
	public void testStatusCreation() {
		ApplicationStatus status = new ApplicationStatus();
		status.setStatusName("approved");
		status.setCreatedBy("ARS");
		status.setCreatedDate(new Date());
		statusService.saveOrUpdate(status);

		ApplicationStatus status1 = new ApplicationStatus();
		status1.setStatusName("rejected");
		status1.setCreatedBy("ARS");
		status1.setCreatedDate(new Date());
		statusService.saveOrUpdate(status1);

		ApplicationStatus status2 = new ApplicationStatus();
		status2.setStatusName("pending");
		status2.setCreatedBy("ARS");
		status2.setCreatedDate(new Date());
		statusService.saveOrUpdate(status2);
	}

	@Test
	@Rollback(value = false)
	public void testSRStatusCreation() {
		ServiceRequestStatus srs = new ServiceRequestStatus("pending");
		serviceRequestStatusService.saveOrUpdate(srs);

		ServiceRequestStatus srs1 = new ServiceRequestStatus("accepted");
		serviceRequestStatusService.saveOrUpdate(srs1);

		ServiceRequestStatus srs2 = new ServiceRequestStatus("rejected");
		serviceRequestStatusService.saveOrUpdate(srs2);
	}

	@Test
	@Rollback(value = false)
	public void notificationPreferedType() {
		NotificationPreferedType npt = new NotificationPreferedType("SMS");
		ntpService.saveOrUpdate(npt);

		NotificationPreferedType npt1 = new NotificationPreferedType("Email");
		ntpService.saveOrUpdate(npt1);
	}

	@Test
	@Rollback(value = false)
	public void departmentTest() {
		Department d = new Department("IT");
		Department d1 = new Department("HR");
		Department d2 = new Department("Any");
		Department d3 = new Department("Operations");

		departmentService.saveOrUpdate(d);
		departmentService.saveOrUpdate(d1);
		departmentService.saveOrUpdate(d2);
		departmentService.saveOrUpdate(d3);
	}

}
