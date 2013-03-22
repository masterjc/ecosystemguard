package com.ecosystem.guard.camera;

import java.io.File;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public interface VideoManager {

	/**
	 * Graba un video con unas características (resolución, fps, bitrate...) durante un periodo
	 * concreto de tiempo. El resultado lo escribe por streaming en el fichero especificado. Este
	 * método ha de ser síncrono, es decir, solo acabará la llamada cuando se haya grabado el video
	 * completo.
	 * 
	 * @param videoConfig configuración del video
	 * @param secLength Longitud en segundos del video a grabar
	 * @param videoFile Fichero donde almacenar el video
	 * @throws Exception
	 */
	public void record(VideoConfig videoConfig, int secLength, File videoFile) throws Exception;
}
