package com.ecosystem.guard.phidgets.sensors;

import com.ecosystem.guard.phidgets.Sensor;
import com.phidgets.TemperatureSensorPhidget;

/**
 * Control del sensor de temperatura acuática formada por el controlador de temperatura y el
 * Thermocouple de tipo K.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AquaticTemperature extends Sensor<TemperatureSensorPhidget> {
	private static final int DEFAULT_TEMP_SENSOR_SLOT = 0;

	/**
	 * @throws Exception
	 */
	public AquaticTemperature() throws Exception {
		super(new TemperatureSensorPhidget());

	}

	/**
	 * Devuelve la temperatura del agua en grados centígrados
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getTemperature() throws Exception {
		return getSensor().getTemperature(DEFAULT_TEMP_SENSOR_SLOT);
	}

}
