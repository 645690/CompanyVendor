package com.companymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="findUniqueCategory", query="select ac from ApplicationCategory ac where ac.categoryName=:categoryName")
public class ApplicationCategory extends Base {
	
	@Column(unique=true)
	String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplicationCategory [categoryName=" + categoryName + "]";
	}
	
	

}
