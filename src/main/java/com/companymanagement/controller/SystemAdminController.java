package com.companymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.AccountRole;
import com.companymanagement.model.Company;
import com.companymanagement.model.Department;
import com.companymanagement.model.NotificationPreferedType;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.DepartmentService;
import com.companymanagement.service.NotificationPreferedTypeService;
import com.companymanagement.service.VendorService;

@Controller
@SessionAttributes("account")
@RequestMapping(value = "/systemadmin")
public class SystemAdminController {

	@Autowired
	CompanyService companyService;

	@Autowired
	VendorService vendorService;

	@Autowired
	AccountRoleService arService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	NotificationPreferedTypeService nptService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showAllCompany() {
		ModelAndView mav = new ModelAndView("systemadmin");
		List<Company> cList = companyService.findAll();
		mav.addObject("companyList", cList);
		return mav;
	}

	@RequestMapping(value = "/viewCompany", method = RequestMethod.GET)
	public ModelAndView viewCompanyInfo() {
		ModelAndView mav = new ModelAndView("companyInfo");
		List<Company> cList = companyService.findAll();
		mav.addObject("companyList", cList);
		return mav;
	}

	@RequestMapping(value = "/viewAllVendors", method = RequestMethod.GET)
	public ModelAndView viewAllVendors() {
		ModelAndView mav = new ModelAndView("viewAllVendors");
		List<Vendor> vList = vendorService.findAll();
		mav.addObject("vendorList", vList);
		return mav;
	}

	@RequestMapping(value = "/viewPendingVendorApplicants", method = RequestMethod.GET)
	public ModelAndView viewPendingUserVendor() {
		ModelAndView mav = new ModelAndView("viewPendingVendorApplicants");
		List<Vendor> vList = vendorService.findVendorsByStatus("pending");
		mav.addObject("vendorList", vList);
		return mav;
	}

	@RequestMapping(value = "/viewVendorApplicantDetails", method = RequestMethod.GET)
	public ModelAndView viewVendorApplicantDetails(@RequestParam(required = false, name = "regNo") Long vaRegNo) {
		ModelAndView mav = new ModelAndView("viewVendorApplicantDetails");
		Vendor vendor = vendorService.findVendorByRegNo(vaRegNo);
		mav.addObject("vendor", vendor);
		return mav;
	}

	@RequestMapping(value = "/acceptVendorApplicant", method = RequestMethod.GET)
	public ModelAndView acceptVendorApplicant(@RequestParam(required = false, name = "regNo") Long vaRegNo) {
		vendorService.acceptByRegNo(vaRegNo);
		ModelAndView mav = new ModelAndView("redirect:/systemadmin/viewPendingVendorApplicants");
		return mav;
	}

	@RequestMapping(value = "/rejectVendorApplicant", method = RequestMethod.GET)
	public ModelAndView rejectVendorApplicant(@RequestParam(required = false, name = "regNo") Long vaRegNo) {
		vendorService.rejectByRegNo(vaRegNo);
		ModelAndView mav = new ModelAndView("redirect:/systemadmin/viewPendingVendorApplicants");
		return mav;
	}

	@RequestMapping(value = "/createAccountRole", method = RequestMethod.GET)
	public ModelAndView createAccountRole() {
		ModelAndView mav = new ModelAndView("createAccountRole");
		mav.addObject("accountRole", new AccountRole());
		return mav;
	}

	@RequestMapping(value = "/createAccountRole/submit", method = RequestMethod.POST)
	public ModelAndView createAccountRoleSubmit(@ModelAttribute("accountRole") AccountRole accountRole) {
		arService.saveOrUpdate(accountRole);
		ModelAndView mav = new ModelAndView("redirect:/systemadmin");
		return mav;
	}

	@RequestMapping(value = "/createDepartment", method = RequestMethod.GET)
	public ModelAndView createDepartment() {
		ModelAndView mav = new ModelAndView("createDepartment");
		mav.addObject("department", new Department());
		return mav;
	}

	@RequestMapping(value = "/createDepartment/submit", method = RequestMethod.POST)
	public ModelAndView createDepartmentSubmit(@ModelAttribute("department") Department department) {
		departmentService.saveOrUpdate(department);
		ModelAndView mav = new ModelAndView("redirect:/systemadmin");
		return mav;
	}

	@RequestMapping(value = "/createNotificationType", method = RequestMethod.GET)
	public ModelAndView createNotification() {
		ModelAndView mav = new ModelAndView("createNotificationType");
		mav.addObject("notification", new NotificationPreferedType());
		return mav;
	}

	@RequestMapping(value = "/createNotificationType/submit", method = RequestMethod.POST)
	public ModelAndView createNoticationSubmit(@ModelAttribute("notification") NotificationPreferedType npt) {
		nptService.saveOrUpdate(npt);
		ModelAndView mav = new ModelAndView("redirect:/systemadmin");
		return mav;
	}

}
