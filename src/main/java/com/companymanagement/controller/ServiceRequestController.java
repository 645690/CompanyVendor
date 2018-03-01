package com.companymanagement.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.Account;
import com.companymanagement.model.Company;
import com.companymanagement.model.Department;
import com.companymanagement.model.Employee;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.EmployeeService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.ServiceRequestStatusService;

@Controller
public class ServiceRequestController {

	@Autowired
	ServiceRequestService serviceRequestService;

	@Autowired
	ServiceRequestStatusService srss;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/createServiceRequest", method = RequestMethod.GET)
	public ModelAndView createServiceRequest() {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("createServiceRequest");
			mav.addObject("serviceRequest", new ServiceRequest());
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "SRcontroller createServiceRequest");
		}
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/createNewServiceRequest", method = RequestMethod.POST)
	public ModelAndView createNewServiceRequest(@ModelAttribute("serviceRequest") ServiceRequest serviceRequest,
			@SessionAttribute("account") Account account) {
		ModelAndView mav = null;
		if (account.getAccountRole().getName().equalsIgnoreCase("employee")) {
			Employee employee = employeeService.findEmployeeByAccount(account);
			serviceRequest.setDepartment(employee.getDepartment());
			serviceRequest.setCompany(employee.getCompany());
		} else {
			Company company = companyService.findCompanyByAccount(account);
			serviceRequest.setCompany(company);
			serviceRequest.setDepartment(new Department("Any"));
		}
		serviceRequestService.saveOrUpdate(serviceRequest);
		mav = new ModelAndView("redirect:company");
		return mav;
	}

	@RequestMapping(value = "/viewAllAcceptedServiceRequests", method = RequestMethod.GET)
	public ModelAndView viewAllAccepted() {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("viewAllAcceptedServiceRequests");
			List<ServiceRequest> srList = serviceRequestService.findAllAcceptedServiceRequests();
			mav.addObject("serviceRequestList", srList);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error getting vendor!!");
		}
		return mav;
	}

}
