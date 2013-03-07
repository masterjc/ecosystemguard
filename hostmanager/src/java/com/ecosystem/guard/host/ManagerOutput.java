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

import com.ecosystem.guard.domain.Result;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ManagerOutput {
	
	public static void printLogo() {
		System.out.println("--------------------------");
		System.out.println("| EcosystemGuard Manager |");
		System.out.println("--------------------------");
	}

	public static void printOperationStatus(String operationMessage, Result result) {
		printSeparatorLine();
		System.out.println(operationMessage + result.getStatus());
		if (result.getStatus() != Result.Status.OK) {
			if (result.getAppStatus() != null) {
				System.out.println("Error: " + result.getAppStatus());
			}
			if (result.getMessage() != null) {
				System.out.println("Message: " + result.getMessage());
			}
		}
		printSeparatorLine();
	}
	
	public static void printSeparatorLine() {
		System.out.println("==============================================");
	}
	
	public static void printRegistrationInfo(HostConfigurator hostConfigurator) {
		if (hostConfigurator.hasCredentials()) {
			System.out.println("** Associated account: '"
					+ hostConfigurator.getUsernamePassword().getUsername() + "' **");
		}
		else {
			System.out.println("** Associated account: Not associated **");
		}
	}

}
