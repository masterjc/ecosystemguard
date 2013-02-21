package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Credentials {
	private UsernamePassword usernamePassword;
	
	public Credentials() {
	}
	
	public Credentials( String username, String password ) {
		usernamePassword = new UsernamePassword(username, password);
	}
	
	public UsernamePassword getUsernamePassword() {
		return usernamePassword;
	}

	@XmlElement
	public void setUsernamePassword(UsernamePassword usernamePassword) {
		this.usernamePassword = usernamePassword;
	}
	
	public boolean defined() {
		return (usernamePassword != null) && usernamePassword.defined();
	}
}
