package com.ecosystem.guard.registry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class RegisterServiceTest {
	@Test
	public void registerTest() throws Exception {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:8080/ecosystemguard-registry/service");
		try {
			httpPost.setEntity(new StringEntity("request"));
			HttpResponse httpResponse = httpclient.execute(httpPost);
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response);
		} finally {
			httpPost.releaseConnection();
		}
	}
	
}
