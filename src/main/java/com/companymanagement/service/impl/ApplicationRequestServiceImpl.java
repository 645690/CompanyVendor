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
import com.companymanagement.dao.ApplicationRequestDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.service.ApplicationRequestService;

@Service("ApplicationRequestService")
public class ApplicationRequestServiceImpl extends BaseServiceImpl<Long, ApplicationRequest> implements ApplicationRequestService {

	@Autowired
	protected ApplicationRequestDAO dao;

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
	public void saveOrUpdate(ApplicationRequest applicationRequest) throws CompanyMgmtException {
		ApplicationRequest findApplicationRequest = findApplicationRequestByRegNo(applicationRequest.getRegNo());
		if (findApplicationRequest != null) {
			findApplicationRequest.setCompany(applicationRequest.getCompany());
			findApplicationRequest.setStatus(applicationRequest.getStatus());
			findApplicationRequest.setCategory(applicationRequest.getCategory());
			findApplicationRequest.setVendor(applicationRequest.getVendor());
			findApplicationRequest.setRegNo(applicationRequest.getRegNo());
			dao.merge(findApplicationRequest);
		} else {
			dao.persist(applicationRequest);
		}
	}

	@Override
	public ApplicationRequest findApplicationRequestByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<ApplicationRequest> ApplicationRequests = findByNamedQueryAndNamedParams("ApplicationRequest.findRegNo", queryParams);
		if (ApplicationRequests.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_APPLICATIONREQUEST_BY_SAME_NAME");
		}
		if (ApplicationRequests.size() == 0) {
			return null;
		}
		return ApplicationRequests.get(0);
	}

	@Override
	public void deleteByRegNo(ApplicationRequest applicationRequest) throws CompanyMgmtException {
		ApplicationRequest findServiceRequest = findApplicationRequestByRegNo(applicationRequest.getRegNo());
		if (findServiceRequest != null) {
			dao.remove(findServiceRequest);
		}
	}
}
