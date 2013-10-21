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

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.ecosystem.guard.nosql.disk.DiskRepository;
import com.ecosystem.guard.nosql.disk.RepositoryManagerImpl;

/**
 * @version $Revision$
 */
public class RepositoryManagerTest {
	@Test
	public void testGet() throws Exception  {
		String TEMPERATURE_ENTRY = "temperature";
		
		Repository repository = new DiskRepository("Sensors");
		repository.addEntry(TEMPERATURE_ENTRY, Double.class);
		RepositoryManager repoManager = new RepositoryManagerImpl(repository);
		
		DateTime date = new DateTime();
		Double value = new Double("1.0");
		repoManager.insert(TEMPERATURE_ENTRY, date, value);
		Double d = repoManager.get(TEMPERATURE_ENTRY, date);
		Assert.assertEquals(value, d);
	}
}
