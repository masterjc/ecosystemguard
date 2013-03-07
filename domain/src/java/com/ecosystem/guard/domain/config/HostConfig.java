package com.ecosystem.guard.domain.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Credentials;

@XmlRootElement(name = "host")
public class HostConfig {
	private String id;
	private Credentials credentials;

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	@XmlElement
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
}
