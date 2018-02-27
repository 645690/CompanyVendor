package com.companymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.Account;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.ServiceRequestApplicationService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.VendorService;

@Controller
public class ServiceRequestApplicationController {

	@Autowired
	ServiceRequestService serviceRequestService;

	@Autowired
	ServiceRequestApplicationService serviceRequestApplicationService;

	@Autowired
	VendorService vendorService;

	@RequestMapping(value = "/viewServiceRequestApplications", method = RequestMethod.GET)
	public ModelAndView showServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo) {
		ModelAndView mav = new ModelAndView("viewServiceRequestApplications");
		List<ServiceRequestApplication> sraList = serviceRequestService.findServiceRequestApplicationsByRegNo(srRegNo);
		mav.addObject("serviceRequestAppList", sraList);
		ServiceRequest serviceRequest = serviceRequestService.findServiceRequestByRegNo(srRegNo);
		mav.addObject("serviceRequest", serviceRequest);
		return mav;
	}

	@RequestMapping(value = "/submitServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView submitServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo) {
		ModelAndView mav = new ModelAndView("submitServiceRequestApplication");
		mav.addObject("serviceRequestApplication", new ServiceRequestApplication());
		mav.addObject("serviceRequestRegNo", srRegNo);
		return mav;
	}

	@RequestMapping(value = "/createNewServiceRequestApplication", method = RequestMethod.POST)
	public ModelAndView createServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo,
			@ModelAttribute("serviceRequestApplication") ServiceRequestApplication srApp,
			@SessionAttribute("account") Account account) {
		Vendor vendor = vendorService.findVendorByAccount(account);
		srApp.setVendor(vendor);
		serviceRequestService.addServiceRequestApplication(srRegNo, srApp);
		ModelAndView mav = new ModelAndView("redirect:vendor");
		return mav;
	}

	@RequestMapping(value = "/acceptServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView acceptServiceRequestApplication(
			@RequestParam(required = false, name = "srAppRegNo") Long srAppRegNo,
			@RequestParam(required = false, name = "srRegNo") Long srRegNo) {
		serviceRequestApplicationService.acceptServiceRequestApplication(srRegNo, srAppRegNo);
		ModelAndView mav = new ModelAndView("redirect:company");
		return mav;
	}

	@RequestMapping(value = "/rejectServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView rejectServiceRequestApplication(
			@RequestParam(required = false, name = "srAppRegNo") Long srAppRegNo,
			@RequestParam(required = false, name = "srRegNo") Long srRegNo) {
		serviceRequestApplicationService.rejectServiceRequestApplication(srRegNo, srAppRegNo);
		ModelAndView mav = new ModelAndView("redirect:company");
		return mav;
	}

}
