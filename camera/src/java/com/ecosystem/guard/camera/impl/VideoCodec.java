package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Codec;
import com.ecosystem.guard.camera.Resolution;

/**
 * Codecs de Video soportados por EcosystemGuard
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum VideoCodec implements Codec {

	H264("libx264", "-preset veryfast -crf 28", H264Resolution.class),
	H263("h263", null, H263Resolution.class);

	private String codec;
	private String options;
	private Class<? extends Resolution> resolutionClass;

	private VideoCodec(String codecStr, String options, Class<? extends Resolution> resolution) {
		this.codec = codecStr;
		this.options = options;
		this.resolutionClass = resolution;
	}

	@Override
	public String getCodec() {
		return codec;
	}

	@Override
	public String getCodecOptions() {
		return options;
	}

	
	@Override
	public Class<? extends Resolution> getResolutionClass() {
		return resolutionClass;
	}
}
