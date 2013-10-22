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

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.ecosystem.guard.nosql.disk.FileEntry;
import com.ecosystem.guard.nosql.disk.FileEntryParser;

/**
 * @version $Revision$
 */
public class FileEntryParserTest {
	@Test
	public void parseCorrectFile() throws Exception {
		String content = "[TIME10:11:12]1.0\n[TIME13:14:15]2.0\n";
		ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes());
		try {
			FileEntryParser parser = new FileEntryParser(stream);
			FileEntry entry = null;
			while ((entry = parser.next()) != null) {
				Assert.assertTrue(new String(entry.getRawData()).equals("1.0") || new String(entry.getRawData()).equals("2.0"));
			}
		}
		finally {
			stream.close();
		}
	}

	@Test
	public void parseTimeWithOneDigit() throws Exception {
		String content = "[TIME10:11:1]1.0\n[TIME13:2:15]2.0\n";
		ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes());
		try {
			FileEntryParser parser = new FileEntryParser(stream);
			FileEntry entry = null;
			while ((entry = parser.next()) != null) {
				Assert.assertTrue(new String(entry.getRawData()).equals("1.0") || new String(entry.getRawData()).equals("2.0"));
				Assert.assertTrue(entry.getTime().getMinute() == 2 || entry.getTime().getSecond() == 1);
			}
		}
		finally {
			stream.close();
		}
	}
}
