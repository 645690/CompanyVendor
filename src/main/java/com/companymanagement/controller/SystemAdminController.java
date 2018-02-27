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
import com.companymanagement.notification.NotificationService;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.DepartmentService;
import com.companymanagement.service.NotificationPreferedTypeService;
import com.companymanagement.service.VendorService;

@Controller
@SessionAttributes("account")
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

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/systemadmin", method = RequestMethod.GET)
	public ModelAndView showAllCompany() {
		ModelAndView mav = null;
				try {
		mav = new ModelAndView("systemadmin");
		List<Company> cList = companyService.findAll();
		mav.addObject("companyList", cList);
				}catch (Exception e) {
					String url = "error";
					mav = new ModelAndView(url);
					mav.addObject("message", "system admin showallcompany");
				}
		return mav;
	}

	@RequestMapping(value = "/viewCompany", method = RequestMethod.GET)
	public ModelAndView viewCompanyInfo(@RequestParam(required = false, name = "regNo") Long regNo) {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("viewCompanyApplicantDetails");
		Company company = companyService.findCompanyByRegNo(regNo);
		mav.addObject("company", company);
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/acceptCompanyApplicant", method = RequestMethod.GET)
	public ModelAndView acceptCompanyApplicant(@RequestParam(required = false, name = "regNo") Long regNo)
			throws Exception {
		ModelAndView mav = null;
		try {
		companyService.acceptByRegNo(regNo);
		String[] cc = {};
		notificationService.sendMail("tk125@hotmail.com", cc, "Company Application",
				"Company application " + regNo + " has been accepted");
		mav = new ModelAndView("redirect:/systemadmin");
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin acceptCompanyApplicant");
		}
		return mav;
	}

	@RequestMapping(value = "/rejectCompanyApplicant", method = RequestMethod.GET)
	public ModelAndView rejectCompanyApplicant(@RequestParam(required = false, name = "regNo") Long regNo)
			throws Exception {
		ModelAndView mav = null;
		try {
		companyService.rejectByRegNo(regNo);
		String[] cc = {};
		notificationService.sendMail("tk125@hotmail.com", cc, "Company Application",
				"Company application " + regNo + " has been rejected");
		mav = new ModelAndView("redirect:/systemadmin");
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/viewAllVendors", method = RequestMethod.GET)
	public ModelAndView viewAllVendors() {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("viewAllVendors");
		List<Vendor> vList = vendorService.findAll();
		mav.addObject("vendorList", vList);
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/viewPendingVendorApplicants", method = RequestMethod.GET)
	public ModelAndView viewPendingUserVendor() {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("viewPendingVendorApplicants");
		List<Vendor> vList = vendorService.findVendorsByStatus("pending");
		mav.addObject("vendorList", vList);
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/viewVendorApplicantDetails", method = RequestMethod.GET)
	public ModelAndView viewVendorApplicantDetails(@RequestParam(required = false, name = "regNo") Long regNo) {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("viewVendorApplicantDetails");
		Vendor vendor = vendorService.findVendorByRegNo(regNo);
		mav.addObject("vendor", vendor);
	}catch (Exception e) {
		String url = "error";
		mav = new ModelAndView(url);
		mav.addObject("message", "system admin viewcompanyinfo");
	}
		return mav;
	}

	@RequestMapping(value = "/acceptVendorApplicant", method = RequestMethod.GET)
	public ModelAndView acceptVendorApplicant(@RequestParam(required = false, name = "regNo") Long regNo)
			throws Exception {
		ModelAndView mav = null;
		try {
		vendorService.acceptByRegNo(regNo);
		String[] cc = {};
		notificationService.sendMail("teamgammatest@gmail.com", cc, "Vendor Application",
				"Vendor application " + regNo + " has been accepted");
		mav = new ModelAndView("redirect:/viewPendingVendorApplicants");
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/rejectVendorApplicant", method = RequestMethod.GET)
	public ModelAndView rejectVendorApplicant(@RequestParam(required = false, name = "regNo") Long vaRegNo)
			throws Exception {
		ModelAndView mav = null;
		try {
		vendorService.rejectByRegNo(vaRegNo);
		String[] cc = {};
		notificationService.sendMail("tk125@hotmail.com", cc, "Vendor Application",
				"Vendor application " + vaRegNo + " has been rejected");
		mav = new ModelAndView("redirect:/viewPendingVendorApplicants");
	}catch (Exception e) {
		String url = "error";
		mav = new ModelAndView(url);
		mav.addObject("message", "system admin viewcompanyinfo");
	}
		return mav;
	}

	@RequestMapping(value = "/createAccountRole", method = RequestMethod.GET)
	public ModelAndView createAccountRole() {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("createAccountRole");
		mav.addObject("accountRole", new AccountRole());
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/createAccountRole/submit", method = RequestMethod.POST)
	public ModelAndView createAccountRoleSubmit(@ModelAttribute("accountRole") AccountRole accountRole) {
		ModelAndView mav = null;
		try {
		arService.saveOrUpdate(accountRole);
		mav = new ModelAndView("redirect:/systemadmin");
	}catch (Exception e) {
		String url = "error";
		mav = new ModelAndView(url);
		mav.addObject("message", "system admin viewcompanyinfo");
	}
		return mav;
	}

	@RequestMapping(value = "/createDepartment", method = RequestMethod.GET)
	public ModelAndView createDepartment() {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("createDepartment");
		mav.addObject("department", new Department());
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/createDepartment/submit", method = RequestMethod.POST)
	public ModelAndView createDepartmentSubmit(@ModelAttribute("department") Department department) {
		ModelAndView mav = null;
		try {
		departmentService.saveOrUpdate(department);
		mav = new ModelAndView("redirect:/systemadmin");
	}catch (Exception e) {
		String url = "error";
		mav = new ModelAndView(url);
		mav.addObject("message", "system admin viewcompanyinfo");
	}
		return mav;
	}

	@RequestMapping(value = "/createNotificationType", method = RequestMethod.GET)
	public ModelAndView createNotification() {
		ModelAndView mav = null;
		try {
		mav = new ModelAndView("createNotificationType");
		mav.addObject("notification", new NotificationPreferedType());
		}catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "system admin viewcompanyinfo");
		}
		return mav;
	}

	@RequestMapping(value = "/createNotificationType/submit", method = RequestMethod.POST)
	public ModelAndView createNoticationSubmit(@ModelAttribute("notification") NotificationPreferedType npt) {
		ModelAndView mav = null;
		try {
		nptService.saveOrUpdate(npt);
		mav = new ModelAndView("redirect:/systemadmin");
	}catch (Exception e) {
		String url = "error";
		mav = new ModelAndView(url);
		mav.addObject("message", "system admin viewcompanyinfo");
	}
		return mav;
	}

}
