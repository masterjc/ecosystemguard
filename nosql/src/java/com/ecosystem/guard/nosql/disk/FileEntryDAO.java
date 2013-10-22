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
import java.io.FileOutputStream;

import com.ecosystem.guard.nosql.time.Time;

/**
 * @version $Revision$
 */
public class FileEntryDAO {
	private static final String TIME_HEADER = "[TIME";
	private static final String TIME_SEPARATOR = ":";
	private static final String TIME_TAIL = "]";
	private static final String LINE_SEPARATOR = "\n";

	public static <T> void write(FileEntry entry, File file) throws Exception {
		FileOutputStream output = new FileOutputStream(file, true);
		try {
			String header = TIME_HEADER + entry.getTime().getHour() + TIME_SEPARATOR + entry.getTime().getMinute() + TIME_SEPARATOR
					+ entry.getTime().getSecond() + TIME_TAIL;
			output.write(header.getBytes());
			output.write(entry.getRawData());
			output.write(LINE_SEPARATOR.getBytes());
		}
		finally {
			output.close();
		}
	}

	public static <T> FileEntry read(BufferedReader reader) throws Exception {
		String line = reader.readLine();
		if (line == null)
			return null;
		int endTimeChar = line.indexOf(TIME_TAIL);
		int startTimeChar = line.indexOf(TIME_HEADER);
		if (startTimeChar == -1 || endTimeChar == -1)
			throw new Exception("Incorrect FileEntry format. Expected time specification [TIME...]");
		String time = line.substring(TIME_HEADER.length(), endTimeChar);
		Time t = Time.parse(time);
		String data = line.substring(endTimeChar + 1, line.length());
		return new FileEntry(t, data.getBytes());
	}
}
