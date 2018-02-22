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
import com.companymanagement.dao.ApplicationRequestCategoryDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.ApplicationRequestCategory;
import com.companymanagement.service.ApplicationRequestCategoryService;

@Service("ApplicationRequestCategoryService")
public class ApplicationRequestCategoryServiceImpl extends BaseServiceImpl<Long, ApplicationRequestCategory>
		implements ApplicationRequestCategoryService {

	@Autowired
	protected ApplicationRequestCategoryDAO dao;

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
	public void saveOrUpdate(ApplicationRequestCategory serviceRequestCategory) throws CompanyMgmtException {
		ApplicationRequestCategory findServiceRequest = findApplicationRequestCategoryByName(serviceRequestCategory.getName());
		if (findServiceRequest != null) {
			findServiceRequest.setName(serviceRequestCategory.getName());
			dao.merge(findServiceRequest);
		} else {
			dao.persist(serviceRequestCategory);
		}
	}

	@Override
	public ApplicationRequestCategory findApplicationRequestCategoryByName(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<ApplicationRequestCategory> serviceRequestCategory = findByNamedQueryAndNamedParams(
				"ApplicationRequestCategory.findName", queryParams);
		if (serviceRequestCategory.size() == 0) {
			return null;
		}
		return serviceRequestCategory.get(0);
	}

	@Override
	public void deleteByName(String name) throws CompanyMgmtException {
		ApplicationRequestCategory findServiceRequestCategory = findApplicationRequestCategoryByName(name);
		if (findServiceRequestCategory != null) {
			dao.remove(findServiceRequestCategory);
		}
	}
}
