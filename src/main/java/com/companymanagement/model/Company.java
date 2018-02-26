package com.companymanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@NamedQueries({ @NamedQuery(name = "Company.findRegNo", query = "SELECT c FROM Company c WHERE c.regNo=:regNo"),
		@NamedQuery(name = "Company.findByAccount", query = "SELECT c FROM Company c WHERE c.account=:account"),
		@NamedQuery(name = "Company.findByStatus", query = "SELECT c FROM Company c WHERE c.status=:status") })
public class Company extends Base {

	private static final long serialVersionUID = 6837969751019580537L;

	@Column(unique = true)
	private Long regNo;
	private String name;
	private String status;

	// Used for file upload + setter getter (1)
	private String docFileUrl;
	@Transient
	private byte docByteArray[];
	@Transient
	private String docFileExtention;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "regNo"), inverseJoinColumns = @JoinColumn(name = "vendor_id", referencedColumnName = "regNo"))
	private List<Vendor> venList = new ArrayList<Vendor>();

	@OneToOne(cascade = CascadeType.MERGE)
	private Account account;

	public Company() {
	}

	public Company(Long regNo, String name) {
		this.regNo = regNo;
		this.name = name;
	}

	public void addVendor(Vendor vendor) {
		venList.add(vendor);
	}

	public Account getAccount() {
		return account;
	}

	public byte[] getDocByteArray() {
		return docByteArray;
	}

	public String getDocFileExtention() {
		return docFileExtention;
	}

	public String getDocFileUrl() {
		return docFileUrl;
	}

	public String getName() {
		return name;
	}

	public Long getRegNo() {
		return regNo;
	}

	public String getStatus() {
		return status;
	}

	public List<Vendor> getVenList() {
		return venList;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setDocByteArray(byte[] docByteArray) {
		this.docByteArray = docByteArray;
	}

	public void setDocFileExtention(String docFileExtention) {
		this.docFileExtention = docFileExtention;
	}

	public void setDocFileUrl(String docFileUrl) {
		this.docFileUrl = docFileUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegNo(Long regNo) {
		this.regNo = regNo;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setVenList(List<Vendor> venList) {
		this.venList = venList;
	}

	
	
}
