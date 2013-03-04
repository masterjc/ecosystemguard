package com.ecosystem.guard.host;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ecosystem.guard.common.RandomGenerator;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Deserializer;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Serializer;
import com.ecosystem.guard.domain.config.HostConfig;
import com.ecosystem.guard.domain.service.AccountInformation;
import com.ecosystem.guard.domain.service.RegisterRequest;
import com.ecosystem.guard.domain.service.RegisterResponse;
import com.ecosystem.guard.domain.service.UnregisterRequest;
import com.ecosystem.guard.domain.service.UnregisterResponse;
import com.ecosystem.guard.domain.service.UpdateCredentialsRequest;
import com.ecosystem.guard.domain.service.UpdateCredentialsResponse;

public class Manager {
	private static String ECOSYSTEM_BASE_URL = "http://localhost:8080/ecosystemguard-registry/";

	private static String REGISTER_SERVICE = "register";
	private static String UNREGISTER_SERVICE = "unregister";
	private static String UPDATE_CREDENTIALS_SERVICE = "updatecredentials";

	private static String HOST_CONFIG_FILENAME = "host.xml";
	private static final int NUM_BITS = 16;
	private static final int MIN_PASSWORD_LENGTH = 8;

	private class Config {
		private String configDirectory;
		private boolean debug = false;

		public Config(String[] args) throws Exception {
			switch (args.length) {
			case 0:
				configDirectory = new String();
				break;
			case 1:
				configDirectory = args[0];
				break;
			case 2:
				configDirectory = args[0];
				debug = args[1].toUpperCase().equals("DEBUG");
				break;
			default:
				throw new Exception("Manager: Incorrect number of arguments");
			}
		}

		public String getConfigDirectory() {
			return configDirectory;
		}

		public boolean isDebug() {
			return debug;
		}

	}

	private enum MainOptionFunction {
		ACCOUNT_SETTINGS, REGISTER, UNREGISTER, EXIT;
	}

	private enum RegistryOptionFunction {
		CREATE_ACCOUNT, DELETE_ACCOUNT, CHANGE_PASSWORD;
	}

