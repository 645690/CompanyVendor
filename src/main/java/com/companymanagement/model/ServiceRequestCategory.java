package com.companymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "ServiceRequestCategory.findName", query = "SELECT src FROM ServiceRequestCategory src WHERE src.name=:name")
public class ServiceRequestCategory extends Base {

	private static final long serialVersionUID = -2601648818220480221L;

	@Column(unique = true)
	private String name;

	public ServiceRequestCategory() {
	}

	public ServiceRequestCategory(String name) {
		this.name = name;
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
		ServiceRequestCategory other = (ServiceRequestCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServiceRequestCategory [name=" + name + "]";
	}

}
