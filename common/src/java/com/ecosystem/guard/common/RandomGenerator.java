package com.ecosystem.guard.common;

import java.security.SecureRandom;

/**
 * Proveedor de secuencias aleatorias estática.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RandomGenerator {
	private static final SecureRandom secureRandom = new SecureRandom();

	public static byte[] generateRandom(int numberOfBits) {
		byte[] bytes = new byte[numberOfBits];
		secureRandom.nextBytes(bytes);
		return bytes;
	}
}
