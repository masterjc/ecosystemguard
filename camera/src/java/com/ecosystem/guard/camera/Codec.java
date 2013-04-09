package com.ecosystem.guard.camera;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public interface Codec {
	public String getCodec();
	public String getCodecOptions();
	public Class<? extends Resolution> getResolutionClass(); 
}
