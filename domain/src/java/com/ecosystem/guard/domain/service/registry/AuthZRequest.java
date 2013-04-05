package com.ecosystem.guard.domain.service.registry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Request;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class AuthZRequest extends Request {
	private String hostId;
	private String resourceId;

	public String getHostId() {
		return hostId;
	}

	@XmlElement
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getResourceId() {
		return resourceId;
	}

	@XmlElement
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
