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

import org.junit.Assert;
import org.junit.Test;

/**
 * @version $Revision$
 */
public class DateTimeTest {
	@Test
	public void testDate() throws Exception {
		Date date = new Date(2012, 10, 11);
		Date otherDate = Date.parse("2012-10-11");
		Assert.assertEquals(date, otherDate);
		Assert.assertEquals("2012-10-11", date.toString());
	}

	@Test
	public void testTime() throws Exception {
		Time time = new Time(10, 11, 12);
		Time othertime = Time.parse("10:11:12");
		Assert.assertEquals(time, othertime);
		Assert.assertEquals("10:11:12", time.toString());
	}

	@Test
	public void testDateTime() throws Exception {
		DateTime dateTime = new DateTime(new Date(2012, 10, 11), new Time(8, 7, 2));
		DateTime other = DateTime.parse("2012-10-11Z8:7:2");
		Assert.assertEquals(dateTime, other);
		Assert.assertEquals("2012-10-11Z8:7:2", dateTime.toString());
	}
	
	@Test
	public void testDateTime_withNull() throws Exception {
		Date dateTime = new Date(2012, 10, 11);
		DateTime dateTime2 = new DateTime(new Date(2012, 10, 11), new Time(11, 12, 13));
		Assert.assertTrue(dateTime2.equals(dateTime));
		Assert.assertTrue(dateTime.equals(dateTime2));
	}
	
	@Test
	public void testDateIsGreater_year() throws Exception {
		Date date1 = new Date(2012, 10, 11);
		Date date2 = new Date(2013, 10, 11);
		Assert.assertTrue(date2.isGreater(date1));
		Assert.assertFalse(date1.isGreater(date2));
		Assert.assertFalse(date1.isGreater(date1));
	}

	@Test
	public void testDateInterval_year() throws Exception {
		Date date1 = new Date(2011, 10, 11);
		Date date2 = new Date(2012, 10, 11);
		Date date3 = new Date(2013, 10, 11);
		Assert.assertTrue(date2.isInsideInterval(date1, date3));
		Assert.assertFalse(date1.isInsideInterval(date2, date3));
	}

	@Test
	public void testDateIsGreater_month() throws Exception {
		Date date1 = new Date(2013, 10, 11);
		Date date2 = new Date(2013, 11, 11);
		Assert.assertTrue(date2.isGreater(date1));
		Assert.assertFalse(date1.isGreater(date2));
		Assert.assertFalse(date1.isGreater(date1));
	}

	@Test
	public void testDateInterval_month() throws Exception {
		Date date1 = new Date(2013, 8, 11);
		Date date2 = new Date(2013, 10, 11);
		Date date3 = new Date(2013, 11, 11);
		Assert.assertTrue(date2.isInsideInterval(date1, date3));
		Assert.assertFalse(date1.isInsideInterval(date2, date3));
	}

	@Test
	public void testDateIsGreater_day() throws Exception {
		Date date1 = new Date(2013, 10, 10);
		Date date2 = new Date(2013, 10, 11);
		Assert.assertTrue(date2.isGreater(date1));
		Assert.assertFalse(date1.isGreater(date2));
		Assert.assertFalse(date1.isGreater(date1));
	}

	@Test
	public void testDateInterval_day() throws Exception {
		Date date1 = new Date(2013, 10, 5);
		Date date2 = new Date(2013, 10, 11);
		Date date3 = new Date(2013, 10, 30);
		Assert.assertTrue(date2.isInsideInterval(date1, date3));
		Assert.assertFalse(date1.isInsideInterval(date2, date3));
	}

	@Test
	public void testTimeIsGreater_year() throws Exception {
		Time Time1 = new Time(12, 10, 11);
		Time Time2 = new Time(13, 10, 11);
		Assert.assertTrue(Time2.isGreater(Time1));
		Assert.assertFalse(Time1.isGreater(Time2));
		Assert.assertFalse(Time1.isGreater(Time1));
	}

	@Test
	public void testTimeInterval_year() throws Exception {
		Time Time1 = new Time(11, 10, 11);
		Time Time2 = new Time(12, 10, 11);
		Time Time3 = new Time(13, 10, 11);
		Assert.assertTrue(Time2.isInsideInterval(Time1, Time3));
		Assert.assertFalse(Time1.isInsideInterval(Time2, Time3));
	}

	@Test
	public void testTimeIsGreater_month() throws Exception {
		Time Time1 = new Time(13, 10, 11);
		Time Time2 = new Time(13, 11, 11);
		Assert.assertTrue(Time2.isGreater(Time1));
		Assert.assertFalse(Time1.isGreater(Time2));
		Assert.assertFalse(Time1.isGreater(Time1));
	}

	@Test
	public void testTimeInterval_month() throws Exception {
		Time Time1 = new Time(13, 8, 11);
		Time Time2 = new Time(13, 10, 11);
		Time Time3 = new Time(13, 11, 11);
		Assert.assertTrue(Time2.isInsideInterval(Time1, Time3));
		Assert.assertFalse(Time1.isInsideInterval(Time2, Time3));
	}

	@Test
	public void testTimeIsGreater_day() throws Exception {
		Time Time1 = new Time(13, 10, 10);
		Time Time2 = new Time(13, 10, 11);
		Assert.assertTrue(Time2.isGreater(Time1));
		Assert.assertFalse(Time1.isGreater(Time2));
		Assert.assertFalse(Time1.isGreater(Time1));
	}

	@Test
	public void testTimeInterval_day() throws Exception {
		Time Time1 = new Time(13, 10, 5);
		Time Time2 = new Time(13, 10, 11);
		Time Time3 = new Time(13, 10, 30);
		Assert.assertTrue(Time2.isInsideInterval(Time1, Time3));
		Assert.assertFalse(Time1.isInsideInterval(Time2, Time3));
	}

	@Test
	public void testDateTimeIsGreater_dateGreater() throws Exception {
		DateTime date1 = new DateTime(new Date(2014, 10, 11), new Time(13, 10, 5));
		DateTime date2 = new DateTime(new Date(2013, 10, 11), new Time(13, 10, 5));
		Assert.assertTrue(date1.isGreater(date2));
		Assert.assertFalse(date2.isGreater(date1));
	}

	@Test
	public void testDateTimeIsGreater_timeGreater() throws Exception {
		DateTime date1 = new DateTime(new Date(2013, 10, 11), new Time(13, 12, 5));
		DateTime date2 = new DateTime(new Date(2013, 10, 11), new Time(13, 10, 5));
		Assert.assertTrue(date1.isGreater(date2));
		Assert.assertFalse(date2.isGreater(date1));
	}

	@Test
	public void testDateTimeInterval() throws Exception {
		DateTime date1 = new DateTime(new Date(2013, 10, 11), new Time(13, 9, 5));
		DateTime date2 = new DateTime(new Date(2013, 10, 11), new Time(13, 9, 15));
		DateTime date3 = new DateTime(new Date(2013, 11, 11), new Time(13, 10, 5));
		Assert.assertTrue(date2.isInsideInterval(date1, date3));
		Assert.assertFalse(date1.isInsideInterval(date2, date3));
	}
}
