package com.ecosystem.guard.camera;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecosystem.guard.camera.impl.FFMpegVideoManager;
import com.ecosystem.guard.camera.impl.HackberryH264VideoConfig;

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
		videoManager.record(new HackberryH264VideoConfig(), 10, File.createTempFile("video", ".mp4"));
	}
	
	@Test
	public void testCommandExecutor() throws Exception {
		FFMpegVideoManager videoManager = new FFMpegVideoManager(new File("c:/Data"));
		videoManager.record(new HackberryH264VideoConfig(), 10, File.createTempFile("video", ".mp4"));
	}
}
