package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Credentials {
	private UsernamePassword usernamePassword;

	public UsernamePassword getUsernamePassword() {
		return usernamePassword;
	}

	@XmlElement
	public void setUsernamePassword(UsernamePassword usernamePassword) {
		this.usernamePassword = usernamePassword;
	}
}
