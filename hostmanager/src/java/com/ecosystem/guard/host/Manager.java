package com.ecosystem.guard.host;

import java.util.Scanner;

public class Manager {
	private enum MainOptionFunction {
		ACCOUNT_SETTINGS, REGISTER, UNREGISTER, GETIP, EXIT;
	}

	private enum RegistryOptionFunction {
		CREATE_ACCOUNT, DELETE_ACCOUNT, CHANGE_PASSWORD, BACK;
	}

	private ManagerConfig managerConfig;
	private HostConfigurator hostConfigurator;
	private AccountManager accountManager;
	private HostRegistryManager hostRegistryManager;
	private IpManager ipManager;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		Manager manager = new Manager(args);
		manager.execute();
	}

	public Manager(String[] args) throws Exception {
		managerConfig = new ManagerConfig(args);
		hostConfigurator = new HostConfigurator(managerConfig);
		accountManager = new AccountManager(hostConfigurator);
		hostRegistryManager = new HostRegistryManager(hostConfigurator);
		ipManager = new IpManager(hostConfigurator);
	}

	/**
	 * Main loop
	 */
	public void execute() {
		boolean exit = false;
		while (!exit) {
			try {
				exit = processMainOptions();
			}
			catch (Exception e) {
				if (managerConfig.isDebug()) {
					e.printStackTrace();
				}
				ManagerOutput.printSeparatorLine();
				if (!e.getClass().getCanonicalName().equals(Exception.class.getCanonicalName())) {
					System.out.println("ERROR: " + e.getClass().getCanonicalName() + ": " + e.getMessage());
				}
				else {
					System.out.println("ERROR: " + e.getMessage());
				}
				ManagerOutput.printSeparatorLine();
			}
		}
	}
	
	

	private boolean processMainOptions() throws Exception {
		ManagerOutput.printLogo();
		ManagerOutput.printRegistrationInfo(hostConfigurator);
		OptionSelections<MainOptionFunction> mainOptions = showMainOptions();
		System.out.println();
		System.out.print("-> Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		MainOptionFunction optionSelected = mainOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		switch (optionSelected) {
		case ACCOUNT_SETTINGS:
			accountSettings();
			break;
		case REGISTER:
			hostRegistryManager.registerHost();
			break;
		case UNREGISTER:
			hostRegistryManager.unregisterHost();
			break;
		case GETIP:
			ipManager.showPublicIpInformation();
			break;
		case EXIT:
			return true;
		}
		return false;
	}

	private void accountSettings() throws Exception {
		ManagerOutput.printLogo();
		OptionSelections<RegistryOptionFunction> registryOptions = showRegistryOptions();
		System.out.println();
		System.out.print("-> Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		RegistryOptionFunction optionSelected = registryOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		switch (optionSelected) {
		case CREATE_ACCOUNT:
			accountManager.createAccount();
			break;
		case DELETE_ACCOUNT:
			accountManager.deleteAccount();
			break;
		case CHANGE_PASSWORD:
			accountManager.changePassword();
			break;
		case BACK:
			return;
		}
	}

	private OptionSelections<MainOptionFunction> showMainOptions() {
		System.out.println();
		OptionSelections<MainOptionFunction> selection = new OptionSelections<MainOptionFunction>();
		int option = 1;
		System.out.println(option + ". Account management");
		selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.ACCOUNT_SETTINGS));
		if (!hostConfigurator.hasCredentials()) {
			System.out.println(option + ". Associate host with an existing account");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.REGISTER));
		}
		else {
			System.out.println(option + ". Disassociate host with current account");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.UNREGISTER));
			System.out.println(option + ". Show public IP Address information");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.GETIP));
		}
		System.out.println(option + ". Exit");
		selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.EXIT));
		return selection;
	}

	private OptionSelections<RegistryOptionFunction> showRegistryOptions() {
		System.out.println();
		System.out.println("Account Management");
		System.out.println("------------------");
		OptionSelections<RegistryOptionFunction> selection = new OptionSelections<RegistryOptionFunction>();
		int option = 1;
		System.out.println(option + ". Create Account");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.CREATE_ACCOUNT));
		System.out.println(option + ". Change Account Password");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.CHANGE_PASSWORD));
		System.out.println(option + ". Delete Account");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.DELETE_ACCOUNT));
		System.out.println(option + ". Back");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.BACK));
		return selection;
	}

}
