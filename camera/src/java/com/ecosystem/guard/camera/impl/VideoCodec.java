package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Codec;

/**
 * Codecs de Video soportados por EcosystemGuard
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum VideoCodec implements Codec {

	H264("libx264", "-preset veryfast -crf 28"),
	H263("h263", null);

	private String codec;
	private String options;

	private VideoCodec(String codecStr, String options) {
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
}
