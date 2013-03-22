package com.ecosystem.guard.camera.impl;

import java.io.File;

import com.ecosystem.guard.camera.PictureConfig;
import com.ecosystem.guard.camera.PictureManager;
import com.ecosystem.guard.common.FileUtils;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegPictureManager implements PictureManager {
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
