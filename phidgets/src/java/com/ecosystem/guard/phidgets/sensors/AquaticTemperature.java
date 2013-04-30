package com.ecosystem.guard.phidgets.sensors;

import com.ecosystem.guard.phidgets.Sensor;
import com.phidgets.TextLCDPhidget;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AquaticTemperature extends Sensor<TextLCDPhidget> {

	/**
	 * @throws Exception
	 */
	public AquaticTemperature() throws Exception {
		super(new TextLCDPhidget());
	}

	
}
