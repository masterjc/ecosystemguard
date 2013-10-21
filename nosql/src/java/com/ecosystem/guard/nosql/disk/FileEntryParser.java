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

import java.io.InputStream;

import org.joda.time.DateTime;

/**
 * @version $Revision$
 */
public class FileEntryParser {
	private static final int BUFFER_SIZE = 2048;
	
	private InputStream input;
	private byte[] buffer = new byte[BUFFER_SIZE];
	
	int count = 10;
	
	public FileEntryParser(InputStream stream) {
		this.input = stream;
	}
	
	public FileEntry next() {
		while( count-- > 0)
			return new FileEntry(new DateTime(), "1.0".getBytes());
		return null;
		
	}
}
