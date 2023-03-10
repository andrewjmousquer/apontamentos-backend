package com.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserModel implements Serializable {

	private static final long serialVersionUID = -6179354323140877535L;

	private Integer id;
	private String username;
	private String password;
	private ClassifierModel userType;
	private Boolean enabled;
	private PersonModel person;
	private List<CustomerModel> customers;
	private CustomerModel customer;
	private List<Contact> contacts;
	private Boolean changePass;
	private Boolean expirePass;
	private Integer passErrorCount;
	private String forgotKey;
	private Date forgotKeyCreated;
	private Date lastPassChange;
	private AccessListModel accessList;
	private Boolean blocked;
	private Date lastLogin;
	private Integer lastErrorCount;
	private String config;
	private String token;
	private String hashQRCode;

	public UserModel() {
	}

	public UserModel(Integer id) {
		this.id = id;
	}

	public UserModel(String username) {
		this.username = username;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
