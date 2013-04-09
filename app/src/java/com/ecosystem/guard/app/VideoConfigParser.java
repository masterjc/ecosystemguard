package com.ecosystem.guard.app;

import com.ecosystem.guard.camera.Codec;
import com.ecosystem.guard.camera.Container;
import com.ecosystem.guard.camera.Resolution;
import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.impl.VideoCodec;
import com.ecosystem.guard.camera.impl.VideoContainer;
import com.ecosystem.guard.domain.service.host.VideoConfiguration;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfigParser {

	public VideoConfig parseVideoConfig(VideoConfiguration config) throws Exception {
		Container container = parseContainer(config);
		Codec codec = parseCodec(config);
		Resolution res = parseResolution(config, codec);
		VideoConfig videoConfig = new VideoConfig();
		videoConfig.setContainer(container);
		videoConfig.setVideoCodec(codec);
		videoConfig.setOptionalOptions(codec.getCodecOptions());
		videoConfig.setResolution(res);
		return videoConfig;
	}

	private Container parseContainer(VideoConfiguration videoConfig) throws Exception {
		if (videoConfig.getContainer() == null)
			throw new Exception("Container configuration is not present");
		String requested = videoConfig.getContainer();
		Container container = VideoContainer.valueOf(requested);
		if (container == null)
			throw new Exception("Container '" + requested + "' is not supported");
		return container;
	}

	private Codec parseCodec(VideoConfiguration videoConfig) throws Exception {
		if (videoConfig.getCodec() == null)
			throw new Exception("Codec configuration is not present");
		String requested = videoConfig.getCodec();
		Codec codec = VideoCodec.valueOf(requested);
		if (codec == null)
			throw new Exception("Codec '" + requested + "' is not supported");
		return codec;
	}

	private Resolution parseResolution(VideoConfiguration videoConfig, Codec usedCodec) throws Exception {
		if (videoConfig.getResolution() == null)
			throw new Exception("Resolution configuration is not present");
		String requested = videoConfig.getResolution();
		Resolution[] resolutions = usedCodec.getResolutionClass().getEnumConstants();
		for (int i = 0; i < resolutions.length; i++) {
			if (resolutions[i].getAbbreviation().equals(requested))
				return resolutions[i];
		}
		throw new Exception("Resolution '" + requested + "' is not supported");
	}

}
