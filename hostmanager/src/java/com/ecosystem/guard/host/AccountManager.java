/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.host;

import java.util.Arrays;
import java.util.Scanner;

import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result.Status;
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
	private HostConfigurator hostConfigurator;
	private Scanner scanner = new Scanner(System.in);

	public AccountManager(HostConfigurator hostConfigurator) {
		this.hostConfigurator = hostConfigurator;
	}

	public void createAccount() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.nextLine();
		System.out.print("Enter new password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Confirm password: ");
		char[] confirmPassword = CmdUtils.readPassword();
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
		RegisterResponse response = XmlServiceRequestor.sendRequest(request, RegisterRequest.class, RegisterResponse.class,
				ManagerConstants.REGISTER_SERVICE);
		ManagerOutput.printOperationStatus("Account registration status: ", response.getResult());
	}

	public void deleteAccount() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Are you sure you want to delete user '" + username + "'? [Y|N]: ");
		String sure = scanner.nextLine();
		if (!sure.toUpperCase().equals("Y"))
			throw new Exception("Delete account operation cancelled");
		if (hostConfigurator.hasCredentials() && username.equals(hostConfigurator.getUsernamePassword().getUsername())) {
			System.out.print("This host is registered with '" + username + "'. Delete account? [Y|N]: ");
			sure = scanner.nextLine();
			if (!sure.toUpperCase().equals("Y"))
				throw new Exception("Delete account operation cancelled");
		}
		Credentials credentials = new Credentials(username, new String(password));
		UnregisterRequest request = new UnregisterRequest();
		request.setCredentials(credentials);
		UnregisterResponse response = XmlServiceRequestor.sendRequest(request, UnregisterRequest.class, UnregisterResponse.class,
				ManagerConstants.UNREGISTER_SERVICE);
		if (response.getResult().getStatus() == Status.OK && hostConfigurator.hasCredentials() && username.equals(hostConfigurator.getUsernamePassword().getUsername())) {
			hostConfigurator.reset();
		}
		ManagerOutput.printOperationStatus("Account unregistration status: ", response.getResult());
	}

	public void changePassword() throws Exception {
		System.out.print("Enter e-mail account (main username): ");
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
				UpdateCredentialsResponse.class, ManagerConstants.UPDATE_CREDENTIALS_SERVICE);
		ManagerOutput.printOperationStatus("Update credentials status: ", response.getResult());
	}

}
