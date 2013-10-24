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

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @version $Revision$
 */
public class DateTime {
	private static Calendar calendar = GregorianCalendar.getInstance();
	private Date date;
	private Time time;

	public DateTime(Date date, Time time) {
		this.date = date;
		this.time = time;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DateTime) {
			DateTime datetime = (DateTime) obj;
			return datetime.getDate().equals(date) && datetime.getTime().equals(time);
		}
		else if (obj instanceof Date) {
			Date ddate = (Date) obj;
			return ddate.equals(date);
		}
		else if (obj instanceof Time) {
			Time dtime = (Time) obj;
			return dtime.equals(time);
		}
		return false;
	}

	public static DateTime getNow() {
		calendar.setTime(new java.util.Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return new DateTime(new Date(year, month, day), new Time(hour, minute, second));
	}

	public static DateTime parse(String dateTime) throws Exception {
		if (dateTime == null)
			throw new Exception("Null input datetime");
		String[] elems = dateTime.split("Z");
		if (elems == null || elems.length != 2)
			throw new Exception("Incorrect datetime format" + dateTime);
		Date date = Date.parse(elems[0]);
		Time time = Time.parse(elems[1]);
		return new DateTime(date, time);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return date.toString() + "Z" + time.toString();
	}

	public boolean isGreater(DateTime dateTime) {
		if (date.isGreater(dateTime.getDate()))
			return true;
		if (dateTime.getDate().isGreater(date))
			return false;

		return time.isGreater(dateTime.getTime());
	}

	public boolean isInsideInterval(DateTime begin, DateTime end) throws Exception {
		return (isGreater(begin) || equals(begin) ) && (end.isGreater(this) || equals(end) );
	}
}
