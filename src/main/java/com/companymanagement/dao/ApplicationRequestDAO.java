package com.companymanagement.dao;

import java.util.List;

import com.companymanagement.model.ApplicationRequest;
import com.companymanagement.model.ApplicationStatus;
import com.companymanagement.model.Company;

public interface ApplicationRequestDao extends JPADAO<ApplicationRequest, Long>{


	List<ApplicationRequest> findRequestbyCompanyAndStatus(Company company, ApplicationStatus status);

}
