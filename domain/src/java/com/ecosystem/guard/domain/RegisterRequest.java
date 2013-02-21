package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterRequest extends Request {
	private AccountInformation accountInformation;

	public AccountInformation getAccountInformation() {
		return accountInformation;
	}

	@XmlElement
	public void setAccountInformation(AccountInformation accountInformation) {
		this.accountInformation = accountInformation;
	}
}
