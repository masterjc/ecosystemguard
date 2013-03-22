package com.ecosystem.guard.camera;

import java.io.File;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public interface PictureManager {
	/**
	 * Toma una fotografía del dispositivo de captura y almacena la imagen por streaming en el
	 * fichero indicado.
	 * 
	 * @param pictureConfig
	 * @param pictureFile
	 * @throws Exception
	 */
	public void takePicture(PictureConfig pictureConfig, File pictureFile) throws Exception;

	/**
	 * Toma una fotografía del dispositivo de captura, guarda la imagen en memoria y la retorna.
	 * 
	 * @param pictureConfig
	 * @return
	 * @throws Exception
	 */
	public byte[] takePicture(PictureConfig pictureConfig) throws Exception;
}
