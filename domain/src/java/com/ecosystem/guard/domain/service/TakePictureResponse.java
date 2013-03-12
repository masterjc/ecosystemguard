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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Response;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
@XmlSeeAlso(TakePictureStatus.class)
public class TakePictureResponse extends Response {

}
