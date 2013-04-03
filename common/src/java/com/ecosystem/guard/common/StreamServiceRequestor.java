package com.ecosystem.guard.common;

import java.io.OutputStream;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Serializer;

public class StreamServiceRequestor {
	public static <T> void sendRequest(T request, Class<T> requestClass, String url, OutputStream response)
			throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			String xmlRequest = Serializer.serialize(request, requestClass);
			httpPost.setEntity(new StringEntity(xmlRequest));
			HttpResponse httpResponse = httpclient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK ) {
				StreamingUtils.consumeStream(httpResponse.getEntity().getContent(), response);
			} else {
				String xmlResponse = EntityUtils.toString(httpResponse.getEntity());
				Result result = Deserializer.deserialize(Result.class, new StringReader(xmlResponse));
				throw new Exception( result.getStatus().getStatusCode() + ": " + result.getMessage());
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
}
