package com.ecosystem.guard.pc;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import com.ecosystem.guard.camera.impl.PictureContainer;
import com.ecosystem.guard.camera.impl.PictureResolution;
import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.common.StreamServiceRequestor;
import com.ecosystem.guard.domain.service.app.PictureConfiguration;
import com.ecosystem.guard.domain.service.app.TakePictureRequest;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PictureManager {
	private Scanner scanner = new Scanner(System.in);

	public void takePicture(Session session) throws Exception {
		ClientOutput.printLogo();
		OptionSelections<PictureResolution> registryOptions = showPictureResolutions();
		System.out.println();
		System.out.print("-> Select picture resolution: ");
		int selection = Integer.parseInt(scanner.nextLine());
		PictureResolution optionSelected = registryOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect resolution selected - " + selection);
		TakePictureRequest request = new TakePictureRequest();
		request.setCredentials(session.getCredentials());
		PictureConfiguration config = new PictureConfiguration();
		config.setResolution(optionSelected.getAbbreviation());
		config.setContainer(PictureContainer.JPEG.name());
		request.setPictureConfiguration(config);
		File picture = new File(RandomGenerator.generateRandom(20) + "." + PictureContainer.JPEG.name());
		try {
			ClientOutput.printSeparatorLine();
			System.out.print("** Taking picture from your EcosystemGuard host...");
			FileOutputStream outputPic = new FileOutputStream(picture);
			StreamServiceRequestor.sendRequest(request, TakePictureRequest.class, session.getAppUrl()
					+ ClientConstants.TAKE_PICTURE_SERVICE, outputPic);
			outputPic.close();
			System.out.println("OK");
			Desktop.getDesktop().open(picture);
			System.out.println("** Picture '" + picture.getName() + "'should be open with your default JPEG desktop application.");
		}
		finally {
			picture.deleteOnExit();
			ClientOutput.printSeparatorLine();
		}
	}

	private OptionSelections<PictureResolution> showPictureResolutions() {
		System.out.println();
		System.out.println("Picture resolution selection");
		System.out.println("----------------------------");
		OptionSelections<PictureResolution> selection = new OptionSelections<PictureResolution>();
		PictureResolution[] picResolutions = PictureResolution.values();
		int option = 1;
		for (int i = 0; i < picResolutions.length; i++) {
			PictureResolution picResolution = picResolutions[i];
			System.out.println(option + ". " + picResolution.getAbbreviation() + " (" + picResolution.getResolution()
					+ ")");
			selection.add(new OptionSelection<PictureResolution>(option++, picResolution));
		}
		return selection;
	}

}
