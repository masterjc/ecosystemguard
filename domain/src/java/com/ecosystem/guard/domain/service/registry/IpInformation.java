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

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class IpInformation {

	private String publicIp;
	private String privateIp;
	private Date lastPublicIpChange;

	public String getPublicIp() {
		return publicIp;
	}

	@XmlElement
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public Date getLastPublicIpChange() {
		return lastPublicIpChange;
	}

	@XmlElement
	public void setLastPublicIpChange(Date lastChange) {
		this.lastPublicIpChange = lastChange;
	}

	
	public String getPrivateIp() {
		return privateIp;
	}

	@XmlElement
	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}
}
