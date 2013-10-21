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

/**
 * @version $Revision$
 */
public class TimeSummaryDAO {
	public static void write(String repositoryName, String entryName, TimeSummary timeSummary) throws Exception {
		FileWriter writer = new FileWriter(repositoryName + "/" + entryName + ".info" );
		try {
			writer.write("[FIRSTDATE]" + timeSummary.getFirst() + "\n");
			writer.write("[LASTDATE]" + timeSummary.getLast() + "\n");
			writer.write("[MAXDATE]" + timeSummary.getMax() + "\n");
			writer.write("[MINDATE]" + timeSummary.getMin() + "\n");
		} finally {
			writer.close();
		}
	}
	
	public static TimeSummary read(File file) throws Exception {
		int counter = 0;
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		try {
			while( (line = reader.readLine()) != null ) {
				switch(counter) {
				case 0:
					parsePattern(line, "[FIRSTDATE]");
					break;
				case 1:
					parsePattern(line, "[LASTDATE]");
					break;
				case 2:
					parsePattern(line, "[MAXDATE]");
					break;
				case 3:
					parsePattern(line, "[MINDATE]");
					break;
				}
				counter++;
			}
		} finally {
			reader.close();
		}
		return null;
	}
	
	private static void parsePattern(String line, String pattern) throws Exception {
		int index = line.indexOf(pattern);
		if( index == -1)
			throw new Exception( "Expected " + pattern + " variable in entry info" );
		String date = line.substring(pattern.length(), line.length());
		System.out.println(pattern + ": " + date);
	}
}
