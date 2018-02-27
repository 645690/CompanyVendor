package com.companymanagement.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.Account;
import com.companymanagement.model.Employee;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.service.EmployeeService;
import com.companymanagement.service.ServiceRequestCategoryService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.ServiceRequestStatusService;

@Controller
public class ServiceRequestController {

	@Autowired
	ServiceRequestService serviceRequestService;

	@Autowired
	ServiceRequestStatusService srss;

	@Autowired
	ServiceRequestCategoryService srcs;

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/createServiceRequest", method = RequestMethod.GET)
	public ModelAndView createServiceRequest() {
		ModelAndView mav = new ModelAndView("createServiceRequest");
		mav.addObject("serviceRequest", new ServiceRequest());
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/createNewServiceRequest", method = RequestMethod.POST)
	public ModelAndView createNewServiceRequest(@ModelAttribute("serviceRequest") ServiceRequest serviceRequest,
			@SessionAttribute("account") Account account) {
		if (account.getAccountRole().getName().equalsIgnoreCase("employee")) {
			Employee employee = employeeService.findEmployeeByAccount(account);
			serviceRequest.setDepartment(employee.getDepartment());
		}
		serviceRequestService.saveOrUpdate(serviceRequest);
		ModelAndView mav = new ModelAndView("redirect:company");
		return mav;
	}

}
