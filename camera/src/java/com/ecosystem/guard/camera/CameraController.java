package com.ecosystem.guard.camera;

/**
 * Controlador de la cámara para tomar videos y fotos
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public interface CameraController {
	/**
	 * Crear un disparador de fotos
	 * 
	 * @return
	 * @throws Exception
	 */
	public PictureManager createPictureManager() throws Exception;

	/**
	 * Crear un dispositivo de captura de videos
	 * 
	 * @return
	 * @throws Exception
	 */
	public VideoManager createVideoManager() throws Exception;
}
