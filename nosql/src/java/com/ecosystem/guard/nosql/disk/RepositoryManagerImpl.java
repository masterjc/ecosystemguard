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
import com.ecosystem.guard.nosql.TimedEntry;
import com.ecosystem.guard.nosql.time.Date;
import com.ecosystem.guard.nosql.time.DateTime;
import com.ecosystem.guard.nosql.time.Time;

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
	public <T> List<TimedEntry<T>> get(String entry, DateTime date) throws Exception {
		synchronized (entryAccessLock) {
			File entryFile = new File(repository.getName() + "/" + date.getDate().getYear() + "/" + date.getDate().getMonth() + "/"
					+ date.getDate().getDay() + "/" + entry);
			if (!entryFile.exists())
				return null;

			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			List<TimedEntry<T>> result = new ArrayList<TimedEntry<T>>();
			FileInputStream input = new FileInputStream(entryFile);
			try {
				FileEntryParser parser = new FileEntryParser(input);
				FileEntry tmpEntry = null;
				boolean inside = false;
				while ((tmpEntry = parser.next()) != null) {
					if (date.equals(tmpEntry.getTime())) {
						inside = true;
						result.add(new TimedEntry<T>((T) e.getEntryTypeParser().deserialize(tmpEntry.getRawData()), date ));
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
	public <T> List<TimedEntry<T>> get(String entry, DateTime beginDate, DateTime endDate) throws Exception {
		synchronized (entryAccessLock) {
			File repoDir = new File( repository.getName() );
			Entry entryInfo = repository.getEntry(entry);
			List<TimedEntry<T>> result = new ArrayList<TimedEntry<T>>();
			
			File[] yearFiles = repoDir.listFiles();
			for( int iYear = 0; iYear < yearFiles.length; iYear++ ) {
				// Not a directory. Do not process
				File yearFile = yearFiles[iYear];
				if( !yearFile.isDirectory() )
					continue;

				Integer yearInt = Integer.parseInt(yearFile.getName());
				int year = yearInt.intValue();
				
				// Not inside year interval. Do not process
				if( year < beginDate.getDate().getYear() && year > endDate.getDate().getYear() )
					continue;
				
				// Process year. Some files will be inside query result
				File[] monthFiles = yearFile.listFiles();
				for( int iMonth = 0; iMonth < monthFiles.length; iMonth++ ) {
					// Not a directory. Do not process
					File monthFile = monthFiles[iMonth];
					if( !monthFile.isDirectory() )
						continue;
					
					File[] dayFiles = monthFile.listFiles();
					for( int iDay = 0; iDay < dayFiles.length; iDay++ ) {
						// Not a directory. Do not process
						File dayFile = dayFiles[iDay];
						if( !dayFile.isDirectory() )
							continue;
						
						int month = Integer.parseInt(monthFile.getName());
						int day = Integer.parseInt(dayFile.getName());
						Date fileDate = new Date(year, month, day);
						
						if( !fileDate.isInsideInterval(beginDate.getDate(), endDate.getDate()) )
							continue;
						
						File entryFile = new File( repository.getName() + "/" + year + "/" + month + "/" + day + "/" + entry);
						if( !entryFile.exists() )
							return new ArrayList<TimedEntry<T>>();
						
						if( fileDate.equals(beginDate.getDate()) ) {
							if( fileDate.equals(endDate.getDate()) ) {
								addEntries( entryInfo, fileDate, entryFile, beginDate.getTime(), endDate.getTime(), result );
							} else {
								addEntries( entryInfo, fileDate, entryFile, beginDate.getTime(), null, result );
							}
						} else
						if( fileDate.equals(endDate.getDate()) ) {
							addEntries( entryInfo, fileDate, entryFile, null, endDate.getTime(), result );
						} else {
							addEntries( entryInfo, fileDate, entryFile, null, null, result );
						}
					}
				}
			}
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> void addEntries(Entry entryInfo, Date date, File file, Time begin, Time end, List<TimedEntry<T>> entries) throws Exception {
		FileEntryParser entryParser = new FileEntryParser(new FileInputStream(file));
		FileEntry entry = null;
		while( (entry = entryParser.next()) != null ) {
			if( end != null && entry.getTime().isGreater(end))
				break;
			boolean addEntry = true;
			if( begin != null && begin.isGreater(entry.getTime()))
				addEntry = false;
			if( addEntry ) {
				entries.add(new TimedEntry<T>(
						(T)entryInfo.getEntryTypeParser().deserialize(entry.getRawData()), new DateTime(date, entry.getTime())));
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nosqlrepository.RepositoryManager#getLast(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> TimedEntry<T> getLast(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			List<TimedEntry<T>> list = get(entry, e.getTimeSummary().getLast());
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
	public <T> TimedEntry<T> getFirst(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			List<TimedEntry<T>> list = get(entry, e.getTimeSummary().getFirst());
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
	public <T> TimedEntry<T> getMax(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			if (e.getTimeSummary().getMax() == null)
				return null;

			List<TimedEntry<T>> list = get(entry, e.getTimeSummary().getMax());
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
	public <T> TimedEntry<T> getMin(String entry) throws Exception {
		synchronized (entryAccessLock) {
			Entry e = repository.getEntry(entry);
			if (e == null)
				throw new Exception("Entry '" + entry + "' does not exist in repository");

			if (e.getTimeSummary().getMin() == null)
				return null;

			List<TimedEntry<T>> list = get(entry, e.getTimeSummary().getMin());
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
