package com.ecosystem.guard.common;

import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.ecosystem.guard.domain.exceptions.DeserializerException;

public class Deserializer {
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> objType, Reader reader)
			throws DeserializerException {
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(objType);
			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

			return (T) jaxbMarshaller.unmarshal(reader);
		}
		catch (JAXBException e) {
			throw new DeserializerException(e);
		}
	}
}
