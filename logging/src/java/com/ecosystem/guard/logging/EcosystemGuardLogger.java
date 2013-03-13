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

import java.util.logging.Logger;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class EcosystemGuardLogger {
	private static final String LOGGER_FACTORY_PROPERTY = "ecosystemguard.logger.factory";

	private static Logger logger = null;
	private static LoggerFactory factory = new SysoutLoggerFactory();
	private static Integer lock = new Integer(0);

	private EcosystemGuardLogger() {
	}

	public static Logger getLogger() throws Exception {
		if (logger == null) {
			synchronized (lock) {
				if (logger == null) {
					initializeLoggerFactory();
					logger = factory.createLogger();
				}
			}
		}
		return logger;
	}

	private static void initializeLoggerFactory() throws Exception {
		String loggerFactory = System.getProperty(LOGGER_FACTORY_PROPERTY);
		if (loggerFactory == null)
			return;
		Class<?> loggerFactoryClass = Class.forName(loggerFactory);
		factory = (LoggerFactory) loggerFactoryClass.newInstance();
	}
}
