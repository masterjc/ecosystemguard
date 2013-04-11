package com.ecosystem.guard.test;

import java.io.FileOutputStream;

import org.junit.Test;

import com.ecosystem.guard.camera.impl.H264Resolution;
import com.ecosystem.guard.camera.impl.VideoCodec;
import com.ecosystem.guard.camera.impl.VideoContainer;
import com.ecosystem.guard.common.StreamServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.service.app.RecordVideoRequest;
import com.ecosystem.guard.domain.service.app.VideoConfiguration;

public class RecordVideoTest {
	@Test
	public void recordDefaultVideo() throws Exception {
		String url = "http://hb:8080/ecosystemguard-app/recordvideo";
		RecordVideoRequest request = new RecordVideoRequest();
		request.setCredentials(new Credentials("juancarlos.fernandezj@gmail.com", "demodemo"));
		request.setLength(10);
		VideoConfiguration config = new VideoConfiguration();
		config.setCodec(VideoCodec.H264.name());
		config.setContainer(VideoContainer.MP4.name());
		config.setResolution(H264Resolution.HVGA.name());
		request.setVideoConfiguration(config);
		FileOutputStream video = new FileOutputStream("testvideo.mp4");
		StreamServiceRequestor.sendRequest(request, RecordVideoRequest.class, url, video);
	}
}
