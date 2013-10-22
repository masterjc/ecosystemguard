/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.nosql.time;

/**
 * @version $Revision$
 */
public class Time {
	private int hour;
	private int minute;
	private int second;

	/**
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public Time(int hour, int minute, int second) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Time) {
			Time time = (Time) obj;
			return time.getHour() == hour && time.getMinute() == minute && time.getSecond() == second;
		}
		else if (obj instanceof DateTime) {
			Time ttime = ((DateTime) obj).getTime();
			return equals(ttime);
		}
		return false;
	}

	public static Time parse(String time) throws Exception {
		if (time == null)
			throw new Exception("Null input time");
		String[] elems = time.split(":");
		if (elems == null || elems.length != 3)
			throw new Exception("Incorrect time format" + time);
		return new Time(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]), Integer.parseInt(elems[2]));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return hour + ":" + minute + ":" + second;
	}

	public boolean isGreater(Time time) {
		if (hour > time.getHour())
			return true;
		if (minute > time.getMinute())
			return true;
		if (second > time.getSecond())
			return true;
		return false;
	}

	public boolean isInsideInterval(Time begin, Time end) throws Exception {
		return isGreater(begin) && end.isGreater(this);
	}

}
