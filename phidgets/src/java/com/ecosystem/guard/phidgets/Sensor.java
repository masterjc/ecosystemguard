package com.ecosystem.guard.phidgets;

import com.phidgets.Phidget;

/**
 * Interfaz general para los sensores de Phidgets
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public abstract class Sensor<T extends Phidget> {
	private T phidget;

	protected Sensor(T phidget) {
		this.phidget = phidget;
	}

	public void open() throws Exception {
		phidget.openAny();
	}

	public boolean isAttached() throws Exception {
		return phidget.isAttached();
	}

	public void close() throws Exception {
		phidget.close();
	}

	public T getSensor() {
		return phidget;
	}
}
