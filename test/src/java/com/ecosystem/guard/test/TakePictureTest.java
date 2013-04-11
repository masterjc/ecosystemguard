package com.ecosystem.guard.test;

import java.io.FileOutputStream;

import org.junit.Test;

import com.ecosystem.guard.camera.impl.PictureContainer;
import com.ecosystem.guard.camera.impl.PictureResolution;
import com.ecosystem.guard.common.StreamServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.service.app.PictureConfiguration;
import com.ecosystem.guard.domain.service.app.TakePictureRequest;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class TakePictureTest {
	@Test
	public void takeDefaultPicture() throws Exception {
		String url = "http://hb:8080/ecosystemguard-app/takepicture";
		TakePictureRequest request = new TakePictureRequest();
		request.setCredentials(new Credentials("juancarlos.fernandezj@gmail.com", "demodemo"));
		PictureConfiguration pictureConfiguration = new PictureConfiguration();
		pictureConfiguration.setContainer(PictureContainer.JPEG.name());
		pictureConfiguration.setResolution(PictureResolution.SVGA.name());
		request.setPictureConfiguration(pictureConfiguration);
		FileOutputStream video = new FileOutputStream("testvideo.jpg");
		StreamServiceRequestor.sendRequest(request, TakePictureRequest.class, url, video);
	}
}
