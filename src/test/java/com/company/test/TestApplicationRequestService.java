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
import com.companymanagement.model.ApplicationCategory;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Company;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.ApplicationCategoryService;
import com.companymanagement.service.ApplicationRequestService;
import com.companymanagement.service.ApplicationStatusService;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.VendorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContext.xml"})
@Transactional
public class TestApplicationRequestService {
	
	@Autowired
	ApplicationRequestService requestService;
	
	@Autowired
	ApplicationStatusService statusService;
	
	@Autowired
	ApplicationCategoryService categoryService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	AccountService accService;
	
	
	
	@Test
	@Rollback(value=false)
	public void testCategoryCreation(){
		ApplicationCategory category = new ApplicationCategory();
		category.setCategoryName("HR");
		category.setCreatedBy("ARS");
		category.setCreatedDate(new Date());
		categoryService.saveOrUpdate(category);
	}
	
	@Test
	@Rollback(value=false)
	public void testStatusCreation(){
		ApplicationStatus status = new ApplicationStatus();
		status.setStatusName("rejected");
		status.setCreatedBy("ARS");
		status.setCreatedDate(new Date());
		statusService.saveOrUpdate(status);
	}
	
//	@Test
//	@Rollback(value=false)
//	public void testRequestCreation(){
//		ApplicationStatus appStatus = statusService.findbyUniqueStatus("validation required");
//		ApplicationCategory appType = categoryService.findbyUniqueCategory("it");
//		Company comp = companyService.findbyUniqueCompany("846389");
//		Vendor vendor = vendorService.findbyUniqueVendor("6587623");
//		ApplicationRequest request = new ApplicationRequest();
//		request.setApplicationNo("12345");
//		request.setAppStatus(appStatus);
//		request.setAppType(appType);
//		request.setCompany(comp);
//		request.setVendor(vendor);
//		request.setCreatedBy("AS");
//		request.setCreatedDate(new Date());
//		requestService.saveOrUpdate(request);
//	}
	
	@Test
	@Rollback(value=false)
	public void testFindByStatus(){
		System.out.println(statusService.findbyUniqueStatus("pending"));
		
	}
	
	@Test
	@Rollback(value=false)
	public void testFindByCategory(){
		System.out.println(categoryService.findbyUniqueCategory("it"));
		
	}

	@Test
	@Rollback(value=false)
	public void testFindByRequest(){
		System.out.println(requestService.findbyUniqueRequest("12345"));
		
	}
	
//	@Test
//	@Rollback(value=false)
//	public void testFindByAccount(){
////		System.out.println(vendorService.findbyUniqueVendor("6587623"));
//		Account acc = accService.findbyUniqueAccount("emp6");
//		Vendor vendor = vendorService.findVendorByAccountId(acc);
////		requestService.findRequestByVendor(vendor);
//		System.out.println(requestService.findRequestByVendor(vendor));
//		
//	}
	
//	@Test
//	@Rollback(value=false)
//	public void testFindRequestByCompanyAndStatus(){
//		Company company = companyService.findbyUniqueCompany("C9328173");
//		ApplicationStatus status = statusService.findbyUniqueStatus("Pending");
//		System.out.println(requestService.findRequestByCompanyAndStatus(company, status));
//		
//	}
	
	@Test
	@Rollback(value=false)
	public void testUpdateStatus(){
		ApplicationRequest request = requestService.findbyUniqueRequest("7821");
		ApplicationStatus status = statusService.findbyUniqueStatus("approved");
		requestService.updateStatus(request, status);
		
	}

}
