package com.ecosystem.guard.host;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
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

public class Manager {
	private static String HOST_CONFIG_FILENAME = "host.xml";
	private static final int RADIX = 64;
	private static final int NUM_BITS = 64;

	private class Config {
		private String configDirectory;

		public Config(String[] args) throws Exception {
			if (args.length == 0) {
				configDirectory = new String();
			}
			else {
				configDirectory = args[0];
			}
		}

		public String getConfigDirectory() {
			return configDirectory;
		}

	}

	private enum MainOptionFunction {
		ACCOUNT_SETTINGS, REGISTER, UNREGISTER, EXIT;
	}

	private enum RegistryOptionFunction {
		CREATE_ACCOUNT, DELETE_ACCOUNT;
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
				System.out.println("=================================");
				System.out.println("ERROR: " + e.getMessage());
				System.out.println("=================================");
			}
		}
	}

	private boolean processMainOptions() throws Exception {
		System.out.println("**********************************");
		printRegistrationInfo();
		OptionSelections<MainOptionFunction> mainOptions = showMainOptions();
		System.out.print("Select an option: ");
		int selection = scanner.nextInt();
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
		System.out.println("**********************************");
		OptionSelections<RegistryOptionFunction> registryOptions = showRegistryOptions();
		System.out.print("Select an option: ");
		int selection = scanner.nextInt();
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
		hostConfig.setId("HostId" + RandomGenerator.generateRandom(NUM_BITS, RADIX));
		System.out.println("Host id generated: " + hostConfig.getId());

		System.out.print("Type your EcosystemGuard host purposes? ");
		hostConfig.setDescription(scanner.next());

		FileWriter writer = new FileWriter(new File(managerConfig.getConfigDirectory() + "/" + HOST_CONFIG_FILENAME));
		try {
			Serializer.serialize(hostConfig, HostConfig.class, writer);
		}
		finally {
			writer.close();
		}
	}

	private RegisterResponse registerAccount(RegisterRequest request) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:8080/ecosystemguard-registry/register");
		httpPost.setEntity(new StringEntity(Serializer.serialize(request, RegisterRequest.class)));
		HttpResponse httpResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		return Deserializer.deserialize(RegisterResponse.class, new StringReader(response));
	}

	/*
	 * Options implementation
	 */

	private void registerHost() throws Exception {
	}

	private void unregisterHost() {
		System.out.println("unregisterHost");
	}

	private void createAccount() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.next();
		System.out.print("Enter new password: ");
		char[] password = readPassword();
		System.out.print("Confirm password: ");
		char[] confirmPassword = readPassword();
		if (!Arrays.equals(password, confirmPassword))
			throw new Exception("ERROR: Passwords do not match.");
		System.out.print("Telephone number: ");
		String telephone = scanner.next();
		System.out.print("Recover e-mail account: ");
		String recoverMail = scanner.next();
		AccountInformation info = new AccountInformation();
		info.setTelephoneNumber(telephone);
		info.setRecoverMail(recoverMail);
		Credentials credentials = new Credentials(username, new String(password));
		RegisterRequest request = new RegisterRequest();
		request.setCredentials(credentials);
		request.setAccountInformation(info);
		RegisterResponse response = registerAccount(request);
		System.out.println("Account registration status: " + response.getResult().getStatus());
		if (response.getResult().getStatus() != Result.Status.OK) {
			System.out.println("Error: " + response.getResult().getAppStatus());
		}
	}

	private char[] readPassword() {
		if (System.console() != null) {
			return System.console().readPassword();
		}
		else {
			return scanner.next().toCharArray();
		}
	}

	private void deleteAccount() {
		System.out.println("deleteAccount");
	}
}
