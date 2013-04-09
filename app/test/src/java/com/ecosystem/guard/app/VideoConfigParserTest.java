package com.ecosystem.guard.app;

import junit.framework.Assert;

import org.junit.Test;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.impl.H264Resolution;
import com.ecosystem.guard.camera.impl.VideoCodec;
import com.ecosystem.guard.camera.impl.VideoContainer;
import com.ecosystem.guard.domain.service.host.VideoConfiguration;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfigParserTest {

	
	@Test
	public void testParse() throws Exception {
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
}
