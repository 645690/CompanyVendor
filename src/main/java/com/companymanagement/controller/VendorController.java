package com.companymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.Account;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.VendorService;

@Controller
@SessionAttributes("account")
@RequestMapping(value = "/vendor")
public class VendorController {

	@Autowired
	VendorService vendorService;

	@Autowired
	AccountService accountService;

	@Autowired
	ServiceRequestService serviceRequestService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showCompany(@SessionAttribute("account") Account account) {
		ModelAndView mav = new ModelAndView("vendor");
		List<ServiceRequest> srList = serviceRequestService.findAll();
		mav.addObject("serviceRequestList", srList);
		return mav;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(@SessionAttribute("account") Account account) {
		ModelAndView mav = new ModelAndView("vendorprofile");
		Vendor vendor = vendorService.findVendorByAccount(account);
		mav.addObject("vendor", vendor);
		return mav;
	}

}
