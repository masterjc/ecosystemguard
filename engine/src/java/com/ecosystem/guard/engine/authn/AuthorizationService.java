/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.engine.authn;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ecosystem.guard.common.Deserializer;
import com.ecosystem.guard.common.Serializer;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.config.HostConfig;
import com.ecosystem.guard.domain.service.registry.AuthZRequest;
import com.ecosystem.guard.domain.service.registry.AuthZResponse;
import com.ecosystem.guard.engine.EcosystemConfig;
import com.ecosystem.guard.engine.SystemProperties;

/**
 * Servicio de autorizaci�n de EcosystemGuard. Es un cliente HTTP del servicio
 * AuthZRequest.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AuthorizationService {

	/**
	 * Autentica y autoriza un usuario contra el servicio AuthZRequest de
	 * EcosystemGuard. La URL es una propiedad del sistema
	 * 
	 * @param credentials
	 * @return
	 * @throws Exception
	 */
	public static AuthorizationContext authorize(HttpServletRequest servletRequest, Credentials credentials,
			String resourceId) throws Exception {
		HostConfig hostConfig = EcosystemConfig.getHostConfig();
		AuthorizationContext authzContext = new AuthorizationContext();
		authzContext.setHostId(hostConfig.getId());
		authzContext.setResourceId(resourceId);
		authzContext.setAuthorized(false);
		AuthenticationContext authnContext = new AuthenticationContext();
		authnContext.setAuthenticated(false);
		authnContext.setUsername(credentials.getUsernamePassword().getUsername());
		AuthenticationService.setCommonServiceAuthnContext(authnContext, servletRequest);
		authzContext.setAuthnContext(authnContext);

		AuthZRequest request = new AuthZRequest();
		request.setCredentials(credentials);
		request.setResourceId(resourceId);
		request.setHostId(hostConfig.getId());
		String authZUrl = SystemProperties.getAuthZServiceUrl();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(authZUrl);
		httpPost.setEntity(new StringEntity(Serializer.serialize(request, AuthZRequest.class)));
		HttpResponse httpResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		AuthZResponse authZResponse = Deserializer.deserialize(AuthZResponse.class, new StringReader(response));

		if (authZResponse.getResult().getStatus() == Result.Status.OK) {
			authzContext.setAuthorized(true);
			authzContext.getAuthnContext().setAuthenticated(true);
		} else {
			if (authZResponse.getResult().getStatus().equals(Status.AUTHZ_ERROR)) {
				authzContext.getAuthnContext().setAuthenticated(true);
			}
		}
		return authzContext;
	}
}
