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

/**
 * Propiedades del sistema EcosystemGuard local
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public final class SystemProperties {
	public static final String CONFIG_DIR_PROPERTY = "com.ecosystem.guard.config.directory";
	public static final String AUTHN_SERVICE_URL = "com.ecosystem.guard.config.authn.url";

	/**
	 * Devuelve el directory donde se encuentra la configuraciÃ³n del sistema
	 * EcosystemGuard. Si no existe el directory lanza una excepciÃ³n. No
	 * retorna NULL nunca
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getConfigDirectory() throws Exception {
		String directory = System.getProperty(CONFIG_DIR_PROPERTY);
		if (directory == null)
			throw new Exception("EcosystemGuard " + CONFIG_DIR_PROPERTY + " property is not configured");

		File dir = new File(directory);
		if (!dir.exists() || !dir.isDirectory())
			throw new Exception("EcosystemGuard configuration directory does not exist");

		return directory;
	}

	/**
	 * Devuelve la URL del servicio de autenticación de usuarios. Si no existe
	 * lanza excepción.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAuthNServiceUrl() throws Exception {
		String url = System.getProperty(AUTHN_SERVICE_URL);
		if (url == null)
			throw new Exception("EcosystemGuard AuthN Service URL property is not configured");
		return url;

	}

}
