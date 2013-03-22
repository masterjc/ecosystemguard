package com.ecosystem.guard.camera;

/**
 * Supported EcosystemGuard resolutions
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public enum Resolution {
	QVGA(320, 240, "QVGA"), VGA(640, 480, "VGA"), HVGA(480, 320, "HVGA"), SVGA(800, 600, "SVGA");

	private int xRes;
	private int yRes;
	private String abbreviation;

	private Resolution(int xResolution, int yResolution, String resolutionAbbreviation) {
		xRes = xResolution;
		yRes = yResolution;
		abbreviation = resolutionAbbreviation;
	}

	public int getXRes() {
		return xRes;
	}

	public int getYRes() {
		return yRes;
	}

	public String getResolution() {
		return Integer.toString(xRes) + "x" + Integer.toString(yRes);
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static Resolution parseResolution(String abbreviation) throws Exception {
		Resolution[] resolutions = Resolution.values();
		for (int i = 0; i < resolutions.length; i++) {
			if (resolutions[i].getAbbreviation().equals(abbreviation))
				return resolutions[i];
		}
		throw new Exception("Unknow resolution abbreviation " + abbreviation);
	}
}
