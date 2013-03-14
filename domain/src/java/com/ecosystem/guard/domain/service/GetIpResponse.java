package com.ecosystem.guard.domain.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Response;

@XmlRootElement
@XmlSeeAlso(GetIpStatus.class)
public class GetIpResponse extends Response {
	private IpInformation ipInformation;

	public IpInformation getIpInformation() {
		return ipInformation;
	}

	@XmlElement
	public void setIpInformation(IpInformation ipInformation) {
		this.ipInformation = ipInformation;
	}
}
