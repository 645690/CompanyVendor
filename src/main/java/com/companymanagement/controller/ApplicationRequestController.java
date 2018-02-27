package com.companymanagement.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.companymanagement.model.Account;
import com.companymanagement.model.ApplicationCategory;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Company;
import com.companymanagement.model.Misc;
import com.companymanagement.model.Vendor;
import com.companymanagement.notification.NotificationService;
import com.companymanagement.service.ApplicationCategoryService;
import com.companymanagement.service.ApplicationRequestService;
import com.companymanagement.service.ApplicationStatusService;
import com.companymanagement.service.CompanyService;

@Controller
public class ApplicationRequestController {

	@Autowired
	ApplicationRequestService requestService;

	@Autowired
	ApplicationCategoryService categoryService;

	@Autowired
	ApplicationStatusService statusService;

	@Autowired
	CompanyService companyService;

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/r_application", method = RequestMethod.GET)
	public ModelAndView requestApplication(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;
		try {
		List<Company> companyList;
		List<ApplicationCategory> categoryList;
		if (session.getAttribute("compList") == null) {
			companyList = companyService.findAll();
			session.setAttribute("compList", companyList);
			HashMap<String, Company> companyHashMap = new HashMap<String, Company>();
			for (Company company : companyList) {
				companyHashMap.put(String.valueOf(company.getRegNo()), company);
			}
			session.setAttribute("companyHashMap", companyHashMap);
		} else {
			companyList = (List<Company>) session.getAttribute("compList");
		}
		if (session.getAttribute("categoryList") == null) {
			categoryList = categoryService.findAll();
			session.setAttribute("categoryList", categoryList);
			HashMap<String, ApplicationCategory> categoryHashMap = new HashMap<String, ApplicationCategory>();
			for (ApplicationCategory category : categoryList) {
				categoryHashMap.put(category.getCategoryName(), category);
			}
			session.setAttribute("categoryHashMap", categoryHashMap);

		} else {
			categoryList = (List<ApplicationCategory>) session.getAttribute("categoryList");
		}
		System.out.println(companyList);
		System.out.println(categoryList);
		mav = new ModelAndView("request_application");
		mav.addObject("compList", companyList);
		mav.addObject("categoryList", categoryList);
		mav.addObject("request", new Misc());
		}catch (Exception e) {
					String url = "error";
					mav = new ModelAndView(url);
					mav.addObject("message", "request application 1");
				}
		return mav;
	}

	@RequestMapping(value = "/r_application", method = RequestMethod.POST)
	public ModelAndView requestCreation(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("request") Misc requestApp, RedirectAttributes redir) throws Exception {
		ModelAndView mav = null;
		try {
		HashMap<String, ApplicationCategory> categoryHashMap = (HashMap<String, ApplicationCategory>) session
				.getAttribute("categoryHashMap");
		HashMap<String, Company> companyHashMap = (HashMap<String, Company>) session.getAttribute("companyHashMap");

		ApplicationStatus appStatus = statusService.findbyUniqueStatus("pending");
		ApplicationRequest newRequestApp = new ApplicationRequest();
		ApplicationCategory category = categoryHashMap.get(requestApp.getType());
		Company company = companyHashMap.get(requestApp.getName());
		Vendor vendor = (Vendor) session.getAttribute("vendor");
		Account acc = (Account) session.getAttribute("account");
		String applicationNo = String.valueOf(acc.getId()) + String.valueOf(company.getId())
				+ String.valueOf(category.getId());

		if (requestService.findbyUniqueRequest(applicationNo) == null) {
			newRequestApp.setApplicationNo(applicationNo);
			newRequestApp.setAppType(category);
			newRequestApp.setCompany(company);
			newRequestApp.setVendor(vendor);
			newRequestApp.setAppStatus(appStatus);
			System.out.println(newRequestApp);
			requestService.saveOrUpdate(newRequestApp);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Application Request Sent",
					"Application: " + applicationNo + " has been created.");
			redir.addFlashAttribute("message", "Application created successfully");
			return new ModelAndView("redirect:vendor");
		} else {
			redir.addFlashAttribute("message", "Application existed.");
			return new ModelAndView("redirect:vendor");
		}
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "request application 2");
		}
		 return mav;
	}

	@RequestMapping(value = "/request_application", method = RequestMethod.GET)
	public ModelAndView companyRequestApplication(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(required = false, name = "applicationNo") String applicationNo,
			@RequestParam(required = false, name = "status") String status, RedirectAttributes redir) throws Exception {
		ModelAndView mav = null;
		try {
		if (applicationNo != null && status != null) {
			ApplicationRequest existingRequest = requestService.findbyUniqueRequest(applicationNo);
			ApplicationStatus existingStatus = statusService.findbyUniqueStatus(status);
			requestService.updateStatus(existingRequest, existingStatus);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Application Request Status Updated",
					"Application: " + applicationNo + " has been " + status);
			redir.addFlashAttribute("error", "The request is updated successfully.");
			return new ModelAndView("redirect:company");
		} else {
			List<ApplicationRequest> requestList;
			mav = new ModelAndView("company_request_application");
			Company userCompany = (Company) session.getAttribute("company");
			System.out.println(userCompany.getVenList());
			ApplicationStatus appStatus = statusService.findbyUniqueStatus("pending");
			requestList = requestService.findRequestByCompanyAndStatus(userCompany, appStatus);
			mav.addObject("requestList", requestList);
		}
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "request application 3");
		}
		return mav;

	}

}
