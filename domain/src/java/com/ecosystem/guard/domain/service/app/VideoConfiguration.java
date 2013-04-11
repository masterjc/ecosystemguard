package com.ecosystem.guard.domain.service.app;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class VideoConfiguration {
	
	private String codec;
	private String resolution;
	private String container;
	
	public String getCodec() {
		return codec;
	}
	
	@XmlElement
	public void setCodec(String codec) {
		this.codec = codec;
	}
	
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
