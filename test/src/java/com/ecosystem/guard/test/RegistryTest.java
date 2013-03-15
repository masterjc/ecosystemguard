/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.service.AccountInformation;
import com.ecosystem.guard.domain.service.GetHostsRequest;
import com.ecosystem.guard.domain.service.GetHostsResponse;
import com.ecosystem.guard.domain.service.GetHostsStatus;
import com.ecosystem.guard.domain.service.GetIpRequest;
import com.ecosystem.guard.domain.service.GetIpResponse;
import com.ecosystem.guard.domain.service.HostInformation;
import com.ecosystem.guard.domain.service.RegisterHostRequest;
import com.ecosystem.guard.domain.service.RegisterHostResponse;
import com.ecosystem.guard.domain.service.RegisterRequest;
import com.ecosystem.guard.domain.service.RegisterResponse;
import com.ecosystem.guard.domain.service.RegisterStatus;
import com.ecosystem.guard.domain.service.UnregisterHostRequest;
import com.ecosystem.guard.domain.service.UnregisterHostResponse;
import com.ecosystem.guard.domain.service.UnregisterRequest;
import com.ecosystem.guard.domain.service.UnregisterResponse;
import com.ecosystem.guard.domain.service.UpdateCredentialsRequest;
import com.ecosystem.guard.domain.service.UpdateCredentialsResponse;
import com.ecosystem.guard.domain.service.UpdateIpRequest;
import com.ecosystem.guard.domain.service.UpdateIpResponse;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class RegistryTest {
	private static String ecosystemGuardRegistryHost;

	@BeforeClass
	public static void setup() throws Exception {
		ecosystemGuardRegistryHost = System.getProperty("test.url");
		if (ecosystemGuardRegistryHost == null)
			throw new Exception("'test.url' jvm variable is not present");
		System.out
				.println("***** IMPORTANTE: Por favor, borre las tablas o el contenido de ellas antes de ejecutar estos tests funcionales ************");
	}

	@Test
	public void registryTest() throws Exception {
		String username = "test@ecosystemguard.com";
		String password1 = "demodemo";
		String password2 = "pepepepe";
		String hostId1 = "hostid129838219839218392";
		String hostId2 = "hostid978676949340299384";

		Assert.assertEquals(Status.OK, register(username, password1).getStatus());
		Assert.assertEquals(RegisterStatus.ALREADY_REGISTERED.getStatusCode(), register(username, password1)
				.getAppStatus());
		Assert.assertEquals(Status.OK, associate(username, password1, hostId1).getStatus());
		Assert.assertEquals(Status.OK, changePassword(username, password1, password2).getStatus());
		Assert.assertEquals(Status.OK, updateIp(username, password2, hostId1).getStatus());
		Assert.assertTrue(hasIpAddress(username, password2, hostId1));
		Assert.assertEquals(Status.OK, disassociate(username, password2, hostId1).getStatus());
		Assert.assertFalse(hasIpAddress(username, password2, hostId1));
		Assert.assertEquals(Status.OK, associate(username, password2, hostId1).getStatus());
		Assert.assertEquals(Status.OK, associate(username, password2, hostId2).getStatus());
		Assert.assertEquals(2, getHosts(username, password2));
		Assert.assertEquals(Status.OK, disassociate(username, password2, hostId2).getStatus());
		Assert.assertEquals(1, getHosts(username, password2));
		Assert.assertEquals(Status.OK, updateIp(username, password2, hostId1).getStatus());
		Assert.assertEquals(Status.OK, unregister(username, password2).getStatus());
		Assert.assertFalse(hasIpAddress(username, password2, hostId1));
		Assert.assertEquals(Status.AUTHN_ERROR, associate(username, password2, hostId1).getStatus());
	}

	private int getHosts(String username, String password) throws Exception {
		GetHostsRequest request = new GetHostsRequest();
		Credentials credentials = new Credentials(username, password);
		request.setCredentials(credentials);
		GetHostsResponse response = XmlServiceRequestor.sendRequest(request, GetHostsRequest.class,
				GetHostsResponse.class, ecosystemGuardRegistryHost + "/gethosts");
		if (response.getResult().getStatus() != Status.OK)
			throw new Exception("getHosts error");
		if (response.getResult().getAppStatus() != null
				&& response.getResult().getAppStatus().equals(GetHostsStatus.NO_REGISTERED_HOSTS.getStatusCode()))
			return 0;
		return response.getHosts().size();
	}

	private Result register(String username, String password) throws Exception {
		AccountInformation info = new AccountInformation();
		info.setTelephoneNumber("8888");
		info.setRecoverMail("aaaa");
		Credentials credentials = new Credentials(username, password);
		RegisterRequest request = new RegisterRequest();
		request.setCredentials(credentials);
		request.setAccountInformation(info);
		RegisterResponse response = XmlServiceRequestor.sendRequest(request, RegisterRequest.class,
				RegisterResponse.class, ecosystemGuardRegistryHost + "/register");
		return response.getResult();
	}

	private Result unregister(String username, String password) throws Exception {
		Credentials credentials = new Credentials(username, new String(password));
		UnregisterRequest request = new UnregisterRequest();
		request.setCredentials(credentials);
		UnregisterResponse response = XmlServiceRequestor.sendRequest(request, UnregisterRequest.class,
				UnregisterResponse.class, ecosystemGuardRegistryHost + "/unregister");
		return response.getResult();
	}

	private Result changePassword(String username, String oldPassword, String newPassword) throws Exception {
		Credentials credentials = new Credentials(username, oldPassword);
		UpdateCredentialsRequest request = new UpdateCredentialsRequest();
		request.setCredentials(credentials);
		request.setNewPassword(newPassword);
		UpdateCredentialsResponse response = XmlServiceRequestor.sendRequest(request, UpdateCredentialsRequest.class,
				UpdateCredentialsResponse.class, ecosystemGuardRegistryHost + "/updatecredentials");
		return response.getResult();
	}

	private Result associate(String username, String password, String hostId) throws Exception {
		RegisterHostRequest request = new RegisterHostRequest();
		Credentials credentials = new Credentials(username, password);
		request.setCredentials(credentials);
		HostInformation info = new HostInformation();
		info.setId(hostId);
		info.setDescription("description");
		info.setSummary("summary");
		request.setHostInformation(info);
		RegisterHostResponse response = XmlServiceRequestor.sendRequest(request, RegisterHostRequest.class,
				RegisterHostResponse.class, ecosystemGuardRegistryHost + "/registerhost");
		return response.getResult();
	}

	private Result updateIp(String username, String password, String hostId) throws Exception {
		UpdateIpRequest ipRequest = new UpdateIpRequest();
		ipRequest.setCredentials(new Credentials(username, password));
		ipRequest.setHostId(hostId);
		UpdateIpResponse ipResponse = XmlServiceRequestor.sendRequest(ipRequest, UpdateIpRequest.class,
				UpdateIpResponse.class, ecosystemGuardRegistryHost + "/updateip");
		return ipResponse.getResult();
	}

	private Result disassociate(String username, String password, String hostId) throws Exception {
		UnregisterHostRequest request = new UnregisterHostRequest();
		Credentials credentials = new Credentials(username, password);
		request.setCredentials(credentials);
		HostInformation info = new HostInformation();
		info.setId(hostId);
		request.setHostInformation(info);
		UnregisterHostResponse response = XmlServiceRequestor.sendRequest(request, UnregisterHostRequest.class,
				UnregisterHostResponse.class, ecosystemGuardRegistryHost + "/unregisterhost");
		return response.getResult();
	}

	private boolean hasIpAddress(String username, String password, String hostId) throws Exception {
		GetIpRequest request = new GetIpRequest();
		request.setCredentials(new Credentials(username, password));
		request.setHostId(hostId);
		GetIpResponse response = XmlServiceRequestor.sendRequest(request, GetIpRequest.class, GetIpResponse.class,
				ecosystemGuardRegistryHost + "/getip");
		return (response.getResult().getStatus().equals(Status.OK) && response.getIpInformation().getPublicIp() != null);
	}
}
