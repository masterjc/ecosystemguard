package com.ecosystem.guard.app;

import com.ecosystem.guard.camera.Container;
import com.ecosystem.guard.camera.PictureConfig;
import com.ecosystem.guard.camera.Resolution;
import com.ecosystem.guard.camera.impl.PictureContainer;
import com.ecosystem.guard.camera.impl.PictureResolution;
import com.ecosystem.guard.domain.service.app.PictureConfiguration;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PictureConfigParser {

	public PictureConfig parsePictureConfig(PictureConfiguration config) throws Exception {
		Container container = parseContainer(config);
		Resolution res = parseResolution(config);
		PictureConfig pictureConfig = new PictureConfig();
		pictureConfig.setContainer(container);
		pictureConfig.setResolution(res);
		return pictureConfig;
	}

	private Container parseContainer(PictureConfiguration pictureConfig) throws Exception {
		if (pictureConfig.getContainer() == null)
			throw new Exception("Container configuration is not present");
		String requested = pictureConfig.getContainer();
		Container container = PictureContainer.valueOf(requested);
		if (container == null)
			throw new Exception("Container '" + requested + "' is not supported");
		return container;
	}

	private Resolution parseResolution(PictureConfiguration pictureConfig) throws Exception {
		if (pictureConfig.getResolution() == null)
			throw new Exception("Resolution configuration is not present");
		String requested = pictureConfig.getResolution();
		Resolution res = PictureResolution.valueOf(requested);
		if (res == null)
			throw new Exception("Resolution '" + requested + "' is not supported");
		return res;
	}

}
