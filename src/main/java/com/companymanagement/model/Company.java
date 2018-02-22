package com.companymanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Company.findRegNo", query = "SELECT c FROM Company c WHERE c.regNo=:regNo")
public class Company extends Base {

	private static final long serialVersionUID = 6837969751019580537L;

	@Column(unique = true)
	private Long regNo;
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "regNo"),
				inverseJoinColumns = @JoinColumn(name = "vendor_id", referencedColumnName = "regNo"))
	private List<Vendor> venList = new ArrayList<Vendor>();

	public Company() {
	}

	public Company(Long regNo, String name) {
		this.regNo = regNo;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vendor> getVenList() {
		return venList;
	}

	public void setVenList(List<Vendor> venList) {
		this.venList = venList;
	}

	public void addVendor(Vendor vendor) {
		venList.add(vendor);
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
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
		result = prime * result + ((venList == null) ? 0 : venList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
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
		if (venList == null) {
			if (other.venList != null)
				return false;
		} else if (!venList.equals(other.venList))
			return false;
		return true;
	}
	
	

}
