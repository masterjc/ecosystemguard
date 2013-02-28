package com.ecosystem.guard.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Utilidades para operar con ficheros.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FileUtils {
	private static final String DEFAULT_FILE_CODIFICATION = "UTF-8";

	/**
	 * Lee el contenido de un fichero binario
	 * 
	 * @param file Fichero a leer
	 * @return
	 * @throws Exception
	 */
	public static byte[] readBinaryFile(File file) throws Exception {
		FileInputStream stream = new FileInputStream(file);
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int readBytes = 0;
			while ((readBytes = stream.read(buffer)) != -1) {
				output.write(buffer, 0, readBytes);
			}
			return output.toByteArray();
		}
		finally {
			stream.close();
		}
	}

	/**
	 * Lee el contenido de un fichero de texto codificado en UTF/8
	 * 
	 * @param file Fichero a leer
	 * @return Contenido integro del fichero en codificacion UTF/8
	 * @throws Exception
	 */
	public static String readTextFile(File file) throws Exception {
		return readTextFile(file, DEFAULT_FILE_CODIFICATION);
	}

	/**
	 * Lee el contenido de un fichero de texto dada una codificacion
	 * 
	 * @param file Fichero a leer
	 * @param codification Codificacion del fichero (UTF-8,...)
	 * @return
	 * @throws Exception
	 */
	public static String readTextFile(File file, String codification) throws Exception {
		Reader reader = new InputStreamReader(new FileInputStream(file), codification);

		try {
			StringBuffer output = new StringBuffer();
			char[] buffer = new char[1024];
			int readChars = 0;
			while ((readChars = reader.read(buffer)) != -1) {
				output.append(buffer, 0, readChars);
			}
			return output.toString();
		}
		finally {
			reader.close();
		}
	}

	/**
	 * Escribe datos binarios en un fichero. Elimina el contenido original del fichero
	 * 
	 * @param file Fichero donde escribir
	 * @param data Datos a escribir
	 * @throws Exception
	 */
	public static void writeBinaryFile(File file, byte[] data) throws Exception {
		writeBinaryFile(file, data, false);
	}

	/**
	 * Escribe datos binarios en un fichero. Permite añadir o sobreescribir el fichero
	 * 
	 * @param file Fichero donde escribir
	 * @param data Datos a escribir
	 * @param append añadir datos al final del fichero
	 * @throws Exception
	 */
	public static void writeBinaryFile(File file, byte[] data, boolean append) throws Exception {
		FileOutputStream output = new FileOutputStream(file, append);
		output.write(data);
		output.close();
	}

}
