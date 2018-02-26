package com.companymanagement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.CompanyDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.Account;
import com.companymanagement.model.Company;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;
import com.companymanagement.service.CompanyService;

@Service("CompanyService")
public class CompanyServiceImpl extends BaseServiceImpl<Long, Company> implements CompanyService {

	@Autowired
	protected CompanyDAO dao;

	@Autowired
	protected AccountRoleService accRoleService;

	@Autowired
	protected AccountService accService;

	@PostConstruct
	public void init() throws Exception {
		super.setDAO((JPADAO) dao);
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public void setEntityManagerOnDao(EntityManager entityManager) {
		dao.setEntityManager(entityManager);
	}

	@Override
	@Transactional
	public void saveOrUpdate(Company company) throws CompanyMgmtException {
		company.setStatus("Pending");
		Company findCompany = findCompanyByAccount(company.getAccount());
		if (findCompany != null) {
			findCompany.setName(company.getName());
			findCompany.setVenList(company.getVenList());
			findCompany.setRegNo(company.getRegNo());
			dao.merge(findCompany);
		} else {
			dao.persist(company);
		}
	}

	@Override
	@Transactional
	public void acceptByRegNo(Long regNo) throws CompanyMgmtException {
		Company findCompany = findCompanyByRegNo(regNo);
		if (findCompany != null) {
			findCompany.getAccount().setAccountRole(accRoleService.findAccountRole("CompanyAdmin"));
			findCompany.setStatus("Accepted");
			dao.merge(findCompany);
		}
	}

	@Override
	@Transactional
	public void rejectByRegNo(Long regNo) throws CompanyMgmtException {
		Company findCompany = findCompanyByRegNo(regNo);
		if (findCompany != null) {
			findCompany.setStatus("Rejected");
			dao.merge(findCompany);
		}
	}

	@Override
	public Company findCompanyByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<Company> companies = findByNamedQueryAndNamedParams("Company.findRegNo", queryParams);
		if (companies.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_COMPANIES_BY_SAME_NAME");
		}
		if (companies.size() == 0) {
			return null;
		}
		return companies.get(0);
	}

	@Override
	public Company findCompanyByAccount(Account account) throws CompanyMgmtException {
		Map<String, Account> queryParams = new HashMap<String, Account>();
		queryParams.put("account", account);

		List<Company> companies = findByNamedQueryAndNamedParams("Company.findByAccount", queryParams);
		if (companies.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_COMPANY_OF_SAME_ACCOUNT");
		}
		if (companies.size() == 0) {
			return null;
		}
		return companies.get(0);
	}

	@Override
	public List<Company> findCompaniesByStatus(String status) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("status", status);

		List<Company> companies = findByNamedQueryAndNamedParams("Company.findByStatus", queryParams);
		if (companies.size() == 0) {
			return null;
		}
		return companies;
	}

	@Override
	public void deleteByRegNo(Company company) throws CompanyMgmtException {
		Company findCompany = findCompanyByRegNo(company.getRegNo());
		if (findCompany != null) {
			dao.remove(findCompany);
		}
	}
	
	@Override
	public void updateVendorList(Company company, Vendor vendor) throws CompanyMgmtException {
		List<Vendor> vList;
		System.out.println("reached");
		System.out.println(company.getRegNo());
		System.out.println(vendor.getRegNo());
		Company existingCompany = findCompanyByRegNo(company.getRegNo());
		if (existingCompany != null) {
			if (existingCompany.getVenList() == null) {
				vList = new ArrayList<Vendor>();
				System.out.println("null");
			} else {
				vList = company.getVenList();
				System.out.println("not full");
				System.out.println(vList);
			}
			System.out.println(vList);
			vList.add(vendor);
			System.out.println(vList);
		    List<Vendor> vendorListNoDuplicate = new ArrayList<>(new HashSet<>(vList));
		 
			
//			System.out.println(vList);
			existingCompany.setVenList(vendorListNoDuplicate);
			dao.merge(existingCompany);
		}

	}
}
