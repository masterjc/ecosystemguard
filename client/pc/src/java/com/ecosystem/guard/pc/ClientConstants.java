/*
 * Copyright (c) 1999-2013 Safelayer Secure Communications, S.A.
 *
 * All rights reserved. No part of this source code may be reproduced,
 * stored in a retrieval system, or transmitted, in any form or by any
 * means, electronic, mechanical, photocopying, recording or otherwise,
 * except as in the end-user license agreement, without the prior
 * permission of the copyright owner.
 */

package com.ecosystem.guard.pc;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
public class ClientConstants {
	public static String ECOSYSTEM_URL_HEAD = "http://";
	public static String ECOSYSTEM_URL_ENDING = ":8080/ecosystemguard-registry/";
	
	public static String ECOSYSTEM_SERVER = "localhost:8080";
	// public static String ECOSYSTEM_SERVER = "registry-ecosystemguard.rhcloud.com";
	public static String ECOSYSTEM_BASE_URL = "http://" + ECOSYSTEM_SERVER + "/ecosystemguard-registry/";

	public static String GET_IP_SERVICE = ECOSYSTEM_BASE_URL + "getip";
	public static String GET_HOSTS_SERVICE = ECOSYSTEM_BASE_URL + "gethosts";
	public static String AUTHN_SERVICE = ECOSYSTEM_BASE_URL + "authn";
	public static String REGISTER_SERVICE = ECOSYSTEM_BASE_URL + "register";
	public static String UNREGISTER_SERVICE = ECOSYSTEM_BASE_URL + "unregister";
	public static String UPDATE_CREDENTIALS_SERVICE = ECOSYSTEM_BASE_URL + "updatecredentials";
	
	public static String TAKE_PICTURE_SERVICE = "/takepicture";
	public static String RECORD_VIDEO_SERVICE = "/recordvideo";
	
	
}
