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

import java.io.File;
import java.io.FileOutputStream;

import org.joda.time.DateTime;

/**
 * @version $Revision$
 */
public class FileEntryDAO {

	public static <T> void write(DateTime date, byte[] value, File file ) throws Exception {
		FileOutputStream output = new FileOutputStream(file, true);
		try {
			String header = "[TIME" + date.getHourOfDay() + ":" + date.getMinuteOfHour() + ":" + date.getSecondOfMinute() + "]";
			output.write(header.getBytes());
			output.write(value);
			output.write("\n".getBytes());
		} finally {
			output.close();
		}
	}
}
