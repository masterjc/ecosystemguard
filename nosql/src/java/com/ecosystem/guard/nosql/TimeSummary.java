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

/**
 * @version $Revision$
 */
public class TimeSummary {
	private DateTime last;
	private DateTime first;
	private DateTime max;
	private DateTime min;
	
	/**
	 * @return the last
	 */
	public DateTime getLast() {
		return last;
	}
	/**
	 * @param last the last to set
	 */
	public void setLast(DateTime last) {
		this.last = last;
	}
	/**
	 * @return the first
	 */
	public DateTime getFirst() {
		return first;
	}
	/**
	 * @param first the first to set
	 */
	public void setFirst(DateTime first) {
		this.first = first;
	}
	/**
	 * @return the max
	 */
	public DateTime getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(DateTime max) {
		this.max = max;
	}
	/**
	 * @return the min
	 */
	public DateTime getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(DateTime min) {
		this.min = min;
	}
}

