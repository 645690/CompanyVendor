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
import com.companymanagement.dao.ApplicationCategoryDao;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.ApplicationCategory;
import com.companymanagement.service.ApplicationCategoryService;

@Service("applicationCategoryService")
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED, rollbackFor = CompanyMgmtException.class)
public class ApplicationCategoryServiceImpl extends BaseServiceImpl<Long, ApplicationCategory> implements ApplicationCategoryService {

	// ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	// Validator validator = factory.getValidator();

	@Autowired
	protected ApplicationCategoryDao dao;
	

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
	public void saveOrUpdate(ApplicationCategory newAppCategory) throws CompanyMgmtException {
		ApplicationCategory existingAppCategory = findbyUniqueCategory(newAppCategory.getCategoryName());
		if (existingAppCategory != null) {
			newAppCategory.setId(existingAppCategory.getId());
			Mapper mapper = new DozerBeanMapper();
			mapper.map(newAppCategory,existingAppCategory); //object
			dao.merge(existingAppCategory);
			System.out.println("Duplicate");
		} else
		dao.persist(newAppCategory);
		
	}
	
	@Override
	public ApplicationCategory findbyUniqueCategory(String category) throws CompanyMgmtException {
		HashMap<String,String> params = new HashMap<>();
		params.put("categoryName", category);
		if(dao.findByNamedQueryAndNamedParams("findUniqueCategory", params).size() == 0) {
			return null;
		}
		return dao.findByNamedQueryAndNamedParams("findUniqueCategory", params).get(0);	
	}

}
