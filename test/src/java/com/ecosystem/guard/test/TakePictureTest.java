package com.ecosystem.guard.test;

import com.ecosystem.guard.common.XmlServiceRequestor;
import com.ecosystem.guard.domain.Credentials;
import com.ecosystem.guard.domain.Result.Status;
import com.ecosystem.guard.domain.service.registry.GetHostsRequest;
import com.ecosystem.guard.domain.service.registry.GetHostsResponse;
import com.ecosystem.guard.domain.service.registry.GetHostsStatus;

public class TakePictureTest {
	private int getHosts(String username, String password) throws Exception {
		GetHostsRequest request = new GetHostsRequest();
		Credentials credentials = new Credentials(username, password);
		request.setCredentials(credentials);
		GetHostsResponse response = XmlServiceRequestor.sendRequest(request, GetHostsRequest.class,
				GetHostsResponse.class, ecosystemGuardRegistryHost + "/takepicture");
		if (response.getResult().getStatus() != Status.OK)
			throw new Exception("getHosts error");
		if (response.getResult().getAppStatus() != null
				&& response.getResult().getAppStatus().equals(GetHostsStatus.NO_REGISTERED_HOSTS.getStatusCode()))
			return 0;
		return response.getHosts().size();
	}
}
