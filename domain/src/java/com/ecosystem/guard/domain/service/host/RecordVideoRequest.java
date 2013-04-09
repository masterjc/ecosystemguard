/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.domain.service.host;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ecosystem.guard.domain.Request;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
public class RecordVideoRequest extends Request {
	private Integer length;
	private VideoConfiguration videoConfiguration;
	
	public Integer getLength() {
		return length;
	}
	
	@XmlElement
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public VideoConfiguration getVideoConfiguration() {
		return videoConfiguration;
	}
	
	@XmlElement
	public void setVideoConfiguration(VideoConfiguration videoConfiguration) {
		this.videoConfiguration = videoConfiguration;
	}
}
