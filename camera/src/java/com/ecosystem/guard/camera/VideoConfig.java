package com.ecosystem.guard.camera;

/**
 * Configuración de parámetros básicos de video
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfig {

	private Resolution resolution;
	private Codec videoCodec;
	private Container container;
	private String optionalOptions;

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Codec getVideoCodec() {
		return videoCodec;
	}

	public void setVideoCodec(Codec videoCodec) {
		this.videoCodec = videoCodec;
	}

	public void setOptionalOptions(String options) {
		this.optionalOptions = options;
	}

	public String getOptionalOptions() {
		return optionalOptions;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

}
