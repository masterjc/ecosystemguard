package com.ecosystem.guard.engine.config;

import java.io.File;
import java.io.FileReader;

import com.ecosystem.guard.common.Deserializer;
import com.ecosystem.guard.domain.config.AppConfig;
import com.ecosystem.guard.domain.config.HostConfig;

/**
 * Acceso a los objetos de configuración del sistema que están serializados en XML.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class EcosystemConfig {
	private static long hostConfigDate = 0;
	private static HostConfig hostConfig;
	private static AppConfig appConfig;
	private static Integer hostLock = 0;
	private static Integer appLock = 0;

	/**
	 * Obtiene el objeto de configuración del host. ATENCIÓN: Cada acceso mira si el fichero ha
	 * cambiado
	 * 
	 * @return
	 * @throws Exception
	 */
	public static HostConfig getHostConfig() throws Exception {
		File configFile = getHostConfigFile();
		if (hasBeenUpdated(configFile)) {
			loadHostConfig(configFile);
		}
		return hostConfig;
	}

	public static AppConfig getAppConfig() throws Exception {
		if (appConfig == null) {
			synchronized (appConfig) {
				if (appConfig == null) {
					File configFile = getAppConfigFile();
					loadAppConfig(configFile);
					appConfig.check();
				}
			}
		}
		return appConfig;
	}

	private static File getHostConfigFile() throws Exception {
		String configDirectory = SystemProperties.getConfigDirectory();
		return new File(configDirectory, SystemProperties.getHostConfigFilename());
	}

	private static File getAppConfigFile() throws Exception {
		String configDirectory = SystemProperties.getConfigDirectory();
		return new File(configDirectory, SystemProperties.getAppConfigFilename());
	}

	private static boolean hasBeenUpdated(File file) {
		return file.lastModified() > hostConfigDate;
	}

	private static void loadHostConfig(File file) throws Exception {
		FileReader input = new FileReader(file);
		try {
			synchronized (hostLock) {
				hostConfig = Deserializer.deserialize(HostConfig.class, input);
			}
		}
		finally {
			input.close();
		}
	}

	private static void loadAppConfig(File file) throws Exception {
		FileReader input = new FileReader(file);
		try {
			synchronized (appLock) {
				appConfig = Deserializer.deserialize(AppConfig.class, input);
			}
		}
		finally {
			input.close();
		}
	}
}
