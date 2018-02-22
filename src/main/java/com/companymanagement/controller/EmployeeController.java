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
import com.companymanagement.model.Employee;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.EmployeeService;
import com.companymanagement.service.ServiceRequestService;

@Controller
@SessionAttributes("account")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	AccountService accountService;

	@Autowired
	ServiceRequestService serviceRequestService;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView showCompany(@SessionAttribute("account") Account account) {
		ModelAndView mav = new ModelAndView("company");
		List<ServiceRequest> srList = serviceRequestService.findAll();
		mav.addObject("serviceRequestList", srList);
		return mav;
	}

	@RequestMapping(value = "/employee/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(@SessionAttribute("account") Account account) {
		ModelAndView mav = new ModelAndView("employeeprofile");
		Employee employee = employeeService.findEmployeeByAccount(account);
		mav.addObject("employee", employee);
		return mav;
	}

}
