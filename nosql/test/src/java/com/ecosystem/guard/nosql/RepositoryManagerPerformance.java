/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.nosql;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecosystem.guard.nosql.disk.DiskRepository;
import com.ecosystem.guard.nosql.disk.RepositoryManagerImpl;
import com.ecosystem.guard.nosql.time.DateTime;

/**
 * @version $Revision$
 */
public class RepositoryManagerPerformance {
	private static RepositoryManager repoManager;
	private static final String TEMPERATURE_ENTRY = "temperature";

	@BeforeClass
	public static void setup() throws Exception {
		Repository repository = new DiskRepository("Sensors");
		repository.addEntry(TEMPERATURE_ENTRY, Double.class);
		repoManager = new RepositoryManagerImpl(repository);
	}

	@Test
	public void performanceTest() throws Exception {
		int iterations = 86400;
		Double value = new Double("32.56");
		DateTime[] dates = new DateTime[iterations];
		long start = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			dates[i] = DateTime.getNow();
			repoManager.insert(TEMPERATURE_ENTRY, dates[i], value);
		}
		System.out.println("Write " + iterations + ": " + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			repoManager.get(TEMPERATURE_ENTRY, dates[i]);
		}
		System.out.println("Read " + iterations + ": " + (System.currentTimeMillis() - start));
	}
}
