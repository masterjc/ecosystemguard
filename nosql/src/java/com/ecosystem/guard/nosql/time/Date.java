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
public class Date {
	public int year;
	public int month;
	public int day;

	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Date) {
			Date date = (Date) obj;
			return date.getYear() == year && date.getMonth() == month && date.getDay() == day;
		} 
		else if (obj instanceof DateTime) {
			Date ddate = ((DateTime) obj).getDate();
			return equals(ddate);
		}
		return false;
	}

	public static Date parse(String date) throws Exception {
		if (date == null)
			throw new Exception("Null input date");
		String[] elems = date.split("-");
		if (elems == null || elems.length != 3)
			throw new Exception("Incorrect date format" + date);
		return new Date(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]), Integer.parseInt(elems[2]));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return year + "-" + month + "-" + day;
	}

	public boolean isGreater(Date date) {
		long dateLong = date.getYear() * 365 + date.getMonth() * 30 + date.getDay();
		long thisLong = getYear() * 365 + getMonth() * 30 + getDay();
		return thisLong > dateLong;
	}

	public boolean isInsideInterval(Date begin, Date end) throws Exception {
		return ( isGreater(begin) || equals(begin)) && (end.isGreater(this) || equals(end) );
	}

}
