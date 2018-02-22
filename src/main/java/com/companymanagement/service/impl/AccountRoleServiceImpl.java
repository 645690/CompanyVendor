package com.companymanagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.dao.AccountRoleDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.AccountRole;
import com.companymanagement.service.AccountRoleService;

@Service("AccountRoleService")
public class AccountRoleServiceImpl extends BaseServiceImpl<Long, AccountRole> implements AccountRoleService {

	@Autowired
	protected AccountRoleDAO dao;

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
	public void saveOrUpdate(AccountRole accountRole) throws CompanyMgmtException {
		AccountRole findAccountRole = findAccountRole(accountRole.getName());
		if (findAccountRole != null) {
			findAccountRole.setName(accountRole.getName());
			dao.merge(findAccountRole);
		} else {
			dao.persist(accountRole);
		}
	}

	@Override
	public AccountRole findAccountRole(String name) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", name);

		List<AccountRole> accountRole = findByNamedQueryAndNamedParams("AccountRole.findName", queryParams);
		if (accountRole.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_ACCOUNTROLES_BY_SAME_NAME");
		}
		if (accountRole.size() == 0) {
			return null;
		}
		return accountRole.get(0);
	}

}
