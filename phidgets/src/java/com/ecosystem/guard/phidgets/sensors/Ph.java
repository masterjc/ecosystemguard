package com.ecosystem.guard.phidgets.sensors;

import com.ecosystem.guard.phidgets.Sensor;
import com.phidgets.PHSensorPhidget;

/**
 * Controles para la medición del PH de un líquido que está vigilado con un sensor de temperatura
 * acuática.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class Ph extends Sensor<PHSensorPhidget> {
	private AquaticTemperature liquidTemperatureSensor;

	/**
	 * @throws Exception
	 */
	public Ph(AquaticTemperature temperatureSensor) throws Exception {
		super(new PHSensorPhidget());
		this.liquidTemperatureSensor = temperatureSensor;
	}

	/**
	 * Devuelve el PH del liquido donde se encuentra el sensor de PH y el sensor de temperatura
	 * acuática
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getPh() throws Exception {
		if (!liquidTemperatureSensor.isAttached())
			throw new Exception("Aquatic temperature sensor is not attached. Cannot calculate PH");
		getSensor().setTemperature(liquidTemperatureSensor.getTemperature());
		return getSensor().getPH();
	}

}
