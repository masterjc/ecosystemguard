package com.ecosystem.guard.app;

import java.util.ArrayList;
import java.util.List;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.impl.H263Resolution;
import com.ecosystem.guard.camera.impl.H264Resolution;
import com.ecosystem.guard.camera.impl.VideoCodec;
import com.ecosystem.guard.camera.impl.VideoContainer;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class VideoConfigParser {
	private Class<?> codecClass;
	private Class<?> containerClass;
	private List<Class<?>> resolutionClasses;
	
	public VideoConfigParser() {
		resolutionClasses = new ArrayList<Class<?>>();
		this.resolutionClasses.add(H263Resolution.class);
		this.resolutionClasses.add(H264Resolution.class);
		this.codecClass = VideoCodec.class;
		this.containerClass = VideoContainer.class;
	}
	
	public VideoConfig parseVideoConfig() {
		
		return null;
	}
}
