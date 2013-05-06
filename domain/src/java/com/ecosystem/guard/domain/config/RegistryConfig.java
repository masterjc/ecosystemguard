package com.ecosystem.guard.domain.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class RegistryConfig {
	private String registryUrl;
	
	public String getRegistryUrl() {
		return registryUrl;
	}
	
	@XmlElement
	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}
	
	public void check() throws Exception {
		if( registryUrl == null )
			throw new Exception("registryUrl config property is not set");
	}
}
