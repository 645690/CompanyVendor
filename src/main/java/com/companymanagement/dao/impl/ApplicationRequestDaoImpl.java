package com.companymanagement.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.companymanagement.dao.ApplicationRequestDao;
import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Company;

@Repository("applicationRequestDAO")
public class ApplicationRequestDaoImpl extends JPADAOImpl<Long, ApplicationRequest> implements ApplicationRequestDao {

	@Autowired
    EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext(unitName="CmpMgmt_PersistenceUnit")
	private EntityManager entityManager;
	
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
		super.setEntityManager(entityManager);
		}
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
        super.setEntityManager(entityManager);
    }

    

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public List<ApplicationRequest> findRequestbyCompanyAndStatus(Company company , ApplicationStatus status) {
		Query query = getEntityManager().createNamedQuery("findRequestByCompanyAndStatus");

			query.setParameter("company", company);
			query.setParameter("status", status);

		List<ApplicationRequest> result = (List<ApplicationRequest>) query.getResultList();
		return result;
	}
	
	
}
