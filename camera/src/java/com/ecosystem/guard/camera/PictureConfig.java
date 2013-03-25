package com.ecosystem.guard.camera;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PictureConfig {

	private Resolution resolution;
	private PictureCodec codec;
	
	public Resolution getResolution() {
		return resolution;
	}
	
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
	public PictureCodec getCodec() {
		return codec;
	}
	
	public void setCodec(PictureCodec codec) {
		this.codec = codec;
	}	
}
