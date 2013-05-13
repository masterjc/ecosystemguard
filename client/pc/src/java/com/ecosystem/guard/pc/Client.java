package com.ecosystem.guard.pc;

import java.util.Scanner;


public class Client {
	private Scanner scanner = new Scanner(System.in);
	
	private enum MainOptionFunction {
		LOGIN, LOGOUT, SENSOR_MANAGEMENT, HOST_CONFIGURATION, ACCOUNT_MANAGEMENT, EXIT;
	}
	
	private ClientConfig clientConfig;
	private LoginManager loginManager = new LoginManager();
	private AccountManager accountManager = new AccountManager();
	private SensorManager sensorManager = new SensorManager();
	private Session session;
	
	public static void main(String[] args) throws Exception {
		Client client = new Client(args);
		client.execute();
	}

	public Client(String[] args) throws Exception {
		clientConfig = new ClientConfig(args);
	}

	/**
	 * Main loop
	 */
	public void execute() {
		boolean exit = false;
		while (!exit) {
			try {
				exit = mainOptions();
				if( session != null) {
					System.out.println("DEBUG DATA");
					System.out.println(session.getCredentials().getUsernamePassword().getUsername());
					System.out.println(session.getAppIpAddress());
					System.out.println(session.getHostInformation().getId());
				}
			}
			catch (Exception e) {
				if (clientConfig.isDebug()) {
					e.printStackTrace();
				}
				ClientOutput.printSeparatorLine();
				if (!e.getClass().getCanonicalName().equals(Exception.class.getCanonicalName())) {
					System.out.println("ERROR: " + e.getClass().getCanonicalName() + ": " + e.getMessage());
				}
				else {
					System.out.println("ERROR: " + e.getMessage());
				}
				ClientOutput.printSeparatorLine();

				if (session == null) {
					exit = true;
				}
			}
		}
	}
	
	private boolean mainOptions() throws Exception {
		ClientOutput.printLogo();
		ClientOutput.printAccountInfo(session);
		OptionSelections<MainOptionFunction> registryOptions = showMainOptions();
		System.out.println();
		System.out.print("-> Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		MainOptionFunction optionSelected = registryOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		switch (optionSelected) {
		case LOGIN:
			session = loginManager.startSession();
			break;
		case LOGOUT:
			session = null;
			break;
		case SENSOR_MANAGEMENT:
			sensorManager.execute(session);
			break;
		case ACCOUNT_MANAGEMENT:
			accountManager.execute();
			break;
		case EXIT:
			return true;
		}
		return false;
	}

	private OptionSelections<MainOptionFunction> showMainOptions() {
		System.out.println();
		OptionSelections<MainOptionFunction> selection = new OptionSelections<MainOptionFunction>();
		int option = 1;
		if( session == null ) {
			System.out.println(option + ". Login into your Ecosystemguard account");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.LOGIN));
		}
		System.out.println(option + ". Manage EcosystemGuard accounts");
		selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.ACCOUNT_MANAGEMENT));
		if( session != null ) {
			System.out.println(option + ". EcosystemGuard host sensors management");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.SENSOR_MANAGEMENT));
			System.out.println(option + ". EcosystemGuard host configuration");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.HOST_CONFIGURATION));
			System.out.println(option + ". Logout of Ecosystemguard account");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.LOGOUT));
		}
		System.out.println(option + ". Exit");
		selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.EXIT));
		return selection;
	}
	
	

}

