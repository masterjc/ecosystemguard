package com.ecosystem.guard.test;

import java.io.FileOutputStream;

import org.junit.Test;

import com.ecosystem.guard.common.StreamServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.service.host.RecordVideoRequest;

public class RecordVideoTest {
	@Test
	public void recordDefaultVideo() throws Exception {
		String url = "http://hb:8080/ecosystemguard-app/recordvideo";
		RecordVideoRequest request = new RecordVideoRequest();
		request.setCredentials(new Credentials("juancarlos.fernandezj@gmail.com", "demodemo"));
		FileOutputStream video = new FileOutputStream("testvideo.mp4");
		StreamServiceRequestor.sendRequest(request, RecordVideoRequest.class, url, video);
	}
}
