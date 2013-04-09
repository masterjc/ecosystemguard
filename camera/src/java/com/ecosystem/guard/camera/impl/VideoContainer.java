package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Container;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum VideoContainer implements Container {
	MP4(".mp4", "video/mp4"),
	THREE_GP(".3gp", "video/3gpp"),
	AVI(".avi", "video/avi");
	
	private String extension;
	private String contentType;
	
	VideoContainer(String extension, String contentType) {
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
