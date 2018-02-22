package com.companymanagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.CompanyDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.Company;
import com.companymanagement.service.CompanyService;

@Service("CompanyService")
public class CompanyServiceImpl extends BaseServiceImpl<Long, Company> implements CompanyService {

	@Autowired
	protected CompanyDAO dao;

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
	public void saveOrUpdate(Company company) throws CompanyMgmtException {
		Company findCompany = findCompanyByRegNo(company.getRegNo());
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
	public void deleteByRegNo(Company company) throws CompanyMgmtException {
		Company findCompany = findCompanyByRegNo(company.getRegNo());
		if (findCompany != null) {
			dao.remove(findCompany);
		}
	}
}
