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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class SysoutLoggerFactory implements LoggerFactory {
	SysoutLoggerFactory() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.logging.LoggerFactory#createLogger()
	 */
	@Override
	public Logger createLogger() throws Exception {
		Logger logger = Logger.getLogger(SysoutLoggerFactory.class.getName());
		logger.setLevel(Level.INFO);
		return logger;
	}
}
