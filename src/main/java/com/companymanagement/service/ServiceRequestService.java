package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;

public interface ServiceRequestService extends BaseService {

	public void saveOrUpdate(ServiceRequest serviceRequest) throws CompanyMgmtException;

	public ServiceRequest findServiceRequestByRegNo(Long regNo) throws CompanyMgmtException;

	void deleteByRegNo(ServiceRequest serviceRequest) throws CompanyMgmtException;

	List<ServiceRequestApplication> findServiceRequestApplicationsByRegNo(Long regNo) throws CompanyMgmtException;

}
