package com.ecosystem.guard.app.camera;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.ecosystem.guard.camera.CameraControllerFactory;
import com.ecosystem.guard.camera.PictureConfig;
import com.ecosystem.guard.camera.PictureManager;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamingUtils;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.exceptions.ServiceException;
import com.ecosystem.guard.domain.service.app.TakePictureRequest;
import com.ecosystem.guard.engine.authn.AuthorizationContext;
import com.ecosystem.guard.engine.servlet.AuthorizedRawPostService;
import com.ecosystem.guard.phidgets.SensorManager;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */

public class TakePictureService extends AuthorizedRawPostService<TakePictureRequest> {
	private static final long serialVersionUID = -8943531131351756829L;

	private static final String SERVICE_MESSAGE = "Picture generator:";
	private static final String TAKING_SNAPSHOT_MESSAGE = "Taking snapshot...";
	private static final String SENDING_SNAPSHOT_MESSAGE = "Sending snapshot...";
	private static final String SENT_SNAPSHOT_MESSAGE = "Snapshot sent!";
	private static final String ERROR_SNAPSHOT_MESSAGE = "*** ERROR ***";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecosystem.guard.engine.servlet.AuthorizedRawPostService#execute(com.ecosystem.guard.engine
	 * .authn.AuthorizationContext, com.ecosystem.guard.domain.Request,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void execute(AuthorizationContext authnContext, TakePictureRequest request, HttpServletResponse response)
			throws Exception {
		if (request.getPictureConfiguration() == null)
			throw new ServiceException(new Result(Result.Status.CLIENT_ERROR, "Missing picture config information"));
		PictureManager pictureManager = CameraControllerFactory.acquireCameraController().createPictureManager();
		PictureConfigParser configParser = new PictureConfigParser();
		PictureConfig pictureConfig = configParser.parsePictureConfig(request.getPictureConfiguration());
		File picFile = new File(Hex.encodeHexString(RandomGenerator.generateRandom(8))
				+ pictureConfig.getContainer().getExtension());
		LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
		boolean error = false;
		try {
			lcd.showMessage(TAKING_SNAPSHOT_MESSAGE, pictureConfig.getResolution().getAbbreviation() + " - " + pictureConfig.getResolution().getResolution());
			pictureManager.takePicture(pictureConfig, picFile);
			lcd.showMessage(SENDING_SNAPSHOT_MESSAGE, pictureConfig.getResolution().getAbbreviation() + " - " + pictureConfig.getResolution().getResolution());
			response.setContentType(pictureConfig.getContainer().getContentType());
			StreamingUtils.consumeFileStream(picFile, response.getOutputStream());
		} catch( Exception e ) {
			error = true;
			throw e;
		} finally {
			CameraControllerFactory.releaseCameraController();
			if (picFile.exists()) {
				picFile.delete();
			}
			if( error ) {
				lcd.showAsynchronousIntermitentMessage(SERVICE_MESSAGE, SENT_SNAPSHOT_MESSAGE, 5);
			} else {
				lcd.showAsynchronousMessage(SERVICE_MESSAGE, ERROR_SNAPSHOT_MESSAGE, 5000);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecosystem.guard.engine.servlet.AuthenticatedService#getRequestJaxbClass ()
	 */
	@Override
	protected Class<TakePictureRequest> getRequestJaxbClass() {
		return TakePictureRequest.class;
	}

}
