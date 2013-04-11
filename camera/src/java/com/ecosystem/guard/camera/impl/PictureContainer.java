package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Container;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum PictureContainer implements Container {
	JPEG(".jpg", "image/jpeg");
	
	private String extension;
	private String contentType;
	
	PictureContainer(String extension, String contentType) {
		this.extension = extension;
		this.contentType = contentType;
	}

	@Override
	public String getExtension() {
		return extension;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	
}
