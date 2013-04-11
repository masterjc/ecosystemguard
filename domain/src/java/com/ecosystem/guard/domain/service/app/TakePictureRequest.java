package com.ecosystem.guard.domain.service.app;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Request;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class TakePictureRequest extends Request {
	private PictureConfiguration config;
	
	public PictureConfiguration getPictureConfiguration() {
		return config;
	}
	
	@XmlElement
	public void setPictureConfiguration(PictureConfiguration pictureConfiguration) {
		this.config = pictureConfiguration;
	}
}
