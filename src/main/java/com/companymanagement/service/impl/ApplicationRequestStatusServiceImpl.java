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
import com.companymanagement.dao.ApplicationRequestStatusDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.ApplicationRequestStatus;
import com.companymanagement.service.ApplicationRequestStatusService;

@Service("ApplicationRequestStatusService")
public class ApplicationRequestStatusServiceImpl extends BaseServiceImpl<Long, ApplicationRequestStatus>
		implements ApplicationRequestStatusService {

	@Autowired
	protected ApplicationRequestStatusDAO dao;

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
	public void saveOrUpdate(ApplicationRequestStatus serviceRequestStatus) throws CompanyMgmtException {
		ApplicationRequestStatus findServiceRequest = findApplicationRequestStatusByName(serviceRequestStatus.getName());
		if (findServiceRequest != null) {
			findServiceRequest.setName(serviceRequestStatus.getName());
			dao.merge(findServiceRequest);
		} else {
			dao.persist(serviceRequestStatus);
		}
	}

	@Override
	public ApplicationRequestStatus findApplicationRequestStatusByName(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<ApplicationRequestStatus> serviceRequestStatus = findByNamedQueryAndNamedParams(
				"ServiceRequestStatus.findName", queryParams);
		if (serviceRequestStatus.size() == 0) {
			return null;
		}
		return serviceRequestStatus.get(0);
	}

	@Override
	public void deleteByName(String name) throws CompanyMgmtException {
		ApplicationRequestStatus findServiceRequestStatus = findApplicationRequestStatusByName(name);
		if (findServiceRequestStatus != null) {
			dao.remove(findServiceRequestStatus);
		}
	}
}
