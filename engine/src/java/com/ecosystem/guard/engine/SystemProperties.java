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
	private static final String ECOSYSTEM_GUARD_VERSION = "0.1";
	public static final String CONFIG_DIR_PROPERTY = "com.ecosystem.guard.config.directory";
	public static final String AUTHN_SERVICE_URL = "com.ecosystem.guard.config.authn.url";

	/**
	 * Devuelve el directory donde se encuentra la configuración del sistema EcosystemGuard. Si no
	 * existe el directory lanza una excepción. No retorna NULL nunca
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
	 * Devuelve la URL del servicio de autenticaci�n de usuarios. Si no existe lanza excepci�n.
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

	/**
	 * Devuelve la versión software de Ecosystem Guard
	 * 
	 * @return la versión
	 */
	public static String getVersion() {
		return ECOSYSTEM_GUARD_VERSION;
	}

}
