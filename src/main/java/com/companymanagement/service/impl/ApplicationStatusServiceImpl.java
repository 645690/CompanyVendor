package com.companymanagement.service.impl;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.ApplicationStatusDao;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.service.ApplicationStatusService;

@Service("applicationStatusService")
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, rollbackFor = CompanyMgmtException.class)
public class ApplicationStatusServiceImpl extends BaseServiceImpl<Long, ApplicationStatus> implements ApplicationStatusService {

	// ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	// Validator validator = factory.getValidator();

	@Autowired
	protected ApplicationStatusDao dao;
	

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
	public void saveOrUpdate(ApplicationStatus newAppStatus) throws CompanyMgmtException {
		ApplicationStatus existingAppStatus = findbyUniqueStatus(newAppStatus.getStatusName());
		if (existingAppStatus != null) {
			newAppStatus.setId(existingAppStatus.getId());
			Mapper mapper = new DozerBeanMapper();
			mapper.map(newAppStatus,existingAppStatus); //object
			dao.merge(existingAppStatus);
			System.out.println("Duplicate");
		} else
		dao.persist(newAppStatus);
		
	}
	
	@Override
	public ApplicationStatus findbyUniqueStatus(String status) throws CompanyMgmtException {
		HashMap<String,String> params = new HashMap<>();
		params.put("statusName", status);
		if(dao.findByNamedQueryAndNamedParams("findUniqueStatus", params).size() == 0) {
			return null;
		}
		return dao.findByNamedQueryAndNamedParams("findUniqueStatus", params).get(0);	
	}

}
