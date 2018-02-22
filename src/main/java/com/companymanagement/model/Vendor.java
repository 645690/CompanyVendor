package com.companymanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({ @NamedQuery(name = "Vendor.findVendorReg", query = "SELECT v FROM Vendor v WHERE v.regNo=:regNo"),
		@NamedQuery(name = "Vendor.findByAccount", query = "SELECT v FROM Vendor v WHERE v.account=:account"),
		@NamedQuery(name = "Vendor.findByStatus", query = "SELECT v FROM Vendor v WHERE v.status=:status") })
public class Vendor extends Base {

	private static final long serialVersionUID = -5029963458895701323L;

	@Column(unique = true)
	private Long regNo;
	private String name;
	private String email;
	private String contact;
	private String status;

	@JoinColumn(name = "npt_name", referencedColumnName = "name")
	@ManyToOne(cascade = CascadeType.MERGE)
	private NotificationPreferedType npt;

	@JoinColumn(name = "cert")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Certification> certList = new ArrayList<Certification>();

	@OneToOne(cascade = CascadeType.MERGE)
	private Account account;

	public Vendor() {
	}

	public Vendor(Long regNo, String name) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public NotificationPreferedType getNpt() {
		return npt;
	}

	public void setNpt(NotificationPreferedType npt) {
		this.npt = npt;
	}

	public List<Certification> getCertList() {
		return certList;
	}

	public void setCertList(List<Certification> certList) {
		this.certList = certList;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((certList == null) ? 0 : certList.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((npt == null) ? 0 : npt.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
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
		Vendor other = (Vendor) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (certList == null) {
			if (other.certList != null)
				return false;
		} else if (!certList.equals(other.certList))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (npt == null) {
			if (other.npt != null)
				return false;
		} else if (!npt.equals(other.npt))
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
		return true;
	}

	@Override
	public String toString() {
		return "Vendor [regNo=" + regNo + ", name=" + name + ", email=" + email + ", contact=" + contact + ", status="
				+ status + ", npt=" + npt + ", certList=" + certList + ", account=" + account + "]";
	}

}
