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

import com.ecosystem.guard.camera.CameraController;
import com.ecosystem.guard.camera.impl.FFMpegCameraController;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CameraControllerFactory {
	private static CameraController cameraController;
	private static final Integer lock = 0;
	
	public static CameraController getCameraController() throws Exception {
		if( cameraController == null ) {
			synchronized (lock) {
				if( cameraController == null ) {
					cameraController = new FFMpegCameraController();
				}
			}
		}
		return cameraController;
	}

}
