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
import com.companymanagement.dao.ServiceRequestDAO;
import com.companymanagement.model.Company;
import com.companymanagement.model.Department;
import com.companymanagement.model.Employee;
import com.companymanagement.model.ServiceRequest;
import com.companymanagement.model.ServiceRequestApplication;
import com.companymanagement.model.ServiceRequestStatus;
import com.companymanagement.service.DepartmentService;
import com.companymanagement.service.ServiceRequestService;
import com.companymanagement.service.ServiceRequestStatusService;

@Service("ServiceRequestService")
public class ServiceRequestServiceImpl extends BaseServiceImpl<Long, ServiceRequest> implements ServiceRequestService {

	@Autowired
	protected ServiceRequestDAO dao;

	@Autowired
	protected ServiceRequestStatusService serviceRequestStatusService;

	@Autowired
	protected DepartmentService departmentService;

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
	public void addServiceRequestApplication(Long srRegNo, ServiceRequestApplication serviceRequestApplication)
			throws CompanyMgmtException {
		ServiceRequestStatus srs = serviceRequestStatusService.findServiceRequestStatusByName("Pending");
		serviceRequestApplication.setStatus(srs);
		ServiceRequest findServiceRequest = findServiceRequestByRegNo(srRegNo);
		if (findServiceRequest != null) {
			findServiceRequest.addServiceRequestApplication(serviceRequestApplication);
			dao.merge(findServiceRequest);
		} else
			throw new CompanyMgmtException("Service Request not found");
	}

	@Override
	@Transactional
	public void saveOrUpdate(ServiceRequest serviceRequest) throws CompanyMgmtException {
		ServiceRequestStatus srs = serviceRequestStatusService.findServiceRequestStatusByName("Pending");
		serviceRequest.setStatus(srs);
		Department department = departmentService.findDepartment(serviceRequest.getDepartment().getName());
		serviceRequest.setDepartment(department);
		ServiceRequest findServiceRequest = findServiceRequestByRegNo(serviceRequest.getRegNo());
		if (findServiceRequest != null) {
			findServiceRequest.setName(serviceRequest.getName());
			findServiceRequest.setStatus(serviceRequest.getStatus());
			findServiceRequest.setDepartment(serviceRequest.getDepartment());
			findServiceRequest.setSrAppList(serviceRequest.getSrAppList());
			dao.merge(findServiceRequest);
		} else {
			dao.persist(serviceRequest);
		}
	}

	@Override
	@Transactional
	public ServiceRequest findServiceRequestByRegNo(Long regNo) throws CompanyMgmtException {
		Map<String, Long> queryParams = new HashMap<String, Long>();
		queryParams.put("regNo", regNo);

		List<ServiceRequest> serviceRequests = findByNamedQueryAndNamedParams("ServiceRequest.findRegNo", queryParams);
		if (serviceRequests.size() > 1) {
			throw new CompanyMgmtException("TOO_MANY_SERVICEREQUEST_BY_SAME_NAME");
		}
		if (serviceRequests.size() == 0) {
			return null;
		}
		return serviceRequests.get(0);
	}

	@Override
	@Transactional
	public List<ServiceRequestApplication> findServiceRequestApplicationsByRegNo(Long regNo)
			throws CompanyMgmtException {
		ServiceRequest serviceRequest = findServiceRequestByRegNo(regNo);
		List<ServiceRequestApplication> srAppList = serviceRequest.getSrAppList();
		srAppList.size();
		return srAppList;
	}

	@Override
	@Transactional
	public void deleteByRegNo(ServiceRequest serviceRequest) throws CompanyMgmtException {
		ServiceRequest findServiceRequest = findServiceRequestByRegNo(serviceRequest.getRegNo());
		if (findServiceRequest != null) {
			dao.remove(findServiceRequest);
		}
	}

	@Override
	@Transactional
	public void acceptServiceRequest(Long srRegNo) throws CompanyMgmtException {
		ServiceRequest serviceRequest = findServiceRequestByRegNo(srRegNo);
		serviceRequest.setStatus(serviceRequestStatusService.findServiceRequestStatusByName("Accepted"));
		dao.merge(serviceRequest);
	}

	@Override
	@Transactional
	public void rejectServiceRequest(Long srRegNo) throws CompanyMgmtException {
		ServiceRequest serviceRequest = findServiceRequestByRegNo(srRegNo);
		serviceRequest.setStatus(serviceRequestStatusService.findServiceRequestStatusByName("Rejected"));
		dao.merge(serviceRequest);
	}

	@Override
	@Transactional
	public List<ServiceRequest> findAllServiceRequestsByEmployeeDepartmentAndCompany(Employee employee)
			throws CompanyMgmtException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("company", employee.getCompany());
		queryParams.put("department", employee.getDepartment());
		queryParams.put("anyDepartment", departmentService.findDepartment("Any"));

		List<ServiceRequest> serviceRequests = findByNamedQueryAndNamedParams(
				"ServiceRequest.findAllByDepartmentAndCompany", queryParams);
		if (serviceRequests.size() == 0) {
			return null;
		}
		return serviceRequests;
	}

	@Override
	@Transactional
	public List<ServiceRequest> findPendingServiceRequestsByEmployeeDepartmentAndCompany(Employee employee)
			throws CompanyMgmtException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("company", employee.getCompany());
		queryParams.put("department", employee.getDepartment());
		queryParams.put("anyDepartment", departmentService.findDepartment("Any"));
		queryParams.put("status", serviceRequestStatusService.findServiceRequestStatusByName("pending"));

		List<ServiceRequest> serviceRequests = findByNamedQueryAndNamedParams(
				"ServiceRequest.findByDepartmentAndCompanyAndStatus", queryParams);
		if (serviceRequests.size() == 0) {
			return null;
		}
		return serviceRequests;
	}

	@Override
	@Transactional
	public List<ServiceRequest> findAllServiceRequestsByCompany(Company company) throws CompanyMgmtException {
		Map<String, Company> queryParams = new HashMap<String, Company>();
		queryParams.put("company", company);

		List<ServiceRequest> serviceRequests = findByNamedQueryAndNamedParams("ServiceRequest.findAllByCompany",
				queryParams);
		if (serviceRequests.size() == 0) {
			return null;
		}
		return serviceRequests;
	}

	@Override
	@Transactional
	public List<ServiceRequest> findPendingServiceRequestsByCompany(Company company) throws CompanyMgmtException {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("company", company);
		queryParams.put("status", serviceRequestStatusService.findServiceRequestStatusByName("pending"));

		List<ServiceRequest> serviceRequests = findByNamedQueryAndNamedParams("ServiceRequest.findByCompany",
				queryParams);
		if (serviceRequests.size() == 0) {
			return null;
		}
		return serviceRequests;
	}

}
