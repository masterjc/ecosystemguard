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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.ecosystem.guard.camera.CameraController;
import com.ecosystem.guard.camera.VideoManager;
import com.ecosystem.guard.camera.impl.FFMpegCameraController;
import com.ecosystem.guard.camera.impl.HackberryH264VideoConfig;
import com.ecosystem.guard.common.FileStreamPump;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamingUtils;
import com.ecosystem.guard.domain.service.host.TakePictureRequest;
import com.ecosystem.guard.domain.service.host.TakePictureResponse;
import com.ecosystem.guard.engine.authn.AuthenticationContext;
import com.ecosystem.guard.engine.servlet.NonTransactionalService;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */

public class TakePictureService extends NonTransactionalService<TakePictureRequest, TakePictureResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9135833740206495900L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File videoFile = new File("ecosystem" + Hex.encodeHexString(RandomGenerator.generateRandom(16)) + ".mp4");
		
		try {
			System.out.println("doGet::TakePicture");
			CameraController controller = new FFMpegCameraController();
			VideoManager videoManager = controller.createVideoManager();
			response.setContentType("video/mp4");
			videoManager.record(new HackberryH264VideoConfig(), 10, videoFile);
			Thread.sleep(500);
			FileInputStream input = new FileInputStream(videoFile);
			StreamingUtils.consumeStream(input, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*if( videoFile.exists())
				videoFile.delete();*/
		}

		/*
		 * FileInputStream stream = new
		 * FileInputStream("/root/ecosystemguard/scripts/video.3gp"); try {
		 * OutputStream outputStream = response.getOutputStream();
		 * response.setContentType("video/3gp"); try {
		 * StreamingUtils.consumeStream(stream, outputStream); } catch
		 * (Exception e) { throw new ServletException(e); } } finally {
		 * stream.close(); }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.NonTransactionalService#execute(com
	 * .ecosystem.guard.engine .authn.AuthenticationContext,
	 * com.ecosystem.guard.domain.Request, java.io.Writer)
	 */
	@Override
	protected void execute(AuthenticationContext arg0, TakePictureRequest arg1, Writer arg2) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass
	 * ()
	 */
	@Override
	protected Class<TakePictureRequest> getRequestJaxbClass() {
		return TakePictureRequest.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.AuthenticatedService#getResponseJaxbClass
	 * ()
	 */
	@Override
	protected Class<TakePictureResponse> getResponseJaxbClass() {
		return TakePictureResponse.class;
	}

}
