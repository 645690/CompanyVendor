package com.companymanagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.dao.ServiceRequestApplicationDAO;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.service.ServiceRequestApplicationService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.ServiceRequestStatusService;

@Service("ServiceRequestApplicationService")
public class ServiceRequestApplicationServiceImpl extends BaseServiceImpl<Long, ServiceRequestApplication>
		implements ServiceRequestApplicationService {

	@Autowired
	protected ServiceRequestApplicationDAO dao;

	@Autowired
	ServiceRequestStatusService srStatusService;

	@Autowired
	ServiceRequestService serviceRequestService;

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
	public void saveOrUpdate(ServiceRequestApplication serviceRequestApplication) throws CompanyMgmtException {
		ServiceRequestApplication findServiceRequestApplication = findServiceRequestApplicationByRegNo(
				serviceRequestApplication.getRegNo());
		if (findServiceRequestApplication != null) {
			findServiceRequestApplication.setName(serviceRequestApplication.getName());
			findServiceRequestApplication.setStatus(serviceRequestApplication.getStatus());
			findServiceRequestApplication.setCategory(serviceRequestApplication.getCategory());
			dao.merge(findServiceRequestApplication);
		} else {
			dao.persist(serviceRequestApplication);
		}
	}

	@Override
	public ServiceRequestApplication findServiceRequestApplicationByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<ServiceRequestApplication> serviceRequestApplication = findByNamedQueryAndNamedParams(
				"ServiceRequestApplication.findRegNo", queryParams);
		if (serviceRequestApplication.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_SERVICEREQUESTAPPLICATION_BY_SAME_NAME");
		}
		if (serviceRequestApplication.size() == 0) {
			return null;
		}
		return serviceRequestApplication.get(0);
	}

	@Override
	public void deleteByRegNo(ServiceRequestApplication serviceRequest) throws CompanyMgmtException {
		ServiceRequestApplication findServiceRequest = findServiceRequestApplicationByRegNo(serviceRequest.getRegNo());
		if (findServiceRequest != null) {
			dao.remove(findServiceRequest);
		}
	}

	@Override
	@Transactional
	public void acceptServiceRequestApplication(Long srRegNo, Long srAppRegNo) throws CompanyMgmtException {
		ServiceRequestApplication srApplication = findServiceRequestApplicationByRegNo(srAppRegNo);
		srApplication.setStatus(srStatusService.findServiceRequestStatusByName("Accepted"));
		dao.merge(srApplication);
		serviceRequestService.acceptServiceRequest(srRegNo);
	}

	@Override
	@Transactional
	public void rejectServiceRequestApplication(Long srRegNo, Long srAppRegNo) throws CompanyMgmtException {
		ServiceRequestApplication srApplication = findServiceRequestApplicationByRegNo(srAppRegNo);
		srApplication.setStatus(srStatusService.findServiceRequestStatusByName("Rejected"));
		dao.merge(srApplication);
		serviceRequestService.rejectServiceRequest(srRegNo);
	}
}
