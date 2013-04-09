package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Codec;

/**
 * Codecs de Video soportados por EcosystemGuard
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum VideoCodec implements Codec {

	H264("libx264"),
	H263("h263");

	private String codec;

	private VideoCodec(String codecStr) {
		this.codec = codecStr;
	}

	public String getCodec() {
		return codec;
	}

}
