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
import com.companymanagement.dao.AccountDAO;
import com.companymanagement.dao.JPADAO;
import com.companymanagement.model.Account;
import com.companymanagement.model.AccountRole;
import com.companymanagement.service.AccountRoleService;
import com.companymanagement.service.AccountService;

@Service("AccountService")
public class AccountServiceImpl extends BaseServiceImpl<Long, Account> implements AccountService {

	@Autowired
	protected AccountDAO dao;

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
	public void create(Account account) throws CompanyMgmtException {
		Account findAccount = findAccountByUsername(account.getUsername());
		if (findAccount == null) {
			dao.persist(account);
		} else
			throw new CompanyMgmtException("ACCOUNT_ALREADY_EXISTS");
	}

	@Override
	@Transactional
	public void update(Account account) throws CompanyMgmtException {
		Account findAccount = findAccountByUsername(account.getUsername());
		if (findAccount != null) {
			findAccount.setUsername(account.getUsername());
			findAccount.setPassword(account.getPassword());
			dao.merge(findAccount);
		} else
			throw new CompanyMgmtException("ACCOUNT_NOT_FOUND");
	}

	@Override
	@Transactional
	public Account findAccount(String username, String password) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("username", username);
		queryParams.put("password", password);

		List<Account> account = findByNamedQueryAndNamedParams("Account.findAccount", queryParams);
		if (account.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_ACCOUNTS_BY_SAME_NAME");
		}
		if (account.size() == 0) {
			return null;
		}
		return account.get(0);
	}

	@Override
	@Transactional
	public Account findAccountByUsername(String username) throws CompanyMgmtException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("username", username);

		List<Account> account = findByNamedQueryAndNamedParams("Account.findAccountByUsername", queryParams);
		if (account.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_ACCOUNTS_BY_SAME_NAME");
		}
		if (account.size() == 0) {
			return null;
		}
		return account.get(0);
	}

}
