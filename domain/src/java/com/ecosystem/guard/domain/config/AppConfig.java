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
	private Integer updatePublicIpThreshold;
	private String registryUrl;
	
	public Integer getUpdatePublicIpThreshold() {
		return updatePublicIpThreshold;
	}
	
	@XmlElement
	public void setUpdatePublicIpThreshold(int updatePublicIpThreshold) {
		this.updatePublicIpThreshold = updatePublicIpThreshold;
	}
	
	public String getRegistryUrl() {
		return registryUrl;
	}
	
	@XmlElement
	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}
	
	public void check() throws Exception {
		if( updatePublicIpThreshold == null )
			throw new Exception("updatePublicIpThreshold config property is not set");
		if( registryUrl == null )
			throw new Exception("registryUrl config property is not set");
	}
}
