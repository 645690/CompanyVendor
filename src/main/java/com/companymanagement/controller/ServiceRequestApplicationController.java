package com.companymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.service.ServiceRequestService;

@Controller
public class ServiceRequestApplicationController {

	@Autowired
	ServiceRequestService serviceRequestService;

	@RequestMapping(value = "/viewServiceRequestApplications", method = RequestMethod.GET)
	public ModelAndView showServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo) {
		ModelAndView mav = new ModelAndView("viewServiceRequestApplications");
		List<ServiceRequestApplication> sraList = serviceRequestService.findServiceRequestApplicationsByRegNo(srRegNo);
		mav.addObject("serviceRequestAppList", sraList);
		return mav;
	}

	@RequestMapping(value = "/submitServiceRequestApplication", method = RequestMethod.GET)
	public ModelAndView submitServiceRequestApplications(
			@RequestParam(required = false, name = "serviceRequestRegNo") Long srRegNo) {
		ModelAndView mav = new ModelAndView("submitServiceRequestApplication");
		mav.addObject("serviceRequestApplication", new ServiceRequestApplication());
		return mav;
	}

}
