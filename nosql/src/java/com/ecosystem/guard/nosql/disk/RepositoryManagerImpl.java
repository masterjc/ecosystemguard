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
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.ecosystem.guard.nosql.Entry;
import com.ecosystem.guard.nosql.Repository;
import com.ecosystem.guard.nosql.RepositoryManager;
import com.ecosystem.guard.nosql.time.DateTime;

/**
 * @version $Revision$
 */
public class RepositoryManagerImpl implements RepositoryManager {
	private Repository repository;
	private static Object entryAccessLock = new Object();

	public RepositoryManagerImpl(Repository repository) {
		this.repository = repository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T> void insert(String entry, T value) throws Exception {
		insert(entry, DateTime.getNow(), value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#insert(java.lang.String, java.util.Date,
	 * java.lang.Object)
	 */
	@Override
	public <T> void insert(String entry, DateTime date, T value) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			String dir = repository.getName() + "/" + date.getDate().getYear() + "/" + date.getDate().getMonth() + "/" + date.getDate().getDay();
			createDirectory(dir);
			File entryFile = new File(dir + "/" + entry);
			byte[] serialized = e.getEntryTypeParser().serialize(value);
			FileEntryDAO.write(new FileEntry(date.getTime(), serialized), entryFile);
			updateEntryTimeSummary(e, date);
		}

	}

	private void updateEntryTimeSummary(Entry entry, DateTime date) throws Exception {
		boolean write = false;
		if (entry.getTimeSummary().getFirst() == null) {
			entry.getTimeSummary().setFirst(date);
			write = true;
		}
		if (entry.getTimeSummary().getLast() == null) {
			entry.getTimeSummary().setLast(date);
			write = true;
		}
		else {
			if (date.isGreater(entry.getTimeSummary().getLast())) {
				entry.getTimeSummary().setLast(date);
				write = true;

			}
		}
		if (write) {
			TimeSummaryDAO.write(repository.getName(), entry.getName(), entry.getTimeSummary());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#get(java.lang.String, java.util.Date, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> get(String entry, DateTime date) throws Exception {
		synchronized (entryAccessLock) {
			File entryFile = new File(repository.getName() + "/" + date.getDate().getYear() + "/" + date.getDate().getMonth() + "/"
					+ date.getDate().getDay() + "/" + entry);
			if (!entryFile.exists())
				return null;

			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			List<T> result = new ArrayList<T>();
			FileInputStream input = new FileInputStream(entryFile);
			try {
				FileEntryParser parser = new FileEntryParser(input);
				FileEntry tmpEntry = null;
				boolean inside = false;
				while ((tmpEntry = parser.next()) != null) {
					if (date.equals(tmpEntry.getTime())) {
						inside = true;
						result.add((T) e.getEntryTypeParser().deserialize(tmpEntry.getRawData()));
					}
					else {
						if (inside) {
							break;
						}
					}
				}
			}
			finally {
				input.close();
			}

			return result;
		}
	}

	@Override
	public <T> List<T> get(String entry, DateTime beginDate, DateTime endDate) throws Exception {
		synchronized (entryAccessLock) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#getLast(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getLast(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			List<T> list = get(entry, e.getTimeSummary().getLast());
			if (list.size() > 0)
				return list.get(list.size() - 1);

			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#getFirst(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getFirst(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			List<T> list = get(entry, e.getTimeSummary().getFirst());
			if (list.size() > 0)
				return list.get(0);

			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#getMax(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getMax(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			if (e.getTimeSummary().getMax() == null)
				return null;

			List<T> list = get(entry, e.getTimeSummary().getMax());
			if (list.size() > 0)
				return list.get(0);

			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#getMin(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getMin(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			if (e.getTimeSummary().getMin() == null)
				return null;

			List<T> list = get(entry, e.getTimeSummary().getMin());
			if (list.size() > 0)
				return list.get(0);

			return null;
		}
	}

	private void createDirectory(String dir) {
		File entryDir = new File(dir);
		if (!entryDir.exists()) {
			entryDir.mkdirs();
		}
	}

}
