package com.companymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name="findUniqueRequest", query="select arq from ApplicationRequest arq where arq.applicationNo=:applicationNo"),
@NamedQuery(name="findRequestByCompanyAndStatus", query="select arq from ApplicationRequest arq where arq.company=:company and arq.appStatus=:status"),
@NamedQuery(name="findRequestByVendor", query="select arq from ApplicationRequest arq where arq.vendor=:vendor")})
public class ApplicationRequest extends Base {
	
	@Column(unique=true)
	String applicationNo;
	
	@ManyToOne
	ApplicationStatus appStatus;
	
	@ManyToOne
	ApplicationCategory appType;
	
	@ManyToOne
	Vendor vendor;
	
	@ManyToOne
	Company company;

	public ApplicationStatus getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(ApplicationStatus appStatus) {
		this.appStatus = appStatus;
	}

	public ApplicationCategory getAppType() {
		return appType;
	}

	public void setAppType(ApplicationCategory appType) {
		this.appType = appType;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	@Override
	public String toString() {
		return "ApplicationRequest [applicationNo=" + applicationNo + ", appStatus=" + appStatus + ", appType="
				+ appType + ", vendor=" + vendor + ", company=" + company + "]";
	}

	
	
}
