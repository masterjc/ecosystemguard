package com.ecosystem.guard.pc;

import java.util.List;
import java.util.Scanner;

import com.ecosystem.guard.common.CmdUtils;
import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.service.registry.AuthNRequest;
import com.ecosystem.guard.domain.service.registry.AuthNResponse;
import com.ecosystem.guard.domain.service.registry.GetHostsRequest;
import com.ecosystem.guard.domain.service.registry.GetHostsResponse;
import com.ecosystem.guard.domain.service.registry.GetIpRequest;
import com.ecosystem.guard.domain.service.registry.GetIpResponse;
import com.ecosystem.guard.domain.service.registry.HostInformation;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class LoginManager {
	private enum ConnectionOption {
		WIFI, WLAN;
	}

	private Scanner scanner = new Scanner(System.in);

	public Session startSession() throws Exception {
		Session session = new Session();
		Credentials credentials = authn();
		HostInformation hostId = getHost(credentials);
		String ipAddress = getAppIpAddress(credentials, hostId);
		session.setCredentials(credentials);
		session.setAppIpAddress(ipAddress);
		session.setHostInformation(hostId);
		return session;
	}

	private OptionSelections<ConnectionOption> showConnectionOptions() {
		System.out.println();
		System.out.println("Connection type selection");
		System.out.println("-------------------------");
		OptionSelections<ConnectionOption> selection = new OptionSelections<ConnectionOption>();
		int option = 1;
		System.out.println(option + ". WiFi connection. EcosystemGuard host is in your local network");
		selection.add(new OptionSelection<ConnectionOption>(option++, ConnectionOption.WIFI));
		System.out.println(option + ". WLAN connection. EcosystemGuard host is through Internet");
		selection.add(new OptionSelection<ConnectionOption>(option++, ConnectionOption.WLAN));
		return selection;
	}

	private OptionSelections<HostInformation> showHosts(List<HostInformation> hostInformations) {
		System.out.println();
		System.out.println("EcosystemGuard host selection");
		System.out.println("-----------------------------");
		OptionSelections<HostInformation> selection = new OptionSelections<HostInformation>();
		int option = 1;
		for (HostInformation hostInfo : hostInformations) {
			System.out.println(option + ". " + hostInfo.getSummary());
			selection.add(new OptionSelection<HostInformation>(option++, hostInfo));
		}
		return selection;
	}

	private String getAppIpAddress(Credentials credentials, HostInformation hostId) throws Exception {
		OptionSelections<ConnectionOption> mainOptions = showConnectionOptions();
		System.out.println();
		System.out.print("-> Select an option: ");
		int selection = Integer.parseInt(scanner.nextLine());
		ConnectionOption optionSelected = mainOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect option selected - " + selection);
		GetIpRequest request = new GetIpRequest();
		request.setCredentials(credentials);
		request.setHostId(hostId.getId());
		GetIpResponse response = XmlServiceRequestor.sendRequest(request, GetIpRequest.class, GetIpResponse.class,
				ClientConstants.GET_IP_SERVICE);
		if (response.getResult().getStatus() != Status.OK)
			throw new Exception(response.getResult().getStatus().toString());
		if (optionSelected == ConnectionOption.WIFI)
			return response.getIpInformation().getPrivateIp();
		if (optionSelected == ConnectionOption.WLAN)
			return response.getIpInformation().getPublicIp();
		throw new Exception("Connection option not supported");
	}

	private HostInformation getHost(Credentials credentials) throws Exception {
		GetHostsRequest request = new GetHostsRequest();
		request.setCredentials(credentials);
		GetHostsResponse response = XmlServiceRequestor.sendRequest(request, GetHostsRequest.class,
				GetHostsResponse.class, ClientConstants.GET_HOSTS_SERVICE);
		if (response.getResult().getStatus() != Status.OK)
			throw new Exception(response.getResult().getStatus().toString() + " - " + response.getResult().getAppStatus().toString());
		OptionSelections<HostInformation> mainOptions = showHosts(response.getHosts());
		System.out.println();
		System.out.print("-> Select a host: ");
		int selection = Integer.parseInt(scanner.nextLine());
		HostInformation optionSelected = mainOptions.getSelection(selection);
		if (optionSelected == null)
			throw new Exception("Incorrect host selected - " + selection);
		return optionSelected;
	}

	private Credentials authn() throws Exception {
		System.out.println();
		System.out.println("Enter your EcosystemGuard account");
		System.out.println("*********************************");
		System.out.print("* Username: ");
		String username = scanner.nextLine();
		System.out.print("* Password: ");
		char[] password = CmdUtils.readPassword();
		System.out.println();
		Credentials credentials = new Credentials(username, new String(password));
		AuthNRequest request = new AuthNRequest();
		request.setCredentials(credentials);
		AuthNResponse response = XmlServiceRequestor.sendRequest(request, AuthNRequest.class, AuthNResponse.class,
				ClientConstants.AUTHN_SERVICE);
		if (response.getResult().getStatus() != Status.OK)
			throw new Exception(response.getResult().getStatus().toString());
		String message = "* Welcome " + credentials.getUsernamePassword().getUsername() + " *";
		System.out.println(getAsteriscs(message.length()));
		System.out.println(message);
		System.out.println(getAsteriscs(message.length()));
		return credentials;
	}

	private String getAsteriscs(int size) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < size; i++)
			buf.append('*');
		return buf.toString();
	}
}
