package com.ecosystem.guard.camera;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecosystem.guard.camera.impl.DefaultVideoConfig;
import com.ecosystem.guard.camera.impl.FFMpegCameraController;
import com.ecosystem.guard.camera.impl.FFMpegVideoManager;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegCameraControllerTest {

	private static CameraController cameraController;

	@BeforeClass
	public static void initCameraController() throws Exception {
		//cameraController = new FFMpegCameraController();
	}

	//@Test
	public void testVideoOk() throws Exception {
		VideoManager videoManager = cameraController.createVideoManager();
		videoManager.record(new DefaultVideoConfig(), 10, File.createTempFile("video", ".mp4"));
	}
	
	@Test
	public void testCommandExecutor() throws Exception {
		FFMpegVideoManager videoManager = new FFMpegVideoManager(new File("c:/Data"));
		videoManager.record(new DefaultVideoConfig(), 10, File.createTempFile("video", ".mp4"));
	}
}
