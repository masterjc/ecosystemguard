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

import java.util.Scanner;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CmdUtils {
	private static final int MIN_PASSWORD_LENGTH = 8;

	private static Scanner scanner = new Scanner(System.in);

	public static char[] readPassword() throws Exception {
		char[] pass = null;
		if (System.console() != null) {
			pass = System.console().readPassword();
		}
		else {
			pass = scanner.nextLine().toCharArray();
		}
		if (pass.length < MIN_PASSWORD_LENGTH)
			throw new Exception("Password must be 8 characters at least");
		return pass;
	}

}
