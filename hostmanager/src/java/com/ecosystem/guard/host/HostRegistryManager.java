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

import java.util.Scanner;

import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.service.HostInformation;
import com.ecosystem.guard.domain.service.RegisterHostRequest;
import com.ecosystem.guard.domain.service.RegisterHostResponse;
import com.ecosystem.guard.domain.service.UnregisterHostRequest;
import com.ecosystem.guard.domain.service.UnregisterHostResponse;
import com.ecosystem.guard.domain.service.UpdateIpRequest;
import com.ecosystem.guard.domain.service.UpdateIpResponse;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class HostRegistryManager {
	private HostConfigurator hostConfigurator;
	private Scanner scanner = new Scanner(System.in);

	public HostRegistryManager(HostConfigurator hostConfigurator) {
		this.hostConfigurator = hostConfigurator;
	}

	public void registerHost() throws Exception {
		if (!hostConfigurator.isInitialized()) {
			hostConfigurator.initialize();
		}
		System.out.print("Type your EcosystemGuard host purposes summary? ");
		String summary = scanner.nextLine();
		System.out.print("Type your EcosystemGuard host purposes description? ");
		String description = scanner.nextLine();
		System.out.print("Enter e-mail account (main username): ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Are you sure you want to register '" + summary + "' with user '" + username + "'? [Y|N]: ");
		String sure = scanner.nextLine();
		if (!sure.toUpperCase().equals("Y"))
			throw new Exception("Registe host operation cancelled");
		RegisterHostRequest request = new RegisterHostRequest();
		Credentials credentials = new Credentials(username, new String(password));
		request.setCredentials(credentials);
		HostInformation info = new HostInformation();
		info.setId(hostConfigurator.getHostId());
		info.setDescription(description);
		info.setSummary(summary);
		request.setHostInformation(info);
		RegisterHostResponse response = CmdUtils.sendRequest(request, RegisterHostRequest.class,
				RegisterHostResponse.class, ManagerConstants.REGISTER_HOST_SERVICE);
		if (response.getResult().getStatus() == Status.OK) {
			hostConfigurator.setCredentials(credentials.getUsernamePassword());
			hostConfigurator.save();
		}
		else {
			ManagerOutput.printOperationStatus("Host registration status: ", response.getResult());
		}
		UpdateIpRequest ipRequest = new UpdateIpRequest();
		ipRequest.setCredentials(new Credentials(hostConfigurator.getUsernamePassword().getUsername(), hostConfigurator
				.getUsernamePassword().getPassword()));
		ipRequest.setHostId(hostConfigurator.getHostId());
		UpdateIpResponse ipResponse = CmdUtils.sendRequest(ipRequest, UpdateIpRequest.class, UpdateIpResponse.class,
				ManagerConstants.UPDATE_IP_SERVICE);
		if (ipResponse.getResult().getStatus() != Status.OK) {
			ManagerOutput.printOperationStatus("Ip registration status: ", ipResponse.getResult());
			return;
		}
		ManagerOutput.printOperationStatus("Host registration status: ", response.getResult());
	}

	public void unregisterHost() throws Exception {
		System.out.print("Are you sure you want to unregister this EcosystemGuard host from your account '"
				+ hostConfigurator.getUsernamePassword().getUsername() + "'? [Y|N]: ");
		String sure = scanner.nextLine();
		if (!sure.toUpperCase().equals("Y"))
			throw new Exception("Unregister host operation cancelled");
		UnregisterHostRequest request = new UnregisterHostRequest();
		Credentials credentials = new Credentials();
		credentials.setUsernamePassword(hostConfigurator.getUsernamePassword());
		request.setCredentials(credentials);
		HostInformation info = new HostInformation();
		info.setId(hostConfigurator.getHostId());
		request.setHostInformation(info);
		UnregisterHostResponse response = CmdUtils.sendRequest(request, UnregisterHostRequest.class,
				UnregisterHostResponse.class, ManagerConstants.UNREGISTER_HOST_SERVICE);
		if (response.getResult().getStatus() == Status.OK) {
			hostConfigurator.reset();
		}
		ManagerOutput.printOperationStatus("Host unregistration status: ", response.getResult());
	}

}
