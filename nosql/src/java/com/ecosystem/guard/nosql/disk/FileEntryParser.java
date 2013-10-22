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
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @version $Revision$
 */
public class FileEntryParser {
	private BufferedReader input;

	public FileEntryParser(InputStream stream) {
		this.input = new BufferedReader(new InputStreamReader(stream));
	}

	public FileEntry next() throws Exception {
		return FileEntryDAO.read(input);
	}
}
