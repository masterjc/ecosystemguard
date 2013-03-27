package com.ecosystem.guard.camera.impl;

import java.io.File;

import com.ecosystem.guard.camera.PictureCodec;
import com.ecosystem.guard.camera.PictureConfig;
import com.ecosystem.guard.camera.PictureManager;
import com.ecosystem.guard.common.CommandLine;
import com.ecosystem.guard.common.FileUtils;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegPictureManager implements PictureManager {
	private static final int TAKE_PHOTO_TIMEOUT = 20;
	private File cameraDevice;

	public FFMpegPictureManager(File linuxCameraDevice) throws Exception {
		this.cameraDevice = linuxCameraDevice;
		if (!cameraDevice.exists())
			throw new Exception("Cannot access camera device " + cameraDevice.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.camera.PictureManager#takePicture(com.ecosystem.guard.camera.PictureConfig
	 * , java.io.File)
	 */
	@Override
	public void takePicture(PictureConfig pictureConfig, File pictureFile) throws Exception {
		if (pictureConfig.getCodec() != PictureCodec.JPEG)
			throw new Exception("Only jpeg webcam snapshot is supported");
		CommandLine commandLine = new CommandLine(FFMpegTraits.FFMPEG_EXEC);
		commandLine.addArgument("-f", FFMpegTraits.CAPTURE_DRIVER);
		commandLine.addArgument("-i", cameraDevice.getAbsolutePath());
		commandLine.addArgument("-s", pictureConfig.getResolution().getResolution());
		Process ffmpegProcess = commandLine.execute();
		ffmpegProcess.wait(TAKE_PHOTO_TIMEOUT);
		FFMpegTraits.checkAndThrowFFMpegError(ffmpegProcess.getInputStream(), ffmpegProcess.getErrorStream());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.camera.PictureManager#takePicture(com.ecosystem.guard.camera.PictureConfig
	 * )
	 */
	@Override
	public byte[] takePicture(PictureConfig pictureConfig) throws Exception {
		File picture = File.createTempFile("pic", "");
		takePicture(pictureConfig, picture);
		return FileUtils.readBinaryFile(picture);
	}
}
