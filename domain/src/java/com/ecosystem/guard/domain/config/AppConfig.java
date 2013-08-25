package com.ecosystem.guard.domain.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class AppConfig {
	private String updateIpThreshold;
	private String registryUrl;
	
	@XmlElement
	public void setUpdateIpThreshold(String updateIpThreshold) {
		this.updateIpThreshold = updateIpThreshold;
	}
	
	public String getUpdateIpThreshold() {
		return updateIpThreshold;
	}
	
	@XmlElement
	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}
	
	public String getRegistryUrl() {
		return registryUrl;
	}
	
	public void check() throws Exception {
		if( updateIpThreshold == null )
			throw new Exception("updateIpThreshold config property is not set");
		if( registryUrl == null )
			throw new Exception("registryUrl config property is not set");
	}
}
