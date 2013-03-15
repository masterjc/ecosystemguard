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

import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.service.registry.AuthNRequest;
import com.ecosystem.guard.domain.service.registry.AuthNResponse;
import com.ecosystem.guard.engine.SystemProperties;

/**
 * Servicio de autenticaci�n de EcosystemGuard. Es un cliente HTTP del servicio AuthNRequest.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AuthenticationService {

	/**
	 * Autentica un usuario contra el servicio AuthNRequest de EcosystemGuard. La URL es una
	 * propiedad del sistema
	 * 
	 * @param credentials
	 * @return
	 * @throws Exception
	 */
	public static AuthenticationContext authenticate(HttpServletRequest servletRequest, Credentials credentials)
			throws Exception {
		AuthenticationContext authnContext = new AuthenticationContext();
		authnContext.setAuthenticated(false);
		authnContext.setUsername(credentials.getUsernamePassword().getUsername());

		AuthNRequest request = new AuthNRequest();
		request.setCredentials(credentials);
		String authNUrl = SystemProperties.getAuthNServiceUrl();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(authNUrl);
		httpPost.setEntity(new StringEntity(Serializer.serialize(request, AuthNRequest.class)));
		HttpResponse httpResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		AuthNResponse authNResponse = Deserializer.deserialize(AuthNResponse.class, new StringReader(response));
		setCommonServiceAuthnContext(authnContext, servletRequest);
		if (authNResponse.getResult().getStatus() == Result.Status.OK) {
			authnContext.setAuthenticated(true);
		}
		return authnContext;
	}

	private static void setCommonServiceAuthnContext(AuthenticationContext authContext, HttpServletRequest request)
			throws Exception {
		authContext.setRemoteIpAddress(request.getRemoteAddr());
	}
}
