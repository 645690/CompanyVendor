package com.company.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.model.ServiceRequestCategory;
import com.companymanagement.model.ServiceRequestStatus;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.ServiceRequestApplicationService;
import com.companymanagement.service.ServiceRequestCategoryService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.ServiceRequestStatusService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class ServiceRequestTest {

	@Autowired
	ServiceRequestService srs;

	@Autowired
	ServiceRequestStatusService srss;

	@Autowired
	ServiceRequestCategoryService srcs;

	@Autowired
	ServiceRequestApplicationService sras;

	@Autowired
	CompanyService companyService;

	@Test
	@Rollback(value = false)
	public void createCatandStatus() {
		ServiceRequestStatus srStatus = new ServiceRequestStatus("Pending");
		srss.saveOrUpdate(srStatus);
		ServiceRequestStatus srStatus2 = new ServiceRequestStatus("Accepted");
		srss.saveOrUpdate(srStatus2);

		ServiceRequestCategory srCat = new ServiceRequestCategory("IT");
		srcs.saveOrUpdate(srCat);
		ServiceRequestCategory srCat2 = new ServiceRequestCategory("HR");
		srcs.saveOrUpdate(srCat2);
	}

	@Test
	@Rollback(value = false)
	public void create() {
		ServiceRequest sr = new ServiceRequest();
		sr.setName("Sr 1");
		sr.setRegNo(1L);
		ServiceRequest sr2 = new ServiceRequest();
		sr2.setName("Sr 2");
		sr2.setRegNo(2L);

		// ServiceRequestStatus srStatus = new ServiceRequestStatus("Pending");
		// srss.saveOrUpdate(srStatus);
		// ServiceRequestStatus srStatus2 = new ServiceRequestStatus("Accepted");
		// srss.saveOrUpdate(srStatus2);
		//
		// ServiceRequestCategory srCat = new ServiceRequestCategory("IT");
		// srcs.saveOrUpdate(srCat);
		// ServiceRequestCategory srCat2 = new ServiceRequestCategory("HR");
		// srcs.saveOrUpdate(srCat2);
		//
		// ServiceRequestApplication srApp = new ServiceRequestApplication(1L);
		// ServiceRequestApplication srApp2 = new ServiceRequestApplication(2L);

		ServiceRequestApplication srApp = sras.findServiceRequestApplicationByRegNo(1L);
		ServiceRequestApplication srApp2 = sras.findServiceRequestApplicationByRegNo(2L);

		ServiceRequestStatus srStatus = srss.findServiceRequestStatusByName("pending");
		ServiceRequestStatus srStatus2 = srss.findServiceRequestStatusByName("accepted");

		ServiceRequestCategory srCat = srcs.findServiceRequestCategoryByName("IT");
		ServiceRequestCategory srCat2 = srcs.findServiceRequestCategoryByName("HR");

		sr.setStatus(srStatus);
		sr.setCategory(srCat);
		sr.addServiceRequestApplication(srApp);

		sr2.setStatus(srStatus);
		sr2.setCategory(srCat);
		sr2.addServiceRequestApplication(srApp2);

		srs.saveOrUpdate(sr);
		srs.saveOrUpdate(sr2);
	}

	@Test
	@Rollback(value = false)
	public void update() {
		ServiceRequest sr = srs.findServiceRequestByRegNo(12L);
		ServiceRequest sr2 = srs.findServiceRequestByRegNo(13L);

		ServiceRequestStatus srStatus = srss.findServiceRequestStatusByName("pending");
		ServiceRequestStatus srStatus2 = srss.findServiceRequestStatusByName("accepted");

		ServiceRequestCategory srCat = srcs.findServiceRequestCategoryByName("IT");
		ServiceRequestCategory srCat2 = srcs.findServiceRequestCategoryByName("HR");

		ServiceRequestApplication srApp = sras.findServiceRequestApplicationByRegNo(1L);
		ServiceRequestApplication srApp2 = sras.findServiceRequestApplicationByRegNo(2L);

		sr.setStatus(srStatus);
		sr2.setStatus(srStatus);

		sr.setCategory(srCat);
		sr2.setCategory(srCat);

		sr.setCompany(companyService.findCompanyByRegNo(4L));
		sr2.setCompany(companyService.findCompanyByRegNo(4L));

		sr.addServiceRequestApplication(srApp);
		sr2.addServiceRequestApplication(srApp2);

		srs.saveOrUpdate(sr);
		srs.saveOrUpdate(sr2);
	}

}
