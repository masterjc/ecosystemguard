package com.ecosystem.guard.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Utilidades para trabajar con streams Java
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class StreamingUtils {

	/**
	 * Consume un InputStream hasta que se cierre. Almacena el contenido en memoria para retornarlo
	 * complemtamente
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] consumeInputStream(InputStream inputStream) throws Exception {
		if (inputStream == null)
			return null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int readBytes = 0;
		while ((readBytes = inputStream.read(buffer)) != -1) {
			output.write(buffer, 0, readBytes);
		}
		return output.toByteArray();
	}

}
