package com.company.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.model.Company;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.VendorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@Transactional
public class CompanyTest {

	@Autowired
	CompanyService companyService;

	@Autowired
	VendorService vendorService;

	@Test
	@Rollback(value = false)
	public void createWithVendor() {
		Long vendorId = 2L;
		Vendor vendor = new Vendor(vendorId, "Vendor 2");
		vendorService.saveOrUpdate(vendor);
		Vendor findVendor = vendorService.findVendorByRegNo(vendorId);
		assertEquals(vendor, findVendor);
		
		Long vendorId2 = 4L;
		Vendor vendor2 = new Vendor(vendorId2, "Vendor 4");
		vendorService.saveOrUpdate(vendor2);
		Vendor findVendor2 = vendorService.findVendorByRegNo(vendorId2);
		assertEquals(vendor2, findVendor2);
		
		Long vendorId3 = 5L;
		Vendor vendor3 = new Vendor(vendorId3, "Vendor 5");
		vendorService.saveOrUpdate(vendor3);
		Vendor findVendor3 = vendorService.findVendorByRegNo(vendorId3);
		assertEquals(vendor3, findVendor3);
		
		Long comId = 4L;
		String name = "john1";
		Company company = new Company(comId, name);
		companyService.saveOrUpdate(company);
		
		Company findCompany = companyService.findCompanyByRegNo(comId);
		findCompany.addVendor(findVendor);
		findCompany.addVendor(findVendor2);
		findCompany.addVendor(findVendor3);
		companyService.saveOrUpdate(findCompany);
	}

}
