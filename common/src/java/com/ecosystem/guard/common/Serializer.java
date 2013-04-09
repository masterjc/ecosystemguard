package com.ecosystem.guard.common;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Serializer {
	public static <T> String serialize(T request, Class<T> objType) throws Exception {
		StringWriter writer = new StringWriter();
		serialize(request, objType, writer);
		return writer.toString();
	}
	
	public static <T> void serialize(T request, Class<T> objType, Writer writer) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(objType);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(request, writer);
	}
}
