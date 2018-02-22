package com.companymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "ApplicationRequestCategory.findName", query = "SELECT arc FROM ApplicationRequestCategory arc WHERE arc.name=:name")
public class ApplicationRequestCategory extends Base {

	private static final long serialVersionUID = 4898619547179436975L;

	@Column(unique = true)
	private String name;

	public ApplicationRequestCategory() {
	}

	public ApplicationRequestCategory(String name) {
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
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ApplicationRequestCategory other = (ApplicationRequestCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
