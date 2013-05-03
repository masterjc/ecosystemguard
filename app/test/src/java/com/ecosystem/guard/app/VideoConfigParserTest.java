package com.ecosystem.guard.app;

import junit.framework.Assert;

import org.junit.Test;

import com.ecosystem.guard.app.camera.VideoConfigParser;
import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.impl.H263Resolution;
import com.ecosystem.guard.camera.impl.H264Resolution;
import com.ecosystem.guard.camera.impl.VideoCodec;
import com.ecosystem.guard.camera.impl.VideoContainer;
import com.ecosystem.guard.domain.service.app.VideoConfiguration;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfigParserTest {

	@Test
	public void test_H264_MP4_VGA_Parse() throws Exception {
		VideoConfigParser parser = new VideoConfigParser();
		VideoConfiguration configuration = new VideoConfiguration();
		configuration.setCodec("H264");
		configuration.setResolution("VGA");
		configuration.setContainer("MP4");
		VideoConfig videoConfig = parser.parseVideoConfig(configuration);
		Assert.assertEquals(VideoContainer.MP4, videoConfig.getContainer());
		Assert.assertEquals(VideoCodec.H264, videoConfig.getVideoCodec());
		Assert.assertEquals(H264Resolution.VGA, videoConfig.getResolution());
	}
	
	@Test
	public void test_H263_MP4_VGA_Parse() throws Exception {
		VideoConfigParser parser = new VideoConfigParser();
		VideoConfiguration configuration = new VideoConfiguration();
		configuration.setCodec("H263");
		configuration.setResolution("VGA");
		configuration.setContainer("MP4");
		VideoConfig videoConfig = parser.parseVideoConfig(configuration);
		Assert.assertEquals(VideoContainer.MP4, videoConfig.getContainer());
		Assert.assertEquals(VideoCodec.H263, videoConfig.getVideoCodec());
		Assert.assertEquals(H263Resolution.VGA, videoConfig.getResolution());
	}
	
	@Test
	public void test_H263_3GP_VGA_Parse() throws Exception {
		VideoConfigParser parser = new VideoConfigParser();
		VideoConfiguration configuration = new VideoConfiguration();
		configuration.setCodec("H263");
		configuration.setResolution("VGA");
		configuration.setContainer("THREE_GP");
		VideoConfig videoConfig = parser.parseVideoConfig(configuration);
		Assert.assertEquals(VideoContainer.THREE_GP, videoConfig.getContainer());
		Assert.assertEquals(VideoCodec.H263, videoConfig.getVideoCodec());
		Assert.assertEquals(H263Resolution.VGA, videoConfig.getResolution());
	}
	
	@Test(expected=Exception.class)
	public void test_incorrectCodec() throws Exception {
		VideoConfigParser parser = new VideoConfigParser();
		VideoConfiguration configuration = new VideoConfiguration();
		configuration.setCodec("H2113");
		configuration.setResolution("VGA");
		configuration.setContainer("THREE_GP");
		parser.parseVideoConfig(configuration);
	}
	
	@Test(expected=Exception.class)
	public void test_incorrectContainer() throws Exception {
		VideoConfigParser parser = new VideoConfigParser();
		VideoConfiguration configuration = new VideoConfiguration();
		configuration.setCodec("H263");
		configuration.setResolution("VGA");
		configuration.setContainer("aaa_GP");
		parser.parseVideoConfig(configuration);
	}
	
	@Test(expected=Exception.class)
	public void test_incorrectResolution() throws Exception {
		VideoConfigParser parser = new VideoConfigParser();
		VideoConfiguration configuration = new VideoConfiguration();
		configuration.setCodec("H263");
		configuration.setResolution("vvv");
		configuration.setContainer("THREE_GP");
		parser.parseVideoConfig(configuration);
	}
}
