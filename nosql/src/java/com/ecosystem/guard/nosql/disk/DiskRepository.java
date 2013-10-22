/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.nosql.disk;

import java.io.File;
import java.util.HashMap;

import com.ecosystem.guard.nosql.Entry;
import com.ecosystem.guard.nosql.Repository;
import com.ecosystem.guard.nosql.TimeSummary;
import com.ecosystem.guard.nosql.type.DoubleConverter;
import com.ecosystem.guard.nosql.type.EntryTypeConverter;

/**
 * @version $Revision$
 */
public class DiskRepository implements Repository {
	private String name;
	private HashMap<String, Entry> entries;
	private HashMap<Class<?>, EntryTypeConverter> parserTable;

	public DiskRepository(String name, HashMap<Class<?>, EntryTypeConverter> typeParsers) {
		entries = new HashMap<String, Entry>();
		this.parserTable = typeParsers;
		this.name = name;
		createRepositoryDir();
	}

	public DiskRepository(String name) {
		entries = new HashMap<String, Entry>();
		parserTable = new HashMap<Class<?>, EntryTypeConverter>();
		parserTable.put(Double.class, new DoubleConverter());
		this.name = name;
		createRepositoryDir();
	}

	private void createRepositoryDir() {
		File repoDir = new File(name);
		if (!repoDir.exists()) {
			repoDir.mkdir();
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public void addEntry(String entryName, Class<?> entryClass) throws Exception {
		if (!parserTable.containsKey(entryClass))
			throw new Exception("'" + entryClass.getName() + "' parser is not configured for entry '" + name + "'");

		File entryInfo = new File(name + "/" + entryName + ".info");
		TimeSummary timeSummary = null;
		if (entryInfo.exists()) {
			timeSummary = TimeSummaryDAO.read(entryInfo);
		}
		else {
			timeSummary = new TimeSummary();
		}
		Entry entry = new Entry(name, entryClass, parserTable.get(entryClass), timeSummary);
		entries.put(entryName, entry);
	}

	@Override
	public Entry getEntry(String name) {
		return entries.get(name);
	}
}
