package com.companymanagement.service;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Account;

public interface AccountService extends BaseService {

	Account findAccount(String username, String password);

	Account findAccountByUsername(String username) throws CompanyMgmtException;

	void create(Account account) throws CompanyMgmtException;

	void update(Account account) throws CompanyMgmtException;

}
