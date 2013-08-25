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

import java.net.InetAddress;
import java.util.Scanner;

import com.ecosystem.guard.common.CmdUtils;
import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.service.registry.HostInformation;
import com.ecosystem.guard.domain.service.registry.RegisterHostRequest;
import com.ecosystem.guard.domain.service.registry.RegisterHostResponse;
import com.ecosystem.guard.domain.service.registry.UnregisterHostRequest;
import com.ecosystem.guard.domain.service.registry.UnregisterHostResponse;
import com.ecosystem.guard.domain.service.registry.UpdateIpRequest;
import com.ecosystem.guard.domain.service.registry.UpdateIpResponse;
import com.ecosystem.guard.phidgets.SensorManager;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;

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
		System.out.print("Enter user account: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		char[] password = CmdUtils.readPassword();
		System.out.print("Are you sure you want to register '" + summary + "' with user '" + username + "'? [Y|N]: ");
		String sure = scanner.nextLine();
		if (!sure.toUpperCase().equals("Y"))
			throw new Exception("Register host operation cancelled");
		RegisterHostRequest request = new RegisterHostRequest();
		Credentials credentials = new Credentials(username, new String(password));
		request.setCredentials(credentials);
		HostInformation info = new HostInformation();
		info.setId(hostConfigurator.getHostId());
		info.setDescription(description);
		info.setSummary(summary);
		request.setHostInformation(info);
		RegisterHostResponse response = XmlServiceRequestor.sendRequest(request, RegisterHostRequest.class,
				RegisterHostResponse.class, ManagerConstants.REGISTER_HOST_SERVICE);
		if (response.getResult().getStatus() != Status.OK) {
			ManagerOutput.printOperationStatus("Host registration status: ", response.getResult());
			return;
		}
		UpdateIpRequest ipRequest = new UpdateIpRequest();
		ipRequest.setCredentials(credentials);
		ipRequest.setHostId(hostConfigurator.getHostId());
		ipRequest.setPrivateIp(InetAddress.getLocalHost().getHostAddress());
		UpdateIpResponse ipResponse = XmlServiceRequestor.sendRequest(ipRequest, UpdateIpRequest.class, UpdateIpResponse.class,
				ManagerConstants.UPDATE_IP_SERVICE);
		if (ipResponse.getResult().getStatus() != Status.OK) {
			ManagerOutput.printOperationStatus("Ip registration status: ", ipResponse.getResult());
			return;
		}
		hostConfigurator.setCredentials(credentials.getUsernamePassword());
		hostConfigurator.save();
		ManagerOutput.printOperationStatus("Host registration status: ", ipResponse.getResult());
		showLcdMessage("Register: " + response.getResult().getStatus(), "User: " + credentials.getUsernamePassword().getUsername(), 10);
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
		UnregisterHostResponse response = XmlServiceRequestor.sendRequest(request, UnregisterHostRequest.class,
				UnregisterHostResponse.class, ManagerConstants.UNREGISTER_HOST_SERVICE);
		if (response.getResult().getStatus() == Status.OK) {
			hostConfigurator.reset();
		}
		ManagerOutput.printOperationStatus("Host unregistration status: ", response.getResult());
		showLcdMessage("Unregister: " + response.getResult().getStatus(), "User: " + credentials.getUsernamePassword().getUsername(), 10);
	}
	
	private void showLcdMessage( String text1, String text2, int seconds ) throws Exception {
		LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
		lcd.showAsynchronousMessage(text1, text2, seconds * 1000);
	}

}
