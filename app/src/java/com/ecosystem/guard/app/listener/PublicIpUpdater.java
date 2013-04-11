package com.ecosystem.guard.app.listener;

import java.util.concurrent.Callable;

import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.config.HostConfig;
import com.ecosystem.guard.domain.service.registry.UpdateIpRequest;
import com.ecosystem.guard.domain.service.registry.UpdateIpResponse;
import com.ecosystem.guard.engine.config.EcosystemConfig;
import com.ecosystem.guard.engine.config.RegistryServices;

/**
 * Actualiza la IP pública del host en el servicio EcosystemGuard registry
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class PublicIpUpdater implements Callable<Void> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Void call() throws Exception {
		HostConfig hostConfig = EcosystemConfig.getHostConfig();
		UpdateIpRequest ipRequest = new UpdateIpRequest();
		ipRequest.setCredentials(hostConfig.getCredentials());
		ipRequest.setHostId(hostConfig.getId());
		UpdateIpResponse ipResponse = XmlServiceRequestor.sendRequest(ipRequest, UpdateIpRequest.class,
				UpdateIpResponse.class, RegistryServices.getUpdateIpUrl());
		if (ipResponse.getResult().getStatus() != Status.OK)
			throw new Exception("PublicIpUpdater error (Status: " + ipResponse.getResult().getStatus() + " - Message: "
					+ ipResponse.getResult().getMessage());
		return null;
	}

}
