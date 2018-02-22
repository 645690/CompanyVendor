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
import com.companymanagement.dao.NotificationPreferedTypeDAO;
import com.companymanagement.model.Department;
import com.companymanagement.model.NotificationPreferedType;
import com.companymanagement.service.NotificationPreferedTypeService;

@Service("NotificationPreferedTypeService")
public class NotificationPreferedTypeServiceImpl extends BaseServiceImpl<Long, NotificationPreferedType>
		implements NotificationPreferedTypeService {

	@Autowired
	protected NotificationPreferedTypeDAO dao;

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
	public void saveOrUpdate(NotificationPreferedType npt) throws CompanyMgmtException {
		NotificationPreferedType findNPT = findNotificationPreferedType(npt.getName());
		if (findNPT != null) {
			findNPT.setName(npt.getName());
			dao.merge(findNPT);
		} else {
			dao.persist(npt);
		}
	}

	@Override
	public NotificationPreferedType findNotificationPreferedType(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<NotificationPreferedType> department = findByNamedQueryAndNamedParams("NotificationPreferedType.findName", queryParams);
		if (department.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_NotificationPreferedType_BY_SAME_NAME");
		}
		if (department.size() == 0) {
			return null;
		}
		return department.get(0);
	}

}
