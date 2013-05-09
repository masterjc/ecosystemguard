package com.ecosystem.guard.pc;

import com.ecosystem.guard.domain.Credentials;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class Session {

	private String appIpAddress;
	private Credentials credentials;
	private String hostId;

	public String getAppIpAddress() {
		return appIpAddress;
	}

	public void setAppIpAddress(String appIpAddress) {
		this.appIpAddress = appIpAddress;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
}
