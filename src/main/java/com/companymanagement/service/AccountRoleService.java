package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.AccountRole;

public interface AccountRoleService extends BaseService {

	AccountRole findAccountRole(String accountRoleName);

	void saveOrUpdate(AccountRole accountRole);

	List<AccountRole> findCompanyAdminAndEmployee() throws CompanyMgmtException;

}
