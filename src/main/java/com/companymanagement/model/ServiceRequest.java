package com.companymanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "ServiceRequest.findRegNo", query = "SELECT sr FROM ServiceRequest sr WHERE sr.regNo=:regNo"),
		@NamedQuery(name = "ServiceRequest.findAllByStatus", query = "SELECT sr FROM ServiceRequest sr WHERE sr.status=:status"),
		@NamedQuery(name = "ServiceRequest.findAllByCompany", query = "SELECT sr FROM ServiceRequest sr WHERE sr.company=:company"),
		@NamedQuery(name = "ServiceRequest.findAllByDepartmentAndCompany", query = "SELECT sr FROM ServiceRequest sr WHERE sr.company=:company AND sr.department=:department OR sr.department=:anyDepartment"),
		@NamedQuery(name = "ServiceRequest.findByCompanyAndStatus", query = "SELECT sr FROM ServiceRequest sr WHERE sr.company=:company AND sr.status=:status"),
		@NamedQuery(name = "ServiceRequest.findByDepartmentAndCompanyAndStatus", query = "SELECT sr FROM ServiceRequest sr WHERE sr.status=:status AND sr.company=:company AND sr.department=:department OR sr.department=:anyDepartment") })
public class ServiceRequest extends Base {

	private static final long serialVersionUID = -4697330149497262611L;

	private Long regNo;
	private String name;

	@JoinColumn(name = "serviceRequest_regNo", referencedColumnName = "regNo")
	@OneToMany(cascade = CascadeType.ALL)
	private List<ServiceRequestApplication> srAppList = new ArrayList<ServiceRequestApplication>();

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "company_regNo", referencedColumnName = "regNo")
	private Company company;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Status", referencedColumnName = "name")
	private ServiceRequestStatus status;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "Department", referencedColumnName = "name")
	private Department department;

	public ServiceRequest() {
	}

	public ServiceRequest(Long regNo, String name) {
		this.regNo = regNo;
		this.name = name;
	}

	public Long getRegNo() {
		return regNo;
	}

	public void setRegNo(Long regNo) {
		this.regNo = regNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceRequestApplication> getSrAppList() {
		return srAppList;
	}

	public void setSrAppList(List<ServiceRequestApplication> srAppList) {
		this.srAppList = srAppList;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ServiceRequestStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceRequestStatus serviceRequestStatus) {
		this.status = serviceRequestStatus;
	}

	public void addServiceRequestApplication(ServiceRequestApplication sra) {
		srAppList.add(sra);
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
		result = prime * result + ((srAppList == null) ? 0 : srAppList.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceRequest other = (ServiceRequest) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (regNo == null) {
			if (other.regNo != null)
				return false;
		} else if (!regNo.equals(other.regNo))
			return false;
		if (srAppList == null) {
			if (other.srAppList != null)
				return false;
		} else if (!srAppList.equals(other.srAppList))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceRequest [regNo=" + regNo + ", name=" + name + ", srAppList=" + srAppList + ", company=" + company
				+ ", status=" + status + ", department=" + department + "]";
	}

}
