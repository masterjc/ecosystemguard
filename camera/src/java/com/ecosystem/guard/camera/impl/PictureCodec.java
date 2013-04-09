package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Codec;
import com.ecosystem.guard.camera.Resolution;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum PictureCodec implements Codec {

	JPEG("jpeg", null);

	private String codec;
	private String options;

	private PictureCodec(String codecStr, String options) {
		this.codec = codecStr;
	}

	@Override
	public String getCodec() {
		return codec;
	}
	
	@Override
	public String getCodecOptions() {
		return options;
	}

	/* (non-Javadoc)
	 * @see com.ecosystem.guard.camera.Codec#getResolutionClass()
	 */
	@Override
	public Class<? extends Resolution> getResolutionClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
