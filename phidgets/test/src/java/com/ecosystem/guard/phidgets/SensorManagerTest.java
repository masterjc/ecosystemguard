package com.ecosystem.guard.phidgets;

import junit.framework.Assert;

import org.junit.Test;

import com.ecosystem.guard.phidgets.exceptions.NotAttachedSensorException;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class SensorManagerTest {

	@Test(expected=NotAttachedSensorException.class)
	public void sensorsTest() throws Exception {
		LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
		Assert.assertNull(lcd);
	}

}
