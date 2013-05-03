package com.ecosystem.guard.app;

import junit.framework.Assert;

import org.junit.Test;

import com.ecosystem.guard.app.camera.PictureConfigParser;
import com.ecosystem.guard.camera.PictureConfig;
import com.ecosystem.guard.camera.impl.PictureContainer;
import com.ecosystem.guard.camera.impl.PictureResolution;
import com.ecosystem.guard.domain.service.app.PictureConfiguration;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PictureConfigParserTest {

	@Test
	public void test_JPEG_VGA_Parse() throws Exception {
		PictureConfigParser parser = new PictureConfigParser();
		PictureConfiguration configuration = new PictureConfiguration();
		configuration.setResolution("VGA");
		configuration.setContainer("JPEG");
		PictureConfig videoConfig = parser.parsePictureConfig(configuration);
		Assert.assertEquals(PictureContainer.JPEG, videoConfig.getContainer());
		Assert.assertEquals(PictureResolution.VGA, videoConfig.getResolution());
	}
	
	@Test
	public void test_JPEG_HD_Parse() throws Exception {
		PictureConfigParser parser = new PictureConfigParser();
		PictureConfiguration configuration = new PictureConfiguration();
		configuration.setResolution("HD720P");
		configuration.setContainer("JPEG");
		PictureConfig videoConfig = parser.parsePictureConfig(configuration);
		Assert.assertEquals(PictureContainer.JPEG, videoConfig.getContainer());
		Assert.assertEquals(PictureResolution.HD720P, videoConfig.getResolution());
	}
	
	@Test(expected=Exception.class)
	public void test_incorrectContainer() throws Exception {
		PictureConfigParser parser = new PictureConfigParser();
		PictureConfiguration configuration = new PictureConfiguration();
		configuration.setResolution("VGA");
		configuration.setContainer("EEE");
		parser.parsePictureConfig(configuration);
	}
	
	@Test(expected=Exception.class)
	public void test_incorrectResolution() throws Exception {
		PictureConfigParser parser = new PictureConfigParser();
		PictureConfiguration configuration = new PictureConfiguration();
		configuration.setResolution("JJJJ");
		configuration.setContainer("JPEG");
		parser.parsePictureConfig(configuration);
	}
}
