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

import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.service.registry.GetIpRequest;
import com.ecosystem.guard.domain.service.registry.GetIpResponse;
import com.ecosystem.guard.phidgets.SensorManager;
import com.ecosystem.guard.phidgets.sensors.LcdScreen;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class IpManager {

	private HostConfigurator hostConfigurator;

	public IpManager(HostConfigurator hostConfigurator) {
		this.hostConfigurator = hostConfigurator;
	}

	public void showPublicIpInformation() throws Exception {
		GetIpRequest request = new GetIpRequest();
		request.setCredentials(new Credentials(hostConfigurator.getUsernamePassword().getUsername(), hostConfigurator
				.getUsernamePassword().getPassword()));
		request.setHostId(hostConfigurator.getHostId());
		GetIpResponse response = XmlServiceRequestor.sendRequest(request, GetIpRequest.class, GetIpResponse.class,
				ManagerConstants.GET_IP_SERVICE);
		printIpInformation(response);
		showLcdIpInformation(response);
	}

	public void printIpInformation(GetIpResponse response) {
		if (response.getResult().getStatus() != Status.OK) {
			ManagerOutput.printOperationStatus("Get Host public ip information status: ", response.getResult());
			return;
		}
		ManagerOutput.printSeparatorLine();
		System.out.println("Public IP Address: " + response.getIpInformation().getPublicIp());
		System.out.println("Last public IP Address update: " + response.getIpInformation().getLastChange());
		ManagerOutput.printSeparatorLine();
	}
	
	public void showLcdIpInformation(GetIpResponse response) throws Exception {
		LcdScreen lcd = SensorManager.getInstance().getSensor(LcdScreen.class);
		if (response.getResult().getStatus() != Status.OK) {
			lcd.showIntermitentMessage("ERROR:", response.getResult().getStatus().getStatusCode(), 5);
			return;
		}
		lcd.showMessage("Public IP:", response.getIpInformation().getPublicIp(), 5000);
	}
}
