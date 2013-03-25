package com.ecosystem.guard.camera;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum PictureCodec {
	JPEG("jpeg");

	private String codec;

	private PictureCodec(String codecStr) {
		this.codec = codecStr;
	}

	public String getCodec() {
		return codec;
	}
}
