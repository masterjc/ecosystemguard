package com.ecosystem.guard.camera.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.Executor;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.VideoManager;
import com.ecosystem.guard.common.CommandLine;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegVideoManager implements VideoManager {
	private static final String FFMPEG_EXEC = "ffmpeg";
	private static final String CAPTURE_DRIVER = "video4linux2";
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
		CommandLine commandLine = new CommandLine(FFMPEG_EXEC);
		commandLine.addArgument("-f", CAPTURE_DRIVER);
		commandLine.addArgument("-i", cameraDevice.getAbsolutePath());
		commandLine.addArgument("-r", Integer.toString(videoConfig.getFps()));
		commandLine.addArgument("-b:v", videoConfig.getBitrate());
		commandLine.addArgument("-s", videoConfig.getResolution().getResolution());
		commandLine.addArgument("-t", Integer.toString(secLength));
		commandLine.addArgument(videoFile.getAbsolutePath());
		Process ffmpegProcess = commandLine.execute();
		
	}
	
	/**
	 * Tiempo que esperará el proceso padre al comando que graba el video. Si es menor a 10
	 * segundos, espera 30 segundos. Si es mayor a 10 segundos y menor a 30 segundos, espera el
	 * doble. Para más de 30 segundos, espera un 20% de tiempo más.
	 * 
	 * @param videoLength
	 * @return
	 */
	private int waitSeconds(int videoLength) {
		if (videoLength < 10)
			return 30;
		if (videoLength < 30)
			return videoLength * 2;
		return videoLength + (videoLength * 20 / 100);
	}

}
