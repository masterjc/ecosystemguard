package com.ecosystem.guard.registry;

import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.service.RegisterRequest;
import com.ecosystem.guard.domain.service.RegisterResponse;

public class RegisterServiceTest {
	@Test
	public void registerTest() throws Exception {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:8080/ecosystemguard-registry/register");
		try {
			RegisterRequest request = new RegisterRequest();
			request.setCredentials(new Credentials("f@gmail.com", "password"));
			httpPost.setEntity(new StringEntity(Serializer.serialize(request, RegisterRequest.class)));
			
			HttpResponse httpResponse = httpclient.execute(httpPost);
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response);
		
			RegisterResponse registerResponse = Deserializer.deserialize(RegisterResponse.class, new StringReader(response));
			System.out.println("RegisterResponse status: " + registerResponse.getResult().getStatus());
			System.out.println("RegisterResponse appstatus: " + registerResponse.getResult().getAppStatus());
			System.out.println("RegisterResponse message: " + registerResponse.getResult().getMessage());
		} finally {
			httpPost.releaseConnection();
		}
	}
	
}
