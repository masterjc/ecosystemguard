package com.ecosystem.guard.domain.service.registry;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ecosystem.guard.domain.Response;

@XmlRootElement
@XmlSeeAlso(value=AuthNStatus.class)
public class AuthNResponse extends Response {

}
