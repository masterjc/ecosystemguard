package com.ecosystem.guard.domain.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DAO object for accesing account information
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "accountInfo")
public class AccountInfo implements Serializable {
	@Id
	@Column(name = "username", nullable = false, length = 254)
	private String username;

	@Column(name = "password", nullable = false, length = 64)
	private String password;

	@Column(name = "telephoneNumber", nullable = true, length = 32)
	private String telephoneNumber;

	@Column(name = "recoverMail", nullable = true, length = 254)
	private String recoverMail;

	public AccountInfo() {
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

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getRecoverMail() {
		return recoverMail;
	}

	public void setRecoverMail(String recoverMail) {
		this.recoverMail = recoverMail;
	}

}
