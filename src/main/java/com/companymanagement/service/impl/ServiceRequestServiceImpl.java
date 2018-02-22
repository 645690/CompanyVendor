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
import com.companymanagement.dao.ServiceRequestDAO;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.service.ServiceRequestService;

@Service("ServiceRequestService")
public class ServiceRequestServiceImpl extends BaseServiceImpl<Long, ServiceRequest> implements ServiceRequestService {

	@Autowired
	protected ServiceRequestDAO dao;

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
	public void saveOrUpdate(ServiceRequest serviceRequest) throws CompanyMgmtException {
		ServiceRequest findServiceRequest = findServiceRequestByRegNo(serviceRequest.getRegNo());
		if (findServiceRequest != null) {
			findServiceRequest.setName(serviceRequest.getName());
			findServiceRequest.setStatus(serviceRequest.getStatus());
			findServiceRequest.setCategory(serviceRequest.getCategory());
			findServiceRequest.setSrAppList(serviceRequest.getSrAppList());
			dao.merge(findServiceRequest);
		} else {
			System.out.println("persisting");
			dao.persist(serviceRequest);
		}
	}

	@Override
	@Transactional
	public ServiceRequest findServiceRequestByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<ServiceRequest> serviceRequests = findByNamedQueryAndNamedParams("ServiceRequest.findRegNo", queryParams);
		if (serviceRequests.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_SERVICEREQUEST_BY_SAME_NAME");
		}
		if (serviceRequests.size() == 0) {
			return null;
		}
		return serviceRequests.get(0);
	}

	@Override
	@Transactional
	public List<ServiceRequestApplication> findServiceRequestApplicationsByRegNo(Long regNo)
			throws CompanyMgmtException {
		ServiceRequest serviceRequest = findServiceRequestByRegNo(regNo);
		List<ServiceRequestApplication> srAppList = serviceRequest.getSrAppList();
		srAppList.size();
		return srAppList;
	}

	@Override
	@Transactional
	public void deleteByRegNo(ServiceRequest serviceRequest) throws CompanyMgmtException {
		ServiceRequest findServiceRequest = findServiceRequestByRegNo(serviceRequest.getRegNo());
		if (findServiceRequest != null) {
			dao.remove(findServiceRequest);
		}
	}
}
