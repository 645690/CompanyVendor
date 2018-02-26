package com.companymanagement.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Account;
import com.companymanagement.model.AccountRole;
import com.companymanagement.model.Company;
import com.companymanagement.model.Employee;
import com.companymanagement.model.Vendor;
import com.companymanagement.notification.NotificationService;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.EmployeeService;
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
	CompanyService companyService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	AccountRoleService arService;

	@Autowired
	NotificationPreferedTypeService nptService;

	@Autowired
	NotificationService notificationService;

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

	@RequestMapping(value = "/applyToBeVendor/submit", method = RequestMethod.POST)
	public ModelAndView applyToBeVendorSubmit(@ModelAttribute("vendor") Vendor vendor,
			@SessionAttribute("account") Account account,
			@RequestParam("file") MultipartFile file/* Used for file upload */) {
		vendor.setStatus("pending");
		vendor.setAccount(account);
		// Used for file upload
		try {
			byte[] data = file.getBytes();
			String fN = file.getName();
			String oN = file.getOriginalFilename();
			vendor.setDocByteArray(data);
			vendor.setDocFileExtention(oN.substring(oN.lastIndexOf(".") + 1, oN.length()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vendorService.saveOrUpdate(vendor);
		ModelAndView mav = new ModelAndView("redirect:/user");
		return mav;
	}

	@RequestMapping(value = "/applyToBeCompany", method = RequestMethod.GET)
	public ModelAndView applyToBeCompany() {
		ModelAndView mav = new ModelAndView("applyToBeCompany");
		mav.addObject("company", new Company());
		return mav;
	}

	@RequestMapping(value = "/applyToBeCompany/submit", method = RequestMethod.POST)
	public ModelAndView applyToBeCompanySubmit(@ModelAttribute("company") Company company,
			@SessionAttribute("account") Account account) {
		company.setStatus("pending");
		company.setAccount(account);
		companyService.saveOrUpdate(company);
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

	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public ModelAndView registerProcess(@ModelAttribute("login") Account account,
			@ModelAttribute("role") AccountRole role) {
		ModelAndView mav = null;
		try {
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

	@RequestMapping(value = "/otp", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("login") Account login) throws Exception {
		ModelAndView mav = null;
		Account acc = accountService.findAccountByUsername(login.getUsername());

		if (login.getUsername().equalsIgnoreCase(acc.getUsername())
				&& login.getPassword().equalsIgnoreCase(acc.getPassword())) {
			mav = new ModelAndView("login_otp");
			Random rand = new Random();
			String token = String.format("%04d", rand.nextInt(10000));
			String[] cc = {};
			notificationService.sendMail("songnian.tay@cognizant.com", cc, "Test Mail", "OTP is " + token);
			System.out.println("Token is " + token);
			session.setAttribute("token", token);
			session.setAttribute("Account", acc);
			mav.addObject("token", "");

		} else {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Username or Password is wrong!!");
		}
		return mav;
	}

	@RequestMapping(value = "/otpProcess", method = RequestMethod.POST)
	public ModelAndView otpProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "token", required = false) String token, ModelMap model) throws Exception {
		ModelAndView mav = null;
		System.out.println(token);

		String storedToken = (String) session.getAttribute("token");
		System.out.println(storedToken);
		if (storedToken.equalsIgnoreCase(token)) {
			session.setAttribute("token", "");
			Account findAccount = (Account) session.getAttribute("Account");
			if (findAccount != null) {
				String ar = findAccount.getAccountRole().getName();
				String url = "redirect:user";

				if (ar.equalsIgnoreCase("employee")) {
					url = "redirect:company";
					Employee employee = employeeService.findEmployeeByAccount(findAccount);
					model.addAttribute("employee", employee);
				} else if (ar.equalsIgnoreCase("companyadmin")) {
					url = "redirect:company";
					Company company = companyService.findCompanyByAccount(findAccount);
					model.addAttribute("company", company);
				} else if (ar.equalsIgnoreCase("vendor")) {
					url = "redirect:vendor";
				} else if (ar.equalsIgnoreCase("systemadmin")) {
					url = "redirect:systemadmin";
				}
				mav = new ModelAndView(url);
				model.addAttribute("account", findAccount);
			}
		} else {
			session.setAttribute("Account", "");
			session.setAttribute("token", "");
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "OTP entered is incorrect");
		}
		return mav;
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, @ModelAttribute("login") Account account, ModelMap model) {
		ModelAndView mav = null;
		// testing for otp
		Account findAccount = accountService.findAccount(account.getUsername(), account.getPassword());
		if (findAccount != null) {
			String ar = findAccount.getAccountRole().getName();
			String url = "redirect:user";

			if (ar.equalsIgnoreCase("employee")) {
				url = "redirect:company";
				Employee employee = employeeService.findEmployeeByAccount(findAccount);
				model.addAttribute("employee", employee);
			} else if (ar.equalsIgnoreCase("companyadmin")) {
				url = "redirect:company";
				Company company = companyService.findCompanyByAccount(findAccount);
				model.addAttribute("company", company);
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
