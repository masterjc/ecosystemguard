package com.ecosystem.guard.pc;

import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.service.registry.HostInformation;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class Session {

	private String appIpAddress;
	private Credentials credentials;
	private HostInformation hostInfo;

	public String getAppIpAddress() {
		return appIpAddress;
	}
	
	public String getAppUrl() {
		return ClientConstants.ECOSYSTEM_URL_HEAD + getAppIpAddress() + ClientConstants.ECOSYSTEM_APP_URL_ENDING;			
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

	public HostInformation getHostInformation() {
		return hostInfo;
	}

	public void setHostInformation(HostInformation hostId) {
		this.hostInfo = hostId;
	}
}
