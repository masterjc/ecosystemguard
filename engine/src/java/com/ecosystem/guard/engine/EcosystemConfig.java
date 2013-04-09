package com.ecosystem.guard.engine;

import java.io.File;
import java.io.FileReader;

import com.ecosystem.guard.common.Deserializer;
import com.ecosystem.guard.domain.config.HostConfig;

public class EcosystemConfig {
	private static long hostConfigDate = 0;
	private static HostConfig hostConfig;

	public static HostConfig getHostConfig() throws Exception {
		File configFile = getHostConfigFile();
		if (hasBeenUpdated(configFile)) {
			loadHostConfig(configFile);
		}
		return hostConfig;
	}

	private static File getHostConfigFile() throws Exception {
		String configDirectory = SystemProperties.getConfigDirectory();
		return new File(configDirectory, SystemProperties.HOST_CONFIG_FILENAME);
	}

	private static boolean hasBeenUpdated(File file) {
		return file.lastModified() > hostConfigDate;

	}

	private static void loadHostConfig(File file) throws Exception {
		FileReader input = new FileReader(file);
		try {
			hostConfig = Deserializer.deserialize(HostConfig.class, input);
		} finally {
			input.close();
		}
	}
}
