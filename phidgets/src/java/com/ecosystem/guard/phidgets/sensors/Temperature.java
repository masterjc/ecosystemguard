package com.ecosystem.guard.phidgets.sensors;

import com.ecosystem.guard.phidgets.Sensor;
import com.phidgets.InterfaceKitPhidget;

/**
 * Control del sensor de temperatura ambiente del chip con temperatura y humedad.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class Temperature extends Sensor<InterfaceKitPhidget> {
	private final static int TEMPERATURE_SENSOR = 0;

	/**
	 * @throws Exception
	 */
	public Temperature() throws Exception {
		super(new InterfaceKitPhidget());

	}

	/**
	 * Devuelve la temperatura del ambiente en grados centígrados
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getTemperature() throws Exception {
		return getTemperature(getSensor().getSensorValue(TEMPERATURE_SENSOR));
	}

	/**
	 * Formula para calcular la temperatura en grados a partir del voltaje de la entrada analógica
	 * 
	 * @param sensorValue
	 * @return
	 */
	private double getTemperature(int sensorValue) {
		return (sensorValue * 0.22222) - 61.11;
	}

}
