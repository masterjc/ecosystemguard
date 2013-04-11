/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.engine;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PasswordCipherTest {

	@BeforeClass
	public static void setup() {
		System.setProperty("com.ecosystem.guard.config.directory", ".");
	}

	@Test
	public void testCipherAndComparePassword() throws Exception {
		String password = "esteeselpassword";
		String ciphered = PasswordCipher.getInstance().cipherPassword(password.getBytes());
		Assert.assertNotNull(ciphered);
		Assert.assertEquals(56, ciphered.length());
		Assert.assertTrue(PasswordCipher.getInstance().checkPasswords(ciphered, password.getBytes()));
	}

	@Test
	public void testCipherAndCompareWrongPassword() throws Exception {
		String password = "esteeselpassword";
		String ciphered = PasswordCipher.getInstance().cipherPassword(password.getBytes());
		Assert.assertNotNull(ciphered);
		Assert.assertEquals(56, ciphered.length());
		Assert.assertFalse(PasswordCipher.getInstance().checkPasswords(ciphered, "incorrecto".getBytes()));
	}

}
