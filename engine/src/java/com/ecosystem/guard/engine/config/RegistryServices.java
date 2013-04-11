package com.ecosystem.guard.engine.config;

import com.ecosystem.guard.engine.config.EcosystemConfig;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RegistryServices {
	private static final String AUTHN_SERVICE_NAME = "authn";
	private static final String AUTHZ_SERVICE_NAME = "authz";
	private static final String UPDATE_IP_SERVICE_NAME = "updateip";
	private static final String URL_PATH_SEPARATOR = "/";

	public static String getAuthNUrl() throws Exception {
		return getServiceUrl(AUTHN_SERVICE_NAME);
	}

	public static String getAuthZUrl() throws Exception {
		return getServiceUrl(AUTHZ_SERVICE_NAME);
	}

	public static String getUpdateIpUrl() throws Exception {
		return getServiceUrl(UPDATE_IP_SERVICE_NAME);
	}

	private static String getServiceUrl(String service) throws Exception {
		String baseUrl = EcosystemConfig.getAppConfig().getRegistryUrl();
		if (baseUrl.endsWith("/")) {
			return baseUrl + service;
		}
		else {
			return baseUrl + URL_PATH_SEPARATOR + service;
		}
	}
}
