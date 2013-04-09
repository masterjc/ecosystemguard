/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.host;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;

import com.ecosystem.guard.common.Deserializer;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.Serializer;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.UsernamePassword;
import com.ecosystem.guard.domain.config.HostConfig;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class HostConfigurator {
	private static String HOST_CONFIG_FILENAME = "host.xml";
	private static final int NUM_BITS = 16;

	private HostConfig hostConfig;
	private ManagerConfig managerConfig;

	public HostConfigurator(ManagerConfig config) throws Exception {
		this.managerConfig = config;
		loadHostConfig();
	}

	private boolean loadHostConfig() throws Exception {
		File configFile = new File(managerConfig.getConfigDirectory() + "/" + HOST_CONFIG_FILENAME);
		FileReader reader = null;
		try {
			reader = new FileReader(configFile);
		}
		catch (FileNotFoundException e) {
			return false;
		}
		hostConfig = Deserializer.deserialize(HostConfig.class, reader);
		return true;
	}

	public boolean isInitialized() {
		return hostConfig != null;
	}

	public boolean hasCredentials() {
		return isInitialized() && hostConfig.getCredentials() != null
				&& hostConfig.getCredentials().getUsernamePassword() != null;
	}

	public UsernamePassword getUsernamePassword() {
		if (!hasCredentials())
			return null;
		return hostConfig.getCredentials().getUsernamePassword();
	}

	public void initialize() throws Exception {
		hostConfig = new HostConfig();
		byte[] randomBits = RandomGenerator.generateRandom(NUM_BITS);
		hostConfig.setId("HostId" + String.format("%x", new BigInteger(1, randomBits)));
		save();
	}

	public void save() throws Exception {
		FileWriter writer = new FileWriter(new File(managerConfig.getConfigDirectory() + "/" + HOST_CONFIG_FILENAME));
		Serializer.serialize(hostConfig, HostConfig.class, writer);
		writer.close();
	}

	public void reset() throws Exception {
		hostConfig.setCredentials(null);
		save();
	}

	public void setCredentials(UsernamePassword credentials) throws Exception {
		if (!isInitialized())
			throw new Exception("Cannot set credentials to an uninitialized host configuration");
		Credentials cred = new Credentials();
		cred.setUsernamePassword(credentials);
		hostConfig.setCredentials(cred);
	}
	
	public String getHostId() throws Exception {
		if (!isInitialized())
			throw new Exception("Cannot get host id. Uninitialized host configuration");
		return hostConfig.getId();
	}
}
