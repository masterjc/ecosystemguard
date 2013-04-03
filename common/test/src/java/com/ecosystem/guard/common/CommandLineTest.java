/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.common;

import org.junit.Test;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CommandLineTest {

	@Test
	public void executeCommandNoTimeout() throws Exception {
		CommandLine command = new CommandLine("sleep 5");
		Process p = command.execute();
		p.waitFor();
	}
	
	@Test(expected=Exception.class)
	public void executeCommandTimeout() throws Exception {		
		CommandLine command = new CommandLine("sleep 10");
		command.setExecTimeoutSeconds(5);
		Process p = command.execute();
		p.waitFor();
	}
}
