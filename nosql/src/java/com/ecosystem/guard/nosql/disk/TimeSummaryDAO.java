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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.ecosystem.guard.nosql.TimeSummary;
import com.ecosystem.guard.nosql.time.DateTime;

/**
 * @version $Revision$
 */
public class TimeSummaryDAO {
	private static final String FIRST_DATE = "[FIRSTDATE]";
	private static final String LAST_DATE = "[LASTDATE]";
	private static final String MAX_DATE = "[MAXDATE]";
	private static final String MIN_DATE = "[MINDATE]";
	private static final String LINE_SEPARATOR = "\n";

	public static void write(String repositoryName, String entryName, TimeSummary timeSummary) throws Exception {
		FileWriter writer = new FileWriter(repositoryName + "/" + entryName + ".info");
		try {
			writer.write(FIRST_DATE + timeSummary.getFirst().toString() + LINE_SEPARATOR);
			writer.write(LAST_DATE + timeSummary.getLast().toString() + LINE_SEPARATOR);
			if (timeSummary.getMax() != null) {
				writer.write(MAX_DATE + timeSummary.getMax().toString() + LINE_SEPARATOR);
			}
			if (timeSummary.getMin() != null) {
				writer.write(MIN_DATE + timeSummary.getMin().toString() + LINE_SEPARATOR);
			}
		}
		finally {
			writer.close();
		}
	}

	public static TimeSummary read(File file) throws Exception {
		int counter = 0;
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		TimeSummary timeSummary = new TimeSummary();
		try {
			while ((line = reader.readLine()) != null) {
				switch (counter) {
				case 0:
					timeSummary.setFirst(parsePattern(line, FIRST_DATE));
					break;
				case 1:
					timeSummary.setLast(parsePattern(line, LAST_DATE));
					break;
				case 2:
					timeSummary.setMax(parsePattern(line, MAX_DATE));
					break;
				case 3:
					timeSummary.setMin(parsePattern(line, MIN_DATE));
					break;
				}
				counter++;
			}
		}
		finally {
			reader.close();
		}
		return timeSummary;
	}

	private static DateTime parsePattern(String line, String pattern) throws Exception {
		int index = line.indexOf(pattern);
		if (index == -1)
			return null;
		String date = line.substring(pattern.length(), line.length());
		return DateTime.parse(date);
	}
}
