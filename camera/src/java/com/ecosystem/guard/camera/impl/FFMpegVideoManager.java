package com.ecosystem.guard.camera.impl;

import java.io.File;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.VideoManager;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegVideoManager implements VideoManager {
	private File cameraDevice;

	/**
	 * Construye un video manager para grabar desde el dispositivo indicado
	 * 
	 * @param linuxCameraDevice Ejemplo: /dev/video0
	 * @throws Exception
	 */
	public FFMpegVideoManager(File linuxCameraDevice) throws Exception {
		this.cameraDevice = linuxCameraDevice;
		if (!cameraDevice.exists())
			throw new Exception("Cannot access camera device " + cameraDevice.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.camera.VideoManager#recVideo(com.ecosystem.guard.camera.VideoConfig,
	 * int, java.io.File)
	 */
	@Override
	public void record(VideoConfig videoConfig, int secLength, File videoFile) throws Exception {
		// Utilizar Apache Commons Exec

	}

}
