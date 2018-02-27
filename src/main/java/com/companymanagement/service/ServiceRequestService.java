package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Company;
import com.companymanagement.model.Employee;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;

public interface ServiceRequestService extends BaseService {

	public void saveOrUpdate(ServiceRequest serviceRequest) throws CompanyMgmtException;

	public ServiceRequest findServiceRequestByRegNo(Long regNo) throws CompanyMgmtException;

	void deleteByRegNo(ServiceRequest serviceRequest) throws CompanyMgmtException;

	List<ServiceRequestApplication> findServiceRequestApplicationsByRegNo(Long regNo) throws CompanyMgmtException;

	void addServiceRequestApplication(Long srRegNo, ServiceRequestApplication srApp) throws CompanyMgmtException;

	List<ServiceRequest> findServiceRequestsByCompany(Company company) throws CompanyMgmtException;

	List<ServiceRequest> findServiceRequestsByEmployeeDepartmentAndCompany(Employee employee)
			throws CompanyMgmtException;

	void acceptServiceRequest(Long srRegNo) throws CompanyMgmtException;

	void rejectServiceRequest(Long srRegNo) throws CompanyMgmtException;

}
