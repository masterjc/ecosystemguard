package com.ecosystem.guard.pc;

import com.ecosystem.guard.domain.Credentials;

public class Client {
	private ClientConfig clientConfig;
	private LoginManager loginManager = new LoginManager();
	
	public static void main(String[] args) throws Exception {
		Client client = new Client(args);
		client.execute();
	}
	
	public Client(String[] args) throws Exception {
		clientConfig = new ClientConfig(args);
	}
	
	/**
	 * Main loop
	 */
	public void execute() {
		boolean exit = false;
		Credentials credentials = null;
		while (!exit) {
			try {
				ClientOutput.printLogo();
				if(credentials == null)
					credentials = loginManager.login();
				
				
			}
			catch (Exception e) {
				if (clientConfig.isDebug()) {
					e.printStackTrace();
				}
				ClientOutput.printSeparatorLine();
				if (!e.getClass().getCanonicalName().equals(Exception.class.getCanonicalName())) {
					System.out.println("ERROR: " + e.getClass().getCanonicalName() + ": " + e.getMessage());
				}
				else {
					System.out.println("ERROR: " + e.getMessage());
				}
				ClientOutput.printSeparatorLine();
				
				if( credentials == null) {
					exit = true;
				}
			}
		}
	}

}
