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

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import com.ecosystem.guard.nosql.disk.FileEntry;
import com.ecosystem.guard.nosql.disk.FileEntryParser;

/**
 * @version $Revision$
 */
public class FileEntryParserTest {
	@Test
	public void parseCorrectFile() throws Exception {
		FileInputStream stream = new FileInputStream(new File("data.bin"));
		try {
			FileEntryParser parser = new FileEntryParser(stream);
			FileEntry entry = null;
			while( (entry = parser.next()) != null ) {
				System.out.println(entry.getDate() + " - " + new String(entry.getRawData()));
			}
		} finally {
			stream.close();
		}
	}
}
