package com.companymanagement.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.ApplicationRequestDao;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Company;
import com.companymanagement.model.Vendor;
import com.companymanagement.service.ApplicationRequestService;
import com.companymanagement.service.CompanyService;

@Service("applicationRequestService")
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, rollbackFor = CompanyMgmtException.class)
public class ApplicationRequestServiceImpl extends BaseServiceImpl<Long, ApplicationRequest> implements ApplicationRequestService {

	// ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	// Validator validator = factory.getValidator();

	@Autowired
	protected ApplicationRequestDao dao;
	
	@Autowired
	CompanyService companyService;

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
	public void saveOrUpdate(ApplicationRequest newAppRequest) throws CompanyMgmtException {
//		ApplicationRequest existingAppRequest = findbyUniqueRequest(newAppRequest.getApplicationNo());
//		if (existingAppRequest != null) {
//			existingAppRequest.setAppStatus(newAppRequest.getAppStatus());
//			existingAppRequest.setAppType(newAppRequest.getAppType());
////			newAppRequest.setId(existingAppRequest.getId());
////			Mapper mapper = new DozerBeanMapper();
////			mapper.map(newAppRequest,existingAppRequest); //object
//			dao.merge(existingAppRequest);
//			System.out.println("Duplicate");
//		} else
		newAppRequest.setCreatedDate(new Date());
		dao.persist(newAppRequest);
		
	}
	
	@Override
	public ApplicationRequest findbyUniqueRequest(String applicationNo) throws CompanyMgmtException {
		HashMap<String,String> params = new HashMap<>();
		params.put("applicationNo", applicationNo);
		if(dao.findByNamedQueryAndNamedParams("findUniqueRequest", params).size() == 0) {
			return null;
		}
		return dao.findByNamedQueryAndNamedParams("findUniqueRequest", params).get(0);	
	}

	@Override
	public List<ApplicationRequest> findRequestByVendor(Vendor vendor) throws CompanyMgmtException {
		HashMap<String,Vendor> params = new HashMap<>();
		params.put("vendor", vendor);
		if(dao.findByNamedQueryAndNamedParams("findRequestByVendor", params).size() == 0) {
			return null;
		}
		return dao.findByNamedQueryAndNamedParams("findRequestByVendor", params);	
	}
	
	@Override
	public List<ApplicationRequest> findRequestByCompanyAndStatus(Company company, ApplicationStatus status) throws CompanyMgmtException {
		return dao.findRequestbyCompanyAndStatus(company, status);
	}

	@Override
	public void updateStatus(ApplicationRequest existingRequest, ApplicationStatus existingStatus) {
		if(existingStatus.getStatusName().equalsIgnoreCase("approved")) {
			companyService.updateVendorList(existingRequest.getCompany(), existingRequest.getVendor());
		}
		existingRequest.setAppStatus(existingStatus);
		dao.merge(existingRequest);
		
	}
}
