package com.companymanagement.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.apache.commons.io.FileUtils;
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
import com.companymanagement.util.ConfUtil;

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
		// try catch for Upload Document (5)
		try {
			String url = getDocumentURL(company);
			if (url != null) {
				company.setDocFileUrl(url);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CompanyMgmtException("Can not save vendor documents", e);
		}
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

	// For uploading of file (6)
	private String getDocumentURL(Company company) throws IOException {
		String locToSave = ConfUtil.get("fileServerLocation");
		System.out.println(locToSave);
		if (company.getDocByteArray() != null && company.getDocFileExtention() != null) {
			String path = locToSave + File.separator + company.getName() + "." + company.getDocFileExtention();
			File file = new File(path);
			FileUtils.writeByteArrayToFile(file, company.getDocByteArray());
			System.out.println(ConfUtil.get("fileServerWebURL"));

			String webUrl = ConfUtil.get("fileServerWebURL") + company.getName() + "." + company.getDocFileExtention();
			return webUrl;
		}

		return null;
		// FileUtils.w
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
}
