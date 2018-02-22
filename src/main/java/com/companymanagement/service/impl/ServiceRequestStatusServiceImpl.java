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
import com.companymanagement.dao.ServiceRequestStatusDAO;
import com.companymanagement.model.ServiceRequestStatus;
import com.companymanagement.service.ServiceRequestStatusService;

@Service("ServiceRequestStatusService")
public class ServiceRequestStatusServiceImpl extends BaseServiceImpl<Long, ServiceRequestStatus>
		implements ServiceRequestStatusService {

	@Autowired
	protected ServiceRequestStatusDAO dao;

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
	public void saveOrUpdate(ServiceRequestStatus serviceRequestStatus) throws CompanyMgmtException {
		ServiceRequestStatus findServiceRequest = findServiceRequestStatusByName(serviceRequestStatus.getName());
		if (findServiceRequest != null) {
			findServiceRequest.setName(serviceRequestStatus.getName());
			dao.merge(findServiceRequest);
		} else {
			dao.persist(serviceRequestStatus);
		}
	}

	@Override
	public ServiceRequestStatus findServiceRequestStatusByName(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<ServiceRequestStatus> serviceRequestStatus = findByNamedQueryAndNamedParams(
				"ServiceRequestStatus.findName", queryParams);
		if (serviceRequestStatus.size() == 0) {
			return null;
		}
		return serviceRequestStatus.get(0);
	}

	@Override
	public void deleteByName(String name) throws CompanyMgmtException {
		ServiceRequestStatus findServiceRequestStatus = findServiceRequestStatusByName(name);
		if (findServiceRequestStatus != null) {
			dao.remove(findServiceRequestStatus);
		}
	}
}
