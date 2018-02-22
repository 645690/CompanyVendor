package com.companymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Account;
import com.companymanagement.model.AccountRole;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.NotificationPreferedTypeService;
import com.companymanagement.service.VendorService;

@Controller
@SessionAttributes("account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	VendorService vendorService;

	@Autowired
	AccountRoleService arService;
	
	@Autowired
	NotificationPreferedTypeService nptService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView showUser() {
		ModelAndView mav = new ModelAndView("user");
		return mav;
	}

	@RequestMapping(value = "/applyToBeVendor", method = RequestMethod.GET)
	public ModelAndView applyToBeVendor() {
		ModelAndView mav = new ModelAndView("applyToBeVendor");
		mav.addObject("vendor", new Vendor());
		mav.addObject("nptList", nptService.findAll());
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/applyToBeVendor/submit", method = RequestMethod.POST)
	public ModelAndView applyToBeVendorSubmit(@ModelAttribute("vendor") Vendor vendor,
			@SessionAttribute("account") Account account) {
		vendor.setStatus("pending");
		vendor.setAccount(account);
		vendorService.saveOrUpdate(vendor);
		ModelAndView mav = new ModelAndView("redirect:/user");
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView showLogout() {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new Account());
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin() {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new Account());
		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister() {
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("login", new Account());
		mav.addObject("role", new AccountRole());
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public ModelAndView registerProcess(@ModelAttribute("login") Account account,
			@ModelAttribute("role") AccountRole role) {
		ModelAndView mav = null;
		try {
			String accRole = role.getName();
			role = arService.findAccountRole(role.getName());
			if (role == null) {
				role = new AccountRole(accRole);
			}
			account.setAccountRole(role);
			accountService.create(account);
			String url = "redirect:login";
			mav = new ModelAndView(url);
			mav.addObject("login", account);
		} catch (CompanyMgmtException e) {
			String url = "register";
			mav = new ModelAndView(url);
			mav.addObject("register", new Account());
			mav.addObject("message", "Username taken");
		}
		return mav;
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(@ModelAttribute("login") Account account, ModelMap model) {
		ModelAndView mav = null;

		Account findAccount = accountService.findAccount(account.getUsername(), account.getPassword());
		if (findAccount != null) {
			String ar = findAccount.getAccountRole().getName();
			String url = "redirect:user";
			if (ar.equalsIgnoreCase("company")) {
				url = "redirect:company";
			} else if (ar.equalsIgnoreCase("vendor")) {
				url = "redirect:vendor";
			} else if (ar.equalsIgnoreCase("systemadmin")) {
				url = "redirect:systemadmin";
			}
			mav = new ModelAndView(url);
			model.addAttribute("account", findAccount);
		} else {
			String url = "login";
			mav = new ModelAndView(url);
			mav.addObject("login", new Account());
			mav.addObject("message", "Username or Password is wrong!!");
		}

		return mav;
	}
}
