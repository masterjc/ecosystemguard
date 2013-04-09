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
 * Controlador de cámara único que controla tanto qué implementación se utiliza para acceder a la
 * cámara como el acceso concurrente a la cámara.
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class CameraControllerFactory {
	private static CameraController cameraController;
	private static Boolean inUse = false;
	private static final Integer creationlock = 0;
	private static final Integer accessLock = 0;

	public static CameraController acquireCameraController() throws Exception {
		if (cameraController == null) {
			synchronized (creationlock) {
				if (cameraController == null) {
					cameraController = new FFMpegCameraController();
				}
			}
		}
		if (!inUse) {
			synchronized (accessLock) {
				if (!inUse) {
					inUse = true;
					return cameraController;
				}
			}
		}
		throw new Exception("CameraController in use. Wait for camera liberation");
	}

	public static void releaseCameraController() {
		if (inUse) {
			synchronized (accessLock) {
				if (inUse) {
					inUse = false;
				}
			}
		}
	}

}
