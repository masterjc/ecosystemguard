package com.ecosystem.guard.camera;

/**
 * Codecs de Video soportados por EcosystemGuard
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum VideoCodec {

	MP4("h264");

	private String codec;

	private VideoCodec(String codecStr) {
		this.codec = codecStr;
	}

	public String getCodec() {
		return codec;
	}

}
