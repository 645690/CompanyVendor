package com.companymanagement.service;

import java.util.List;

import com.companymanagement.common.CompanyMgmtException;
import com.companymanagement.model.Company;
import com.companymanagement.model.Employee;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.model.Vendor;

public interface ServiceRequestService extends BaseService {

	public void saveOrUpdate(ServiceRequest serviceRequest) throws CompanyMgmtException;

	public ServiceRequest findServiceRequestByRegNo(Long regNo) throws CompanyMgmtException;

	void deleteByRegNo(ServiceRequest serviceRequest) throws CompanyMgmtException;

	List<ServiceRequestApplication> findServiceRequestApplicationsByRegNo(Long regNo) throws CompanyMgmtException;

	void addServiceRequestApplication(Long srRegNo, ServiceRequestApplication srApp) throws CompanyMgmtException;

	List<ServiceRequest> findAllServiceRequestsByCompany(Company company) throws CompanyMgmtException;

	List<ServiceRequest> findAllServiceRequestsByEmployeeDepartmentAndCompany(Employee employee)
			throws CompanyMgmtException;

	List<ServiceRequest> findPendingServiceRequestsByCompany(Company company) throws CompanyMgmtException;

	List<ServiceRequest> findPendingServiceRequestsByEmployeeDepartmentAndCompany(Employee employee)
			throws CompanyMgmtException;

	void acceptServiceRequest(Long srRegNo) throws CompanyMgmtException;

	void rejectServiceRequest(Long srRegNo) throws CompanyMgmtException;

	List<ServiceRequest> findAllPendingServiceRequests() throws CompanyMgmtException;

	List<ServiceRequest> findAllAcceptedServiceRequests() throws CompanyMgmtException;

}
