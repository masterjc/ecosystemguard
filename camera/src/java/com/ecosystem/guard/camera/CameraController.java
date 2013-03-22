package com.ecosystem.guard.camera;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public interface CameraController {
	public PictureManager createPictureManager() throws Exception;
	public VideoManager createVideoManager() throws Exception;
}
