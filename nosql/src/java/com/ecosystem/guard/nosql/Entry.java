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

import com.ecosystem.guard.nosql.type.EntryTypeConverter;

/**
 * @version $Revision$
 */
public class Entry {
	private String name;
	private Class<?> entryClass;
	private EntryTypeConverter entryTypeParser;
	private TimeSummary timeSummary;
	
	public Entry(String name, Class<?> entryClass, EntryTypeConverter entryTypeParser, TimeSummary timeSummary ) {
		this.name = name;
		this.entryClass = entryClass;
		this.entryTypeParser = entryTypeParser;
		this.timeSummary = timeSummary;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the entryClass
	 */
	public Class<?> getEntryClass() {
		return entryClass;
	}

	/**
	 * @param entryClass the entryClass to set
	 */
	public void setEntryClass(Class<?> entryClass) {
		this.entryClass = entryClass;
	}

	/**
	 * @return the timeSummary
	 */
	public TimeSummary getTimeSummary() {
		return timeSummary;
	}

	/**
	 * @param timeSummary the timeSummary to set
	 */
	public void setTimeSummary(TimeSummary timeSummary) {
		this.timeSummary = timeSummary;
	}

	/**
	 * @return the entryTypeParser
	 */
	public EntryTypeConverter getEntryTypeParser() {
		return entryTypeParser;
	}

	/**
	 * @param entryTypeParser the entryTypeParser to set
	 */
	public void setEntryTypeParser(EntryTypeConverter entryTypeParser) {
		this.entryTypeParser = entryTypeParser;
	}
	
	

	
	
}
