/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.app;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.VideoManager;
import com.ecosystem.guard.camera.impl.HackberryH264VideoConfig;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamingUtils;
import com.ecosystem.guard.domain.service.host.RecordVideoRequest;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.AuthenticatedRawPostService;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RecordVideoService extends AuthenticatedRawPostService<RecordVideoRequest> {
	private static final long serialVersionUID = 1947515630361657851L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.AuthenticatedRawPostService#execute(com.ecosystem.guard
	 * .engine.authn.AuthenticationContext, com.ecosystem.guard.domain.Request,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(AuthenticationContext authn, RecordVideoRequest request, HttpServletResponse response)
			throws Exception {
		// Cosas que deben venir por petición
		int videoLength = 10;
		VideoConfig videoConfig = new HackberryH264VideoConfig();
		String contentType = "video/mp4";
		// Código fijo
		File videoFile = new File(Hex.encodeHexString(RandomGenerator.generateRandom(16)) + ".mp4");
		try {
			VideoManager videoManager = CameraControllerFactory.getCameraController().createVideoManager();
			videoManager.record(videoConfig, videoLength, videoFile);
			response.setContentType(contentType);
			StreamingUtils.consumeFileStream(videoFile, response.getOutputStream());
		}
		finally {
			if (videoFile.exists()) {
				videoFile.delete();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedRawPostService#getRequestJaxbClass()
	 */
	@Override
	protected Class<RecordVideoRequest> getRequestJaxbClass() {
		return RecordVideoRequest.class;
	}

}
