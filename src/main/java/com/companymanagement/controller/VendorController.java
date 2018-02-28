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
public class VendorController {

	@Autowired
	VendorService vendorService;

	@Autowired
	AccountService accountService;

	@Autowired
	ServiceRequestService serviceRequestService;

	@RequestMapping(value = "/vendor", method = RequestMethod.GET)
	public ModelAndView showCompany(@SessionAttribute("account") Account account) {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("vendor");
			List<ServiceRequest> srList = serviceRequestService.findAllPendingServiceRequests();
			mav.addObject("serviceRequestList", srList);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error getting vendor!!");
		}
		return mav;
	}

	@RequestMapping(value = "/vendorprofile", method = RequestMethod.GET)
	public ModelAndView showProfile(@SessionAttribute("account") Account account) {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("vendorprofile");
			Vendor vendor = vendorService.findVendorByAccount(account);
			mav.addObject("vendor", vendor);
		} catch (

		Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error getting vendor!!");
		}
		return mav;
	}

}
