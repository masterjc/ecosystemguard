package com.ecosystem.guard.camera;

/**
 * Configuración de parámetros básicos de video
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfig {

	private Resolution resolution;
	private String bitrate;
	private int fps;
	private VideoCodec videoCodec;

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public VideoCodec getVideoCodec() {
		return videoCodec;
	}

	public void setVideoCodec(VideoCodec videoCodec) {
		this.videoCodec = videoCodec;
	}
	
}
