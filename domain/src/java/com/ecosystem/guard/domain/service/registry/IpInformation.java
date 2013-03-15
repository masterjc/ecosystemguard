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
	private Date lastChange;

	public String getPublicIp() {
		return publicIp;
	}

	@XmlElement
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public Date getLastChange() {
		return lastChange;
	}

	@XmlElement
	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}

}
