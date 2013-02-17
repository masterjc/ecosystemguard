package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountInformation {
	private String username;
	private String telephoneNumber;
	private String recoverMail;

	public AccountInformation() {
	}

	public String getUsername() {
		return username;
	}

	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	@XmlElement
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getRecoverMail() {
		return recoverMail;
	}

	@XmlElement
	public void setRecoverMail(String recoverMail) {
		this.recoverMail = recoverMail;
	}
}
