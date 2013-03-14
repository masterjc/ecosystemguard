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

import java.io.StringReader;

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
public class XmlServiceRequestor {

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
