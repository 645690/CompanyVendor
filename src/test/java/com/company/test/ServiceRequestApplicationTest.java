package com.company.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.ServiceRequestApplicationService;
import com.companymanagement.service.VendorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class ServiceRequestApplicationTest {

	@Autowired
	ServiceRequestApplicationService sras;

	@Autowired
	VendorService vendorService;

	@Test
	@Rollback(value = false)
	public void create() {
		ServiceRequestApplication srApp = new ServiceRequestApplication(1L);
		ServiceRequestApplication srApp2 = new ServiceRequestApplication(2L);

		Vendor vendor = vendorService.findVendorByRegNo(2L);
		Vendor vendor2 = vendorService.findVendorByRegNo(4L);

		srApp.setVendor(vendor);
		srApp2.setVendor(vendor2);

		sras.saveOrUpdate(srApp);
		sras.saveOrUpdate(srApp2);
	}

}
