package com.companymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "ApplicationRequestStatus.findName", query = "SELECT ars FROM ApplicationRequestStatus ars WHERE ars.name=:name")
public class ApplicationRequestStatus extends Base {

	private static final long serialVersionUID = 2493798144839873798L;

	@Column(unique = true)
	private String name;

	public ApplicationRequestStatus() {
	}

	public ApplicationRequestStatus(String statusDescription) {
		this.name = statusDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ApplicationRequestStatus other = (ApplicationRequestStatus) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
