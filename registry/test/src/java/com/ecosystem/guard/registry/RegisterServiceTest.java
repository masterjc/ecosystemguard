package com.ecosystem.guard.registry;

import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.RegisterRequest;
import com.ecosystem.guard.domain.RegisterResponse;
import com.ecosystem.guard.domain.RegisterStatus;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.Serializer;

public class RegisterServiceTest {
	@Test
	public void registerTest() throws Exception {

		/*HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:8080/ecosystemguard-registry/register");
		try {
			RegisterRequest request = new RegisterRequest();
			request.setCredentials(new Credentials("e@gmail.com", "password"));
			httpPost.setEntity(new StringEntity(Serializer.serialize(request, RegisterRequest.class)));
			
			HttpResponse httpResponse = httpclient.execute(httpPost);
			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response);
			int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
			if( httpStatusCode != HttpStatus.SC_OK )
				throw new Exception( "Incorrect Http Status code: " + httpStatusCode );
			
			RegisterResponse registerResponse = Deserializer.deserialize(RegisterResponse.class, new StringReader(response));
			System.out.println("RegisterResponse status: " + registerResponse.getResult().getStatus());
			System.out.println("RegisterResponse message: " + registerResponse.getResult().getMessage());
		} finally {
			httpPost.releaseConnection();
		}*/
		RegisterResponse response = new RegisterResponse(Status.OK, RegisterStatus.OK, "hola");
		System.out.println( Serializer.serialize(response, RegisterResponse.class) );
	}
	
}
