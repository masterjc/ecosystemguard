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

import org.joda.time.DateTime;

import com.ecosystem.guard.nosql.Entry;
import com.ecosystem.guard.nosql.Repository;
import com.ecosystem.guard.nosql.RepositoryManager;

/**
 * @version $Revision$
 */
public class RepositoryManagerImpl implements RepositoryManager {
	private Repository repository;
	private static Object entryAccessLock = new Object();
	
	public RepositoryManagerImpl(Repository repository) {
		this.repository = repository;
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T> void insert(String entry, T value) throws Exception {
		insert( entry, new DateTime(), value);
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#insert(java.lang.String, java.util.Date, java.lang.Object)
	 */
	@Override
	public <T> void insert(String entry, DateTime date, T value) throws Exception {
		Entry e = repository.getEntry(entry);
		if( e == null )
			throw new Exception("Entry '" + entry + "' does not exist in repository");
		
		String dir = repository.getName() + "/" + date.year().get() + "/" + date.monthOfYear().get() + "/" + date.dayOfMonth().get();
		createDirectory(dir);
		File entryFile = new File( dir + "/" + entry );
		synchronized (entryAccessLock) {
			byte[] serialized = e.getEntryTypeParser().serialize(value);
			FileEntryDAO.write(date, serialized, entryFile);
		}
		
		synchronized (e) {
			if( e.getTimeSummary().getFirst() == null ) {
				e.getTimeSummary().setFirst(date);
			}
			e.getTimeSummary().setLast(date);
		}
	}
	
	private void createDirectory(String dir) {
		File entryDir = new File(dir);
		if( !entryDir.exists() ) {
			entryDir.mkdirs();
		}
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#get(java.lang.String, java.util.Date, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String entry, DateTime date) throws Exception {
		File entryFile = new File(repository.getName() + "/" + date.year().get() + "/" + date.monthOfYear().get() + "/" + date.dayOfMonth().get() + "/" + entry);
		if( !entryFile.exists() )
			return null;
		
		FileEntry fileEntry = null;
		synchronized (entryAccessLock) {
			FileInputStream input = new FileInputStream(entryFile);
			try {
				FileEntryParser parser = new FileEntryParser(input);
				FileEntry tmpEntry = null;
				while((tmpEntry = parser.next()) != null ) {
					if( tmpEntry.getDate().isEqual(date) ) {
						fileEntry = tmpEntry;
						break;
					}
				}
			} finally {
				input.close();
			}
		}
		
		if( fileEntry == null )
			 return null;
		
		Entry e = repository.getEntry(entry);
		if( e == null )
			return null;
		return (T)e.getEntryTypeParser().deserialize(fileEntry.getRawData());
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#getLast(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getLast(String entry) {
		return null;
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#getFirst(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getFirst(String entry) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#getMax(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getMax(String entry) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nosqlrepository.RepositoryManager#getMin(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getMin(String entry) {
		// TODO Auto-generated method stub
		return null;
	}

}
