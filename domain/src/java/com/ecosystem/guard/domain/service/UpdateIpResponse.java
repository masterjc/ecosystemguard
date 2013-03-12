/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain.service;

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
@XmlSeeAlso(UpdateIpStatus.class)
public class UpdateIpResponse extends Response {
	private boolean updated;
	private String publicIp;

	public boolean isUpdated() {
		return updated;
	}

	@XmlElement
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public String getPublicIp() {
		return publicIp;
	}

	@XmlElement
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

}
