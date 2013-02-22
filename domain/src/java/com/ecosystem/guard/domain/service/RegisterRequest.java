package com.ecosystem.guard.domain.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Request;

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
