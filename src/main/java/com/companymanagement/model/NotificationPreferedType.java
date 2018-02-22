package com.companymanagement.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "NotificationPreferedType.findName", query = "SELECT npt FROM NotificationPreferedType npt WHERE npt.name=:name") })
public class NotificationPreferedType extends Base {

	private static final long serialVersionUID = -8807848647055893151L;

	private String name;

	public NotificationPreferedType() {
	}

	public NotificationPreferedType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
