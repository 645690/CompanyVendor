package com.companymanagement.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView showCompany(@SessionAttribute("account") Account account) {
		ModelAndView mav = new ModelAndView("company");
		List<ServiceRequest> srList = null;
		if (account.getAccountRole().getName().equalsIgnoreCase("employee")) {
			Employee employee = employeeService.findEmployeeByAccount(account);
			srList = serviceRequestService.findServiceRequestsByEmployeeDepartmentAndCompany(employee);
		} else {
			Company company = companyService.findCompanyByAccount(account);
			srList = serviceRequestService.findServiceRequestsByCompany(company);
		}
		mav.addObject("serviceRequestList", srList);
		return mav;
	}

	@RequestMapping(value = "/employeeprofile", method = RequestMethod.GET)
	public ModelAndView showProfile(@SessionAttribute("account") Account account) {
		ModelAndView mav = new ModelAndView("employeeprofile");
		Employee employee = employeeService.findEmployeeByAccount(account);
		mav.addObject("employee", employee);
		return mav;
	}

	@RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
	public ModelAndView createEmployee() {
		List<Department> departmentList = departmentService.findAll();
		ModelAndView mav = new ModelAndView("createEmployee");
		mav.addObject("employee", new Employee());
		mav.addObject("permission", new Permission());
		mav.addObject("departmentList", departmentList);

		return mav;
	}

	@Transactional
	@RequestMapping(value = "/createNewEmployee", method = RequestMethod.POST)
	public ModelAndView createNewEmployee(@SessionAttribute("account") Account compAdminAccount,
			@ModelAttribute("employee") Employee employee, @ModelAttribute("permission") Permission permission) {
		ModelAndView mav = null;

		Employee emp = employeeService.findEmployeeByRegNo(employee.getRegNo());

		if (emp == null) {
			Account account = accountService.findAccountByUsername(employee.getAccount().getUsername());

			if (account != null) {
				List<Department> departmentList = departmentService.findAll();

				mav = new ModelAndView("createEmployee");
				mav.addObject("employee", new Employee());
				mav.addObject("permission", new Permission());
				mav.addObject("departmentList", departmentList);

				mav.addObject("message", "Account Username already exists!!");
			}

			else if (account == null) {
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

				mav = new ModelAndView("redirect:company");
			}
		}

		if (emp != null) {
			mav = new ModelAndView("createEmployee");
			mav.addObject("employee", new Employee());
			mav.addObject("permission", new Permission());
			mav.addObject("message", "Employee Registration No already exists!!");
		}

		return mav;
	}

	@RequestMapping(value = "/viewAllEmployees", method = RequestMethod.GET)
	public ModelAndView viewAllEmployees(@SessionAttribute("account") Account compAdminAccount) {
		compAdminAccount = accountService.findAccountByUsername(compAdminAccount.getUsername());
		Company company = companyService.findCompanyByAccount(compAdminAccount);

		List<Employee> employeeList = employeeService.findEmployeeByCompany(company);

		ModelAndView mav = new ModelAndView("viewAllEmployees");
		mav.addObject("employeeList", employeeList);

		return mav;
	}
}