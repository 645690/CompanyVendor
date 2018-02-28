package com.companymanagement.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	public ModelAndView showUser(HttpSession session) {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("user");
			Account account = (Account) session.getAttribute("Account");
			Vendor vendor = vendorService.findVendorByAccount(account);
			Company company = companyService.findCompanyByAccount(account);
			if (company != null || vendor != null) {
				session.setAttribute("applied", "applied");
			}
			mav.addObject("company", company);
			mav.addObject("vendor", vendor);

		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "User page error!!");
		}
		return mav;

	}

	@RequestMapping(value = "/applyToBeVendor", method = RequestMethod.GET)
	public ModelAndView applyToBeVendor(HttpSession session) {
		ModelAndView mav = null;
		if (session.getAttribute("applied") == "applied") {
			return new ModelAndView("redirect:user");
		}
		try {
			mav = new ModelAndView("applyToBeVendor");
			mav.addObject("vendor", new Vendor());
			mav.addObject("nptList", nptService.findAll());
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error with applying to be vendor!!");
		}
		return mav;
	}

	@RequestMapping(value = "/applyToBeVendor/submit", method = RequestMethod.POST)
	public ModelAndView applyToBeVendorSubmit(@ModelAttribute("vendor") Vendor vendor,
			@SessionAttribute("account") Account account,
			@RequestParam("file") MultipartFile file/* Used for file upload */) throws Exception {
		ModelAndView mav = null;
		try {
			vendor.setStatus("pending");
			vendor.setAccount(account);
			// Used for file upload
			try {
				byte[] data = file.getBytes();
				String fN = file.getName();
				String oN = file.getOriginalFilename();
				if (data.length != 0) {
					vendor.setDocByteArray(data);
					vendor.setDocFileExtention(oN.substring(oN.lastIndexOf(".") + 1, oN.length()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vendorService.saveOrUpdate(vendor);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Vendor Application",
					"Vendor application " + vendor.getName() + " has been created");
			mav = new ModelAndView("redirect:/user");
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error submitting vendor application!!");
		}
		return mav;
	}

	@RequestMapping(value = "/applyToBeCompany", method = RequestMethod.GET)
	public ModelAndView applyToBeCompany(HttpSession session) {
		ModelAndView mav = null;
		if (session.getAttribute("applied") == "applied") {
			return new ModelAndView("redirect:user");
		}
		try {
			mav = new ModelAndView("applyToBeCompany");
			mav.addObject("company", new Company());
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error with applying to be company!!");
		}
		return mav;
	}

	@RequestMapping(value = "/applyToBeCompany/submit", method = RequestMethod.POST)
	public ModelAndView applyToBeCompanySubmit(@ModelAttribute("company") Company company,
			@SessionAttribute("account") Account account,
			@RequestParam("file") MultipartFile file/* Used for file upload (3) */) throws Exception {
		ModelAndView mav = null;
		try {
			company.setStatus("pending");
			company.setAccount(account);

			// Used for file upload (4)
			try {
				byte[] data = file.getBytes();
				String fN = file.getName();
				String oN = file.getOriginalFilename();
				if (data.length != 0) {
					company.setDocByteArray(data);
					company.setDocFileExtention(oN.substring(oN.lastIndexOf(".") + 1, oN.length()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			companyService.saveOrUpdate(company);
			String[] cc = {};
			notificationService.sendMail("teamgammatest@gmail.com", cc, "Company Application",
					"Company application " + company.getName() + " has been created");
			mav = new ModelAndView("redirect:/user");
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error submitting company application!!");
		}
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView showLogout(HttpSession session) {
		ModelAndView mav = null;
		try {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Account());
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error login out!!");
		}
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin() {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("login");
			mav.addObject("login", new Account());
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error login in!!");
		}
		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegister() {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("register");
			mav.addObject("login", new Account());
			mav.addObject("role", new AccountRole());
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Registering page failed!!");
		}
		return mav;
	}

	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public ModelAndView registerProcess(@ModelAttribute("login") Account account,
			@ModelAttribute("role") AccountRole role) {
		ModelAndView mav = null;
		try {
			try {
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
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error registering!!");
		}
		return mav;
	}

	@RequestMapping(value = "/otp", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("login") Account login) throws Exception {
		ModelAndView mav = null;
		try {
			Account acc = accountService.findAccountByUsername(login.getUsername());

			if (login.getUsername().equalsIgnoreCase(acc.getUsername())
					&& login.getPassword().equalsIgnoreCase(acc.getPassword())) {
				mav = new ModelAndView("login_otp");
				Random rand = new Random();
				String token = String.format("%04d", rand.nextInt(10000));
				String[] cc = {};
				notificationService.sendMail("songnian.tay@cognizant.com", cc, "Login OTP", "OTP is " + token);
				System.out.println("Token is " + token);
				session.setAttribute("token", token);
				session.setAttribute("Account", acc);
				mav.addObject("token", "");

			}
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error sending out OTP!!");
		}
		return mav;
	}

	@RequestMapping(value = "/otpProcess", method = RequestMethod.POST)
	public ModelAndView otpProcess(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "token", required = false) String token, ModelMap model) throws Exception {
		ModelAndView mav = null;
		try {
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
						session.setAttribute("company", company);
					} else if (ar.equalsIgnoreCase("vendor")) {
						url = "redirect:vendor";
						Vendor vendor = vendorService.findVendorByAccount(findAccount);
						session.setAttribute("vendor", vendor);
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
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "OTP entered is incorrect");
		}
		return mav;
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, @ModelAttribute("login") Account account, ModelMap model) {
		ModelAndView mav = null;
		try {
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
					session.setAttribute("company", company);
				} else if (ar.equalsIgnoreCase("vendor")) {
					url = "redirect:vendor";
					Vendor vendor = vendorService.findVendorByAccount(findAccount);
					session.setAttribute("vendor", vendor);
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
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "Error Logging in!!");
		}
		return mav;
	}

}
