package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterRequest {
	private Credentials credentials;
	private AccountInformation accountInformation;

	public AccountInformation getAccountInformation() {
		return accountInformation;
	}

	@XmlElement
	public void setAccountInformation(AccountInformation accountInformation) {
		this.accountInformation = accountInformation;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	@XmlElement
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	
}
