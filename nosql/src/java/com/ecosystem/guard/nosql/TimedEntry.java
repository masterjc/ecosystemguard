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

import com.ecosystem.guard.nosql.time.DateTime;

/**
 * @version $Revision$
 */
public class TimedEntry<T> {
	private T value;
	private DateTime dateTime;
	
	/**
	 * @param value
	 * @param dateTime
	 */
	public TimedEntry(T value, DateTime dateTime) {
		super();
		this.value = value;
		this.dateTime = dateTime;
	}
	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}
	/**
	 * @return the dateTime
	 */
	public DateTime getDateTime() {
		return dateTime;
	}
	
	
}
