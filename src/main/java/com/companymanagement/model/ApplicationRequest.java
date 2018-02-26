package com.companymanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "ApplicationRequest.findRegNo", query = "SELECT ar FROM ApplicationRequest ar WHERE ar.regNo=:regNo")
public class ApplicationRequest extends Base {

	private static final long serialVersionUID = -7374318586382201381L;

	@Column(unique = true)
	private Long regNo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status", referencedColumnName = "name")
	private ApplicationRequestStatus status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category", referencedColumnName = "name")
	private ApplicationRequestCategory category;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_regNo", referencedColumnName = "regNo")
	private Company company;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendor_regNo", referencedColumnName = "regNo")
	private Vendor vendor;

	public ApplicationRequest() {
	}

	public ApplicationRequest(ApplicationRequestStatus status, ApplicationRequestCategory category, Company company,
			Vendor vendor) {
		this.status = status;
		this.category = category;
		this.company = company;
		this.vendor = vendor;
	}

	public ApplicationRequestStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationRequestStatus status) {
		this.status = status;
	}

	public ApplicationRequestCategory getCategory() {
		return category;
	}

	public void setCategory(ApplicationRequestCategory category) {
		this.category = category;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Long getRegNo() {
		return regNo;
	}

	public void setRegNo(Long regNo) {
		this.regNo = regNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		ApplicationRequest other = (ApplicationRequest) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (regNo == null) {
			if (other.regNo != null)
				return false;
		} else if (!regNo.equals(other.regNo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}

}
