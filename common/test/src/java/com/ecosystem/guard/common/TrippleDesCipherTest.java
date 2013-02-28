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

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class TrippleDesCipherTest {
	
	@Test
	public void generate3DESKeyTest() throws Exception {
		byte[] key = TrippleDesCipher.generate3DesSecretKey();
		Assert.assertNotNull(key);
		Assert.assertEquals(24, key.length);
	}
	
	@Test
	public void cipherAndDecipherTest() throws Exception {
		String data = "Data to be ciphered";
		byte[] key = TrippleDesCipher.generate3DesSecretKey();
		TrippleDesCipher cipher = new TrippleDesCipher(key);
		byte[] ciphered = cipher.encrypt(data.getBytes());
		System.out.println(new String(ciphered));
		Assert.assertEquals(data, new String(cipher.decrypt(ciphered)));
	}
	
	@Test
	public void doIt() throws Exception {
		byte[] a = { 'a', 'b', 'c' };
		System.out.println(new String(a));
		zeroize(a);
		System.out.println(new String(a));
	}
	
	private void zeroize(byte[] b) {
		for( int i = 0; i < b.length; i++ ) {
			b[i] = '0';
		}
	}


}
