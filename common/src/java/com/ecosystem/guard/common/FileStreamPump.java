package com.ecosystem.guard.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Lee los datos del fichero y los escribe en el stream de salida. La particularidad de esta clase
 * es que es capaz de escribir el contenido del fichero de entrada mientras este está siendo
 * escrito. Por defecto, espera 5 segundos como mucho a que se genere el archivo que se ha de
 * bombear, si no hay datos disponibles dormirá durante 50ms antes de volver a consultar si hay
 * datos disponibles en el fichero y el buffer interno es de 50K. El control de tiempo de bombeo es
 * externo a este objeto.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FileStreamPump {

	private int availableThresholdMs;
	private int creationTimeoutMs;
	private int bufferSize;
	private boolean stop;
	private PumpThread pumpThread = null;

	/**
	 * Constructor indicando el fichero de origen y el stream de salida donde bombear los datos
	 * 
	 * @param inputFile
	 * @param outputStream
	 */
	public FileStreamPump() {
		availableThresholdMs = 50;
		creationTimeoutMs = 5000;
		bufferSize = 50 * 1024;
		stop = false;
	}

	/**
	 * Milisegundos que esperará el proceso de bombeo a que se cree el archivo observado
	 * 
	 * @param timeoutMs
	 */
	public void setCreationFileTimeout(int timeoutMs) {
		this.creationTimeoutMs = timeoutMs;
	}

	/**
	 * Milisegundos que dormirá el proceso de bombeo cuando no hay datos disponibles para leer. Al
	 * finalizar este tiempo volverá a consultar si hay datos disponibles.
	 * 
	 * @param timeoutMs
	 */
	public void setAvailableThreshold(int thresholdMs) {
		this.availableThresholdMs = thresholdMs;
	}

	/**
	 * Tamaño en bytes del buffer interno intermedio para el paso de datos por memoria durante el
	 * bombeo
	 * 
	 * @param size bytes
	 */
	public void setBufferSize(int size) {
		this.bufferSize = size;
	}

	/**
	 * Método asíncrono que bombea todos los datos del fichero disponibles al stream de escritura.
	 * Si no se dispone de más datos el proceso duerme durante un tiempo determinado antes de volver
	 * a consultar si hay datos de nuevo. Este método es asíncrono pero lanza un thread que realiza
	 * el trabajo, por lo tanto solo puede terminarse la ejecución del proceso llamando al método
	 * stopPumping(). Así pues, el proceso que controla la escritura del fichero deberá ser el que
	 * le indique a este objeto.
	 * 
	 * @throws Exception
	 */
	public void startPumping(File inputFile, OutputStream outputStream) throws Exception {
		stop = false;
		pumpThread = new PumpThread(inputFile, outputStream);
		pumpThread.start();

	}

	/**
	 * Detiene el proceso asíncrono de bombeo comenzado mediante startPumping. Si hubo alguna
	 * excepción durante el bombeo se lanzará en este punto
	 * 
	 * @throws Exception
	 */
	public void stopPumping() throws Exception {
		stop = true;
		pumpThread.join();
		if (pumpThread.getPumpException() != null)
			throw pumpThread.getPumpException();
	}

	/**
	 * Indica si el proceso de bombeo está parado.
	 * 
	 * @return
	 */
	public boolean isStopped() {
		return stop;
	}

	/**
	 * Espera a que el fichero de lectura exista (si no existe ya). Si no existe, el thread duerme
	 * durante 50ms.
	 * 
	 * @return
	 * @throws Exception
	 */
	private FileInputStream createFileInputStream(File inputFile) throws Exception {
		int sleepMs = 50;
		int iterations = creationTimeoutMs / sleepMs;
		int nIt = 0;
		do {
			if (inputFile.exists()) {
				return new FileInputStream(inputFile);
			}
			else {
				Thread.sleep(sleepMs);
			}
		} while (nIt++ < iterations);
		throw new Exception("Exceeded file creation timeout. Error opening file");
	}

	/**
	 * Clase que realiza el bombeo de datos
	 */
	private class PumpThread extends Thread {
		private File inputFile;
		private OutputStream outputStream;
		private Exception exception;

		public PumpThread(File inputFile, OutputStream outputStream) {
			this.inputFile = inputFile;
			this.outputStream = outputStream;
		}

		public void run() {
			try {
				FileInputStream reader = createFileInputStream(inputFile);
				try {
					byte[] buffer = new byte[bufferSize];
					while (!stop) {
						int available = reader.available();
						if (available < 0)
							break;
						if (available > bufferSize)
							available = bufferSize;
						if (available != 0) {
							int readBytes = reader.read(buffer, 0, available);
							outputStream.write(buffer, 0, readBytes);
						}
						else {
							Thread.sleep(availableThresholdMs);
						}
					}
				}
				finally {
					reader.close();
				}
			}
			catch (Exception e) {
				exception = e;
			}
		}

		public Exception getPumpException() {
			return exception;
		}
	}

}
