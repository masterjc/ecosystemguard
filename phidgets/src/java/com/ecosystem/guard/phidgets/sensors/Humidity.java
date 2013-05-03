package com.ecosystem.guard.phidgets.sensors;

import com.ecosystem.guard.phidgets.Sensor;
import com.phidgets.InterfaceKitPhidget;

/**
 * Control del sensor de humedad relativa del chip con temperatura y humedad.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class Humidity extends Sensor<InterfaceKitPhidget> {
	private final static int HUMIDITY_SENSOR = 1;

	/**
	 * @throws Exception
	 */
	public Humidity() throws Exception {
		super(new InterfaceKitPhidget());

	}

	/**
	 * Devuelve la humedad relativa (%) del ambiente
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getRelativeHumidity() throws Exception {
		return getRelativeHumidity(getSensor().getSensorValue(HUMIDITY_SENSOR));
	}

	/**
	 * Formula para calcular la humedad relativa a partir del voltaje de la entrada analógica
	 * 
	 * @param sensorValue
	 * @return
	 */
	private double getRelativeHumidity(int sensorValue) {
		return (sensorValue * 0.1906) - 40.2;
	}

}
