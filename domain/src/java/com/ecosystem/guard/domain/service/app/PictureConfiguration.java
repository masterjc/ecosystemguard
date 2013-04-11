package com.ecosystem.guard.domain.service.app;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PictureConfiguration {

	private String resolution;
	private String container;
	
	public String getResolution() {
		return resolution;
	}
	
	@XmlElement
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	public String getContainer() {
		return container;
	}
	
	@XmlElement
	public void setContainer(String container) {
		this.container = container;
	}

}
