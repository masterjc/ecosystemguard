/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.host;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ManagerConfig {
	private String configDirectory;
	private boolean debug = false;

	public ManagerConfig(String[] args) throws Exception {
		switch (args.length) {
		case 0:
			configDirectory = new String();
			break;
		case 1:
			configDirectory = args[0];
			break;
		case 2:
			configDirectory = args[0];
			debug = args[1].toUpperCase().equals("DEBUG");
			break;
		default:
			throw new Exception("Manager: Incorrect number of arguments");
		}
	}

	public String getConfigDirectory() {
		return configDirectory;
	}

	public boolean isDebug() {
		return debug;
	}

}