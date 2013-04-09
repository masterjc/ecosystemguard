package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Resolution;

/**
 * Supported EcosystemGuard resolutions
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum H263Resolution implements Resolution {
	QVGA(352, 288, "QVGA"), VGA(704, 576, "VGA"), SVGA(1408, 1152, "SVGA");

	private int xRes;
	private int yRes;
	private String abbreviation;

	private H263Resolution(int xResolution, int yResolution, String resolutionAbbreviation) {
		xRes = xResolution;
		yRes = yResolution;
		abbreviation = resolutionAbbreviation;
	}

	@Override
	public int getXRes() {
		return xRes;
	}

	@Override
	public int getYRes() {
		return yRes;
	}

	@Override
	public String getResolution() {
		return Integer.toString(xRes) + "x" + Integer.toString(yRes);
	}

	@Override
	public String getAbbreviation() {
		return abbreviation;
	}

	public static Resolution parseResolution(String abbreviation) throws Exception {
		H263Resolution[] resolutions = H263Resolution.values();
		for (int i = 0; i < resolutions.length; i++) {
			if (resolutions[i].getAbbreviation().equals(abbreviation))
				return resolutions[i];
		}
		throw new Exception("Unknow resolution abbreviation " + abbreviation);
	}
}
