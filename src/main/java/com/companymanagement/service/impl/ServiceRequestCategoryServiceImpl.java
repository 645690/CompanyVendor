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
import com.companymanagement.dao.JPADAO;
import com.companymanagement.dao.ServiceRequestCategoryDAO;
import com.companymanagement.model.ServiceRequestCategory;
import com.companymanagement.service.ServiceRequestCategoryService;

@Service("ServiceRequestCategoryService")
public class ServiceRequestCategoryServiceImpl extends BaseServiceImpl<Long, ServiceRequestCategory>
		implements ServiceRequestCategoryService {

	@Autowired
	protected ServiceRequestCategoryDAO dao;

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
	public void saveOrUpdate(ServiceRequestCategory serviceRequestCategory) throws CompanyMgmtException {
		ServiceRequestCategory findServiceRequest = findServiceRequestCategoryByName(serviceRequestCategory.getName());
		if (findServiceRequest != null) {
			findServiceRequest.setName(serviceRequestCategory.getName());
			dao.merge(findServiceRequest);
		} else {
			dao.persist(serviceRequestCategory);
		}
	}

	@Override
	public ServiceRequestCategory findServiceRequestCategoryByName(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<ServiceRequestCategory> serviceRequestCategory = findByNamedQueryAndNamedParams(
				"ServiceRequestCategory.findName", queryParams);
		if (serviceRequestCategory.size() == 0) {
			return null;
		}
		return serviceRequestCategory.get(0);
	}

	@Override
	public void deleteByName(String name) throws CompanyMgmtException {
		ServiceRequestCategory findServiceRequestCategory = findServiceRequestCategoryByName(name);
		if (findServiceRequestCategory != null) {
			dao.remove(findServiceRequestCategory);
		}
	}
}
