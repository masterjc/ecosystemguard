package com.ecosystem.guard.domain;

import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class Deserializer {
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> objType, Reader reader)
			throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(objType);
		Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

		return (T) jaxbMarshaller.unmarshal(reader);
	}
}
