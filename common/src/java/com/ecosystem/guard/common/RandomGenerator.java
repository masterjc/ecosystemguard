package com.ecosystem.guard.common;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Proveedor de secuencias aleatorias estática.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RandomGenerator {
	private static final int DEFAULT_NUMBER_BITS = 64;
	private static final int DEFAULT_RADIX = 64;
	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generateRandom(int numberOfBits, int radix) {

		return (new BigInteger(numberOfBits, secureRandom)).toString(radix);
	}

	public static String generateRandom() {
		return (new BigInteger(DEFAULT_NUMBER_BITS, secureRandom)).toString(DEFAULT_RADIX);
	}
}
