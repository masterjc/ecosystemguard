package com.ecosystem.guard.camera;


/**
 * Configuración de parámetros básicos de video
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfig {

	private Resolution resolution;
	private VideoCodec videoCodec;
	private String containerExtension;
	private String optionalOptions;

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public VideoCodec getVideoCodec() {
		return videoCodec;
	}

	public void setVideoCodec(VideoCodec videoCodec) {
		this.videoCodec = videoCodec;
	}
	
	public void setOptionalOptions(String options) {
		this.optionalOptions = options;
	}
	
	public String getOptionalOptions() {
		return optionalOptions;
	}

	public String getContainerExtension() {
		return containerExtension;
	}

	public void setContainerExtension(String containerExtension) {
		this.containerExtension = containerExtension;
	}
	
	
	
}
