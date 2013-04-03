/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.VideoCodec;
import com.ecosystem.guard.camera.VideoConfig;

/**
 * Configuración por defecto de baja resolución. QVGA, 30 fps, bitrate 200k y codec H264 (MP4)
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class HackberryH264VideoConfig extends VideoConfig {

	public HackberryH264VideoConfig() {
		super.setResolution(H264Resolution.HVGA);
		super.setVideoCodec(VideoCodec.H264);
		super.setOptionalOptions("-preset veryfast -crf 28");
		super.setContainerExtension("mp4");
		
	}
}
