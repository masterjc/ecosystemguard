package com.ecosystem.guard.phidgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.ecosystem.guard.logging.EcosystemGuardLogger;
import com.ecosystem.guard.phidgets.exceptions.NotAttachedSensorException;
import com.ecosystem.guard.phidgets.sensors.AquaticTemperature;
import com.ecosystem.guard.phidgets.sensors.Humidity;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;
import com.ecosystem.guard.phidgets.sensors.Ph;
import com.ecosystem.guard.phidgets.sensors.Temperature;

/**
 * Clase responsable de mantener la conexión con los diferentes sensores de Phidgets
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class SensorManager {
	private static final int SENSOR_ATTACH_TIMEOUT_MS = 5000;
	private static final int SENSOR_ATTACH_WAIT_THRESHOLD_MS = 500;
	private static SensorManager instance = new SensorManager();

	private Set<Class<? extends Sensor<?>>> supportedSensorClasses = new HashSet<Class<? extends Sensor<?>>>();
	private HashMap<Class<?>, Sensor<?>> attachedSensors = new HashMap<Class<?>, Sensor<?>>();

	public static SensorManager getInstance() {
		return instance;
	}

	/**
	 * Devuelve la instancia del sensor solicitado si está conectado o se está conectando. Si no se
	 * conectó dicho sensor soportado devolverá NULL. Si el dispositivo estaba conectado y se
	 * desconectó se eliminará de la lista de conectados y se devolverá null (previo reintento de
	 * reconexión)
	 * 
	 * @param sensorClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T extends Sensor<?>> T getSensor(Class<? extends Sensor<?>> sensorClass) throws Exception {
		if (!supportedSensorClasses.contains(sensorClass))
			throw new UnsupportedOperationException("Sensor " + sensorClass.getName() + " is not supported");
		T attachedSensor = (T) attachedSensors.get(sensorClass);
		if (attachedSensor != null) {
			if (attachedSensor.isAttached())
				return attachedSensor;
			attachedSensor.close();
			attachedSensors.remove(sensorClass);
		}
		T sensor = (T) sensorClass.newInstance();
		sensor.open();
		if (!waitAttachment(sensor))
			throw new NotAttachedSensorException(sensorClass.getName());
		attachedSensors.put(sensorClass, sensor);
		return sensor;
	}

	/**
	 * Libera recursos y todas las conexiones con los sensores activos
	 * 
	 * @throws Exception
	 */
	public void release() throws Exception {
		for (Class<?> clazz : attachedSensors.keySet()) {
			attachedSensors.get(clazz).close();
		}
		attachedSensors.clear();
		supportedSensorClasses.clear();
	}

	/**
	 * Constructor: Inicialización del sistema de gestión de sensores
	 */
	private SensorManager() {
		try {
			addSupportedSensors();
		}
		catch (Exception e) {
			EcosystemGuardLogger.logError(e, SensorManager.class);
		}
	}

	/**
	 * Añadir los sensores soportados por el sistema
	 */
	private void addSupportedSensors() {
		supportedSensorClasses.add(AquaticTemperature.class);
		supportedSensorClasses.add(Humidity.class);
		supportedSensorClasses.add(LcdScreen.class);
		supportedSensorClasses.add(Ph.class);
		supportedSensorClasses.add(Temperature.class);
	}

	/**
	 * Espera como máximo SENSOR_ATTACH_TIMEOUT_MS milisegundos a que el sensor se conecte. Devuelve
	 * si se ha podido conectar el sensor
	 * 
	 * @param sensor
	 * @return
	 * @throws Exception
	 */
	private boolean waitAttachment(Sensor<?> sensor) throws Exception {
		int count = SENSOR_ATTACH_TIMEOUT_MS / SENSOR_ATTACH_WAIT_THRESHOLD_MS;
		while (count > 0) {
			if (sensor.isAttached())
				return true;
			Thread.sleep(SENSOR_ATTACH_WAIT_THRESHOLD_MS);
			count--;
		}
		return false;
	}
}
