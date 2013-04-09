package com.ecosystem.guard.app;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.VideoManager;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamingUtils;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.host.RecordVideoRequest;
import com.ecosystem.guard.engine.authn.AuthorizationContext;
import com.ecosystem.guard.engine.servlet.AuthorizedRawPostService;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RecordVideoService extends AuthorizedRawPostService<RecordVideoRequest> {
	private static VideoConfigParser videoConfigParser = new VideoConfigParser();
	private static final long serialVersionUID = 1947515630361657851L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedRawPostService#execute
	 * (com.ecosystem.guard .engine.authn.AuthenticationContext, com.ecosystem.guard.domain.Request,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(AuthorizationContext authn, RecordVideoRequest request, HttpServletResponse response)
			throws Exception {
		if (request.getLength() == null || request.getVideoConfiguration() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing video config information"));
		VideoManager videoManager = CameraControllerFactory.acquireCameraController().createVideoManager();
		VideoConfig videoConfig = videoConfigParser.parseVideoConfig(request.getVideoConfiguration());
		File videoFile = new File(Hex.encodeHexString(RandomGenerator.generateRandom(8))
				+ videoConfig.getContainer().getExtension());
		try {
			videoManager.record(videoConfig, request.getLength(), videoFile);
			response.setContentType(videoConfig.getContainer().getContentType());
			StreamingUtils.consumeFileStream(videoFile, response.getOutputStream());
		}
		finally {
			CameraControllerFactory.releaseCameraController();
			if (videoFile.exists()) {
				videoFile.delete();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedRawPostService# getRequestJaxbClass()
	 */
	@Override
	protected Class<RecordVideoRequest> getRequestJaxbClass() {
		return RecordVideoRequest.class;
	}

}
