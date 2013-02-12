package com.ecosystem.guard.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterRequest {
	private Credentials credentials;
	
	private EducationLevelType type;

	public Credentials getCredentials() {
		return credentials;
	}

	@XmlElement
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	@XmlElement
	public void setEducationLevelType(EducationLevelType type) {
		this.type = type;
	}
	
	public EducationLevelType getEducationLevelType() {
		return this.type;
	}
}
