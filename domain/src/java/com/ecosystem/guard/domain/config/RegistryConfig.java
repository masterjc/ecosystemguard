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
	private String url;
	
	public String getUrl() {
		return url;
	}
	
	@XmlElement
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void check() throws Exception {
		if( url == null )
			throw new Exception("url config property is not set");
	}
}
