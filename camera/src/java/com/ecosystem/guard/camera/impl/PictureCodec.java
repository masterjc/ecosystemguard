package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Codec;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum PictureCodec implements Codec {

	JPEG("jpeg");

	private String codec;

	private PictureCodec(String codecStr) {
		this.codec = codecStr;
	}

	public String getCodec() {
		return codec;
	}

}
