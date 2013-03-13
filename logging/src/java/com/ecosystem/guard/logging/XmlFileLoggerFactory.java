/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.logging;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class XmlFileLoggerFactory implements LoggerFactory {
	private static final String LOG_LEVEL = "com.ecosystem.guard.logging.XmlFileLoggerFactory.level";
	private static final String LOG_FILE_NAME = "ecosystemguard.log";
	private static final int LOG_FILE_SIZE = 5 * 1024 * 1024;
	private static final int LOG_FILE_COUNT = 20;

	XmlFileLoggerFactory() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.logging.LoggerFactory#createLogger()
	 */
	@Override
	public Logger createLogger() throws Exception {
		Logger logger = Logger.getLogger(XmlFileLoggerFactory.class.getName());
		logger.addHandler(new FileHandler(LOG_FILE_NAME, LOG_FILE_SIZE, LOG_FILE_COUNT, true));
		String logLevel = System.getProperty(LOG_LEVEL);
		Level level = Level.INFO;
		if (logLevel != null)
			Level.parse(logLevel);
		logger.setLevel(level);
		logger.setUseParentHandlers(false);
		return logger;
	}

}
