package com.ecosystem.guard.domain.service.registry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Request;

@XmlRootElement
public class GetIpRequest extends Request {
	private String hostId;

	public String getHostId() {
		return hostId;
	}

	@XmlElement
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

}
