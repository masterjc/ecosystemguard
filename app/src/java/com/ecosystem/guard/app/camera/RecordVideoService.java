package com.ecosystem.guard.app.camera;

import java.io.File;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.ecosystem.guard.camera.CameraControllerFactory;
import com.ecosystem.guard.camera.VideoConfig;
import com.ecosystem.guard.camera.VideoManager;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamingUtils;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.app.RecordVideoRequest;
import com.ecosystem.guard.engine.authn.AuthorizationContext;
import com.ecosystem.guard.engine.servlet.AuthorizedRawPostService;
import com.ecosystem.guard.phidgets.SensorManager;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RecordVideoService extends AuthorizedRawPostService<RecordVideoRequest> {
	private static final long serialVersionUID = 1947515630361657851L;
	
	private static final String SERVICE_MESSAGE = "Video Recoder:";
	private static final String RECORDING_VIDEO_MESSAGE = "Recording video...";
	private static final String SENDING_VIDEO_MESSAGE = "Sending video...";
	private static final String SENT_VIDEO_MESSAGE = "Video sent!";
	private static final String ERROR_VIDEO_MESSAGE = "*** ERROR ***";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedRawPostService#execute
	 * (com.ecosystem.guard .engine.authn.AuthenticationContext, com.ecosystem.guard.domain.Request,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(AuthorizationContext authn, RecordVideoRequest request, HttpServletResponse response, OutputStream outStream)
			throws Exception {
		if (request.getLength() == null || request.getVideoConfiguration() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing video config information"));
		VideoManager videoManager = CameraControllerFactory.acquireCameraController().createVideoManager();
		VideoConfigParser videoConfigParser = new VideoConfigParser();
		VideoConfig videoConfig = videoConfigParser.parseVideoConfig(request.getVideoConfiguration());
		File videoFile = new File(Hex.encodeHexString(RandomGenerator.generateRandom(8))
				+ videoConfig.getContainer().getExtension());
		LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
		boolean error = false;
		try {
			lcd.showMessage(RECORDING_VIDEO_MESSAGE, videoConfig.getContainer().getContentType() + " - " + request.getLength().intValue() + "s");
			videoManager.record(videoConfig, request.getLength(), videoFile);
			lcd.showMessage(SENDING_VIDEO_MESSAGE, videoConfig.getContainer().getContentType() + " - " + request.getLength().intValue() + "s");
			response.setContentType(videoConfig.getContainer().getContentType());
			StreamingUtils.consumeFileStream(videoFile, outStream);
		} catch( Exception e ) {
			error = true;
			throw e;
		} finally {
			CameraControllerFactory.releaseCameraController();
			if (videoFile.exists()) {
				videoFile.delete();
			}
			if( error ) {
				lcd.showAsynchronousIntermitentMessage(SERVICE_MESSAGE, ERROR_VIDEO_MESSAGE, 5);
			} else {
				lcd.showAsynchronousMessage(SERVICE_MESSAGE, SENT_VIDEO_MESSAGE, 5000);
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