	private Config managerConfig;
	private HostConfig hostConfig;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		System.out.println("EcosystemGuard Configuration Manager");
		System.out.println("------------------------------------");
		Manager manager = new Manager(args);
		manager.execute();
	}

	public Manager(String[] args) throws Exception {
		managerConfig = new Config(args);
		boolean configExists = loadHostConfig();
		if (!configExists) {
			createInitialHostConfig();
		}
	}

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
				printSeparatorLine();
				if (!e.getClass().getCanonicalName().equals(Exception.class.getCanonicalName())) {
					System.out.println("ERROR: " + e.getClass().getCanonicalName() + ": " + e.getMessage());
				}
				else {
					System.out.println("ERROR: " + ": " + e.getMessage());
				}
				printSeparatorLine();
			}
		}
	}

	private boolean processMainOptions() throws Exception {
		printRegistrationInfo();
		OptionSelections<MainOptionFunction> mainOptions = showMainOptions();
		System.out.print("Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		MainOptionFunction optionSelected = mainOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		switch (optionSelected) {
		case ACCOUNT_SETTINGS:
			accountSettings();
			break;
		case REGISTER:
			registerHost();
			break;
		case UNREGISTER:
			unregisterHost();
			break;
		case EXIT:
			return true;
		}
		return false;
	}

	private void accountSettings() throws Exception {
		System.out.println("**********************************************");
		OptionSelections<RegistryOptionFunction> registryOptions = showRegistryOptions();
		System.out.print("Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		RegistryOptionFunction optionSelected = registryOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		switch (optionSelected) {
		case CREATE_ACCOUNT:
			createAccount();
			break;
		case DELETE_ACCOUNT:
			deleteAccount();
			break;
		case CHANGE_PASSWORD:
			changePassword();
		}
	}

	private void printRegistrationInfo() {
		if (hostConfig.getCredentials() != null) {
			System.out.println("Host registration status: REGISTERED");
		}
		else {
			System.out.println("Host registration status: NOT REGISTERED");
		}
	}

	private OptionSelections<MainOptionFunction> showMainOptions() {
		OptionSelections<MainOptionFunction> selection = new OptionSelections<MainOptionFunction>();
		int option = 1;
		System.out.println(option + ". Account settings");
		selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.ACCOUNT_SETTINGS));
		if (hostConfig.getCredentials() == null) {
			System.out.println(option + ". Register EcosystemGuard host");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.REGISTER));
		}
		else {
			System.out.println(option + ". Unregister EcosystemGuard host");
			selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.UNREGISTER));
		}
		System.out.println(option + ". Exit");
		selection.add(new OptionSelection<MainOptionFunction>(option++, MainOptionFunction.EXIT));
		return selection;
	}

	private OptionSelections<RegistryOptionFunction> showRegistryOptions() {
		OptionSelections<RegistryOptionFunction> selection = new OptionSelections<RegistryOptionFunction>();
		int option = 1;
		System.out.println(option + ". Create Account");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.CREATE_ACCOUNT));
		System.out.println(option + ". Change Account Password");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.CHANGE_PASSWORD));
		System.out.println(option + ". Delete Account");
		selection.add(new OptionSelection<RegistryOptionFunction>(option++, RegistryOptionFunction.DELETE_ACCOUNT));
		return selection;
	}

	private boolean loadHostConfig() throws Exception {
		File configFile = new File(managerConfig.getConfigDirectory() + "/" + HOST_CONFIG_FILENAME);
		FileReader reader = null;
		try {
			reader = new FileReader(configFile);
		}
		catch (FileNotFoundException e) {
			return false;
		}
		hostConfig = Deserializer.deserialize(HostConfig.class, reader);
		return true;
	}

	private void createInitialHostConfig() throws Exception {
		System.out.println("Initializing EcosystemGuard host...");
		hostConfig = new HostConfig();
		byte[] randomBits = RandomGenerator.generateRandom(NUM_BITS);
		hostConfig.setId("HostId" + String.format("%x", new BigInteger(1, randomBits)));
		System.out.println("Host id generated: " + hostConfig.getId());

		System.out.print("Type your EcosystemGuard host purposes summary? ");
		hostConfig.setSummary(scanner.nextLine());

		System.out.print("Type your EcosystemGuard host purposes description? ");
		hostConfig.setDescription(scanner.nextLine());

		FileWriter writer = new FileWriter(new File(managerConfig.getConfigDirectory() + "/" + HOST_CONFIG_FILENAME));
		try {
			Serializer.serialize(hostConfig, HostConfig.class, writer);
		}
		finally {
			writer.close();
		}
	}

	private static <T, R> R sendRequest(T request, Class<T> requestClass, Class<R> responseClass, String url,
			boolean debug) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			String xmlRequest = Serializer.serialize(request, requestClass);
			if (debug) {
				System.out.println("SOAP REQUEST");
				System.out.println("************");
				System.out.println(xmlRequest);
			}
			httpPost.setEntity(new StringEntity(xmlRequest));
			HttpResponse httpResponse = httpclient.execute(httpPost);
			String response = EntityUtils.toString(httpResponse.getEntity());
			if (debug) {
				System.out.println("SOAP RESPONSE");
				System.out.println("************");
				System.out.println(response);
				System.out.println("------------------");
			}
			return Deserializer.deserialize(responseClass, new StringReader(response));
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/*
	 * Options implementation
	 */

	private void registerHost() throws Exception {
		System.out.println("registerHost");
	}

	private void unregisterHost() {
		System.out.println("unregisterHost");
	}

	private void createAccount() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.nextLine();
		System.out.print("Enter new password: ");
		char[] password = readPassword();
		System.out.print("Confirm password: ");
		char[] confirmPassword = readPassword();
		if (!Arrays.equals(password, confirmPassword))
			throw new Exception("ERROR: Passwords do not match.");
		System.out.print("Telephone number: ");
		String telephone = scanner.nextLine();
		System.out.print("Recover e-mail account: ");
		String recoverMail = scanner.nextLine();
		AccountInformation info = new AccountInformation();
		info.setTelephoneNumber(telephone);
		info.setRecoverMail(recoverMail);
		Credentials credentials = new Credentials(username, new String(password));
		RegisterRequest request = new RegisterRequest();
		request.setCredentials(credentials);
		request.setAccountInformation(info);
		RegisterResponse response = sendRequest(request, RegisterRequest.class, RegisterResponse.class,
				ECOSYSTEM_BASE_URL + REGISTER_SERVICE, managerConfig.isDebug());
		printOperationStatus("Account registration status: ", response.getResult());
	}

	private void deleteAccount() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = readPassword();
		System.out.print("Are you sure you want to delete user '" + username + "'? [Y|N]: ");
		String sure = scanner.nextLine();
		if (!sure.toUpperCase().equals("Y"))
			throw new Exception("Delete account operation cancelled");
		Credentials credentials = new Credentials(username, new String(password));
		UnregisterRequest request = new UnregisterRequest();
		request.setCredentials(credentials);
		UnregisterResponse response = sendRequest(request, UnregisterRequest.class, UnregisterResponse.class,
				ECOSYSTEM_BASE_URL + UNREGISTER_SERVICE, managerConfig.isDebug());
		printOperationStatus("Account unregistration status: ", response.getResult());
	}

	private void changePassword() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = readPassword();
		System.out.print("Type new password: ");
		char[] newPassword1 = readPassword();
		System.out.print("Confirm new password: ");
		char[] newPassword2 = readPassword();
		if (!Arrays.equals(newPassword1, newPassword2))
			throw new Exception("ERROR: New passwords do not match.");
		Credentials credentials = new Credentials(username, new String(password));
		UpdateCredentialsRequest request = new UpdateCredentialsRequest();
		request.setCredentials(credentials);
		request.setNewPassword(new String(newPassword1));
		UpdateCredentialsResponse response = sendRequest(request, UpdateCredentialsRequest.class,
				UpdateCredentialsResponse.class, ECOSYSTEM_BASE_URL + UPDATE_CREDENTIALS_SERVICE,
				managerConfig.isDebug());
		printOperationStatus("Update credentials status: ", response.getResult());
	}

	private void printOperationStatus(String operationMessage, Result result) {
		printSeparatorLine();
		System.out.println(operationMessage + result.getStatus());
		if (result.getStatus() != Result.Status.OK) {
			if (result.getAppStatus() != null) {
				System.out.println("Error: " + result.getAppStatus());
			}
		}
		printSeparatorLine();
	}

	private char[] readPassword() throws Exception {
		char[] pass = null;
		if (System.console() != null) {
			pass = System.console().readPassword();
		}
		else {
			pass = scanner.nextLine().toCharArray();
		}
		if (pass.length < MIN_PASSWORD_LENGTH)
			throw new Exception("Password must be 8 characters at least");
		return pass;
	}

	private void printSeparatorLine() {
		System.out.println("==============================================");
	}
}
