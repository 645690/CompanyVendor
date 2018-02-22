package com.companymanagement.service;

import com.companymanagement.model.AccountRole;

public interface AccountRoleService extends BaseService {

	AccountRole findAccountRole(String accountRoleName);

	void saveOrUpdate(AccountRole accountRole);

}
