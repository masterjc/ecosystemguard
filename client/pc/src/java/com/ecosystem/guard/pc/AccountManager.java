package com.ecosystem.guard.pc;

import java.util.Arrays;
import java.util.Scanner;

import com.ecosystem.guard.common.CmdUtils;
import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.service.registry.AccountInformation;
import com.ecosystem.guard.domain.service.registry.RegisterRequest;
import com.ecosystem.guard.domain.service.registry.RegisterResponse;
import com.ecosystem.guard.domain.service.registry.UnregisterRequest;
import com.ecosystem.guard.domain.service.registry.UnregisterResponse;
import com.ecosystem.guard.domain.service.registry.UpdateCredentialsRequest;
import com.ecosystem.guard.domain.service.registry.UpdateCredentialsResponse;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class AccountManager {
	private Scanner scanner = new Scanner(System.in);
	
	private enum RegistryOptionFunction {
		CREATE_ACCOUNT, DELETE_ACCOUNT, CHANGE_PASSWORD, BACK;
	}
	
	public void execute() throws Exception {
		ClientOutput.printLogo();
		OptionSelections<RegistryOptionFunction> registryOptions = showRegistryOptions();
		System.out.println();
		System.out.print("-> Select an option: ");
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
			break;
		case BACK:
			return;
		}
	}

	private void createAccount() throws Exception {
		System.out.print("Enter account username: ");
		String username = scanner.nextLine();
		System.out.print("Enter new password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Confirm password: ");
		char[] confirmPassword = CmdUtils.readPassword();
		if (!Arrays.equals(password, confirmPassword))
			throw new Exception("ERROR: Passwords do not match.");
		System.out.print("Telephone number: ");
		String telephone = scanner.nextLine();
		System.out.print("User e-mail account: ");
		String recoverMail = scanner.nextLine();
		AccountInformation info = new AccountInformation();
		info.setTelephoneNumber(telephone);
		info.setRecoverMail(recoverMail);
		Credentials credentials = new Credentials(username, new String(password));
		RegisterRequest request = new RegisterRequest();
		request.setCredentials(credentials);
		request.setAccountInformation(info);
		RegisterResponse response = XmlServiceRequestor.sendRequest(request, RegisterRequest.class,
				RegisterResponse.class, ClientConstants.REGISTER_SERVICE);
		ClientOutput.printOperationStatus("Account registration status: ", response.getResult());
	}

	private void deleteAccount() throws Exception {
		System.out.print("Enter account username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Are you sure you want to delete user '" + username + "'? [Y|N]: ");
		String sure = scanner.nextLine();
		if (!sure.toUpperCase().equals("Y"))
			throw new Exception("Delete account operation cancelled");
		Credentials credentials = new Credentials(username, new String(password));
		UnregisterRequest request = new UnregisterRequest();
		request.setCredentials(credentials);
		UnregisterResponse response = XmlServiceRequestor.sendRequest(request, UnregisterRequest.class,
				UnregisterResponse.class, ClientConstants.UNREGISTER_SERVICE);
		ClientOutput.printOperationStatus("Account unregistration status: ", response.getResult());
	}

	private void changePassword() throws Exception {
		System.out.print("Enter account username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Type new password: ");
		char[] newPassword1 = CmdUtils.readPassword();
		System.out.print("Confirm new password: ");
		char[] newPassword2 = CmdUtils.readPassword();
		if (!Arrays.equals(newPassword1, newPassword2))
			throw new Exception("ERROR: New passwords do not match.");
		Credentials credentials = new Credentials(username, new String(password));
		UpdateCredentialsRequest request = new UpdateCredentialsRequest();
		request.setCredentials(credentials);
		request.setNewPassword(new String(newPassword1));
		UpdateCredentialsResponse response = XmlServiceRequestor.sendRequest(request, UpdateCredentialsRequest.class,
				UpdateCredentialsResponse.class, ClientConstants.UPDATE_CREDENTIALS_SERVICE);
		ClientOutput.printOperationStatus("Update credentials status: ", response.getResult());
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
