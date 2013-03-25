package com.ecosystem.guard.camera.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.VideoManager;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegVideoManager implements VideoManager {
	//private static final String FFMPEG_EXEC = "ffmpeg";
	private static final String FFMPEG_EXEC = "dir";
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
		/*commandLine.addArgument("-f " + CAPTURE_DRIVER);
		commandLine.addArgument("-i " + cameraDevice.getName());
		commandLine.addArgument("-r " + videoConfig.getFps());
		commandLine.addArgument("-b:v " + videoConfig.getBitrate());
		commandLine.addArgument("-s " + videoConfig.getResolution().getResolution());
		commandLine.addArgument("-t " + secLength);
		commandLine.addArgument(videoFile.getName());*/
		Executor executor = new DefaultExecutor();
		ExecuteWatchdog watchDog = new ExecuteWatchdog(waitSeconds(secLength));
		executor.setWatchdog(watchDog);
		executor.setExitValue(1);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
		executor.setStreamHandler(streamHandler);
		executor.execute(commandLine);
		System.out.println(errorStream.toString());
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
