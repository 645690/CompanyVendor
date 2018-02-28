package com.companymanagement.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.companymanagement.model.Account;
import com.companymanagement.model.AccountRole;
import com.companymanagement.model.Company;
import com.companymanagement.model.Department;
import com.companymanagement.model.Employee;
import com.companymanagement.model.Permission;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.notification.NotificationService;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.CompanyService;
import com.companymanagement.service.DepartmentService;
import com.companymanagement.service.EmployeeService;
import com.companymanagement.service.ServiceRequestService;

@Controller
@SessionAttributes("account")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRoleService accountRoleService;

	@Autowired
	CompanyService companyService;

	@Autowired
	ServiceRequestService serviceRequestService;

	@Autowired
	NotificationService notificationService;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView showCompany(@SessionAttribute("account") Account account) {
		ModelAndView mav = null;
		try {
			List<ServiceRequest> srList = null;
			if (account.getAccountRole().getName().equalsIgnoreCase("employee")) {
				Employee employee = employeeService.findEmployeeByAccount(account);
				srList = serviceRequestService.findPendingServiceRequestsByEmployeeDepartmentAndCompany(employee);
			} else {
				Company company = companyService.findCompanyByAccount(account);
				srList = serviceRequestService.findPendingServiceRequestsByCompany(company);
			}
			mav = new ModelAndView("company");
			mav.addObject("serviceRequestList", srList);
		} catch (Exception e) {
			e.printStackTrace();
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "showCompany");
		}
		return mav;
	}

	@RequestMapping(value = "/employeeprofile", method = RequestMethod.GET)
	public ModelAndView showProfile(@SessionAttribute("account") Account account) {
		ModelAndView mav = null;

		try {
			Employee employee = employeeService.findEmployeeByAccount(account);
			mav = new ModelAndView("employeeprofile");
			mav.addObject("employee", employee);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "showProfile");
		}
		return mav;
	}

	@RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
	public ModelAndView createEmployee() {
		List<Department> departmentList = departmentService.findAllAllowedDepartments();
		ModelAndView mav = new ModelAndView("createEmployee");
		mav.addObject("employee", new Employee());
		mav.addObject("permission", new Permission());
		mav.addObject("departmentList", departmentList);
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/createNewEmployee", method = RequestMethod.POST)
	public ModelAndView createNewEmployee(@SessionAttribute("account") Account compAdminAccount,
			@ModelAttribute("employee") Employee employee, @ModelAttribute("permission") Permission permission)
			throws Exception {
		ModelAndView mav = null;
		try {
			Employee emp = employeeService.findEmployeeByRegNo(employee.getRegNo());

			if (emp == null) {
				Account account = accountService.findAccountByUsername(employee.getAccount().getUsername());
				if (account != null) {
					List<Department> departmentList = departmentService.findAllAllowedDepartments();
					mav = new ModelAndView("createEmployee");
					mav.addObject("employee", new Employee());
					mav.addObject("permission", new Permission());
					mav.addObject("departmentList", departmentList);
					mav.addObject("message", "Account Username already exists!!");
				} else if (account == null) {
					Department department = departmentService.findDepartment(employee.getDepartment().getName());
					AccountRole accountRole = accountRoleService.findAccountRole("employee");
					Account empAccount = new Account();
					compAdminAccount = accountService.findAccountByUsername(compAdminAccount.getUsername());
					Company company = companyService.findCompanyByAccount(compAdminAccount);
					// Set<Permission> permissions = new HashSet<Permission>();
					// StringTokenizer countTokens = new StringTokenizer(permission.getName(), "
					// ,");
					// while(countTokens.hasMoreTokens())
					// {
					// Permission perm = new Permission();
					// perm.setName(countTokens.nextToken());
					// permissions.add(permission);
					// }
					// employee.getAccount().setPermission(permissions);
					empAccount.setUsername(employee.getAccount().getUsername());
					empAccount.setPassword(employee.getAccount().getPassword());
					empAccount.setAccountRole(accountRole);
					employee.setAccount(empAccount);
					employee.setDepartment(department);
					employee.setCompany(company);
					employeeService.saveOrUpdate(employee);
					String[] cc = {};
					notificationService.sendMail("teamgammatest@gmail.com", cc, "Employee Account Created",
							"Your username is: " + empAccount.getUsername());
					mav = new ModelAndView("redirect:company");
				}
			}

			if (emp != null) {
				mav = new ModelAndView("createEmployee");
				mav.addObject("employee", new Employee());
				mav.addObject("permission", new Permission());
				mav.addObject("message", "Employee Registration No already exists!!");
			}
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "createNewEmployee");
		}

		return mav;
	}

	@RequestMapping(value = "/viewAllEmployees", method = RequestMethod.GET)
	public ModelAndView viewAllEmployees(@SessionAttribute("account") Account compAdminAccount) {
		ModelAndView mav = null;

		try {
			compAdminAccount = accountService.findAccountByUsername(compAdminAccount.getUsername());
			Company company = companyService.findCompanyByAccount(compAdminAccount);
			List<Employee> employeeList = employeeService.findEmployeeByCompany(company);
			mav = new ModelAndView("viewAllEmployees");
			mav.addObject("employeeList", employeeList);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "viewAllEmployees");
		}
		return mav;
	}

	@RequestMapping(value = "/viewEmployeeDetails", method = RequestMethod.GET)
	public ModelAndView viewEmployeeDetails(@RequestParam(required = false, name = "regNo") Long regNo) {
		ModelAndView mav = null;

		try {
			Employee employee = employeeService.findEmployeeByRegNo(regNo);
			List<Department> departmentList = departmentService.findAllAllowedDepartments();
			for (int i = 0; i < departmentList.size(); i++) {
				if (departmentList.get(i).getName().equals(employee.getDepartment().getName())) {
					departmentList.remove(i);
				}
			}
			List<AccountRole> accountRoleList = accountRoleService.findCompanyAdminAndEmployee();
			for (int i = 0; i < accountRoleList.size(); i++) {
				if (accountRoleList.get(i).getName().equals(employee.getAccount().getAccountRole().getName())) {
					accountRoleList.remove(i);
				}
			}

			mav = new ModelAndView("viewEmployeeDetails");
			mav.addObject("employee", employee);
			mav.addObject("updateEmployee", new Employee());
			mav.addObject("departmentList", departmentList);
			mav.addObject("accountRoleList", accountRoleList);
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "viewEmployeeDetails");
		}
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	public ModelAndView updateEmployee(@ModelAttribute("updateEmployee") Employee updateEmployee) {
		ModelAndView mav = null;

		try {
			Department department = departmentService.findDepartment(updateEmployee.getDepartment().getName());
			AccountRole accountRole = accountRoleService
					.findAccountRole(updateEmployee.getAccount().getAccountRole().getName());
			Employee employee = employeeService.findEmployeeByRegNo(updateEmployee.getRegNo());
			Account account = employee.getAccount();
			account.setAccountRole(accountRole);
			employee.setDepartment(department);
			employee.setAccount(account);
			employeeService.saveOrUpdate(employee);
			mav = new ModelAndView("redirect:company");
		} catch (Exception e) {
			String url = "error";
			mav = new ModelAndView(url);
			mav.addObject("message", "updateEmployee");
		}
		return mav;
	}
}