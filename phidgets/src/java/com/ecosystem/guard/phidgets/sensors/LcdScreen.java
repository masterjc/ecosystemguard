package com.ecosystem.guard.phidgets.sensors;

import com.ecosystem.guard.logging.EcosystemGuardLogger;
import com.ecosystem.guard.phidgets.Sensor;
import com.phidgets.TextLCDPhidget;

/**
 * Controles estándares para la pantalla LCD.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class LcdScreen extends Sensor<TextLCDPhidget> {
	private static final int MAXIMUM_LINE_CHARS = 20;
	private static final int FIRST_LINE = 0;
	private static final int SECOND_LINE = 1;
	private static final String EMPTY_LINE = new String();
	private Thread asyncThread = null;
	

	public enum Contrast {
		LOW(20), MEDIUM(128), HIGH(255);

		private int contrast;

		private Contrast(int contrast) {
			this.contrast = contrast;
		}

		public int getContrast() {
			return contrast;
		}
	}

	public enum Brightness {
		LOW(20), MEDIUM(128), HIGH(255);

		private int brightness;

		private Brightness(int brightness) {
			this.brightness = brightness;
		}

		public int getBrightness() {
			return brightness;
		}
	}

	private Contrast contrast;
	private Brightness brightness;

	/**
	 * @throws Exception
	 */
	public LcdScreen() throws Exception {
		this(Contrast.MEDIUM, Brightness.MEDIUM);
	}

	/**
	 * @throws Exception
	 */
	public LcdScreen(Contrast contrast, Brightness brightness) throws Exception {
		super(new TextLCDPhidget());
		this.contrast = contrast;
		this.brightness = brightness;
	}

	/**
	 * Imprime un mensaje de texto en la primera línea de la LCD de máximo 20
	 * caracteres. Imprime un mensaje de texto en la segunda línea de la LCD de
	 * máximo 20 caracteres. El mensaje permanecerá N segundos en la LCD con
	 * iluminación, después desaparecerá el mensaje y la iluminación.
	 * 
	 * @param line1
	 * @param line2
	 * @param seconds
	 * @throws Exception
	 */
	public void showMessage(String line1, String line2, int timeMs) throws Exception {
		if (line1 == null && line2 == null)
			throw new Exception("Message to show cannot be null");
		if (line1 != null && line1.length() > MAXIMUM_LINE_CHARS)
			throw new Exception("Max characters length of Line #1 exceeded");
		if (line2 != null && line2.length() > MAXIMUM_LINE_CHARS)
			throw new Exception("Max characters length of Line #2 exceeded");
		getSensor().setBrightness(brightness.getBrightness());
		getSensor().setContrast(contrast.getContrast());
		getSensor().setBacklight(true);
		if (line1 != null) {
			getSensor().setDisplayString(FIRST_LINE, line1);
		}
		if (line2 != null) {
			getSensor().setDisplayString(SECOND_LINE, line2);
		}
		try {
			Thread.sleep(timeMs);	
		} catch(InterruptedException e) {
			return;
		}
		getSensor().setBacklight(false);
		getSensor().setDisplayString(FIRST_LINE, EMPTY_LINE);
		getSensor().setDisplayString(SECOND_LINE, EMPTY_LINE);
	}

	/**
	 * Imprime un mensaje de texto en la primera línea de la LCD de máximo 20
	 * caracteres. La segunda linea estará vacía. El mensaje permanecerá N
	 * segundos en la LCD con iluminación, después desaparecerá el mensaje y
	 * la iluminación.
	 * 
	 * @param line1
	 * @param seconds
	 * @throws Exception
	 */
	public void showMessage(String line1, int timeMs) throws Exception {
		showMessage(line1, null, timeMs);
	}

	/**
	 * Imprime un mensaje de texto en la primera línea de la LCD de máximo 20
	 * caracteres. La segunda linea estará vacía. La retroiluminación
	 * parpadeará: 1 segundo encendida, 0,5 segundos apagada. Al cabo de N
	 * segundos desaparecerá el mensaje y la iluminación.
	 * 
	 * @param line1
	 * @param seconds
	 * @throws Exception
	 */
	public void showIntermitentMessage(String line1, int timeMs) throws Exception {
		showIntermitentMessage(line1, null, timeMs);
	}

	/**
	 * Imprime un mensaje de texto en la primera línea de la LCD de máximo 20
	 * caracteres. Imprime un mensaje de texto en la segunda línea de la LCD de
	 * máximo 20 caracteres. El mensaje parpadeará: 1 segundo muestra el
	 * mensaje, 0,5 segundos sin mensaje. Al cabo de N repeticiones
	 * desaparecerá el mensaje y la iluminación.
	 * 
	 * @param line1
	 * @param seconds
	 * @throws Exception
	 */
	public void showIntermitentMessage(String line1, String line2, int intermitentRepetitions) throws Exception {
		for (int i = 0; i < intermitentRepetitions; i++) {
			if (i != 0) {
				try {
					Thread.sleep(500);	
				} catch(InterruptedException e) {
					return;
				}
			}
			showMessage(line1, line2, 1000);
		}
	}

	/**
	 * @param line1
	 * @param line2
	 * @param timeMs
	 * @throws Exception
	 */
	public void showAsynchronousMessage(String line1, String line2, int timeMs) throws Exception {
		final String firstLine = line1;
		final String secondLine = line2;
		final int threadTime = timeMs;
		asyncThread = new Thread(new Runnable() {
			public void run() {
				try {
					showMessage(firstLine, secondLine, threadTime);
				} catch (Exception e) {
					EcosystemGuardLogger.logError(e, LcdScreen.class);
				}
			}
		});
		asyncThread.start();
	}

	/**
	 * 
	 * 
	 * @param line1
	 * @param seconds
	 * @throws Exception
	 */
	public void showAsynchronousIntermitentMessage(String line1, String line2, int intermitentRepetitions)
			throws Exception {
		final String firstLine = line1;
		final String secondLine = line2;
		final int repetitions = intermitentRepetitions;
		asyncThread = new Thread(new Runnable() {
			public void run() {
				try {
					showIntermitentMessage(firstLine, secondLine, repetitions);
				} catch (Exception e) {
					EcosystemGuardLogger.logError(e, LcdScreen.class);
				}
			}
		});
		asyncThread.start();
	}
	
	@Override
	public void close() throws Exception {
		if(asyncThread != null && asyncThread.isAlive()) {
			asyncThread.interrupt();
			asyncThread.join();
		}
		getSensor().setBacklight(false);
		getSensor().setDisplayString(0, "");
		getSensor().setDisplayString(1, "");
		super.close();
	}

}
