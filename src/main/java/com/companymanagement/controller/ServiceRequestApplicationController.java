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
import com.companymanagement.notification.NotificationService;
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

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/viewServiceRequestApplications", method = RequestMethod.GET)
	public ModelAndView showServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo) {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("viewServiceRequestApplications");
			List<ServiceRequestApplication> sraList = serviceRequestService
					.findServiceRequestApplicationsByRegNo(srRegNo);
			mav.addObject("serviceRequestAppList", sraList);
			ServiceRequest serviceRequest = serviceRequestService.findServiceRequestByRegNo(srRegNo);
			mav.addObject("serviceRequest", serviceRequest);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "SRAppcontroller viewServiceRequestApplications");
		}
		return mav;
	}

	@RequestMapping(value = "/submitServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView submitServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo) {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("submitServiceRequestApplication");
			mav.addObject("serviceRequestApplication", new ServiceRequestApplication());
			mav.addObject("serviceRequestRegNo", srRegNo);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "SRAppcontroller submitServiceRequestApplication");
		}
		return mav;
	}

	@RequestMapping(value = "/createNewServiceRequestApplication", method = RequestMethod.POST)
	public ModelAndView createServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo,
			@ModelAttribute("serviceRequestApplication") ServiceRequestApplication srApp,
			@SessionAttribute("account") Account account) throws Exception {
		ModelAndView mav = null;
		try {
			Vendor vendor = vendorService.findVendorByAccount(account);
			srApp.setVendor(vendor);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Service Request Application sent",
					"Service Request Application " + srRegNo);
			serviceRequestService.addServiceRequestApplication(srRegNo, srApp);
			mav = new ModelAndView("redirect:vendor");
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "SRAppcontroller createNewServiceRequestApplication");
		}
		return mav;
	}

	@RequestMapping(value = "/acceptServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView acceptServiceRequestApplication(
			@RequestParam(required = false, name = "srAppRegNo") Long srAppRegNo,
			@RequestParam(required = false, name = "srRegNo") Long srRegNo) throws Exception {
		ModelAndView mav = null;
		try {
			serviceRequestApplicationService.acceptServiceRequestApplication(srRegNo, srAppRegNo);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Accepted Service Request Application",
					"Service Request Application " + srAppRegNo + " accepted for Service Request: " + srRegNo);
			mav = new ModelAndView("redirect:company");
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "SRAppcontroller acceptServiceRequestApplication");
		}
		return mav;
	}

	@RequestMapping(value = "/rejectServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView rejectServiceRequestApplication(
			@RequestParam(required = false, name = "srAppRegNo") Long srAppRegNo,
			@RequestParam(required = false, name = "srRegNo") Long srRegNo) throws Exception {
		ModelAndView mav = null;
		try {
			serviceRequestApplicationService.rejectServiceRequestApplication(srRegNo, srAppRegNo);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Rejected Service Request Application",
					"Service Request Application " + srAppRegNo + " rejected for Service Request: " + srRegNo);
			mav = new ModelAndView("redirect:company");
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "SRAppcontroller rejectServiceRequestApplication");
		}
		return mav;
	}

}
