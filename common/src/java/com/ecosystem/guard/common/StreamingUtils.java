package com.ecosystem.guard.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilidades para trabajar con streams Java
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class StreamingUtils {

	/**
	 * Consume un InputStream hasta que se cierre. Almacena el contenido en
	 * memoria para retornarlo complemtamente
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

	/**
	 * @param inputFile
	 * @param output
	 * @throws Exception
	 */
	public static void consumeFileStream(File inputFile, OutputStream output) throws Exception {
		byte[] buffer = new byte[50 * 1024];
		int readBytes = 0;
		FileInputStream input = new FileInputStream(inputFile);
		try {
			while ((readBytes = input.read(buffer)) != -1) {
				output.write(buffer, 0, readBytes);
			}
		} finally {
			input.close();
		}
	}

	/**
	 * Consume un InputStream y va escribiendo su contenido en el OutputStream
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static void consumeStream(InputStream inputStream, OutputStream outputStream) throws Exception {
		byte[] buffer = new byte[10 * 1024];
		int readBytes = 0;
		while ((readBytes = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readBytes);
		}
	}

}
