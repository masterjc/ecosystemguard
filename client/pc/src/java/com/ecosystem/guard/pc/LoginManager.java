package com.ecosystem.guard.pc;

import java.util.Scanner;

import com.ecosystem.guard.common.CmdUtils;
import com.ecosystem.guard.domain.Credentials;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class LoginManager {
	private Scanner scanner = new Scanner(System.in);

	public LoginManager() {
	}

	public Credentials login() throws Exception {
		printLoginLogo();
		System.out.print("- Username: ");
		String username = scanner.nextLine();
		System.out.print("- Password: ");
		char[] password = CmdUtils.readPassword();
		Credentials credentials = new Credentials(username, new String(password));
		return credentials;
	}
	
	private void printLoginLogo() {
		System.out.println("Login EcosystemGuard account");
	}
}
