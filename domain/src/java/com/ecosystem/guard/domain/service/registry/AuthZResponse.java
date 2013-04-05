package com.ecosystem.guard.domain.service.registry;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Response;

/**
 * 
 * @author juancarlos.fernandez
 * @version $Revision$
 */
@XmlRootElement
@XmlSeeAlso(value = AuthZStatus.class)
public class AuthZResponse extends Response {

}
