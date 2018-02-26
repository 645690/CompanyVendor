package com.companymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="findUniqueStatus", query="select a from ApplicationStatus a where a.statusName=:statusName")
public class ApplicationStatus extends Base {
	
	@Column(unique=true)
	String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "ApplicationStatus [statusName=" + statusName + "]";
	}
	
	

}
