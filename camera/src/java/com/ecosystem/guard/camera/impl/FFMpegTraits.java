package com.ecosystem.guard.camera.impl;

import java.io.InputStream;

/**
 * Características del comando ffmpeg así como utilidades
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class FFMpegTraits {
	public static final String FFMPEG_EXEC = "ffmpeg";
	public static final String CAPTURE_DRIVER = "video4linux2";

	/**
	 * Parsea la salida estándar y de error de ffmpeg para detectar si ha funcionado bien el comando
	 * o no
	 * 
	 * @param ffmpegOutput
	 * @param ffmpegErrorOutput
	 * @throws Exception
	 */
	public static void checkAndThrowFFMpegError(InputStream ffmpegOutput, InputStream ffmpegErrorOutput) throws Exception {
		// byte[] output = StreamingUtils.consumeInputStream(ffmpegOutput);
		// byte[] errorOutput = StreamingUtils.consumeInputStream(ffmpegErrorOutput);
	}

}
