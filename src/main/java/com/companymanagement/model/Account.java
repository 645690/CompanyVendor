package com.companymanagement.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Account.findAccount", query = "SELECT a FROM Account a WHERE a.username=:username AND a.password=:password"),
		@NamedQuery(name = "Account.findAccountByUsername", query = "SELECT a from Account a WHERE a.username=:username") })
public class Account extends Base {

	private static final long serialVersionUID = 2123344216142245806L;

	@Column(unique = true)
	private String username;
	private String password;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_Role", referencedColumnName = "name")
	private AccountRole accountRole;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "permission", referencedColumnName = "name")
	private Set<Permission> permission;

	public Account() {
	}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountRole getAccountRole() {
		return accountRole;
	}

	public void setAccountRole(AccountRole accountRole) {
		this.accountRole = accountRole;
	}

	public Set<Permission> getPermission() {
		return permission;
	}

	public void setPermission(Set<Permission> permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", accountRole=" + accountRole
				+ ", permission=" + permission + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accountRole == null) ? 0 : accountRole.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Account other = (Account) obj;
		if (accountRole == null) {
			if (other.accountRole != null)
				return false;
		} else if (!accountRole.equals(other.accountRole))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
