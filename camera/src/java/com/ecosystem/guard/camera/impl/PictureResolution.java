package com.ecosystem.guard.camera.impl;

import com.ecosystem.guard.camera.Resolution;

/**
 * Supported EcosystemGuard resolutions
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum PictureResolution implements Resolution {
	QVGA(320, 240, "QVGA"), VGA(640, 480, "VGA"), HVGA(480, 320, "HVGA"), SVGA(800, 600, "SVGA"), HD720P(1280, 720, "HD720P");

	private int xRes;
	private int yRes;
	private String abbreviation;

	private PictureResolution(int xResolution, int yResolution, String resolutionAbbreviation) {
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
		PictureResolution[] resolutions = PictureResolution.values();
		for (int i = 0; i < resolutions.length; i++) {
			if (resolutions[i].getAbbreviation().equals(abbreviation))
				return resolutions[i];
		}
		throw new Exception("Unknow resolution abbreviation " + abbreviation);
	}
}
