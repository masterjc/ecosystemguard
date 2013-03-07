/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.host;

import java.io.StringReader;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Serializer;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CmdUtils {
	private static final int MIN_PASSWORD_LENGTH = 8;

	private static Scanner scanner = new Scanner(System.in);

	public static char[] readPassword() throws Exception {
		char[] pass = null;
		if (System.console() != null) {
			pass = System.console().readPassword();
		}
		else {
			pass = scanner.nextLine().toCharArray();
		}
		if (pass.length < MIN_PASSWORD_LENGTH)
			throw new Exception("Password must be 8 characters at least");
		return pass;
	}

	public static <T, R> R sendRequest(T request, Class<T> requestClass, Class<R> responseClass, String url)
			throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			String xmlRequest = Serializer.serialize(request, requestClass);
			httpPost.setEntity(new StringEntity(xmlRequest));
			HttpResponse httpResponse = httpclient.execute(httpPost);
			String response = EntityUtils.toString(httpResponse.getEntity());
			return Deserializer.deserialize(responseClass, new StringReader(response));
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

}
