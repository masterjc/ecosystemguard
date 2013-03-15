/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain.service.registry;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Response;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
@XmlSeeAlso(GetHostsStatus.class)
public class GetHostsResponse extends Response {
	private List<HostInformation> hosts;

	public List<HostInformation> getHosts() {
		return hosts;
	}

	@XmlElement(name = "hostInformation")
	public void setHosts(List<HostInformation> hosts) {
		this.hosts = hosts;
	}
}
