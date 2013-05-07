package com.ecosystem.guard.pc;

import com.ecosystem.guard.domain.Result;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ClientOutput {

	public static void printLogo() {
		System.out.println("-------------------------");
		System.out.println("| EcosystemGuard Client |");
		System.out.println("-------------------------");
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
}
