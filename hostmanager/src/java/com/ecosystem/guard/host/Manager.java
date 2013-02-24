package com.ecosystem.guard.host;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.config.HostConfig;

public class Manager {
	private static String HOST_CONFIG_FILENAME = "host.xml";
	private static final int RADIX = 64;
	private static final int NUM_BITS = 64;

	private class Config {
		private String configDirectory;

		public Config(String[] args) throws Exception {
			if (args.length == 0)
				configDirectory = new String();
			configDirectory = args[0];
		}

		public String getConfigDirectory() {
			return configDirectory;
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println("EcosystemGuard Configuration Manager");
		System.out.println("------------------------------------");
		Manager manager = new Manager(args);
		manager.execute();
	}

	private Config managerConfig;
	private HostConfig hostConfig;

	public Manager(String[] args) throws Exception {
		managerConfig = new Config(args);
		boolean configExists = loadHostConfig();
		if (!configExists) {
			createInitialHostConfig();
		}
	}

	public void execute() {
		boolean exit = false;
		
		while (!exit) {
			try {
				exit = processOptions();
			} catch (Exception e) {

			}
		}
	}

	private boolean processOptions() throws Exception {

		return true;
	}

	private String createRandom() {
		SecureRandom secureRandom = new SecureRandom();
		return (new BigInteger(NUM_BITS, secureRandom)).toString(RADIX);
	}

	private boolean loadHostConfig() throws Exception {
		File configFile = new File(managerConfig.getConfigDirectory() + "/" + HOST_CONFIG_FILENAME);
		FileReader reader = null;
		try {
			reader = new FileReader(configFile);
		} catch (FileNotFoundException e) {
			return false;
		}
		hostConfig = Deserializer.deserialize(HostConfig.class, reader);
		return true;
	}

	private void createInitialHostConfig() {
		System.out.println("Initializing EcosystemGuard host...");
		hostConfig = new HostConfig();
		hostConfig.setId("HostId" + createRandom());
		System.out.println("Host id generated: " + hostConfig.getId());
		Scanner scanner = new Scanner(System.in);
		System.out.print("Type your EcosystemGuard host purposes? ");
		hostConfig.setDescription(scanner.next());
		

	}
}
