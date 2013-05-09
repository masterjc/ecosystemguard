package com.ecosystem.guard.pc;


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
		Session session = null;
		while (!exit) {
			try {
				ClientOutput.printLogo();
				if (session == null) {
					session = loginManager.startSession();
				}
				System.out.println("DEBUG DATA");
				System.out.println(session.getCredentials().getUsernamePassword().getUsername());
				System.out.println(session.getAppIpAddress());
				System.out.println(session.getHostId());
				
				return;
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

				if (session == null) {
					exit = true;
				}
			}
		}
	}

}
