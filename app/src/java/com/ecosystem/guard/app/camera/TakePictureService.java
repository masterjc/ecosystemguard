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

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */

public class TakePictureService extends AuthorizedRawPostService<TakePictureRequest> {
	private static final long serialVersionUID = -8943531131351756829L;

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
		try {
			pictureManager.takePicture(pictureConfig, picFile);
			response.setContentType(pictureConfig.getContainer().getContentType());
			StreamingUtils.consumeFileStream(picFile, response.getOutputStream());
		}
		finally {
			CameraControllerFactory.releaseCameraController();
			if (picFile.exists()) {
				picFile.delete();
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
