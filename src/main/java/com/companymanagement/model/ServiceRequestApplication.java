package com.companymanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "ServiceRequestApplication.findRegNo", query = "SELECT sra FROM ServiceRequestApplication sra WHERE sra.regNo=:regNo"),
		@NamedQuery(name = "ServiceRequestApplication.findByVendor", query = "SELECT sra FROM ServiceRequestApplication sra WHERE sra.regNo=:regNo") })
public class ServiceRequestApplication extends Base {

	private static final long serialVersionUID = -266578485313115304L;

	@Column(unique = true)
	private Long regNo;
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Status", referencedColumnName = "name")
	private ServiceRequestStatus status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Vendor_regNo", referencedColumnName = "regNo")
	private Vendor vendor;

	public ServiceRequestApplication() {
	}

	public ServiceRequestApplication(Long regNo) {
		this.regNo = regNo;
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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public ServiceRequestStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceRequestStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ServiceRequestApplication other = (ServiceRequestApplication) obj;
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

	@Override
	public String toString() {
		return "ServiceRequestApplication [regNo=" + regNo + ", name=" + name + ", status=" + status + ", vendor="
				+ vendor + "]";
	}

}
