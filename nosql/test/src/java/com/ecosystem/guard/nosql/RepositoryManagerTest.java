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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ecosystem.guard.nosql.disk.DiskRepository;
import com.ecosystem.guard.nosql.disk.RepositoryManagerImpl;
import com.ecosystem.guard.nosql.time.Date;
import com.ecosystem.guard.nosql.time.DateTime;
import com.ecosystem.guard.nosql.time.Time;

/**
 * @version $Revision$
 */
public class RepositoryManagerTest {
	@Test
	public void testSingleDateGet() throws Exception {
		String TEMPERATURE_ENTRY = "temperature";

		Repository repository = new DiskRepository("testSingleDateGet");
		repository.addEntry(TEMPERATURE_ENTRY, Double.class);
		RepositoryManager repoManager = new RepositoryManagerImpl(repository);

		DateTime date = DateTime.getNow();
		Double value = new Double("1.0");
		repoManager.insert(TEMPERATURE_ENTRY, date, value);
		List<Double> d = repoManager.get(TEMPERATURE_ENTRY, date);
		Assert.assertEquals(1, d.size());
		Assert.assertEquals(value, d.get(0));
	}

	@Test
	public void testLastDateGet() throws Exception {
		String TEMPERATURE_ENTRY = "temperature";

		Repository repository = new DiskRepository("testLastDateGet");
		repository.addEntry(TEMPERATURE_ENTRY, Double.class);
		RepositoryManager repoManager = new RepositoryManagerImpl(repository);

		DateTime date1 = new DateTime(new Date(2013, 10, 22), new Time(11, 12, 13));
		DateTime date2 = new DateTime(new Date(2013, 10, 22), new Time(12, 12, 13));
		Double value1 = new Double("1.0");
		Double value2 = new Double("2.0");
		repoManager.insert(TEMPERATURE_ENTRY, date1, value1);
		repoManager.insert(TEMPERATURE_ENTRY, date2, value2);
		Double d = repoManager.getLast(TEMPERATURE_ENTRY);
		Assert.assertEquals(value2, d);
	}

	@Test
	public void testFirstDateGet() throws Exception {
		String TEMPERATURE_ENTRY = "temperature";

		Repository repository = new DiskRepository("testFirstDateGet");
		repository.addEntry(TEMPERATURE_ENTRY, Double.class);
		RepositoryManager repoManager = new RepositoryManagerImpl(repository);

		DateTime date1 = new DateTime(new Date(2013, 10, 22), new Time(11, 12, 13));
		DateTime date2 = new DateTime(new Date(2013, 10, 22), new Time(12, 12, 13));
		Double value1 = new Double("1.0");
		Double value2 = new Double("2.0");
		repoManager.insert(TEMPERATURE_ENTRY, date1, value1);
		repoManager.insert(TEMPERATURE_ENTRY, date2, value2);
		Double d = repoManager.getFirst(TEMPERATURE_ENTRY);
		Assert.assertEquals(value1, d);
	}

	@Test
	public void testLastDateGet2() throws Exception {
		String TEMPERATURE_ENTRY = "temperature";

		Repository repository = new DiskRepository("testFirstDateGet2");
		repository.addEntry(TEMPERATURE_ENTRY, Double.class);
		RepositoryManager repoManager = new RepositoryManagerImpl(repository);

		DateTime date1 = new DateTime(new Date(2013, 10, 22), new Time(11, 12, 13));
		DateTime date2 = new DateTime(new Date(2013, 10, 22), new Time(12, 12, 13));
		DateTime date3 = new DateTime(new Date(2011, 10, 22), new Time(12, 12, 13));
		Double value1 = new Double("1.0");
		Double value2 = new Double("2.0");
		Double value3 = new Double("3.0");
		repoManager.insert(TEMPERATURE_ENTRY, date1, value1);
		repoManager.insert(TEMPERATURE_ENTRY, date2, value2);
		repoManager.insert(TEMPERATURE_ENTRY, date3, value3);
		Double d = repoManager.getLast(TEMPERATURE_ENTRY);
		Assert.assertEquals(value2, d);
	}
}
