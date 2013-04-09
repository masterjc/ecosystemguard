package com.ecosystem.guard.camera;


/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PictureConfig {

	private Resolution resolution;
	private Codec codec;
	
	public Resolution getResolution() {
		return resolution;
	}
	
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
	public Codec getCodec() {
		return codec;
	}
	
	public void setCodec(Codec codec) {
		this.codec = codec;
	}	
}
