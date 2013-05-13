package com.ecosystem.guard.pc;

import java.util.Scanner;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class SensorManager {
	private Scanner scanner = new Scanner(System.in);
	private PictureManager picManager = new PictureManager();
	

	private enum SensorsOptionFunction {
		TAKE_PICTURE, RECORD_VIDEO, BACK;
	}

	public void execute(Session session) throws Exception {
		ClientOutput.printLogo();
		OptionSelections<SensorsOptionFunction> registryOptions = showMainOptions();
		System.out.println();
		System.out.print("-> Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		SensorsOptionFunction optionSelected = registryOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		switch (optionSelected) {
		case TAKE_PICTURE:
			picManager.takePicture(session);
			break;
		case RECORD_VIDEO:
			break;
		case BACK:
		}
	}

	private OptionSelections<SensorsOptionFunction> showMainOptions() {
		System.out.println();
		System.out.println("Sensors Management");
		System.out.println("------------------");
		OptionSelections<SensorsOptionFunction> selection = new OptionSelections<SensorsOptionFunction>();
		int option = 1;
		System.out.println(option + ". Take a picture");
		selection.add(new OptionSelection<SensorsOptionFunction>(option++, SensorsOptionFunction.TAKE_PICTURE));
		System.out.println(option + ". Record a video");
		selection.add(new OptionSelection<SensorsOptionFunction>(option++, SensorsOptionFunction.RECORD_VIDEO));
		System.out.println(option + ". Back");
		selection.add(new OptionSelection<SensorsOptionFunction>(option++, SensorsOptionFunction.BACK));
		return selection;
	}
}
