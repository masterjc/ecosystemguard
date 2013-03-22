/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.camera.impl;

import java.io.File;
import java.io.FilenameFilter;

import com.ecosystem.guard.camera.CameraController;
import com.ecosystem.guard.camera.PictureManager;
import com.ecosystem.guard.camera.VideoManager;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegCameraController implements CameraController {
	private static final String DEVICES_DIR = "/dev";
	private File videoDevice;

	public FFMpegCameraController() throws Exception {
		if (!isFFMpegInstalled())
			throw new Exception("FFMpeg is not installed or accesible");
		videoDevice = getVideoDevice();
		if (videoDevice == null)
			throw new Exception("No camera was detected. Please, plug your webcam");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.camera.CameraController#createPictureManager()
	 */
	@Override
	public PictureManager createPictureManager() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.camera.CameraController#createVideoManager()
	 */
	@Override
	public VideoManager createVideoManager() throws Exception {
		return new FFMpegVideoManager(videoDevice);
	}

	private class VideoFilenameFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String name) {
			if (name.startsWith("video"))
				return true;
			return false;
		}

	}

	private boolean isFFMpegInstalled() throws Exception {
		return true;
	}

	/**
	 * Detecta las camaras conectadas al sistema y devuelve la primera
	 * 
	 * @return
	 * @throws Exception
	 */
	private File getVideoDevice() throws Exception {
		File devDir = new File(DEVICES_DIR);
		if (!devDir.exists() || !devDir.isDirectory())
			throw new Exception("Cannot access system devices directory");

		File[] videoDevices = devDir.listFiles(new VideoFilenameFilter());
		if (videoDevices == null)
			return null;
		return videoDevices[0];
	}
}
