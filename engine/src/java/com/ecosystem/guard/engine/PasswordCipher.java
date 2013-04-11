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

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import com.ecosystem.guard.common.FileUtils;
import com.ecosystem.guard.common.TrippleDesCipher;
import com.ecosystem.guard.engine.config.SystemProperties;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public final class PasswordCipher {
	private static final String DESEDE_KEY_FILENAME = "passwordcipher.key";
	private static PasswordCipher passwordCipher;
	private static final Integer lock = new Integer(0);

	private TrippleDesCipher cipher;
	private final MessageDigest digester = MessageDigest.getInstance("SHA-256");

	/**
	 * Carga la clave privada 3DES de un fichero de configuración del sistema. Si no existe el
	 * fichero, genera la clave 3DES y lo guarda en el fichero.
	 * 
	 * @throws Exception
	 */
	private PasswordCipher() throws Exception {
		String keyFilename = SystemProperties.getConfigDirectory() + "/" + DESEDE_KEY_FILENAME;
		File keyFile = new File(keyFilename);
		byte[] cipherKey = null;
		if (!keyFile.exists()) {
			cipherKey = TrippleDesCipher.generate3DesSecretKey();
			FileUtils.writeBinaryFile(keyFile, cipherKey);
		}
		else {
			cipherKey = FileUtils.readBinaryFile(keyFile);
		}
		cipher = new TrippleDesCipher(cipherKey);
	}

	/**
	 * Retorna la instancia única de PasswordCipher
	 * 
	 * @return PasswordCipher
	 * @throws Exception
	 */
	public static PasswordCipher getInstance() throws Exception {
		if (passwordCipher == null) {
			synchronized (lock) {
				if (passwordCipher == null) {
					passwordCipher = new PasswordCipher();
				}
			}
		}
		return passwordCipher;
	}

	/**
	 * Calcula el hash SHA-256 del password, cifra mediante 3DES, devuelve la codificación Base64 y
	 * ceroiza el password original
	 * 
	 * @param clearPassword
	 * @return Base64( 3DES( HashSHA256( clearPassword) ) )
	 * @throws Exception
	 */
	public String cipherPassword(byte[] clearPassword) throws Exception {
		byte[] digestedPassword = digester.digest(clearPassword);
		byte[] cipheredPassword = cipher.encrypt(digestedPassword);
		zeroize(digestedPassword);
		String finalPassword = Base64.encodeBase64String(cipheredPassword);
		zeroize(cipheredPassword);
		zeroize(clearPassword);
		return finalPassword;
	}

	/**
	 * Comprueba que un password cifrado mediante esta clase se igual a un password en claro. Este
	 * método zeroiza el password original una vez comprobado
	 * 
	 * @param cipheredPassword Password cifrado mediante el método cipherPassword() de esta clase.
	 * @param clearPassword Password en claro (será ceroizado)
	 * @return Los password son iguales
	 * @throws Exception
	 */
	public boolean checkPasswords(String cipheredPassword, byte[] clearPassword) throws Exception {
		byte[] binCipheredPassword = Base64.decodeBase64(cipheredPassword);
		byte[] decipheredPassword = cipher.decrypt(binCipheredPassword);
		zeroize(binCipheredPassword);
		boolean equals = Arrays.equals(decipheredPassword, digester.digest(clearPassword));
		zeroize(decipheredPassword);
		zeroize(clearPassword);
		return equals;
	}

	/**
	 * Zeroiza un password
	 * 
	 * @param password
	 */
	private void zeroize(byte[] password) {
		for (int i = 0; i < password.length; i++) {
			password[i] = '0';
		}
	}
}
