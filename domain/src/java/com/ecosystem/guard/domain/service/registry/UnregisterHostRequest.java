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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Request;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class UnregisterHostRequest extends Request {
	private HostInformation hostInformation;

	public HostInformation getHostInformation() {
		return hostInformation;
	}

	@XmlElement
	public void setHostInformation(HostInformation hostInformation) {
		this.hostInformation = hostInformation;
	}
}
