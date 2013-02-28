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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase para realizar cifrado y descifrado con algoritmo 3DES. La clave se ha de indicar en el
 * constructor. Esta clase es costosa de generar, se recomienda mantener una sola instancia
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public final class TrippleDesCipher {
	private final static String SECRET_KEY_ALGORITHM = "DESede";
	private final static String CIPHER_INSTANCE = SECRET_KEY_ALGORITHM + "/CBC/PKCS5Padding";
	
	private final static int DESEDE_BYTES_KEYLENGTH = 24;

	private final Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
	private final Cipher decipher = Cipher.getInstance(CIPHER_INSTANCE);

	public TrippleDesCipher(byte[] desEdeSecretKey) throws Exception {
		if (desEdeSecretKey.length != DESEDE_BYTES_KEYLENGTH)
			throw new Exception("Incorrect 3DES key length. Expected: " + DESEDE_BYTES_KEYLENGTH + ". Length: "
					+ desEdeSecretKey.length);
		initialize(desEdeSecretKey);
	}

	/**
	 * Cifra los datos utilizando el algoritmo 3DES (clave especificada por constructor)
	 * 
	 * @param dataToEncrypt
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] dataToEncrypt) throws Exception {
		return cipher.doFinal(dataToEncrypt);
	}

	/**
	 * Genera una clave simétrica 3DES aleatoria de 192 bits (24 bytes)
	 * 
	 * @return Clave 3DES de 24 bytes
	 * @throws Exception
	 */
	public static byte[] generate3DesSecretKey() throws Exception {
		return RandomGenerator.generateRandom(DESEDE_BYTES_KEYLENGTH);
	}

	/**
	 * Descifra los datos utilizando el algoritmo 3DES (clave especificada por constructor)
	 * 
	 * @param dataToDecrypt
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] dataToDecrypt) throws Exception {
		return decipher.doFinal(dataToDecrypt);
	}

	private void initialize(byte[] desEdeSecretKey) throws Exception {
		SecretKey secretKey = new SecretKeySpec(desEdeSecretKey, SECRET_KEY_ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		decipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	}
}
